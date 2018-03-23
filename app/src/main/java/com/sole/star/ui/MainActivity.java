package com.sole.star.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sole.star.R;
import com.sole.star.ui.greendao.GreenDaoTestActivity;

public class MainActivity extends AppCompatActivity {
    private Button mHzSeekBarBTN;
    private Button mInterpolatorBTN;
    private Button mPwdBTN;
    private Button mExpandBTN;
    private Button mGreenDaoTestBTN;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHzSeekBarBTN = findViewById(R.id.btn_main_hz_seek_bar);
        mHzSeekBarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HZSeekBarActivity.class));
            }
        });

        mInterpolatorBTN = findViewById(R.id.btn_main_interpolator_test);
        mInterpolatorBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InterpolatorTestActivity.class));
            }
        });

        mPwdBTN = findViewById(R.id.btn_main_pwd_test);
        mPwdBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PwdActivity.class));
            }
        });

        mExpandBTN = findViewById(R.id.btn_main_expandable_text);
        mExpandBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExpandableTextActivity.class));
            }
        });

        mGreenDaoTestBTN = findViewById(R.id.btn_main_green_dao_test);
        mGreenDaoTestBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GreenDaoTestActivity.class));
            }
        });
    }
}
