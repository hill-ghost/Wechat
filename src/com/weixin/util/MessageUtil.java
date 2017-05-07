package com.weixin.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.io.SAXReader;
import com.thoughtworks.xstream.XStream;
import com.weixin.common.*;
import org.dom4j.*;


public class MessageUtil {
	
	public static final String MES_TEXT = "text";
	public static final String MES_IMAGE = "image";
	public static final String MES_VOICE = "voice";
	public static final String MES_VIDEO = "video";
	public static final String MES_NEWS = "news";
	public static final String MES_LINK = "link";
	public static final String MES_LOCATION = "location";
	public static final String MES_EVENT = "event";
	public static final String MES_SUBSCRIBE = "subscribe";
	public static final String MES_CLICK = "CLICK";
	public static final String MES_VIEW = "VIEW";
	
	/*
	 * xmlת����Map����
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		
		try {
			InputStream ins = request.getInputStream();
			Document doc = reader.read(ins);
			Element root = doc.getRootElement();
			List<Element> list = root.elements();
			for(Element e : list) {
				map.put(e.getName(), e.getText());
			}
			ins.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	//���ı����͵���ͨ��Ϣת����xml��ʽ
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	//���ı����͵�ͼƬ��Ϣת����xml��ʽ
	public static String imageMessageToXml(ImageMessage imageMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	//���ı����͵�ͼ����Ϣת����xml��ʽ
	public static String newsMessageToXml(NewsMessage newsMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}
	
	public static String initText(String toUserName, String fromUserName, String content){
	   	TextMessage text = new TextMessage();
	   	text.setFromUserName(toUserName);
	   	text.setToUserName(fromUserName);
	   	text.setMsgType(MessageUtil.MES_TEXT);
	   	text.setCreateTime(new Date().getTime());
	   	text.setContent(content);
	   	return textMessageToXml(text);
	}
	
	public static String initImage(String toUserName, String fromUserName, String mediaId){
	   	ImageMessage image = new ImageMessage();
	   	image.setFromUserName(toUserName);
	   	image.setToUserName(fromUserName);
	   	image.setMsgType(MessageUtil.MES_IMAGE);
	   	image.setCreateTime(new Date().getTime());
	   	return imageMessageToXml(image);
	}
	
	public static String initNews(String toUserName, String fromUserName) {
		List<News> list = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();
		
		News news1 = new News();
		news1.setTitle("�ٶ�");
		news1.setDescription("�ٶ�һ�����֪��");
		news1.setPicUrl("http://123.207.114.243/Wechat/image/pic.jpg");
		news1.setUrl("www.baidu.com");
		
		list.add(news1);
		
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setMsgType(MessageUtil.MES_NEWS);
		newsMessage.setArticles(list);
		newsMessage.setArticleCount(list.size());
		
		return newsMessageToXml(newsMessage);
	}
		
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("��ӭ��ע:\n");
		sb.append("1 hello\n");
		sb.append("2.world");
		return sb.toString();
	}
}
