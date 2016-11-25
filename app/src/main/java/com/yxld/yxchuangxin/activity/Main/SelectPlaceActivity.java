package com.yxld.yxchuangxin.activity.Main;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.SelectPlaceListAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.RepairController;
import com.yxld.yxchuangxin.controller.impl.ReparirControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyXiangmu;
import com.yxld.yxchuangxin.entity.RepairList;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;

/**
 * @ClassName: SelectPlaceActivity 
 * @author 选择地点界面
 * @date 2016年4月6日 下午2:49:40 
 */
public class SelectPlaceActivity extends BaseActivity {

	/** 小区列表*/
	private ListView addressList ;
	/** 定位地点提示*/
	private TextView placeTipTv;
	/** 定位地点*/
	private TextView placeTv;
	
	/** 报修接口实现类*/
	private RepairController repairController;
	/** 小区集合*/
	private List<CxwyXiangmu> listData =  new ArrayList<CxwyXiangmu>();
	
	/** 项目适配器*/
	private SelectPlaceListAdapter adapter;


	private RelativeLayout location;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.select_place_activity_layout);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void initView() {
		addressList = (ListView) findViewById(R.id.addressList);
		placeTipTv = (TextView) findViewById(R.id.placeTip);
		placeTv = (TextView) findViewById(R.id.place);

		location = (RelativeLayout) findViewById(R.id.location);
		location.setOnClickListener(this);
		
		adapter = new SelectPlaceListAdapter(listData, this);
		
		addressList.setAdapter(adapter);

		addressList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Contains.curSelectXiaoQuName = listData.get(arg2).getXiangmuLoupan();
				Contains.curSelectXiaoQuId = listData.get(arg2).getXiangmuId();
				finish();
			}
		});
		
	}

	@Override
	protected void initDataFromLocal() {
		if(StringUitl.isNoEmpty(Contains.AoiName)){
			placeTipTv.setText("当前定位的小区");
			placeTv.setText(Contains.AoiName);
		}else{
			if(StringUitl.isNoEmpty(Contains.locationCity)){
				placeTipTv.setText("当前定位的小区");
				placeTv.setText(Contains.locationCity);
			}else{
				placeTipTv.setText("未定位到城市");
				placeTv.setText("正在定位");
			}
		}
		initDataFromNet();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.location:
				Contains.curSelectXiaoQuName = placeTv.getText().toString();
				finish();
				break;
		default:
			break;
		}
	}
	
	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if (repairController == null) {
			repairController = new ReparirControllerImpl();
		}
		repairController.getProject(mRequestQueue, listener);
	}
	
	private ResultListener<RepairList> listener = new ResultListener<RepairList>() {

		@Override
		public void onResponse(RepairList info) {
			progressDialog.hide();
			// 获取请求码
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
			}
			if (isEmptyList(info.getRows())) {
				onError("获取失败");
			} else {
				listData = info.getRows();
				adapter.setListDatas(listData);
			}
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError("请求失败");
		}
	};
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
