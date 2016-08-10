package com.coors.ibikego.member;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;

public class MemberLostPwActivity extends AppCompatActivity {
    private final static String TAG = "MemberCheckTask";
    private EditText etLostAcc,etLostMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_lost_pw);
        findViews();
    }

    private void findViews() {
        etLostAcc = (EditText) findViewById(R.id.etLostAcc);
        etLostMail = (EditText) findViewById(R.id.etLostMail);

    }

    public void onClickLostPw(View view) {
        String mem_acc = etLostAcc.getText().toString().trim();
        String mem_email = etLostMail.getText().toString().trim();

        if (Common.networkConnected(this)) {
            String url = Common.URL + "member/memberApp.do";
//            String imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
            String action = "lostPw";
            try {
                new MemberCheckTask().execute(url, action, mem_acc, mem_email).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
        finish();
    }

    public void onClickLostRes(View view) {
        etLostAcc.setText("");
        etLostMail.setText("");
    }

    public void onClickMagic(View view) {
        etLostAcc.setText("nodeKing");
        etLostMail.setText("coorsray@gmail.com");
    }
}
