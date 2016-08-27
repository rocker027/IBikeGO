package com.coors.ibikego.blog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coors.ibikego.R;
import com.coors.ibikego.daovo.BlogVO;

import java.util.concurrent.ExecutionException;

public class BlogReportActivity extends AppCompatActivity {
    private TextView tvTarget;
    private EditText etContent;
    private BlogVO blogVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_report);
        blogVO = (BlogVO) getIntent().getExtras().getSerializable("blog");
        tvTarget = (TextView) findViewById(R.id.tvTarget);
        tvTarget.setText(blogVO.getBlog_title());


    }

    public void onBlogReport(View view) {
        etContent = (EditText) findViewById(R.id.etContent);
        Integer blog_no = blogVO.getBlog_no();
        Integer mem_no = blogVO.getMem_no();
        String rep_cnt = etContent.getText().toString().trim();
        try {
            String resule = new BlogReportTask().execute(blog_no, mem_no, rep_cnt).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBlogReportCancel(View view) {
        finish();
    }
}
