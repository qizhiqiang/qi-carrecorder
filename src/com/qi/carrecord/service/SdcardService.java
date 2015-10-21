package com.qi.carrecord.service;

import java.io.File;
import java.util.List;

import com.qi.carrecord.dao.MediaDao;
import com.qi.carrecord.dao.MediaDaoImpl;
import com.qi.carrecord.model.MediaModel;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.StatFs;

public class SdcardService extends Service {

    private static final int SETINTERVAL = 0x2710;
    private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (null == mediaDaoImpl){
				mediaDaoImpl = new MediaDaoImpl(getApplicationContext());
			}
			double min = getSDAvailableSize();
			double size = getSDTotalSize();

			if (min <= 100){
				List<MediaModel> list = mediaDaoImpl.findByTem(0, 0);

				if(list.size()>1){
					mediaDaoImpl.delete(((MediaModel)list.get(0)).getId());
					try{
						String temp = String.valueOf(((MediaModel)list.get(0)).getVideo_address());
						StringBuilder sb = new StringBuilder(temp);
						String temp1 = String.valueOf(((MediaModel)list.get(0)).getName());
						sb.append(temp1);
						sb.append(".mp4");
						File file = new File(sb.toString());
						file.delete();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
	        mHandler.sendEmptyMessageDelayed(0x0, SETINTERVAL);
		}
    	
    };
    private MediaDaoImpl mediaDaoImpl;
    
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
