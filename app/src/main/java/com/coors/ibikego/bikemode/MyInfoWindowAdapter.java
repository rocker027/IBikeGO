package com.coors.ibikego.bikemode;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coors.ibikego.Common;
import com.coors.ibikego.R;
import com.coors.ibikego.daovo.TravelVO;
import com.coors.ibikego.travel.TravelGetBitmapTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by user on 2016/8/26.
 */
public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final static String TAG = "TravelInfoAdapter";
    private View infoWindow;
    private TravelVO travelVO;


    MyInfoWindowAdapter(Context context, TravelVO travelVO) {
        infoWindow = LayoutInflater.from(context).inflate(
                R.layout.custom_info_window, null);
        this.travelVO = travelVO;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        ImageView imageView = (ImageView) infoWindow.findViewById(R.id.ivLogo);
        String url = Common.URL + "travel/travelApp";
        int id = travelVO.getTra_no();
        int imageSize = 250;
        Bitmap bitmap = null;
        try {
            // passing null and calling get() means not to run FindImageByIdTask.onPostExecute()
            bitmap = new TravelGetBitmapTask(null).execute(url, id, imageSize).get();
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
