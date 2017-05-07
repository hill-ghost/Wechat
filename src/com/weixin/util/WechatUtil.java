package com.weixin.util;

import net.sf.json.JSONObject;

import java.io.File;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;

import com.weixin.common.AccessToken;

public class WechatUtil {
	
	private static final String APPID = "wxb9965f2e6cd9b70f";
	private static final String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	public static JSONObject doGetStr(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static JSONObject doPostStr(String url, String outStr) {
		DefaultHttpClient  httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
		try {
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			jsonObject = JSONObject.fromObject(result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static AccessToken getAccessToken() {
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject != null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	
	public static String upload(String filePath, String accessToken, String type) throws IOException {
		File file = new File(filePath);
		if(!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		return "";
	}
}
