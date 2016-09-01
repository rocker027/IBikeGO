package com.coors.ibikego.member;

import android.os.AsyncTask;
import android.util.Log;

import com.coors.ibikego.Common;
import com.coors.ibikego.daovo.MemberVO;
import com.coors.ibikego.daovo.RelationshipVO;
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
 * Created by user on 2016/9/1.
 */
public class MemberGetFriendsTask extends AsyncTask<Object, Integer, List<RelationshipVO>> {
    private final static String TAG = "MemberGetFriendsAllTask";
    String urltoGegall = Common.URL + "relationship/relationshipApp";
    private final static String ACTION = "getMFollowRms";

    @Override
    protected List<RelationshipVO> doInBackground(Object... params) {
        String url = urltoGegall;
        String jsonIn;
        String mem_no = params[0].toString();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", ACTION);
        jsonObject.addProperty("mem_no", mem_no);
        try {
            jsonIn = getRemoteData(url, jsonObject.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MMM-dd").create();

        Type listType = new TypeToken<List<RelationshipVO>>() {
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
