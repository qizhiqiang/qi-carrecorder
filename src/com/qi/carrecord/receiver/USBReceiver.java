package com.qi.carrecord.receiver;

import com.qi.carrecord.service.USBClickService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class USBReceiver extends BroadcastReceiver {

	@Override
    public void onReceive(Context context, Intent arg1) {
        Intent intent = new Intent(context, USBClickService.class);
        Bundle b = new Bundle();
        if(arg1.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
            b.putString("usb", "close");
        } else if(arg1.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED")) {
            b.putString("usb", "start");
        }
        intent.putExtras(b);
        Log.e("qizhiqiang", "startservice clickservice");
        context.startService(intent);
    }

}
