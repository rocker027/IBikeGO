package com.coors.ibikego.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.google.gson.Gson;

public class MemberLostPwActivity extends AppCompatActivity {
    private final static String TAG = "MemberCheckTask";
    private EditText etLostAcc,etLostMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_lost_pw);
        findViews();
        initToolbar();
    }

    private void findViews() {
        etLostAcc = (EditText) findViewById(R.id.etLostAcc);
        etLostMail = (EditText) findViewById(R.id.etLostMail);

    }

    public void onClickLostPw(View view) {
        String mem_acc = etLostAcc.getText().toString().trim();
        String mem_email = etLostMail.getText().toString().trim();

        if(mem_acc.length()==0){
            etLostAcc.setError("請輸入帳號!!");
            return;
        }

        if(mem_email.length()==0){
            etLostAcc.setError("請輸入電子信箱!!");
            return;
        }

        if (Common.networkConnected(this)) {
            String url = Common.URL + "member/memberApp.do";
//            String imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
            String action = "lostPw";
            try {

               String str =  new MemberCheckTask().execute(url, action, mem_acc, mem_email).get();


                if("\"noMatch\"".equals(str)){
                    Toast.makeText(this, "帳號與電子信箱不相符，請重新確認", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(view,"帳號與電子信箱不相符，請重新確認", Snackbar.LENGTH_LONG).show();
                }

                if("\"Match\"".equals(str)) {
                    Toast.makeText(this, "已發送新密碼至電子信箱", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
//        finish();
    }

    public void onClickLostReset(View view) {
        etLostAcc.setText("");
        etLostMail.setText("");
    }

    public void onClickMagic(View view) {
        etLostAcc.setText("nodeKing");
        etLostMail.setText("coorsray@gmail.com");
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
finish();
            }
        });
    }
}
