package com.example.ebook.service;

import com.example.ebook.dto.CommentDTO;
import com.example.ebook.dto.UserCommentDTO;
import com.example.ebook.enums.CommentTypeEnum;
import com.example.ebook.enums.NotificationEnum;
import com.example.ebook.enums.NotificationStatusEnum;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.*;
import com.example.ebook.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2020/3/14 12:19
 * @Description
 */
@Service
public class CommentService {

	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private CommentExtMapper commentExtMapper;
	@Autowired
	private BookMapper bookMapper;
	@Autowired
	private BookExtMapper bookExtMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private NotificationMapper notificationMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private PostExtMapper postExtMapper;
	
	@Transactional
	public void insert(Comment comment) {
		
		if (comment.getParentId() == null || comment.getParentId() == 0) {
			throw new MyException(ResultCode.TARGET_PARAM_NOT_FOUND);
		}
		if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
			throw new MyException((ResultCode.TYPE_PARAM_WRONG));
		}
		//回复书籍 书籍评论
		if (comment.getType().equals(CommentTypeEnum.BOOK.getType())) {
			Book book = bookMapper.selectByPrimaryKey(comment.getParentId());
			if (book == null) {
				throw new MyException(ResultCode.BOOK_NOT_FOUND);
			}
			
			commentMapper.insertSelective(comment);
			//加一操作 增加书籍评论数
			Book parentBook = new Book();
			book.setId(book.getId());
			parentBook.setCommentCount(1);
			bookExtMapper.incCommentCount(book);
			// 书籍没有通知消息 增加通知消息
		}
		//回复书籍一级评论一级、一级评论下面的二级评论
		if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
			Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
			if (dbComment == null) {
				throw new MyException(ResultCode.COMMENT_NOT_FOUND);
			}
			User receiver = userMapper.selectByPrimaryKey(comment.getRecevierId());
			if (receiver == null) {
				throw new MyException(ResultCode.RECEIVER_NOT_FOUND);
			}
			User commentator = userMapper.selectByPrimaryKey(comment.getCommentator());
			if (commentator == null) {
				throw new MyException(ResultCode.USER_NOT_FOUND);
			}
			commentMapper.insertSelective(comment);
			//增加评论数
			Comment parentComment = new Comment();
			Book book = bookMapper.selectByPrimaryKey(comment.getCommentTopic());
			parentComment.setId(comment.getParentId());
			parentComment.setCommentCount(1);
			commentExtMapper.incCommentCount(parentComment);
			//创建通知
			createNotify(comment, comment.getRecevierId(),NotificationEnum.REPLY_COMMENT,commentator.getUserName(),book.getBookName(),book.getId());
		}
		//回复讨论 一级评论
		if (comment.getType().equals(CommentTypeEnum.TOPIC.getType())) {
			Post post = postMapper.selectByPrimaryKey(comment.getParentId());
			if (post == null) {
				throw new MyException(ResultCode.POST_NOT_FOUND);
			}
			User commentator = userMapper.selectByPrimaryKey(comment.getCommentator());
			if (commentator == null) {
				throw new MyException(ResultCode.USER_NOT_FOUND);
			}
			commentMapper.insertSelective(comment);
			post.setCommentCount(1);
			postExtMapper.incCommentCount(post);
			
			//创建通知
			createNotify(comment, comment.getRecevierId(), NotificationEnum.REPLY_TOPIC, commentator.getUserName(), post.getTitle(), post.getId());
		}
		//回复讨论下面的一级或者二级评论
		if (comment.getType().equals(CommentTypeEnum.TOPIC_COMMENT.getType())) {
			Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
			if (dbComment == null) {
				throw new MyException(ResultCode.COMMENT_NOT_FOUND);
			}
			User receiver = userMapper.selectByPrimaryKey(comment.getRecevierId());
			if (receiver == null) {
				throw new MyException(ResultCode.RECEIVER_NOT_FOUND);
			}
			User commentator = userMapper.selectByPrimaryKey(comment.getCommentator());
			if (commentator == null) {
				throw new MyException(ResultCode.USER_NOT_FOUND);
			}
				commentMapper.insertSelective(comment);
				//增加评论数
				Comment parentComment = new Comment();
				Post post = postMapper.selectByPrimaryKey(comment.getCommentTopic());
				parentComment.setId(comment.getParentId());
				parentComment.setCommentCount(1);
				commentExtMapper.incCommentCount(parentComment);
				//创建通知
				createNotify(comment, comment.getRecevierId(),NotificationEnum.REPLY_TOPIC_COMMENT,commentator.getUserName(),post.getTitle(),post.getId());
			}
		}
	public List<CommentDTO> list(Integer type, Long id) {
		//获取一级评论
		CommentExample commentExample = new CommentExample();
		commentExample.createCriteria()
				.andParentIdEqualTo(id)
				.andTypeEqualTo(type);
		commentExample.setOrderByClause("gmt_create desc");
		List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);
		
		if (comments.size() == 0) {
			return new ArrayList<>();
		}
		//获取书籍去重一级评论人id
		List<Long> userIds = new ArrayList<>();
		Set<Long> uniqueValues = new HashSet<>();
		for (Comment comment : comments) {
			Long commentator = comment.getCommentator();
			if (uniqueValues.add(commentator)) {
				userIds.add(commentator);
			}
		}
		//获取一级评论人以及转换成map
		UserExample userExample = new UserExample();
		userExample.createCriteria()
				.andIdIn(userIds);
		List<User> users = userMapper.selectByExample(userExample);
		Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
		
		//将Comment 转换为CommentDTO
		List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
			CommentDTO commentDTO = new CommentDTO();
			BeanUtils.copyProperties(comment, commentDTO);
			commentDTO.setCommentUser(userMap.get(comment.getCommentator()));
			commentDTO.setReply(getSecondComment(comment.getId(),type + 1));
			return commentDTO;
		}).collect(Collectors.toList());
		return commentDTOS;
	}
	
	public List<CommentDTO> getSecondComment(Long id, int type) {
		
		CommentExample commentExample = new CommentExample();
		commentExample.createCriteria()
				.andTypeEqualTo(type)
				.andParentIdEqualTo(id);
		commentExample.setOrderByClause("gmt_create desc");
		List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);
		
		if (comments.size() == 0) {
			return new ArrayList<>();
		}
		//获取去重的二级评论人id 以及接收者Id
		List<Long> secondUserIds = comments.stream().map(Comment::getCommentator).distinct().collect(Collectors.toList());
		List<Long> secondReceiverIds = comments.stream().map(Comment::getRecevierId).distinct().collect(Collectors.toList());
		//获取二级评论人 接收者转换成map
		UserExample userExample1 = new UserExample();
		userExample1.createCriteria()
				.andIdIn(secondUserIds);
		List<User> secondUser = userMapper.selectByExample(userExample1);
		Map<Long, User> secondUserMap = secondUser.stream().collect(Collectors.toMap(User::getId, user -> user));
		UserExample userExample2 = new UserExample();
		userExample1.createCriteria()
				.andIdIn(secondReceiverIds);
		List<User> secondReceiver = userMapper.selectByExample(userExample2);
		Map<Long, User> secondReceiverMap = secondReceiver.stream().collect(Collectors.toMap(User::getId, user -> user));
		
		return comments.stream().map(comment -> {
			CommentDTO commentDTO = new CommentDTO();
			BeanUtils.copyProperties(comment, commentDTO);
			commentDTO.setCommentUser(secondUserMap.get(comment.getCommentator()));
			commentDTO.setRecevier(secondReceiverMap.get(comment.getRecevierId()));
			return commentDTO;
		}).collect(Collectors.toList());
	}
	
	public List<UserCommentDTO> getUserCommentById(Long id) {
		
		List<UserCommentDTO> userCommentDTOS = new ArrayList<>();
		//包括评论内容 评论主题或者评论 评论对象
		User user = userMapper.selectByPrimaryKey(id);
		if (user == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		}
		//获取用户所有评论
		CommentExample commentExample = new CommentExample();
		commentExample.createCriteria()
				.andCommentatorEqualTo(id);
		List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);
		
		if (comments.size() == 0) {
			return new ArrayList<>();
		}
		
		Map<Long, Comment> commentMap = comments.stream().collect(Collectors.toMap(Comment::getId, comment -> comment));
		//获取被评论人id集合
		List<Long> receiverId = comments.stream().filter(comment -> !CommentTypeEnum.BOOK.getType().equals(comment.getType())).map(Comment::getRecevierId).collect(Collectors.toList());
		//获取被评论人
		UserExample userExample = new UserExample();
		userExample.createCriteria()
				.andIdIn(receiverId);
		List<User> receivers = userMapper.selectByExample(userExample);
		//转换为map对象
		Map<Long, User> receiverMap = receivers.stream().collect(Collectors.toMap(User::getId, users -> users));
		//将评论分类
		List<Comment> bookComments = comments.stream().filter(comment -> CommentTypeEnum.BOOK.getType().equals(comment.getType())).collect(Collectors.toList());
		List<Comment> commentsComment = comments.stream().filter(comment -> CommentTypeEnum.COMMENT.getType().equals(comment.getType())).collect(Collectors.toList());
		List<Comment> topicComments = comments.stream().filter(comment -> CommentTypeEnum.TOPIC.getType().equals(comment.getType())).collect(Collectors.toList());
		List<Comment> topicCommentComments = comments.stream().filter(comment -> CommentTypeEnum.TOPIC_COMMENT.getType().equals(comment.getType())).collect(Collectors.toList());
		//首先处理书籍评论
		//获取评论的书籍
		List<Long> booksId = bookComments.stream().map(Comment::getCommentTopic).collect(Collectors.toList());
		BookExample bookExample = new BookExample();
		bookExample.createCriteria()
				.andIdIn(booksId);
		List<Book> books = bookMapper.selectByExampleWithBLOBs(bookExample);
		Map<Long, Book> bookMap = books.stream().collect(Collectors.toMap(Book::getId, book -> book));
		
		if(bookComments.size() != 0) {
			List<UserCommentDTO> bookCommentsDTO = bookComments.stream().map(comment -> {
				UserCommentDTO userCommentDTO = new UserCommentDTO();
				BeanUtils.copyProperties(comment, userCommentDTO);
				userCommentDTO.setCommentBook(bookMap.get(comment.getCommentTopic()));
				return userCommentDTO;
			}).collect(Collectors.toList());
			userCommentDTOS.addAll(bookCommentsDTO);
		}
		if (commentsComment.size() != 0) {
			//获取二级评论
			List<UserCommentDTO> commentDTOS = commentsComment.stream().map(comment -> {
				UserCommentDTO userCommentDTO = new UserCommentDTO();
				BeanUtils.copyProperties(comment, userCommentDTO);
				userCommentDTO.setCommentBook(bookMap.get(comment.getCommentTopic()));
				userCommentDTO.setComment(commentMap.get(comment.getParentId()));
				userCommentDTO.setReceiver(receiverMap.get(comment.getRecevierId()));
				return userCommentDTO;
			}).collect(Collectors.toList());
			userCommentDTOS.addAll(commentDTOS);
		}
		
		//获取评论主题
		List<Long> postIds = topicComments.stream().map(Comment::getCommentTopic).collect(Collectors.toList());
		PostExample postExample = new PostExample();
		postExample.createCriteria()
				.andIdIn(postIds);
		List<Post> posts = postMapper.selectByExampleWithBLOBs(postExample);
		Map<Long, Post> postMap = posts.stream().collect(Collectors.toMap(Post::getId, post -> post));
		
		if (topicComments.size() != 0) {
			List<UserCommentDTO> postCommentDTO = topicComments.stream().map(comment -> {
				UserCommentDTO userCommentDTO = new UserCommentDTO();
				BeanUtils.copyProperties(comment, userCommentDTO);
				userCommentDTO.setCommentPost(postMap.get(comment.getCommentTopic()));
				return userCommentDTO;
			}).collect(Collectors.toList());
			userCommentDTOS.addAll(postCommentDTO);
		}
		if (topicCommentComments.size() != 0) {
			List<UserCommentDTO> postCommentComments = topicCommentComments.stream().map(comment -> {
				UserCommentDTO userCommentDTO = new UserCommentDTO();
				BeanUtils.copyProperties(comment, userCommentDTO);
				userCommentDTO.setCommentPost(postMap.get(comment.getCommentTopic()));
				userCommentDTO.setComment(commentMap.get(comment.getParentId()));
				userCommentDTO.setReceiver(receiverMap.get(comment.getRecevierId()));
				return userCommentDTO;
			}).collect(Collectors.toList());
			userCommentDTOS.addAll(postCommentComments);
		}
		return userCommentDTOS;
	}
	
	private void createNotify(Comment record, Long receiver, NotificationEnum notificationEnum, String notifierName, String outerTitle, Long outerId) {
		
		if (receiver.equals(record.getCommentator())) {
			return;
		}
		Notification notification = new Notification();
		notification.setNotifier(record.getCommentator());
		notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
		notification.setReceiver(receiver);
		notification.setOuterid(outerId);
		notification.setType(notificationEnum.getType());
		notification.setGmtCreate(System.currentTimeMillis());
		notification.setNotifierName(notifierName);
		notification.setOuterTitle(outerTitle);
		notificationMapper.insert(notification);
	}
}
