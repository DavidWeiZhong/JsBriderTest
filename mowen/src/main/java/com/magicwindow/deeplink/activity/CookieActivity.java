package com.magicwindow.deeplink.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.magicwindow.deeplink.R;

public class CookieActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookie);
    }


    public static void cookieSync(Context context) {
        String sessionid="jssession=fklsjfsdafasdfdsafsd";
        CookieSyncManager syncManager = CookieSyncManager.createInstance(context);
        CookieManager cm = CookieManager.getInstance();


    }
}
