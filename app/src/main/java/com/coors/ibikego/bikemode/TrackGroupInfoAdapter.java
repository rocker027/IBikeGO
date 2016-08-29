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
import com.coors.ibikego.daovo.SqlGroupDeatilsVO;
import com.coors.ibikego.daovo.TravelVO;
import com.coors.ibikego.member.MemberGetImageTask;
import com.coors.ibikego.travel.TravelGetBitmapTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by user on 2016/8/26.
 */
public class TrackGroupInfoAdapter implements GoogleMap.InfoWindowAdapter {
    private final static String TAG = "TravelInfoAdapter";
    private View infoWindow;
    private SqlGroupDeatilsVO sqlGroupDeatilsVO;


    TrackGroupInfoAdapter(Context context, SqlGroupDeatilsVO sqlGroupDeatilsVO) {
        infoWindow = LayoutInflater.from(context).inflate(
                R.layout.trackgroup_infowindow, null);
        this.sqlGroupDeatilsVO = sqlGroupDeatilsVO;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        ImageView imageView = (ImageView) infoWindow.findViewById(R.id.ivLogo);
        String url = Common.URL + "member/memberApp.do";
        int id = sqlGroupDeatilsVO.getMem_no();
        int imageSize = 400;
        Bitmap bitmap = null;
        try {
            // passing null and calling get() means not to run FindImageByIdTask.onPostExecute()
            bitmap = new MemberGetImageTask(null).execute(url, id, imageSize).get();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.ic_navi_member_24dp);
        }
        TextView tvTitle = (TextView) infoWindow.findViewById(R.id.tvTitle);
        tvTitle.setText(marker.getTitle());
//        TextView tvSnippet = (TextView) infoWindow.findViewById(R.id.tvSnippet);
//        tvSnippet.setText(marker.getSnippet());
        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
