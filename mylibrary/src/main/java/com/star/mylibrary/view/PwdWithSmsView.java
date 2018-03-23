package com.star.mylibrary.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * 有验证码的页面的，新的输入密码的样式，跟老版本的长的不同
 */

public class PwdWithSmsView extends android.support.v7.widget.AppCompatEditText implements TextWatcher {
    /**
     * 密码字符
     */
    private List<Character> mPwdChars;
    private Character mCur;
    private final Character mReplaceChar = '*';

    public PwdWithSmsView(Context context) {
        super(context);
        init();
    }

    public PwdWithSmsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PwdWithSmsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mPwdChars == null) {
            mPwdChars = new ArrayList<>();
        }
        if (s.length() > mPwdChars.size()) {
            mCur = s.charAt(s.length()-1);
            if (mReplaceChar == mCur) {
                return;
            }
            mPwdChars.add(mCur);
            setText(mReplaceChar);
        } else if (s.length() < mPwdChars.size()) {
            mPwdChars.remove(mPwdChars.size()-1);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0) {
            setTextSize(13);
        } else {
            setTextSize(30);
        }
    }
}
