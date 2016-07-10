package com.vc.swoop.model;

import java.io.Serializable;

public class User {

	
	private long user_id;
	private String session_id;
	private String device_id;
	private String password;
	private String phone;
	private String name;
	private double lat;
	private double lng;
	private long lastLoginTime;
	private String client_id;
	
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	
//	private User() {
//		this.session_id = "fed8d127dbf4a0b9f14eabedf8ff7ac4";
//		this.lat = 33;
//		this.lng = 100;
//	}
//	
//	private static User user = new User();
//
//	
//	public static User getInstance() {
//		return user;
//	}
	
}
