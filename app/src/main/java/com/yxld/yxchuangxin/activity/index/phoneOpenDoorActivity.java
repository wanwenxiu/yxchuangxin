package com.yxld.yxchuangxin.activity.index;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.entity.ShareInfo;
import com.yxld.yxchuangxin.util.YouMengShareUtil;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		Log.d("geek","获取内容为："+codestr+","+time);
		address = bundle1.getString("address");
		shareInfo.setTitle("手机开门二维码");
		shareInfo.setShareCon(address);
	}

	@Override
	protected void initDataFromLocal() {
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
			if(mengShareUtil == null){
				mengShareUtil = new YouMengShareUtil(this);
			}
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

		try {
			getOpenDoor(codestr);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		}
	}

	public static String getFormatedDateTime(String pattern, long dateTime) {
		Log.d("geek", "getFormatedDateTime: pattern"+pattern+",dateTime="+dateTime);
		SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
		return sDateFormat.format(new Date(dateTime));
	}

	private void getOpenDoor(String contentString)  throws  Exception{
			if (!contentString.equals("")) {
				//根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
				Bitmap qrCodeBitmap = CodeUtils.createImage(contentString, 450, 450, BitmapFactory.decodeResource(getResources(), R.mipmap.login_icon_bg));
				//设置二维码图片
				codeImg.setImageBitmap(qrCodeBitmap);
				Log.d("geek", "getOpenDoor: time"+time);
				Timestamp dateStr = new Timestamp(Long.parseLong(time));

				System.out.println(time.toString());
				Log.d("geek", "getOpenDoor: dateStr"+dateStr);
				youxiaoqi.setText("有效期至："+dateStr);

				//设置分享内容
				shareInfo.setBitmap(qrCodeBitmap);
				String qqurl = API.yuming+"/qr_code.html?timr="+time+"&code="+codestr;
				shareInfo.setQQImgUrl(qqurl);

				try{
					codestr = URLEncoder.encode(codestr,"UTF-8").toString();
				}catch (Exception e){
					e.printStackTrace();
				}
				shareInfo.setImgUrl(API.yuming+"/qr_code.html?timr="+time+"&code="+codestr);

				Log.d("geek","手机界面设置完成shareInfo ="+shareInfo.toString());

			}else {
				Toast.makeText(phoneOpenDoorActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
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
