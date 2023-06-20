package com.allinonewhatstools.test.Ads;

import android.net.Uri;

import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.allinonewhatstools.test.MyApplication;
import com.allinonewhatstools.test.R;
import com.allinonewhatstools.test.SplashActivity;


public class Qureka_Calling {
    public static void stratQureka() {
        Uri uri = Uri.parse(SplashActivity.qurekaLink);
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setDefaultColorSchemeParams(new CustomTabColorSchemeParams.Builder().setNavigationBarColor(ContextCompat.getColor(MyApplication.getContext(), R.color.black)).setToolbarColor(ContextCompat.getColor(MyApplication.getContext(), R.color.white)).setSecondaryToolbarColor(ContextCompat.getColor(MyApplication.getContext(), R.color.white)).build());
        CustomTabsIntent customTabsIntent = intentBuilder.build();
        customTabsIntent.intent.addFlags(268435456);
        customTabsIntent.launchUrl(MyApplication.getContext(), uri);
    }
}
