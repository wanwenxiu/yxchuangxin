package com.yxld.yxchuangxin.activity.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ysf.explosionfield.ExplosionField;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.LoginController;
import com.yxld.yxchuangxin.controller.impl.LoginControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallUser;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: UpdatePwd 
 * @Description: 修改密码 
 * @author wwx
 * @date 2016年4月18日 下午3:05:34 
 */
public class UpdatePwd extends BaseActivity {
	private EditText old_password;
	private EditText new_password;
	private EditText repeat_password;
	private TextView next_step;
	private LoginController loginController;
	private ExplosionField mExplosionField;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_updatepwd);
		getSupportActionBar().setTitle("修改密码");
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
		old_password = (EditText) findViewById(R.id.old_password);
		new_password = (EditText) findViewById(R.id.new_password);
		repeat_password = (EditText) findViewById(R.id.repeat_password);
		next_step = (TextView) findViewById(R.id.next_step);
		mExplosionField = ExplosionField.attach2Window(this);
		addListener(findViewById(R.id.next_step));
	}

	@Override
	protected void initDataFromNet() {
		if (loginController == null) {
			loginController = new LoginControllerImpl();
		}
		String old_pwd = old_password.getText().toString();
		String new_pwd = new_password.getText().toString();
		String repeat_pwd = repeat_password.getText().toString();
		String id = Contains.user.getYezhuShouji();
		if (new_pwd.equals(repeat_pwd)) {
			loginController.getUpdatePwd(mRequestQueue, new Object[] { id,
					old_pwd, new_pwd }, listener);
		} else {
			Toast.makeText(UpdatePwd.this, "两次输入的新密码不一致",
					Toast.LENGTH_SHORT).show();
		}

	}

	private ResultListener<BaseEntity> listener = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			Log.d("...", info.toString());
			if (info.status != STATUS_CODE_OK) {
				if (info.MSG.equals("旧密码错误")) {
					Toast.makeText(UpdatePwd.this, "旧密码输入错误，请重新输入",
							Toast.LENGTH_SHORT).show();
					return;
				}
				onError(info.MSG);
				return;
			}
			mExplosionField.explode(next_step);
			next_step.setOnClickListener(null);
			Contains.user.setYezhuPwd(new_password.getText().toString());
//			dbUtil.clearData(CxwyMallUser.class);
//			dbUtil.insert(Contains.cxwyMallUser, Contains.cxwyMallUser.getUserId() + "");
			finish();
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError("请求失败");
		}
	};

	private void addListener(View next_step) {
		if (next_step instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) next_step;
			for (int i = 0; i < parent.getChildCount(); i++) {
				addListener(parent.getChildAt(i));
			}
		} else {
			next_step.setClickable(true);
			next_step.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					initDataFromNet();
				}
			});
		}
	}

	private void reset(View next_step) {
		if (next_step instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) next_step;
			for (int i = 0; i < parent.getChildCount(); i++) {
				reset(parent.getChildAt(i));
			}
		} else {
			next_step.setScaleX(1);
			next_step.setScaleY(1);
			next_step.setAlpha(1);
		}
	}

	@Override
	protected void initDataFromLocal() {

	}

	@Override
	public void onClick(View v) {
	}

}
