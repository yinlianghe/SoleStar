package com.sole.star.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;
import android.widget.Toast;

import com.sole.star.R;
import com.star.mylibrary.view.pwd.PwdView;

public class PwdActivity extends AppCompatActivity {
    private PwdView mPwdView;
    private TextView textView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd);

        mPwdView = findViewById(R.id.pwd_view);
        mPwdView.setCallback(new PwdView.OnPwdCallback() {
            @Override
            public void getPwd(boolean isValidate, String pwd) {
                Toast.makeText(PwdActivity.this, pwd, Toast.LENGTH_SHORT).show();
            }
        });
        textView = findViewById(R.id.text);
        String str = textView.getText().toString();
        textView.setText(getSubtitleSS(str));
    }
    private SpannableString getSubtitleSS(String subtitle) {
        SpannableString spannableString = new SpannableString(subtitle);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.wait_payerror);
        drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, subtitle.length() - 1, subtitle.length(), ImageSpan.ALIGN_BASELINE);
        return spannableString;
    }
}
