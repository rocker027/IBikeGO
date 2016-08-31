package com.coors.ibikego;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by user on 2016/8/30.
 */
public class GetAddressLatlng {
    private List<Address> addressList = null;
    private LatLng position;
    public LatLng getLatlng(Activity activity, String locationName) {
        Geocoder geocoder = new Geocoder(activity);
        int maxResults = 1;
        try {
            addressList = geocoder
                    .getFromLocationName(locationName, maxResults);
        } catch (IOException e) {
        }

        if (addressList == null || addressList.isEmpty()) {
        } else {
            Address address = addressList.get(0);

            LatLng position = new LatLng(address.getLatitude(),
                    address.getLongitude());

        }
        return position;
    }
}
