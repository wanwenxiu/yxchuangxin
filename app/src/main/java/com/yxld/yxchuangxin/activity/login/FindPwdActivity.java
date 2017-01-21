package com.yxld.yxchuangxin.activity.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaomi.mipush.sdk.MiPushClient;
import com.ysf.explosionfield.ExplosionField;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.AppConfig;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.controller.LoginController;
import com.yxld.yxchuangxin.controller.impl.LoginControllerImpl;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.view.TimeButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 找回密码
 */
public class FindPwdActivity extends BaseActivity {

	private LoginController loginController;
	private EditText register_tel,register_pwd,register_yzm,register_sure_pwd;
	private Button regsubmit;
	private TimeButton register_button_phone;
	private ExplosionField mExplosionField;

	// 填写从短信SDK应用后台注册得到的APPKEY
	private  String APPKEY = "14e1cc04efbc0";
	// 填写从短信SDK应用后台注册得到的APPSECRET
	private  String APPSECRET = "39a445e014ef8b2575238d50f97b94b2";
	public String phString;
	private BroadcastReceiver smsReceiver;
	private IntentFilter filter;
	private String strContent;
	private String strCode;
	private Handler captcha;
	private String patternCoder = "(?<!\\d)\\d{4}(?!\\d)";

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_findpwd);
		getSupportActionBar().setTitle("找回密码");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		register_button_phone= (TimeButton) findViewById(R.id.register_button_phone);
		register_button_phone.onCreate(savedInstanceState);
		register_button_phone.setOnClickListener(this);
		register_button_phone.setTextAfter("重新发送").setTextBefore("获取验证码").setLenght(30 * 1000);
	}


	@Override
	protected void initView() {
		register_tel = (EditText) findViewById(R.id.register_tel);
		register_pwd = (EditText) findViewById(R.id.register_pwd);
		regsubmit = (Button) findViewById(R.id.regsubmit);
		register_yzm= (EditText) findViewById(R.id.register_yzm);
		register_sure_pwd = (EditText) findViewById(R.id.register_sure_pwd);
		mExplosionField = ExplosionField.attach2Window(this);
		addListener(findViewById(R.id.regsubmit));
		//获取短信sdk
		SMSSDK.initSDK(this, APPKEY, APPSECRET);
		EventHandler eh = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {

				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}

		};
		SMSSDK.registerEventHandler(eh);

		//获取短信的验证码放到edittext
		captcha= new Handler() {
			public void handleMessage(Message msg) {
				register_yzm.setText(strContent);
				register_yzm.setSelection(register_yzm.getText().length());
			};
		};
		filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(Integer.MAX_VALUE);

		smsReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Toast.makeText(FindPwdActivity.this,"dasda",Toast.LENGTH_SHORT).show();
				Object[] objs = (Object[]) intent.getExtras().get("pdus");
				for (Object obj : objs) {
					byte[] pdu = (byte[]) obj;
					SmsMessage sms = SmsMessage.createFromPdu(pdu);
					// 短信的内容
					String message = sms.getMessageBody();
					Log.d("logo", "message     " + message);
					// 短息的手机号。。+86开头？
					String from = sms.getOriginatingAddress();
					Log.d("logo", "from     " + from);
					if (!TextUtils.isEmpty(from)) {
						String code = patternCode(message);
						if (!TextUtils.isEmpty(code)) {
							strContent = code;
							captcha.sendEmptyMessage(1);
						}
					}
				}
			}
		};
		registerReceiver(smsReceiver, filter);
	}

	@Override
	protected void initDataFromNet() {
		if (loginController == null) {
			loginController = new LoginControllerImpl();
		}

		AppConfig.setMainActivity(this);
		String alias=register_tel.getText().toString();
		String account=register_tel.getText().toString();
		MiPushClient.setAlias(FindPwdActivity.this, account, null);
		MiPushClient.setUserAccount(FindPwdActivity.this,alias, null);

		//判断是否存在手机号码
		loginController.getExistShouji(mRequestQueue,
				new Object[] { register_tel.getText().toString()},
				Alreadylistener);

	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshLogInfo();
	}

	private ResultListener<BaseEntity> Alreadylistener = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			//0可以注册 1已经注册 2手机号未输入
			if (info.status == 2) {
				onError(info.MSG);
				return;
			}
			if (info.status == 0) {
				register_button_phone.setTextAfter("重新发送").setTextBefore("获取验证码").setLenght(5 * 1000);
				Toast.makeText(FindPwdActivity.this,
						"你输入的手机号未注册哦    O(∩_∩)O谢谢", Toast.LENGTH_LONG).show();
			}else {
				String str = register_tel.getText().toString();
				String str1 = str.replaceAll(" ", "");
				Toast.makeText(FindPwdActivity.this, str1, Toast.LENGTH_SHORT).show();
				if (!TextUtils.isEmpty(str1)) {
					SMSSDK.getVerificationCode("86", str1);
					phString = str1;
				} else {
					Toast.makeText(FindPwdActivity.this, "电话不能为空", Toast.LENGTH_SHORT).show();
				}
			}

		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError("请求失败");
		}

	};

	private ResultListener<BaseEntity> listener = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
			}
			if (info.MSG !=null && !"".equals(info.MSG) ) {
				Toast.makeText(FindPwdActivity.this,info.MSG,Toast.LENGTH_SHORT).show();
				register_button_phone.setTextAfter("重新发送").setTextBefore("获取验证码").setLenght(1 * 1000);
				mExplosionField.explode(regsubmit);
				regsubmit.setOnClickListener(null);
				new Thread() {
					public void run() {
						try {
							sleep(1000);
							finish();
						} catch (Exception e) {
							e.printStackTrace();
						}
					};
				}.start();
			}
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError("请求失败");
		}

	};

	private void addListener(View regsubmit) {
		if (regsubmit instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) regsubmit;
			for (int i = 0; i < parent.getChildCount(); i++) {
				addListener(parent.getChildAt(i));
			}
		} else {
			regsubmit.setClickable(true);
			regsubmit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String code=register_yzm.getText().toString();
					if (!TextUtils.isEmpty(code)) {
						progressDialog.show();
						SMSSDK.submitVerificationCode("86",register_tel.getText().toString(),register_yzm.getText().toString());
						strCode = code;
					}else {
						progressDialog.hide();
						Toast.makeText(FindPwdActivity.this,"验证码错误，请重新输入",Toast.LENGTH_LONG).show();
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
			case R.id.returnWrap:
				finish();
				break;
			case R.id.register_button_phone:
				if(!StringUitl.isNotEmpty(FindPwdActivity.this,register_tel,"请输入手机号码")){
					register_button_phone.setTextAfter("重新发送").setTextBefore("获取验证码").setLenght(5 * 1000);
					return;
				}
				if(StringUitl.isNotEmpty(FindPwdActivity.this,register_sure_pwd,"请输入密码")){
					int len = register_pwd.getText().toString().length();
					if (len >=6 && len <= 16) {
						if(register_pwd.getText().toString().equals(register_sure_pwd.getText().toString())){
							initDataFromNet();
						}else{
							Toast.makeText(FindPwdActivity.this,"两次密码输入不一致",Toast.LENGTH_LONG).show();
							register_button_phone.setTextAfter("重新发送").setTextBefore("获取验证码").setLenght(5 * 1000);
						}
					} else {
						ToastUtil.show(FindPwdActivity.this,"密码必须为6-16个数字/大小字母");
						register_button_phone.setTextAfter("重新发送").setTextBefore("获取验证码").setLenght(5 * 1000);
					}
				}else{
					register_button_phone.setTextAfter("重新发送").setTextBefore("获取验证码").setLenght(5 * 1000);
				}
				break;
		}
	}


	//匹配短信中间的6个数字（验证码等）
	private String patternCode(String patternContent) {
		if (TextUtils.isEmpty(patternContent)) {
			return null;
		}
		Pattern p = Pattern.compile(patternCoder);
		Matcher matcher = p.matcher(patternContent);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("event", "event=" + event);
			if (result == SMSSDK.RESULT_COMPLETE) {
				System.out.println("----" + event);
				//短信注册成功后
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
					if (result == SMSSDK.RESULT_COMPLETE) {
						Toast.makeText(getApplicationContext(), "短信验证成功", Toast.LENGTH_SHORT).show();
						register_button_phone.setTextAfter("重新发送").setTextBefore("获取验证码").setLenght(30 * 1000);
						loginController.getFindPwd(mRequestQueue, new Object[] {
								register_tel.getText().toString(),
								StringUitl.getMD5(register_pwd.getText().toString())}, listener);
					}
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
					Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();

				} else if (event == SMSSDK.RESULT_ERROR) {
					Toast.makeText(getApplicationContext(), "------", Toast.LENGTH_SHORT).show();
				}
			} else {
				progressDialog.hide();
				((Throwable) data).printStackTrace();
				try {
					JSONObject josn=new JSONObject(((Throwable) data).getMessage().toString());
					Toast.makeText(getApplicationContext(), josn.get("detail").toString(), Toast.LENGTH_SHORT).show();
				}catch (JSONException e){
					e.printStackTrace();
				}
			}

		}

	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		register_button_phone.onDestroy();
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
		unregisterReceiver(smsReceiver);

		if(captcha != null){
			captcha.removeCallbacksAndMessages(null);
		}
	}
}
