package com.coors.ibikego.attractions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.R;
import com.coors.ibikego.daovo.TravelVO;

public class AttractionsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attractions_detail);

        TravelVO attractions = (TravelVO) getIntent().getExtras().getSerializable("attractions");
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        TextView tvContent = (TextView) findViewById(R.id.tvContent);
//        imageView.setImageResource(blog.getImageId());
        tvTitle.setText(String.valueOf(attractions.getTra_no()));
        tvTime.setText(String.valueOf(attractions.getTra_no()));
        tvContent.setText(String.valueOf(attractions.getTra_no()));

    }
}