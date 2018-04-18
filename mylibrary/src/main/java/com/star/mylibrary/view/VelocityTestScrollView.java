package com.star.mylibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.ScrollView;

/**
 * Created by yinlianghe on 2018/4/16.
 */

public class VelocityTestScrollView extends ScrollView {
    private VelocityTracker mVelocityTracker;

    public VelocityTestScrollView(Context context) {
        this(context, null);
    }

    public VelocityTestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                break;
        }

        return super.onTouchEvent(ev);
    }
}
