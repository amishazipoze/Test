package com.allinonewhatstools.test.Ads;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.allinonewhatstools.test.Activity_Qureka;
import com.allinonewhatstools.test.MyApplication;


import com.allinonewhatstools.test.SplashActivity;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

public class Inter_Ads {
    public static int countofloop;
    public static ArrayList<Integer> intercount = new ArrayList<>();
    static InterstitialAd interstitialFbAd;
    static ArrayList<Model_Inter_Ad> mInterstitialAd = new ArrayList<>();
    public static OnAdListner onAdListner;
    public static int putcount;

    public interface OnAdListner {
        void OnClose();
    }

    public static void BackpressInterShow(Activity activity, OnAdListner onAdListner1) {
        onAdListner = onAdListner1;
        SplashActivity.adsBackpressPercentageCount++;
        if (!SplashActivity.isAdInBackpress || SplashActivity.adsBackpressPercentageCount % SplashActivity.adsBackpressCount != 0) {
            onAdListner.OnClose();
        } else {
            InterLoad(activity, onAdListner1);
        }
    }

    public static void ShowAlertDialog(final Activity activity) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setTitle("Please wait");
        dialog.setMessage("Ads Showing...");
        dialog.setCancelable(false);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
                Inter_Ads.ContinewToAds(activity);
            }
        }, (long) SplashActivity.adsTimer);
    }

    public static void InterLoad(Activity activity, OnAdListner onAdListner1) {
        onAdListner = onAdListner1;
        if (!SplashActivity.isInsterShow) {
            onAdListner.OnClose();
            return;
        }
        countofloop = 0;
        SplashActivity.AdsCOUNTER++;
        SplashActivity.adsOpenCounter++;
        if (SplashActivity.isTimerShow) {
            ShowAlertDialog(activity);
        } else {
            ContinewToAds(activity);
        }
    }

    public static void ContinewToAds(final Activity activity) {
        if (!SplashActivity.isAdmob || !SplashActivity.isAppOpenOnIn || SplashActivity.adsOpenCounter % SplashActivity.adsOpenMAX != 0) {
            ShowAllInter(activity);
        } else {
            SplashActivity.appOpenManager.AppOpenShow(new AppOpen_App.OnAppOpenCloseInterClose() {
                public void OnAppOpenClose() {
                    Inter_Ads.onAdListner.OnClose();
                }

                public void OnAppFailClose() {
                    Inter_Ads.ShowAllInter(activity);
                }
            });
        }
    }

    public static void ShowAllInter(Activity activity) {
        if (SplashActivity.AdsCOUNTER % SplashActivity.AdsMAX != 0) {
            onAdListner.OnClose();
        } else if (SplashActivity.isAdmob) {
            int i = 0;
            while (true) {
                if (i >= mInterstitialAd.size()) {
                    break;
                } else if (mInterstitialAd.get(i).iShowed) {
                    mInterstitialAd.get(i).setiShowed(false);
                    int i2 = i + 1;
                    putcount = i2;
                    if (i2 >= mInterstitialAd.size()) {
                        putcount = 0;
                    }
                    intercount.clear();
                    for (int j = 0; j < mInterstitialAd.size(); j++) {
                        intercount.add(Integer.valueOf(putcount));
                        int i3 = putcount + 1;
                        putcount = i3;
                        if (i3 >= mInterstitialAd.size()) {
                            putcount = 0;
                        }
                    }
                } else {
                    i++;
                }
            }
            int i4 = 0;
            while (true) {
                if (i4 >= mInterstitialAd.size()) {
                    break;
                }
                Log.e("Adserror", "first : " + intercount.get(i4));
                Log.e("Adserror", "first 12 : " + mInterstitialAd.size());
                Log.e("Adserror", "first 12 : " + i4 + " :: " + mInterstitialAd.get(intercount.get(i4).intValue()).getAdsIds());
                if (mInterstitialAd.get(intercount.get(i4).intValue()).isiLoaded()) {
                    mInterstitialAd.get(intercount.get(i4).intValue()).getInterstitialAd().show(activity);
                    mInterstitialAd.get(intercount.get(i4).intValue()).setiShowed(true);
                    mInterstitialAd.get(intercount.get(i4).intValue()).setiLoaded(false);
                    mInterstitialAd.get(intercount.get(i4).intValue()).setInterstitialAd((com.google.android.gms.ads.interstitial.InterstitialAd) null);
                    break;
                }
                mInterstitialAd.get(intercount.get(i4).intValue()).setiShowed(false);
                mInterstitialAd.get(intercount.get(i4).intValue()).setiLoaded(false);
                mInterstitialAd.get(intercount.get(i4).intValue()).setInterstitialAd((com.google.android.gms.ads.interstitial.InterstitialAd) null);
                LoadAdInterSingle(mInterstitialAd.get(intercount.get(i4).intValue()));
                countofloop = i4;
                i4++;
            }
            if (countofloop != mInterstitialAd.size() - 1) {
                return;
            }
            if (SplashActivity.isFacebook) {
                if (interstitialFbAd.isAdLoaded()) {
                    interstitialFbAd.show();
                } else if (SplashActivity.isQureka) {
                    LoadFbInter();
                    activity.startActivity(new Intent(activity, Activity_Qureka.class));
                } else {
                    LoadFbInter();
                    onAdListner.OnClose();
                }
            } else if (SplashActivity.isQureka) {
                activity.startActivity(new Intent(activity, Activity_Qureka.class));
            } else {
                onAdListner.OnClose();
            }
        } else if (SplashActivity.isFacebook) {
            if (interstitialFbAd.isAdLoaded()) {
                interstitialFbAd.show();
            } else if (SplashActivity.isQureka) {
                LoadFbInter();
                activity.startActivity(new Intent(activity, Activity_Qureka.class));
            } else {
                LoadFbInter();
                onAdListner.OnClose();
            }
        } else if (SplashActivity.isQureka) {
            activity.startActivity(new Intent(activity, Activity_Qureka.class));
        } else {
            onAdListner.OnClose();
        }
    }

    public static void LoadAdInter() {
        if (SplashActivity.isAdmob) {
            for (int i = 0; i < SplashActivity.adsKeyModelArrayList.size(); i++) {
                intercount.add(Integer.valueOf(i));
                final int finalI = i;
                com.google.android.gms.ads.interstitial.InterstitialAd.load(MyApplication.getInstance(), SplashActivity.adsKeyModelArrayList.get(i).getAdInter(), new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                    public void onAdLoaded(com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                        Model_Inter_Ad interAModel = new Model_Inter_Ad();
                        interAModel.setAdsIds(SplashActivity.adsKeyModelArrayList.get(finalI).getAdInter());
                        interAModel.setiLoaded(true);
                        interAModel.setiShowed(false);
                        interAModel.setInterstitialAd(interstitialAd);
                        Inter_Ads.mInterstitialAd.add(interAModel);
                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            public void onAdDismissedFullScreenContent() {
                                Inter_Ads.mInterstitialAd.get(finalI).setInterstitialAd((com.google.android.gms.ads.interstitial.InterstitialAd) null);
                                Inter_Ads.mInterstitialAd.get(finalI).setiShowed(true);
                                Inter_Ads.mInterstitialAd.get(finalI).setiLoaded(false);
                                Inter_Ads.LoadAdInterSingle(Inter_Ads.mInterstitialAd.get(finalI));
                                Inter_Ads.onAdListner.OnClose();
                                Log.d("TAG", "The ad was dismissed.");
                            }

                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Inter_Ads.mInterstitialAd.get(finalI).setInterstitialAd((com.google.android.gms.ads.interstitial.InterstitialAd) null);
                                Inter_Ads.mInterstitialAd.get(finalI).setiShowed(true);
                                Inter_Ads.mInterstitialAd.get(finalI).setiLoaded(false);
                                Inter_Ads.LoadAdInterSingle(Inter_Ads.mInterstitialAd.get(finalI));
                                Inter_Ads.onAdListner.OnClose();
                                Log.d("TAG", "The ad failed to show.");
                            }

                            public void onAdShowedFullScreenContent() {
                                Log.d("TAG", "The ad was shown.");
                            }
                        });
                    }

                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Model_Inter_Ad interAModel = new Model_Inter_Ad();
                        interAModel.setAdsIds(SplashActivity.adsKeyModelArrayList.get(finalI).getAdInter());
                        interAModel.setiLoaded(false);
                        interAModel.setiShowed(false);
                        interAModel.setInterstitialAd((com.google.android.gms.ads.interstitial.InterstitialAd) null);
                        Inter_Ads.mInterstitialAd.add(interAModel);
                    }
                });
            }
        }
    }

    public static void LoadAdInterSingle(final Model_Inter_Ad interAModel) {
        if (SplashActivity.isAdmob) {
            com.google.android.gms.ads.interstitial.InterstitialAd.load(MyApplication.getInstance(), interAModel.getAdsIds(), new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                public void onAdLoaded(com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                    interAModel.setInterstitialAd(interstitialAd);
                    interAModel.setiLoaded(true);
                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        public void onAdDismissedFullScreenContent() {
                            Inter_Ads.LoadAdInterSingle(interAModel);
                            interAModel.setInterstitialAd((com.google.android.gms.ads.interstitial.InterstitialAd) null);
                            interAModel.setiShowed(true);
                            interAModel.setiLoaded(false);
                            Inter_Ads.onAdListner.OnClose();
                            Log.d("TAG", "The ad was dismissed.");
                        }

                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            interAModel.setInterstitialAd((com.google.android.gms.ads.interstitial.InterstitialAd) null);
                            interAModel.setiShowed(true);
                            interAModel.setiLoaded(false);
                            Inter_Ads.LoadAdInterSingle(interAModel);
                            Inter_Ads.onAdListner.OnClose();
                            Log.d("TAG", "The ad failed to show.");
                        }

                        public void onAdShowedFullScreenContent() {
                            Log.d("TAG", "The ad was shown.");
                        }
                    });
                }

                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    interAModel.setInterstitialAd((com.google.android.gms.ads.interstitial.InterstitialAd) null);
                    interAModel.setiLoaded(false);
                }
            });
        }
    }

    public static void LoadFbInter() {
        if (SplashActivity.isFacebook) {
            interstitialFbAd = new InterstitialAd(MyApplication.getInstance(), SplashActivity.fbInter);
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                public void onInterstitialDisplayed(Ad ad) {
                    Log.e("FbInter", "Interstitial ad displayed.");
                }

                public void onInterstitialDismissed(Ad ad) {
                    Log.e("FbInter", "Interstitial ad dismissed.");
                    Inter_Ads.LoadFbInter();
                    Inter_Ads.onAdListner.OnClose();
                }

                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    Log.e("FbInter", "Interstitial ad failed to load: " + adError.getErrorMessage());
                }

                public void onAdLoaded(Ad ad) {
                    Log.d("FbInter", "Interstitial ad is loaded and ready to be displayed!");
                }

                public void onAdClicked(Ad ad) {
                    Log.d("FbInter", "Interstitial ad clicked!");
                }

                public void onLoggingImpression(Ad ad) {
                    Log.d("FbInter", "Interstitial ad impression logged!");
                }
            };
            InterstitialAd interstitialAd = interstitialFbAd;
            interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
        }
    }
}
