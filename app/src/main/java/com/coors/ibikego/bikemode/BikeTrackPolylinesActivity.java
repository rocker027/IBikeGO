package com.coors.ibikego.bikemode;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coors.ibikego.R;
import com.coors.ibikego.RouteDetailsVO;
import com.coors.ibikego.RouteVO;
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
            pointlist.add(new LatLng(detailsVO.getRoute_det_lati(),detailsVO.getRoute_det_longi()));
            ToPoint = new LatLng(detailsVO.getRoute_det_lati(), detailsVO.getRoute_det_longi());
        }

//        taroko = new LatLng(24.151287, 121.625537);
//        yushan = new LatLng(23.791952, 120.861379);
//        kenting = new LatLng(21.985712, 120.813217);
//        yangmingshan = new LatLng(25.091075, 121.559834);
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

//        marker_yushan = map.addMarker(new MarkerOptions().position(yushan)
//                .title(getString(R.string.marker_title_yushan))
//                .snippet(getString(R.string.marker_snippet_yushan))
//                .draggable(true));
//
//        marker_kenting = map.addMarker(new MarkerOptions().position(kenting)
//                .title(getString(R.string.marker_title_kenting))
//                .snippet(getString(R.string.marker_snippet_kenting))
//                .icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//
//        marker_yangmingshan = map.addMarker(new MarkerOptions()
//                .position(yangmingshan)
//                .title(getString(R.string.marker_title_yangmingshan))
//                .snippet(getString(R.string.marker_snippet_yangmingshan))
//                .icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    private void addPolylinesPolygonsToMap() {


//        Iterator<RouteDetailsVO> iterator = detailsVOList.iterator();
//
//        while (iterator.hasNext()){
//            RouteDetailsVO detailsVO = new RouteDetailsVO();
//            pointlist.add(new LatLng(detailsVO.getRoute_det_lati(),detailsVO.getRoute_det_longi()));
//        }


//        pointlist.add(new LatLng(24.2,120.1));
//        pointlist.add(new LatLng(24.2,120.11));
//        pointlist.add(new LatLng(24.2,120.111));
//        pointlist.add(new LatLng(24.2,120.1111));
//        pointlist.add(new LatLng(24.2,120.1113));
//        pointlist.add(new LatLng(24.2,120.1114));
//        pointlist.add(new LatLng(24.2,120.1115));
//        pointlist.add(new LatLng(24.2,120.1116));
//        pointlist.add(new LatLng(24.2,120.1117));
//        pointlist.add(new LatLng(24.2,120.1118));
//        pointlist.add(new LatLng(24.2,120.1119));
//        pointlist.add(new LatLng(24.2,120.1120));
//        pointlist.add(new LatLng(24.2,120.1121));

        polylineOptions = new PolylineOptions();
        polylineOptions.addAll(pointlist);
        polylineOptions.width(5).color(Color.MAGENTA);
        map.addPolyline(polylineOptions);




//        Polyline polyline = map.addPolyline(
//                new PolylineOptions()
//                        .add(yushan, yangmingshan, taroko)
//                        .width(5)
//                        .color(Color.MAGENTA)
//                        .zIndex(1));
//
//        polyline.setWidth(6);
//
//        map.addPolygon(
//                new PolygonOptions()
//                        .add(yushan, taroko, kenting)
//                        .strokeWidth(5)
//                        .strokeColor(Color.BLUE)
//                        .fillColor(Color.argb(200, 100, 150, 0)));
//
//        map.addCircle(
//                new CircleOptions()
//                        .center(yushan)
//                        .radius(100000)
//                        .strokeWidth(5)
//                        .strokeColor(Color.TRANSPARENT)
//                        .fillColor(Color.argb(100, 0, 0, 100)));
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
