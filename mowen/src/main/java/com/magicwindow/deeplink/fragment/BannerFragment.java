package com.magicwindow.deeplink.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.config.Constant;
import com.zxinsight.MWImageView;

public final class BannerFragment extends Fragment {

	private MWImageView homeBg;

	private static final int[] ids = { R.drawable.banner1, R.drawable.banner2,
			R.drawable.banner3, R.drawable.banner4, R.drawable.banner5 };

	private int mPosition;
	private static String POSITION = "position";

	public static final BannerFragment newInstance(int position) {
		BannerFragment f = new BannerFragment();
		Bundle bdl = new Bundle();
		bdl.putInt(POSITION, position);
		f.setArguments(bdl);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.item_home_banner, container, false);

		Bundle bundle = getArguments();
		mPosition = bundle.getInt(POSITION);

		homeBg = (MWImageView) v.findViewById(R.id.homeBanner);
		initViews();

		return v;
	}

	private void initViews() {

		if (mPosition == 2) {
			homeBg.bindEvent(Constant.MW_NORMAL_STYLE01);
		} else if (mPosition == 0) {
			homeBg.setImageResource(ids[0]);
			homeBg.bindEvent(Constant.MW_NORMAL_STYLE02);
		} else {
			homeBg.setImageResource(ids[1]);
		}

	}
}
