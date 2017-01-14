package com.yxld.yxchuangxin.activity.order;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.AddressController;
import com.yxld.yxchuangxin.controller.RepairController;
import com.yxld.yxchuangxin.controller.impl.AddressControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallAdd;
import com.yxld.yxchuangxin.entity.CxwyXiangmu;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yishangfei on 2016/3/4.
 */
public class addAddressActivity extends BaseActivity {
//	String[] city = { };
//	private Spinner spinner1;
	
	/** 姓名*/
	private EditText editName;
	/** 电话*/
	private EditText editPhone;
//	/** 选择省市*/
//	private TextView selectAddress;
	/** 详细地址*/
	private EditText editDestail;

	/** 保存地址*/
	private TextView addAddress,xiaoqu;
	
	private RepairController repairController;
	
	private List<CxwyXiangmu> listData =  new ArrayList<CxwyXiangmu>();
	
	private AddressController addressController ;
	
//	private String xiangmuName;
	

	/** 添加地址 为 true 。修改地址为false*/
	private boolean isADD = true;

	/** 0为默认地址，1不位默认地址*/
	private int  updateIsDefalu = 1;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.new_address);
		getSupportActionBar().setTitle("新增收货地址");
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
		editName =(EditText) findViewById(R.id.editName);
		editPhone =(EditText) findViewById(R.id.editPhone);
//		selectAddress = (TextView) findViewById(R.id.selectAddress);
		xiaoqu = (TextView) findViewById(R.id.xiaoqu);
//		selectAddress.setOnClickListener(this);
		editDestail = (EditText) findViewById(R.id.editDestail);
		
//		spinner1 = (Spinner)findViewById(R.id.spinner1);
//		spinner1.setTextAlignment();
		addAddress = (TextView) findViewById(R.id.addAddress);
		addAddress.setOnClickListener(this);
		xiaoqu.setText(Contains.curSelectXiaoQuName);
		
//		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0,
//					View arg1, int arg2, long arg3) {
//				if(city != null && city.length!=0){
//					xiangmuName = city[arg2];
//				}
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//
//			}
//
//		});
		initDataFromNet();
	}
	
	@Override
	protected void initDataFromLocal() {
		address = (CxwyMallAdd) getIntent().getSerializableExtra("address");
		if(address != null){
			isADD = false;
			setTitle("修改收货地址");
			editName.setText(address.getAddName());
			editPhone.setText(address.getAddTel());
//			selectAddress.setText(address.getAddSpare1());
			editDestail.setText(address.getAddAdd());
			xiaoqu.setText(address.getAddVillage());
			updateIsDefalu = address.getAddStatus();
		}else{
			isADD = true;
			updateIsDefalu = 1;
			setTitle("新增收获地址");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addAddress:
			addAddress();
			break;
		case R.id.selectAddress:
//			AddressPicker addressPicker = new AddressPicker();
//			addressPicker.selectAddressDialog(this,selectAddress);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void initDataFromNet() {
//		super.initDataFromNet();
//		if (repairController == null) {
//			repairController = new ReparirControllerImpl();
//		}
//		repairController.getProject(mRequestQueue, listener);
	}
	
//	private ResultListener<RepairList> listener = new ResultListener<RepairList>() {
//
//		@Override
//		public void onResponse(RepairList info) {
//			progressDialog.hide();
//			// 获取请求码
//			if (info.status != STATUS_CODE_OK) {
//				onError(info.MSG);
//				return;
//			}
//			if (isEmptyList(info.getRows())) {
//				onError("获取失败");
//			} else {
//				listData = info.getRows();
//				city = new String[info.getRows().size()];
//				for (int i = 0; i < info.getRows().size(); i++) {
//
//					String xq = info.getRows().get(i).getXiangmuLoupan();
//					city[i] = xq;
//					ArrayAdapter aa = new ArrayAdapter(addAddressActivity.this,
//							android.R.layout.simple_dropdown_item_1line, city);
//					spinner1.setAdapter(aa);
//				}
//			}
//
//		}
//
//		@Override
//		public void onErrorResponse(String errMsg) {
//			onError("请求失败");
//		}
//	};

	
	/**
	 * @Title: addAddress 
	 * @Description: 判断信息是否填写完整    
	 * @return void
	 * @throws
	 */
	public void addAddress() {
		if(StringUitl.isNotEmpty(this, editName, "请填写收货人")){
			if(StringUitl.isNotEmpty(this, editPhone, "请填写联系电话")){
				if(StringUitl.isNotEmpty(this, editDestail, "请填写详细地址")){
//					if(selectAddress.getText().equals("请选择省市区") || selectAddress.getText().equals("")){
//						ToastUtil.show(this, "请选择省市区");
//					}else{
					if(!StringUitl.isMobileNum(editPhone.getText().toString())){
						ToastUtil.show(this,"请输入正确手机号码");
						return;
					}
					requestAddUpdateAdd();
//					}
				}
			}
		}
		
	}

	/**
	 * @Title: requestAddUpdateAdd 
	 * @Description:请求新增或修改地址    
	 * @return void
	 * @throws
	 */
	private void requestAddUpdateAdd() {
		if(addressController == null){
			addressController = new AddressControllerImpl();
		}
		
		progressDialog.show();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("add.addName", editName.getText().toString());
		map.put("add.addVillage", xiaoqu.getText().toString());
		map.put("add.addTel", editPhone.getText().toString());
//		map.put("add.addSpare1", selectAddress.getText().toString());
		map.put("add.addAdd", editDestail.getText().toString());
		map.put("add.addUserName", Contains.user.getYezhuName()+"");
		map.put("add.addStatus", String.valueOf(updateIsDefalu));
		map.put("add.addSpare2", Contains.user.getYezhuId()+"");
		Log.d("geek","添加修改地址map"+map.toString());
		if(isADD){
			addressController.addAddress(mRequestQueue, map, AddressListener);
		}else{
			map.put("add.addId", address.getAddId()+"");
			addressController.updateAddress(mRequestQueue, map, AddressListener);
		}
	}
	
	private ResultListener<BaseEntity> AddressListener = new ResultListener<BaseEntity>() {
		
		@Override
		public void onResponse(BaseEntity info) {
			progressDialog.hide();
			if (info.status != STATUS_CODE_OK) {  
				onError(info.MSG);
				return;
			} 
			finish();
		}
		
		@Override
		public void onErrorResponse(String errMsg) {
			onError(errMsg);
		}
	};
	private CxwyMallAdd address;


	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(listData != null){
			listData = null;
		}
	}
}
