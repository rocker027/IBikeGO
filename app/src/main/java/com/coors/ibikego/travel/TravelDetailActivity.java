package com.coors.ibikego.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.blog.BlogCollectTask;
import com.coors.ibikego.daovo.TravelVO;

public class TravelDetailActivity extends AppCompatActivity {
//    private TravelVO travelVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_detail);

        initToolbar();

        TravelVO travelVO = (TravelVO) getIntent().getExtras().getSerializable("travelVO");

        int tra_no = travelVO.getTra_no();

        String url = Common.URL + "travel/travelApp";
        ImageView ivDetail = (ImageView) findViewById(R.id.ivDetail);
        try {
            new TravelGetImageTask(ivDetail).execute(url,tra_no, 250).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        travelVO = (TravelVO) getIntent().getExtras().getSerializable("travelVO");
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        TextView tvTel = (TextView) findViewById(R.id.tvTel);
        TextView tvAdd = (TextView) findViewById(R.id.tvAdd);
        TextView tvContent = (TextView) findViewById(R.id.tvContent);
        tvTitle.setText(String.valueOf(travelVO.getTra_name()));
        tvTime.setText(String.valueOf(travelVO.getTra_cre()));
        tvTel.setText(String.valueOf(travelVO.getTra_tel()));
        tvAdd.setText(String.valueOf(travelVO.getTra_add()));
        tvContent.setText(String.valueOf(travelVO.getTra_content()));

    }

    private void initToolbar() {
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("景點");


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

    public void onClickNavi(View view) {
        TravelVO travelVO = (TravelVO) getIntent().getExtras().getSerializable("travelVO");
        Intent intent = new Intent(this, TravelShowMapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("travelVO",travelVO);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void onTravelFavor(View view) {
        TravelVO travelVO = (TravelVO) getIntent().getExtras().getSerializable("travelVO");
        Integer tra_no = travelVO.getTra_no();
        Integer mem_no = travelVO.getMem_no();
        try {
            String resule = new BlogCollectTask().execute(tra_no, mem_no).get();
            Toast.makeText(this, resule, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTravelReport(View view) {
        TravelVO travelVO = (TravelVO) getIntent().getExtras().getSerializable("travelVO");
        Intent intent  = new Intent(TravelDetailActivity.this,TravelReportActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("travelVO", travelVO);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}