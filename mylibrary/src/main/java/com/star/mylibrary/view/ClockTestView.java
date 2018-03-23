package com.star.mylibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.star.mylibrary.utils.ScreenUtils;

/**
 * 【时钟 练习】
 */

public class ClockTestView extends View {
    private int mMinuteHandCount = 60;
    final private int DEGREE_UNIT = 360 / 60;

    private int mCx, mCy, mRadius;

    private Paint mOutlinePaint;
    private Paint mInnerBgPaint;
    private Paint mMinuteHandPaint;
    private int mMinuteHandLength;
    private Paint mHourHandPaint;
    private int mHourHandLength;
    private Paint mClockNumPaint;
    private Rect mClockNumBound;


    public ClockTestView(Context context) {
        super(context);
        init();
    }

    public ClockTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public ClockTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mOutlinePaint = new Paint();
        mOutlinePaint.setColor(0xff999999);
        mOutlinePaint.setAntiAlias(true);
        mOutlinePaint.setStyle(Paint.Style.STROKE);
        mOutlinePaint.setStrokeWidth(ScreenUtils.dip2px(getContext(), 1));

        mInnerBgPaint = new Paint();
        mInnerBgPaint.setColor(Color.WHITE);
        mInnerBgPaint.setStyle(Paint.Style.FILL);
        mInnerBgPaint.setAntiAlias(true);

        mMinuteHandPaint = new Paint();
        mMinuteHandPaint.setColor(0xff333333);
        mMinuteHandPaint.setStyle(Paint.Style.STROKE);
        mMinuteHandPaint.setStrokeWidth(ScreenUtils.dip2px(getContext(), 1));

        mHourHandPaint = new Paint();
        mHourHandPaint.setColor(0xff333333);
        mHourHandPaint.setStyle(Paint.Style.STROKE);
        mHourHandPaint.setStrokeWidth(ScreenUtils.dip2px(getContext(), 2));

        mMinuteHandLength = ScreenUtils.dip2px(getContext(), 6);
        mHourHandLength = ScreenUtils.dip2px(getContext(), 12);

        mClockNumPaint = new Paint();
        mClockNumPaint.setColor(0xff333333);
        mClockNumPaint.setTextSize(ScreenUtils.sp2px(getContext(), 12));
        mClockNumPaint.setAntiAlias(true);
        mClockNumPaint.setStyle(Paint.Style.FILL);
        mClockNumPaint.setStrokeWidth(ScreenUtils.dip2px(getContext(), 1));

        mClockNumBound = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mCx = widthSize / 2;
        mCy = heightSize / 2;
        mRadius = mCx - ScreenUtils.dip2px(getContext(), 10);

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(0xFFFFDAB9);
        canvas.drawCircle(mCx,mCy,mRadius, mOutlinePaint);
        canvas.drawCircle(mCx,mCy,mRadius, mInnerBgPaint);

        // 绘制表盘
        for (int i = 0; i < mMinuteHandCount; i++) {
            if (i % 5 == 0) {
                // 时针
                canvas.drawLine(mCx, mCy - mRadius, mCx, mCy - mRadius + mHourHandLength, mHourHandPaint);
            } else {
                // 分针
                canvas.drawLine(mCx, mCy - mRadius, mCx, mCy -mRadius + mMinuteHandLength, mHourHandPaint);
            }
            canvas.save();
            canvas.rotate(DEGREE_UNIT, mCx, mCy);
        }
        canvas.restore();

        // 绘制刻度
        int degree_hour = 360 / 12;
        int angle;
        double sinAngle, cosAngle;
        String clockNum;
        int innerRadius = mRadius - mHourHandLength - 36;
        int clockNumCx, clockNumCy;
        for (int i = 0; i < 12; i++) {
            clockNum = String.valueOf(i == 0 ? 12 : i);
            mClockNumPaint.getTextBounds(clockNum, 0 , clockNum.length(), mClockNumBound);
            angle = i * degree_hour;
            sinAngle = Math.sin(Math.PI * angle/180);
            cosAngle = Math.cos(Math.PI * angle/180);
            clockNumCx = (int) (mCx + innerRadius * sinAngle);
            clockNumCy = (int) (mCy - innerRadius * cosAngle);
            canvas.drawText(clockNum, clockNumCx + (mClockNumBound.right-mClockNumBound.left)/2,
                    clockNumCy + (mClockNumBound.bottom-mClockNumBound.top)/2, mClockNumPaint);
        }
    }
}
