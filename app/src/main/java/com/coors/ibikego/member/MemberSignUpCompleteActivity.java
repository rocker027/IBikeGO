package com.coors.ibikego.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coors.ibikego.R;

public class MemberSignUpCompleteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_sign_up_complete);
    }

    public void onClickReturnLogin(View view) {
        startActivity(new Intent(this,MemberLoginActivity.class));
    }
}
