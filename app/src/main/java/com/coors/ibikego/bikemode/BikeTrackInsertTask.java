package com.coors.ibikego.bikemode;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cuser on 2016/8/7.
 */
public class BikeTrackInsertTask extends AsyncTask<Object,Integer,Integer>{
    private final static String TAG = "BikeTrackInsertTask";

    @Override
    protected Integer doInBackground(Object... params) {
//        new BikeTrackInsertTask().execute(url,action ,json);

        String url = params[0].toString();
        String action = params[1].toString();
//        BlogVO blog = (BlogVO) params[2];
        String json = params[2].toString();
        String result;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", action);
        jsonObject.addProperty("json", json);

//        jsonObject.addProperty("blogVO", new Gson().toJson(blog));
        try {
            result = getRemoteData(url, jsonObject.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }

        return null;
    }

    private String getRemoteData(String url, String jsonOut) throws IOException {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setDoInput(true); // allow inputs
        connection.setDoOutput(true); // allow outputs
        connection.setUseCaches(false); // do not use a cached copy
        connection.setRequestMethod("POST");
        connection.setRequestProperty("charset", "UTF-8");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        bw.write(jsonOut);
        Log.d(TAG, "jsonOut: " + jsonOut);
        bw.close();

        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } else {
            Log.d(TAG, "response code: " + responseCode);
        }
        connection.disconnect();
        Log.d(TAG, "jsonIn: " + sb);
        return sb.toString();
    }
}
