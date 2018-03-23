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

import java.util.Calendar;

/**
 * 【表盘 自定义 练习】
 */

public class ClockCircleView extends View {
    private Paint mPaint;
    private int mPadding = ScreenUtils.dip2px(getContext(), 10);
    private int mRadius;
    private int mPointEndLength;
    // 时针长度
    private int mHourLength = ScreenUtils.dip2px(getContext(), 20f);
    // 分针长度
    private int mMinuteLength = ScreenUtils.dip2px(getContext(), 10f);
    // 刻度距离轮廓10dp的偏移量
    private int mTenDpOffset = ScreenUtils.dip2px(getContext(), 10f);
    private Rect mTxtBound;
    private Calendar mCalendar;
    private RectF mHourPointerRectF, mMinutePointerRectF, mSecondPointerRectF;
    private int mHourPointWidth = ScreenUtils.dip2px(getContext(), 5);
    private int mMinutePointWidth = ScreenUtils.dip2px(getContext(), 3);
    private int mSecondPointWidth = ScreenUtils.dip2px(getContext(), 2);
    private int mPointRadius = ScreenUtils.dip2px(getContext(), 10);


    public ClockCircleView(Context context) {
        super(context);
        init();
    }

    public ClockCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public ClockCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mTxtBound = new Rect();
        mCalendar = Calendar.getInstance();
        mHourPointerRectF = new RectF();
        mMinutePointerRectF = new RectF();
        mSecondPointerRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize-getPaddingRight(), heightSize-getPaddingBottom());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = (Math.min(w,h)-mPadding)/2;
        mPointEndLength = mRadius/6;// 尾部默认为半径的六分之一
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(getWidth()/2, getHeight()/2);
        // 绘制圆盘
        drawCircle(canvas);
        // 绘制刻度
        drawScale(canvas);
        // 绘制指针
        drawPointer(canvas);
        //绘制中心小圆
        drawLittleCircle(canvas);
        canvas.restore();
        //刷新
        postInvalidateDelayed(1000);
    }

    private void drawCircle(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0,0,mRadius,mPaint);
    }

    private void drawScale(Canvas canvas) {
        canvas.save();
        mPaint.setColor(0xFF333333);
        int lineLength;// 刻度线长度
        int lineStrokeWidth;
        for (int i = 0; i < 60; i++) {
            canvas.save();
            if (i % 5 == 0) {// 整点
                lineLength = ScreenUtils.dip2px(getContext(), 14f);
                lineStrokeWidth = ScreenUtils.dip2px(getContext(), 1.5f);

                mPaint.setTextSize(ScreenUtils.sp2px(getContext(), 12));
                String txt = i == 0 ? "12" : String.valueOf(i/5);
                mPaint.getTextBounds(txt, 0, txt.length(), mTxtBound);

                canvas.save();
                canvas.translate(0, -mRadius + ScreenUtils.dip2px(getContext(), 15) + lineLength + (mTxtBound.bottom-mTxtBound.top) );
                canvas.rotate(-6 * i);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawText(txt, -(mTxtBound.right-mTxtBound.left)/2, mTxtBound.bottom, mPaint);
                canvas.restore();

            } else {// 非整点
                lineLength = ScreenUtils.dip2px(getContext(), 7f);
                lineStrokeWidth = ScreenUtils.dip2px(getContext(), 1f);
            }
            mPaint.setStrokeWidth(lineStrokeWidth);
            canvas.drawLine(0, -mRadius + mTenDpOffset, 0, -mRadius + mTenDpOffset + lineLength, mPaint);
            canvas.rotate(6);
        }
        canvas.restore();
    }

    private void drawPointer(Canvas canvas) {
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        int second = mCalendar.get(Calendar.SECOND);
        int angleHour = (hour % 12) * 360 / 12; //时针转过的角度
        int angleMinute = minute * 360 / 60; //分针转过的角度
        int angleSecond = second * 360 / 60; //秒针转过的角度

        // 时针
        canvas.save();
        canvas.rotate(angleHour);
        mPaint.setColor(0xFF333333);
        mPaint.setStrokeWidth(ScreenUtils.dip2px(getContext(), 3));
        canvas.drawRoundRect(getHourPointerRectF(), mPointRadius, mPointRadius, mPaint);
        canvas.restore();

        // 分针
        canvas.save();
        canvas.rotate(angleMinute);
        mPaint.setColor(0xFF333333);
        mPaint.setStrokeWidth(ScreenUtils.dip2px(getContext(), 2));
        canvas.drawRoundRect(getMinutePointerRectF(), mPointRadius, mPointRadius, mPaint);
        canvas.restore();

        // 秒针
        canvas.save();
        canvas.rotate(angleSecond);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStrokeWidth(mSecondPointWidth);
        canvas.drawRoundRect(getSecondPointerRectF(), mPointRadius, mPointRadius, mPaint);
        canvas.restore();

    }

    private RectF getHourPointerRectF() {
        mHourPointerRectF.left = -mHourPointWidth/2;
        mHourPointerRectF.right = mHourPointWidth/2;
        mHourPointerRectF.top = -mRadius*3/5;
        mHourPointerRectF.bottom = mPointEndLength;
        return mHourPointerRectF;
    }

    private RectF getMinutePointerRectF() {
        mMinutePointerRectF.left = -mMinutePointWidth/2;
        mMinutePointerRectF.right = mMinutePointWidth/2;
        mMinutePointerRectF.top = (float) (-mRadius*3.5/5);
        mMinutePointerRectF.bottom = mPointEndLength;
        return mMinutePointerRectF;
    }

    private RectF getSecondPointerRectF() {
        mSecondPointerRectF.left = -mSecondPointWidth/2;
        mSecondPointerRectF.right = mSecondPointWidth/2;
        mSecondPointerRectF.top = (float) (-mRadius*3.5/5);
        mSecondPointerRectF.bottom = mPointEndLength;
        return mSecondPointerRectF;
    }

    private void drawLittleCircle(Canvas canvas) {
        mPaint.setColor(Color.RED);
        canvas.drawCircle(0, 0, ScreenUtils.dip2px(getContext(), 5), mPaint);
    }
}
