package com.coors.ibikego.bikemode;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coors.ibikego.R;
import com.coors.ibikego.daovo.RouteDetailsVO;
import com.coors.ibikego.daovo.RouteVO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BikeTrackPolylinesActivity extends AppCompatActivity implements
        OnMapReadyCallback {
    private final static String TAG = "BikeTrackPolylines";
    private GoogleMap map;
    private Marker marker_ToPoint;
    private Marker marker_yushan;
    private Marker marker_kenting;
    private Marker marker_yangmingshan;
    private LatLng taroko;
    private LatLng yushan;
    private LatLng kenting;
    private LatLng yangmingshan;
    PolylineOptions polylineOptions;
    private LatLng FromPoint, ToPoint;
    ArrayList<LatLng> pointlist = new ArrayList<LatLng>();
    RouteDetailsVO FristPoint,LastPoint;
    List<RouteDetailsVO> detailsVOList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bike_polyline_activity);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fmMap);
        mapFragment.getMapAsync(this);
        initPoints();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        setUpMap();
        showInfo();
    }

    private void initPoints() {
        RouteVO routeVO = (RouteVO) getIntent().getExtras().getSerializable("routeVO");
        Integer route_no = routeVO.getRoute_no();
        String url = null;

        try {
            detailsVOList = new BikeGetOneDeatailsTask().execute(url,route_no).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FromPoint =null;
        LastPoint = null;
        for(RouteDetailsVO detailsVO : detailsVOList){
            if(FromPoint == null){
                FromPoint = new LatLng(detailsVO.getRoute_det_lati(), detailsVO.getRoute_det_longi());
                FristPoint = detailsVO;
            }
            pointlist.add(new LatLng(detailsVO.getRoute_det_lati(),detailsVO.getRoute_det_longi()));
            ToPoint = new LatLng(detailsVO.getRoute_det_lati(), detailsVO.getRoute_det_longi());
            LastPoint = detailsVO;
        }

        Log.d(TAG, String.valueOf(pointlist.size()));
    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(ToPoint)
                .zoom(16)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        addMarkersToMap();

        map.setInfoWindowAdapter(new MyInfoWindowAdapter());

        MyMarkerListener myMarkerListener = new MyMarkerListener();
        map.setOnMarkerClickListener(myMarkerListener);
        map.setOnInfoWindowClickListener(myMarkerListener);

        addPolylinesPolygonsToMap();
    }

    private void showInfo() {
        //抓View
        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        TextView tvContent = (TextView) findViewById(R.id.tvContent);
        TextView tvSpeed = (TextView) findViewById(R.id.tvSpeed);
        TextView tvCal = (TextView) findViewById(R.id.tvCal);

        //算時間
        Long diff = LastPoint.getRoute_det_time() - FristPoint.getRoute_det_time();
        Long totalSec = diff / 1000;
        Long diffsec = diff / 1000 % 60 ;
        Long diffMinutes = diff / (60 * 1000) % 60;
        Long diffHours = diff / (60 * 60 * 1000) % 24;
        tvTime.setText("總時間 : " + diffHours +":"+diffMinutes+":"+diffsec);

        //算總距離
        float distence = 0;
        for(int i = 0; i< detailsVOList.size()-1 ;i++){
            RouteDetailsVO p1 = detailsVOList.get(i+1);
            RouteDetailsVO p2 = detailsVOList.get(i);
//            Location.distanceBetween(p1.getRoute_det_lati(),p1.getRoute_det_longi(),p2.getRoute_det_lati(),p2.getRoute_det_longi(),results);
            Location d1 = new Location("d1");
            Location d2 = new Location("d2");
            d1.setLatitude(p1.getRoute_det_lati());
            d1.setLongitude(p1.getRoute_det_longi());
            d2.setLatitude(p2.getRoute_det_lati());
            d2.setLongitude(p2.getRoute_det_longi());
            distence = distence + d2.distanceTo(d1)/1000;
        }
        NumberFormat formatter = new DecimalFormat("###.##");
        String dis =formatter.format(distence);
        tvContent.setText("總距離 : "+dis+" 公里");

        //速度
        double speed=(distence/totalSec)*60*60; //單位是km/hr
        String sp =formatter.format(speed);
        tvSpeed.setText("平均時速 : "+ sp +" 公里");

        //消耗熱量
        double cal = 0;
        if(speed <= 8.8){
            cal =  (3/60/60)*totalSec;
        }else if(speed <= 31){
            cal =  (4.7/60/60)*totalSec;
        }else {
            cal = (7.4/60/60)*totalSec;
        }
        String scal =formatter.format(cal);
        tvCal.setText("消耗熱量 : " + scal +"大卡");

    }

    private void addMarkersToMap() {
        marker_ToPoint = map.addMarker(new MarkerOptions()
                .position(ToPoint)
                .title(getString(R.string.marker_title))
                .snippet(getString(R.string.marker_snippet))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

//        marker_ToPoint = map.addMarker(new MarkerOptions()
//                .position(FromPoint)
//                .title(getString(R.string.marker_title))
//                .snippet(getString(R.string.marker_snippet))
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

    }

    private void addPolylinesPolygonsToMap() {

        polylineOptions = new PolylineOptions();
        polylineOptions.addAll(pointlist);
        polylineOptions.width(5).color(Color.MAGENTA);
        map.addPolyline(polylineOptions);


    }


    private class MyMarkerListener implements OnMarkerClickListener,
            OnInfoWindowClickListener {
        @Override
        public boolean onMarkerClick(final Marker marker) {
            return false;
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            Toast.makeText(BikeTrackPolylinesActivity.this, marker.getTitle(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    private class MyInfoWindowAdapter implements InfoWindowAdapter {
        private final View infoWindow;

        MyInfoWindowAdapter() {
            infoWindow = View.inflate(
                    BikeTrackPolylinesActivity.this,
                    R.layout.custom_info_window,
                    null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            int logoId;
            if (marker.equals(marker_yangmingshan)) {
                logoId = R.drawable.logo_yangmingshan;
            } else if (marker.equals(marker_ToPoint)) {
                logoId = R.drawable.logo_taroko;
            } else if (marker.equals(marker_yushan)) {
                logoId = R.drawable.logo_yushan;
            } else if (marker.equals(marker_kenting)) {
                logoId = R.drawable.logo_kenting;
            } else {
                logoId = 0;
            }

            ImageView iv_logo = ((ImageView) infoWindow
                    .findViewById(R.id.ivLogo));
            iv_logo.setImageResource(logoId);

            String title = marker.getTitle();
            TextView tv_title = ((TextView) infoWindow
                    .findViewById(R.id.tvTitle));
            tv_title.setText(title);

            String snippet = marker.getSnippet();
            TextView tv_snippet = ((TextView) infoWindow
                    .findViewById(R.id.tvSnippet));
            tv_snippet.setText(snippet);

            return infoWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }

    public void onClearMapClick(View view) {
        map.clear();
    }

    public void onResetMapClick(View view) {
        map.clear();
        addMarkersToMap();
        addPolylinesPolygonsToMap();
    }

}
