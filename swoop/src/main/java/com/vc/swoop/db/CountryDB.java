package com.vc.swoop.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.vc.swoop.model.Country;

 

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CountryDB {
	Context context;
	// DatabaseHelper mDbHelper = null;
	SQLiteDatabase mDb = null;
	// 数据库存储路径
	String filePath = "data/data/com.vc.swoop/language.db";
	String pathStr =  "data/data/com.vc.swoop";

	public SQLiteDatabase openDatabase(Context context) {
		System.out.println("filePath:" + filePath);
		File jhPath = new File(filePath);
		// 查看数据库文件是否存在
		if (jhPath.exists()) {
			// 存在则直接返回打开的数据库
			return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
		} else {
			// 不存在先创建文件夹
			File path = new File(pathStr);
			if (!path.exists()) {
				if (path.mkdir()) {
					System.out.println("创建成功");
				} else {
					System.out.println("创建失败");
				}
			}
			try {
				// 得到资源
				AssetManager am = context.getAssets();
				// 得到数据库的输入流
				InputStream is = am.open("country.db");
				// 用输出流写到SDcard上面
				FileOutputStream fos = new FileOutputStream(jhPath);
				// 创建byte数组 用于1KB写一次
				byte[] buffer = new byte[1024];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				// 最后关闭就可以了
				fos.flush();
				fos.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			// 如果没有这个数据库 我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
			return openDatabase(context);
		}
	}

	public CountryDB(Context c) {
		context = c;

		mDb = openDatabase(context);

	}
	
	public List<Country> select()
	{
		try {
			

				String sql;
				sql = "select id,name,chname,nickname,num,time from country  ";
				Cursor cursor = mDb.rawQuery(sql, new String[] {});
				List<Country> countries = new LinkedList<Country>();
				while (cursor.moveToNext()) {
					Country c = new Country();
					c.setId(cursor.getInt(0));
					c.setName(cursor.getString(1));
					c.setChname(cursor.getString(2));
					c.setNickname(cursor.getString(3));
					c.setNum(cursor.getString(4));
					c.setTime(cursor.getString(5));
					countries.add(c);
				}
				cursor.close();
				mDb.close();
				return countries;
		} catch (Exception e) {
			// TODO: handle exception
			if(mDb!=null)
				mDb.close();
			return null;
		}
	}

}

