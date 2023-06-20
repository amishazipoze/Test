package com.allinonewhatstools.test.Ads;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.allinonewhatstools.test.MyApplication;

import com.allinonewhatstools.test.SplashActivity;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.ArrayList;
import java.util.Date;

public class AppOpen_App implements Application.ActivityLifecycleCallbacks, LifecycleObserver {
    private static final String LOG_TAG = "AppOpenManager";
    private static boolean isShowingAd = false;
    int SplashTimeLoad = 0;
    boolean adsLoadnow = true;
    ArrayList<Model_AppOpen_Ads> appOpenAd = new ArrayList<>();
    private Activity currentActivity;
    /* access modifiers changed from: private */
    public long loadTime = 0;
    int mainShowCount = 0;
    private final MyApplication myApplication;
    int myids1;
    Close_App_open onAppOpenClose;
    OnAppOpenCloseInterClose onAppOpenCloseInterClose = null;

    public interface OnAppOpenCloseInterClose {
        void OnAppFailClose();

        void OnAppOpenClose();
    }

    public AppOpen_App(MyApplication myApplication2, Close_App_open onAppOpenClose2) {
        this.onAppOpenClose = onAppOpenClose2;
        this.myApplication = myApplication2;
        myApplication2.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        this.myids1 = 1;
        this.SplashTimeLoad = 0;
        this.mainShowCount = 0;
    }

