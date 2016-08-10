package com.coors.ibikego.blog;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.BlogVO;
import com.coors.ibikego.Common;
import com.coors.ibikego.R;

import java.util.concurrent.ExecutionException;


public class BlogDetailActivity extends AppCompatActivity {
    private BlogVO blog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_detail);

        initToolbar();


        blog = (BlogVO) getIntent().getExtras().getSerializable("blog");

        int blog_no = blog.getBlog_no();

        String url = Common.URL + "blog/blogApp.do";
        ImageView ivDetail = (ImageView) findViewById(R.id.ivDetail);
        try {
            new BlogGetImageTask(ivDetail).execute(url,blog_no, 250).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        TextView tvContent = (TextView) findViewById(R.id.tvContent);
//        imageView.setImageResource(blog.getImageId());
        tvTitle.setText(blog.getBlog_title());
        tvTime.setText(blog.getBlog_cre().toString());
        tvContent.setText(blog.getBlog_content());

    }

    private void initToolbar() {
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickBlogFavor(View view) {

    }

    public void onClickBlogReport(View view) {
    }
}