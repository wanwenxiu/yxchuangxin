package com.yxld.yxchuangxin.activity.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.JiaofeiAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.WuyeRecordAndroid;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: JiaoFeiListActivity
 * @Description: 缴费列表
 * @author wwx
 * @date 2016年4月6日 下午2:36:37
 */
public class JiaoFeiListActivity extends BaseActivity implements ResultListener<WuyeRecordAndroid> {
	
	/** 列表*/
	private ListView jiaofeiList;
	/** 列表集合*/
	private List<WuyeRecordAndroid> mlist = new ArrayList<WuyeRecordAndroid>();
	/** 列表适配器*/
	private JiaofeiAdapter adapter;
	
	/** 类型*/
	private String type;
	/** 接口*/
	private String url;
	
	private YeZhuController yezhuController;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_jiaofei_list);
	}

	@Override
	protected void initView() {
		setTitle("缴费记录");
		jiaofeiList = (ListView) findViewById(R.id.jiaofeiList);

		adapter = new JiaofeiAdapter(mlist, this);
		jiaofeiList.setAdapter(adapter);
	}
	
	@Override
	protected void initDataFromLocal() {
		type = getIntent().getStringExtra("curType");
		if(!StringUitl.isNoEmpty(type)){
			type = "1";
		}
		if(type.equals("2")){
			setTitle("欠费列表");
			url = API.URL_ALL_QIANFEI_RECORDS;
		}else{
			setTitle("缴费列表");
			url = API.URL_ALL_PAYMENT_RECORDS;
		}
		initDataFromNet();
	}
	
	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if(yezhuController == null){
			yezhuController = new YeZhuControllerImpl();
		}
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("i", type);
		parm.put("loupan", Contains.appYezhuFangwus.get(0).getXiangmuLoupan());
		parm.put("loudong", Contains.appYezhuFangwus.get(0).getFwLoudong());
		parm.put("danyuan", Contains.appYezhuFangwus.get(0).getFwDanyuan());
		parm.put("fanghao", Contains.appYezhuFangwus.get(0).getFwFanghao()+ "");
		Log.d("geek", "parm "+parm.toString());
		yezhuController.getAllPaymentRecords(mRequestQueue, url, parm, this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.returnWrap:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onResponse(WuyeRecordAndroid info) {
		Log.d("adsa", info.toString());
		if (info.status != STATUS_CODE_OK) {
			onError(info.MSG);
			return;
		}
		if (isEmptyList(info.getRows())) {
			findViewById(R.id.noData).setVisibility(View.VISIBLE);
			ToastUtil.show(JiaoFeiListActivity.this, "没有查询到记录");
		} else {
			mlist = info.getRows();
			adapter.setListDatas(mlist);
			findViewById(R.id.noData).setVisibility(View.GONE);
		}
		progressDialog.hide();
	}

	@Override
	public void onErrorResponse(String errMsg) {
		onError("请求失败");
		findViewById(R.id.noData).setVisibility(View.GONE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mlist != null){
			mlist = null;
		}

		if(yezhuController != null){
			yezhuController = null;
		}
	}
}
