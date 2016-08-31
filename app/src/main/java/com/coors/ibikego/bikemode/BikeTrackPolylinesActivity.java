package com.coors.ibikego.bikemode;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

import java.util.ArrayList;
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
    }

    private void initPoints() {
        RouteVO routeVO = (RouteVO) getIntent().getExtras().getSerializable("routeVO");
        Integer route_no = routeVO.getRoute_no();
        String url = null;
        List<RouteDetailsVO> detailsVOList = null;
        try {
            detailsVOList = new BikeGetOneDeatailsTask().execute(url,route_no).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(RouteDetailsVO detailsVO : detailsVOList){
            if(FromPoint == null){
                FromPoint = new LatLng(detailsVO.getRoute_det_lati(), detailsVO.getRoute_det_longi());
            }
            pointlist.add(new LatLng(detailsVO.getRoute_det_lati(),detailsVO.getRoute_det_longi()));
            ToPoint = new LatLng(detailsVO.getRoute_det_lati(), detailsVO.getRoute_det_longi());
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

    private void addMarkersToMap() {
        marker_ToPoint = map.addMarker(new MarkerOptions()
                .position(ToPoint)
                .title(getString(R.string.marker_title))
                .snippet(getString(R.string.marker_snippet))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

        marker_ToPoint = map.addMarker(new MarkerOptions()
                .position(FromPoint)
                .title(getString(R.string.marker_title))
                .snippet(getString(R.string.marker_snippet))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

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
