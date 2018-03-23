package com.star.mylibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.star.mylibrary.utils.ScreenUtils;

/**
 * 【自定义滚动条实现】
 */

public class HZSeekBar extends View {
    private Paint mPaint;
    private int mWidth, mHeight;
    private int mRadius = ScreenUtils.dip2px(getContext(),8f);
    private Position mLeftDot, mRightDot;
    private boolean isInLeftDot, isInRightDot;
    private int mLeftLimit, mRightLimit;
    private OnSlideListener mOnSlideListener;
    private int mLineWidth, mPaddingLeft;

    public void setOnSlideListener(OnSlideListener onSlideListener) {
        this.mOnSlideListener = onSlideListener;
    }

    public HZSeekBar(Context context) {
        super(context);
        init();
    }

    public HZSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HZSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = ScreenUtils.dip2px(getContext(), 30f);
        }

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mPaddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();

        mLineWidth = mWidth-mPaddingLeft-paddingRight;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();

        if (mLeftDot == null || mRightDot == null) {
            mLeftLimit = paddingLeft;
            mRightLimit = paddingLeft+mLineWidth;
            mLeftDot = new Position(mLeftLimit, mHeight/2);
            mRightDot = new Position(mRightLimit, mHeight/2);
        }
        mPaint.setColor(0xff999999);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(ScreenUtils.dip2px(getContext(), 1));
        canvas.drawLine(mLeftLimit, mHeight/2,mRightLimit,mHeight/2,mPaint);

        mPaint.setColor(0xFF9400D3);
        canvas.drawLine(mLeftDot.x, mHeight/2, mRightDot.x, mHeight/2, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff999999);
        mPaint.setStrokeWidth(ScreenUtils.dip2px(getContext(),2));
        Log.i("ylh", "mLeftDot.x==" + mLeftDot.x);
        Log.i("ylh", "mRightDot.x==" + mRightDot.x);
        canvas.drawCircle(mLeftDot.x, mLeftDot.y, mRadius, mPaint);
        canvas.drawCircle(mRightDot.x, mRightDot.y, mRadius, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffffffff);
        canvas.drawCircle(mLeftDot.x, mLeftDot.y, mRadius, mPaint);
        canvas.drawCircle(mRightDot.x, mRightDot.y, mRadius, mPaint);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float cx=0, cy, dx, originLeftDotX=0, originRightDotX=0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cx = event.getX();
                cy = event.getY();
                originLeftDotX = mLeftDot.x;
                originRightDotX = mRightDot.x;
                Log.i("ylh", "cx==" + cx + "" + "cy==" + cy + "");
                isInLeftDot = isInLeftDotSection(cx, cy);
                isInRightDot = isInRightDotSection(cx, cy);
                Log.i("ylh", "isInLeftDot==" +isInLeftDot + "isInRightDot==" + isInRightDot);
                performClick();
                break;

            case MotionEvent.ACTION_MOVE:
                Log.i("ylh", "isWithinRangeLimit" + isWithinRangeLimit(cx) + "");
                dx = event.getX()-cx;
                if (isInLeftDot && isWithinRangeLimit(event.getX())) {
                    mLeftDot.x = originLeftDotX + dx;
                    if (mLeftDot.x >= mRightDot.x) {
                        mRightDot.x = mLeftDot.x;
                    }
                    Log.i("ylh", "left-----cx==" + cx);
                    if (mOnSlideListener != null) {
                        mOnSlideListener.getPercentage(mLeftDot.x-mPaddingLeft/mLineWidth, mRightDot.x-mPaddingLeft/mLineWidth);
                    }
                    invalidate();
                } else if (isInRightDot && isWithinRangeLimit(cx)) {
                    mRightDot.x = originRightDotX + dx;
                    if (mRightDot.x <= mLeftDot.x) {
                        mLeftDot.x = mRightDot.x;
                    }
                    Log.i("ylh", "right-----cx==" + cx);
                    if (mOnSlideListener != null) {
                        mOnSlideListener.getPercentage(mLeftDot.x-mPaddingLeft/mLineWidth, mRightDot.x-mPaddingLeft/mLineWidth);
                    }
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                isInLeftDot = false;
                isInRightDot = false;
                break;
        }

        return true;
    }

    private boolean isInLeftDotSection(float cx, float cy) {
        return Math.pow(cx - mLeftDot.x, 2) + Math.pow(cy-mLeftDot.y, 2) <= Math.pow(mRadius+6, 2);// 扩大2dp范围，为了更好的点击到
    }

    private boolean isInRightDotSection(float cx, float cy) {
        return Math.pow(cx - mRightDot.x, 2) + Math.pow(cy-mRightDot.y, 2) <= Math.pow(mRadius+6, 2);// 扩大2dp范围，为了更好的点击到
    }

    private boolean isWithinRangeLimit(float cx) {
        return cx >= mLeftLimit-getPaddingLeft() && cx <= mRightLimit-getPaddingLeft();
    }

    class Position {
        float x, y;

        Position(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public interface OnSlideListener {

        void getPercentage(float leftPercentage, float rightPercentage);
    }


}
