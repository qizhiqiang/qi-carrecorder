package com.qi.carrecord.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class SlowScrollView extends ScrollView {
    public SlowScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public SlowScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public SlowScrollView(Context context) {
        super(context);
    }
    
    public void fling(int velocityY) {
        super.fling((velocityY / 0xa));
    }
}
