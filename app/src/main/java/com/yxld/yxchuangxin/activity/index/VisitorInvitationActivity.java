package com.yxld.yxchuangxin.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.DoorController;
import com.yxld.yxchuangxin.controller.impl.DoorControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyYezhu;
import com.yxld.yxchuangxin.entity.Door;
import com.yxld.yxchuangxin.entity.OpenDoorCode;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: VisitorInvitationActivity
 * @Description: 访客邀请
 * @author wwx
 * @date 2016年5月27日 下午5:14:24
 */
public class VisitorInvitationActivity extends BaseActivity implements ResultListener<BaseEntity> {
	/**门禁实现类*/
	private DoorController DoorController;
	/** 用户地址*/
	private TextView addr;
	/** 姓名*/
	private EditText name;
	/** 电话*/
	private EditText phone;
	/** 确认按钮*/
	private TextView sure;
//	/** 门禁下拉框*/
//	private MaterialSpinner doorspinner;

	private List<String> doorNameList = new ArrayList<>();
	private List<Door> doorList = new ArrayList<>();
	private  CxwyYezhu yezhu = new CxwyYezhu();
	String address = "";


	private String mac;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.visitor_invitation_activity_layout);
		getSupportActionBar().setTitle("来访邀请");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		List<CxwyYezhu> list = Contains.cxwyYezhu;
		yezhu = list.get(0);
		Log.d("geek","业主"+yezhu.toString());
		initDataFromNet();
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
		sure = (TextView)findViewById(R.id.sure);
		name = (EditText)findViewById(R.id.name);
		phone = (EditText)findViewById(R.id.phone);
		sure.setOnClickListener(this);
//		doorspinner = (MaterialSpinner) findViewById(R.id.doorspinner);
		addr = (TextView) findViewById(R.id.addr);

		address = yezhu.getYezhuLoupan()+""+yezhu.getYezhuLoudong()+"栋"+yezhu.getYezhuDanyuan()+"单元" +yezhu.getYezhuFanghao();
		addr.setText(address);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.sure:  //确认 请求生成二维码
				if(mac !=null && StringUitl.isNoEmpty(mac)){
					if(StringUitl.isNotEmpty(this,name,"请输入姓名") &&
							StringUitl.isNotEmpty(this,phone,"请输入电话")
							){
						Map<String, String> parm = new HashMap<String, String>();
						parm.put("name", name.getText().toString());
						parm.put("tel", phone.getText().toString());
						parm.put("houses", "小区");
						parm.put("yezhuid", yezhu.getYezhuId()+"");
						parm.put("machineMAC", mac);
						progressDialog.show();
						DoorController.GetOPENDoorList(mRequestQueue, parm, OpenDoorCode);
					}
				}else{
					ToastUtil.show(this,"获取门禁失败");
				}
				break;
			default:
				break;

		}
	}



	@Override
	protected void initDataFromLocal() {
	}

	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if (DoorController == null) {
			DoorController = new DoorControllerImpl();
		}


		doorList.clear();
		doorNameList.clear();

		Map<String, String> parm = new HashMap<String, String>();
		parm.put("xiaoquId",yezhu.getYezhuId()+"");
		parm.put("houses", yezhu.getYezhuLoupan());
		parm.put("dong", yezhu.getYezhuLoudong());
		parm.put("danyuan", yezhu.getYezhuDanyuan());

		Log.d("geek","获取门禁列表"+parm.toString());
		DoorController.GetDoorList(mRequestQueue, parm, this);
	}

	@Override
	public void onResponse(BaseEntity info) {
		Log.d("geek","门禁 info="+info.toString());
		mac = info.MSG;

//		if (isEmptyList(info.getRows())) {
//			ToastUtil.show(VisitorInvitationActivity.this, "没有查询到记录");
//		}else{
//			doorList = info.getRows();
//			for (int i =0;i<doorList.size();i++){
//				String name = doorList.get(i).getMenjinname();
//				if(name != null && !name.equals("")){
//					doorNameList.add(name);
//				}else{
//					doorNameList.add("");
//				}
//			};
//			doorspinner.setItems(doorNameList);
//			Log.d("geek","获取的文字doorNameList"+doorNameList.toString());
//		}
		progressDialog.hide();
	}

	@Override
	public void onErrorResponse(String errMsg) {
		onError(errMsg);
	}


	private ResultListener<OpenDoorCode>  OpenDoorCode = new ResultListener<com.yxld.yxchuangxin.entity.OpenDoorCode>() {
		@Override
		public void onResponse(OpenDoorCode info) {
			Log.d("geek","OpenDoorCode info"+info.toString());

			Log.d("geek","OpenDoorCode 数据"+info.getCode().toString());
			if(info != null && info.getCode().getStr() != null && !"".equals(info.getCode().getStr()) && info.getCode().getShijian() != null
					&& !"".equals(info.getCode().getShijian() )){
				Intent intent = new Intent(VisitorInvitationActivity.this,
						phoneOpenDoorActivity.class);
				Bundle bundle1 = new Bundle();
				bundle1.putString("codestr",info.getCode().getStr());
				bundle1.putString("time",info.getCode().getShijian());
				bundle1.putString("address",address);
				intent.putExtras(bundle1);
				startActivity(intent, bundle1);

			}else{
				ToastUtil.show(VisitorInvitationActivity.this,"获取二维码失败");
			}
			progressDialog.hide();
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError(errMsg);
		}
	};
}
