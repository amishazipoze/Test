package com.allinonewhatstools.test.Ads;

import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class Model_Native_Ad {
    String adsIDs;
    NativeAdView nativeAdView;
    boolean nativeIsLoaded;
    boolean nativeIsShowed;
    NativeAd unNative;

    public NativeAd getUnNative() {
        return this.unNative;
    }

    public void setUnNative(NativeAd unNative2) {
        this.unNative = unNative2;
    }

    public NativeAdView getNativeAdView() {
        return this.nativeAdView;
    }

    public void setNativeAdView(NativeAdView nativeAdView2) {
        this.nativeAdView = nativeAdView2;
    }

    public boolean isNativeIsLoaded() {
        return this.nativeIsLoaded;
    }

    public void setNativeIsLoaded(boolean nativeIsLoaded2) {
        this.nativeIsLoaded = nativeIsLoaded2;
    }

    public boolean isNativeIsShowed() {
        return this.nativeIsShowed;
    }

    public void setNativeIsShowed(boolean nativeIsShowed2) {
        this.nativeIsShowed = nativeIsShowed2;
    }

    public String getAdsIDs() {
        return this.adsIDs;
    }

    public void setAdsIDs(String adsIDs2) {
        this.adsIDs = adsIDs2;
    }
}
