package com.star.mylibrary.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.star.mylibrary.utils.ScreenUtils;

/**
 * 【水波纹】 练习
 */

public class WaveTestView extends View {
    private Paint mPaint;
    // 波长
    private int mWaveLength;
    private int mViewHeight;
    // 振幅
    private int mWaveAmplitude;
    private int mOffset;
    private Path mPath;
    private ValueAnimator mAnim;


    public WaveTestView(Context context) {
        super(context);
        init();
    }

    public WaveTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public WaveTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(ScreenUtils.dip2px(getContext(), 2));
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWaveLength = w;
        mViewHeight = h;
        mWaveAmplitude = mWaveLength/6;
        initAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(0, mViewHeight/2);
        mPath.reset();
        mPath.moveTo(-mWaveLength + mOffset, 0);
        mPath.quadTo(-mWaveLength*3/4+mOffset, -mWaveAmplitude, -mWaveLength/2 + mOffset, 0);
        mPath.quadTo(-mWaveLength/4 + mOffset, -mWaveAmplitude, mOffset, 0);
        mPath.quadTo(mWaveLength/4+mOffset, mWaveAmplitude, mWaveLength/2+mOffset,0);
        mPath.quadTo(mWaveLength*3/4+mOffset, -mWaveAmplitude, mWaveLength+mOffset, 0);
        mPath.lineTo(mWaveLength, mViewHeight);
        mPath.lineTo(0, mViewHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    private void initAnimation() {
        mAnim = ValueAnimator.ofInt(0, getWidth());
        mAnim.setDuration(1000);
        mAnim.setRepeatCount(ValueAnimator.INFINITE);
        mAnim.setInterpolator(new LinearInterpolator());
        mAnim.setRepeatMode(ValueAnimator.RESTART);
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                Log.e("ylh==", mOffset + "");
                invalidate();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(getContext(), "111111", Toast.LENGTH_SHORT).show();
                mAnim.start();
                break;
        }
        return true;

    }


}
