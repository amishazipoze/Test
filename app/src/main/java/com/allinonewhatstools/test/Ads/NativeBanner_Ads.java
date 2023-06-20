package com.allinonewhatstools.test.Ads;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.exifinterface.media.ExifInterface;

import com.allinonewhatstools.test.MyApplication;
import com.allinonewhatstools.test.R;
import com.allinonewhatstools.test.SplashActivity;
import com.bumptech.glide.Glide;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.List;

public class NativeBanner_Ads {
    static String TAG = "NativeLog";
    public static int countofloop;
    public static ArrayList<Integer> intercount = new ArrayList<>();
    static boolean isFbLoaded;
    static boolean isFbLoaded2;
    static boolean isFbShowed;
    static boolean isFbShowed2;
    static ArrayList<Model_Native_Ad> nativeAModels = new ArrayList<>();
    static com.facebook.ads.NativeAd nativeAdFb;
    static com.facebook.ads.NativeAd nativeAdFb2;
    static NativeAdLayout nativefbAdLayout;
    static NativeAdLayout nativefbAdLayout2;
    public static int putcount;

    public static void NativeFinalLoad(FrameLayout Adlayout, RelativeLayout adsWhiteLayout) {
        Adlayout.removeAllViews();
        adsWhiteLayout.setVisibility(0);
        NativeAdLayout nativeAdLayout = nativefbAdLayout;
        if (!(nativeAdLayout == null || nativeAdLayout.getParent() == null)) {
            ((ViewGroup) nativefbAdLayout.getParent()).removeView(nativefbAdLayout);
        }
        NativeAdLayout nativeAdLayout2 = nativefbAdLayout2;
        if (!(nativeAdLayout2 == null || nativeAdLayout2.getParent() == null)) {
            ((ViewGroup) nativefbAdLayout2.getParent()).removeView(nativefbAdLayout2);
        }
        if (!SplashActivity.isSmallNatShow) {
            adsWhiteLayout.setVisibility(8);
            return;
        }
        countofloop = 0;
        if (!SplashActivity.AdsFlag.matches(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS)) {
            return;
        }
        if (SplashActivity.isAdmob) {
            int i = 0;
            while (true) {
                if (i >= nativeAModels.size()) {
                    break;
                }
                if (!(nativeAModels.get(i).getNativeAdView() == null || nativeAModels.get(i).getNativeAdView().getParent() == null)) {
                    ((ViewGroup) nativeAModels.get(i).getNativeAdView().getParent()).removeView(nativeAModels.get(i).getNativeAdView());
                }
                if (nativeAModels.get(i).isNativeIsShowed()) {
                    nativeAModels.get(i).setNativeIsShowed(false);
                    LoadAdNativeSingle(nativeAModels.get(i));
                    int i2 = i + 1;
                    putcount = i2;
                    if (i2 >= nativeAModels.size()) {
                        putcount = 0;
                    }
                    intercount.clear();
                    for (int j = 0; j < nativeAModels.size(); j++) {
                        intercount.add(Integer.valueOf(putcount));
                        int i3 = putcount + 1;
                        putcount = i3;
                        if (i3 >= nativeAModels.size()) {
                            putcount = 0;
                        }
                    }
                } else {
                    i++;
                }
            }
            int i4 = 0;
            while (true) {
                if (i4 >= nativeAModels.size()) {
                    break;
                }
                if (nativeAModels.get(intercount.get(i4).intValue()).isNativeIsLoaded()) {
                    if (!nativeAModels.get(intercount.get(i4).intValue()).isNativeIsShowed()) {
                        Log.e(TAG, "NativeFinalLoad Count: " + intercount.get(i4));
                        Adlayout.removeAllViews();
                        Adlayout.addView(nativeAModels.get(intercount.get(i4).intValue()).getNativeAdView());
                        nativeAModels.get(intercount.get(i4).intValue()).setNativeIsShowed(true);
                        break;
                    }
                    LoadAdNativeSingle(nativeAModels.get(intercount.get(i4).intValue()));
                } else {
                    LoadAdNativeSingle(nativeAModels.get(intercount.get(i4).intValue()));
                }
                countofloop = i4;
                i4++;
            }
            if (countofloop != nativeAModels.size() - 1) {
                return;
            }
            if (SplashActivity.isFacebook) {
                if (isFbLoaded && !isFbShowed) {
                    Adlayout.removeAllViews();
                    Adlayout.addView(nativefbAdLayout);
                    isFbShowed = true;
                    if (!isFbLoaded2 || isFbShowed2) {
                        LoadFbNative01();
                    }
                } else if (isFbLoaded2 && !isFbShowed2) {
                    LoadFbNative();
                    Adlayout.removeAllViews();
                    Adlayout.addView(nativefbAdLayout2);
                    isFbShowed2 = true;
                } else if (SplashActivity.isQureka) {
                    LoadQurekaNative(Adlayout);
                    LoadFbNative();
                    LoadFbNative01();
                } else {
                    LoadFbNative();
                    LoadFbNative01();
                }
            } else if (SplashActivity.isQureka) {
                LoadQurekaNative(Adlayout);
            }
        } else if (SplashActivity.isFacebook) {
            if (isFbLoaded && !isFbShowed) {
                Adlayout.removeAllViews();
                Adlayout.addView(nativefbAdLayout);
                isFbShowed = true;
                if (!isFbLoaded2 || isFbShowed2) {
                    LoadFbNative01();
                }
            } else if (isFbLoaded2 && !isFbShowed2) {
                LoadFbNative();
                Adlayout.removeAllViews();
                Adlayout.addView(nativefbAdLayout2);
                isFbShowed2 = true;
            } else if (SplashActivity.isQureka) {
                LoadQurekaNative(Adlayout);
                LoadFbNative();
                LoadFbNative01();
            } else {
                LoadFbNative();
                LoadFbNative01();
            }
        } else if (SplashActivity.isQureka) {
            LoadQurekaNative(Adlayout);
        }
    }

