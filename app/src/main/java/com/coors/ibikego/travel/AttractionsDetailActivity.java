package com.coors.ibikego.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.daovo.TravelVO;

public class AttractionsDetailActivity extends AppCompatActivity {
    private TravelVO travelVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_detail);

        initToolbar();

        travelVO = (TravelVO) getIntent().getExtras().getSerializable("travelVO");

        int tra_no = travelVO.getTra_no();

        String url = Common.URL + "travel/travelApp";
        ImageView ivDetail = (ImageView) findViewById(R.id.ivDetail);
        try {
            new TravelGetImageTask(ivDetail).execute(url,tra_no, 250).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TravelVO travelVO = (TravelVO) getIntent().getExtras().getSerializable("travelVO");
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

    }

    public void onClickNavi(View view) {
        travelVO = (TravelVO) getIntent().getExtras().getSerializable("travelVO");
        Intent intent = new Intent(this, TravelShowMapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("travelVO",travelVO);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}