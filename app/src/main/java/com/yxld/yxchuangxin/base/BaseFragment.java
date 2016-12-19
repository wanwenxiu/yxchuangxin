package com.yxld.yxchuangxin.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.Volley;
import com.orhanobut.logger.Logger;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.db.DBUtil;
import com.yxld.yxchuangxin.entity.AppYezhuFangwu;
import com.yxld.yxchuangxin.entity.CxwyYezhu;
import com.yxld.yxchuangxin.util.CxUtil;
import com.yxld.yxchuangxin.util.Network;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.view.ProgressDialog;
import com.yxld.yxchuangxin.view.XListView;
import com.yxld.yxchuangxin.view.XListView.IXListViewListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @ClassName: BaseFragment 
 * @Description: 基础Fragment�?
 * @author wwx
 * @date 2015�?1�?6�?下午1:38:32 
 */
@SuppressLint("ShowToast")
public abstract class BaseFragment extends Fragment implements OnClickListener,
OnCancelListener, IXListViewListener {
	/** 服务器请求成功 */
	protected static final int STATUS_CODE_OK = 0;
	/** 服务器请求失败 */
	protected static final int STATUS_CODE_FAILED = 1;
	
	/**
	 * 数据库操作类
	 */
	protected DBUtil dbUtil;
	/**
	 * 请求队列
	 */
	protected RequestQueue mRequestQueue;
	
	/**
	 * 进度条加载
	 */
	protected ProgressDialog progressDialog;
	
	/**
	 * 根布局
	 */
	protected View rootView;
	/**
	 * 数据加载失败后显示的布局
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
	 */
	protected XListView xlistView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//当该变量为空，说明内存被回收 ，清空业主房屋等信息，重新进入欢迎界面
		if (Contains.isKill == null|| "".equals(Contains.isKill)) {
			Logger.d("BaseFragment onCreate isKill为空");
			return;
		}

//		//在自己的应用初始Activity中加入如下两行代码
//		RefWatcher refWatcher = AppConfig.getRefWatcher(getActivity());
//		refWatcher.watch(this);
		if (savedInstanceState != null) {
			//取出保存在savedInstanceState中
			Logger.d("BaseActivity onRestoreInstanceState() savedInstanceState != null");
			CxUtil.getLogindata(savedInstanceState);
		}
		AppConfig.getInstance().addFragment(this);
		
		if (dbUtil == null) {
			dbUtil = new DBUtil(getActivity());
		}
	}
	
	/**
	 * @Title: netWorkIsAvailable
	 * @Description: 网络是否可用
	 * @return
	 * @return boolean
	 * @throws
	 */
	public boolean netWorkIsAvailable() {
		if (!Network.isAvailableByPing(getActivity())) {
			return false;
		} else {
			return true;
		}
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initView(inflater, container);
		initDataFromLocal();
		return rootView;
	}

	/**
	 * 加载界面控件
	 */
	protected void initView(LayoutInflater inflater, ViewGroup container) {
		ly_empty_data = findView(R.id.ly_empty_data);
		ly_loading_failed = findView(R.id.ly_loading_failed);
		if (ly_loading_failed != null) {
			ly_loading_failed.setOnClickListener(this);
		}
		xlistView = findXListView(R.id.xListView);
		if (xlistView != null) {
			xlistView.setXListViewListener(this);
			Log.d("geek", "BaseFrgment initView xlistView "+xlistView.toString());
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMyCancelListener(this);
		// 初始化请求列队
		mRequestQueue = Volley.newRequestQueue(activity);
	}

	/**
	 * 获取网络数据
	 * 
	 * @return
	 */
	protected void initDataFromNet() {
		progressDialog.show();
	}

	/**
	 * 获取本地数据
	 * 
	 * @return
	 */
	protected abstract void initDataFromLocal();

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ly_loading_failed:
			pageCode = 0;
			initDataFromNet();
			break;

		default:
			break;
		}
	}

	/**
	 * 启动Activity
	 * 
	 * @param <T>
	 * @param clazz
	 */
	protected <T> void startActivity(Class<T> clazz) {
		Intent intent = new Intent(getActivity(), clazz);
		try {
			startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(getActivity(), "敬请期待！"+ clazz.getSimpleName()
					+ "未注册！",Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 启动Activity
	 * 
	 * @param clazz
	 */
	protected <T> void startActivity(Class<T> clazz, Bundle bundle) {
		Intent intent = new Intent(getActivity(), clazz);
		intent.putExtras(bundle);
		try {
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), "敬请期待！"+ clazz.getSimpleName()
					+ "未注册！",Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 启动Activity
	 * 
	 * @param clazz
	 */
	protected <T> void startActivity4Result(Class<T> clazz, Bundle bundle,
			int requestCode) {
		Intent intent = new Intent(getActivity(), clazz);
		intent.putExtras(bundle);
		try {
			getActivity().startActivityForResult(intent, requestCode);
		} catch (Exception e) {
			Toast.makeText(getActivity(), "敬请期待！"+ clazz.getSimpleName()
					+ "未注册！",Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 启动Activity
	 * 
	 * @param clazz
	 */
	protected <T> void startActivity4Result(Class<T> clazz, int requestCode) {
		Intent intent = new Intent(getActivity(), clazz);
		try {
			getActivity().startActivityForResult(intent, requestCode);
		} catch (Exception e) {
			Toast.makeText(getActivity(), "敬请期待！"+ clazz.getSimpleName()
					+ "未注册！",Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 获取界面控件
	 * 
	 * @param resId
	 * @return
	 */
	protected View findView(int resId) {
		return rootView.findViewById(resId);
	}

	/**
	 * 获取文本框
	 * 
	 * @param resId
	 * @return
	 */
	protected TextView findTextView(int resId) {
		return (TextView) rootView.findViewById(resId);
	}

	/**
	 * 获取按钮
	 * 
	 * @param resId
	 * @return
	 */
	protected Button findButton(int resId) {
		return (Button) rootView.findViewById(resId);
	}

	/**
	 * 获取按钮
	 * 
	 * @param resId
	 * @return
	 */
	protected ImageButton findImageButton(int resId) {
		return (ImageButton) rootView.findViewById(resId);
	}

	/**
	 * 获取按钮
	 * 
	 * @param resId
	 * @return
	 */
	protected ImageView findImageView(int resId) {
		return (ImageView) rootView.findViewById(resId);
	}

	/**
	 * 获取编辑框
	 * 
	 * @param resId
	 * @return
	 */
	protected EditText findEditText(int resId) {
		return (EditText) rootView.findViewById(resId);
	}

	/**
	 * 获取复选框
	 * 
	 * @param resId
	 * @return
	 */
	protected CheckBox findCheckBox(int resId) {
		return (CheckBox) rootView.findViewById(resId);
	}

	/**
	 * 获取listView
	 * 
	 * @param resId
	 * @return
	 */
	protected ListView findListView(int resId) {
		return (ListView) rootView.findViewById(resId);
	}

	/**
	 * 获取listView
	 * 
	 * @param resId
	 * @return
	 */
	protected XListView findXListView(int resId) {
		return (XListView) rootView.findViewById(resId);
	}

	/**
	 * 获取文本框中的文字（已处理首尾空格）</br> 支持TextView所有子类如：EditText, CheckBox, RadioButton等
	 * 
	 * @param v
	 *            TextView
	 * @return String
	 */
	protected String getText(TextView v) {
		if (v == null) {
			return "";
		}
		return v.getText().toString().trim();
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
	 * 退出时关闭数据库
	 */
	@Override
	public void onDestroy() {
		try {
			dbUtil.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		getRequestQueue();
		super.onDestroy();
	}

	private RequestQueue getRequestQueue() {
		Log.d("geek",this+"调用清除缓存");
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getActivity());
		}
		File cacheDir = new File(getActivity().getCacheDir(), "volley");
		DiskBasedCache cache = new DiskBasedCache(cacheDir);
		mRequestQueue.start();

		// clear all volley caches.
		mRequestQueue.add(new ClearCacheRequest(cache, null));
		return mRequestQueue;
	}

	public void onMyResponse(BaseEntity info) {
		resetView();
		if (ly_loading_failed != null) {
			ly_loading_failed.setVisibility(View.GONE);
		}

		if (info.status != STATUS_CODE_OK) {
			if (pageCode != 0) {
				ToastUtil.show(getActivity(), ((BaseEntity) info).MSG);
			}
			return;
		}
		if(info.status == STATUS_CODE_OK){
			isLoaded = true;
		}
		pageCode += 1;
	}

	public void onError(String errMsg) {
		if(!netWorkIsAvailable()){
			ToastUtil.show(getActivity(),"网络连接失败，请检查您的网络状态");
//			new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
//					.setTitleText("连接失败")
//					.setContentText("网络连接失败，请检查您的网络状态")
//					.show();
		}else{
			if(StringUitl.isNoEmpty(errMsg)){
				ToastUtil.show(getActivity(),"errMsg");
//				new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
//						.setTitleText("提示")
//						.setContentText(errMsg)
//						.show();
			}
		}
		resetView();
		showErrorPage(true);
	}
	@Override
	public void onRefresh() {
		Log.d("geek", "BaseFragment onRefresh()");
		pageCode = 0;
		initDataFromNet();
	}

	@Override
	public void onLoadMore() {
		Log.d("geek", "BaseFragment onLoadMore()");
		initDataFromNet();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		//保存至outState中
		if(Contains.user != null && (ArrayList)Contains.appYezhuFangwus != null){
			Logger.d("BaseFragment onSaveInstanceState user="+Contains.user+",house="+(ArrayList)Contains.appYezhuFangwus);
			CxUtil.setLoginData(outState);
		}else{
			Logger.d("BaseFragment onSaveInstanceState ");
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		try {
			//取出保存在savedInstanceState的值
			if (savedInstanceState != null) {
				Logger.d("BaseFragment onRestoreInstanceState()");
				CxUtil.getLogindata(savedInstanceState);
			}
			super.onViewStateRestored(savedInstanceState);
		} catch (Exception e) {
		}
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

	/** 是否已经加载过初始数据, 在页面初始数据加载成功之后请置为false */
	protected boolean isLoaded = false;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if(this != null){
			super.setUserVisibleHint(isVisibleToUser);
			if (isVisibleToUser) {
				if(!isLoaded){
					firstLoading();
				}
			}
		}
	}
	
	/**
	 * 初始化界面数据
	 */
	public void firstLoading(){
		if(xlistView != null){
			Log.d("geek", "BaseFrgment firstLoading xlistView "+xlistView.toString());
		}
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

}
