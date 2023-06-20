package com.allinonewhatstools.test;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

//import com.onesignal.OneSignal;

import java.io.File;

public class MyApplication extends Application {
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";
//    private static final String ONESIGNAL_APP_ID = "92f5c768-12c1-4535-be06-ecf8b146318e";
    private static MyApplication instance;
    public static Context mActivity;
    public static Resources resources;
    SharedPreferences sharedPreferences;

    static {
        System.loadLibrary("test");
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
//        OneSignal.initWithContext(this);
//        OneSignal.setAppId(ONESIGNAL_APP_ID);
        mActivity = this;
        resources = getResources();
        createNotificationChannnel();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences = defaultSharedPreferences;
        if (defaultSharedPreferences.getBoolean(getString(R.string.app_name), true)) {
            AppCompatDelegate.setDefaultNightMode(1);
        } else {
            AppCompatDelegate.setDefaultNightMode(2);
        }
    }

    private void createNotificationChannnel() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService(NotificationManager.class)).createNotificationChannel(new NotificationChannel("ALARM_SERVICE_CHANNEL", getString(R.string.app_name) + "Service Channel", 4));
        }
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public void clearApplicationData() {
        File applicationDirectory = new File(getCacheDir().getParent());
        if (applicationDirectory.exists()) {
            for (String fileName : applicationDirectory.list()) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static synchronized Context getContext() {
        Context applicationContext;
        synchronized (MyApplication.class) {
            applicationContext = getInstance().getApplicationContext();
        }
        return applicationContext;
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file == null) {
            return true;
        }
        if (!file.isDirectory()) {
            return file.delete();
        }
        String[] children = file.list();
        for (int i = 0; i < children.length; i++) {
            deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
        }
        return deletedAll;
    }
}
