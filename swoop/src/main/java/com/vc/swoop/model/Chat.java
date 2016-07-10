package com.vc.swoop.model;

public class Chat {

	private long c_id;
	private long ad_id;
	private long user_id;
	private boolean ad_unread;
	private boolean user_unread;
	
	public long getC_id() {
		return c_id;
	}
	public void setC_id(long c_id) {
		this.c_id = c_id;
	}
	public long getAd_id() {
		return ad_id;
	}
	public void setAd_id(long ad_id) {
		this.ad_id = ad_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public boolean isAd_unread() {
		return ad_unread;
	}
	public void setAd_unread(boolean ad_unread) {
		this.ad_unread = ad_unread;
	}
	public boolean isUser_unread() {
		return user_unread;
	}
	public void setUser_unread(boolean user_unread) {
		this.user_unread = user_unread;
	}
	
}
