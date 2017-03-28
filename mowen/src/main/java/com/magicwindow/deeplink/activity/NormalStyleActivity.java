package com.magicwindow.deeplink.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.base.BaseActivity;
import com.magicwindow.deeplink.config.Constant;
import com.magicwindow.deeplink.fragment.BannerFragment;
import com.magicwindow.deeplink.view.CircleIndicator;
import com.zxinsight.MWImageView;
import com.zxinsight.MarketingHelper;

import java.util.HashMap;

public class NormalStyleActivity extends BaseActivity {

    private ViewPager pager;

    private MarketingHelper marketingHelper;

    private MWImageView bottomImg;

    private MWBroadCastReceiver receiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.magicwindow.deeplink.R.layout.activity_normal_style);

        marketingHelper = MarketingHelper.currentMarketing(this);
        receiver = new MWBroadCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.magicwindow.marketing.update.MW_MESSAGE");
        registerReceiver(receiver, filter);

        initMWView();

        pager = (ViewPager) findViewById(com.magicwindow.deeplink.R.id.pager);
        //如果魔窗未开启，则隐藏第二个魔窗位 isActive()方法用来判断魔窗位是否开启
        if (marketingHelper.isActive(Constant.MW_NORMAL_STYLE02)) {
            pager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), 3));
        } else {
            pager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), 2));
        }

        CircleIndicator indicator = (CircleIndicator) findViewById(com.magicwindow.deeplink.R.id.indicator);
        indicator.setViewPager(pager);
    }

    /**
     * 当魔窗后台活动更新时，会发送MWSSAGE消息，通过MWBroadCastReceiver来刷新活动展示
     */
    private void initMWView() {

        //// TODO: 16/3/4 具体请参照下面
        MWImageView img_1 = (MWImageView) findViewById(R.id.img_1);
        img_1.bindEvent(Constant.MW_NORMAL_STYLE01);

        MWImageView img_2 = (MWImageView) findViewById(R.id.img_2);
        img_2.bindEvent(Constant.MW_NORMAL_STYLE02);

        /**
         *
         * TODO: 演示带mLink动态参数的bind
         * 如果此魔窗位需要通过mLink跳转到其他APP的具体页面，并且需要传动态参数。
         * 则用bindEventWithMLink()。
         */
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("key", "value");
        MWImageView img_3 = (MWImageView) findViewById(R.id.img_3);
        img_3.bindEventWithMLink(Constant.MW_NORMAL_STYLE03, param, param);


        //TODO: 如果魔窗位只需要显示一张图片，推荐此方式，最简单
        bottomImg = (MWImageView) findViewById(com.magicwindow.deeplink.R.id.bottom_img);
        bottomImg.bindEvent(Constant.MW_NORMAL_STYLE03);
        //end

    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        private int mCount = 3;

        public FragmentAdapter(FragmentManager fm, int count) {
            super(fm);
            mCount = count;
        }

        @Override
        public Fragment getItem(int position) {
            return BannerFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mCount;
        }
    }

    @Override
    public void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    public class MWBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.e("aaron", "action = " + action);
            if (action.equals("com.magicwindow.marketing.update.MW_MESSAGE")) {
                //todo: 你的代码 这个是活动webview onResume消息
                initMWView();
            }
        }
    }

}
