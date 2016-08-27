package com.coors.ibikego.blog;

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
public class BlogReportTask extends AsyncTask<Object,Integer,String> {
    private final static String TAG = "BlogReportTask";
    private String ACTION = "reportBlog";
    private String url = Common.URL + "reportcollect/reportcollectAPP";

    @Override
    protected String doInBackground(Object... params) {
//        new BikeTrackInsertTask().execute(url,action ,json);

        String blog_no = params[0].toString();
        String mem_no = params[1].toString();
        String rep_cnt = params[1].toString();
        String result;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", ACTION);
        jsonObject.addProperty("blog_no", blog_no);
        jsonObject.addProperty("mem_no", mem_no);
        jsonObject.addProperty("rep_cnt", rep_cnt);


//        jsonObject.addProperty("blogVO", new Gson().toJson(blog));
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
