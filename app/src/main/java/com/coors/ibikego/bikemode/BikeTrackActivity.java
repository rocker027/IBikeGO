package com.coors.ibikego.bikemode;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.coors.ibikego.Common;
import com.coors.ibikego.GetStringDate;
import com.coors.ibikego.LatlngVO;
import com.coors.ibikego.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BikeTrackActivity extends AppCompatActivity implements OnMapReadyCallback {
    private final static int REQUEST_CODE_RESOLUTION = 1;
    private final static String TAG = "BikeTrack";
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private GoogleMap map;
    private Marker marker_NowPoint;
    private Marker marker_FromPoint;
    private LatLng FromPoint=null, NowPoint=null;
    private PolylineOptions polylineOptions;
    private ArrayList<LatLng> pointlist = new ArrayList<LatLng>();
//    private String[] details = null;
    private StringBuilder sb = null;
    private List<LatlngVO> latlngVOs = new LinkedList<LatlngVO>();
    private SharedPreferences pref ;
    private Marker markerGroup1,markerGroup2,markerGroup3,markerGroup4,markerGroup5;
    private List<LatLng> grouplist;
    private LatLng pos1,pos2,pos3,pos4,pos5;
    private List<Marker> markers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bike_track);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fmMap);
        mapFragment.getMapAsync(this);
        initPos();
        findToggleBtn();

    }

    private void initPos() {
        pos1 = new LatLng(24.151287, 121.625537);
        pos2 = new LatLng(23.791952, 120.861379);
        pos3 = new LatLng(25.033611, 121.565000);


    }

    private void findToggleBtn() {
        final ToggleButton btnGroupPos = (ToggleButton) findViewById(R.id.btnGroupPos);
        ToggleButton btnTravelPos = (ToggleButton) findViewById(R.id.btnTravelPos);

        btnGroupPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnGroupPos.isChecked()){
                    if(markerGroup1!=null){
                        markerGroup1.remove();
                    }
                    if(markerGroup2!=null){
                        markerGroup2.remove();
                    }

                    if(markerGroup3!=null){
                        markerGroup3.remove();
                    }
                    addMarkersG1ToMap();
                    addMarkersG2ToMap();
                    addMarkersG3ToMap();
                    Toast.makeText(BikeTrackActivity.this, "Group ON",Toast.LENGTH_SHORT).show();
                }else {
                    markerGroup1.remove();
                    markerGroup2.remove();
                    markerGroup3.remove();
//                      markerGroup3.remove();

                    Toast.makeText(BikeTrackActivity.this, "Group is off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTravelPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnGroupPos.isChecked()){
                    Toast.makeText(BikeTrackActivity.this, "Travel ON",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BikeTrackActivity.this, "Travel is off",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        setUpMap();
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


//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(NowPoint)
//                .zoom(16)
//                .build();
//        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        addMarkersG1ToMap();
//        addMarkersG2ToMap();
//        addMarkersG3ToMap();
//        addMarkersToMap();

//        map.setInfoWindowAdapter(new MyInfoWindowAdapter());
//
//        MyMarkerListener myMarkerListener = new MyMarkerListener();
//        map.setOnMarkerClickListener(myMarkerListener);
//        map.setOnInfoWindowClickListener(myMarkerListener);

    }

    //畫追蹤路徑線段
    private void addPolylinesPolygonsToMap() {
        polylineOptions = new PolylineOptions();
        polylineOptions.addAll(pointlist);
        polylineOptions.width(5).color(Color.MAGENTA);
        map.addPolyline(polylineOptions);
    }

    //增加起點與目前點的marker
    private void addMarkersToMap() {
        marker_NowPoint = map.addMarker(new MarkerOptions()
                .position(NowPoint)
                .title(getString(R.string.marker_title_now))
                .snippet(getString(R.string.marker_snippet))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

        marker_FromPoint = map.addMarker(new MarkerOptions()
                .position(FromPoint)
                .title(getString(R.string.marker_title_From))
                .snippet(getString(R.string.marker_snippet))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));

    }

    //增加Group成員的marker
    private void addMarkersG1ToMap() {
        MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.position(pos1);
        markerOpt.title("台北101");
        markerOpt.icon(BitmapDescriptorFactory.fromResource((R.drawable.cycling)));
        markerGroup1 = map.addMarker(markerOpt);
    }

    private void addMarkersG2ToMap() {
        MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.position(pos2);
        markerOpt.title("台北101");
        markerOpt.icon(BitmapDescriptorFactory.fromResource((R.drawable.cycling)));
        markerGroup2 = map.addMarker(markerOpt);
    }

    private void addMarkersG3ToMap() {
        MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.position(pos3);
        markerOpt.title("台北101");
        markerOpt.icon(BitmapDescriptorFactory.fromResource((R.drawable.cycling)));
        markerGroup3 = map.addMarker(markerOpt);
    }

    private void addMarkersG4ToMap() {
        MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.position(new LatLng(25.033611, 121.565000));
        markerOpt.title("台北101");
        markerOpt.icon(BitmapDescriptorFactory.fromResource((R.drawable.cycling)));
        markerGroup4 = map.addMarker(markerOpt);
    }

    private void addMarkersG5oMap() {
        MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.position(new LatLng(25.033611, 121.565000));
        markerOpt.title("台北101");
        markerOpt.icon(BitmapDescriptorFactory.fromResource((R.drawable.cycling)));
        markerGroup5 = map.addMarker(markerOpt);
    }


//       Marker aaa = map.addMarker(new MarkerOptions()
//                .position(new LatLng(24.9996,121.855))
//                .title(getString(R.string.marker_title_From))
////                .snippet(getString(R.string.marker_snippet))
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_navi_bike_24dp)));

