package com.coors.ibikego.member;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.daovo.MemberVO;

import java.util.concurrent.ExecutionException;

public class MemberInfoActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private String mem_name, mem_mail, mem_acc,mem_tel,mem_add,mem_nickName;
    private Integer mem_no;
    private ImageView imageView;
    private TextView tvMemName,tvMemAcc,tvMemMail,tvMemAdd,tvMemTel,tvMemNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_info);
        pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
        findViews();
        initToolbar();

    }

    //toolbar
    private void initToolbar() {
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("個人資訊");

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    //toolbar 返回鍵
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


    private void findViews() {
        tvMemName = (TextView) findViewById(R.id.tvMemName);
        tvMemAcc = (TextView) findViewById(R.id.tvMemAcc);
        tvMemNickName = (TextView) findViewById(R.id.tvMemNickName);
        tvMemAdd = (TextView) findViewById(R.id.tvMemAdd);
        tvMemTel = (TextView) findViewById(R.id.tvMemTel);
        tvMemMail = (TextView) findViewById(R.id.tvMemMail);
        imageView = (ImageView) findViewById(R.id.ivMemImage);
        mem_no = pref.getInt("pref_memno", 0);
        MemberVO memberVO = null;
        try {
            memberVO = new MemberGetOneTask().execute(memberVO,mem_no).get();
            String url = Common.URL + "member/memberApp.do" ;
            new MemberGetImageTask(imageView).execute(url,mem_no,250);

        } catch (Exception e) {
            e.printStackTrace();
        }
        tvMemName.setText(memberVO.getMem_name());
        tvMemAcc.setText("會員帳號 : "+memberVO.getMem_acc());

//        if(memberVO.getMem_acc() == null) {
            tvMemNickName.setText("會員暱稱 : 尚未填寫");
//        }else{
//            tvMemNickName.setText("會員暱稱 : "+memberVO.getMem_nickname());
//
//        }

//        if(memberVO.getMem_acc() == null) {
            tvMemAdd.setText("聯絡地址 : 尚未填寫");
//        }else{
//            tvMemAdd.setText("聯絡地址 : " + memberVO.getMem_add());

//        }

//        if(memberVO.getMem_acc() == null) {
            tvMemTel.setText("聯絡電話 : 尚未填寫");
//        }else{
//            tvMemTel.setText("聯絡電話 : "+memberVO.getMem_phone());

//        }
        tvMemMail.setText("會員帳號 : "+memberVO.getMem_email());
    }

}
