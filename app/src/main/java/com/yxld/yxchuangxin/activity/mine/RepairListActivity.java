package com.yxld.yxchuangxin.activity.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.RepairListItemAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.RepairController;
import com.yxld.yxchuangxin.controller.impl.ReparirControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyBaoxiu;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RepairListActivity
 * @Description: 报修列表
 * @author wwx
 * @date 2016年4月15日 上午11:18:34
 *
 */
public class RepairListActivity extends BaseActivity implements ResultListener<CxwyBaoxiu>{

	/** 全部 */
	private TextView tvAllRepair = null;
	/** 已处理 */
	private TextView tvYclRepair = null;
	/** 未处理 */
	private TextView tvWclRepair = null;
	/** 装载选项卡中文字（处理状态） */
	private List<TextView> listTextView = new ArrayList<TextView>();

	/** 评价接口实现类*/
	private RepairController repairController;

	/** 记录查询的状态 1."" ，2，回执 3，填单*/
	private String repairId = "";

	/** 总条数 */
	private int totals;

	private List<CxwyBaoxiu> listData = new ArrayList<CxwyBaoxiu>();

	private RepairListItemAdapter adapter;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.goods_praise_list_activity);
		getSupportActionBar().setTitle("我的报修列表");
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void initView() {
		setTitle("报修列表");
		tvAllRepair = (TextView) findViewById(R.id.tvGoods);
		tvYclRepair = (TextView) findViewById(R.id.tvComment);
		tvWclRepair = (TextView) findViewById(R.id.tvbad);

		tvAllRepair.setText("全部报修");
		tvYclRepair.setText("处理完");
		tvWclRepair.setText("处理中");

		listTextView.add(tvAllRepair);
		listTextView.add(tvYclRepair);
		listTextView.add(tvWclRepair);

		tvAllRepair.setOnClickListener(this);
		tvYclRepair.setOnClickListener(this);
		tvWclRepair.setOnClickListener(this);

		if (adapter == null) {
			adapter = new RepairListItemAdapter(this,listData);
			xlistView.setAdapter(adapter);
		}

		initDataFromNet();
	}

	@Override
	protected void initDataFromLocal() {
		repairId = getIntent().getStringExtra("repairId");
		if(repairId == null){
			findViewById(R.id.ly_empty_data).setVisibility(View.VISIBLE);
			return;
		}else{
			findViewById(R.id.ly_empty_data).setVisibility(View.GONE);
		}
		changeTvBg(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.returnWrap:
				finish();
				break;
			case R.id.tvGoods:
				// 修改筛选条件为全部报修
				repairId = "";
				changeTvBg(0);
				break;
			case R.id.tvComment:
				repairId = "2";
				// 修改筛选条件为已处理
				changeTvBg(1);
				break;
			case R.id.tvbad:
				repairId = "1";
				// 修改筛选条件为待指派 （未完成）
				changeTvBg(2);
				break;
			default:
				break;
		}
	}

	/**
	 * @Title: changeTvBg
	 * @Description: 改变textView背景
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	private void changeTvBg(int position) {
		for (int i = 0; i < listTextView.size(); i++) {
			if (i == position) {
				listTextView.get(i).setBackgroundResource(
						R.drawable.order_border);
				listTextView.get(i).setTextColor(
						getResources().getColor(R.color.color_main_color));
			} else {
				listTextView.get(i).setBackgroundResource(0);
				listTextView.get(i).setTextColor(
						getResources().getColor(R.color.black));
			}
		}

		pageCode = 0;
		initDataFromNet();
	}

	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if(repairController == null){
			repairController = new ReparirControllerImpl();
		}
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("loupan", Contains.appYezhuFangwus.get(0).getXiangmuLoupan());
		parm.put("loudong", Contains.appYezhuFangwus.get(0).getFwLoudong());
		parm.put("danyuan", Contains.appYezhuFangwus.get(0).getFwDanyuan());
		parm.put("fanghao", Contains.appYezhuFangwus.get(0).getFwFanghao()+"");
		if(repairId == null){
			repairId = "";
		}
		parm.put("status", repairId);

		Log.d("geek", "parm"+parm.toString());
		if(repairId == null || repairId.equals("")){
			repairController.getRepairAllList(mRequestQueue, parm, this);
		}else{
			repairController.getRepairOtherList(mRequestQueue, parm, this);
		}
	}

	@Override
	public void onResponse(CxwyBaoxiu info) {
		onMyResponse(info);
		totals = info.getTotal();
		listData = info.getRows();
		showView();
	}

	@Override
	public void onErrorResponse(String errMsg) {
		Logger.d(errMsg);
		onError("");
	}

	/**
	 * 加载数据更新界面
	 */
	protected void showView() {
		Log.d("geek", "showView: pageCode="+pageCode+",listData="+listData.size());
		// 第一次加载
		if (pageCode == 1) {
			if (isEmptyList(listData)) {
				showEmptyDataPage(true);
				return;
			}
			adapter.setListOrderDatas(listData);

			if (totals <= adapter.getListOrderDatas().size()) {// 加载更多禁止
				xlistView.setPullLoadEnable(false);
			}
		} else {// 不是第一次加载
			if ((isEmptyList(listData)) || totals <= adapter.getListOrderDatas().size()) {// 加载更多禁止
				xlistView.setPullLoadEnable(false);
			}
			adapter.addData(listData);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(listTextView != null){
			listTextView = null;
		}

		if(listData != null){
			listData = null;
		}
	}
}
