package com.coors.ibikego.travel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coors.ibikego.R;
import com.coors.ibikego.blog.BlogReportTask;
import com.coors.ibikego.daovo.BlogVO;
import com.coors.ibikego.daovo.TravelVO;

public class TravelReportActivity extends AppCompatActivity {
//    private TextView tvTarget;
    private EditText etContent;
    private TravelVO travelVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_report);
        travelVO = (TravelVO) getIntent().getExtras().getSerializable("travelVO");
        TextView tvTarget = (TextView) findViewById(R.id.tvTarget);
        String title = travelVO.getTra_name();
        tvTarget.setText(title);
    }

    public void onBlogReport(View view) {
        etContent = (EditText) findViewById(R.id.etContent);
        Integer tra_no = travelVO.getTra_no();
        Integer mem_no = travelVO.getMem_no();
        String rep_cnt = etContent.getText().toString().trim();
        try {
            String resule = new TravelReportTask().execute(tra_no, mem_no, rep_cnt).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBlogReportCancel(View view) {
        finish();
    }
}
