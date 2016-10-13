/**
 * 
 */
package com.yxld.yxchuangxin.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ToastUtil {
	private static Toast toast;
	private static Handler handler = new Handler();
	private static Runnable run = new Runnable() {
		public void run() {
			toast.cancel();
		}
	};

	public static void show(Context context, String info, int d) {
		if (toast == null)
			toast = Toast.makeText(context, info, d);
		else
			toast.setText(info);
		handler.postDelayed(run, 3000);
		toast.show();
	}

	public static void show(Context context, String info) {
		if (toast == null)
			toast = Toast.makeText(context, info, Toast.LENGTH_LONG);
		else
			toast.setText(info);
		toast.show();
		handler.postDelayed(run, 2000);
	}
}
