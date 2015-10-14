package com.qi.carrecord.dao;

import java.util.ArrayList;
import java.util.List;

import com.qi.carrecord.db.RecordDB;
import com.qi.carrecord.model.MediaModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MediaDaoImpl implements MediaDao {
    private SQLiteDatabase db;
    private RecordDB record;
    
    public MediaDaoImpl(Context context) {
        record = new RecordDB(context);
    }
    
    public int add(MediaModel media) {
        db = record.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", media.getName());
        values.put("begintime", media.getBegintime());
        values.put("video_length", Integer.valueOf(media.getVideo_length()));
        values.put("video_address", media.getVideo_address());
        int count = (int)db.insert("T_B_MEDIA", null, values);
        db.close();
        return count;
    }
    
    public int delete(int id) {
        db = record.getReadableDatabase();
        int count = db.delete("T_B_MEDIA", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return count;
    }
    
    public List findAll() {
        db = record.getReadableDatabase();
        ArrayList<MediaModel> list = new ArrayList<MediaModel>();
        Cursor cursor = null;
        cursor = db.rawQuery("select * from  T_B_MEDIA order by id desc", null);
        if((cursor != null) && (cursor.getCount() > 0)) {
            if(!cursor.moveToNext()) {
            }
            MediaModel media = new MediaModel();
            media.setId(cursor.getInt(0x0));
            media.setName(cursor.getString(0x1));
            media.setBegintime(cursor.getString(0x2));
            media.setEndtime(cursor.getString(0x3));
            media.setVideo_length(cursor.getInt(0x4));
            media.setVideo_address(cursor.getString(0x5));
            media.setType(cursor.getInt(0x6));
            media.setSmallimage(cursor.getString(0x7));
            list.add(media);
        }
        db.close();
        return list;
    }
    
    public MediaModel findById(int id) {
        db = record.getReadableDatabase();
        MediaModel media = null;
        Cursor cursor = null;
        cursor = db.rawQuery("select * from  T_B_MEDIA where id = ?", new String[] {String.valueOf(id)});
        if((cursor != null) && (cursor.getCount() > 0)) {
            if(!cursor.moveToNext()) {
            }
            media = new MediaModel();
            media.setId(cursor.getInt(0x0));
            media.setName(cursor.getString(0x1));
            media.setBegintime(cursor.getString(0x2));
            media.setEndtime(cursor.getString(0x3));
            media.setVideo_length(cursor.getInt(0x4));
            media.setVideo_address(cursor.getString(0x5));
            media.setType(cursor.getInt(0x6));
            media.setSmallimage(cursor.getString(0x7));
        }
        db.close();
        return media;
    }
    
    public int update(MediaModel media) {
        db = record.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", media.getName());
        values.put("video_length", Integer.valueOf(media.getVideo_length()));
        values.put("endtime", media.getEndtime());
        values.put("smallimage", media.getSmallimage());
        int count = db.update("T_B_MEDIA", values, "id = ?", new String[] {String.valueOf(media.getId())});
        db.close();
        return count;
    }
    
    public MediaModel findByName(String name) {
        db = record.getReadableDatabase();
        MediaModel media = null;
        Cursor cursor = null;
        cursor = db.rawQuery("select * from  T_B_MEDIA where name= ?", new String[] {String.valueOf(name)});
        if((cursor != null) && (cursor.getCount() > 0)) {
            if(!cursor.moveToNext()) {
            }
            media = new MediaModel();
            media.setId(cursor.getInt(0x0));
            media.setName(cursor.getString(0x1));
            media.setBegintime(cursor.getString(0x2));
            media.setEndtime(cursor.getString(0x3));
            media.setVideo_length(cursor.getInt(0x4));
            media.setVideo_address(cursor.getString(0x5));
            media.setType(cursor.getInt(0x6));
            media.setSmallimage(cursor.getString(0x7));
        }
        db.close();
        return media;
    }
    
    public List<MediaModel> findByTem(int type, int currentPage) {
        db = record.getReadableDatabase();
        ArrayList<MediaModel> list = new ArrayList<MediaModel>();
        String sql = type + " order by id desc,id limit " + currentPage + "," + 0x5;
        Cursor cursor = null;
        cursor = db.rawQuery("select * from T_B_MEDIA where type = ", null);
        if((cursor != null) && (cursor.getCount() > 0)) {
            if(!cursor.moveToNext()) {
            }
            MediaModel media = new MediaModel();
            media.setId(cursor.getInt(0x0));
            media.setName(cursor.getString(0x1));
            media.setBegintime(cursor.getString(0x2));
            media.setEndtime(cursor.getString(0x3));
            media.setVideo_length(cursor.getInt(0x4));
            media.setVideo_address(cursor.getString(0x5));
            media.setType(cursor.getInt(0x6));
            media.setSmallimage(cursor.getString(0x7));
            list.add(media);
        }
        db.close();
        return list;
    }
    
    public int updateState(int state, int id) {
        db = record.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", Integer.valueOf(state));
        int count = db.update("T_B_MEDIA", values, "id = ?", new String[] {String.valueOf(id)});
        db.close();
        return count;
    }
}

