package com.example.ebook.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ebook.model.User;

import java.util.Date;

/**
 * @author pxx
 * Date 2020/2/27 16:48
 * @Description
 */
public class JwtUtil {
	
	private static final int TOKEN_EXPIRE_TIME = 12 * 60 * 60 * 1000;
	
	public static String getToken(User user) {
		String token="";
		Date start = new Date();
		long currentTime = System.currentTimeMillis() + TOKEN_EXPIRE_TIME;
		Date end = new Date(currentTime);
		token = JWT.create().withAudience(user.getId().toString())
						.withIssuedAt(start)
						.withExpiresAt(end)
						.sign(Algorithm.HMAC256(user.getPassword()));
		return token;
	}
}
