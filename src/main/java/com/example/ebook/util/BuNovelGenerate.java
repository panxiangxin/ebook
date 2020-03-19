package com.example.ebook.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 小说数据落库
 *
 * @author hsh
 * @email 287878222@qq.com
 * @date 2019-07-15 20:43:05
 */

public class BuNovelGenerate {
	private  static Pattern p = Pattern.compile("(^\\s*第)(.{1,9})[章节卷集部篇回](\\s)(.*)($\\s*)");
	
	public static void main(String[] args) throws FileNotFoundException {
		String bookName = "贼胆";
		File file = new File("D:\\home\\ebook\\txt\\《贼胆》（校对版全本）作者：发飙的蜗牛.txt");
		read(bookName, file);
	}
	public static List<Content> read(String bookName, File fileNadirs) throws FileNotFoundException {
		
		List<Content> contents = new ArrayList<>();
		try {
			// 编码格式
			String encoding = resolveCode(fileNadirs);
			// 判断文件是否存在
			if (fileNadirs.isFile() && fileNadirs.exists()) {
				// 输入流
				// 考虑到编码格式
				InputStreamReader read = new InputStreamReader(new FileInputStream(fileNadirs), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				long count = (long) 0;
				boolean bflag=false;
				int n=0;
				String newStr=null;
				String titleName=null;
				String previousChapterName = null;
				//新章节名称
				String newChapterName = null;
				String substring=null;
				int indexOf=0;
				int indexOf1=0;
				int line = 0;
				//小说内容类
				Content content;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					content = new Content();
					//小说名称
					content.setName(bookName);
					count++;
					// 正则表达式
					Matcher matcher = p.matcher(lineTxt);
					Matcher matcher1 = p.matcher(lineTxt);
					newStr = newStr + lineTxt;
					while (matcher.find()) {
						titleName = matcher.group();
						//章节去空
						previousChapterName = newChapterName;
						newChapterName = titleName.trim();
						//获取章节
						indexOf1 = indexOf;
						indexOf = newStr.indexOf(newChapterName);
						//System.out.println(newChapterName + ":" + "第" + count + "行"); // 得到返回的章
						if (bflag) {
							bflag = false;
							break;
						}
						if (n == 0) {
							indexOf1 = newStr.indexOf(newChapterName);
						}
						n = 1;
						bflag = true;
					}
					while (matcher1.find()) {
						if (indexOf != indexOf1) {
							content.setChapter(previousChapterName);
							content.setNumber(++line);
							content.setId(line);
							substring = newStr.substring(indexOf1, indexOf);
							content.setContent(substring);
							contents.add(content);
						}
					}
				}
				//存储最后一章
				content = new Content();
				content.setNumber(++line);
				content.setId(line);
				content.setChapter(newChapterName);
				content.setContent(newStr.substring(indexOf, newStr.length() - 1));
				contents.add(content);
				bufferedReader.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return contents;
	}
	public static String resolveCode(File file) throws Exception {
		InputStream inputStream = new FileInputStream(file);
		byte[] head = new byte[3];
		inputStream.read(head);
		//或GBK
		String code = "gb2312";
		if (head[0] == -1 && head[1] == -2) {
			code = "UTF-16";
		} else if (head[0] == -2 && head[1] == -1) {
			code = "Unicode";
		} else if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
			code = "UTF-8";
		}
		
		inputStream.close();
		return code;
		
	}
}
