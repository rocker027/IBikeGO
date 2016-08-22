package com.coors.ibikego.blog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.daovo.BlogVO;
import com.coors.ibikego.Common;
import com.coors.ibikego.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class BlogDetailMemberActivity extends AppCompatActivity {
    private BlogVO blog;
    private static final String TAG = "BlogDeleteMember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_detail_for_member);

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

    public void onClickEdit(View view) {
        Intent updateIntent = new Intent(BlogDetailMemberActivity.this,
                BlogUpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("blog",blog);
        updateIntent.putExtras(bundle);
        startActivity(updateIntent);
    }

    public void onClickDelete(View view) {
        Snackbar.make(view,"將會刪除本篇單車日誌",Snackbar.LENGTH_SHORT).setAction("是",new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (Common.networkConnected(BlogDetailMemberActivity.this)) {
                    String url = Common.URL + "blog/blogApp.do";
                    int count = 0;
                    try {
                        //先行定義時間格式
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        //取得現在時間
                        Date dt = new Date();
                        //透過SimpleDateFormat的format方法將Date轉為字串
                        String now = sdf.format(dt);
                        String blog_cre = now;
                        String blog_no = String.valueOf(blog.getBlog_no());
                        count = new BlogDeleteTask().execute(url, blog_no,blog_cre).get();
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    if (count == 0) {
                        Common.showToast(BlogDetailMemberActivity.this, R.string.msg_DeleteFail);
                    } else {
                        Common.showToast(BlogDetailMemberActivity.this, R.string.msg_DeleteSuccess);
                    }
                } else {
                    Common.showToast(BlogDetailMemberActivity.this, R.string.msg_NoNetwork);
                }            }
        }).show();

        finish();


    }
}