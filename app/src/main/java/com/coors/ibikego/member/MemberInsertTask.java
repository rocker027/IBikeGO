package com.coors.ibikego.member;

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
public class MemberInsertTask extends AsyncTask<Object,Integer,Integer>{
    private final static String TAG = "MemberInsertTask";

    @Override
    protected Integer doInBackground(Object... params) {
//      url, action, blog_no,mem_no, blog_title, blog_content, blog_cre, blog_del, imageBase64
        String url = params[0].toString();
        String action = params[1].toString();
        String mem_no = (String) params[2];
        String mem_acc = (String) params[3];
        String mem_pw = (String) params[4];
        String mem_name = (String) params[5];
        String mem_email = (String) params[6];
        String mem_reg = (String) params[7];
//        String result;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", action);
        jsonObject.addProperty("mem_no", mem_no);
        jsonObject.addProperty("mem_acc", mem_acc);
        jsonObject.addProperty("mem_pw", mem_pw);
        jsonObject.addProperty("mem_name", mem_name);
        jsonObject.addProperty("mem_email", mem_email);
        jsonObject.addProperty("mem_reg", mem_reg);

//        jsonObject.addProperty("blogVO", new Gson().toJson(blog));
        if (params[8] != null) {
            String mem_photo = params[8].toString();
            jsonObject.addProperty("mem_photo", mem_photo);
        }
        try {
            getRemoteData(url, jsonObject.toString());
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
