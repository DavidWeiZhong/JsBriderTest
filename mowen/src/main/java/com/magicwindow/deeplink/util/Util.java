package com.magicwindow.deeplink.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.Locale;

public class Util {

	/**
	 * 检查权限是否开启
	 *
	 * @param permission
	 * @return true or false
	 */
	public static boolean checkPermissions(Context context, String permission) {
		PackageManager localPackageManager = context.getPackageManager();
		return localPackageManager.checkPermission(permission,
				context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
	}

	/**
	 * 判断当前网络是否可用
	 *
	 * @param context
	 * @return
	 */
	public static boolean isNetworkEnabled(Context context) {
		if (!Util.checkPermissions(context, "android.permission.INTERNET")) {
			return false;
		}

		if (context != null) {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity == null) {
				return false;
			} else {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) {
					for (NetworkInfo anInfo : info) {
						if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
					}
				}
			}
		}
		Toast.makeText(context, "网络未连接", Toast.LENGTH_SHORT).show();
		return false;
	}



}
