package com.allinonewhatstools.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.allinonewhatstools.test.Ads.Banner_Ads;
import com.allinonewhatstools.test.Ads.Inter_Ads;
import com.allinonewhatstools.test.Ads.NativeBanner_Ads;
import com.allinonewhatstools.test.Ads.Native_Ads;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'test' library on application startup.
//    static {
//        System.loadLibrary("test");
//    }
    @Override
    protected void onResume() {
        super.onResume();
//        Native_Ads.NativeFinalLoad(findViewById(R.id.fl_adplaceholder),findViewById(R.id.ad_native_big));
        NativeBanner_Ads.NativeFinalLoad(findViewById(R.id.fl_adplaceholder1), findViewById(R.id.ad_native_banner));
        Banner_Ads.BanerLoad(findViewById(R.id.bannerAds));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void checkOverlayPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                // send user to the device settings
                Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(myIntent);
            }
        }
    }


    public void cccc(View view) {

        Inter_Ads.InterLoad(this, () -> {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        });

    }
}