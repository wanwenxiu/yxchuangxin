package com.yxld.yxchuangxin.activity.mine;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.AppVersionController;
import com.yxld.yxchuangxin.controller.impl.AppVersionControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyAppVersion;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.CxUtil;
import com.yxld.yxchuangxin.util.UpdateManager;

/**
 * com.nmore.linelogisticsfindgoods
 * @author xiongaolin
 * @Description:版本更新
 * 2016年1月19日  上午9:38:42
 */
public class MineVisionUpdateMainActivity extends BaseActivity implements ResultListener<CxwyAppVersion> {

	private ImageView returnWrap = null;
	private TextView searchCate = null;
	
	/** 当前版本*/
	private TextView cur_vision = null;
	/** 最新版本*/
	private TextView new_vision = null;
	/** 点击更新*/
	private TextView update_vision = null;
	
	private String curVersion;
	private String newVersion;
	
	private String newVersionUrl;
	
	private AppVersionController versionController;
	/** 版本信息*/
	private CxwyAppVersion entity;

	/** 动态获取定位权限*/
	public final static int REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE = 124;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.mine_vision_update);
		getSupportActionBar().setTitle("版本更新");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}


	@Override
	protected void initView() {

		cur_vision = (TextView) findViewById(R.id.cur_vision);
		
		new_vision = (TextView) findViewById(R.id.new_vision);
		
		update_vision =(TextView) findViewById(R.id.update_vision);
		update_vision.setOnClickListener(this);
		
		curVersion = CxUtil.getVersion(this);
		cur_vision.setText("您的当前版本号:"+curVersion);
		
		new_vision.setText("最新版本号:"+curVersion);
	}

	@Override
	protected void initDataFromLocal() {
		initDataFromNet();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.update_vision:
			//permission.READ_PHONE_STATE
			checkPermission(REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
			break;
		default:
			break;
		}
	}

	private  void alertUpdate(){
		// 这里来检测版本是否需要更新
		UpdateManager mUpdateManager = new UpdateManager(this,API.PIC +newVersionUrl);
		mUpdateManager.checkUpdateInfo(entity.getVersionUId(),entity.getVersionExplain(),entity.getVersionIsCompulsory());
	}
	
	
	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if(versionController==null){
			versionController = new AppVersionControllerImpl();
		}
		
		versionController.getAppVersionInfo(mRequestQueue, new Object[]{}, this);
	}

	@Override
	public void onResponse(CxwyAppVersion info) {
		progressDialog.hide();
		if (info.status != STATUS_CODE_OK) {
			onError(info.MSG);
			return;
		}
		if (info.getVer() != null) {
			entity = info.getVer();
			Log.d("geek", " 版本entity=" + entity.toString());
			curVersion = CxUtil.getVersion(this);
			newVersion = entity.getVersionUId();
			
			newVersionUrl = entity.getVersionDownloadUrl();
			
			cur_vision.setText("您的当前版本号:"+curVersion);
			new_vision.setText("最新版本号:"+newVersion);

			if (Float.valueOf(newVersion) > Float.valueOf(curVersion)) {
				update_vision.setVisibility(View.VISIBLE);
			}else{
				update_vision.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Override
	public void onErrorResponse(String errMsg) {
		onError(errMsg);
	}


	/**
	 * 请求权限
	 *
	 * @param id
	 *            请求授权的id 唯一标识即可
	 * @param permission
	 *            请求的权限
	 */
	protected void checkPermission(int id, String permission) {
		// 版本判断
		if (Build.VERSION.SDK_INT >= 23) {
			// 减少是否拥有权限
			int checkPermissionResult = getApplication().checkSelfPermission(
					permission);
			if (checkPermissionResult != PackageManager.PERMISSION_GRANTED) {
				// 弹出对话框接收权限
				requestPermissions(new String[] { permission }, id);
				return;
			} else {
				// 获取到权限
				alertUpdate();
			}
		} else {
			// 获取到权限
			alertUpdate();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// 获取到权限
				alertUpdate();
			} else {
				// 没有获取到权限
				Toast.makeText(MineVisionUpdateMainActivity.this, "没有获取到自动更新权限", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
