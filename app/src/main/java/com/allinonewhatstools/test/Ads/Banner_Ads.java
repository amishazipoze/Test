package com.allinonewhatstools.test.Ads;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.exifinterface.media.ExifInterface;

import com.allinonewhatstools.test.MyApplication;
import com.allinonewhatstools.test.SplashActivity;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.LoadAdError;

import java.util.ArrayList;

public class Banner_Ads {
    static String TAG = "FbNative";
    static ArrayList<Model_Banner_Ad> adViewBn12 = new ArrayList<>();
    static CountDownTimer cTimer = null;
    public static int countofloop;
    static AdView fbAdView12;
    static AdView fbAdView123;
    public static boolean fbLoaded;
    public static boolean fbLoaded2;
    public static boolean fbShowed;
    public static boolean fbShowed2;
    static boolean idtimer = true;
    public static ArrayList<Integer> intercount = new ArrayList<>();
    public static int putcount;
    public static int totaltime = 15000;

    public static void BanerLoad(RelativeLayout Adlayout) {
        Adlayout.removeAllViews();
        AdView adView = fbAdView12;
        if (!(adView == null || adView.getParent() == null)) {
            ((ViewGroup) fbAdView12.getParent()).removeView(fbAdView12);
        }
        AdView adView2 = fbAdView123;
        if (!(adView2 == null || adView2.getParent() == null)) {
            ((ViewGroup) fbAdView123.getParent()).removeView(fbAdView123);
        }
        countofloop = 0;
        if (!SplashActivity.isBannerShow || !SplashActivity.AdsFlag.matches(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS)) {
            return;
        }
        if (SplashActivity.isAdmob) {
            int i = 0;
            while (true) {
                if (i >= adViewBn12.size()) {
                    break;
                }
                if (!(adViewBn12.get(i).getAdView() == null || adViewBn12.get(i).getAdView().getParent() == null)) {
                    ((ViewGroup) adViewBn12.get(i).getAdView().getParent()).removeView(adViewBn12.get(i).getAdView());
                }
                if (adViewBn12.get(i).isShowed()) {
                    adViewBn12.get(i).setShowed(false);
                    LoadSingleAdBanner(adViewBn12.get(i));
                    int i2 = i + 1;
                    putcount = i2;
                    if (i2 >= adViewBn12.size()) {
                        putcount = 0;
                    }
                    intercount.clear();
                    for (int j = 0; j < adViewBn12.size(); j++) {
                        intercount.add(Integer.valueOf(putcount));
                        int i3 = putcount + 1;
                        putcount = i3;
                        if (i3 >= adViewBn12.size()) {
                            putcount = 0;
                        }
                    }
                } else {
                    i++;
                }
            }
            int i4 = 0;
            while (true) {
                if (i4 >= adViewBn12.size()) {
                    break;
                }
                if (adViewBn12.get(intercount.get(i4).intValue()).isLoaded()) {
                    if (!adViewBn12.get(intercount.get(i4).intValue()).isShowed()) {
                        Adlayout.removeAllViews();
                        Adlayout.addView(adViewBn12.get(intercount.get(i4).intValue()).getAdView());
                        adViewBn12.get(intercount.get(i4).intValue()).setShowed(true);
                        break;
                    }
                    LoadSingleAdBanner(adViewBn12.get(intercount.get(i4).intValue()));
                } else {
                    LoadSingleAdBanner(adViewBn12.get(intercount.get(i4).intValue()));
                }
                countofloop = i4;
                i4++;
            }
            if (countofloop != adViewBn12.size() - 1) {
                return;
            }
            if (!SplashActivity.isFacebook) {
                boolean z = SplashActivity.isQureka;
            } else if (fbLoaded && !fbShowed) {
                Adlayout.removeAllViews();
                Adlayout.addView(fbAdView12);
                fbShowed = true;
                if (!fbLoaded2 || fbShowed2) {
                    LoadFbBanner2();
                }
            } else if (!fbLoaded2 || fbShowed2) {
                LoadFbBanner();
                LoadFbBanner2();
            } else {
                LoadFbBanner();
                Adlayout.removeAllViews();
                Adlayout.addView(fbAdView123);
                fbShowed2 = true;
            }
        } else if (!SplashActivity.isFacebook) {
            boolean z2 = SplashActivity.isQureka;
        } else if (fbLoaded && !fbShowed) {
            Adlayout.removeAllViews();
            Adlayout.addView(fbAdView12);
            fbShowed = true;
            if (!fbLoaded2 || fbShowed2) {
                LoadFbBanner2();
            }
        } else if (!fbLoaded2 || fbShowed2) {
            LoadFbBanner();
            LoadFbBanner2();
        } else {
            LoadFbBanner();
            Adlayout.removeAllViews();
            Adlayout.addView(fbAdView123);
            fbShowed2 = true;
        }
    }

