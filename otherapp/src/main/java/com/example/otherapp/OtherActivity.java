package com.example.otherapp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zxinsight.mlink.annotation.MLinkDefaultRouter;

/**
 * Created by ${邱伟中} on 2016/12/20 0020.上午 10:11
 */
@MLinkDefaultRouter
public class OtherActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
