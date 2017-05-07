package com.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixin.util.MessageUtil;
import com.weixin.util.VerifyUtil;
import com.weixin.common.*;

 /**
  * 核心请求处理类
  */

@WebServlet(urlPatterns = {"/wx.do"}, asyncSupported = true, name = "Verify")

public class Verify extends HttpServlet {
 
     private static final long serialVersionUID = -5021188348833856475L;
     
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
         // 微信加密签名  
         String signature = request.getParameter("signature");  
         // 时间戳  
         String timestamp = request.getParameter("timestamp");  
         // 随机数  
         String nonce = request.getParameter("nonce");  
         // 随机字符串  
         String echostr = request.getParameter("echostr");  
         
         PrintWriter out = response.getWriter();  
         // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
         if(VerifyUtil.checkSignature(signature, timestamp, nonce)) {  
             out.print(echostr);  
         }  
         out.close();
         out = null;
     }
     
     
     //消息的接收、处理、响应  
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         request.setCharacterEncoding("UTF-8");
         response.setCharacterEncoding("UTF-8");
    	 PrintWriter out = response.getWriter();
    	 
    	 Map<String, String> map = MessageUtil.xmlToMap(request);
         String toUserName = map.get("ToUserName");
         String fromUserName = map.get("FromUserName");
         String msgType = map.get("MsgType");

         String message = "";
         
         if(msgType.equals(MessageUtil.MES_TEXT)) {
        	 //普通文本消息
             String content = map.get("Content");
             if(content.equals("1")){
            	 message = MessageUtil.initNews(toUserName, fromUserName);
             }
             else {
            	 if(content.equals("2")) {
            		 message = MessageUtil.initText(toUserName, fromUserName, "hill-ghost.cn/ec");
            	 }
            	 else {
            		 message = MessageUtil.initText(toUserName, fromUserName, content);        
             
            	 }
             }
         }
         else {
        	 if(msgType.equals(MessageUtil.MES_EVENT)) {
        		 //时间消息
        		 String eventType = map.get("Event");
        		 if(eventType.equals(MessageUtil.MES_SUBSCRIBE)) {
        			 //被关注时发送此消息（注意如果消息中包含.会自动转化成带链接的文字）
        			 message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
        		 }
        	 }
        	 else {
	        	 if(msgType.equals(MessageUtil.MES_IMAGE)) {
	        		 //图片消息
	        		 String picUrl = map.get("PicUrl");
	            	 ImageMessage image = new ImageMessage();
	            	 image.setFromUserName(toUserName);
	            	 image.setToUserName(fromUserName);
	            	 image.setMsgType(MessageUtil.MES_IMAGE);
	            	 image.setCreateTime(new Date().getTime());
	            	 image.setPicUrl(picUrl);
	        		 message = MessageUtil.imageMessageToXml(image);
	        	 }
        	 }
         }
         out.print(message);
         out.close();
     }
}