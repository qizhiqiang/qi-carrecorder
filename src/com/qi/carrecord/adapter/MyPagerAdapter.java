package com.qi.carrecord.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class MyPagerAdapter extends PagerAdapter {
    public List<View> mListViews;
    
    public MyPagerAdapter(List<View> ListViews) {
        mListViews = ListViews;
    }
    
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((android.support.v4.view.ViewPager)arg0).removeView((View)mListViews.get(arg1));
    }
    
    public void finishUpdate(View arg0) {
    }
    
    public int getCount() {
        return mListViews.size();
    }
    
    public Object instantiateItem(View arg0, int arg1) {
    	((android.support.v4.view.ViewPager)arg0).addView((View)mListViews.get(arg1), 0x0);
        return mListViews.get(arg1);
    }
    
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }
    
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }
    
    public Parcelable saveState() {
        return null;
    }
    
    public void startUpdate(View arg0) {
    }

}
