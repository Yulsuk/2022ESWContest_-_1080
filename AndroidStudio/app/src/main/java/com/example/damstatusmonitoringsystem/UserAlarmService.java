package com.example.damstatusmonitoringsystem;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserAlarmService extends Service {

    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        while (true) {
                            Log.e("UserAlarmService", "UserAlarmService is Running...");
                            boolean risk_flag = floodRiskCheck();
                            if (risk_flag) {
                                floodRiskAlarm();
                                damOpenAlarm();
                            }
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void floodRiskAlarm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("myCh", "My Channel", NotificationManager.IMPORTANCE_LOW);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myCh")
                .setSmallIcon(android.R.drawable.stat_notify_error)
                .setColor(0xFFFF0000)
                .setContentTitle("홍수 발생 알림")
                .setContentText("홍수가 발생했습니다! 신속히 대피하십시오!");

        notification = builder.build();

        notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(1, notification);
    }

    public void damOpenAlarm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("myCh", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myCh")
                .setSmallIcon(android.R.drawable.stat_notify_error)
                .setColor(0xFFFF0000)
                .setContentTitle("댐 개방 알림")
                .setContentText("댐이 개방되었습니다! 대비하십시오.");

        notification = builder.build();

        notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(2, notification);
    }

    public boolean floodRiskCheck() {
        boolean risk_flag = false;
        try {
            URL url = new URL("http://220.69.240.24/android/getjson.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");

                int resCode = conn.getResponseCode();

                if (resCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    String aaa = "";
                    int count = 0;

                    while (true) {
                        line = reader.readLine();
                        boolean floodR = line.contains("floodRisk");
                        String number = "";
                        System.out.println("floodR : "+floodR);
                        if (floodR) {
                            number = line.replaceAll("[^0-9]", "");
                            count++;
                        }
                        System.out.println("count : "+count);
                        if (number.equals("1")) {
                            risk_flag = true;
                        }
                        System.out.println("risk_flag : "+risk_flag);

                        if (count == 2) {
                            break;
                        }
                    }
                    //Log.d("test", aaa);

                    reader.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {
        }

        return risk_flag;
    }

}