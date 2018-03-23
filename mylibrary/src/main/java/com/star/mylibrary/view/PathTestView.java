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
import android.view.MotionEvent;
import android.view.View;

import com.star.mylibrary.utils.ScreenUtils;

/**
 * 【path】学习
 */

public class PathTestView extends View {
    private Paint mPaint;
    private Path mPath;
    private RectF oval;

    private Point mControlPoint = new Point(600, 300);
    private Point mControlPoint2 = new Point(500, 500);

    private boolean isClickPointTwo = true;

    public PathTestView(Context context) {
        super(context);
        init();
    }

    public PathTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public PathTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(ScreenUtils.dip2px(getContext(), 2));

        mPath = new Path();
        oval = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*mPath.lineTo(ScreenUtils.dip2px(getContext(), 20), ScreenUtils.dip2px(getContext(), 20));
        mPath.lineTo(ScreenUtils.dip2px(getContext(), 40), 0);
        canvas.drawPath(mPath, mPaint);

        canvas.save();
        canvas.translate(0, 100);
        mPath.reset();
        mPath.lineTo(200, 200);
        mPath.moveTo(300, 300);
        mPath.lineTo(400, 0);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();


        canvas.save();
        canvas.translate(0, 500);
        mPath.reset();
        mPath.lineTo(200, 200);
        mPath.setLastPoint(300, 100);// 重置上一个点
        mPath.lineTo(400, 0);
        mPath.close();// 封闭图形
        canvas.drawPath(mPath, mPaint);
        canvas.restore();*/

        // 二次贝塞尔曲线
//        canvas.save();
//        canvas.translate(500, 500);
//        mPath.reset();
//        mPath.moveTo(-200, 0);
//        mPath.quadTo(0, 300, 200, 0);
//        canvas.drawPath(mPath, mPaint);
//        canvas.restore();

        // 三次贝塞尔曲线
//        mPath.reset();
//        mPath.cubicTo(100, 400, 300, 100, 400, 400);
//        canvas.drawPath(mPath, mPaint);

//        mPath.reset();
//        mPath.addRect(100, 200, 500, 400, Path.Direction.CW);
//        canvas.drawPath(mPath, mPaint);

//        mPath.reset();
//        mPath.addRect(100, 200, 500, 400, Path.Direction.CW);
//        mPath.setLastPoint(200, 400);
//        canvas.drawPath(mPath, mPaint);

//        mPath.reset();
//        mPath.addRect(100, 200, 500, 400, Path.Direction.CCW);
//        mPath.setLastPoint(400, 300);
//        canvas.drawPath(mPath, mPaint);

          // 圆
//        mPath.reset();
//        mPath.addCircle(300, 300, 200, Path.Direction.CW);
//        canvas.drawPath(mPath, mPaint);

//        mPath.reset();
//        mPath.moveTo(100, 100);
//        oval.left = 100;
//        oval.top = 100;
//        oval.right = 200;
//        oval.bottom = 200;
//        mPath.arcTo(oval, 0, 90, false);
//        canvas.drawPath(mPath, mPaint);

//        mPath.reset();
//        mPath.moveTo(100, 100);
//        mPath.rLineTo(200, 200);
//        canvas.drawPath(mPath, mPaint);

//        mPath.reset();
//        mPath.moveTo(100, 100);
//        oval.left = 100;
//        oval.right = 100;
//        oval.right = 300;
//        oval.bottom = 400;
//        mPath.addOval(oval, Path.Direction.CCW);
//        canvas.drawPath(mPath, mPaint);
//        String txt = "123456789";
//        mPaint.setTextSize(ScreenUtils.sp2px(getContext(), 16));
//        canvas.drawTextOnPath(txt, mPath, 0, 100, mPaint);

        // 二次贝塞尔曲线
//        mPath.reset();
//        mPath.moveTo(400, 400);
//        mPath.quadTo(mControlPoint.x, mControlPoint.y, 800, 400);
//        canvas.drawPath(mPath, mPaint);
//        canvas.drawPoint(mControlPoint.x, mControlPoint.y, mPaint);

        mPath.reset();
        mPath.moveTo(400, 400);
        mPath.cubicTo(mControlPoint.x, mControlPoint.y, mControlPoint2.x, mControlPoint2.y, 800, 400);
        canvas.drawPath(mPath, mPaint);
        canvas.drawPoint(mControlPoint.x, mControlPoint.y, mPaint);
        canvas.drawPoint(mControlPoint2.x, mControlPoint2.y, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isClickPointTwo) {
                    mControlPoint2.x = event.getX();
                    mControlPoint2.y = event.getY();
                } else {
                    mControlPoint.x = event.getX();
                    mControlPoint.y = event.getY();
                }
                invalidate();
                break;
        }

        return true;
    }

    class Point {
        float x;
        float y;
        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
