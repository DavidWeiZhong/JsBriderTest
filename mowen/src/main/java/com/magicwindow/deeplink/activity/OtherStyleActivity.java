package com.magicwindow.deeplink.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.magicwindow.deeplink.base.BaseActivity;
import com.magicwindow.deeplink.config.Constant;
import com.magicwindow.deeplink.dialog.MWDialog;
import com.zxinsight.MarketingHelper;

public class OtherStyleActivity extends BaseActivity {

	private MarketingHelper marketingHelper;

	private Button shareByClick;
	
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// TODO Auto-generated method stub
		setContentView(com.magicwindow.deeplink.R.layout.activity_other_style);

		marketingHelper = MarketingHelper.currentMarketing(this);

		initView();
		mContext = this;
	}

	private void initView() {

		// 如果MW_1开启，则会显示MWDialog
		new MWDialog(this, Constant.MW_DIALOG).showOrDismiss();
		// end

		shareByClick = (Button) findViewById(com.magicwindow.deeplink.R.id.share_by_click);
		shareByClick.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO:此示例用来演示点击直接分享。
				// 最后一个值是分享的url，如果想分享特殊的url则传一个字符串。不传则分享的是MW_1内的分享url
				MarketingHelper.currentMarketing(mContext).click(mContext, Constant.MW_OTHER_STYLE01);
				// TODO: 下面这个接口在2.3版本开放
				// ShareHelper.share(OtherStyleActivity.this, Constant.MW_1);
			}
		});

	}

}
