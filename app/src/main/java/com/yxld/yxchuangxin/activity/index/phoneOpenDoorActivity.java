package com.yxld.yxchuangxin.activity.index;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.DoorController;
import com.yxld.yxchuangxin.controller.impl.DoorControllerImpl;
import com.yxld.yxchuangxin.entity.ShareInfo;
import com.yxld.yxchuangxin.util.YouMengShareUtil;
import com.zxing.encoding.EncodingHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wwx
 * @ClassName: phoneOpenDoorActivity
 * @Description: 手机访客开门
 * @date 2016年5月27日 下午5:14:24
 */
public class phoneOpenDoorActivity extends BaseActivity {

	private YouMengShareUtil mengShareUtil = null;

	private TextView update,youxiaoqi,shareSms;

	/** 二维码图片 */
	private ImageView codeImg;

	private Intent intent;

	private Bundle bundle1;

	private  String time,address,codestr;

	private ShareInfo shareInfo = new ShareInfo();

	private String shareUrl = "";

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.phone_open_door_activity_layout);
		getSupportActionBar().setTitle("手机开门");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		intent = getIntent();
		bundle1 = intent.getExtras();
		codestr = bundle1.getString("codestr");
		time = bundle1.getString("time");
		address = bundle1.getString("address");
		shareInfo.setTitle("手机开门二维码");
		shareInfo.setShareCon(address);

		Log.d("geek",codestr+","+time);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}

		if (item.getItemId() == R.id.action_share) {
			mengShareUtil.addCustomPlatforms(shareInfo);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void initView() {
		update = (TextView) findViewById(R.id.update);
		update.setOnClickListener(this);
		codeImg = (ImageView) findViewById(R.id.codeImg);
		youxiaoqi = (TextView) findViewById(R.id.youxiaoqi);
		shareSms = (TextView)findViewById(R.id.shareSms);
		shareSms.setOnClickListener(this);
		update.setVisibility(View.INVISIBLE);

		getOpenDoor(codestr);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//			case R.id.update:
//				break;
//			case R.id.shareSms:
//				if(shareUrl == null || "".equals(shareUrl)){
//					Toast.makeText(phoneOpenDoorActivity.this,"未获取到分享二维码链接",Toast.LENGTH_SHORT).show();
//					return;
//				}
//				sendSMS(shareUrl);
//				break;
		}
	}


	@Override
	protected void initDataFromLocal() {
		Log.d("geek", "进入initDataFromLocal（）");
		mengShareUtil = new YouMengShareUtil(this);
	}


	private void getOpenDoor(String contentString){
		try {
			if (!contentString.equals("")) {
				//根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
				Bitmap qrCodeBitmap = EncodingHandler.createQRCode(contentString, 450);
				codeImg.setImageBitmap(qrCodeBitmap);
				youxiaoqi.setText("有效期至："+time);
				shareInfo.setBitmap(qrCodeBitmap);
				shareInfo.setImgUrl(API.yuming+"/qr_code.html?timr="+time+"&code="+codestr);
			}else {
				Toast.makeText(phoneOpenDoorActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
		}
		return false;
	}


	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();

	}

//	/**
//	 * 发短信
//	 */
//	private   void  sendSMS(String webUrl){
//
//		String smsBody = address+"开门二维码地址:" + webUrl;
//		Uri smsToUri = Uri.parse( "smsto:" );
//		Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);
//		//sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
//		//短信内容
//		sendIntent.putExtra( "sms_body", smsBody);
//		sendIntent.setType( "vnd.android-dir/mms-sms" );
//		startActivityForResult(sendIntent, 1002 );
//	}

}
