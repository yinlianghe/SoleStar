package com.star.mylibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.star.mylibrary.utils.ScreenUtils;

import org.w3c.dom.Text;

/**
 * 倾斜字体
 */

public class LeanTextView extends View {
    private Paint mPaint;
    // 画弧的时候，弧边长最大值
    private int mMaxArcSide;
    private RectF mOval;
    private Path mPath;

    public LeanTextView(Context context) {
        super(context);
        init();
    }

    public LeanTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(0xff333333);
        mPaint.setTextSize(14 * 3);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);

        mOval = new RectF();

        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST) {
            throw new UnsupportedOperationException("can not support this mode");
        }
        mMaxArcSide = widthSize >= heightSize ? heightSize : widthSize;

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.translate(getWidth()/2, getHeight()/2);
//        canvas.save();
//        canvas.rotate(-30);
//        canvas.drawText("去购买", 0, 0, mPaint);
//
//        canvas.restore();

//        canvas.translate(getWidth(), getHeight());
//        // 假设画一个最短半径一半的弧边长
//        mOval.top = mMaxArcSide/2;
//        mOval.bottom = 0;
//        mOval.left = -mMaxArcSide/2;
//        mOval.right = 0;
//        canvas.drawArc(mOval,-270, -180, false, mPaint);

//        canvas.translate(getWidth()/2, getHeight()/2);
//        canvas.drawCircle(0, 0, mMaxArcSide/2, mPaint);

        drawOutline(canvas);
        drawArc(canvas);
        drawLeanText(canvas);
    }


    private void drawOutline(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xffcccccc);
        mPaint.setStrokeWidth(ScreenUtils.dip2px(getContext(), 1f));
        mPath.lineTo(getWidth(), 0);
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void drawArc(Canvas canvas) {
        canvas.translate(getWidth(), getHeight());
        mPaint.setColor(0xffaaaaaa);
//        canvas.drawRect(-getWidth()/2, -getHeight()/2, 0, 0, mPaint);

        mOval.left = -getWidth();
        mOval.top = -getHeight();
        mOval.right = 0;
        mOval.bottom = 0;
        mOval.offset(getWidth()/2, getHeight()/2);
        canvas.drawArc(mOval, 180, 270, false, mPaint);

        mOval.left = -getWidth()-6;
        mOval.top = -getHeight()-6;
        mOval.right = 0;
        mOval.bottom = 0;
        mOval.offset(getWidth()/2-3, getHeight()/2-3);
        canvas.drawArc(mOval, 180, 270, false, mPaint);

        mOval.left = -getWidth()-12;
        mOval.top = -getHeight()-12;
        mOval.right = 0;
        mOval.bottom = 0;
        mOval.offset(getWidth()/2-6, getHeight()/2-6);
        canvas.drawArc(mOval, 180, 270, false, mPaint);
    }

    private void drawLeanText(Canvas canvas) {
        mPaint.setColor(0xffff0000);

        canvas.save();
        canvas.rotate(-45);
        canvas.translate(0, (float) -30*3);
        canvas.drawText("已认证", -getWidth()/4, 0, mPaint);
        canvas.restore();
    }




}
