package com.qi.carrecord.service;

import java.io.File;

import com.qi.carrecord.dao.MediaDao;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.StatFs;

public class SdcardService extends Service {

    private static final int SETINTERVAL = 0x2710;
    private Handler mHandler = new Handler();
    private MediaDao mediaDaoImpl;
    
    public IBinder onBind(Intent arg0) {
        return null;
    }
    
    public void onCreate() {
        mHandler.sendEmptyMessageDelayed(0x0, SETINTERVAL);
        super.onCreate();
    }
    
    private double getSDTotalSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = (long)stat.getBlockSize();
        long totalBlocks = (long)stat.getBlockCount();
        return (((double)(blockSize * totalBlocks) / 1024.0) / 1024.0);
    }
    
    private double getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        int blockSize = stat.getBlockSize();
        int availableBlocks = stat.getAvailableBlocks();
        return ((((double)blockSize * (double)availableBlocks) / 1024.0) / 1024.0);
    }

}
