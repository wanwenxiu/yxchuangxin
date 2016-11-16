package com.yxld.yxchuangxin.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ysf.explosionfield.ExplosionField;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.Main.NewMainActivity2;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.LoginController;
import com.yxld.yxchuangxin.controller.impl.LoginControllerImpl;
import com.yxld.yxchuangxin.db.DBUtil;
import com.yxld.yxchuangxin.entity.CxwyMallUser;
import com.yxld.yxchuangxin.entity.LoginEntity;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.SPUtils;
import com.yxld.yxchuangxin.util.ToastUtil;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends BaseActivity {
	private final String LAST_LOGIN_USER_ID = "lastLoginUserId";
	private final String CB_SAVE_PWD = "cb_save_pwd";

	private TextView txt_register;
	private EditText login_tel;
	private EditText login_pwd;
	private Button loginSubmit;

	/** 登陆接口实现类 */
	private LoginController loginController;
	private ExplosionField mExplosionField;

	/** 记住密码 */
	private CheckBox checkBox1;

	/** 数据库保存的用户信息 */
	private CxwyMallUser curUser = null;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.login_layout2);
		setToorBar(false);
	}
	@Override
	protected void initView() {

		txt_register = (TextView) findViewById(R.id.txt_register);
		login_tel = (EditText) findViewById(R.id.login_tel);
		login_pwd = (EditText) findViewById(R.id.login_pwd);
		loginSubmit = (Button) findViewById(R.id.loginSubmit);
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		txt_register.setOnClickListener(this);
		mExplosionField = ExplosionField.attach2Window(this);
		addListener(findViewById(R.id.loginSubmit));

		queryShipperInfo();
	}

	/**
	 * @Title: queryUserInfo
	 * @Description: 查询用户信息
	 * @return void
	 * @throws
	 */
	private void queryShipperInfo() {

		boolean savePsd = (boolean) SPUtils.get(this, CB_SAVE_PWD, false);
		if (!savePsd) {
			Log.d("geek", "GuideActivity getLogin()  curUser 没有保存密码");
			return;
		}
		String userId = String.valueOf(SPUtils
				.get(this, LAST_LOGIN_USER_ID, ""));

		if (!TextUtils.isEmpty(userId)) {
			if (dbUtil == null) {
				dbUtil = new DBUtil(this);
			}
			if (!isEmptyList(dbUtil.query(CxwyMallUser.class, userId))) {
				curUser = (CxwyMallUser) (dbUtil.query(CxwyMallUser.class,
						userId).get(0));
				if(curUser != null){
					login_tel.setText(curUser.getUserTel());
					login_pwd.setText(curUser.getUserPassWord());
				}
			}
		}
	}

	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if (loginController == null) {
			loginController = new LoginControllerImpl();
		}
		loginController.getLogin(mRequestQueue,
				new Object[] { login_tel.getText().toString(),
						login_pwd.getText().toString() }, listener);
	}

	private ResultListener<LoginEntity> listener = new ResultListener<LoginEntity>() {

		@Override
		public void onResponse(LoginEntity info) {
			progressDialog.hide();
			Log.d("...", info.toString());
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
			}
			if (info.MSG.equals("已存在用户")) {

				dbUtil.clearData(CxwyMallUser.class);
				long result = dbUtil.insert(info.getUser(), info.getUser().getUserId()+"");

				if (result == -1) {
					ToastUtil.show(LoginActivity.this, "登录失败,数据插入错误, 请重试!");
					return;
				}

				mExplosionField.explode(loginSubmit);
				loginSubmit.setOnClickListener(null);
				Contains.cxwyYezhu = info.getYzList();
				Contains.cxwyMallUser = info.getUser();
				Log.d("denglu","登陆info.getDefuleaddr()  ="+info.getDefuleaddr().toString());
				Contains.defuleAddress = info.getDefuleaddr();
				Log.d("denglu","登陆 Contains.defuleAddress  ="+Contains.defuleAddress.toString());
				// 保存用户ID和账号至配置文件中
				SPUtils.put(LoginActivity.this, CB_SAVE_PWD,
						checkBox1.isChecked());
				SPUtils.put(LoginActivity.this, LAST_LOGIN_USER_ID, info
						.getUser().getUserId() + "");
				Contains.curSelectXiaoQu = info.getUser().getUserSpare1();
				new Thread() {
					public void run() {
						try {
							sleep(1000);
							Intent login = new Intent(LoginActivity.this,
									NewMainActivity2.class);
							startActivity(login);
							finish();
						} catch (Exception e) {
							e.printStackTrace();
						}
					};
				}.start();
			} else {
				Toast.makeText(LoginActivity.this, "你输入的手机号或者密码错误",
						Toast.LENGTH_LONG).show();
				loginSubmit.setClickable(true);
			}
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError(errMsg);
			loginSubmit.setClickable(true);
		}
	};

	private void addListener(final View loginSubmit) {
		if (loginSubmit instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) loginSubmit;
			for (int i = 0; i < parent.getChildCount(); i++) {
				addListener(parent.getChildAt(i));
			}
		} else {

			loginSubmit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					loginSubmit.setClickable(false);
					initDataFromNet();

				}
			});
		}
	}

	@Override
	protected void initDataFromLocal() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_register:
			Intent register = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(register);

			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}
