package com.example.ebook.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;

/**
 * @author pxx
 * Date 2020/3/8 14:13
 * @Description
 */
public class Test {
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzIiwiZXhwIjoxNTgzODY0NTMyLCJpYXQiOjE1ODM4MjEzMzJ9.HTHqulIpwL55Y-_rl5JZvuOuseHEaPcoyRrsf6n8HUA";
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("1234")).build();
		try {
			jwtVerifier.verify(token);
			System.out.println("success");
		} catch (JWTVerificationException e) {
			throw new MyException(ResultCode.TOKEN_INVALID);
		}
	}
}
