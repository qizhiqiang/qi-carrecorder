package com.qi.carrecord.activity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.qi.carrecord.CameraManager;
import com.qi.carrecord.ExitApplication;
import com.qi.carrecord.R;
import com.qi.carrecord.R.drawable;
import com.qi.carrecord.R.id;
import com.qi.carrecord.R.layout;
import com.qi.carrecord.R.raw;
import com.qi.carrecord.dao.MediaDao;
import com.qi.carrecord.dao.MediaDaoImpl;
import com.qi.carrecord.dao.SettingDao;
import com.qi.carrecord.dao.SettingDaoImpl;
import com.qi.carrecord.model.MediaModel;
import com.qi.carrecord.model.SettingModel;
import com.qi.carrecord.utils.Util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class VideoActivity extends Activity implements GestureDetector.OnGestureListener, SurfaceHolder.Callback, View.OnClickListener, View.OnTouchListener{
    private boolean isStart;
    private SurfaceHolder mSurfaceHolder;
    private MediaRecorder mMediaRecorder;  
    private ProgressBar pro;    
    private GestureDetector mGestureDetector;  
    private ProgressBar battery;    
    private ImageButton btn_video;
    private TextView currenttime;
    private TextView dianliang; 
    private ImageView img;    
    private SoundPool sp;
    private HashMap<Integer, Integer> spMap;    
    private boolean state;   
    private TimerTask task;
    private TimerTask taskTime;
    private Timer timer;
    private Timer timerTime;  
    private Handler handler;
    private Handler handlerTime;    
    private String address;   
    private String name;    
    private AlphaAnimation alphaAnimation1;
    private MediaModel media;
    private MediaDao mediadao;  
    private int minth;   
    private SettingDao settingdao;    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        //ExitApplication.getInstance().addActivity(this);		
		requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏，无状态栏		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//屏幕常亮
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏显示
        //getWindow().setFormat(PixelFormat.TRANSLUCENT); 
		setContentView(R.layout.video);
        CameraManager.init(getApplication());
        InitSound();
        init();        
	}
	
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
        CloseCamera();		
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        if((isStart) && (!state)) {
            OpenCamera();
        }
        if(state) {
            OpenTimerProgressBar();
        }		
	}



	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.btn_video:
            if(state) {
                stopRecord();
                return;
            }
            startRecord();			
			break;
			
		default:
			break;
		}
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
        OpenCamera();
        isStart = true;
        InitSource();		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		stopRecord();
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
    public void OpenCamera() {
        try {
            CameraManager.get().openDriver(mSurfaceHolder);
            CameraManager.get().startPreview();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void CloseCamera() {
        CameraManager.get().stopPreview();
        CameraManager.get().closeDriver();
    }	
    
    private void initMediaRecorder() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        mMediaRecorder.setOutputFile(new StringBuilder(String.valueOf(address)).append(name).append(".mp4").toString());
    }    
	
    public void InitSource() {
        settingdao = new SettingDaoImpl(this);
        SettingModel setting = settingdao.findAll();
        if(setting != null) {
            if(setting.getSplit_video() > 0) {
                minth = (setting.getSplit_video() * 0x3c);
            }
            try {
                String msg = getIntent().getExtras().getString("msg");
                if((msg != null) && (setting.getStart_video() != 0)) {
                    //start_video.sendEmptyMessageDelayed(0x1, 0x7d0);
                    return;
                }
            } catch(Exception localException1) {
            }
            
        }
    }
    
    @SuppressWarnings("deprecation")
	public void init() {
        pro = (ProgressBar)findViewById(R.id.progress);
        SurfaceView mSurfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        mGestureDetector = new GestureDetector(this);
        mSurfaceView.setOnTouchListener(this);
        mSurfaceView.setLongClickable(true);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        btn_video = (ImageButton)findViewById(R.id.btn_video);
        battery = (ProgressBar)findViewById(R.id.battery);
        currenttime = (TextView)findViewById(R.id.currenttime);
        btn_video.setOnClickListener(this);
        dianliang = (TextView)findViewById(R.id.dianliang);
        mediadao = new MediaDaoImpl(this);
        img = (ImageView)findViewById(R.id.video_icon);
        try {
            if((getIntent().getExtras().getString("msg") != null) && (!"".equals(getIntent().getExtras().getString("msg").trim()))) {
                startRecord();
                return;
            }
        } catch(Exception localException1) {
        }
    }
    
    public void InitSound() {
        sp = new SoundPool(0x5, 0x3, 0x0);
        spMap = new HashMap();
        spMap.put(Integer.valueOf(0x1), Integer.valueOf(sp.load(this, R.raw.start, 0x1)));
        spMap.put(Integer.valueOf(0x2), Integer.valueOf(sp.load(this, R.raw.stop, 0x1)));
        //spMap.put(Integer.valueOf(0x3), Integer.valueOf(sp.load(this, 0x7f050004, 0x1)));
    } 
    
    public void playSound(int sound, int number) {
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        float audioMaxVolumn = (float)am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volumnCurrent = (float)am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = volumnCurrent / audioMaxVolumn;
        sp.play((Integer)spMap.get(Integer.valueOf(sound)).intValue(), volumnRatio, volumnRatio, 0x1, number, 1.0f);
    }    
    
    public void startRecord() {
        if(state) {
            return;
        }
        address = Util.getSDPath("Video");
        if (null != address){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Date curDate = new Date(System.currentTimeMillis());
            name = formatter.format(curDate);        	
        }else{
        	Toast.makeText(getApplication(), "\u60a8\u7684\u624b\u673a\u4e0a\u4e0d\u5b58\u5728SD\u5361\uff0c\u4e0d\u80fd\u8fdb\u884c\u5f55\u50cf", Toast.LENGTH_SHORT).show();
            return;
        }
        
        CloseCamera();
        initMediaRecorder();
        
        try{
        	mMediaRecorder.prepare(); 
            mMediaRecorder.start();
            playSound(0x1, 0x0);
            state = true;
            btn_video.setBackgroundResource(R.drawable.xc_video_stop_bg);
            iconStart();
            OpenTimerProgressBar();
            int num = AddMedia();
            if (num > 0){
            	media.setId(num); 
            }
        }catch(IOException e){
        	Log.e("qi", e.getMessage());
        }

    }
    
    public void stopRecord() {
        if(!state) {
            return;
        }
        CloseTimerProgressBar();
        if(mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
            playSound(0x2, 0x0);
            updateMedia();
            OpenCamera();
            state = false;
            img.clearAnimation();
            btn_video.setBackgroundResource(R.drawable.xc_video_start_bg);
        }
    }  
    
    public void OpenTimerProgressBar() {
        /*if(timer == null) {
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
        if((timer != null) && (task != null) && (pro.getProgress() == 0x64)) {
            timer.schedule(task, 0x0, (long)(minth * 0xa));
        }*/
    }
    
    public void CloseTimerProgressBar() {
        /*if(timer != null) {
            timer.cancel();
            timer = null;
        }
        if(task != null) {
            task.cancel();
            task = null;
        }
        pro.setProgress(0x64);*/
    } 
    
    public void updateMedia() {
        if(media != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String endtime = formatter.format(curDate);
            Date date = null;
            try {
                date = formatter.parse(media.getBegintime());
            } catch(ParseException e) {
                e.printStackTrace();
            }
            media.setEndtime(endtime);
            media.setVideo_length(((int)(curDate.getTime() - date.getTime()) / 0x3e8));
            mediadao.update(media);
        }
    }   
    
    public void iconStart() {
        alphaAnimation1 = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation1.setDuration(0x1f4);
        alphaAnimation1.setRepeatCount(Animation.INFINITE);
        alphaAnimation1.setRepeatMode(Animation.REVERSE);
        img.setAnimation(alphaAnimation1);
        alphaAnimation1.start();
    }  
    
    public int AddMedia() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String begintime = formatter.format(curDate);
        media = new MediaModel();
        media.setName(name);
        media.setVideo_address(address);
        media.setVideo_length(0x0);
        media.setBegintime(begintime);
        int count = mediadao.add(media);
        return count;
    }    
    
}
