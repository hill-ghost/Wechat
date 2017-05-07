package com.weixin.test;

import com.weixin.common.AccessToken;
import com.weixin.util.WechatUtil;

public class Main {
	public static void Main(String[] args) {
		AccessToken token = WechatUtil.getAccessToken();
	}
}
