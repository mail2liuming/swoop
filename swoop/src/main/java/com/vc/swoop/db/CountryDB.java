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
	// ���ݿ�洢·��
	String filePath = "data/data/com.vc.swoop/language.db";
	String pathStr =  "data/data/com.vc.swoop";

	public SQLiteDatabase openDatabase(Context context) {
		System.out.println("filePath:" + filePath);
		File jhPath = new File(filePath);
		// �鿴���ݿ��ļ��Ƿ����
		if (jhPath.exists()) {
			// ������ֱ�ӷ��ش򿪵����ݿ�
			return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
		} else {
			// �������ȴ����ļ���
			File path = new File(pathStr);
			if (!path.exists()) {
				if (path.mkdir()) {
					System.out.println("�����ɹ�");
				} else {
					System.out.println("����ʧ��");
				}
			}
			try {
				// �õ���Դ
				AssetManager am = context.getAssets();
				// �õ����ݿ��������
				InputStream is = am.open("country.db");
				// �������д��SDcard����
				FileOutputStream fos = new FileOutputStream(jhPath);
				// ����byte���� ����1KBдһ��
				byte[] buffer = new byte[1024];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				// ���رվͿ�����
				fos.flush();
				fos.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			// ���û��������ݿ� �����Ѿ�����д��SD�����ˣ�Ȼ����ִ��һ��������� �Ϳ��Է������ݿ���
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

