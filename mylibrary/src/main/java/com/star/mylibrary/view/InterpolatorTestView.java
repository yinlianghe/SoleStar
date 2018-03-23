package com.star.mylibrary.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.star.mylibrary.utils.ScreenUtils;

/**
 * 插值器 学习view
 */

public class InterpolatorTestView extends View {
    private Paint mPaint;
    private int mStartColor = Color.parseColor("#FF6495ED");
    private int mRadius = ScreenUtils.dip2px(getContext(), 10f);
    private Point mStartPoint, mEndPoint, mCurrentPoint;


    public InterpolatorTestView(Context context) {
        super(context);
        init();
    }

    public InterpolatorTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InterpolatorTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(mStartColor);
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
        mStartPoint = new Point(mRadius, mRadius);
        mEndPoint = new Point(w - mRadius, h - mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mCurrentPoint == null) {
            mCurrentPoint = new Point(mStartPoint.x, mStartPoint.y);
            drawCircle(canvas);
            startAnimator();
        } else {
            drawCircle(canvas);
        }
    }

    private void startAnimator() {
        ValueAnimator animator = ValueAnimator.ofObject(new MyEvaluator(), mStartPoint, mEndPoint);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(5 * 1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPoint = (Point) animation.getAnimatedValue();
                Log.i("ylh", "x==" + mCurrentPoint.x + "   y==" + mCurrentPoint.y);
                invalidate();
            }
        });
        ObjectAnimator animator1 = ObjectAnimator.ofObject(this, "color",
                new ColorEvaluator(), "#0000FF", "#FF0000");
        animator1.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setDuration(5 * 1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(animator1);
        animatorSet.start();
    }


    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mCurrentPoint.x, mCurrentPoint.y, mRadius, mPaint);
    }

    class MyEvaluator implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            float x = startValue.x + (endValue.x - startValue.x) * fraction;
            float y = startValue.y + (endValue.y - startValue.y) * fraction;

            return new Point(x, y);
        }
    }

    public class ColorEvaluator implements TypeEvaluator {

        private int mCurrentRed = -1;

        private int mCurrentGreen = -1;

        private int mCurrentBlue = -1;

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            String startColor = (String) startValue;
            String endColor = (String) endValue;
            int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
            int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
            int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
            int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
            int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
            int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);
            // 初始化颜色的值
            if (mCurrentRed == -1) {
                mCurrentRed = startRed;
            }
            if (mCurrentGreen == -1) {
                mCurrentGreen = startGreen;
            }
            if (mCurrentBlue == -1) {
                mCurrentBlue = startBlue;
            }
            // 计算初始颜色和结束颜色之间的差值
            int redDiff = Math.abs(startRed - endRed);
            int greenDiff = Math.abs(startGreen - endGreen);
            int blueDiff = Math.abs(startBlue - endBlue);
            int colorDiff = redDiff + greenDiff + blueDiff;
            if (mCurrentRed != endRed) {
                mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0,
                        fraction);
            } else if (mCurrentGreen != endGreen) {
                mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff,
                        redDiff, fraction);
            } else if (mCurrentBlue != endBlue) {
                mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,
                        redDiff + greenDiff, fraction);
            }
            // 将计算出的当前颜色的值组装返回
            String currentColor = "#" + getHexString(mCurrentRed)
                    + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);
            return currentColor;
        }

        /**
         * 根据fraction值来计算当前的颜色。
         */
        private int getCurrentColor(int startColor, int endColor, int colorDiff,
                                    int offset, float fraction) {
            int currentColor;
            if (startColor > endColor) {
                currentColor = (int) (startColor - (fraction * colorDiff - offset));
                if (currentColor < endColor) {
                    currentColor = endColor;
                }
            } else {
                currentColor = (int) (startColor + (fraction * colorDiff - offset));
                if (currentColor > endColor) {
                    currentColor = endColor;
                }
            }
            return currentColor;
        }

        /**
         * 将10进制颜色值转换成16进制。
         */
        private String getHexString(int value) {
            String hexString = Integer.toHexString(value);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            return hexString;
        }

    }

    class Point {
        float x;
        float y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }
}
