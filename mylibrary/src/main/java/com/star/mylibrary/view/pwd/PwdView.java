package com.star.mylibrary.view.pwd;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.star.mylibrary.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 密码输入框
 */

public class PwdView extends android.support.v7.widget.AppCompatEditText implements TextWatcher {
    private Paint mPaint;
    private List<Character> mCharacterList;
    private int mDotRadius;
    private int mDotSpace;

    private OnPwdCallback mCallback;

    public void setCallback(OnPwdCallback callback) {
        this.mCallback = callback;
    }

    public PwdView(Context context) {
        super(context);
        init(context);
    }

    public PwdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PwdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xff333333);
        mPaint.setTextSize(ScreenUtils.sp2px(getContext(), 13f));

        mDotRadius = ScreenUtils.dip2px(getContext(), 14f)/2;
        mDotSpace = ScreenUtils.dip2px(getContext(), 8f);

        setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mCharacterList == null) {
            mCharacterList = new ArrayList<>();
        }
        mCharacterList.clear();
        for (int i = 0; i < s.length(); i++) {
            mCharacterList.add(s.charAt(i));
        }

        invalidate();

        if (mCallback != null) {
            if (s.length() == 6) {
                mCallback.getPwd(true, s.toString());
            } else {
                mCallback.getPwd(false, s.toString());
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCharacterList.size() == 0) {
            mPaint.setColor(0xff999999);
            Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
            int startX = + getPaddingLeft();
            int startY = getHeight() / 2 - fm.descent + (fm.bottom - fm.top) / 2;
            canvas.drawText("请输入支付密码", startX, startY, mPaint);
            return;
        }
        mPaint.setColor(0xff333333);
        for (int i = 0; i < mCharacterList.size(); i++) {
            int cx = (mDotRadius*2 + mDotSpace) * i + mDotRadius + getPaddingLeft();
            canvas.drawCircle(cx, getHeight()/2, mDotRadius, mPaint);
            setCursorVisible(true);
            setSelection(mCharacterList.size());
        }
    }

    public interface OnPwdCallback {
        void getPwd(boolean isValidate, String pwd);
    }
}
