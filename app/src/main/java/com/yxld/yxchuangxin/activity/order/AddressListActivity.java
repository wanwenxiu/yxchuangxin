package com.yxld.yxchuangxin.activity.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.AddressAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.AddressController;
import com.yxld.yxchuangxin.controller.impl.AddressControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallAdd;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * Created by yishangfei on 2016/3/4.
 */
@SuppressLint("HandlerLeak")
public class AddressListActivity extends BaseActivity {
	/** 删除地址*/
	private final int ADDRESS_DELETE  = 1;
	/** 默认地址*/
	private final int ADDRESS_DEFALU  = 2;
	
	private ListView address_list;
	/** 收货地址为空 文字 */
	private TextView shopAddressTest = null;
	/** 添加收货地址 按钮 */
	private TextView addShopAddress = null;
	
	private AddressController addressController ;
	
	private List<CxwyMallAdd> mList = new ArrayList<CxwyMallAdd>();
	private AddressAdapter adapter;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.delivery_address_fragment);
		getSupportActionBar().setTitle("我的收获地址");
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
	protected void onResume() {
		super.onResume();
		initDataFromNet();
	}
	
	@Override
	protected void initDataFromLocal() {
	}

	@Override
	protected void initView() {
		address_list = (ListView) findViewById(R.id.address_list);
		shopAddressTest = (TextView) findViewById(R.id.shopAddressTest);
		addShopAddress = (TextView) findViewById(R.id.addAddress);
		addShopAddress.setOnClickListener(this);


		adapter = new AddressAdapter(mList, this,mHandler);
		address_list.setAdapter(adapter);
		address_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//当前一个窗体是确认订单时 点击条目时返回地址
				if(getIntent() != null && getIntent().getStringExtra("AddressTag") != null && getIntent().getStringExtra("AddressTag").equals("sureOrder")){
					 if(mList.size() == 0){
						return;
					}
					Contains.defuleAddress = mList.get(position);
					finish();
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addAddress:
			//新建收货地址界面
			startActivity(addAddressActivity.class);
			break;
		default:
			break;
		}
	}

	
	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if(addressController == null){
			addressController = new AddressControllerImpl();
		}
		
		addressController.getAddressListFromID(mRequestQueue, new Object[]{Contains.user.getYezhuId()}, getListListener);
	}
	
	private  ResultListener<CxwyMallAdd> getListListener = new ResultListener<CxwyMallAdd>() {
		
		@Override
		public void onResponse(CxwyMallAdd info) {
			if (info.status != STATUS_CODE_OK) {  
				onError(info.MSG);
				return;
			} 
			if(isEmptyList(info.getAddList())){
				shopAddressTest.setVisibility(View.VISIBLE);
				address_list.setVisibility(View.GONE);
			}else{
				shopAddressTest.setVisibility(View.GONE);
				address_list.setVisibility(View.VISIBLE);
				mList = info.getAddList();
				Collections.sort(mList, new Comparator<CxwyMallAdd>() {
					@Override
					public int compare(CxwyMallAdd arg0, CxwyMallAdd arg1) {
						return arg0.getAddStatus().compareTo(
								arg1.getAddStatus());
					}
				});
				adapter.setmList(mList);
			}
			
			progressDialog.hide();
		}
		
		@Override
		public void onErrorResponse(String errMsg) {
			
		}
	};

	
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ADDRESS_DELETE:
				int addId = msg.arg1;
				progressDialog.show();
				addressController.deleteAddressFromID(mRequestQueue, new Object[]{addId}, listener);
				break;
			case ADDRESS_DEFALU:
				int addIds = msg.arg1;
				progressDialog.show();
				addressController.defuleAddressFromID(mRequestQueue, new Object[]{addIds}, listener);
				break;
			default:
				break;
			}
		};
	};
	
	private ResultListener<BaseEntity> listener = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			if (info.status != STATUS_CODE_OK) {  
				onError(info.MSG);
				return;
			}
			initDataFromNet();
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError(errMsg);
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mList != null){
			mList = null;
		}
	}
}
