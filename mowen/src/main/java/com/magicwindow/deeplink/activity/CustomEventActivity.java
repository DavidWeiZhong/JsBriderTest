package com.magicwindow.deeplink.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.base.BaseActivity;
import com.magicwindow.deeplink.config.Constant;
import com.zxinsight.TrackAgent;

import java.util.HashMap;
import java.util.Map;

public class CustomEventActivity extends BaseActivity {


    private Button comeToBuy;
    private Button clickToBuy;
    private Button buyDone;

    private TrackAgent trackAgent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_event);
        initView();
    }

    private void initView() {

        trackAgent = TrackAgent.currentEvent();

        comeToBuy = (Button) findViewById(R.id.come_in_buy_page);
        comeToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:统计发生次数
                trackAgent.customEvent(Constant.CUSTOM_COME_TO_BUY_ID);
                //end
                Toast.makeText(CustomEventActivity.this, getString(R.string.come_in_buy_page), Toast.LENGTH_SHORT).show();
            }

            ;
        });

        clickToBuy = (Button) findViewById(R.id.click_to_buy);
        clickToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:统计发生次数
                trackAgent.customEvent(Constant.CUSTOM_CLICK_TO_BUY_ID);
                //end
                Toast.makeText(CustomEventActivity.this, getString(R.string.click_to_buy), Toast.LENGTH_SHORT).show();

            }
        });

        buyDone = (Button) findViewById(R.id.buy_done);
        buyDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:统计点击行为各属性被触发的次数
                //例如，统计电商应用中“购买”事件发生的次数，以及购买的商品类型及数量
                Map<String, String> map = new HashMap<String, String>();
                map.put("page_title", "购买");
                map.put("type", "书");
                map.put("quantities", "3");
                trackAgent.customEvent(Constant.CUSTOM_BUY_DONE_ID, map);
                //end
                Toast.makeText(CustomEventActivity.this, getString(R.string.buy_done), Toast.LENGTH_SHORT).show();

            }
        });


    }


}
