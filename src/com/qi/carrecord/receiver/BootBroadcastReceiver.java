package com.qi.carrecord.receiver;

import java.util.ArrayList;

import com.qi.carrecord.dao.SettingDao;
import com.qi.carrecord.dao.SettingDaoImpl;
import com.qi.carrecord.model.SettingModel;
import com.qi.carrecord.service.USBService;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {
	   private SettingModel sett;
	    private SettingDao settingdao;
	    
	    public void onReceive(Context arg0, Intent arg1) {
	        if(settingdao == null) {
	            settingdao = new SettingDaoImpl(arg0);
	        }
	        sett = settingdao.findAll();
	        if((sett == null) || (sett.getUsb_state() == 0)) {
	            return;
	        }
	        if(("android.intent.action.ACTION_POWER_DISCONNECTED".equals(arg1.getAction())) || ("android.intent.action.BOOT_COMPLETED".equals(arg1.getAction())) || ("android.intent.action.BOOT_COMPLETED".equals(arg1.getAction()))) {
	            if(sett.getUsb_state() == 0x1) {
	                if(!isWorked(arg0)) {
	                    startUploadService(arg0);
	                }
	            }
	        }
	    }
	    
	    private void startUploadService(Context arg0) {
	        Intent sevice = new Intent(arg0, USBService.class);
	        arg0.startService(sevice);
	    }
	    
	    public boolean isWorked(Context arg0) {
	        ActivityManager myManager = (ActivityManager)arg0.getSystemService("activity");
	        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList)myManager.getRunningServices(0x1e);
	        for(int i = 0; i < runningService.size(); i = i++) {
		        if(((ActivityManager.RunningServiceInfo)runningService.get(i)).service.getClassName().toString().equals("com.qi.carrecord.service.USBService")) {
		            return true;
		        }        	
	        }

            return false;
	    }

}
