package com.rjfun.cordova.ad;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.rjfun.cordova.ext.CordovaPluginExt;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class GenericAdPlugin
  extends CordovaPluginExt
{
  private static final String LOGTAG = "GenericAdPlugin";
  public static final String ACTION_SET_OPTIONS = "setOptions";
  public static final String ACTION_CREATE_BANNER = "createBanner";
  public static final String ACTION_REMOVE_BANNER = "removeBanner";
  public static final String ACTION_HIDE_BANNER = "hideBanner";
  public static final String ACTION_SHOW_BANNER = "showBanner";
  public static final String ACTION_SHOW_BANNER_AT_XY = "showBannerAtXY";
  public static final String ACTION_PREPARE_INTERSTITIAL = "prepareInterstitial";
  public static final String ACTION_SHOW_INTERSTITIAL = "showInterstitial";
  public static final String ADTYPE_BANNER = "banner";
  public static final String ADTYPE_INTERSTITIAL = "interstitial";
  public static final String ADTYPE_NATIVE = "native";
  public static final String EVENT_BANNER_RECEIVE = "onBannerReceive";
  public static final String EVENT_BANNER_FAILRECEIVE = "onBannerFailedReceive";
  public static final String EVENT_BANNER_PRESENT = "onBannerPresent";
  public static final String EVENT_BANNER_LEAVEAPP = "onBannerLeaveApp";
  public static final String EVENT_BANNER_DISMISS = "onBannerDismiss";
  public static final String EVENT_BANNER_WILLPRESENT = "onBannerWillPresent";
  public static final String EVENT_BANNER_WILLDISMISS = "onBannerWillDismiss";
  public static final String EVENT_INTERSTITIAL_RECEIVE = "onInterstitialReceive";
  public static final String EVENT_INTERSTITIAL_FAILRECEIVE = "onInterstitialFailedReceive";
  public static final String EVENT_INTERSTITIAL_PRESENT = "onInterstitialPresent";
  public static final String EVENT_INTERSTITIAL_LEAVEAPP = "onInterstitialLeaveApp";
  public static final String EVENT_INTERSTITIAL_DISMISS = "onInterstitialDismiss";
  public static final String EVENT_INTERSTITIAL_WILLPRESENT = "onInterstitialWillPresent";
  public static final String EVENT_INTERSTITIAL_WILLDISMISS = "onInterstitialWillDismiss";
  public static final String EVENT_AD_LOADED = "onAdLoaded";
  public static final String EVENT_AD_FAILLOAD = "onAdFailLoad";
  public static final String EVENT_AD_PRESENT = "onAdPresent";
  public static final String EVENT_AD_LEAVEAPP = "onAdLeaveApp";
  public static final String EVENT_AD_DISMISS = "onAdDismiss";
  public static final String EVENT_AD_WILLPRESENT = "onAdWillPresent";
  public static final String EVENT_AD_WILLDISMISS = "onAdWillDismiss";
  public static final String ADSIZE_BANNER = "BANNER";
  public static final String ADSIZE_SMART_BANNER = "SMART_BANNER";
  public static final String ADSIZE_FULL_BANNER = "FULL_BANNER";
  public static final String ADSIZE_MEDIUM_RECTANGLE = "MEDIUM_RECTANGLE";
  public static final String ADSIZE_LEADERBOARD = "LEADERBOARD";
  public static final String ADSIZE_SKYSCRAPER = "SKYSCRAPER";
  public static final String ADSIZE_CUSTOM = "CUSTOM";
  public static final String OPT_ADID = "adId";
  public static final String OPT_AUTO_SHOW = "autoShow";
  public static final String OPT_LICENSE = "license";
  public static final String OPT_IS_TESTING = "isTesting";
  public static final String OPT_LOG_VERBOSE = "logVerbose";
  public static final String OPT_AD_SIZE = "adSize";
  public static final String OPT_WIDTH = "width";
  public static final String OPT_HEIGHT = "height";
  public static final String OPT_OVERLAP = "overlap";
  public static final String OPT_ORIENTATION_RENEW = "orientationRenew";
  public static final String OPT_POSITION = "position";
  public static final String OPT_X = "x";
  public static final String OPT_Y = "y";
  public static final String OPT_BANNER_ID = "bannerId";
  public static final String OPT_INTERSTITIAL_ID = "interstitialId";
  protected String bannerId = "";
  protected String interstialId = "";
  public static final int NO_CHANGE = 0;
  public static final int TOP_LEFT = 1;
  public static final int TOP_CENTER = 2;
  public static final int TOP_RIGHT = 3;
  public static final int LEFT = 4;
  public static final int CENTER = 5;
  public static final int RIGHT = 6;
  public static final int BOTTOM_LEFT = 7;
  public static final int BOTTOM_CENTER = 8;
  public static final int BOTTOM_RIGHT = 9;
  public static final int POS_XY = 10;
  protected static final int TEST_TRAFFIC = 3;
  protected boolean testTraffic = new Random().nextInt(100) <= 3;
  protected boolean licenseValidated = false;
  protected boolean isTesting = false;
  protected boolean logVerbose = false;
  protected int adWidth = 0;
  protected int adHeight = 0;
  protected boolean overlap = false;
  protected boolean orientationRenew = true;
  protected int adPosition = 8;
  protected int posX = 0;
  protected int posY = 0;
  protected boolean autoShowBanner = true;
  protected boolean autoShowInterstitial = false;
  protected OrientationEventListener orientation = null;
  protected int widthOfView = 0;
  protected RelativeLayout overlapLayout = null;
  protected View adView = null;
  protected Object interstitialAd = null;
  protected boolean isWebViewInLinearLayout = false;
  protected ViewGroup originalParent = null;
  protected ViewGroup.LayoutParams originalLayoutParams = null;
  protected LinearLayout splitLayout = null;
  protected boolean bannerVisible = false;
  
  public boolean execute(String action, JSONArray inputs, CallbackContext callbackContext)
    throws JSONException
  {
    PluginResult result = null;
    if ("setOptions".equals(action))
    {
      JSONObject options = inputs.optJSONObject(0);
      setOptions(options);
      result = new PluginResult(PluginResult.Status.OK);
    }
    else if ("createBanner".equals(action))
    {
      JSONObject options = inputs.optJSONObject(0);
      if (options.length() > 1) {
        setOptions(options);
      }
      String adId = options.optString("adId");
      boolean autoShow = options.has("autoShow") ? options.optBoolean("autoShow") : true;
      
      boolean isOk = createBanner(adId, autoShow);
      result = new PluginResult(isOk ? PluginResult.Status.OK : PluginResult.Status.ERROR);
    }
    else if ("removeBanner".equals(action))
    {
      removeBanner();
      result = new PluginResult(PluginResult.Status.OK);
    }
    else if ("hideBanner".equals(action))
    {
      hideBanner();
      result = new PluginResult(PluginResult.Status.OK);
    }
    else if ("showBanner".equals(action))
    {
      int nPos = inputs.optInt(0);
      showBanner(nPos, 0, 0);
      result = new PluginResult(PluginResult.Status.OK);
    }
    else if ("showBannerAtXY".equals(action))
    {
      JSONObject args = inputs.optJSONObject(0);
      int x = args.optInt("x");
      int y = args.optInt("y");
      showBanner(10, x, y);
      result = new PluginResult(PluginResult.Status.OK);
    }
    else if ("prepareInterstitial".equals(action))
    {
      JSONObject options = inputs.optJSONObject(0);
      if (options.length() > 1) {
        setOptions(options);
      }
      String adId = options.optString("adId");
      boolean autoShow = options.has("autoShow") ? options.optBoolean("autoShow") : false;
      boolean isOk = prepareInterstitial(adId, autoShow);
      result = new PluginResult(isOk ? PluginResult.Status.OK : PluginResult.Status.ERROR);
    }
    else if ("showInterstitial".equals(action))
    {
      showInterstitial();
      result = new PluginResult(PluginResult.Status.OK);
    }
    else
    {
      Log.w("GenericAdPlugin", String.format("Invalid action passed: %s", new Object[] { action }));
      result = new PluginResult(PluginResult.Status.INVALID_ACTION);
    }
    if (result != null) {
      sendPluginResult(result, callbackContext);
    }
    return true;
  }
  
  public void fireEvent(String obj, String eventName, String jsonData)
  {
    if (this.isTesting) {
      Log.d("GenericAdPlugin", obj + ", " + eventName + ", " + jsonData);
    }
    super.fireEvent(obj, eventName, jsonData);
  }
  
  protected void pluginInitialize()
  {
    super.pluginInitialize();
    if (new Random().nextInt(100) <= 3) {
      this.testTraffic = true;
    }
    this.orientation = new OrientationEventWatcher(getActivity());
    this.orientation.enable();
  }
  
  private class OrientationEventWatcher
    extends OrientationEventListener
  {
    public OrientationEventWatcher(Context context)
    {
      super();
    }
    
    public void onOrientationChanged(int orientation)
    {
      GenericAdPlugin.this.checkOrientationChange();
    }
  }
  
  public void checkOrientationChange()
  {
    int w = getView().getWidth();
    if (w == this.widthOfView) {
      return;
    }
    this.widthOfView = w;
    
    onViewOrientationChanged();
  }
  
  public void setOptions(JSONObject options)
  {
    if (options != null)
    {
      if (options.has("license")) {
        validateLicense(options.optString("license"));
      }
      if (options.has("isTesting")) {
        this.isTesting = options.optBoolean("isTesting");
      }
      if (options.has("logVerbose")) {
        this.logVerbose = options.optBoolean("logVerbose");
      }
      if (options.has("width")) {
        this.adWidth = options.optInt("width");
      }
      if (options.has("height")) {
        this.adHeight = options.optInt("height");
      }
      if (options.has("overlap")) {
        this.overlap = options.optBoolean("overlap");
      }
      if (options.has("orientationRenew")) {
        this.orientationRenew = options.optBoolean("orientationRenew");
      }
      if (options.has("position")) {
        this.adPosition = options.optInt("position");
      }
      if (options.has("x")) {
        this.posX = options.optInt("x");
      }
      if (options.has("y")) {
        this.posY = options.optInt("y");
      }
      if (options.has("bannerId")) {
        this.bannerId = options.optString("bannerId");
      }
      if (options.has("interstitialId")) {
        this.interstialId = options.optString("interstitialId");
      }
    }
  }
  
  @SuppressLint({"DefaultLocale"})
  private void validateLicense(String license)
  {
    String[] fields = license.split("/");
    if (fields.length >= 2)
    {
      String userid = fields[0];
      String key = fields[1];
      String genKey = md5("licensed to " + userid + " by floatinghotpot");
      String genKey2 = md5(__getProductShortName().toLowerCase() + " licensed to " + userid + " by floatinghotpot");
      this.licenseValidated = ((key.equalsIgnoreCase(genKey)) || (key.equalsIgnoreCase(genKey2)));
      if (this.licenseValidated) {
        this.testTraffic = false;
      }
    }
    if (this.licenseValidated) {
      Log.w("GenericAdPlugin", "valid license");
    }
  }
  
  public final String md5(String s)
  {
    try
    {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(s.getBytes());
      byte[] messageDigest = digest.digest();
      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < messageDigest.length; i++)
      {
        String h = Integer.toHexString(0xFF & messageDigest[i]);
        while (h.length() < 2) {
          h = "0" + h;
        }
        hexString.append(h);
      }
      return hexString.toString();
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
    return "";
  }
  
  public boolean createBanner(String adId, boolean autoShow)
  {
    Log.d("GenericAdPlugin", "createBanner: " + adId + ", " + autoShow);
    
    this.autoShowBanner = autoShow;
    if ((adId != null) && (adId.length() > 0)) {
      this.bannerId = adId;
    } else {
      adId = this.bannerId;
    }
    if (this.testTraffic) {
      adId = __getTestBannerId();
    }
    final String strAdUnitId = adId;
    Activity activity = getActivity();
    activity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (GenericAdPlugin.this.adView == null) {
          GenericAdPlugin.this.adView = GenericAdPlugin.this.__createAdView(strAdUnitId);
        }
        if (GenericAdPlugin.this.adView.getParent() != null) {
          ((ViewGroup)GenericAdPlugin.this.adView.getParent()).removeView(GenericAdPlugin.this.adView);
        }
        GenericAdPlugin.this.bannerVisible = false;
        
        GenericAdPlugin.this.isWebViewInLinearLayout = (GenericAdPlugin.this.getView().getParent() instanceof LinearLayout);
        if (GenericAdPlugin.this.isWebViewInLinearLayout) {
          Log.d("GenericAdPlugin", "cordova-android 3.x, webview in linearlayout");
        } else {
          Log.d("GenericAdPlugin", "cordova-android 4.0+, webview in framelayout");
        }
        GenericAdPlugin.this.__loadAdView(GenericAdPlugin.this.adView);
      }
    });
    return true;
  }
  
  public void removeBanner()
  {
    Log.d("GenericAdPlugin", "removeBanner");
    
    Activity activity = getActivity();
    activity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (GenericAdPlugin.this.adView != null)
        {
          GenericAdPlugin.this.hideBanner();
          GenericAdPlugin.this.__destroyAdView(GenericAdPlugin.this.adView);
          GenericAdPlugin.this.adView = null;
        }
        GenericAdPlugin.this.bannerVisible = false;
      }
    });
  }
  
  public void showBanner(final int argPos, final int argX, final int argY)
  {
    Log.d("GenericAdPlugin", "showBanner");
    if (this.adView == null)
    {
      Log.e("GenericAdPlugin", "banner is null, call createBanner() first.");
      return;
    }
    final Activity activity = getActivity();
    activity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        View mainView = GenericAdPlugin.this.getView();
        
        ViewGroup adParent = (ViewGroup)GenericAdPlugin.this.adView.getParent();
        if (adParent != null) {
          adParent.removeView(GenericAdPlugin.this.adView);
        }
        int bw = GenericAdPlugin.this.__getAdViewWidth(GenericAdPlugin.this.adView);
        int bh = GenericAdPlugin.this.__getAdViewHeight(GenericAdPlugin.this.adView);
        

        ViewGroup rootView = (ViewGroup)mainView.getRootView();
        int rw = rootView.getWidth();int rh = rootView.getHeight();
        

        Log.w("GenericAdPlugin", "show banner, overlap:" + GenericAdPlugin.this.overlap + ", position: " + argPos);
        if (GenericAdPlugin.this.overlap)
        {
          int x = GenericAdPlugin.this.posX;int y = GenericAdPlugin.this.posY;
          int ww = mainView.getWidth();int wh = mainView.getHeight();
          if ((argPos >= 1) && (argPos <= 9)) {
            switch ((argPos - 1) % 3)
            {
            case 0: 
              x = 0; break;
            case 1: 
              x = (ww - bw) / 2; break;
            case 2: 
              x = ww - bw;
            }
          }
          switch ((argPos - 1) / 3)
          {
          case 0: 
            y = 0; break;
          case 1: 
            y = (wh - bh) / 2; break;
          case 2: 
            y = wh - bh;
          default: 
            break;
            if (argPos == 10)
            {
              x = argX;
              y = argY;
            }
            break;
          }
          int[] offsetRootView = new int[2];int[] offsetWebView = new int[2];
          rootView.getLocationOnScreen(offsetRootView);
          mainView.getLocationOnScreen(offsetWebView);
          
          x += offsetWebView[0] - offsetRootView[0];
          y += offsetWebView[1] - offsetRootView[1];
          

          RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(bw, bh);
          params.leftMargin = x;
          params.topMargin = y;
          if (GenericAdPlugin.this.overlapLayout == null)
          {
            GenericAdPlugin.this.overlapLayout = new RelativeLayout(activity);
            rootView.addView(GenericAdPlugin.this.overlapLayout, new RelativeLayout.LayoutParams(
              -1, 
              -1));
            GenericAdPlugin.this.overlapLayout.bringToFront();
          }
          GenericAdPlugin.this.overlapLayout.addView(GenericAdPlugin.this.adView, params);
        }
        else
        {
          FrameLayout v = new FrameLayout(GenericAdPlugin.this.getActivity());
          v.addView(GenericAdPlugin.this.adView, new FrameLayout.LayoutParams(
            -2, 
            -2));
          if (!GenericAdPlugin.this.isWebViewInLinearLayout)
          {
            if (GenericAdPlugin.this.originalParent == null)
            {
              GenericAdPlugin.this.originalParent = ((ViewGroup)mainView.getParent());
              GenericAdPlugin.this.originalLayoutParams = mainView.getLayoutParams();
            }
            if (GenericAdPlugin.this.splitLayout == null)
            {
              GenericAdPlugin.this.splitLayout = new LinearLayout(activity);
              GenericAdPlugin.this.splitLayout.setOrientation(1);
              GenericAdPlugin.this.originalParent.addView(GenericAdPlugin.this.splitLayout, new FrameLayout.LayoutParams(
                -1, 
                -1));
              GenericAdPlugin.this.splitLayout.bringToFront();
            }
            GenericAdPlugin.this.splitLayout.removeAllViews();
            
            GenericAdPlugin.this.originalParent.removeView(mainView);
            GenericAdPlugin.this.splitLayout.addView(mainView, new LinearLayout.LayoutParams(
              -1, 
              GenericAdPlugin.this.originalParent.getHeight() - bh));
          }
          ViewGroup parentView = (ViewGroup)mainView.getParent();
          if (argPos <= 3) {
            parentView.addView(v, 0);
          } else {
            parentView.addView(v);
          }
        }
        GenericAdPlugin.this.adView.setVisibility(0);
        GenericAdPlugin.this.adView.bringToFront();
        
        GenericAdPlugin.this.__resumeAdView(GenericAdPlugin.this.adView);
        GenericAdPlugin.this.bannerVisible = true;
      }
    });
  }
  
  public void hideBanner()
  {
    Log.d("GenericAdPlugin", "hideBanner");
    if (this.adView == null) {
      return;
    }
    this.autoShowBanner = false;
    
    Activity activity = getActivity();
    activity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        GenericAdPlugin.this.adView.setVisibility(8);
        if (GenericAdPlugin.this.adView.getParent() != null) {
          ((ViewGroup)GenericAdPlugin.this.adView.getParent()).removeView(GenericAdPlugin.this.adView);
        }
        if (!GenericAdPlugin.this.isWebViewInLinearLayout)
        {
          if (GenericAdPlugin.this.splitLayout != null) {
            GenericAdPlugin.this.splitLayout.removeAllViews();
          }
          if (GenericAdPlugin.this.originalParent != null)
          {
            View mainView = GenericAdPlugin.this.getView();
            if (mainView.getParent() != null) {
              ((ViewGroup)mainView.getParent()).removeView(mainView);
            }
            mainView.setLayoutParams(GenericAdPlugin.this.originalLayoutParams);
            GenericAdPlugin.this.originalParent.addView(mainView);
          }
        }
        GenericAdPlugin.this.__pauseAdView(GenericAdPlugin.this.adView);
        GenericAdPlugin.this.bannerVisible = false;
      }
    });
  }
  
  public boolean prepareInterstitial(String adId, boolean autoShow)
  {
    Log.d("GenericAdPlugin", "prepareInterstitial: " + adId + ", " + autoShow);
    
    this.autoShowInterstitial = autoShow;
    if ((adId != null) && (adId.length() > 0)) {
      this.interstialId = adId;
    } else {
      adId = this.interstialId;
    }
    if (this.testTraffic) {
      adId = __getTestInterstitialId();
    }
    final String strUnitId = adId;
    Activity activity = getActivity();
    activity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (GenericAdPlugin.this.interstitialAd != null)
        {
          GenericAdPlugin.this.__destroyInterstitial(GenericAdPlugin.this.interstitialAd);
          GenericAdPlugin.this.interstitialAd = null;
        }
        if (GenericAdPlugin.this.interstitialAd == null)
        {
          GenericAdPlugin.this.interstitialAd = GenericAdPlugin.this.__createInterstitial(strUnitId);
          GenericAdPlugin.this.__loadInterstitial(GenericAdPlugin.this.interstitialAd);
        }
      }
    });
    return false;
  }
  
  public void showInterstitial()
  {
    Log.d("GenericAdPlugin", "showInterstitial");
    if (this.interstitialAd == null)
    {
      this.autoShowInterstitial = true;
      prepareInterstitial(this.interstialId, true);
    }
    Activity activity = getActivity();
    activity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        GenericAdPlugin.this.__showInterstitial(GenericAdPlugin.this.interstitialAd);
      }
    });
  }
  
  public void removeInterstitial()
  {
    if (this.interstitialAd != null)
    {
      Activity activity = getActivity();
      activity.runOnUiThread(new Runnable()
      {
        public void run()
        {
          GenericAdPlugin.this.__destroyInterstitial(GenericAdPlugin.this.interstitialAd);
        }
      });
      this.interstitialAd = null;
    }
  }
  
  public void onPause(boolean multitasking)
  {
    if (this.adView != null) {
      __pauseAdView(this.adView);
    }
    super.onPause(multitasking);
  }
  
  public void onResume(boolean multitasking)
  {
    super.onResume(multitasking);
    if (this.adView != null) {
      __resumeAdView(this.adView);
    }
  }
  
  public void onDestroy()
  {
    if (this.adView != null)
    {
      __destroyAdView(this.adView);
      this.adView = null;
    }
    if (this.interstitialAd != null)
    {
      __destroyInterstitial(this.interstitialAd);
      this.interstitialAd = null;
    }
    if (this.overlapLayout != null)
    {
      ViewGroup parentView = (ViewGroup)this.overlapLayout.getParent();
      if (parentView != null) {
        parentView.removeView(this.overlapLayout);
      }
      this.overlapLayout = null;
    }
    super.onDestroy();
  }
  
  public void onViewOrientationChanged()
  {
    if (this.isTesting) {
      Log.d("GenericAdPlugin", "Orientation Changed");
    }
    if ((this.adView != null) && (this.bannerVisible)) {
      if (this.orientationRenew)
      {
        if (this.isTesting) {
          Log.d("GenericAdPlugin", "renew banner on orientation change");
        }
        removeBanner();
        createBanner(this.bannerId, true);
      }
      else
      {
        if (this.isTesting) {
          Log.d("GenericAdPlugin", "adjust banner position");
        }
        showBanner(this.adPosition, this.posX, this.posY);
      }
    }
  }
  
  protected void fireAdEvent(String event, String adType)
  {
    String obj = __getProductShortName();
    String json = String.format("{'adNetwork':'%s','adType':'%s','adEvent':'%s'}", new Object[] { obj, adType, event });
    fireEvent(obj, event, json);
  }
  
  @SuppressLint({"DefaultLocale"})
  protected void fireAdErrorEvent(String event, int errCode, String errMsg, String adType)
  {
    String obj = __getProductShortName();
    String json = String.format("{'adNetwork':'%s','adType':'%s','adEvent':'%s','error':%d,'reason':'%s'}", new Object[] { obj, adType, event, Integer.valueOf(errCode), errMsg });
    fireEvent(obj, event, json);
  }
  
  protected abstract String __getProductShortName();
  
  protected abstract String __getTestBannerId();
  
  protected abstract String __getTestInterstitialId();
  
  protected abstract View __createAdView(String paramString);
  
  protected abstract int __getAdViewWidth(View paramView);
  
  protected abstract int __getAdViewHeight(View paramView);
  
  protected abstract void __loadAdView(View paramView);
  
  protected abstract void __pauseAdView(View paramView);
  
  protected abstract void __resumeAdView(View paramView);
  
  protected abstract void __destroyAdView(View paramView);
  
  protected abstract Object __createInterstitial(String paramString);
  
  protected abstract void __loadInterstitial(Object paramObject);
  
  protected abstract void __showInterstitial(Object paramObject);
  
  protected abstract void __destroyInterstitial(Object paramObject);
}
