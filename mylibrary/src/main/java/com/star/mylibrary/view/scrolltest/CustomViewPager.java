package com.star.mylibrary.view.scrolltest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by yinlianghe on 2018/4/17.
 */

public class CustomViewPager extends ViewGroup {
    private int mLastX;
    private Scroller mScroller;

    public CustomViewPager(Context context) {
        this(context, null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).layout(i * getWidth(), t, (i + 1) * getWidth(), b);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = mLastX - x;
                int oldScrollX = getScrollX();
                int preScrollX = oldScrollX + dx;
                if (preScrollX > (getChildCount() - 1) * getWidth()) {
                    preScrollX = (getChildCount() - 1) * getWidth();// 最大滑动距离
                }
                if (preScrollX < 0) {
                    preScrollX = 0;
                }
                if (preScrollX == 0 || preScrollX == (getChildCount() - 1) * getWidth()) {// 滑动边界
                    scrollTo(preScrollX, getScrollY());
                } else {// 中间部分
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, 0, 2000);
                    invalidate();
                }
                mLastX = x;
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
        super.computeScroll();
    }
}
