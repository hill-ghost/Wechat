package com.weixin.common;

public class ImageMessage extends BaseMessage{
	
	/*
	 * Õº∆¨œ˚œ¢¿‡
	 */
	
	private String picUrl;
	private Image image;

	
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}	
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
}
