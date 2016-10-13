package com.yxld.yxchuangxin.activity.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class UpdateCard extends BaseActivity {
	private EditText old_card;
	private ImageView card_clear;
	private TextView update_card;
	private LoginController loginController;
	private ExplosionField mExplosionField;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_updatecard);
		getSupportActionBar().setTitle("修改身份证");
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
	protected void initView() {
		old_card = (EditText) findViewById(R.id.old_card);
		card_clear = (ImageView) findViewById(R.id.card_clear);
		update_card = (TextView) findViewById(R.id.update_card);

		old_card.setText(Contains.cxwyMallUser.getUserIdCard());
		old_card.setSelection(old_card.getText().length());
		card_clear.setOnClickListener(this);
		mExplosionField = ExplosionField.attach2Window(this);
		addListener(findViewById(R.id.update_card));
	}

	@Override
	protected void initDataFromNet() {
		if (loginController == null) {
			loginController = new LoginControllerImpl();
		}
		String id = Contains.cxwyMallUser.getUserId().toString();
		String card = old_card.getText().toString();

		loginController.getUpdateCard(mRequestQueue, new Object[] { id, card },
				listener);
	}

	private ResultListener<BaseEntity> listener = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			Log.d("...", info.toString());
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
			}
			mExplosionField.explode(update_card);
			update_card.setOnClickListener(null);
			Contains.cxwyMallUser
					.setUserIdCard(old_card.getText().toString());
			Toast.makeText(UpdateCard.this, "修改成功", Toast.LENGTH_SHORT).show();
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

	private void addListener(View update_card) {
		if (update_card instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) update_card;
			for (int i = 0; i < parent.getChildCount(); i++) {
				addListener(parent.getChildAt(i));
			}
		} else {
			update_card.setClickable(true);
			update_card.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					initDataFromNet();
				}
			});
		}
	}

	@SuppressWarnings("unused")
	private void reset(View update_card) {
		if (update_card instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) update_card;
			for (int i = 0; i < parent.getChildCount(); i++) {
				reset(parent.getChildAt(i));
			}
		} else {
			update_card.setScaleX(1);
			update_card.setScaleY(1);
			update_card.setAlpha(1);
		}
	}

	@Override
	protected void initDataFromLocal() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.card_clear:
			old_card.setText("");
			break;
		}
	}

}
