package com.coors.ibikego.bikemode;

import android.os.AsyncTask;
import android.util.Log;

import com.coors.ibikego.Common;
import com.coors.ibikego.daovo.RouteVO;
import com.coors.ibikego.daovo.SqlGroupDeatilsVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by user on 2016/8/21.
 */
public class BikeGroupPosFlashTask extends AsyncTask<Object,Integer,List<SqlGroupDeatilsVO>>{
    private final static String TAG = "BikeGetGroupPosTask";
    String url = Common.URL + "routedetails/routedetailsApp.do";
    private final static String ACTION = "getGroupPos";

    @Override
    protected List<SqlGroupDeatilsVO> doInBackground(Object... params) {
        String jsonIn;
        String groupbike_key = (String)params[0];
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", ACTION);
        jsonObject.addProperty("groupbike_key", groupbike_key);
        try {
            jsonIn = getRemoteData(url, jsonObject.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MMM-dd").create();

        Type listType = new TypeToken<List<SqlGroupDeatilsVO>>() {
        }.getType();
        return gson.fromJson(jsonIn, listType);
    }

    private String getRemoteData(String url, String jsonOut) throws IOException {
        StringBuilder jsonIn = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setDoInput(true); // allow inputs
        connection.setDoOutput(true); // allow outputs
        connection.setUseCaches(false); // do not use a cached copy
        connection.setRequestMethod("POST");
        connection.setRequestProperty("charset", "UTF-8");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        bw.write(jsonOut);
        Log.d(TAG, "jsonOut: " + jsonOut);
        Log.d(TAG, "URL: " + url);
        bw.close();

        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                jsonIn.append(line);
            }
        } else {
            Log.d(TAG, "response code: " + responseCode);
        }
        connection.disconnect();
        Log.d(TAG, "jsonIn: " + jsonIn);
        return jsonIn.toString();
    }

}
