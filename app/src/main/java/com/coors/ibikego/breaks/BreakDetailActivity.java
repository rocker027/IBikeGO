package com.coors.ibikego.breaks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.R;
import com.coors.ibikego.TravelVO;

public class BreakDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.break_detail);
        TravelVO breakpoint = (TravelVO) getIntent().getExtras().getSerializable("break");
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        TextView tvContent = (TextView) findViewById(R.id.tvContent);
//        imageView.setImageResource(blog.getImageId());
        tvTitle.setText(String.valueOf(breakpoint.getTra_no()));
        tvTime.setText(String.valueOf(breakpoint.getTra_no()));
        tvContent.setText(String.valueOf(breakpoint.getTra_no()));

    }
}
