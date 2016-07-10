package com.vc.swoop.common;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SoftInputUtil {

	/***
	 * ���������
	 * @param context
	 */
	public static void openSoftInput(Context context){
		
		try {
			InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);    
			//�õ�InputMethodManager��ʵ��  
			if (imm.isActive()) {  
			//�������  
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);   
			//�ر�����̣�����������ͬ������������л�������ر�״̬��  
			}  
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}
	
	/**
	 * �ر������
	 * @param context
	 * @param editText
	 */
	public static void closeSoftInput(Context context,EditText editText) {
		// TODO Auto-generated method stub
		try {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0); //ǿ�����ؼ���
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