    public static void LoadAdBanner() {
        for (int i = 0; i < SplashActivity.adsKeyModelArrayList.size(); i++) {
            intercount.add(Integer.valueOf(i));
            final com.google.android.gms.ads.AdView adView12 = new com.google.android.gms.ads.AdView(MyApplication.getInstance());
            adView12.setAdSize(AdSize.SMART_BANNER);
            adView12.setAdUnitId(SplashActivity.adsKeyModelArrayList.get(i).getAdBanner());
            adView12.loadAd(new AdRequest.Builder().build());
            final int finalI = i;
            adView12.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    Log.w("Banner", "Load Admob Banner");
                    Model_Banner_Ad bannerAModel = new Model_Banner_Ad();
                    bannerAModel.setAdsId(SplashActivity.adsKeyModelArrayList.get(finalI).getAdBanner());
                    bannerAModel.setLoaded(true);
                    bannerAModel.setAdView(adView12);
                    bannerAModel.setShowed(false);
                    Banner_Ads.adViewBn12.add(bannerAModel);
                }

                public void onAdFailedToLoad(LoadAdError adError) {
                    Log.e("ADMOB", "onAdFailedToLoad:" + adError.toString());
                    Model_Banner_Ad bannerAModel = new Model_Banner_Ad();
                    bannerAModel.setAdsId(SplashActivity.adsKeyModelArrayList.get(finalI).getAdBanner());
                    bannerAModel.setLoaded(false);
                    bannerAModel.setAdView(adView12);
                    bannerAModel.setShowed(false);
                    Banner_Ads.adViewBn12.add(bannerAModel);
                }

                public void onAdOpened() {
                }

                public void onAdClicked() {
                }

                public void onAdClosed() {
                }
            });
        }
    }

    public static void LoadSingleAdBanner(final Model_Banner_Ad bannerAModel) {
        final com.google.android.gms.ads.AdView adView12 = new com.google.android.gms.ads.AdView(MyApplication.getInstance());
        adView12.setAdSize(AdSize.SMART_BANNER);
        adView12.setAdUnitId(bannerAModel.getAdsId());
        adView12.loadAd(new AdRequest.Builder().build());
        adView12.setAdListener(new AdListener() {
            public void onAdLoaded() {
                Log.w("Banner", "Load Admob Banner");
                Model_Banner_Ad bannerAModel = new Model_Banner_Ad();
                bannerAModel.setAdsId(bannerAModel.getAdsId());
                bannerAModel.setLoaded(true);
                bannerAModel.setAdView(adView12);
                bannerAModel.setShowed(false);
            }

            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e("ADMOB", "onAdFailedToLoad:" + adError.toString());
                Model_Banner_Ad bannerAModel = new Model_Banner_Ad();
                bannerAModel.setAdsId(bannerAModel.getAdsId());
                bannerAModel.setLoaded(false);
                bannerAModel.setAdView(adView12);
                bannerAModel.setShowed(false);
            }

            public void onAdOpened() {
            }

            public void onAdClicked() {
            }

            public void onAdClosed() {
            }
        });
    }

    public static void LoadFbBanner() {
        final AdView fbAdView = new AdView((Context) MyApplication.getInstance(), SplashActivity.fbBanner, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        fbAdView.loadAd(fbAdView.buildLoadAdConfig().withAdListener(new com.facebook.ads.AdListener() {
            public void onError(Ad ad, AdError adError) {
                Log.e("FBADMOB", "onAdFailedToLoad:" + adError.getErrorMessage());
                Banner_Ads.fbLoaded = false;
                Banner_Ads.fbShowed = false;
                Banner_Ads.fbAdView12 = null;
            }

            public void onAdLoaded(Ad ad) {
                Banner_Ads.fbLoaded = true;
                Banner_Ads.fbShowed = false;
                Banner_Ads.fbAdView12 = fbAdView;
                Log.w("FbBanner", "Load Admob Banner");
            }

            public void onAdClicked(Ad ad) {
            }

            public void onLoggingImpression(Ad ad) {
            }
        }).build());
    }

    public static void LoadFbBanner2() {
        final AdView fbAdView = new AdView((Context) MyApplication.getInstance(), SplashActivity.fbBanner, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        fbAdView.loadAd(fbAdView.buildLoadAdConfig().withAdListener(new com.facebook.ads.AdListener() {
            public void onError(Ad ad, AdError adError) {
                Log.e("FBADMOB", "onAdFailedToLoad:" + adError.getErrorMessage());
                Banner_Ads.fbLoaded2 = false;
                Banner_Ads.fbShowed2 = false;
                Banner_Ads.fbAdView123 = null;
            }

            public void onAdLoaded(Ad ad) {
                Banner_Ads.fbLoaded2 = true;
                Banner_Ads.fbShowed2 = false;
                Banner_Ads.fbAdView123 = fbAdView;
                Log.w("FbBanner", "Load Admob Banner");
            }

            public void onAdClicked(Ad ad) {
            }

            public void onLoggingImpression(Ad ad) {
            }
        }).build());
    }
}
