package com.yxld.yxchuangxin.activity.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.Main.NewMainActivity2;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.LoginController;
import com.yxld.yxchuangxin.controller.impl.LoginControllerImpl;
import com.yxld.yxchuangxin.db.DBUtil;
import com.yxld.yxchuangxin.entity.CxwyMallUser;
import com.yxld.yxchuangxin.entity.CxwyYezhu;
import com.yxld.yxchuangxin.entity.LoginEntity;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.SPUtils;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.view.Utils;

/**
 * @ClassName: WelcomeActivity
 * @Description: 欢迎页
 * @author wwx
 * @date 2016年1月18日 下午2:34:22
 */
@SuppressLint("HandlerLeak")
public class WelcomeActivity extends BaseActivity implements
		ResultListener<LoginEntity>, OnItemClickListener, AMapLocationListener {
	private final String LAST_LOGIN_USER_ID = "lastLoginUserId";
	private final String CB_SAVE_PWD = "cb_save_pwd";
	/** 跳转界面*/
	private final int JUMP_ACTIVITY = 66;
	/** 定位结束*/
	private final int LOCATION_FINISH= 65;
	
	/** 动态获取定位权限*/
	 public final  int REQUEST_CODE_ASK_LOCATION = 123;

	/** 数据库保存的用户信息 */
	private CxwyMallUser curUser = null;
	/** 登陆接口实现类 */
	private LoginController loginController;
	private SharedPreferences sp;
	private String name,pwd;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(curUser != null){
			curUser = null;
		}
		if(loginController != null){
			loginController = null;
		}

		if(handler != null){
			handler.removeCallbacksAndMessages(null);
		}
	}

	@Override
	protected void initContentView(Bundle savedInstanceState) {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcom_activity_layout);
		sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		setToorBar(false);
		//permission.READ_PHONE_STATE
		checkPermission(REQUEST_CODE_ASK_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
}

	Handler handler = new Handler() {
		@SuppressWarnings("static-access")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case JUMP_ACTIVITY:
				Log.d("geek", "跳转");
				Intent intent = new Intent(WelcomeActivity.this,
						GuideActivity.class);
				startActivity(intent);
				finish();
				break;
			case LOCATION_FINISH:
//				if(!locationClient.isStarted()){
//					// 启动定位
//					locationClient.setLocationListener(WelcomeActivity.this);
//					locationClient.startLocation();
//				}
				queryShipperInfo();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * @Title: queryUserInfo
	 * @Description: 查询用户信息
	 * @return void
	 * @throws
	 */
	private void queryShipperInfo() {
		boolean savePsd = sp.getBoolean("ISCHECK", false);

		if (!savePsd) {
			Log.d("geek", "GuideActivity getLogin()  curUser 没有保存密码");
			handler.sendEmptyMessage(JUMP_ACTIVITY);
			return;
		}else {
			 name=sp.getString("NAME", "");
			 pwd=sp.getString("PASSWORD", "");
			Log.d("...",name);
			Log.d("...",pwd);
			if (loginController == null) {
				loginController = new LoginControllerImpl();
			}
			loginController.getLogin(
					mRequestQueue,
					new Object[] {name,
							pwd}, this);
		}

//		handler.sendEmptyMessageDelayed(JUMP_ACTIVITY, 500);
//		handler.sendEmptyMessage(JUMP_ACTIVITY);
	}

	@Override
	protected void initView() {
	}

	@Override
	protected void initDataFromLocal() {

	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onResponse(LoginEntity info) {
		handler.sendEmptyMessage(JUMP_ACTIVITY);
		if (info.status != STATUS_CODE_OK) {
			Log.d("geek","自动登录失败");
			return;
		}
		if (info.MSG.equals("登录成功")) {
			Contains.token=info.getToken();
			Contains.user = info.getUser();
			if (info.getHouse()!=null && info.getHouse().size() >0 ){
				Contains.appYezhuFangwus=info.getHouse();
				Contains.curSelectXiaoQuName = info.getHouse().get(0).getXiangmuLoupan();
				Contains.curSelectXiaoQuId = info.getHouse().get(0).getFwLoupanId();
			}

			SharedPreferences.Editor editor = sp.edit();
			editor.putString("NAME", name);
			editor.putString("PASSWORD", pwd);
			editor.commit();
			sp.edit().putBoolean("ISCHECK", true).commit();
//			dbUtil.clearData(CxwyMallUser.class);
//			long result = dbUtil.insert(info.getUser(), info.getUser()
//					.getYezhuId() + "");
//
//			if (result == -1) {
//				ToastUtil.show(this, "登录失败,数据插入错误, 请重试!");
//				return;
//			}
//
//			Contains.user = info.getUser();
////
//			Contains.curSelectXiaoQu = info.getHouse().get(0).getXiangmuLoupan();
//
//			// 保存用户ID至配置文件中
//			SPUtils.put(WelcomeActivity.this, LAST_LOGIN_USER_ID, info
//					.getUser().getYezhuId() + "");
		} else {
			Log.d("geek","自动登录失败");
		}
	}

	@Override
	public void onErrorResponse(String errMsg) {
		//handler.sendEmptyMessageDelayed(JUMP_ACTIVITY, 500);
		handler.sendEmptyMessage(JUMP_ACTIVITY);
	}

	@Override
	public void onLocationChanged(AMapLocation arg0) {
		if (null != arg0) {
			Log.d("geek", "arg0"+arg0);
			Utils.getLocationStr(arg0);
		}
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
				handler.sendEmptyMessage(LOCATION_FINISH);
			}
		} else {
			// 获取到权限
			handler.sendEmptyMessage(LOCATION_FINISH);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
			String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode ==REQUEST_CODE_ASK_LOCATION) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// 获取到权限
				handler.sendEmptyMessage(LOCATION_FINISH);
			} else {
				// 没有获取到权限
				Toast.makeText(WelcomeActivity.this, "没有定位权限", Toast.LENGTH_SHORT).show();
				handler.sendEmptyMessage(LOCATION_FINISH);
			}
		}
	}


}
