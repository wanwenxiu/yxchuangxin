package com.yxld.yxchuangxin.activity.mine;

import java.util.HashMap;
import java.util.Map;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ysf.explosionfield.ExplosionField;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.LoginController;
import com.yxld.yxchuangxin.controller.impl.LoginControllerImpl;
import com.yxld.yxchuangxin.listener.ResultListener;

public class UpdateName extends BaseActivity {
	private EditText old_name;
	private ImageView clear;
	private TextView update_name;
	private LoginController loginController;
	private ExplosionField mExplosionField;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_updatename);
		getSupportActionBar().setTitle("修改昵称");
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
		old_name = (EditText) findViewById(R.id.old_name);
		clear = (ImageView) findViewById(R.id.clear);
		update_name = (TextView) findViewById(R.id.update_name);

		old_name.setText(Contains.user.getYezhuName());
		old_name.setSelection(old_name.getText().length());
		clear.setOnClickListener(this);
		mExplosionField = ExplosionField.attach2Window(this);
		addListener(findViewById(R.id.update_name));
	}

	@Override
	protected void initDataFromNet() {
		if (loginController == null) {
			loginController = new LoginControllerImpl();
		}
		String id = Contains.user.getYezhuId().toString();
		String name = old_name.getText().toString();
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("user.userId", id);
		parm.put("user.userUserName", name);
		loginController.getUpdateName(mRequestQueue, parm, listener);
	}

	private ResultListener<BaseEntity> listener = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			Log.d("...", info.toString());
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
			}
			mExplosionField.explode(update_name);
			update_name.setOnClickListener(null);
			Contains.user
					.setYezhuName(old_name.getText().toString());
			Toast.makeText(UpdateName.this, "修改成功", Toast.LENGTH_SHORT).show();
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
		};

		@Override
		public void onErrorResponse(String errMsg) {
			onError("请求失败");
		}
	};

	private void addListener(View update_name) {
		if (update_name instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) update_name;
			for (int i = 0; i < parent.getChildCount(); i++) {
				addListener(parent.getChildAt(i));
			}
		} else {
			update_name.setClickable(true);
			update_name.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					initDataFromNet();
				}
			});
		}
	}

	@SuppressWarnings("unused")
	private void reset(View update_name) {
		if (update_name instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) update_name;
			for (int i = 0; i < parent.getChildCount(); i++) {
				reset(parent.getChildAt(i));
			}
		} else {
			update_name.setScaleX(1);
			update_name.setScaleY(1);
			update_name.setAlpha(1);
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
		case R.id.clear:
			old_name.setText("");
			break;
		}
	}

}
