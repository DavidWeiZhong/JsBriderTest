package com.magicwindow.deeplink.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.base.BaseActivity;
import com.zxinsight.MWConfiguration;
import com.zxinsight.MagicWindowSDK;
import com.zxinsight.TrackAgent;

/**
 * Created by aaron on 16/4/10.
 */
public class SplashActivity extends BaseActivity {

    private static String TAG = "SplashActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        //mw: 初始化魔窗
        initMw();
        gotoHomeActivity();
    }

    private void initMw() {
        // TODO:初始化魔窗，此函数在第一个activity或者application类内调用。放在application内更优。
        MWConfiguration config = new MWConfiguration(this);
        config.setChannel("WanDouJia")
                .setDebugModel(true)
                .setPageTrackWithFragment(true)
                .setWebViewBroadcastOpen(true)
                .setCustomWebViewTitleBarOn()
                .setSharePlatform(MWConfiguration.ORIGINAL)
                .setMLinkOpen();

        MagicWindowSDK.initSDK(config);
    }

    private void gotoHomeActivity() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        /* compiled code */
        if (event.getAction() == MotionEvent.ACTION_UP) {
            startActivity(new Intent(this, HomeActivity.class));
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onPause() {
        TrackAgent.currentEvent().onPageEnd("引导页");
        super.onPause();
    }

    @Override
    public void onResume() {
        TrackAgent.currentEvent().onPageStart("引导页");

        super.onResume();
    }


}
