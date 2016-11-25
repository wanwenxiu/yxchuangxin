package com.yxld.yxchuangxin.base;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.Volley;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.login.LoginActivity;
import com.yxld.yxchuangxin.activity.login.WelcomeActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.db.DBUtil;
import com.yxld.yxchuangxin.entity.CxwyMallUser;
import com.yxld.yxchuangxin.util.Network;
import com.yxld.yxchuangxin.util.SPUtils;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.view.ProgressDialog;
import com.yxld.yxchuangxin.view.XListView;
import com.yxld.yxchuangxin.view.XListView.IXListViewListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @ClassName: BaseActivity
 * @Description: 基础Activity�?
 * @author wwx
 * @date 2015�?1�?6�?下午1:37:53
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements
		OnClickListener, OnCancelListener, IXListViewListener,
		OnItemClickListener {
	public final String LAST_LOGIN_USER_ID = "lastLoginUserId";
	public final String CB_SAVE_PWD = "cb_save_pwd";
	/** 服务器请求成功 */
	protected static final int STATUS_CODE_OK = 0;
	/** 服务器请求失败 */
	protected static final int STATUS_CODE_FAILED = 1;

	/** 保存当前选择小区*/
	private String SAVEXIAOQU = "SAVEXIAOQU";
	/** 保存当前登录用户信息*/
	private String SAVEYONGHU = "SAVEYONGHU";
	/** 保存当前登录业主信息*/
	private String SAVEYEZHU = "SAVEYEZHU";

	/**
	 * 数据库操作类
	 */
	protected DBUtil dbUtil;


	/** 标题栏 */
	protected TextView tv_title, rightTv;
	
	protected ImageView returnWrap;

	public RelativeLayout headerWarp;

	public LinearLayout rootLayout;

	public Toolbar toolbar;

	/**
	 * 网络请求列队
	 */
	protected RequestQueue mRequestQueue;
	/**
	 * 公共的加载进度弹窗
	 */
	public ProgressDialog progressDialog;

	/**
	 * 数据加载失败时显示的视图
	 */
	protected View ly_loading_failed;
	/**
	 * 没有数据时显示的布局
	 */
	protected View ly_empty_data;
	/**
	 * 页码
	 */
	protected int pageCode = 0;

	/**
	 * 
	 */
	protected XListView xlistView;

	public AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;

	public static List<String> logList = new CopyOnWriteArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 这句很关键，注意是调用父类的方法
		super.setContentView(R.layout.activity_base);

		// 经测试在代码里直接声明透明状态栏更有效
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
		initToolbar();
//		//在自己的应用初始Activity中加入如下两行代码
//		RefWatcher refWatcher = AppConfig.getRefWatcher(this);
//		refWatcher.watch(this);
		if(!netWorkIsAvailable()){
//			Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
		}
		AppConfig.getInstance().addActivity(this);

		// 请不要更改以下方法的调用顺序
		// 初始化请求列队
		mRequestQueue = Volley.newRequestQueue(this);

		progressDialog = new ProgressDialog(this);
		progressDialog.setMyCancelListener(this);
		
		dbUtil = new DBUtil(this);

		locationClient = new AMapLocationClient(this);
		locationOption = new AMapLocationClientOption();
		// 设置定位模式为高精度模式
		locationOption
				.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
		locationOption.setOnceLocation(false);
		initOption();

		initContentView(savedInstanceState);

		initTitle();
		initView();
		initDataFromLocal();
	}



	/**
	 * 初始化界面布局
	 *
	 * @param savedInstanceState
	 */
	protected abstract void initContentView(Bundle savedInstanceState);

	private void initToolbar() {
		 toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}
	}

	// 根据控件的选择，重新设置定位参数
	private void initOption() {
		// 设置是否需要显示地址信息
		locationOption.setNeedAddress(true);
		String strInterval = "10000";
		if (!TextUtils.isEmpty(strInterval)) {
			// 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
			locationOption.setInterval(Long.valueOf(strInterval));
		}

		// 设置定位参数
		locationClient.setLocationOption(locationOption);

	}

	/**
	 * @Title: netWorkIsAvailable
	 * @Description: 网络是否可用
	 * @return
	 * @return boolean
	 * @throws
	 */
	public boolean netWorkIsAvailable() {
		if (!Network.isAvailableByPing(this)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setContentView(int layoutId) {
		setContentView(View.inflate(this, layoutId, null));
	}

	@Override
	public void setContentView(View view) {
		rootLayout = (LinearLayout) findViewById(R.id.root_layout);
		if (rootLayout == null) return;
		rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		initToolbar();
	}

	/**
	 * 初始化界面控件
	 */
	protected abstract void initView();

	/**
	 * 初始化activity传递过来的intent数据
	 */
	protected abstract void initDataFromLocal();

	/**
	 * 初始化网络数据
	 */
	protected void initDataFromNet() {
		progressDialog.show();
	}

	/**
	 * 初始化界面头部
	 */
	private void initTitle() {
		headerWarp = (RelativeLayout) findViewById(R.id.header);
		tv_title = (TextView) findViewById(R.id.searchCate);
		returnWrap = (ImageView) findViewById(R.id.returnWrap);
		rightTv = (TextView) findViewById(R.id.rightTv);

		ly_loading_failed = (View) findViewById(R.id.ly_loading_failed);
		ly_empty_data = (View) findViewById(R.id.ly_empty_data);
		xlistView = (XListView) findViewById(R.id.xListView);

		if (returnWrap != null) {
			returnWrap.setOnClickListener(this);
		}
		if (rightTv != null) {
			rightTv.setOnClickListener(this);
		}

		if (ly_loading_failed != null) {
			ly_loading_failed.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					initDataFromNet();
				}
			});
		}
		if (xlistView != null) {
			xlistView.setXListViewListener(this);
			xlistView.setOnItemClickListener(this);
		}
	}

	/**
	 * 设置页面标题文字
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		if (tv_title == null) {
			return;
		}
		if (TextUtils.isEmpty(title)) {
			title = "创欣";
			return;
		}
		tv_title.setText(title);
	}

	public void setToorBar(boolean isVisitiy){
		if(toolbar != null){
			if(isVisitiy){
				toolbar.setVisibility(View.VISIBLE);
			}else{
				toolbar.setVisibility(View.GONE);
			}
		}
	}

	@Override
	protected void onDestroy() {
		try {
			dbUtil.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 停止定位
		if(locationClient != null && locationClient.isStarted()){
			locationClient.stopLocation();
		}
		if (null != locationClient) {
			/**
			 * 如果AMapLocationClient是在当前Activity实例化的，
			 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
			 */
			locationClient.onDestroy();
			locationClient = null;
			locationOption = null;
		}

		AppConfig.getInstance().removeActivity(this);
		getRequestQueue();

		super.onDestroy();
	}



	private RequestQueue getRequestQueue() {
		Log.d("geek",this+"调用清除缓存");
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}
		File cacheDir = new File(this.getCacheDir(), "volley");
		DiskBasedCache cache = new DiskBasedCache(cacheDir);
		mRequestQueue.start();

		// clear all volley caches.
		mRequestQueue.add(new ClearCacheRequest(cache, null));
		return mRequestQueue;
	}


	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public abstract void onClick(View v);

	/**
	 * 启动Activity
	 * 
	 * @param clazz
	 */
	protected <T> void startActivity(Class<T> clazz) {
		Intent intent = new Intent(this, clazz);
		try {
			startActivity(intent);
		} catch (Exception e) {
			ToastUtil.show(this, "敬请期待！");
		}
	}

	/**
	 * 启动Activity
	 * 
	 * @param clazz
	 * @param bundle
	 */
	protected <T> void startActivity(Class<T> clazz, Bundle bundle) {
		Intent intent = new Intent(this, clazz);
		intent.putExtras(bundle);
		try {
			startActivity(intent);
		} catch (Exception e) {
			ToastUtil.show(this, "敬请期待！");
		}
	}

	public void onError(String errMsg) {
		if(!this.isFinishing()){
			if(!netWorkIsAvailable()){
				new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
						.setTitleText("连接失败")
						.setContentText("网络连接失败，请检查您的网络状态")
						.show();
			}else{
				new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
						.setTitleText("连接失败")
						.setContentText(errMsg)
						.show();
			}
			resetView();
			showErrorPage(true);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onRefresh() {
		pageCode = 0;
		initDataFromNet();
	}

	@Override
	public void onLoadMore() {
		initDataFromNet();
	}

	/**
	 * 
	 */
	protected void resetView() {
		progressDialog.hide();
		if (xlistView != null) {
			xlistView.setVisibility(View.VISIBLE);
			xlistView.setPullLoadEnable(true);
			xlistView.stopRefresh();
			xlistView.stopLoadMore();
		}
		showEmptyDataPage(false);
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		if (xlistView != null) {
			xlistView.setPullLoadEnable(true);
			xlistView.stopRefresh();
			xlistView.stopLoadMore();
		}
	}

	public void onMyResponse(BaseEntity info) {
		resetView();
		if (ly_loading_failed != null) {
			ly_loading_failed.setVisibility(View.GONE);
		}
		if (info.status != STATUS_CODE_OK) {
			if (pageCode != 0) {
				ToastUtil.show(this, ((BaseEntity) info).MSG);
			}
			return;
		}
		pageCode += 1;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		System.out.println("Baseactivity onSaveInstanceState()");
		outState.putString(SAVEXIAOQU, Contains.curSelectXiaoQu);
//		outState.putSerializable(SAVEYONGHU,Contains.cxwyMallUser);
//		outState.putSerializable(SAVEYEZHU,Contains.user);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		try {
			if (savedInstanceState != null) {
			Contains.curSelectXiaoQu = savedInstanceState.getString(SAVEXIAOQU);
//			Contains.cxwyMallUser = (CxwyMallUser) savedInstanceState.getSerializable(SAVEYONGHU);
//			Contains.cxwyYezhu = (ArrayList) savedInstanceState.getSerializable(SAVEYONGHU);
		}
			super.onRestoreInstanceState(savedInstanceState);
		} catch (Exception e) {
		}
	}
	
	/**
	 * 检查list是否为空
	 * 
	 * @param list
	 * @return
	 */
	protected <T> boolean isEmptyList(List<T> list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 显示错误页面
	 * @param show
	 */
	public void showErrorPage(boolean show){
		if(show && xlistView != null){
			xlistView.setVisibility(View.GONE);
		}
		if(ly_empty_data != null){
			ly_empty_data.setVisibility(View.GONE);
		}
		if (ly_loading_failed == null) {
			return;
		}
		if(show){
			ly_loading_failed.setVisibility(View.VISIBLE);
		}else{
			ly_loading_failed.setVisibility(View.GONE);
		}
		
	}
	
	/**
	 * 显示空内容页面
	 * @param show
	 */
	public void showEmptyDataPage(boolean show){
		if(show && xlistView != null){
			xlistView.setVisibility(View.GONE);
		}
		if(ly_loading_failed != null){
			ly_loading_failed.setVisibility(View.GONE);
		}
		if (ly_empty_data == null) {
			return;
		}
		if(show){
			ly_empty_data.setVisibility(View.VISIBLE);
		}else{
			ly_empty_data.setVisibility(View.GONE);
		}
	}

	public void refreshLogInfo() {
		String AllLog = "";
		for (String log : logList) {
			AllLog = AllLog + log + "\n\n";
		}
	}

}