    public static void LoadQurekaNative(FrameLayout Adlayout) {
        Adlayout.removeAllViews();
        View layout = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.act_ad_qbanner_native, (ViewGroup) null, false);
        Glide.with(MyApplication.getContext()).load(Integer.valueOf(R.drawable.qgif1)).into((ImageView) layout.findViewById(R.id.ad_app_icon));
        ((RelativeLayout) layout.findViewById(R.id.mainNativeQrekaImage)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Qureka_Calling.stratQureka();
            }
        });
        Adlayout.addView(layout);
    }

    public static void LoadAdNative() {
        for (int i = 0; i < SplashActivity.adsKeyModelArrayList.size(); i++) {
            intercount.add(Integer.valueOf(i));
            AdLoader.Builder builder = new AdLoader.Builder(MyApplication.getInstance(), SplashActivity.adsKeyModelArrayList.get(i).getAdNative());
            final int finalI = i;
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                public void onNativeAdLoaded(com.google.android.gms.ads.nativead.NativeAd unifiedNativeAd) {
                    NativeAdView adView12;
                    if (SplashActivity.isAttractButton) {
                        adView12 = (NativeAdView) LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.act_ad_nativ_banner_admob, (ViewGroup) null);
                    } else {
                        adView12 = (NativeAdView) LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.act_ad_native_banner_admob_old, (ViewGroup) null);
                    }
                    Model_Native_Ad nativeAModel = new Model_Native_Ad();
                    nativeAModel.setNativeAdView(adView12);
                    nativeAModel.setUnNative(unifiedNativeAd);
                    nativeAModel.setNativeIsLoaded(true);
                    nativeAModel.setNativeIsShowed(false);
                    nativeAModel.setAdsIDs(SplashActivity.adsKeyModelArrayList.get(finalI).getAdNative());
                    NativeBanner_Ads.nativeAModels.add(nativeAModel);
                    NativeBanner_Ads.populateUnifiedNativeAdView(unifiedNativeAd, adView12);
                }
            });
            builder.withAdListener(new AdListener() {
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                    Model_Native_Ad nativeAModel = new Model_Native_Ad();
                    nativeAModel.setNativeAdView((NativeAdView) null);
                    nativeAModel.setUnNative((com.google.android.gms.ads.nativead.NativeAd) null);
                    nativeAModel.setNativeIsLoaded(false);
                    nativeAModel.setNativeIsShowed(false);
                    nativeAModel.setAdsIDs(SplashActivity.adsKeyModelArrayList.get(finalI).getAdNative());
                    NativeBanner_Ads.nativeAModels.add(nativeAModel);
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
    }

    public static void LoadAdNativeSingle(final Model_Native_Ad nativeAModel) {
        AdLoader.Builder builder = new AdLoader.Builder(MyApplication.getInstance(), nativeAModel.getAdsIDs());
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            public void onNativeAdLoaded(com.google.android.gms.ads.nativead.NativeAd unifiedNativeAd) {
                NativeAdView adView12;
                if (nativeAModel.getUnNative() != null) {
                    nativeAModel.getUnNative().destroy();
                }
                if (SplashActivity.isAttractButton) {
                    adView12 = (NativeAdView) LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.act_ad_nativ_banner_admob, (ViewGroup) null);
                } else {
                    adView12 = (NativeAdView) LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.act_ad_native_banner_admob_old, (ViewGroup) null);
                }
                nativeAModel.setNativeAdView(adView12);
                nativeAModel.setUnNative(unifiedNativeAd);
                nativeAModel.setNativeIsLoaded(true);
                NativeBanner_Ads.populateUnifiedNativeAdView(unifiedNativeAd, adView12);
            }
        });
        builder.withAdListener(new AdListener() {
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Model_Native_Ad nativeAModel = new Model_Native_Ad();
                nativeAModel.setNativeAdView((NativeAdView) null);
                nativeAModel.setUnNative((com.google.android.gms.ads.nativead.NativeAd) null);
                nativeAModel.setNativeIsLoaded(false);
            }
        }).build().loadAd(new AdRequest.Builder().build());
    }

    /* access modifiers changed from: private */
    public static void populateUnifiedNativeAdView(com.google.android.gms.ads.nativead.NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(4);
        } else {
            adView.getBodyView().setVisibility(0);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(4);
        } else {
            adView.getCallToActionView().setVisibility(0);
            ((TextView) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(8);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(0);
        }
        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(4);
        } else {
            adView.getPriceView().setVisibility(0);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }
        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(4);
        } else {
            adView.getStoreView().setVisibility(0);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }
        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(4);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(0);
        }
        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(4);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(0);
        }
        adView.setNativeAd(nativeAd);
    }

    public static void setUpFadeAnimation(final TextView textView) {
        final Animation fadeIn = new AlphaAnimation(0.2f, 1.0f);
        fadeIn.setDuration(700);
        fadeIn.setStartOffset(100);
        final Animation fadeOut = new AlphaAnimation(1.0f, 0.2f);
        fadeOut.setDuration(700);
        fadeOut.setStartOffset(100);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation arg0) {
                textView.startAnimation(fadeOut);
            }

            public void onAnimationRepeat(Animation arg0) {
            }

            public void onAnimationStart(Animation arg0) {
            }
        });
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation arg0) {
                textView.startAnimation(fadeIn);
            }

            public void onAnimationRepeat(Animation arg0) {
            }

            public void onAnimationStart(Animation arg0) {
            }
        });
        textView.startAnimation(fadeOut);
    }

    public static void LoadFbNative() {
        final com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd((Context) MyApplication.getInstance(), SplashActivity.fbNative);
        nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            public void onMediaDownloaded(Ad ad) {
            }

            public void onError(Ad ad, AdError adError) {
               NativeBanner_Ads.isFbLoaded = false;
               NativeBanner_Ads.isFbShowed = false;
               NativeBanner_Ads.nativeAdFb = null;
            }

            public void onAdLoaded(Ad ad) {
                if (nativeAd != null && nativeAd == ad) {
                   NativeBanner_Ads.isFbLoaded = true;
                   NativeBanner_Ads.isFbShowed = false;
                   NativeBanner_Ads.nativeAdFb = nativeAd;
                   NativeBanner_Ads.inflateAd(nativeAd);
                }
            }

            public void onAdClicked(Ad ad) {
                Log.d(NativeBanner_Ads.TAG, "Native ad clicked!");
            }

            public void onLoggingImpression(Ad ad) {
                Log.d(NativeBanner_Ads.TAG, "Native ad impression logged!");
            }
        }).build());
    }

    /* access modifiers changed from: private */
    public static void inflateAd(com.facebook.ads.NativeAd nativeAd) {
        nativeAd.unregisterView();
        nativefbAdLayout = new NativeAdLayout(MyApplication.getInstance());
        int i = 0;
        LinearLayout adView = (LinearLayout) LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.act_ad_banner_fb_native, nativefbAdLayout, false);
        nativefbAdLayout.addView(adView);
        LinearLayout adChoicesContainer = (LinearLayout) adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(MyApplication.getInstance(), nativeAd, nativefbAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);
        com.facebook.ads.MediaView nativeAdIcon = (com.facebook.ads.MediaView) adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
        com.facebook.ads.MediaView nativeAdMedia = (com.facebook.ads.MediaView) adView.findViewById(R.id.native_ad_media);
        TextView sponsoredLabel = (TextView) adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = (Button) adView.findViewById(R.id.native_ad_call_to_action);
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        ((TextView) adView.findViewById(R.id.native_ad_body)).setText(nativeAd.getAdBodyText());
        ((TextView) adView.findViewById(R.id.native_ad_social_context)).setText(nativeAd.getAdSocialContext());
        if (!nativeAd.hasCallToAction()) {
            i = 4;
        }
        nativeAdCallToAction.setVisibility(i);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeAd.registerViewForInteraction((View) adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    public static void LoadFbNative01() {
        final com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd((Context) MyApplication.getInstance(), SplashActivity.fbNative);
        nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            public void onMediaDownloaded(Ad ad) {
            }

            public void onError(Ad ad, AdError adError) {
                NativeBanner_Ads.isFbLoaded2 = false;
                NativeBanner_Ads.isFbShowed2 = false;
                NativeBanner_Ads.nativeAdFb2 = null;
            }

            public void onAdLoaded(Ad ad) {
                if (nativeAd != null && nativeAd == ad) {
                   NativeBanner_Ads.isFbLoaded2 = true;
                   NativeBanner_Ads.isFbShowed2 = false;
                   NativeBanner_Ads.nativeAdFb2 = nativeAd;
                   NativeBanner_Ads.inflateAd01(nativeAd);
                }
            }

            public void onAdClicked(Ad ad) {
                Log.d(NativeBanner_Ads.TAG, "Native ad clicked!");
            }

            public void onLoggingImpression(Ad ad) {
                Log.d(NativeBanner_Ads.TAG, "Native ad impression logged!");
            }
        }).build());
    }

    /* access modifiers changed from: private */
    public static void inflateAd01(com.facebook.ads.NativeAd nativeAd) {
        nativeAd.unregisterView();
        nativefbAdLayout2 = new NativeAdLayout(MyApplication.getInstance());
        int i = 0;
        LinearLayout adView = (LinearLayout) LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.act_ad_banner_fb_native, nativefbAdLayout2, false);
        nativefbAdLayout2.addView(adView);
        LinearLayout adChoicesContainer = (LinearLayout) adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(MyApplication.getInstance(), nativeAd, nativefbAdLayout2);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);
        com.facebook.ads.MediaView nativeAdIcon = (com.facebook.ads.MediaView) adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
        com.facebook.ads.MediaView nativeAdMedia = (com.facebook.ads.MediaView) adView.findViewById(R.id.native_ad_media);
        TextView sponsoredLabel = (TextView) adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = (Button) adView.findViewById(R.id.native_ad_call_to_action);
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        ((TextView) adView.findViewById(R.id.native_ad_body)).setText(nativeAd.getAdBodyText());
        ((TextView) adView.findViewById(R.id.native_ad_social_context)).setText(nativeAd.getAdSocialContext());
        if (!nativeAd.hasCallToAction()) {
            i = 4;
        }
        nativeAdCallToAction.setVisibility(i);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeAd.registerViewForInteraction((View) adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }
}
