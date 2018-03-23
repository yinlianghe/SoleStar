package com.star.mylibrary.view;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 文字可扩展的显示框
 */

public class ExpandableTextView extends android.support.v7.widget.AppCompatTextView {
    private String shortMsg = "可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦" +
            "可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦";
    private String longMsg = "可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦" +
            "可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦" +
            "可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦" +
            "可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦" +
            "可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦" +
            "可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦可扩展的字符串啦啦啦";


    public ExpandableTextView(Context context) {
        super(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 1.计算不同的文字的textview的高度
    // 2.加入动画效果

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Layout layout = getLayout();
        if (layout != null) {
            int height = (int) Math.ceil(getMaxLineHeight(this.getText().toString()))
                    + getCompoundPaddingTop() + getCompoundPaddingBottom();
            int width = getMeasuredWidth();
            setMeasuredDimension(width, height);
        }
    }

    private float getMaxLineHeight(String str) {
        float height = 0.0f;
        float screenW = ((Activity) getContext()).getWindowManager().getDefaultDisplay().getWidth();
        float paddingLeft = ((LinearLayout) this.getParent()).getPaddingLeft();
        float paddingReft = ((LinearLayout) this.getParent()).getPaddingRight();
//这里具体this.getPaint()要注意使用，要看你的TextView在什么位置，这个是拿TextView父控件的Padding的，为了更准确的算出换行
        int line = (int) Math.ceil((this.getPaint().measureText(str) / (screenW - paddingLeft - paddingReft)));
        height = (this.getPaint().getFontMetrics().descent - this.getPaint().getFontMetrics().ascent) * line;
        return height;
    }
}
