package com.star.mylibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Toast;

import com.star.mylibrary.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinlianghe on 06/01/2018.
 * 颜色是argb
 */

public class PieChartTestView extends View {
    /**
     * 圆的半径
     */
    private int mRadius;
    private int mCx, mCy;
    private Paint mPaint;
    RectF mRectF;
    private List<Integer> mColorList = new ArrayList<>();

    private Paint mOuterPaint;
    private int mOuterRadius;
    private Paint mInnerPaint;
    private int mInnerRadius = ScreenUtils.dip2px(getContext(), 50f);


    public PieChartTestView(Context context) {
        super(context);
        init();
    }

    public PieChartTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChartTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public PieChartTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        mColorList.add(Color.RED);
        mColorList.add(Color.BLUE);
        mColorList.add(Color.GREEN);
        mColorList.add(Color.CYAN);

        mPaint = new Paint();
//        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.red));
        mPaint.setColor(Color.RED);
//        mPaint.setColor(0xFFFF0000);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);

        mRectF = new RectF();

        mOuterPaint = new Paint();
        mOuterPaint.setStrokeWidth(ScreenUtils.dip2px(getContext(), 2));
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setColor(0xffdddddd);

        mInnerPaint = new Paint();
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mRadius = Math.min(widthSize, heightSize) / 2 - ScreenUtils.dip2px(getContext(), 20f);
        Log.i("ylh", "========" + mRadius);
        mCx = getWidth() / 2;
        mCy = getHeight() / 2;

        mRectF.left = mCx-mRadius;
        mRectF.top = mCy-mRadius;
        mRectF.right = mCx+mRadius;
        mRectF.bottom = mCy+mRadius;

        mOuterRadius = mRadius + 2;


        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mColorList.size(); i++) {
            mPaint.setColor(mColorList.get(i));
            canvas.drawArc(mRectF, -90 + i * 90, 90, true, mPaint);
        }
        canvas.drawCircle(mCx, mCy, mOuterRadius, mOuterPaint);
        canvas.drawCircle(mCx, mCy, mInnerRadius, mInnerPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                if (Math.abs(x - mCx) <= mRadius && Math.abs(x - mCx) >= mInnerRadius
                        && Math.abs(y - mCy) <= mRadius && Math.abs(y - mCy) >= mInnerRadius) {
                    if (x > mCx && y < mCy) {
                        // 第一象限
                        Log.i("ylh", "第一象限");
                        Toast.makeText(getContext(), "第一象限", Toast.LENGTH_SHORT).show();
                    } else if (x > mCx && y > mCy) {
                        // 第二象限
                        Log.i("ylh", "第二象限");
                        Toast.makeText(getContext(), "第二象限", Toast.LENGTH_SHORT).show();
                    } else if (x < mCx && y < mCy) {
                        // 第四象限
                        Log.i("ylh", "第四象限");
                        Toast.makeText(getContext(), "第四象限", Toast.LENGTH_SHORT).show();
                    } else if (x < mCx && y > mCy) {
                        // 第三象限
                        Log.i("ylh", "第三象限");
                        Toast.makeText(getContext(), "第三象限", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("ylh", "其他区域");
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    class MyAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);



        }
    }

}
