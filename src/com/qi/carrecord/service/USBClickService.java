package com.qi.carrecord.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.qi.carrecord.R;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class USBClickService extends Service {
    private Dialog addDialog;
    private TextView content;
    private MediaPlayer mPlayer;
    private int size;
    private TimerTask task;
    private Timer timer;
    private boolean usbstate;
    private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (null == addDialog){
				if (false == usbstate){
					CloseTimerProgressBar();
				}				
			}else{
				if (addDialog.isShowing()){
					if (false == usbstate){
						CloseTimerProgressBar();
					}
				}else{
					CloseTimerProgressBar();
				}
			}
			
			size = size -1;
			content.setText(new StringBuilder(String.valueOf(size)).append("\u79d2\u540e\u81ea\u52a8\u6253\u5f00\u884c\u8f66\u8bb0\u5f55\u4eea..").toString());
			if (size <= 0){
				Intent intent = new Intent("android.intent.action.MAIN");
				PackageManager pm = getPackageManager();
				intent = pm.getLaunchIntentForPackage("com.qi.carrecord");
				intent.addCategory("android.intent.category.LAUNCHER");
				USBClickService.this.startActivity(intent);
	            addDialog.dismiss();
				CloseTimerProgressBar();
			}
		}
    	
    };    
    
    public IBinder onBind(Intent arg0) {
        return null;
    }
    
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Bundle bundle = null;
        try {
            bundle = intent.getExtras();
        } catch(Exception e) {
        	e.printStackTrace();
            return;
        }

        String usb = bundle.getString("usb");
        Log.e("qizhiqiang", " "+usb);
        if(usb.equals("start")) {
            usbstate = true;
            boolean isok = isTopActivity();
            if(!isok) {
                Log.e("qizhiqiang", "isTopActivity");
                if((addDialog != null) && (addDialog.isShowing())) {
                    addDialog.dismiss();
                    addDialog = null;
                }
                addDialog = new Dialog(getApplicationContext(),0);
                addDialog.setContentView(R.layout.dialog);
                TextView title = (TextView)addDialog.findViewById(R.id.dialog_title);
                content = (TextView)addDialog.findViewById(R.id.dialog_content);
                Button affirm = (Button)addDialog.findViewById(R.id.btOK);
                Button exit = (Button)addDialog.findViewById(R.id.btCancel);
                title.setText(R.string.setting_usb);
                content.setText("\u79d2\u540e\u81ea\u52a8\u6253\u5f00\u884c\u8f66\u8bb0\u5f55\u4eea..");
                affirm.setText(R.string.menu_affrim);
                affirm.setOnClickListener(new View.OnClickListener() {
                    
                    public void onClick(View arg0) {
                        Intent i = new Intent("android.intent.action.MAIN");
                        PackageManager pm = getPackageManager();
                        i = pm.getLaunchIntentForPackage("com.qi.carrecord");
                        i.addCategory("android.intent.category.LAUNCHER");
                        startActivity(i);
                        addDialog.dismiss();
                        CloseTimerProgressBar();
                    }
                });
                exit.setOnClickListener(new View.OnClickListener() {
                    
                    public void onClick(View arg0) {
                        addDialog.dismiss();
                    }
                });
                addDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                addDialog.show();
                if(mPlayer == null) {
                    //mPlayer = MediaPlayer.create(getApplicationContext(), 0);
                    //mPlayer.setLooping(false);
                }
                //mPlayer.start();
                TimerClick();
            }
            return;
        }
        if(usb.equals("close")) {
            usbstate = false;
            if(isTopActivity()) {
                Intent intent_close = new Intent();
                intent_close.setAction("com.qi.carrecord.receiver.usbdisconnect");
                sendBroadcast(intent_close);
            }
        }
    }
    
    public void TimerClick() {
        if(timer == null) {
            timer = new Timer(true);
        }
        if(task == null) {
            task = new TimerTask() {
                
                public void run() {
                    Message msg = new Message();
                    handler.sendMessage(msg);
                }
            };
        }
        timer.schedule(task, 0x0, 0x3e8);
    }
    
    public void CloseTimerProgressBar() {
        size = 0xa;
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
        if(task != null) {
            task.cancel();
            task = null;
        }
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer = null;
        }
        if((addDialog != null) && (addDialog.isShowing())) {
            addDialog.dismiss();
            addDialog = null;
        }
    }
    
    protected boolean isTopActivity() {
        String packageName = "com.qi.carrecord";
        ActivityManager activityManager = (ActivityManager)getSystemService("activity");
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(0x1);
        if (tasksInfo.size()==0){
        	return false;
        }
        if(packageName.equals(((ActivityManager.RunningTaskInfo)tasksInfo.get(0x0)).topActivity.getPackageName())) {
            return true;
        }        

        return false;
    }

}
