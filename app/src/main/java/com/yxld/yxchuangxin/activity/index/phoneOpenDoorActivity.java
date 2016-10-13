package com.yxld.yxchuangxin.activity.index;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.Main.NewMainActivity2;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.ComplaintController;
import com.yxld.yxchuangxin.controller.impl.ComplaintControllerImpl;

import com.yxld.yxchuangxin.entity.ShareInfo;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.util.YouMengShareUtil;
import com.yxld.yxchuangxin.view.LineGridView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wwx
 * @ClassName: phoneOpenDoorActivity
 * @Description: 手机开门
 * @date 2016年5月27日 下午5:14:24
 */
public class phoneOpenDoorActivity extends BaseActivity {

	private YouMengShareUtil mengShareUtil = null;

	private TextView update;

	private Intent intent;

	private Bundle bundle1;

	private  String name;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.phone_open_door_activity_layout);
		getSupportActionBar().setTitle("手机开门");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		intent = getIntent();
		bundle1 = intent.getExtras();
		name = bundle1.getString("name");
		Log.d("...",name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}

		if (item.getItemId() == R.id.action_share) {
			mengShareUtil.addCustomPlatforms(new ShareInfo("手机开门二维码", "华菱蓝调国际1栋1201", API.PIC + "/wygl/files/img/201605/2code.png"));
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void initView() {
		update = (TextView) findViewById(R.id.update);
		update.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.update:
				break;
		}
	}


	@Override
	protected void initDataFromLocal() {
		Log.d("geek", "进入initDataFromLocal（）");
		mengShareUtil = new YouMengShareUtil(this);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
//			if (name.equals("1")) {
//				Intent intent = new Intent(phoneOpenDoorActivity.this, NewMainActivity2.class);
//				setResult(RESULT_OK, intent);
//				ActivityOptions opts = null;
//				opts = ActivityOptions.makeCustomAnimation(
//						phoneOpenDoorActivity.this, R.anim.slide_up_in,
//						R.anim.slide_down_out);
//				startActivity(intent, opts.toBundle());
//				finish();
//			}   else if (name.equals("2")){
//				finish();
//			}
		}
		return false;
	}
}
