package com.yxld.yxchuangxin.activity.index;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseFragment;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.DoorController;
import com.yxld.yxchuangxin.controller.impl.DoorControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyYezhu;
import com.yxld.yxchuangxin.entity.OpenDoorCode;
import com.yxld.yxchuangxin.entity.ShareInfo;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.util.YouMengShareUtil;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yishangfei on 2016/11/5 0005.
 * 业主二维码
 */
public class CodeFragment extends BaseFragment {
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

	private CxwyYezhu yezhu = new CxwyYezhu();
	private String shareUrl = "";

	/** 更新二维码时间*/
	private int UPDATETIME = 1000*30;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.phone_open_door_activity_layout, container, false);
		initview(view);
		return view;
	}

	private void initview(View view) {
		shareInfo.setTitle("业主开门二维码");
		shareInfo.setShareCon(address);

		List<CxwyYezhu> list = Contains.cxwyYezhu;
		yezhu = list.get(0);
		address = yezhu.getYezhuLoupan()+""+yezhu.getYezhuLoudong()+"栋"+yezhu.getYezhuDanyuan()+"单元" +yezhu.getYezhuFanghao();
		Log.d("geek","业主"+yezhu.toString());
		handler.postDelayed(runnable, UPDATETIME); //每隔1s执行
		initDataFromNet();
		update = (TextView)view.findViewById(R.id.update);
		update.setOnClickListener(this);
		codeImg = (ImageView) view.findViewById(R.id.codeImg);
		youxiaoqi = (TextView) view.findViewById(R.id.youxiaoqi);
		shareSms = (TextView)view.findViewById(R.id.shareSms);
		shareSms.setVisibility(View.INVISIBLE);
		shareSms.setOnClickListener(this);
		youxiaoqi.setText("二维码即时更新中,复制无效。");
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
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
	public void onDestroy() {
		super.onDestroy();
		if(handler != null){
			handler.removeCallbacks(runnable);
			handler = null;
		}
	}

	@Override
	protected void initDataFromLocal() {
		mengShareUtil = new YouMengShareUtil(getActivity());
	}

	private void getOpenDoor(String contentString,String time){
			if (!contentString.equals("")) {
				//根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
				Bitmap qrCodeBitmap =  CodeUtils.createImage(contentString, 450, 450, BitmapFactory.decodeResource(getResources(), R.mipmap.login_icon_bg));
				codeImg.setImageBitmap(qrCodeBitmap);
				shareInfo.setBitmap(qrCodeBitmap);
				shareUrl = API.yuming+"/qr_code.html?timr="+time+"&code="+contentString;
				shareInfo.setImgUrl(shareUrl);
				youxiaoqi.setText("二维码即时更新中,复制无效。");
				codeImg.setVisibility(View.VISIBLE);
			}else {
				Toast.makeText(getActivity(), "生成二维码失败", Toast.LENGTH_SHORT).show();
			}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			getActivity().finish();
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
		Log.d("geek","initDataFromNet");
		if(doorController == null ){
			doorController = new DoorControllerImpl();
		}

		if(yezhu != null && yezhu.getYezhuName() != null && yezhu.getYezhuParentId() != null
				&& yezhu.getYezhuGuanxi() != null && yezhu.getYezhuShouji() != null
				&& yezhu.getYezhuBeizhu2() != null && yezhu.getYezhuLoudong() != null
				&& yezhu.getYezhuDanyuan() != null){

			int Role = 0;
			if(yezhu.getYezhuParentId() == 0){
				Role = 0;
			}

			if(yezhu.getYezhuGuanxi() != null && !"".equals(yezhu.getYezhuGuanxi())){
				if("家人".equals(yezhu.getYezhuGuanxi())){
					Role = 1;
				}else if("租客".equals(yezhu.getYezhuGuanxi())){
					Role = 2;
				}
			}

			String name = "";
			try {
				name =  URLEncoder.encode(yezhu.getYezhuName(),"UTF-8").toString();
			}catch (Exception e){
				Log.d("geek","业主用户名编码失败");
			}
			///业主姓名/业主电话/业主角色/楼盘ID/楼栋/单元
			doorController.GetYEZHUDoorCODE(mRequestQueue,new Object[]{name,yezhu.getYezhuShouji(),Role,yezhu.getYezhuBeizhu2()
					,yezhu.getYezhuLoudong(),yezhu.getYezhuDanyuan()},yezhuDoorCode);
		}else{
			ToastUtil.show(getActivity(),"业主信息不完善");
		}

	}

	/**
	 * 获取业主开门二维码
	 */
	private ResultListener<OpenDoorCode> yezhuDoorCode = new ResultListener<OpenDoorCode>() {
		@Override
		public void onResponse(OpenDoorCode info) {
			Log.d("geek","业主开门 OpenDoorCode info"+info.toString());
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
		public void onErrorResponse(String errMsg) {
				//onError("网络连接失败");
			if(youxiaoqi != null){
				youxiaoqi.setText("网络连接失败！");
			}
		}
	};

}
