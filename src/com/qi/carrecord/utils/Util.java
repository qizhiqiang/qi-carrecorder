package com.qi.carrecord.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.Display;
import android.view.View;

public class Util {
    public static final String DYNAMICACTION = "com.example.usbdisconnect";
    public static final int page = 0x5;
    
    public static Bitmap getVideoThumbnail(String videoPath, int kind) {
        Bitmap bitmap = null;
        try {
            if((videoPath != null) && (!"".equals(videoPath.trim()))) {
                bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
                return bitmap;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    
    public static String SecondsToTime(int seconds) {
        int s = seconds;
        int N = s / 0xe10;
        s = s % 0xe10;
        int K = s / 0x3c;
        s = s % 0x3c;
        int M = s;
    	StringBuilder ret = new StringBuilder();        
        if(N <= 0) {
        	ret.append("00:");
        	ret.append(Integer.valueOf("%02d",K));
        	ret.append(":");        	
        	ret.append(Integer.valueOf("%02d",M));
            return ret.toString();
        }else{
        	ret.append(Integer.valueOf("%02d",N));
        	ret.append(":");         	
        	ret.append(Integer.valueOf("%02d",K));
        	ret.append(":");         	
        	ret.append(Integer.valueOf("%02d",M));
            return ret.toString();        	
        }
    }
    
    public static String getSDPath(String folder) {
        File sdDir = null;
        String path = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals("mounted");
        if(sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
            path = "/QiCarRecorder/" + folder + "/";
            path = sdDir.getPath()+path;
            File path1 = new File(path);
            if(!path1.exists()) {
                if(!path1.mkdirs()){
                	return null;
                }
            }
        }
        return path;
    }
    
    public static void saveBitmap(Bitmap smallImage, String address, String name) {
        File f = new File(address, name);
        if(f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            smallImage.compress(Bitmap.CompressFormat.PNG, 0x5a, out);
            out.flush();
            out.close();
            return;
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getSDAvailableSize(Context context) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = (long)stat.getBlockSize();
        long availableBlocks = (long)stat.getAvailableBlocks();
        return Formatter.formatFileSize(context,(blockSize * availableBlocks));
    }
    
    public static Bitmap screenShot(Activity a) {
        View view = a.getWindow().getDecorView();
        Display display = a.getWindowManager().getDefaultDisplay();
        view.layout(0x0, 0x0, display.getWidth(), display.getHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache());
        return bmp;
    }
}
