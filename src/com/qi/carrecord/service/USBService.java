package com.qi.carrecord.service;

import com.qi.carrecord.receiver.USBReceiver;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class USBService extends Service {
    private static final String SYSTEMACTION = "android.intent.action.ACTION_POWER_CONNECTED";
    private static final String SYSTEMACTIONNO = "android.intent.action.ACTION_POWER_DISCONNECTED";
    
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
    public void onCreate() {
        super.onCreate();
        USBReceiver systemreceiver = new USBReceiver();
        IntentFilter filter_system = new IntentFilter();
        filter_system.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        registerReceiver(systemreceiver, filter_system);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        registerReceiver(systemreceiver, filter);
    }
}