//        markerGroup2 = map.addMarker(new MarkerOptions()
//                .position(pos2)
//                .title(getString(R.string.marker_title_From))
//                .snippet(getString(R.string.marker_snippet))
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_navi_bike_24dp)));




    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateLastLocationInfo(location);
            lastLocation = location;
            pref= getSharedPreferences(Common.PREF_FILE,
                    MODE_PRIVATE);
            Integer mem_no =pref.getInt("pref_memno",0);

            //當位置改變時
            if(lastLocation != null){
                //抓到第一個位置
                if(FromPoint == null){
                    FromPoint = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                }

                LatlngVO latlngVO = new LatlngVO();
                latlngVO.setLat(lastLocation.getLatitude());
                latlngVO.setLng(lastLocation.getLongitude());
                latlngVO.setMem_no(mem_no);
                latlngVO.setTime(lastLocation.getTime());
                latlngVO.setAltitude(lastLocation.getAltitude());
                latlngVO.setSpeed(lastLocation.getSpeed());
                latlngVO.setRoute_time(new GetStringDate().getDate_cre());

                latlngVOs.add(latlngVO);




                NowPoint = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                pointlist.add(NowPoint);

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 16));
                addPolylinesPolygonsToMap();

            }


        }
    };

    private GoogleApiClient.ConnectionCallbacks connectionCallbacks =
            new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    Log.i(TAG, "GoogleApiClient connected");
                    if (ActivityCompat.checkSelfPermission(BikeTrackActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BikeTrackActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    lastLocation = LocationServices.FusedLocationApi
                            .getLastLocation(googleApiClient);

                    LocationRequest locationRequest = LocationRequest.create()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(5)
                            .setSmallestDisplacement(50);
                    LocationServices.FusedLocationApi.requestLocationUpdates(
                            googleApiClient, locationRequest, locationListener);
                }

                @Override
                public void onConnectionSuspended(int i) {
                    showToast(R.string.msg_GoogleApiClientConnectionSuspended);
                }
            };

    private GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener =
            new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult result) {
                    showToast(R.string.msg_GoogleApiClientConnectionFailed);
                    if (!result.hasResolution()) {
                        GooglePlayServicesUtil.getErrorDialog(
                                result.getErrorCode(),
                                BikeTrackActivity.this,
                                0
                        ).show();
                        return;
                    }
                    try {
                        result.startResolutionForResult(
                                BikeTrackActivity.this,
                                REQUEST_CODE_RESOLUTION);
                    } catch (IntentSender.SendIntentException e) {
                        Log.e(TAG, "Exception while starting resolution activity");
                    }
                }
            };








    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(connectionCallbacks)
                    .addOnConnectionFailedListener(onConnectionFailedListener)
                    .build();
        }
        googleApiClient.connect();
    }

    @Override
    protected void onPause() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_RESOLUTION) {
                googleApiClient.connect();
            }
        }
    }

    private void updateLastLocationInfo(Location lastLocation) {
        String message = "";
        message += "The Information of the Last Location \n";

        if (lastLocation == null) {
            showToast(R.string.msg_LastLocationNotAvailable);
            return;
        }

        Date date = new Date(lastLocation.getTime());
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String time = dateFormat.format(date);
        message += "fix time: " + time + "\n";

        message += "latitude: " + lastLocation.getLatitude() + "\n";
        message += "longitude: " + lastLocation.getLongitude() + "\n";
        message += "accuracy (meters): " + lastLocation.getAccuracy() + "\n";
        message += "altitude (meters): " + lastLocation.getAltitude() + "\n";
        message += "bearing (horizontal direction- in degrees): "
                + lastLocation.getBearing() + "\n";
        message += "speed (meters/second): " + lastLocation.getSpeed() + "\n";

//        tvLastLocation.setText(message);
    }

    private boolean lastLocationFound() {
        if (lastLocation == null) {
            showToast(R.string.msg_LocationNotAvailable);
            return false;
        }
        return true;
    }

    private void showToast(int messageResId) {
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        askPermissions();
    }

    private static final int REQ_PERMISSIONS = 0;

    // New Permission see Appendix A
    private void askPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        Set<String> permissionsRequest = new HashSet<>();
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsRequest.add(permission);
            }
        }

        if (!permissionsRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionsRequest.toArray(new String[permissionsRequest.size()]),
                    REQ_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_PERMISSIONS:
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        String text = getString(R.string.text_ShouldGrant);
                        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }

                break;
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

    public void onClickRec(View view) {
        EditText etTitile = (EditText) findViewById(R.id.et_TrackTitile);
        String trackTitle = etTitile.getText().toString().trim();
        if(trackTitle.length() <= 0 || trackTitle.isEmpty()){
            etTitile.setError("請輸入本次主題名稱");
        }

        String url = Common.URL + "route/routeApp";
        String action = "insertWithDetails";
        Gson gson = new Gson();
        Integer mem_no =pref.getInt("pref_memno",0);//        String json = gson.toJson(pointlist);
        String json = gson.toJson(latlngVOs);

        new BikeTrackInsertTask().execute(url,action,json,mem_no,trackTitle);
    }
}
