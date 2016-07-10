package com.vc.swoop.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vc.swoop.common.App;
import com.vc.util.LogUtils;

public class ADCommentCountDAL {
	Context context;
	DatabaseHelper mDbHelper = null;
	SQLiteDatabase mDb = null; 
	public ADCommentCountDAL(Context c) {
		context = c;
		mDbHelper = DatabaseHelper.getInstance(c);
		mDb = mDbHelper.getReadableDatabase();
	}
	public String select(String key)
	{
		String result = "";
		try {
			synchronized (App.threadDBLock) {
				
				String sql = "select [Value] from ADCommentCount where Ad_id =?";
				Cursor cursor = mDb.rawQuery(sql,new String[]{key});
				if (cursor.moveToFirst()) {
					result=cursor.getString(0);
				}
				
				return result;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return "0";
		}
	}
	public boolean insert(String key,String val)
	{
		//boolean result = false;
		try {
			synchronized (App.threadDBLock) {
				
				String sql = "delete from ADCommentCount where Ad_id =?";
				try{
						mDb.execSQL(sql,new Object[]{key});
				} catch (Exception e) {
					// TODO: handle exception
					LogUtils.e(e.toString());
					return false;
				}
				sql = "insert into  ADCommentCount (Ad_id,[Value]) values (?,?)";
				try{
						mDb.execSQL(sql,new Object[]{key,val});
				} catch (Exception e) {
					// TODO: handle exception
					LogUtils.e(e.toString());
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogUtils.e(e.toString());
			return false;
		}
	}
}
