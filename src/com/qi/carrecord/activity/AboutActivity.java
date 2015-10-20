package com.qi.carrecord.activity;

import com.qi.carrecord.ExitApplication;
import com.qi.carrecord.R;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;

public class AboutActivity extends Activity implements View.OnClickListener {
    private Button btn_fankui;
    private Button btn_update;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApplication.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏，无状态栏		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏显示
        setContentView(R.layout.about);
        initTitle();
    }
    
    private void initTitle() {
        btn_update = (Button)findViewById(R.id.update);
        btn_fankui = (Button)findViewById(R.id.fankui);
        Button title_back = (Button)findViewById(R.id.title_back);
        title_back.setText(R.string.title_about);
        btn_update.setOnClickListener(this);
        btn_fankui.setOnClickListener(this);
        title_back.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View arg0) {
                finish();
            }
        });
    }
    
    public void finish() {
        super.finish();
        //overridePendingTransition(0x7f040006, 0x7f040007);
    }
    
    public void onClick(View arg0) {
        switch(arg0.getId()) {
            case R.id.update:
            {
                Toast.makeText(getApplicationContext(), "\u7814\u53d1\u4e2d\uff0c\u656c\u8bf7\u671f\u5f85!", 0x0).show();
                return;
            }
            case R.id.fankui:
            {
                Toast.makeText(getApplicationContext(), "\u7814\u53d1\u4e2d\uff0c\u656c\u8bf7\u671f\u5f85!", 0x0).show();
                break;
            }
        }
    }
}
