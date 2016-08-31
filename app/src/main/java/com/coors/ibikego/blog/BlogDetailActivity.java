package com.coors.ibikego.blog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coors.ibikego.daovo.BlogVO;
import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.daovo.SqlBlogVO;

import java.util.concurrent.ExecutionException;


public class BlogDetailActivity extends AppCompatActivity {
    private SqlBlogVO blog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_detail);

        initToolbar();


        blog = (SqlBlogVO) getIntent().getExtras().getSerializable("blog");

        int blog_no = blog.getBlog_no();

        String url = Common.URL + "blog/blogApp";
        ImageView ivDetail = (ImageView) findViewById(R.id.ivDetail);
        try {
            new BlogGetImageTask(ivDetail).execute(url,blog_no, 500).get();
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
        getSupportActionBar().setTitle("單車日誌");


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
        Integer blog_no = blog.getBlog_no();
        Integer mem_no = blog.getMem_no();
        try {
            String resule = new BlogCollectTask().execute(blog_no, mem_no).get();
            Toast.makeText(this, resule, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onClickBlogReport(View view) {
        Intent intent  = new Intent(BlogDetailActivity.this,BlogReportActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("blog", blog);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}