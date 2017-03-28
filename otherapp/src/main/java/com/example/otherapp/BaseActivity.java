package com.example.otherapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.zxinsight.MLink;
import com.zxinsight.MagicWindowSDK;

/**
 * Created by ${邱伟中} on 2016/12/26 0026.上午 10:06
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Uri mLink = getIntent().getData();
        if (mLink != null) {
            MagicWindowSDK.getMLink().router(mLink);
        } else {
            MLink.getInstance(this).checkYYB();
        }
    }
}
