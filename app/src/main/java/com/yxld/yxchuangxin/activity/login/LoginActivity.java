package com.yxld.yxchuangxin.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.yxld.yxchuangxin.entity.CxwyMallAdd;
import com.yxld.yxchuangxin.entity.CxwyMallUser;
import com.yxld.yxchuangxin.entity.CxwyYezhu;
import com.yxld.yxchuangxin.entity.LoginEntity;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.SPUtils;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.media.CamcorderProfile.get;
import static com.yxld.yxchuangxin.R.id.login_pwd;
import static com.yxld.yxchuangxin.R.id.phone;
import static com.yxld.yxchuangxin.R.id.register_button_phone;
import static com.yxld.yxchuangxin.R.id.register_pwd;

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
	private SharedPreferences sp;
	private String userNameValue,passwordValue;

	/** 数据库保存的用户信息 */
	private CxwyYezhu curUser = null;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.login_layout2);
		sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
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
		//判断记住密码多选框的状态
		if(sp.getBoolean("ISCHECK", false))
		{
			//设置默认是记录密码状态
			checkBox1.setChecked(true);
			login_tel.setText(sp.getString("NAME", ""));
			login_pwd.setText(sp.getString("PASSWORD", ""));
		}

		checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (checkBox1.isChecked()) {
					sp.edit().putBoolean("ISCHECK", true).commit();

				}else {
					sp.edit().putBoolean("ISCHECK", false).commit();

				}
			}
		});
	}


	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if (loginController == null) {
			loginController = new LoginControllerImpl();
		}

		loginController.getLogin(mRequestQueue,
				new Object[] { login_tel.getText().toString(),
						StringUitl.getMD5(login_pwd.getText().toString()) }, listener);
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
			if (info.MSG.equals("登录成功")) {

				mExplosionField.explode(loginSubmit);
				loginSubmit.setOnClickListener(null);
				Contains.token=info.getToken();
				Contains.user = info.getUser();
				if (info.getHouse()!=null && info.getHouse().size() >0 ){
					Contains.appYezhuFangwus=info.getHouse();
					Contains.curSelectXiaoQuName = info.getHouse().get(0).getXiangmuLoupan();
					Contains.curSelectXiaoQuId = info.getHouse().get(0).getFwLoupanId();

					Contains.defuleAddress = new CxwyMallAdd();
					//设置默认地址项目
					if(Contains.user.getYezhuName() != null && !"".equals(Contains.user.getYezhuName() )){
						Contains.defuleAddress.setAddName(Contains.user.getYezhuName());
					}else{
						Contains.defuleAddress.setAddName(Contains.user.getYezhuShouji());
					}
					Contains.defuleAddress.setAddTel(Contains.user.getYezhuShouji());
					Contains.defuleAddress.setAddAdd(info.getHouse().get(0).getXiangmuLoupan()+
							info.getHouse().get(0).getFwLoudong()+"栋"+
							info.getHouse().get(0).getFwDanyuan()+"单元"+
							info.getHouse().get(0).getFwFanghao());
				}
				SharedPreferences.Editor editor = sp.edit();
				editor.putString("NAME", userNameValue);
				editor.putString("PASSWORD", passwordValue);
				editor.commit();
				sp.edit().putBoolean("ISCHECK", true).commit();
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
					userNameValue = login_tel.getText().toString();
					passwordValue = login_pwd.getText().toString();
					if(StringUitl.isNotEmpty(LoginActivity.this,login_tel,"请输入手机号码") && StringUitl.isNotEmpty(LoginActivity.this,login_pwd,"请输入密码")){
						loginSubmit.setClickable(false);
						int len = login_tel.getText().toString().length();
						int len1=login_pwd.getText().toString().length();
						if (len1 >= 6 &&  len==11 ) {
							initDataFromNet();
							loginSubmit.setClickable(true);
						} else {
							Toast.makeText(LoginActivity.this, "请确定账号密码格式是否正确", Toast.LENGTH_SHORT).show();
							loginSubmit.setClickable(true);
							initDataFromNet();
						}
					}
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
