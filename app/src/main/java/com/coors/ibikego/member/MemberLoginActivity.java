package com.coors.ibikego.member;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.coors.ibikego.Common;
import com.coors.ibikego.MemberVO;
import com.coors.ibikego.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MemberLoginActivity extends AppCompatActivity {
    private final static String TAG = "MemberLoginTask";
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

        if(mem_acc.length()==0){
            etMemAcc.setError("請輸入帳號!!");
            return;
        }

        if(mem_pw.length()==0){
            etMemPw.setError("請輸入密碼!!");
            return;
        }

        if (Common.networkConnected(this)) {
            String url = Common.URL + "member/memberApp.do";
//            String imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
            String action = "login";
            try {

                String str =  new MemberLoginTask().execute(url, action, mem_acc, mem_pw).get();


                if("\"noMatch\"".equals(str)){
                    Toast.makeText(this, "帳號與密碼不相符，請重新確認", Toast.LENGTH_SHORT).show();
                }
                else {
                    Gson gson = new Gson();
                    MemberVO memberVO = gson.fromJson(str, MemberVO.class);
//                    JsonObject jObject = gson.fromJson(str, JsonObject.class);
//                    String memberJson = jObject.get("memberVO").getAsString();

                    Integer memno = memberVO.getMem_no();
                    String userAcc = memberVO.getMem_acc();
                    String password = memberVO.getMem_pw();
                    String userName = memberVO.getMem_name();
                    String userMail = memberVO.getMem_email();

//                    User user = new User(
//                            jObject.get("mem_no").getAsString(),
//                            jObject.get("password").getAsString(),
//                            Base64.decode(jObject.get("imageBase64").getAsString(), Base64.DEFAULT)
//                    );
                        SharedPreferences pref = getSharedPreferences(Common.PREF_FILE,
                                MODE_PRIVATE);
                        pref.edit()
                                .putBoolean("login", true)
                                .putInt("pref_memno", memno)
                                .putString("pref_acc", userAcc)
                                .putString("pref_pw", password)
                                .putString("pref_name", userName)
                                .putString("pref_mail", userMail)
                                .apply();
                        setResult(RESULT_OK,getIntent());
                        finish();
                    }


            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }

//        if("nodeking".equals(mem_acc) && ("1234".equals(mem_pw))){
//            Toast.makeText(this,"Login OK",Toast.LENGTH_SHORT).show();
//            getIntent().putExtra("LOGIN_USERID", mem_acc);
//            getIntent().putExtra("LOGIN_PASSWD", mem_pw);
//            setResult(RESULT_OK,getIntent());
//            finish();
//        }else{
//
//        }
    }

    public void onClickLostPw(View view) {
        startActivity(new Intent(MemberLoginActivity.this,MemberLostPwActivity.class));
    }

    public void onClickSignUp(View view) {
        startActivity(new Intent(MemberLoginActivity.this,MemberSignUpActivity.class));
    }
}
