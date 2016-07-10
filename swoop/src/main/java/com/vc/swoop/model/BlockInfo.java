package com.vc.swoop.model;
 

public class BlockInfo {
	long ad_id;
	long ad_user_id;
	long other_user_id;
	
	public BlockInfo() {
		
	}
	public BlockInfo(long ad_id, long ad_user_id, long other_user_id) {
		super();
		this.ad_id = ad_id;
		this.ad_user_id = ad_user_id;
		this.other_user_id = other_user_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (ad_id ^ (ad_id >>> 32));
		result = prime * result + (int) (ad_user_id ^ (ad_user_id >>> 32));
		result = prime * result
				+ (int) (other_user_id ^ (other_user_id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlockInfo other = (BlockInfo) obj;
		if (ad_id != other.ad_id)
			return false;
		if (ad_user_id != other.ad_user_id)
			return false;
		if (other_user_id != other.other_user_id)
			return false;
		return true;
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

	
	
	
}