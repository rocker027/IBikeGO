package com.coors.ibikego.blog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.BlogVO;
import com.coors.ibikego.R;


public class BlogDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_detail);

        BlogVO blog = (BlogVO) getIntent().getExtras().getSerializable("blog");
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        TextView tvContent = (TextView) findViewById(R.id.tvContent);
        imageView.setImageResource(blog.getImageId());
        tvTitle.setText(blog.getBlog_title());
        tvTime.setText(blog.getBlog_cre().toString());
        tvContent.setText(blog.getBlog_content());

    }
}