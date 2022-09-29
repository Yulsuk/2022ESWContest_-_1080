package com.example.damstatusmonitoringsystem;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
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

public class ManagerAlarmService extends Service {

    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        while (true) {
                            Log.e("Service", "Service is Running...");
                            boolean risk_flag = floodRiskCheck();
                            boolean rise_flag = waterLevelRiseCheck();
                            boolean change_flag = waterQualityChangeCheck();
                            if (risk_flag) {
                                floodRiskAlarm();
                                damOpenAlarm();
                            }
                            if (rise_flag) {
                                waterLevelRiseAlarm();
                            }
                            if (change_flag) {
                                waterQualityChangeAlarm();
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
            NotificationChannel channel = new NotificationChannel("damOpenAlarm", "damOpen", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "damOpenAlarm")
                .setSmallIcon(android.R.drawable.stat_notify_error)
                .setColor(0xFFFF0000)
                .setContentTitle("댐 개방 알림")
                .setContentText("댐이 개방되었습니다! 대비하십시오.");

        notification = builder.build();

        notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(2, notification);
    }

    public void waterLevelRiseAlarm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("damOpenAlarm", "damOpen", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "damOpenAlarm")
                .setSmallIcon(android.R.drawable.stat_notify_error)
                .setColor(0xFFFF0000)
                .setContentTitle("수위 상승 알림")
                .setContentText("수위가 상승이 감지되었습니다.");

        notification = builder.build();

        notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(3, notification);
    }

    public void waterQualityChangeAlarm() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("damOpenAlarm", "damOpen", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "damOpenAlarm")
                .setSmallIcon(android.R.drawable.stat_notify_error)
                .setColor(0xFFFF0000)
                .setContentTitle("수질 등급 변화 알림")
                .setContentText("수질 등급이 바뀌었습니다.");

        notification = builder.build();

        notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(4, notification);
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
                        if (floodR) {
                            number = line.replaceAll("[^0-9]", "");
                            count++;
                        }
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

    public boolean waterLevelRiseCheck() {
        boolean rise_flag = false;
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
                    float[] waterLevelRise = {0, 0};

                    while (true) {
                        line = reader.readLine();
                        boolean wrlF = line.contains("detectionWaterLevelRise");
                        String number = "";
                        if (wrlF) {
                            number = line.replaceAll("[^0-9]", "");
                            //waterLevelRise[count] = Integer.parseInt(number);
                            waterLevelRise[count] = Float.parseFloat(number);
                            count++;
                        }
                        if (count == 2) {
                            break;
                        }
                    }
                    if (waterLevelRise[0] - waterLevelRise[1] > 1.0) {
                        rise_flag = true;
                    }
                    //Log.d("test", aaa);

                    reader.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {
        }

        return rise_flag;
    }

    public boolean waterQualityChangeCheck() {
        boolean change_flag = false;
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
                    int count = 0;
                    int[] waterQualityChange = {0, 0};

                    while (true) {
                        line = reader.readLine();
                        boolean wqcF = line.contains("waterQuality");
                        String number = "";
                        //System.out.println(floodR);
                        if (wqcF) {
                            number = line.replaceAll("[^0-9]", "");
                            waterQualityChange[count] = Integer.parseInt(number);
                            count++;
                        }
                        if (count == 2) {
                            break;
                        }
                    }
                    if (waterQualityChange[0] != waterQualityChange[1]) {
                        change_flag = true;
                    }
                    //Log.d("test", aaa);

                    reader.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {
        }

        return change_flag;
    }
}