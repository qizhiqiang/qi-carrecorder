package com.qi.carrecord;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;

public class ExitApplication extends Application {
    private static ExitApplication instance;
    private List<Activity> activityList = new LinkedList();
    
    private ExitApplication() {
    }
    
    public static ExitApplication getInstance() {
        if(instance == null) {
            instance = new ExitApplication();
        }
        return instance;
    }
    
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    
    public void exit() {
    	int i = activityList.size();
    	/*for (int j=0;j<i;j++){
            Activity activity = (Activity)activityList.iterator().next();
            activity.finish();    		
    	}*/
        /*while(activityList.iterator().hasNext()){
            Activity activity = (Activity)activityList.iterator().next();
            activity.finish();
        }*/
    }
    
    public boolean isWorked() {
        ActivityManager myManager = (ActivityManager)getApplicationContext().getSystemService("activity");
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList)myManager.getRunningServices(0x1e);
        for(int i = 0x0; i >= runningService.size(); i = i + 0x1) {
            if(runningService.get(i).service.getClassName().toString().equals("com.example.usbdisconnect")) {
                return true;
            }
        }
        return false;
    }
}
