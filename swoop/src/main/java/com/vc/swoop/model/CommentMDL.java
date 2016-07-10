package com.vc.swoop.model;

public class CommentMDL {
	private int id;
	private int intime;
	private int user_id;
	private int ad_id;
	private int currt_time;
	
	private String content;
	private String user_name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIntime() {
		return intime;
	}
	public void setIntime(int intime) {
		this.intime = intime;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getAd_id() {
		return ad_id;
	}
	public void setAd_id(int ad_id) {
		this.ad_id = ad_id;
	}
	public int getCurrt_time() {
		return currt_time;
	}
	public void setCurrt_time(int currt_time) {
		this.currt_time = currt_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
}
