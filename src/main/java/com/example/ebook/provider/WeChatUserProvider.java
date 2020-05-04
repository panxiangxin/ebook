package com.example.ebook.provider;

import com.alibaba.fastjson.JSONObject;
import com.example.ebook.dto.WeChatKey;
import com.example.ebook.dto.WeChatUserInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author pxx
 * Date 2020/4/21 20:30
 * @Description
 */
@Component
public class WeChatUserProvider {

	@Value("${auth.wechat.sessionHost}")
	private String sessionHost;
	@Value("${auth.wechat.appId}")
	private String appId;
	@Value("${auth.wechat.secret}")
	private String secret;
	@Value("${auth.wechat.grantType}")
	private String grantType;
	
	public WeChatKey getWxOpenId(String code) {
		String url = sessionHost + "?appId=" + appId + "&secret=" + secret + "&grantType=" + grantType;
		
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
								  .url(url + "&js_code=" + code)
								  .build();
		try {
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			return JSONObject.parseObject(result, WeChatKey.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
