package com.qi.carrecord.activity;

import com.qi.carrecord.ExitApplication;
import com.qi.carrecord.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class StartActivity extends Activity {
    private static final int GO_HOME = 0x3e8;
    private static final long SPLASH_DELAY_MILLIS = 0xbb8L;
    private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case 0:
				goHome();
				break;
			default:
				break;
			}
		}
    	
    };
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.start);
        mHandler.sendEmptyMessageDelayed(0x0, SPLASH_DELAY_MILLIS);
    }
    
    private void goHome() {
        Intent intent = new Intent();
        intent.setClassName(getApplicationContext(), "com.qi.carrecord.activity.MainActivity");
        Bundle bundle = new Bundle();
        bundle.putString("msg", "open");
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        //overridePendingTransition(0x7f040000, 0x7f040001);
    }
}
