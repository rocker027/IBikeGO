package com.coors.ibikego.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.coors.ibikego.R;

public class MemberLoginActivity extends AppCompatActivity {
    private EditText etMemAcc;
    private EditText etMemPw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_login);

        findViews();
    }

    private void findViews() {
        etMemAcc = (EditText) findViewById(R.id.et_memb_acc);
        etMemPw = (EditText) findViewById(R.id.et_memb_pw);
    }

    public void onClickLogin(View view) {
        String mem_acc = etMemAcc.getText().toString();
        String mem_pw = etMemPw.getText().toString();
        if("nodeking".equals(mem_acc) && ("1234".equals(mem_pw))){
            Toast.makeText(this,"Login OK",Toast.LENGTH_SHORT).show();
            getIntent().putExtra("LOGIN_USERID", mem_acc);
            getIntent().putExtra("LOGIN_PASSWD", mem_pw);
            setResult(RESULT_OK,getIntent());
            finish();
        }else{
            Snackbar.make(view,"Wrong Password!", Snackbar.LENGTH_SHORT).setAction("Reset your Password",new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MemberLoginActivity.this,MemberLostPwActivity.class));
                }
            }).show();
        }
    }

    public void onClickLostPw(View view) {
        startActivity(new Intent(MemberLoginActivity.this,MemberLostPwActivity.class));
    }

    public void onClickSignUp(View view) {
        startActivity(new Intent(MemberLoginActivity.this,MemberSignUpActivity.class));

    }
}
