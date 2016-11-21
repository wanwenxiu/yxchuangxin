package com.yxld.yxchuangxin.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yxld.yxchuangxin.activity.login.WelcomeActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.util.MemoryCache;
import com.yxld.yxchuangxin.view.LoadingImg;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import im.fir.sdk.FIR;

/**
 * @ClassName: AppConfig
 * @Description: Application 对象
 * @author wwx
 * @date 2015�?1�?6�?下午1:36:10
 *
 */
public class AppConfig extends Application {
	PendingIntent restartIntent;
	/** 配置文件�? */
	public String SP_FILE_NAME = "ChuangXinConfig";
	/** 配置文件工具 */
	private SharedPreferences sp = null;

	/** 运用list来保存每�?��activity */
	private  List<Activity> mListActivity = new LinkedList<Activity>();
	/** 运用list来保存每�?��activity */
	private List<BaseFragment> mListFragment = new LinkedList<BaseFragment>();

	private RequestQueue mRequestQueue;
	public static ImageLoader mImageLoader;
	
	//public static AMapLocationClient locationClient = null;
	//private AMapLocationClientOption locationOption = null;

	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	/** 为了实现每次使用该类时不创建新的对象而创建的静�?对象 */
	public static AppConfig instance = null;

	// 用于存放倒计时时间
	public static Map<String, Long> map;
	public static AppConfig app;

	public AppConfig() {

	}

	public static synchronized AppConfig getInstance() {
		if (null == instance) {
			instance = new AppConfig();
		}
		return instance;
	}


	// user your appid the key.
	private static final String APP_ID = "2882303761517496984";
	// user your appid the key.
	private static final String APP_KEY = "5871749656984";

	// 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep
	// com.xiaomi.mipushdemo
	public static final String TAG = "com.yxld.ysf";

	private static DemoHandler sHandler = null;
	private static BaseActivity sMainActivity = null;

//	//在自己的Application中添加如下代码
//	public static RefWatcher getRefWatcher(Context context) {
//		AppConfig application = (AppConfig) context
//				.getApplicationContext();
//		return application.refWatcher;
//	}
//
//	//在自己的Application中添加如下代码
//	private RefWatcher refWatcher;


	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		FIR.init(this);
		ZXingLibrary.initDisplayOpinion(this);
		// 创建加载图片
		Contains.loadingImg = LoadingImg.LoadingImgs(getApplicationContext());

		Fresco.initialize(this);
		// 初始化配置文�?
		sp = getSharedPreferences(SP_FILE_NAME, MODE_PRIVATE);

		File cacheDir = new File(this.getCacheDir(), "volley");
		mRequestQueue = new RequestQueue(new DiskBasedCache(cacheDir),
				new BasicNetwork(new HurlStack()), 1);

		MemoryCache mCache = new MemoryCache();
		mImageLoader = new ImageLoader(mRequestQueue, mCache);
		mRequestQueue.start();


		// 注册push服务，注册成功后会向DemoMessageReceiver发送广播
		// 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
		if (shouldInit()) {
			MiPushClient.registerPush(this, APP_ID, APP_KEY);
		}

		LoggerInterface newLogger = new LoggerInterface() {

			@Override
			public void setTag(String tag) {
				// ignore
			}

			@Override
			public void log(String content, Throwable t) {
				Log.d(TAG, content, t);
			}

			@Override
			public void log(String content) {
				Log.d(TAG, content);
			}
		};
		Logger.setLogger(this, newLogger);
		if (sHandler == null) {
			sHandler = new DemoHandler(getApplicationContext());
		}

//		locationClient = new AMapLocationClient(this);
//		locationOption = new AMapLocationClientOption();
//		// 设置定位模式为高精度模式
//		locationOption
//				.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//		locationOption.setOnceLocation(false);
//		initOption();

		//在自己的Application中添加如下代码
//		refWatcher = LeakCanary.install(this);

		// 以下用来捕获程序崩溃异常
		Intent intent = new Intent();
		// 参数1：包名，参数2：程序入口的activity
		intent.setClassName("com.yxld.yxchuangxin", "com.yxld.yxchuangxin.activity.login.WelcomeActivity");
		intent.setClass(this,WelcomeActivity.class);
		restartIntent = PendingIntent.getActivity(getApplicationContext(), 0,
				intent,0);
//		Intent.FLAG_ACTIVITY_NEW_TASK
		Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程
	}

	public Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			Log.d("geek","异常异常异常");
			AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
					restartIntent); // 1秒钟后重启应用
			AppConfig.getInstance().exit();
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	};

	/***
	 * @Title: addActivity
	 * @Description: 将实例化出的Activity添加至list�?
	 * @param activity
	 * @return void
	 * @throws
	 */
	public void addActivity(Activity activity) {
		mListActivity.add(activity);
	}

	public void removeActivity(Activity activity){
		mListActivity.remove(activity);
	}

	/**
	 * @Title: addFragment
	 * @Description: 将实例化出的BaseFragment添加至list�?
	 * @param fragment
	 * @return void
	 * @throws
	 */
	public void addFragment(BaseFragment fragment) {
		mListFragment.add(fragment);
	}

	public void exit() {
		if (mListActivity != null) {
			for (Activity activity : mListActivity) {
				if (activity != null && !activity.isFinishing())
					activity.finish();
			}
		}
	}

	/**
	 * @Title: exitApplication
	 * @Description: �?��应用程序
	 * @return void
	 * @throws
	 */
	public void exitApplication() {
		try {
			// finish�?��Activity
			for (Activity activity : mListActivity) {
				if (activity != null) {
					activity.finish();
				}
			}
			// finish�?��Fragment
			for (BaseFragment baseFragment : mListFragment) {
				if (baseFragment != null) {
					baseFragment.getActivity().finish();
					;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 强制�?��应用程序
			System.exit(0);
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
//		// 停止定位
//		if(locationClient.isStarted()){
//			locationClient.stopLocation();
//		}
//		if (null != locationClient) {
//			/**
//			 * 如果AMapLocationClient是在当前Activity实例化的，
//			 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
//			 */
//			locationClient.onDestroy();
//			locationClient = null;
//			locationOption = null;
//		}
		System.gc();
	}

	public SharedPreferences getSp() {
		return sp;
	}

	public void setSp(SharedPreferences sp) {
		this.sp = sp;
	}

	
	// 根据控件的选择，重新设置定位参数
	private void initOption() {
//		// 设置是否需要显示地址信息
//		locationOption.setNeedAddress(true);
//		String strInterval = "10000";
//		if (!TextUtils.isEmpty(strInterval)) {
//			// 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
//			locationOption.setInterval(Long.valueOf(strInterval));
//		}
//
//		// 设置定位参数
//		locationClient.setLocationOption(locationOption);
//
	}


	private boolean shouldInit() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		int myPid = Process.myPid();
		for (ActivityManager.RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}

	public static DemoHandler getHandler() {
		return sHandler;
	}

	public static void setMainActivity(BaseActivity activity) {
		sMainActivity = activity;
	}

	public static class DemoHandler extends Handler {

		private Context context;

		public DemoHandler(Context context) {
			this.context = context;
		}

		@Override
		public void handleMessage(Message msg) {
			String s = (String) msg.obj;
			if (sMainActivity != null) {
				sMainActivity.refreshLogInfo();
			}
			if (!TextUtils.isEmpty(s)) {
				//Toast.makeText(context, s, Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * 分割 Dex 支持
	 * @param base
	 */
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

}
