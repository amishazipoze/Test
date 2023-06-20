package com.allinonewhatstools.test.Ads;

import com.google.android.gms.ads.AdView;

public class Model_Banner_Ad {
    AdView adView;
    String adsId;
    boolean isLoaded;
    boolean isShowed;

    public boolean isShowed() {
        return this.isShowed;
    }

    public void setShowed(boolean showed) {
        this.isShowed = showed;
    }

    public String getAdsId() {
        return this.adsId;
    }

    public void setAdsId(String adsId2) {
        this.adsId = adsId2;
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }

    public void setLoaded(boolean loaded) {
        this.isLoaded = loaded;
    }

    public AdView getAdView() {
        return this.adView;
    }

    public void setAdView(AdView adView2) {
        this.adView = adView2;
    }
}

