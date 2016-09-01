package com.coors.ibikego.member;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.Common;
import com.coors.ibikego.ContactUsActivity;
import com.coors.ibikego.MainActivity;
import com.coors.ibikego.R;

public class MemberCenterActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private String mem_name, mem_mail, mem_acc;
    private Integer mem_no;
    private ImageView imageView;
    private TextView tvMemName,tvMemAcc,tvMemMail;

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

        new MemberGetImageTask(imageView).execute(url,mem_no,250);
        tvMemName.setText(mem_name);
//        tvMemMail.setText("歡迎你");
    }

    private void findViews() {
        tvMemName = (TextView) findViewById(R.id.tvMemName);
//        tvMemMail = (TextView) findViewById(R.id.tvMemMail);
        imageView = (ImageView) findViewById(R.id.ivMemImage);
    }

    public void onClickMemInfo(View view) {
    }

    public void onClickMyPost(View view) {
        startActivity(new Intent(MemberCenterActivity.this, MemberBlogManageActivity.class));

    }

    public void onClickFriend(View view) {
        Intent intent = new Intent(MemberCenterActivity.this,MemberFriendsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mem_no",mem_no);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickContactUs(View view) {
        startActivity(new Intent(MemberCenterActivity.this, ContactUsActivity.class));

    }

    public void onClickLogOut(View view) {
        pref.edit().putBoolean("login", false)
                .remove("pref_acc")
                .remove("pref_pw")
                .remove("pref_name")
                .remove("pref_email")
                .remove("pref_key")
                .remove("pref_memno")
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
}
