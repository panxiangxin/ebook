package com.example.ebook.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.ebook.annotation.PassToken;
import com.example.ebook.annotation.UserLoginToken;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.UserMapper;
import com.example.ebook.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author pxx
 * Date 2020/2/27 16:52
 * @Description
 */
@Service
public class AuthenticationInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("token");// 从 http 请求头中取出 token
		// 如果不是映射到方法直接通过
		if(!(handler instanceof HandlerMethod)){
			return true;
		}
		HandlerMethod handlerMethod=(HandlerMethod)handler;
		Method method=handlerMethod.getMethod();
		//检查是否有passtoken注释，有则跳过认证
		if (method.isAnnotationPresent(PassToken.class)) {
			PassToken passToken = method.getAnnotation(PassToken.class);
			if (passToken.required()) {
				return true;
			}
		}
		//检查有没有需要用户权限的注解
		if (method.isAnnotationPresent(UserLoginToken.class)) {
			UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
			if (userLoginToken.required()) {
				// 执行认证
				if (token == null) {
					throw new MyException(ResultCode.TOKEN_NOT_FOUND);
				}
				// 获取 token 中的 user id
				String userId;
				long id;
				try {
					userId = JWT.decode(token).getAudience().get(0);
					id = Long.parseLong(userId);
				} catch (JWTDecodeException j) {
					throw new RuntimeException("401");
				}
				User user = userMapper.selectByPrimaryKey(id);
				if (user == null) {
					throw new MyException(ResultCode.USER_NOT_FOUND);
				}
				// 验证 token
				token = token.replaceAll("\"", "");
				JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
				try {
					jwtVerifier.verify(token);
				} catch (JWTVerificationException e) {
					throw new MyException(ResultCode.TOKEN_INVALID);
				}
				return true;
			}
		}
		return true;
	}
}
