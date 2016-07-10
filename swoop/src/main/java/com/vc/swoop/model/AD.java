package com.vc.swoop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
 
import android.R.integer;
import android.content.Context;

public class AD implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4176242591504401307L;
	
	private long ad_id;
	private String title;
	private String name;
	private String describe;
	private double price;
	private int commentcount;
	private int state;
	private long realease_time;
	private long end_time;
	private long user_id;
	private double lat;
	private double lng;
	private String pic1;
	private String pic2;
	private String pic3;
	private String pic4;
	private String pic5;
	private String in_watchlist;
	private long currt_time;
	private int pricetype;
	private int category;
	private int isFlow = 0;
	private int view_count=0;
	private int watchlistcount=0;
	private long withdrawn_time;
	private String phone;
	
	private boolean isCheck;
	
	

	public int getCommentcount() {
		return commentcount;
	}
	public void setCommentcount(int commentcount) {
		this.commentcount = commentcount;
	}
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public long getWithdrawn_time() {
		return withdrawn_time;
	}
	public void setWithdrawn_time(long withdrawn_time) {
		this.withdrawn_time = withdrawn_time;
	}
	public int getView_count() {
		return view_count;
	}
	public void setView_count(int view_count) {
		this.view_count = view_count;
	}
	public int getWatchlistcount() {
		return watchlistcount;
	}
	public void setWatchlistcount(int watchlistcount) {
		this.watchlistcount = watchlistcount;
	}
	public int getPricetype() {
		return pricetype;
	}
	public void setPricetype(int pricetype) {
		this.pricetype = pricetype;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getIsFlow() {
		return isFlow;
	}
	public void setIsFlow(int isFlow) {
		this.isFlow = isFlow;
	}
	public double getPrice() {
		try
		{
			return price;
			//BigDecimal bg = new BigDecimal(price);
			// double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			// return f1;
			///DecimalFormat df = new DecimalFormat("#.00");
			//return df.format(price);
		}
		catch(Exception ex)
		{
			return 0;
		}
		//return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public long getCurrt_time() {
		return currt_time;
	}
	public void setCurrt_time(long currt_time) {
		this.currt_time = currt_time;
	}
	public boolean isIn_watchlist() {
		return  "1".equals(this.in_watchlist);
	}
	public void setIn_watchlist(boolean in_watchlist) {
		if(in_watchlist)
		{
			this.in_watchlist = "1";
		}else {
			this.in_watchlist = "0";
		}
		
	}
	public String getPic1() {
		return pic1;
	}
	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}
	public String getPic2() {
		return pic2;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public String getPic3() {
		return pic3;
	}
	public void setPic3(String pic3) {
		this.pic3 = pic3;
	}
	public String getPic4() {
		return pic4;
	}
	public void setPic4(String pic4) {
		this.pic4 = pic4;
	}
	public String getPic5() {
		return pic5;
	}
	public void setPic5(String pic5) {
		this.pic5 = pic5;
	}
	public long getAd_id() {
		return ad_id;
	}
	public void setAd_id(long ad_id) {
		this.ad_id = ad_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public long getRealease_time() {
		return realease_time;
	}
	public void setRealease_time(long realease_time) {
		this.realease_time = realease_time;
	}
	public long getEnd_time() {
		return end_time;
	}
	public void setEnd_time(long end_time) {
		this.end_time = end_time;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
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
	
		
}


