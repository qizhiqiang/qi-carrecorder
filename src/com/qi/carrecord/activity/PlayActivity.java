package com.qi.carrecord.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.qi.carrecord.ExitApplication;
import com.qi.carrecord.R;
import com.qi.carrecord.adapter.MyPagerAdapter;
import com.qi.carrecord.dao.MediaDao;
import com.qi.carrecord.dao.MediaDaoImpl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class PlayActivity extends Activity {
    private Dialog MyDialog;
    private int bmpW;
    private int currIndex;
    private int currentPage;
    private ImageView cursor;
    private GridView gridview_tab1;
    private GridView gridview_tab2;
    private boolean istab;
    private List<View> listViews;
    private ViewPager mPager;
    private MediaDao mediadao;
    private int offset;
    private SimpleAdapter saImageItems1;
    private SimpleAdapter saImageItems2;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private ArrayList<HashMap<String, Object>> tab1source;
    private int tab2_currentPage;
    private ArrayList<HashMap<String, Object>> tab2source;
    private boolean tab_isadd;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ExitApplication.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.play);
        mediadao = new MediaDaoImpl(this);
        InitImageView();
        InitTextView();
        initTitle();
        InitViewPager();
        //new PlayActivity.InitVideoAsyncTask(this).execute(new String[] {""});
    }
    
    public void initTab1(ArrayList<HashMap<String, Object>> lstImageItem) {
        // :( Parsing error. Please contact me.
    }
    
    public void DiaLogShow(ArrayList<HashMap<String, Object>> id, HashMap<String, Object> index, ArrayList lstImageItem, HashMap item) {
        String[] items = getResources().getStringArray(0x7f090000);
        //localPlayActivity.41 = new AlertDialog.Builder(this).setTitle("\u8bf7\u9009\u62e9").setItems(items, new PlayActivity.4(this, id, item, lstImageItem, index));
        /*new DialogInterface.OnClickListener(this, id, item, lstImageItem, index) {
            
        	DialogInterface.OnClickListener(PlayActivity p1, int p2, HashMap p3, ArrayList p4, int p5) {
            }
            
            public void onClick(DialogInterface arg0, int arg1) {
                if(arg1 == 0) {
                    int count = mediadao.delete(id);
                    if(count > 0) {
                        try {
                            File file = new File(item.get("address").toString());
                            file.delete();
                        } catch(Exception localException1) {
                        }
                        lstImageItem.remove(index);
                        saImageItems1.notifyDataSetChanged();
                        return;
                    }
                    Toast.makeText(getApplicationContext(), 0x7f08001a, 0x0).show();
                    return;
                }
                count = mediadao.updateState(0x1, id);
                if(count > 0) {
                    lstImageItem.remove(index);
                    saImageItems1.notifyDataSetChanged();
                    tab2source.add(item);
                    saImageItems2.notifyDataSetChanged();
                    return;
                }
                Toast.makeText(getApplicationContext(), 0x7f08001b, 0x0).show();
            }
        }.show();*/
    }
    
    public void initTab2(ArrayList<HashMap<String, Object>> lstImageItem) {
        // :( Parsing error. Please contact me.
    }
    
    public void YongJiuDialog(HashMap<String, Object> id, int index, HashMap item) {
        String[] items = getResources().getStringArray(0x7f090001);
        //localAlertDialog.Builder1 = new AlertDialog.Builder(this).setTitle("\u8bf7\u9009\u62e9").setItems(items, new PlayActivity.8(this, id, index, item));
        //new AlertDialog.Builder(this).setTitle("\u8bf7\u9009\u62e9").show();
    }
    
    private void InitTextView() {
        t1 = (TextView)findViewById(R.id.text1);
        t2 = (TextView)findViewById(R.id.text2);
        t3 = (TextView)findViewById(R.id.text3);
        t1.setOnClickListener(new PlayActivity.MyOnClickListener(this, 0x0));
        t2.setOnClickListener(new PlayActivity.MyOnClickListener(this, 0x1));
        t3.setOnClickListener(new PlayActivity.MyOnClickListener(this, 0x2));
    }
    
    private void initTitle() {
        Button title_back = (Button)findViewById(R.id.title_back);
        title_back.setText(R.string.title_about);
        title_back.setOnClickListener(new View.OnClickListener() {
          
            public void onClick(View arg0) {
                finish();
                //overridePendingTransition(0x7f040006, 0x7f040007);
            }
        });
    }
    
    private void InitViewPager() {
        mPager = (ViewPager)findViewById(R.id.vPager);
        listViews = new ArrayList();
        LayoutInflater mInflater = getLayoutInflater();
        listViews.add(mInflater.inflate(R.layout.play_tab1, null));
        listViews.add(mInflater.inflate(R.layout.play_tab2, null));
        listViews.add(mInflater.inflate(R.layout.play_tab3, null));
        mPager.setAdapter(new MyPagerAdapter(listViews));
        mPager.setCurrentItem(0x0);
        mPager.setOnPageChangeListener(new PlayActivity.MyOnPageChangeListener(this));
        gridview_tab1 = (GridView)(View)listViews.get(0x0).findViewById(0x7f0c001c);
        gridview_tab2 = (GridView)(View)listViews.get(0x1).findViewById(0x7f0c001d);
    }
    
    public void deleteVideo(int id) {
        Dialog addDialog = new Dialog(this, 0x7f0a0005);
        addDialog.setContentView(0x7f030003);
        TextView title = (TextView)addDialog.findViewById(0x7f0c0009);
        TextView content = (TextView)addDialog.findViewById(0x7f0c000a);
        Button affirm = (Button)addDialog.findViewById(0x7f0c000b);
        Button exit = (Button)addDialog.findViewById(0x7f0c000c);
        title.setText("\u89c6\u9891\u5220\u9664");
        content.setText("\u4f60\u786e\u5b9a\u8981\u5220\u9664\u9009\u4e2d\u7684\u5f55\u50cf\u5417\uff1f");
        affirm.setText("\u786e\u5b9a");
        affirm.setOnClickListener(new View.OnClickListener() {
            
            public void onClick(View arg0) {
            }
        });
        /*exit.setOnClickListener(new View.OnClickListener(this, addDialog) {
            
            11(PlayActivity p1, Dialog p2) {
            }
            
            public void onClick(View arg0) {
                addDialog.dismiss();
            }
        });*/
        addDialog.show();
    }
    
    private void InitImageView() {
        cursor = (ImageView)findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.returns).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (((screenW / 0x3) - bmpW) / 0x2);
        Matrix matrix = new Matrix();
        matrix.postTranslate((float)offset, 0.0f);
        cursor.setImageMatrix(matrix);
    }
    
    public void finish() {
        super.finish();
        //overridePendingTransition(0x7f040006, 0x7f040007);
    }
    
    public void infraredStudent(boolean showDialog) {
        MyDialog = new Dialog(this, 0x7f0a0005);
        MyDialog.setContentView(0x7f030002);
        TextView study = (TextView)MyDialog.findViewById(0x7f0c0008);
        study.setText(0x7f080013);
        MyDialog.show();
        if(showDialog) {
            MyDialog.dismiss();
        }
    }
    
    public class MyOnClickListener implements View.OnClickListener{
    	private int index = 0;
    	
    	
    	MyOnClickListener(Context context,int id){
    		index = id;
    	}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mPager.setCurrentItem(index);
		}
    	
    }
    
    public class MyOnPageChangeListener implements android.support.v4.view.ViewPager.OnPageChangeListener{
    	private int one = 0;
    	private int two = 0;
    	private Context context;
    	
    	
    	MyOnPageChangeListener(Context cont){
    		context = cont;
    		one = offset * 2 + bmpW;
    		two = one * 2;
    	}


		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			//Animation animation;
			
		}


    	
    }
    
}
