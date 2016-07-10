package com.vc.swoop.model;

public class ChatLog {

	long id;
	long ad_id;
	long ad_user_id;
	long other_user_id;
	int talk_type;
	String msg;
	long time;
	int ad_user_isread;
	int other_user_isread;
	String ad_username;
	String other_username;
	
	String ad_title;
	
	public String getAd_title() {
		return ad_title;
	}
	public void setAd_title(String ad_title) {
		this.ad_title = ad_title;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAd_id() {
		return ad_id;
	}
	public void setAd_id(long ad_id) {
		this.ad_id = ad_id;
	}
	public long getAd_user_id() {
		return ad_user_id;
	}
	public void setAd_user_id(long ad_user_id) {
		this.ad_user_id = ad_user_id;
	}
	public long getOther_user_id() {
		return other_user_id;
	}
	public void setOther_user_id(long other_user_id) {
		this.other_user_id = other_user_id;
	}
	public int getTalk_type() {
		return talk_type;
	}
	public void setTalk_type(int talk_type) {
		this.talk_type = talk_type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getAd_user_isread() {
		return ad_user_isread;
	}
	public void setAd_user_isread(int ad_user_isread) {
		this.ad_user_isread = ad_user_isread;
	}
	public int getOther_user_isread() {
		return other_user_isread;
	}
	public void setOther_user_isread(int other_user_isread) {
		this.other_user_isread = other_user_isread;
	}
	public String getAd_username() {
		return ad_username;
	}
	public void setAd_username(String ad_username) {
		this.ad_username = ad_username;
	}
	public String getOther_username() {
		return other_username;
	}
	public void setOther_username(String other_username) {
		this.other_username = other_username;
	}
	
	
	
}
