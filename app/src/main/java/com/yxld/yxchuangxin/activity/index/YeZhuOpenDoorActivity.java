package com.yxld.yxchuangxin.activity.index;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.DoorController;
import com.yxld.yxchuangxin.controller.impl.DoorControllerImpl;
import com.yxld.yxchuangxin.entity.AppYezhuFangwu;
import com.yxld.yxchuangxin.entity.OpenDoorCode;
import com.yxld.yxchuangxin.entity.ShareInfo;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.util.YouMengShareUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author wwx
 * @ClassName: phoneOpenDoorActivity
 * @Description: 手机开门
 * @date 2016年5月27日 下午5:14:24
 */
public class YeZhuOpenDoorActivity extends BaseActivity {

	/**门禁实现类*/
	private DoorController doorController;

	private YouMengShareUtil mengShareUtil = null;

	private TextView update,youxiaoqi,shareSms;

	/** 二维码图片 */
	private ImageView codeImg;

	private Intent intent;

	private Bundle bundle1;

	private  String address;

	private ShareInfo shareInfo = new ShareInfo();

	private AppYezhuFangwu house = new AppYezhuFangwu();
	private String shareUrl = "";

	/** 更新二维码时间*/
	private int UPDATETIME = 1000*30;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.phone_open_door_activity_layout);
		getSupportActionBar().setTitle("业主开门");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		shareInfo.setTitle("业主开门二维码");
		shareInfo.setShareCon(address);

		List<AppYezhuFangwu> list = Contains.appYezhuFangwus;
		if(list != null && list.size() != 0){
			house = list.get(0);
			address = house.getXiangmuLoupan()+""+house.getFwLoudong()+"栋"+house.getFwDanyuan()+"单元" +house.getFwFanghao();
			Log.d("geek","房屋"+house.toString());
			//第一次进入弹出加载
			progressDialog.show();
			initDataFromNet();
			handler.postDelayed(runnable, UPDATETIME); //每隔1s执行
		}
	}

	Handler handler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			//handler自带方法实现定时器
			try {
				handler.postDelayed(this, UPDATETIME);
				initDataFromNet();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("exception...");
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(handler != null){
			handler.removeCallbacks(runnable);
			handler = null;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
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
		shareSms.setVisibility(View.INVISIBLE);
		shareSms.setOnClickListener(this);
		youxiaoqi.setText("二维码即时更新中,复制无效。");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		}
	}

	@Override
	protected void initDataFromLocal() {
		mengShareUtil = new YouMengShareUtil(this);
	}

	private void getOpenDoor(String contentString,String time){
			if (!contentString.equals("")) {
				//根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
				Bitmap qrCodeBitmap =CodeUtils.createImage(contentString, 450, 450, BitmapFactory.decodeResource(getResources(), R.mipmap.login_icon_bg));
				codeImg.setImageBitmap(qrCodeBitmap);
				shareInfo.setBitmap(qrCodeBitmap);
				shareUrl = API.yuming+"/qr_code.html?timr="+time+"&code="+contentString;
				shareInfo.setImgUrl(shareUrl);
				youxiaoqi.setText("二维码即时更新中,复制无效。");
				codeImg.setVisibility(View.VISIBLE);
			}else {
				Toast.makeText(YeZhuOpenDoorActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
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
		if(!netWorkIsAvailable()){
			if(codeImg != null){
				codeImg.setVisibility(View.INVISIBLE);
			}
			if(youxiaoqi != null){
				youxiaoqi.setText("更新二维码失败！请检查您的网络状态");
			}

			return;
		}
		if(doorController == null ){
			doorController = new DoorControllerImpl();
		}
		//http://120.25.78.92/coed/getcode?bName=##&bPhone=##&bRole=##&building=##&buildingHouse=##&buildingUnit=##
		if(house != null && house.getFwyzType() != null && Contains.user.getYezhuShouji() != null
				&& house.getFwLoupanId() != null && house.getFwLoudong() != null
				&& house.getFwDanyuan() != null){

			String bname = "";
			//如果业主姓名为空时，设置为业主手机
			if(Contains.user.getYezhuName() == null || "".equals(Contains.user.getYezhuName())){
				bname = Contains.user.getYezhuShouji();
			}else{
				bname = Contains.user.getYezhuName();
			}
			///业主姓名/业主电话/业主角色/楼盘ID/楼栋/单元
			doorController.GetYEZHUDoorCODE(mRequestQueue,new Object[]{bname,
					Contains.user.getYezhuShouji(),
					String.valueOf(house.getFwyzType()),
					String.valueOf(house.getFwLoupanId()),
					house.getFwLoudong(),
					house.getFwDanyuan()},yezhuDoorCode);
		}else{
			ToastUtil.show(this,"业主信息不完善");
		}
	}

	/**
	 * 获取业主开门二维码
	 */
	private ResultListener<OpenDoorCode>  yezhuDoorCode = new ResultListener<OpenDoorCode>() {
		@Override
		public void onResponse(OpenDoorCode info) {
			Log.d("geek","业主开门 OpenDoorCode info"+info.toString());
			//成功
			if(info != null && info.getState() != null && "0".equals(info.getState())){
				getOpenDoor(info.getCode(),info.getTime());
			}else{
				if(youxiaoqi != null){
					youxiaoqi.setText("更新二维码失败！");
				}
			}
			progressDialog.hide();
		}

		@Override
		public void onErrorResponse(String errMsg)
		{
			if(youxiaoqi != null){
				youxiaoqi.setText("网络连接失败！");
			}
			progressDialog.hide();
		}
	};

}
