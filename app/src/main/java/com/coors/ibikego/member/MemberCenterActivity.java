package com.coors.ibikego.member;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.coors.ibikego.Common;
import com.coors.ibikego.ContactUsActivity;
import com.coors.ibikego.MainActivity;
import com.coors.ibikego.R;
import com.coors.ibikego.bikemode.BikeModeMainActivity;

public class MemberCenterActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private String mem_name, mem_mail, mem_acc;
    private Integer mem_no;
    private ImageView imageView;
    private TextView tvMemName,tvMemAcc,tvMemMail;
    public static final int FUNC_LOGIN = 1;
    private BootstrapButton btnlogIn,btnlogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_center);
        findViews();
        pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);

        initToolbar();
        initDate();


    }

    private void initDate() {
        String url = Common.URL + "member/memberApp.do" ;
        mem_no = pref.getInt("pref_memno", 0);
        mem_name = pref.getString("pref_name", "");
        mem_acc = pref.getString("pref_acc", "");
        mem_mail = pref.getString("pref_mail", "");

        boolean islogin = pref.getBoolean("login", false);
        if (islogin) {
            //按鈕
            btnlogIn.setVisibility(View.GONE);
            btnlogOut.setVisibility(View.VISIBLE);
            //功能按鈕
            LinearLayout g1 = (LinearLayout) findViewById(R.id.memFunc1);
            LinearLayout g2 = (LinearLayout) findViewById(R.id.memFunc1Title);
            LinearLayout g3 = (LinearLayout) findViewById(R.id.memFunc2);
            LinearLayout g4 = (LinearLayout) findViewById(R.id.memFunc2Title);
            g1.setVisibility(View.VISIBLE);
            g2.setVisibility(View.VISIBLE);
            g3.setVisibility(View.VISIBLE);
            g4.setVisibility(View.VISIBLE);


        }
        else {
            btnlogOut.setVisibility(View.GONE);
            btnlogIn.setVisibility(View.VISIBLE);
            //功能按鈕
            LinearLayout g1 = (LinearLayout) findViewById(R.id.memFunc1);
            LinearLayout g2 = (LinearLayout) findViewById(R.id.memFunc1Title);
            LinearLayout g3 = (LinearLayout) findViewById(R.id.memFunc2);
            LinearLayout g4 = (LinearLayout) findViewById(R.id.memFunc2Title);
            g1.setVisibility(View.GONE);
            g2.setVisibility(View.GONE);
            g3.setVisibility(View.GONE);
            g4.setVisibility(View.GONE);
            //title
            tvMemName.setText("尚未登入");
        }


        new MemberGetImageTask(imageView).execute(url,mem_no,250);
        tvMemName.setText(mem_name);
//        tvMemMail.setText("歡迎你");
    }

    private void findViews() {
        tvMemName = (TextView) findViewById(R.id.tvMemName);
//        tvMemMail = (TextView) findViewById(R.id.tvMemMail);
        imageView = (ImageView) findViewById(R.id.ivMemImage);
        btnlogIn = (BootstrapButton) findViewById(R.id.btn_login);
        btnlogOut = (BootstrapButton) findViewById(R.id.btn_logout);
    }

    public void onClickMyPost(View view) {
        startActivity(new Intent(MemberCenterActivity.this, MemberBlogManageActivity.class));

    }

//    public void onClickFriend(View view) {
//        Intent intent = new Intent(MemberCenterActivity.this,MemberFriendsActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("mem_no",mem_no);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }

    public void onClickContactUs(View view) {
        startActivity(new Intent(MemberCenterActivity.this, ContactUsActivity.class));

    }

    public void onClickLogOut(View view) {
        pref.edit().putBoolean("login", false)
                .remove("pref_memno")
                .remove("pref_acc")
                .remove("pref_pw")
                .remove("pref_name")
                .remove("pref_email")
                .remove("pref_key")
                .apply();
        finish();
    }

    //toolbar
    private void initToolbar() {
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("會員中心");

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

    public void onClickLogIn(View view) {
        Intent intent = new Intent(MemberCenterActivity.this,MemberLoginActivity.class);
        startActivityForResult(intent, FUNC_LOGIN);
    }

    public void onClickFavorInfo(View view) {
        startActivity(new Intent(MemberCenterActivity.this,MemberCollectBlogActivity.class));
    }

    public void onClickMemberInfo(View view) {
        startActivity(new Intent(MemberCenterActivity.this,MemberInfoActivity.class));
    }
}
