package com.sole.star.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sole.star.R;
import com.star.mylibrary.view.HZSeekBar;

/**
 *  自定义seekbar练习
 */

public class HZSeekBarActivity extends AppCompatActivity {
    private HZSeekBar mHZSeekBar;
    private TextView mPriceTV;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hz_seek_bar);

        mHZSeekBar = findViewById(R.id.hzsb_main_seek_bar);
        mPriceTV = findViewById(R.id.tv_main_price);

        mHZSeekBar.setOnSlideListener(new HZSeekBar.OnSlideListener() {
            @Override
            public void getPercentage(float leftPercentage, float rightPercentage) {
                int leftPrice = (int) Math.floor(leftPercentage);
                int rightPrice = (int) Math.floor(rightPercentage);
                String price = String.format("￥%1s - ￥%2s", leftPrice, rightPrice);
                mPriceTV.setText(price);
            }
        });
    }
}
