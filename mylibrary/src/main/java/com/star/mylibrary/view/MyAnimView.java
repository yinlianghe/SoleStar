package com.star.mylibrary.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyAnimView extends View {

    public static final float RADIUS = 10f;

    private Point currentPoint;

    private Paint mPaint;

    public MyAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(5l);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawLine((getLeft()+getRight())/2, (getBottom()+getTop())/2,x,y,mPaint);
    }

    public void startAnimation() {

        Point startPoint = new Point((getLeft()+getRight())/2, (getBottom()+getTop())/2+ Math.min(((getRight()-getLeft()))/2,(getBottom()-getTop())/2));
        Point endPoint = startPoint;
        Log.e("myAnim","x="+(getLeft()+getRight())/2+"    y="+(getTop()+getBottom())/2);
        Log.e("myAnim","left="+getLeft()+"   right="+getRight()+"    top="+getTop()+"   botton"+getBottom());

        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(Math.min(((getRight()-getLeft()))/2,(getBottom()-getTop())/2)), startPoint, endPoint);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.setRepeatCount(1000);
        anim.setDuration(4000);
        anim.start();
    }

    class Point {
        float x, y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        float getX() {
            return x;
        }

        float getY() {
            return y;
        }
    }


    public class PointEvaluator implements TypeEvaluator {
        float r;
        public PointEvaluator(float r)
        {
            this.r = r;
        }

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;
            float x = new Float(startPoint.getX() - Math.sin(fraction* Math.PI*2*20)*r);
            float y = new Float(startPoint.getY() - (r - Math.cos(fraction* Math.PI*2*20) * r) );
            Point point = new Point(x, y);
            return point;
        }

    }
}