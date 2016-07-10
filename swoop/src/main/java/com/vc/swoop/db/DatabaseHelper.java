package com.vc.swoop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "calder";
	public static final int DATABASE_VERSION = 1;

	private static final String AppConfig_CREATE = "CREATE TABLE [AppConfig] ([KeyName] NVARCHAR2, [Value] NVARCHAR2);"; 
	private static final String ADCommentCount_CREATE = "CREATE TABLE [ADCommentCount] ([Ad_id] NVARCHAR2, [Value] NVARCHAR2);"; 
	private static DatabaseHelper instance;

	public synchronized static DatabaseHelper getInstance(Context c) {
		if (null == instance) {
			instance = new DatabaseHelper(c);
		}
		return instance;
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DatabaseHelper(Context c) {
		// TODO Auto-generated constructor stub
		super(c, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL(AppConfig_CREATE); 
		db.execSQL(ADCommentCount_CREATE); 
		init(db);
	}
	private void init(SQLiteDatabase db)
	{
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 

	}

}