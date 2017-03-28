package com.magicwindow.deeplink.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.zxinsight.MWConfiguration;
import com.zxinsight.MagicWindowSDK;

/**
 * Created by aaron on 15/7/4.
 */
public class MWApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        //TODO: mw 初始化魔窗,此处跟SplashActivity二选一，放在此处更优。
//        initMw();
        initImageLoader(getApplicationContext());
    }


    private void initMw() {
        // TODO:初始化魔窗，此函数在第一个activity或者application类内调用。
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

    public static void initImageLoader(Context context) {
        DisplayImageOptions option = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.defaultDisplayImageOptions(option);
//        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
