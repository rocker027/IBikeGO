package com.coors.ibikego.bikemode;

import android.os.AsyncTask;
import android.util.Log;

import com.coors.ibikego.Common;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2016/8/27.
 */
public class BikeGroupUpdatePos extends AsyncTask<Object,Integer,Integer> {
    private final static String TAG = "BikeGroupUpdatePosTask";
    String url_this = Common.URL + "groupdetails/groupdetailsApp";
    private final static String ACTION = "updatePos";

    @Override
    protected Integer doInBackground(Object... params) {
        String url = url_this;
        String jsonIn;
        String mem_no = params[0].toString();
        String groupbike_no = params[1].toString();
        String lat = params[2].toString();
        String lng = params[3].toString();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", ACTION);
        jsonObject.addProperty("mem_no", mem_no);
        jsonObject.addProperty("groupbike_no", groupbike_no);
        jsonObject.addProperty("lat", lat);
        jsonObject.addProperty("lng", lng);
        try {
            jsonIn = getRemoteData(url, jsonObject.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }
        return Integer.parseInt(jsonIn);
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
