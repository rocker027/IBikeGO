package com.coors.ibikego.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.daovo.SqlSearchVO;
import com.coors.ibikego.daovo.TravelVO;
import com.coors.ibikego.travel.TravelCollectTask;
import com.coors.ibikego.travel.TravelGetImageTask;
import com.coors.ibikego.travel.TravelReportActivity;
import com.coors.ibikego.travel.TravelShowMapActivity;

public class TravelSearchDelActivity extends AppCompatActivity {
    private PopupWindow popupWindow;
    //    private TravelVO travelVO;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_search_del);
        pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
        initToolbar();

        SqlSearchVO travelVO = (SqlSearchVO) getIntent().getExtras().getSerializable("travelVO");

        final int tra_no = travelVO.getTra_no();

        final String url = Common.URL + "travel/travelApp";
        final ImageView ivDetail = (ImageView) findViewById(R.id.ivDetail);
        try {
            new TravelGetImageTask(ivDetail).execute(url,tra_no, 800).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ivDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater mLayoutInflater = LayoutInflater.from(TravelSearchDelActivity.this);
                View contentView = mLayoutInflater.inflate(R.layout.pop_windows, null);

                popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                ImageView ivpop = (ImageView) contentView.findViewById(R.id.ivPop);

                if (Common.networkConnected(TravelSearchDelActivity.this)) {
//            String url = Common.URL + "travel/travelApp";
//            Integer tra_no = travelVO.getTra_no();
                    try {
//                        DisplayMetrics dm = new DisplayMetrics();
//                        TravelDetailActivity.this.getWindowManager().getDefaultDisplay().getMetrics(dm);
//                        int imageSize = dm.widthPixels;
                        int imageSize = 1500;
                        new TravelGetImageTask(ivpop).execute(url, tra_no, imageSize).get();
                    } catch (Exception e) {
//                Log.e(TAG, e.toString());
                    }
                } else {
//                    Common.showToast(this, R.string.msg_NoNetwork);
                }


                ColorDrawable cd = new ColorDrawable(0x000000);
                popupWindow.setBackgroundDrawable(cd);
//产生背景变暗效果
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.4f;
                getWindow().setAttributes(lp);

                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation((View) ivDetail.getParent(), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

                popupWindow.update();
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

                    //在dismiss中恢复透明度
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
            }
        });


        travelVO = (SqlSearchVO) getIntent().getExtras().getSerializable("travelVO");
//        TravelVO travelVO = new Trav;
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
        Integer mem_no = pref.getInt("pref_memno", 0);
        try {
            String resule = new TravelCollectTask().execute(tra_no, mem_no).get();
            Toast.makeText(this, resule, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void onTravelReport(View view) {
//        TravelVO travelVO = (TravelVO) getIntent().getExtras().getSerializable("travelVO");
//        Intent intent  = new Intent(Tr.this,TravelReportActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("travelVO", travelVO);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }
}
