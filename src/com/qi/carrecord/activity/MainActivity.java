package com.qi.carrecord.activity;

import com.qi.carrecord.ExitApplication;
import com.qi.carrecord.R;
import com.qi.carrecord.dao.SettingDao;
import com.qi.carrecord.dao.SettingDaoImpl;
import com.qi.carrecord.db.RecordDB;
import com.qi.carrecord.model.SettingModel;
import com.qi.carrecord.service.SdcardService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements View.OnClickListener {
    public Animation animation1;
    public Animation animation2;
    public int count = 0;
    public Handler handler = new Handler();
    public ImageView imageView;
    public ImageView imageView2;
    public int[] images = {R.drawable.allapplist_background,R.drawable.allapplist_background};
    boolean isExit = false;
    public boolean juage = true;
    public boolean lock;
    Handler mHandler;
    private ImageButton play;
    public Runnable runnable;
    private ImageButton setting;
    private SettingDao settingdao;
    private Handler startVideo;
    private ImageButton video;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExitApplication.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main);
        runnable = new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				AnimationSet animationSet1 = new AnimationSet(true);
				AnimationSet animationSet2 = new AnimationSet(true);
				imageView2.setVisibility(View.VISIBLE);
				TranslateAnimation ta = new TranslateAnimation( Animation.RESTART,0,Animation.RESTART,-0x4080,Animation.RESTART,0,Animation.RESTART,0);
				ta.setDuration(0x3a98);
				animationSet1.addAnimation(ta);
				animationSet1.setFillAfter(true);
				TranslateAnimation ta1 = new TranslateAnimation(Animation.RESTART,0x3f80,Animation.RESTART,0,Animation.RESTART,0,Animation.RESTART,0);
				ta1.setDuration(0x3a98);
				animationSet2.addAnimation(ta1);
				animationSet2.setFillAfter(true);	
				
				//imageView.startAnimation(animationSet1);
				//imageView2.startAnimation(animationSet2);
				
				imageView.setBackgroundResource(images[count%2]);
				count = count + 1;
				
				imageView2.setBackgroundResource(images[count%2]);
				
				handler.postDelayed(runnable, 0x3a98);
				
				
			}
        	
        };
        
        mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				isExit = false;
			}
        	
        };
        
        startVideo = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				Intent intent = new Intent();
				intent.setClassName(MainActivity.this.getApplicationContext(),"com.qi.carrecord.activity.VideoActivity");
				Bundle bundle = new Bundle();
				bundle.putString("msg", "open");
				intent.putExtras(bundle);
				MainActivity.this.startActivity(intent);
			}
        	
        };        
        
        RecordDB recorddb = new RecordDB(this);
        SQLiteDatabase db = recorddb.getReadableDatabase();
        db.close();
        settingdao = new SettingDaoImpl(this);
        init();
        Intent intent = new Intent(getApplicationContext(), SdcardService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(intent);
        Tishi();
    }
    
    public void Tishi() {
        SharedPreferences sharedPreferences = getSharedPreferences("share", 0x0);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(isFirstRun) {
            Dialog();
            editor.putBoolean("isFirstRun", false);
            editor.commit();
        }
    }
    
    public void Dialog() {
        AlertDialog.Builder Builder1 = new AlertDialog.Builder(this);
        Builder1 = Builder1.setTitle("开发者提示");
        Builder1 = Builder1.setMessage("qizhiqiang");
        Builder1.setPositiveButton("确定", null).show();
    }
    
    protected void onResume() {
        super.onResume();
        lock = true;
    }
    
    public void onPause() {
        super.onPause();
    }
    
    public void init() {
        video = (ImageButton)findViewById(R.id.video);
        play = (ImageButton)findViewById(R.id.play);
        setting = (ImageButton)findViewById(R.id.setting);
        video.setOnClickListener(this);
        play.setOnClickListener(this);
        setting.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView2.setVisibility(View.INVISIBLE);
        handler.postDelayed(runnable, 0x0);
        try {
            if((getIntent().getExtras().getString("msg") != null) && (!"".equals(getIntent().getExtras().getString("msg").trim()))) {
                CheckRef();
                return;
            }
        } catch(Exception localException1) {
        }
    }
    
    public void onClick(View view) {
        if(!lock) {
            return;
        }
        if(view == video) {
            startActivity(new Intent(getApplication(), VideoActivity.class));
            //overridePendingTransition(0x7f04, 0x7f040001);
            return;
        }
        if(view == play) {
            startActivity(new Intent(getApplication(), PlayActivity.class));
            //overridePendingTransition(0x7f04, 0x7f040001);
            return;
        }
        if(view == setting) {
            startActivity(new Intent(getApplication(), SettingActivity.class));
            //overridePendingTransition(0x7f04, 0x7f040001);
        }
    }
    
    public void CheckRef() {
        SettingModel setting = settingdao.findAll();
        if(setting == null) {
            return;
        }
        if(setting.getStart_video() != 0) {
            lock = false;
            //MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), 0x7f050000);
            //mPlayer.setLooping(false);
            //mPlayer.start();
            startVideo.sendEmptyMessageDelayed(0x0, 0x3e8);
        }
    }
    
    public void exit() {
        if(!lock) {
            return;
        }
        if(!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "\u518d\u6309\u4e00\u6b21\u9000\u51fa\u7a0b\u5e8f", 0x0).show();
            mHandler.sendEmptyMessageDelayed(0x0, 0x7d0);
            return;
        }
        Intent itnent = new Intent(getApplicationContext(), SdcardService.class);
        stopService(itnent);
        ExitApplication.getInstance().exit();
        finish();
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode) {
            case KeyEvent.KEYCODE_BACK:
            {
                exit();
                break;
            }
        }
        return false;
    }
}
