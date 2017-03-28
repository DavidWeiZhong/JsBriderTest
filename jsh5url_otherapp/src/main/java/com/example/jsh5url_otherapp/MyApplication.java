package com.example.jsh5url_otherapp;

import android.app.Application;

import com.zxinsight.MWConfiguration;
import com.zxinsight.MagicWindowSDK;
import com.zxinsight.Session;

/**
 * Created by ${邱伟中} on 2016/12/23 0023.上午 10:03
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Session.setAutoSession(this);
        MWConfiguration config = new MWConfiguration(this);
//设置渠道，非必须（渠道推荐在AndroidManifest.xml内填写）
        config.setChannel("你的渠道名称")
                //开启Debug模式，显示Log，release时注意关闭
                .setDebugModel(true)
                //带有Fragment的页面。具体查看2.2.2
                . setPageTrackWithFragment(true)
                //设置分享方式，如果之前有集成sharesdk，可在此开启
                . setSharePlatform (MWConfiguration. ORIGINAL);
        MagicWindowSDK.initSDK(config);
    }
}
