package com.qi.carrecord.dao;

import com.qi.carrecord.db.RecordDB;
import com.qi.carrecord.model.SettingModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SettingDaoImpl implements SettingDao {
    private SQLiteDatabase db;
    private RecordDB record;
    
    public SettingDaoImpl(Context context) {
        record = new RecordDB(context);
    }
    
    public SettingModel findAll() {
        db = record.getReadableDatabase();
        SettingModel setting = null;
        Cursor cursor = null;
        cursor = db.rawQuery("select * from T_B_SETTING", null);
        if((cursor != null) && (cursor.getCount() > 0)) {
            if(!cursor.moveToNext()) {
            }
            setting = new SettingModel();
            setting.setId(cursor.getInt(0x0));
            setting.setRecording(cursor.getInt(0x1));
            setting.setSplit_video(cursor.getInt(0x2));
            setting.setStart_video(cursor.getInt(0x3));
            setting.setUsb_state(cursor.getInt(0x4));
            cursor.close();
        }
        db.close();
        return setting;
    }
    
    public int update(SettingModel setting) {
        // :( Parsing error. Please contact me.
    	return 0;
    }
    
    public int add(SettingModel setting) {
        db = record.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("recording", Integer.valueOf(setting.getRecording()));
        values.put("split_video", Integer.valueOf(setting.getSplit_video()));
        values.put("start_video", Integer.valueOf(setting.getStart_video()));
        values.put("usb_state", Integer.valueOf(setting.getUsb_state()));
        int count = (int)db.insert("T_B_SETTING", null, values);
        db.close();
        return count;
    }
}
