package com.vc.swoop.common;

import java.text.DecimalFormat;

public class TextU {

	
	/** 
	  * 判断字符串是否是整数 
	  */
	public static boolean isInteger(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	     } catch (NumberFormatException e) {  
	         return false;  
	     }  
	 }  
	  
	 /** 
	  * 判断字符串是否是浮点�?
	  */  
	 public static boolean isDouble(String value) {  
	     try {  
	         Double.parseDouble(value);  
	         if (value.contains("."))  
	             return true;  
	         return false;  
	     } catch (NumberFormatException e) {  
	         return false;  
	     }  
	 }  
	  public static String ShowPrice(double d)
	  {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(d);
	  }
	 /** 
	  * 判断字符串是否是数字 
	  */  
	 public static boolean isNumber(String value) {  
	     return isInteger(value) || isDouble(value);  
	 }    
	 
	 
}
