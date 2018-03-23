package com.star.mylibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.star.mylibrary.utils.ScreenUtils;

/**
 * 三角角标 如  分享
 */

public class TriangleLabel extends View {
    private Paint mPaint;
    private Path mPath;
    private RectF mBounds;



    public TriangleLabel(Context context) {
        super(context);
        init(context, null);
    }

    public TriangleLabel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TriangleLabel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public TriangleLabel(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    private void init(Context context, @Nullable AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xfffd7753);
        mPaint.setTextSize(ScreenUtils.dip2px(getContext(), 11));

        mPath = new Path();
        mBounds = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 宽高写死
//        int widthSize = ScreenUtils.dip2px(getContext(), 36f);
//        int heightSize = ScreenUtils.dip2px(getContext(), 38);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.save();
        mPath.reset();
        mPath.lineTo(ScreenUtils.dip2px(getContext(), 36), 0);
        mPath.lineTo(0, ScreenUtils.dip2px(getContext(), 38));
        mPath.close();
        mPaint.setColor(0xfffd7753);
        canvas.drawPath(mPath, mPaint);


        mPaint.setColor(Color.WHITE);
        canvas.save();
        canvas.translate(0, ScreenUtils.dip2px(getContext(), 38));
        canvas.rotate(-45);
        canvas.drawTextOnPath("专享".toCharArray(), 0, 2, mPath, 45, -15, mPaint);
        canvas.restore();


    }
}
