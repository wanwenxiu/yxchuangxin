package com.yxld.yxchuangxin.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.contain.Contains;

public class CxUtil {

	/**
	 * 抖一抖动画
	 */
	public static void actionAndAction(View iv, Context context) {
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.action_and_action);
		iv.startAnimation(animation);
	}
	
	// 用户是否已经登陆
		public static boolean isLoginOk() {
			if (Contains.user == null || Contains.user.getYezhuShouji() == null
					|| Contains.user.getYezhuPwd()== null) {
				return false;
			}
			return true;
		}

		// 购物车是否为空
		public static boolean cartIsNull() {
			if (Contains.CartList != null && Contains.CartList.size() != 0) {
				return false;
			}
			return true;
		}
	
		/**
		 * ListView 加载动画
		 */
		public static LayoutAnimationController getListAnim() {
			AnimationSet set = new AnimationSet(true);
			Animation animation = new AlphaAnimation(0.0f, 1.0f);
			animation.setDuration(300);
			set.addAnimation(animation);

			animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
					1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
			animation.setDuration(500);
			set.addAnimation(animation);
			LayoutAnimationController controller = new LayoutAnimationController(
					set, 0.5f);
			return controller;
		}
		
		public static String getVersion(Context mContext) {
			try {
				PackageManager manager = mContext.getPackageManager();
				PackageInfo info = manager.getPackageInfo(
						mContext.getPackageName(), 0);
				String version = info.versionName;
				Log.d("geek","version"+version);
				return version;
			} catch (Exception e) {
				e.printStackTrace();
				return mContext.getString(R.string.can_not_find_version_name);
			}
		}
}
