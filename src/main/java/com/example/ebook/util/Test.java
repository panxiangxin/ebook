package com.example.ebook.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author pxx
 * Date 2020/3/8 14:13
 * @Description
 */
public class Test {
	public static void main(String[] args) throws UnknownHostException {
		String addr = InetAddress.getLocalHost().getHostAddress();
		System.out.println(addr);
	}
}
