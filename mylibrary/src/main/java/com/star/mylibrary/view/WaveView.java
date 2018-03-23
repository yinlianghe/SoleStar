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
import android.widget.Toast;

import com.star.mylibrary.utils.ScreenUtils;

/**
 * 【水波纹】 练习
 */

public class WaveView extends View {
    private Paint mPaint;
    // 波长
    private int mWaveLength;
    // 贝塞尔曲线的控制点高度
    private int mWaveControllerHeight;
    // 这里只需要画两个完整的波，故此只需要4个控制点
    private ControllerPosition[] mControllerPositions = new ControllerPosition[4];
    private Path mPath;
    private ValueAnimator mAnim;
    private int mOffsetDis = 0;

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        mWaveControllerHeight = mWaveLength/6;
        boolean isBelow = true;// 默认起始点从左边一屏外开始
        int cx = -mWaveLength * 3 / 4;
        int cy;
        ControllerPosition position;
        for (int i = 0; i < mControllerPositions.length; i++) {
            position = mControllerPositions[i];
            cx += mWaveLength/2;
            cy = isBelow ? -mWaveControllerHeight : mWaveControllerHeight;
            isBelow = !isBelow;
            if (position == null) {
                mControllerPositions[i] = new ControllerPosition(cx, cy);
            } else {
                position.x = cx;
                position.y = cy;
            }
        }
        mAnim = getAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        mPath.reset();
        canvas.translate(0, getMeasuredHeight()/2);
        mPath.moveTo(-mWaveLength + mOffsetDis, 0);
        for (ControllerPosition cp : mControllerPositions) {
            mPath.quadTo(cp.x, cp.y, cp.x + mWaveLength / 4, 0);
        }
        mPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
        mPath.lineTo(0, getMeasuredHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
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

    class ControllerPosition {
        float x;
        float y;

        ControllerPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    private ValueAnimator getAnimator() {
        ValueAnimator anim = ValueAnimator.ofInt(0, getWidth());
        anim.setDuration(1000);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffsetDis = (int) animation.getAnimatedValue();
                for (ControllerPosition cp : mControllerPositions) {
                    cp.x += mOffsetDis;
                }

                invalidate();
            }
        });
        return anim;
    }
}
