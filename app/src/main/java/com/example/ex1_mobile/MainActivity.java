package com.example.ex1_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton main_BTN_logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();
    }

    private void initViews() {
        main_BTN_logIn.setOnClickListener(v -> checkConditions());
    }

    private void findViews() {
        main_BTN_logIn = findViewById(R.id.main_BTN_logIn);
    }

    private void checkConditions() {
        if (isBatteryLevelSufficient() && isWifiEnabled() && isLandscapeOrientation()) {
            Toast.makeText(this, "Log in successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cannot log in. Check battery, Wi-Fi status, and orientation.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isBatteryLevelSufficient() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryPct = level * 100 / (float) scale;
            return batteryPct > 80;
        } else {
            Toast.makeText(this, "Unable to access battery information", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isWifiEnabled() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            return wifiManager.isWifiEnabled();
        } else {
            Toast.makeText(this, "Unable to access Wi-Fi information", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isLandscapeOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
