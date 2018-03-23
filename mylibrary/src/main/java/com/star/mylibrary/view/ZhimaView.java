package com.star.mylibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 正N边形
 * author : stone
 * email  : aa86799@163.com
 * time   : 2016/11/17 14 12
 */

public class ZhimaView extends View {

    private Paint mPaint;
    private float mR, mCx, mCy;
    private static final int mN = 9;
    private static final float DEGREES_UNIT = 360 / mN; //正N边形每个角  360/mN能整除

    public ZhimaView(Context context) {
        this(context, null);
    }

    public ZhimaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZhimaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float mW = getMeasuredWidth();
        float mH = getMeasuredHeight();

        mCx = mW / 2;
        mCy = mH / 2;
        mR = Math.min(mCx, mCy) / 4 * 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);

        float d = (float) (2 * mR * Math.sin(Math.toRadians(DEGREES_UNIT / 2)));
        float c = mCy - mR;
        float y = (d * d + mCy * mCy - c * c - mR * mR) / (2 * (mCy - c));
        float x = (float) (mCx + Math.sqrt(-1 * c * c + 2 * c * y + d * d - y * y));

        for (int i = 0; i < mN; i++) {
            canvas.save();
            canvas.rotate(DEGREES_UNIT * i, mCx, mCy);
            canvas.drawLine(mCx, mCy, mCx, c, mPaint);
            canvas.drawLine(mCx, c, x, y, mPaint);
            canvas.restore();
        }
    }
}