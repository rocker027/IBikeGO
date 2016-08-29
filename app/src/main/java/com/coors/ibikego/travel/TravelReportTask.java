package com.coors.ibikego.travel;

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
public class TravelReportTask extends AsyncTask<Object,Integer,String> {
    private final static String TAG = "TravelReportTask";
    private String ACTION = "reportTravel";
    private String url = Common.URL + "reportcollect/reportcollectAPP";

    @Override
    protected String doInBackground(Object... params) {
//        new BikeTrackInsertTask().execute(url,action ,json);

        String tra_no = params[0].toString();
        String mem_no = params[1].toString();
        String rep_cnt = params[2].toString();
        String result;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", ACTION);
        jsonObject.addProperty("tra_no", tra_no);
        jsonObject.addProperty("mem_no", mem_no);
        jsonObject.addProperty("rep_cnt", rep_cnt);


        try {
            result = getRemoteData(url, jsonObject.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }

        return result;
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
