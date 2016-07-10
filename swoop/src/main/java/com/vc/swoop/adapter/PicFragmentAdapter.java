package com.vc.swoop.adapter;

import java.util.ArrayList;
import java.util.List;

import com.vc.swoop.ImageDetailFragment;
import com.vc.swoop.PicFragment;
 
 
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PicFragmentAdapter  extends FragmentPagerAdapter {

//	protected static String[] urls = new String[]{};
	
	private List<String> urls = new ArrayList<String>();
	
	
	public PicFragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return PicFragment.newInstance(urls.get(position),urls,position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return urls.size();
	}  

	public void setImgs(List<String> urls) {
		this.urls = urls; 
	}
	
    
    
}
