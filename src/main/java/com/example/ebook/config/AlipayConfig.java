package com.example.ebook.config;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author pxx
 * Date 2020/3/20 15:24
 * @Description
 */
public class AlipayConfig {
	//这里用natapp内外网穿透
	public static final String natUrl = "http://panxx.natapp1.cc";
	
	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String APP_ID = "2016101800719531";
	
	// 商户私钥，您的PKCS8格式RSA2私钥，这些就是我们刚才设置的
	public static String MERCHANT_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCCu+up+10mLTYNs9mpNQnHsfpXPyvn+ZqpvfCVAYWDpyZ8ZLa+EPXbnKIS4rIqM5M2brR0Bua9mVuWOeJNGSscODrRrWKLi2Kg2U5pp5AFYBDl7HZlU2aGovoEdq/MG0P73trICqWXzM12rtuVMqLGmPJKBAPbRPgAG57GfyOcEw/nHqsNwaes9nEp8sHPSnGIJMGm3FFbqqcL18wcGalrA8gUvJbwjEUv6cwIZJLElez8KgoDBC1VEGzZKwWfB1HWadugveWJ63grmptZXA9dsWxnhiLXi+t3heAxx3jQ9OF2AHYgAx32iEsmjM+Xezw9DW0rUoqMDY/QWMDUWou9AgMBAAECggEAI+DzAkmL/Og3h3cuazO1JWsA62TY5ecovSD1n881FzaOZ2E0jeEVuFm6WTiB8PcKlcu4Foe2V5549jeYldQscqgz7EuLWgS84qwAyxpDelYtDQziSVikMJsVxpfUE04AUGWO4YuaepioYsQGW2H0LlDXXdzQOiMpdM4fuxY1baWfr7PjONMTy8Kw+y2RxNj74sm0x/XLrYeo4JEgEBVIeBx0RjFONe88h4cZRjm0dwUxegiaysbInn0T+s0TsTlnuhOIEvnXPzJHJIfx+ynzhZeiQKlz4L/WFyfRh1krTYYBbPd2f67TOI6zmoWJoEXIO8MAZK0qKKxLkeyg6wz2eQKBgQDZmCSUpAbTVLdLMXu+gvuFq74H0Q2IR0ZcCi2Iq3TnG9S5gIvsroyCj48B5s0AMvkMnbFPpJPjFZWQXajiJAn8TjoqUvsa8OUHWyWXk3bCIMyOsEnZoTOy2KUgYdjtJmqTTTdqfC3Bk9ndfa5wyoTTJAYC583f8u+F+LLg7PP30wKBgQCZzw3b0ZJXVgTzupljFQs+7f/ak5YVzoQHBmtCByfeRamvfcOwciTYelQd8kuOkrpVEIdnrqnbqmecdnuyuFHH6whoFJnpA4aBzB1b3H/HtSYdG0y7Y4259WrqlLYdk3a08DWnMYy60WOWYL8cpaFRFBYv4iPs0E69xsr7qdRELwKBgQC75TUxZbK/Kphcxg4lJNYtR3CBL3SQCXwoXmGTcvsv1kiLJwWtC80psO28pqC/W0TUMBe8vlE19p2AXGBJZvV4edSgV6eeTAZ5o6uCiEQ0vj3luTUMSLdr6Mgufk/j+Rhuum7v9OtODW5EZRMNMtQfvyuxtpmAmTdcaLWOpQ7HIQKBgQCNz7s+Zq7f+X6dXfmYwklZH0UcOaFSv9M5SvDPsxtOM7qtBG7OG5/fPIKcDc8eSCimz5OYxBD2aNoZB6EreghlJE3e8MercMWunQygqt/huSLICWipCbQdtap7WNvEzzbOwv8lBWl0wiRo/RCYEBp5T+TZB08KRFGlZkm22bHNnwKBgCTEACKGSjhu0d6gSTleh6mgWAknJroIxuPxV2GvesuECken2p3eaEhaT6n6mQVeavAlxIHYExGbVS23qIpa0jj4eUN4tdajKnuMjF8c17xL2Uqe1nisN3t+I28oi/uwaXYJQTX/3ZCfv84hC3gMM3K/ziHU6f3BzdC1jVMmrsP0";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。，这些就是我们刚才设置的
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhsn9tOQ9+0jyf92bF7S8vX5r5dOezkJopXKgc4TZQROFzz7buYPmjzQkSDXz5kkXhBHbciUGunxY+GKz7CXO44n7JcKy5eEterFbvwQjidZLTYQ17q2m8YMAGVrWH9lirl6xwxxbqhgR9U0BVyaYBJ8t9B6eTlwBC9/AOzX0MBp1sl6AqvBowxTDMiE5aPYLGnSpwsubt9G4b4CZZFNO9A69EuLQFRWMdFaEnlkEpqWXWdDCq1iz6xCm1mwcMXccI1QYByFrDnkZAvGnFdYa0KsxUtlRJFFU8MqLSFmGtm8TeWPTEaACQ3xPqFmJHJeNXvNuIx2HHYpX1ZMzI5mlywIDAQAB";
	
	//异步通知，再这里我们设计自己的后台代码
	public static String notify_url = natUrl + "/alipay/notify";
	
	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080";
	
	// 签名方式
	public static String SIGN_TYPE = "RSA2";
	
	// 字符编码格式
	public static String CHARSET = "utf-8";
	
	// 支付宝网关
	public static String GATEWAYURL = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝
	public static String LOG_PATH = "D:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * @param sWord 要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(LOG_PATH + "alipay_log_" + System.currentTimeMillis()+".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
