package com.qi.carrecord.activity;

import java.util.ArrayList;

import com.qi.carrecord.ExitApplication;
import com.qi.carrecord.R;
import com.qi.carrecord.dao.SettingDao;
import com.qi.carrecord.dao.SettingDaoImpl;
import com.qi.carrecord.model.SettingModel;
import com.qi.carrecord.service.SdcardService;
import com.qi.carrecord.service.USBService;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class SettingActivity extends Activity implements View.OnClickListener {
    private TextView auto;
    private Dialog bottom_dialog;
    private TextView exit;
    private TextView fenduan;
    private TextView fenduan_text;
    private TextView luyin;
    private ImageButton luyin_icon;
    private TextView open;
    private ImageButton open_icon;
    private SeekBar seekBar;
    private SettingModel sett;
    private SettingDao settingdao;
    private Button title_returns;
    private TextView usb;
    private ImageButton usb_icon;
    private TextView title;
    private TextView resolution;
    private Spinner resolution_text;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApplication.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏，无状态栏		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏显示
        setContentView(R.layout.setting);
        settingdao = new SettingDaoImpl(getApplication());
        sett = settingdao.findAll();
        init();
        initSource();
    }
    
    public void init() {
        luyin = (TextView)findViewById(R.id.setting_luyin);
        fenduan = (TextView)findViewById(R.id.setting_fenduan);
        fenduan_text = (TextView)findViewById(R.id.setting_fenduan_text);
        open = (TextView)findViewById(R.id.setting_open);
        auto = (TextView)findViewById(R.id.setting_auto);
        exit = (TextView)findViewById(R.id.setting_exit);
        usb = (TextView)findViewById(R.id.setting_usb);
        luyin_icon = (ImageButton)findViewById(R.id.setting_luyinicon);
        open_icon = (ImageButton)findViewById(R.id.setting_open_icon);
        title_returns = (Button)findViewById(R.id.title_back);
        usb_icon = (ImageButton)findViewById(R.id.setting_usb_icon);
        resolution = (TextView)findViewById(R.id.setting_resolution);
        resolution_text = (Spinner)findViewById(R.id.setting_resolution_text);        
        luyin.setOnClickListener(this);
        fenduan.setOnClickListener(this);
        open.setOnClickListener(this);
        auto.setOnClickListener(this);
        exit.setOnClickListener(this);
        usb.setOnClickListener(this);       
        luyin_icon.setOnClickListener(this);
        open_icon.setOnClickListener(this);
        usb_icon.setOnClickListener(this);
        title_returns.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View arg0) {
                finish();
                //overridePendingTransition(0x7f040006, 0x7f040007);
            }
        });
        resolution_text.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
                long id = resolution_text.getSelectedItemId(); 
                if (id >=0 && id < resolution_text.getCount()){
                	int reso = (int)id;
                	SettingResolution(reso);
                }else{
                	SettingResolution(0);                	
                }
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
    }
    
    public void initSource() {
        if(sett == null) {
            return;
        }
        StringBuilder ss = new StringBuilder(String.valueOf(sett.getSplit_video()));
        ss.append("分钟");        
        fenduan_text.setText(ss.toString());
        if(sett.getRecording() == 0) {
            luyin_icon.setBackgroundResource(R.drawable.switch_close);
        } else {
            luyin_icon.setBackgroundResource(R.drawable.switch_open);
        }
        if(sett.getStart_video() == 0) {
            open_icon.setBackgroundResource(R.drawable.switch_close);
        } else {
            open_icon.setBackgroundResource(R.drawable.switch_open);
        }
        int pos = sett.getResolution_state();
        resolution_text.setSelection(pos, true);
        if(sett.getUsb_state() == 0) {
            usb_icon.setBackgroundResource(R.drawable.switch_close);
            if(isWorked()) {
                Intent close = new Intent(getApplicationContext(), USBService.class);
                stopService(close);
            }
            return;
        }
        usb_icon.setBackgroundResource(R.drawable.switch_open);
        if(!isWorked()) {
            Intent intent = new Intent(getApplicationContext(), USBService.class);
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            startService(intent);
        }
    }
    
    public void onClick(View arg0) {
        switch(arg0.getId()) {
            case R.id.setting_luyin:
            {
                OpenOrCloseRecord();
                return;
            }
            case R.id.setting_luyinicon:
            {
                OpenOrCloseRecord();
                return;
            }
            case R.id.setting_fenduan:
            {
                OpenBottomDialog();
                return;
            }
           
            case R.id.setting_open:
            {
                SettingOpen();
                return;
            }
            case R.id.setting_open_icon:
            {
                SettingOpen();
                return;
            }
            case R.id.setting_usb:
            {
                SettingUSB();
                return;
            }
            case R.id.setting_usb_icon:
            {
                SettingUSB();
                return;
            }
            case R.id.setting_auto:
            {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                //overridePendingTransition(0x7f040000, 0x7f040001);
                return;
            }
            case R.id.setting_exit:
            {
                if(isWorked()) {
                    Intent itnent = new Intent(getApplicationContext(), SdcardService.class);
                    stopService(itnent);
                }
                ExitApplication.getInstance().exit();
                return;
            }
            case R.id.bottom_men_exit:
            {
                if(bottom_dialog != null) {
                    bottom_dialog.dismiss();
                }
                return;
            }
            case R.id.bottom_men_affrim:
            {
                if(seekBar.getProgress() <= 0) {
                    Toast.makeText(getApplicationContext(), R.string.error_spit, 0x0).show();
                    return;
                }
                if(sett == null) {
                    Toast.makeText(getApplicationContext(), R.string.error_source, 0x0).show();
                    return;
                }
                sett.setSplit_video(seekBar.getProgress());
                int count = settingdao.update(sett);
                if(bottom_dialog != null) {
                    bottom_dialog.dismiss();
                }
                if(count > 0) {
                    StringBuilder ss = new StringBuilder(String.valueOf(sett.getSplit_video()));
                    ss.append("分钟");        
                    fenduan_text.setText(ss.toString());
                    return;
                }
                Toast.makeText(getApplicationContext(), R.string.nosetting, 0x0).show();
                break;
            }
        }
    }
    
    public void OpenOrCloseRecord() {
        if(sett == null) {
            add();
            OpenOrCloseRecord();
            return;
        }
        if(sett.getRecording() == 0) {
            sett.setRecording(0x1);
        } else {
            sett.setRecording(0x0);
        }
        int count = settingdao.update(sett);
        if(count > 0) {
            initSource();
        }
    }
    
    public void SettingUSB() {
        if(sett == null) {
            add();
            SettingUSB();
            return;
        }
        if(sett.getUsb_state() == 0x1) {
            sett.setUsb_state(0x0);
        } else {
            sett.setUsb_state(0x1);
        }
        int count = settingdao.update(sett);
        if(count > 0) {
            initSource();
        }
    }
    
    public void add() {
        sett = new SettingModel();
        sett.setRecording(0x0);
        sett.setSplit_video(0x5);
        sett.setStart_video(0x0);
        sett.setResolution_state(0x0);        
        int count = settingdao.add(sett);
        if(count > 0) {
            sett = settingdao.findAll();
        }
    }
    
    public void SettingOpen() {
        if(sett == null) {
            add();
            OpenOrCloseRecord();
            return;
        }
        if(sett.getStart_video() == 0) {
            sett.setStart_video(0x1);
        } else {
            sett.setStart_video(0x0);
        }
        int count = settingdao.update(sett);
        if(count > 0) {
            initSource();
        }
    }
    
    public void SettingResolution(int reso) {
        if(sett == null) {
            add();
            SettingResolution(0);
            return;
        }
        sett.setResolution_state(reso);
        int count = settingdao.update(sett);
        if(count > 0) {
            initSource();
        }
    }    
    
    public boolean isWorked() {
        ActivityManager myManager = (ActivityManager)getApplicationContext().getSystemService("activity");
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList)myManager.getRunningServices(0x1e);
        for(int i = 0; i < runningService.size(); i++) {
        	String temp = ((RunningServiceInfo)runningService.get(i)).service.getClassName().toString();
        	if (temp.equals("com.qi.carrecord.service.USBService")){
        		return true;
        	}else{
                continue;
        	}
        }

        return false;
    }
    
    public void finish() {
        super.finish();
        //overridePendingTransition(0x7f040006, 0x7f040007);
    }
    
    public void OpenBottomDialog() {
        bottom_dialog = new Dialog(this, 0);
        bottom_dialog.setContentView(R.layout.dialog_bottom);
        Window window = bottom_dialog.getWindow();
        window.setGravity(0x51);
        WindowManager.LayoutParams lp = window.getAttributes();
        seekBar = (SeekBar)bottom_dialog.findViewById(R.id.bottom_men_seekBar);
        title = (TextView)bottom_dialog.findViewById(R.id.bottom_men_title);
        Button exit = (Button)bottom_dialog.findViewById(R.id.bottom_men_exit);
        Button affrim = (Button)bottom_dialog.findViewById(R.id.bottom_men_affrim);
        LinearLayout lin = (LinearLayout)bottom_dialog.findViewById(R.id.bottom_men_top);
        exit.setOnClickListener(this);
        affrim.setOnClickListener(this);
        if(sett != null) {
            seekBar.setProgress(sett.getSplit_video());
            StringBuilder ss = new StringBuilder(String.valueOf(sett.getSplit_video()));
            ss.append("分钟");
            title.setText(ss.toString());
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar arg0) {
            }
            
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            	StringBuilder sbuild = new StringBuilder(String.valueOf(arg1));
            	sbuild.append("分钟");
            	title.setText(sbuild.toString());
            }
        });
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        lp.width = metric.widthPixels;
        window.setAttributes(lp);
        bottom_dialog.show();
        try {
            Thread.sleep(0xa);
            return;
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
