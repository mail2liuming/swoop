package com.vc.swoop.common;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SoftInputUtil {

	/***
	 * 开启软键盘
	 * @param context
	 */
	public static void openSoftInput(Context context){
		
		try {
			InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);    
			//得到InputMethodManager的实例  
			if (imm.isActive()) {  
			//如果开启  
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);   
			//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的  
			}  
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}
	
	/**
	 * 关闭软键盘
	 * @param context
	 * @param editText
	 */
	public static void closeSoftInput(Context context,EditText editText) {
		// TODO Auto-generated method stub
		try {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //强制隐藏键盘
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
