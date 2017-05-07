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
  * ������������
  */

@WebServlet(urlPatterns = {"/wx.do"}, asyncSupported = true, name = "Verify")

public class Verify extends HttpServlet {
 
     private static final long serialVersionUID = -5021188348833856475L;
     
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
         // ΢�ż���ǩ��  
         String signature = request.getParameter("signature");  
         // ʱ���  
         String timestamp = request.getParameter("timestamp");  
         // �����  
         String nonce = request.getParameter("nonce");  
         // ����ַ���  
         String echostr = request.getParameter("echostr");  
         
         PrintWriter out = response.getWriter();  
         // ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��  
         if(VerifyUtil.checkSignature(signature, timestamp, nonce)) {  
             out.print(echostr);  
         }  
         out.close();
         out = null;
     }
     
     
     //��Ϣ�Ľ��ա�������Ӧ  
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
        	 //��ͨ�ı���Ϣ
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
        		 //ʱ����Ϣ
        		 String eventType = map.get("Event");
        		 if(eventType.equals(MessageUtil.MES_SUBSCRIBE)) {
        			 //����עʱ���ʹ���Ϣ��ע�������Ϣ�а���.���Զ�ת���ɴ����ӵ����֣�
        			 message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
        		 }
        	 }
        	 else {
	        	 if(msgType.equals(MessageUtil.MES_IMAGE)) {
	        		 //ͼƬ��Ϣ
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