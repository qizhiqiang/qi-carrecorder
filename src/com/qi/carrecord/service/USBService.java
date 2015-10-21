package com.qi.carrecord.service;

import com.qi.carrecord.receiver.USBReceiver;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class USBService extends Service {
    private static final String SYSTEMACTION = "android.intent.action.ACTION_POWER_CONNECTED";
    private static final String SYSTEMACTIONNO = "android.intent.action.ACTION_POWER_DISCONNECTED";
    USBReceiver systemreceiver;
    
    
    
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
    @Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(systemreceiver);
        Log.e("qizhiqiang","stop USBService");		
	}


	public void onCreate() {
        super.onCreate();
        Log.e("qizhiqiang","start USBService");
        systemreceiver = new USBReceiver();
        IntentFilter filter_system = new IntentFilter();
        filter_system.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        registerReceiver(systemreceiver, filter_system);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        registerReceiver(systemreceiver, filter);
    }
}
