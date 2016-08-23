package com.coors.ibikego.bikemode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.RanPwd;
import com.coors.ibikego.daovo.GroupBikeVO;
import com.coors.ibikego.daovo.GroupDetailsVO;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class BikeGroupKeyActivity extends AppCompatActivity {
    private EditText etKeyIn;
    private TextView tvGroupKey;
    private String key;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bike_group_key);
        pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);
        findViews();

    }

    private void findViews() {
        etKeyIn = (EditText) findViewById(R.id.etGroupKeyIn);
        tvGroupKey = (TextView) findViewById(R.id.tvGroupKey);

    }

    public void onClickKeyCreate(View view) {
        RanPwd ranPwd = new RanPwd();
        key = ranPwd.RanPwd(8);
        tvGroupKey.setText(key);
        tvGroupKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Text Label", key);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void onClickKeyJoin(View view) {
        if(etKeyIn.length() <=0){
            etKeyIn.setError("請輸入連線Key");
            return;
        }else {
            key = etKeyIn.getText().toString();
            try {

                GroupDetailsVO groupDetailsVO = new GroupDetailsVO();
                groupDetailsVO.setMem_no(pref.getInt("pref_memno",0));
                GroupBikeVO groupBikeVO  = new BikeGetGroupNoTask().execute(key).get();
                groupDetailsVO.setGroupbike_no(groupBikeVO.getGroupbike_no());
                String objtostr = new Gson().toJson(groupDetailsVO);

                int isOK = new BikeGroupKeyJoinTask().execute(objtostr).get();
                if(isOK == 0){
                    Toast.makeText(this,"加入失敗，請確認連線Key是否正確",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"加入成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BikeGroupKeyActivity.this,BikeGroupRoomActivity.class));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void onClickKeySummit(View view) {
        try {
            Integer mem_no = pref.getInt("pref_memno", 0);
            int isOK = new BikeGroupKeyCreateTask().execute(mem_no,key).get();
            if(isOK == 0){
                Toast.makeText(this,"建立失敗，請確認網路連線是否正常",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,"建立成功",Toast.LENGTH_SHORT).show();
                pref.edit().putString("pref_key", key).apply();

//                Toast.makeText(this,pref.getString("pref_key", ""),Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
