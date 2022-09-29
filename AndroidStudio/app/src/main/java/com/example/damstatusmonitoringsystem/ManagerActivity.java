package com.example.damstatusmonitoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        Intent serviceIntent = new Intent(this, ManagerAlarmService.class);
        startService(serviceIntent);

        Button BTNWaterLevel = (Button) findViewById(R.id.button_waterLevel);
        BTNWaterLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WaterLevelMonitoringActivity.class);
                startActivity(intent);
            }
        });

        Button BTNWaterQuality = (Button) findViewById(R.id.button_waterQuality);
        BTNWaterQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WaterQualityMonitoringActivity.class);
                startActivity(intent);
            }
        });

        Button BTNGreenTide = (Button) findViewById(R.id.button_greenTide);
        BTNGreenTide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GreenTideMonitoringActivity.class);
                startActivity(intent);
            }
        });
    }
}