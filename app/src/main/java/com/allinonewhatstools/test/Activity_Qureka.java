package com.allinonewhatstools.test;



import static com.allinonewhatstools.test.SplashActivity.qurekaLink;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.allinonewhatstools.test.Ads.Qureka_Calling;
import com.allinonewhatstools.test.Ads.Inter_Ads;


public class Activity_Qureka extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ad_qureka);


        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();

        customTabsIntent.intent.setPackage("com.android.chrome");
        builder.setToolbarColor(ContextCompat.getColor(this,R.color.black));
        builder.setShowTitle(true);
        builder.addDefaultShareMenuItem();
        customTabsIntent.intent.setData(Uri.parse(qurekaLink));
        startActivityForResult(customTabsIntent.intent,51,customTabsIntent.startAnimationBundle);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed  here it is 1
        if (requestCode == 51) {


            finish();
            Inter_Ads.onAdListner.OnClose();


        }
    }


    public void ShowAd(View view) {
        Qureka_Calling.stratQureka();
    }

    public void CloseAd(View view) {
        finish();
        Inter_Ads.onAdListner.OnClose();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Inter_Ads.onAdListner.OnClose();
    }
}