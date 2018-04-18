package com.star.mylibrary.view.scrolltest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by yinlianghe on 2018/4/17.
 */

public class CustomViewPage2 extends ViewGroup {
    private int mLastX;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaxVelocity;
    /**
     * 切页的时候，当前是第几页
     */
    private int mCurrenPage = 0;

    public CustomViewPage2(Context context) {
        this(context, null);
    }

    public CustomViewPage2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        ViewConfiguration cfg = ViewConfiguration.get(getContext());
        mTouchSlop = cfg.getScaledPagingTouchSlop();
        mMaxVelocity = cfg.getScaledMinimumFlingVelocity();
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.layout(i * getWidth(), t, (i + 1) * getWidth(), b);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        initVelocityTrackerIfNotExist();
        mVelocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastX = x;
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = mLastX - x;
                scrollBy(dx, 0);
                mLastX = x;
                break;

            case MotionEvent.ACTION_UP:
                // 是否大于最大速度
                mVelocityTracker.computeCurrentVelocity(1000);
                int initVelocity = (int) mVelocityTracker.getXVelocity();
                if (initVelocity > mMaxVelocity && mCurrenPage > 0) {
                    // 向右滑动
                    scrollToPage(mCurrenPage - 1);
                } else if (initVelocity < -mMaxVelocity && mCurrenPage < (getChildCount() - 1)) {
                    // 向左滑动
                    scrollToPage(mCurrenPage + 1);
                } else {
                    slowScrollToPage();
                }
                recycleVelocityTracker();
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

    /**
     * 滑动到指定屏幕
     *
     * @param indexPage
     */
    private void scrollToPage(int indexPage) {
        mCurrenPage = indexPage;
        if (mCurrenPage > getChildCount() - 1) {
            mCurrenPage = getChildCount() - 1;
        }
        int dx = mCurrenPage * getWidth() - getScrollX();
        mScroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx) * 2);
    }

    /**
     * 慢慢滑动，此时需要计算出到底要停留在那一页
     */
    private void slowScrollToPage() {
        int scrollX = getScrollX();
        // TODO 这里为啥要加一半的屏幕宽，不理解
        int whichPage = (scrollX + getWidth() / 2) / getWidth();
        scrollToPage(whichPage);
    }

    private void initVelocityTrackerIfNotExist() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
}
