package com.allinonewhatstools.test.Ads;

import com.google.android.gms.ads.appopen.AppOpenAd;

public class Model_AppOpen_Ads {
    String adsIDs;
    boolean appOpenIsLoaded;
    boolean appOpenIsShowed;
    int count;
    AppOpenAd openAd;

    public int getCount() {
        return this.count;
    }

    public void setCount(int count2) {
        this.count = count2;
    }

    public AppOpenAd getOpenAd() {
        return this.openAd;
    }

    public void setOpenAd(AppOpenAd openAd2) {
        this.openAd = openAd2;
    }

    public boolean isAppOpenIsLoaded() {
        return this.appOpenIsLoaded;
    }

    public void setAppOpenIsLoaded(boolean appOpenIsLoaded2) {
        this.appOpenIsLoaded = appOpenIsLoaded2;
    }

    public boolean isAppOpenIsShowed() {
        return this.appOpenIsShowed;
    }

    public void setAppOpenIsShowed(boolean appOpenIsShowed2) {
        this.appOpenIsShowed = appOpenIsShowed2;
    }

    public String getAdsIDs() {
        return this.adsIDs;
    }

    public void setAdsIDs(String adsIDs2) {
        this.adsIDs = adsIDs2;
    }
}
