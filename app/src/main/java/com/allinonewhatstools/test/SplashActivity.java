package com.allinonewhatstools.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.allinonewhatstools.test.Ads.Inter_Ads;
import com.allinonewhatstools.test.Ads.Model_Key_Ad;
import com.allinonewhatstools.test.Ads.AppOpen_App;
import com.allinonewhatstools.test.Ads.Banner_Ads;
import com.allinonewhatstools.test.Ads.NativeBanner_Ads;
import com.allinonewhatstools.test.Ads.Native_Ads;
import com.allinonewhatstools.test.Ads.Close_App_open;
import com.allinonewhatstools.test.ApiManage.APIClient;
import com.allinonewhatstools.test.ApiManage.GitHubService;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    public static int AdsCOUNTER = 0;
    public static String AdsFlag = null;
    public static int AdsMAX = 0;
    private static final String CHANNEL_ID = "vpnmaster";
    public static String adBanner;
    public static String adInter;
    public static String adNative;
    public static String adOpenAd;
    public static String adReward;
    public static int adsBackpressCount = 0;
    public static int adsBackpressPercentageCount = 0;
    public static ArrayList<Model_Key_Ad> adsKeyModelArrayList = new ArrayList<>();
    public static int adsOpenCounter = 0;
    public static int adsOpenMAX = 0;
    public static int adsTimer;
    public static AppOpen_App appOpenManager;
    public static String basehost;
    public static String careerId;
    public static String fbBanner;
    public static String fbInter;
    public static String fbNative;
    public static boolean forceUpdate;
    public static boolean isAVpn;
    public static boolean isAdInBackpress;
    public static boolean isAdmob;
    public static boolean isAppOpen;
    public static boolean isAppOpenOnIn;
    public static boolean isAttractButton;
    public static boolean isBackScreenShow;
    public static boolean isBannerShow;
    public static boolean isBigNatShow;
    public static boolean isFacebook;
    public static boolean isGetstartShow;
    public static boolean isInsterShow;
    public static boolean isLimitedByads;
    public static boolean isMove;
    public static boolean isNativeShow;
    public static boolean isNotAllLimited;
    public static boolean isQureka;
    public static boolean isSmallNatShow;
    public static boolean isTimerShow;
    public static boolean isUpdateAvailable;
    public static String moveLink;
    public static String privacyPolicy;
    public static String qurekaLink;
    public static int unused = 0;
    public static String updatelink;
    int adshowed = 0;
    int count = 0;
    Close_App_open onAppOpenClose;
