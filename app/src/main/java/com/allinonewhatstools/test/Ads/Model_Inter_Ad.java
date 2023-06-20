package com.allinonewhatstools.test.Ads;

import com.google.android.gms.ads.interstitial.InterstitialAd;

public class Model_Inter_Ad {
    String adsIds;
    boolean iLoaded;
    boolean iShowed;
    InterstitialAd interstitialAd;

    public String getAdsIds() {
        return this.adsIds;
    }

    public void setAdsIds(String adsIds2) {
        this.adsIds = adsIds2;
    }

    public InterstitialAd getInterstitialAd() {
        return this.interstitialAd;
    }

    public void setInterstitialAd(InterstitialAd interstitialAd2) {
        this.interstitialAd = interstitialAd2;
    }

    public boolean isiShowed() {
        return this.iShowed;
    }

    public void setiShowed(boolean iShowed2) {
        this.iShowed = iShowed2;
    }

    public boolean isiLoaded() {
        return this.iLoaded;
    }

    public void setiLoaded(boolean iLoaded2) {
        this.iLoaded = iLoaded2;
    }
}
