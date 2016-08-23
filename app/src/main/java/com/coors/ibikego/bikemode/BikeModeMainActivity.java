package com.coors.ibikego.bikemode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;

public class BikeModeMainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bike_mode_main);
        pref = getSharedPreferences(Common.PREF_FILE,
                MODE_PRIVATE);

    }

    public void onClick(View view) {
        startActivity(new Intent(this,BikeTrackActivity.class));
    }

    public void onClick2(View view) {
        startActivity(new Intent(this,BikeTrackListActivity.class));
    }

    public void onClickGroupKey(View view) {
        startActivity(new Intent(this,BikeGroupKeyActivity.class));
    }

    public void onClickGroupMem(View view) {
        key = pref.getString("pref_key", "").trim();
        if(key.isEmpty() || "".equals(key)){
            Toast.makeText(this,"目前沒有建立或加入連線",Toast.LENGTH_SHORT).show();
        }else {
            startActivity(new Intent(BikeModeMainActivity.this,BikeGroupRoomActivity.class));
        }

    }
}
