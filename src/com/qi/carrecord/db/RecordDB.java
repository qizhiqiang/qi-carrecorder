package com.qi.carrecord.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecordDB extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_MEDIA = "create table T_B_MEDIA(id integer primary key autoincrement,name varchar(100),begintime string,endtime string,video_length,video_address varchar(200),type integer default 0,smallimage varchar(200))";
    private static final String CREATE_TABLE_SETTING = "create table T_B_SETTING(id integer primary key autoincrement,recording int default 1,split_video integer default 5,start_video int default 0,usb_state integer default 0,resolution_state integer default 0)";
    private static final String name = "RecordDB";
    private static final int version = 0x1;
    
    public RecordDB(Context context) {
        super(context, name, null, version);
    }
    
    public RecordDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table T_B_SETTING(id integer primary key autoincrement,recording int default 1,split_video integer default 5,start_video int default 0,usb_state integer default 0,resolution_state integer default 0)");
        db.execSQL("create table T_B_MEDIA(id integer primary key autoincrement,name varchar(100),begintime string,endtime string,video_length,video_address varchar(200),type integer default 0,smallimage varchar(200))");
        init(db);
    }
    
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }
    
    public void init(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("recording", Integer.valueOf(0x1));
        long c = db.insert("T_B_SETTING", null, values);
        if(c > 0x0) {
            System.out.println("\u65b0\u589e\u6210\u529f");
        }
    }
}
