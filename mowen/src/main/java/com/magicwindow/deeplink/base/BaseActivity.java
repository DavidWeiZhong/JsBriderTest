package com.magicwindow.deeplink.base;

import android.support.v4.app.FragmentActivity;

import com.zxinsight.Session;
import com.zxinsight.TrackAgent;

/**
 * User: Aaron.Liu
 * Date: 15/7/6
 * Time: 17:46
 */
public class BaseActivity extends FragmentActivity {


    @Override
    protected void onPause() {
        //TODO：必加
        Session.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        //TODO：必加
        Session.onResume(this);
        super.onResume();
    }
}
