package com.coors.ibikego.travel;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.Common;
import com.coors.ibikego.daovo.TravelVO;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.coors.ibikego.R;

public class TravelShowMapActivity extends AppCompatActivity {
    private final static String TAG = "TravelShowMapActivity";
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_show_map_activity);
        initMap();
        TravelVO travelVO = (TravelVO) this.getIntent().getExtras().getSerializable("travelVO");
        if (travelVO == null) {
            Common.showToast(TravelShowMapActivity.this, R.string.msg_NoTravelsFound);
        } else {
            showMap(travelVO);
        }
    }

    private void initMap() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fmMap)).getMap();
            if (map == null) {
                Common.showToast(this, R.string.msg_MapNotDisplayed);
                finish();
                return;
            }
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
        }
    }

    private void showMap(TravelVO travelVO) {
        LatLng position = new LatLng(travelVO.getTra_lati(), travelVO.getTra_longi());
        String snippet = getString(R.string.col_Name) + ": " + travelVO.getTra_name() + "\n" +
                getString(R.string.col_PhoneNo) + ": " + travelVO.getTra_tel() + "\n" +
                getString(R.string.col_Address) + ": " + travelVO.getTra_add();

        // focus on the spot
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(9)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);

        // add spot on the map
        map.addMarker(new MarkerOptions()
                .position(position)
                .title(travelVO.getTra_name())
                .snippet(snippet));

        map.setInfoWindowAdapter(new MyInfoWindowAdapter(this, travelVO));
    }

    private class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private View infoWindow;
        private TravelVO travelVO;


        MyInfoWindowAdapter(Context context, TravelVO travelVO) {
            infoWindow = LayoutInflater.from(context).inflate(
                    R.layout.travel_map_infowindow, null);
            this.travelVO = travelVO;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            ImageView imageView = (ImageView) infoWindow.findViewById(R.id.imageView);
            String url = Common.URL + "travel/travelApp";
            int id = travelVO.getTra_no();
            int imageSize = 400;
            Bitmap bitmap = null;
            try {
                // passing null and calling get() means not to run FindImageByIdTask.onPostExecute()
                bitmap = new TravelGetImageTask(null).execute(url, id, imageSize).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setImageResource(R.drawable.ic_insert_photo_black_24dp);
            }
            TextView tvTitle = (TextView) infoWindow.findViewById(R.id.tvTitle);
            tvTitle.setText(marker.getTitle());

            TextView tvSnippet = (TextView) infoWindow.findViewById(R.id.tvSnippet);
            tvSnippet.setText(marker.getSnippet());
            return infoWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }
}
