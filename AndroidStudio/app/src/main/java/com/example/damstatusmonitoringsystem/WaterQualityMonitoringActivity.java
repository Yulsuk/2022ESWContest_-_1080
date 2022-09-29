package com.example.damstatusmonitoringsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class WaterQualityMonitoringActivity extends AppCompatActivity {

    private static String TAG = "Project_WaterQualityMonitoringActivity";

    public static final String TAG_JSON = "webnautes";
    private static final String TAG_ID = "id";
    private static final String TAG_PH = "ph";
    private static final String TAG_DO = "dissolvedOxygen";
    private static final String TAG_UT = "waterUtilizationField";
    private static final String TAG_WQR = "waterQualityRating";


    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_quality_monitoring);


        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);
        mlistView = (ListView) findViewById(R.id.listView_main_list);
        mArrayList = new ArrayList<>();

        GetData task = new GetData();
        task.execute("http://220.69.240.24/android/getjson.php");

        Button refresh = (Button) findViewById(R.id.btn_waterQuality);
        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent refresh = new Intent(getApplicationContext(), WaterQualityMonitoringActivity.class);
                startActivity(refresh);
            }
        });
    }


    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(com.example.damstatusmonitoringsystem.WaterQualityMonitoringActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null) {
                mTextViewResult.setText(errorString);
            } else {
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    public void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String ph = item.getString(TAG_PH);
                String dissolvedOxygen = item.getString(TAG_DO);
                String util = item.getString(TAG_UT);
                String waterQualityRating = item.getString(TAG_WQR);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_PH, ph);
                hashMap.put(TAG_DO, dissolvedOxygen);
                hashMap.put(TAG_UT, util);
                hashMap.put(TAG_WQR, waterQualityRating);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    com.example.damstatusmonitoringsystem.WaterQualityMonitoringActivity.this, mArrayList, R.layout.water_quality_monitoring_item_list,
                    new String[]{TAG_ID, TAG_PH, TAG_DO, TAG_UT, TAG_WQR},
                    new int[]{R.id.textView_list_id, R.id.textView_list_ph,
                            R.id.textView_list_do, R.id.textView_list_util, R.id.textView_list_wqr}
            );

            mlistView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}