//    @BindView(2131362308)
    RelativeLayout parent;
    private String selectedCountry = "";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_splash);
        getWindow().setFlags(1024, 1024);
        ButterKnife.bind((Activity) this);
        Log.e("hello1 : ", "Error in Splash");
        LoginAPp();
    }

    private void LoginAPp() {
        if (appOpenManager != null) {
            Log.e("hello2 : ", "Error in Splash");
            appOpenManager.DestroyAllAds();
            this.adshowed = 0;
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        ((GitHubService) APIClient.getRetrofitInstance().create(GitHubService.class)).listRepos().enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONArray(response.body().string()).getJSONObject(0);
                    SplashActivity.adsKeyModelArrayList.clear();
                    SplashActivity.isAVpn = jsonObject.getBoolean("isAVpn");
                    boolean isByLocation = jsonObject.getBoolean("isByLocation");
                    SplashActivity.basehost = jsonObject.getString("basehost");
                    SplashActivity.careerId = jsonObject.getString("careerId");
                    JSONArray bannedcountry = jsonObject.getJSONArray("bannedcountry");
                    JSONArray jSONArray = jsonObject.getJSONArray("country");
                    String countryCodeValue = ((TelephonyManager) SplashActivity.this.getSystemService("phone")).getNetworkCountryIso();
                    if (!isByLocation) {
                        SplashActivity.isAVpn = false;
                    } else if (countryCodeValue != null) {
                        int ij = 0;
                        while (true) {
                            if (ij >= bannedcountry.length()) {
                                break;
                            } else if (countryCodeValue.toLowerCase().trim().matches(bannedcountry.getString(ij))) {
                                SplashActivity.isAVpn = false;
                                break;
                            } else {
                                ij++;
                            }
                        }
                    } else {
                        SplashActivity.isAVpn = false;
                    }
                    SplashActivity.this.GoAfterConnected(jsonObject);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("ErrorRes", "onErrorResponse: " + t.toString());
            }
        });
    }

    public void GoAfterConnected(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("allAdsAdmob");
        adsOpenMAX = jsonObject.getInt("adsOpenCount");
        adsBackpressCount = jsonObject.getInt("adsBackpressCount");
        isAppOpenOnIn = jsonObject.getBoolean("isAppOpenOnIn");
        isTimerShow = jsonObject.getBoolean("isTimerShow");
        isBackScreenShow = jsonObject.getBoolean("isBackScreenShow");
        isAdInBackpress = jsonObject.getBoolean("isAdInBackpress");
        forceUpdate = jsonObject.getBoolean("forceUpdate");
        isUpdateAvailable = jsonObject.getBoolean("isUpdateAvailable");
        isMove = jsonObject.getBoolean("isMove");
        isAppOpen = jsonObject.getBoolean("isAppOpen");
        isBigNatShow = jsonObject.getBoolean("isBigNatShow");
        isSmallNatShow = jsonObject.getBoolean("isSmallNatShow");
        isInsterShow = jsonObject.getBoolean("isInsterShow");
        isNativeShow = jsonObject.getBoolean("isNativeShow");
        isBannerShow = jsonObject.getBoolean("isBannerShow");
        isGetstartShow = jsonObject.getBoolean("isGetstartShow");
        moveLink = jsonObject.getString("moveLink");
        updatelink = jsonObject.getString("updatelink");
        isAttractButton = jsonObject.getBoolean("isAttractButton");
        isLimitedByads = jsonObject.getBoolean("isLimitedByads");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            adOpenAd = jsonObject1.getString("adOpenAd");
            adBanner = jsonObject1.getString("adBanner");
            adInter = jsonObject1.getString("adInter");
            adNative = jsonObject1.getString("adNative");
            adReward = jsonObject1.getString("adReward");
            Model_Key_Ad adsKeyModel = new Model_Key_Ad();
            adsKeyModel.setAdBanner(adBanner);
            adsKeyModel.setAdInter(adInter);
            adsKeyModel.setAdNative(adNative);
            adsKeyModel.setAdReward(adReward);
            adsKeyModel.setAdOpenAd(adOpenAd);
            if (jsonArray.length() == 1) {
                adsKeyModelArrayList.add(adsKeyModel);
                adsKeyModelArrayList.add(adsKeyModel);
            } else {
                adsKeyModelArrayList.add(adsKeyModel);
            }
        }
        isAdmob = jsonObject.getBoolean("isAdmob");
        AdsFlag = jsonObject.getString("AdsFlag");
        adsTimer = jsonObject.getInt("adsTimer");
        AdsMAX = jsonObject.getInt("adsCount");
        isFacebook = jsonObject.getBoolean("isFacebook");
        isQureka = jsonObject.getBoolean("isQureka");
        qurekaLink = jsonObject.getString("qurekaLink");
        privacyPolicy = jsonObject.getString("privacyPolicy");
        fbBanner = jsonObject.getString("fbBanner");
        fbNative = jsonObject.getString("fbNative");
        fbInter = jsonObject.getString("fbInter");
        isLimitedByads = jsonObject.getBoolean("isLimitedByads");
        if (isBannerShow) {
            Banner_Ads.LoadAdBanner();
            Banner_Ads.LoadFbBanner();
            Banner_Ads.LoadFbBanner2();
        }
        if (isInsterShow) {
            Inter_Ads.LoadFbInter();
            Inter_Ads.LoadAdInter();
        }
        if (isSmallNatShow) {
            NativeBanner_Ads.LoadAdNative();
            NativeBanner_Ads.LoadFbNative();
            NativeBanner_Ads.LoadFbNative01();
        }
        if (isBigNatShow) {
            Native_Ads.LoadAdNative();
            Native_Ads.LoadFbNative();
            Native_Ads.LoadFbNative01();
        }
        this.onAppOpenClose = new Close_App_open() {
            public void OnAppOpenClose() {
                if (SplashActivity.this.adshowed == 0) {
                    SplashActivity.this.adshowed = 1;
                    SplashActivity.this.NextScreen();
                }
            }

            public void OnAppOpenFailToLoad() {
                if (SplashActivity.this.adshowed == 0) {
                    SplashActivity.this.adshowed = 1;
                    SplashActivity.this.NextScreen();
                }
            }
        };
        this.adshowed = 0;
        if (!isAdmob || !isAppOpen) {
            this.adshowed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    SplashActivity.this.NextScreen();
                }
            }, 2000);
            return;
        }
        appOpenManager = new AppOpen_App(MyApplication.getInstance(), this.onAppOpenClose);
    }

    public void NextScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
