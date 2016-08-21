package com.coors.ibikego.bikemode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.coors.ibikego.R;

public class BikeModeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bike_mode_main);
    }

    public void onClick(View view) {
        startActivity(new Intent(this,BikeTrackActivity.class));
    }

    public void onClick2(View view) {
        startActivity(new Intent(this,BikeTrackListActivity.class));

    }
}