    public void AppOpenShow(OnAppOpenCloseInterClose onAppOpenCloseInterClose2) {
        int iCount = 0;
        this.onAppOpenCloseInterClose = onAppOpenCloseInterClose2;
        int i = 0;
        while (true) {
            if (i >= SplashActivity.adsKeyModelArrayList.size()) {
                break;
            }
            int i2 = this.mainShowCount + 1;
            this.mainShowCount = i2;
            if (i2 >= SplashActivity.adsKeyModelArrayList.size()) {
                this.mainShowCount = 0;
            }
            if (!this.appOpenAd.get(this.mainShowCount).appOpenIsShowed && isAdAvailable(this.appOpenAd.get(this.mainShowCount).getCount())) {
                showAdIfAvailable(this.appOpenAd.get(this.mainShowCount));
                break;
            }
            fetchAdOnlyOne(this.appOpenAd.get(this.mainShowCount));
            iCount = i;
            i++;
        }
        if (iCount >= SplashActivity.adsKeyModelArrayList.size() - 1) {
            this.onAppOpenCloseInterClose.OnAppFailClose();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        if (this.SplashTimeLoad != 0) {
            int i = 0;
            while (true) {
                if (i >= SplashActivity.adsKeyModelArrayList.size()) {
                    break;
                }
                int i2 = this.mainShowCount + 1;
                this.mainShowCount = i2;
                if (i2 >= SplashActivity.adsKeyModelArrayList.size()) {
                    this.mainShowCount = 0;
                }
                if (!this.appOpenAd.get(this.mainShowCount).appOpenIsShowed && isAdAvailable(this.appOpenAd.get(this.mainShowCount).getCount())) {
                    showAdIfAvailable(this.appOpenAd.get(this.mainShowCount));
                    break;
                } else {
                    fetchAdOnlyOne(this.appOpenAd.get(this.mainShowCount));
                    i++;
                }
            }
        } else {
            this.SplashTimeLoad = 1;
            this.mainShowCount = 0;
            fetchAd(0);
        }
        Log.d(LOG_TAG, "onStart");
    }

    public void DestroyAllAds() {
        if (SplashActivity.isAdmob) {
            this.adsLoadnow = false;
            this.myApplication.unregisterActivityLifecycleCallbacks(this);
            ProcessLifecycleOwner.get().getLifecycle().removeObserver(this);
            this.appOpenAd.clear();
            isShowingAd = false;
            this.currentActivity = null;
            this.SplashTimeLoad = 0;
            this.mainShowCount = 0;
        }
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public boolean isAdAvailable(int i) {
        return this.appOpenAd.get(i).getOpenAd() != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        return new Date().getTime() - this.loadTime < 3600000 * numHours;
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    public void onActivityStarted(Activity activity) {
        this.currentActivity = activity;
    }

    public void onActivityResumed(Activity activity) {
        this.currentActivity = activity;
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
        this.currentActivity = null;
    }

    public void fetchAd(int i) {
        final int finalI = i;
        AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            public void onAdLoaded(AppOpenAd ad) {
                Model_AppOpen_Ads appOpenModel = new Model_AppOpen_Ads();
                appOpenModel.setOpenAd(ad);
                appOpenModel.setAppOpenIsLoaded(true);
                appOpenModel.setAppOpenIsShowed(false);
                appOpenModel.setAdsIDs(SplashActivity.adsKeyModelArrayList.get(finalI).getAdOpenAd());
                appOpenModel.setCount(finalI);
                AppOpen_App.this.appOpenAd.add(appOpenModel);
                long unused = AppOpen_App.this.loadTime = new Date().getTime();
                if (AppOpen_App.this.myids1 == 1) {
                    AppOpen_App.this.myids1 = 0;
                    AppOpen_App appOpenManager = AppOpen_App.this;
                    appOpenManager.showAdIfAvailable(appOpenManager.appOpenAd.get(finalI));
                }
                if (finalI + 1 < SplashActivity.adsKeyModelArrayList.size()) {
                    AppOpen_App.this.fetchAd(finalI + 1);
                }
            }

            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Model_AppOpen_Ads appOpenModel = new Model_AppOpen_Ads();
                appOpenModel.setOpenAd((AppOpenAd) null);
                appOpenModel.setAppOpenIsLoaded(false);
                appOpenModel.setAppOpenIsShowed(false);
                appOpenModel.setAdsIDs(SplashActivity.adsKeyModelArrayList.get(finalI).getAdOpenAd());
                appOpenModel.setCount(finalI);
                AppOpen_App.this.appOpenAd.add(appOpenModel);
                if (AppOpen_App.this.myids1 == 1) {
                    AppOpen_App.this.myids1 = 0;
                    AppOpen_App.this.onAppOpenClose.OnAppOpenFailToLoad();
                }
                if (finalI + 1 < SplashActivity.adsKeyModelArrayList.size()) {
                    AppOpen_App.this.fetchAd(finalI + 1);
                }
            }
        };
        AdRequest request = getAdRequest();
        if (this.adsLoadnow) {
            AppOpenAd.load((Context) this.myApplication, SplashActivity.adsKeyModelArrayList.get(finalI).getAdOpenAd(), request, 1, loadCallback);
        }
    }

    public void showAdIfAvailable(final Model_AppOpen_Ads appOpenModel) {
        if (appOpenModel.appOpenIsShowed || !isAdAvailable(appOpenModel.getCount())) {
            Log.d(LOG_TAG, "Can not show ad.");
            fetchAdOnlyOne(appOpenModel);
            return;
        }
        Log.d(LOG_TAG, "Will show ad.");
        appOpenModel.getOpenAd().setFullScreenContentCallback(new FullScreenContentCallback() {
            public void onAdDismissedFullScreenContent() {
                appOpenModel.setOpenAd((AppOpenAd) null);
                appOpenModel.setAppOpenIsShowed(false);
                AppOpen_App.this.fetchAdOnlyOne(appOpenModel);
                AppOpen_App.this.onAppOpenClose.OnAppOpenClose();
                if (AppOpen_App.this.onAppOpenCloseInterClose != null) {
                    AppOpen_App.this.onAppOpenCloseInterClose.OnAppOpenClose();
                }
            }

            public void onAdFailedToShowFullScreenContent(AdError adError) {
            }

            public void onAdShowedFullScreenContent() {
                appOpenModel.setAppOpenIsShowed(true);
            }
        });
        appOpenModel.getOpenAd().show(this.currentActivity);
    }

    public void fetchAdOnlyOne(final Model_AppOpen_Ads appOpenModel) {
        if (!isAdAvailable(appOpenModel.getCount())) {
            AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                public void onAdLoaded(AppOpenAd ad) {
                    appOpenModel.setOpenAd(ad);
                    appOpenModel.setAppOpenIsLoaded(true);
                    appOpenModel.setAppOpenIsShowed(false);
                    long unused = AppOpen_App.this.loadTime = new Date().getTime();
                }

                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    appOpenModel.setOpenAd((AppOpenAd) null);
                    appOpenModel.setAppOpenIsLoaded(false);
                    appOpenModel.setAppOpenIsShowed(false);
                }
            };
            AdRequest request = getAdRequest();
            if (this.adsLoadnow) {
                Log.e(LOG_TAG, "appOpenCount: " + appOpenModel.getCount());
                AppOpenAd.load((Context) this.myApplication, appOpenModel.getAdsIDs(), request, 1, loadCallback);
            }
        }
    }
}
