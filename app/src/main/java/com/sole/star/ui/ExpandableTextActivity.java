package com.sole.star.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sole.star.R;
import com.star.mylibrary.view.ExpandableTextView;

public class ExpandableTextActivity extends AppCompatActivity {
    private ExpandableTextView mExpandableTextTV;
    private TextView mExpandTV;
    private TextView mCollapseTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_text);

        mExpandableTextTV = findViewById(R.id.expand_text_view);
        mExpandTV = findViewById(R.id.tv_expandable_expand);
        mCollapseTV = findViewById(R.id.tv_expandable_collapse);



        mExpandTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCollapseTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
