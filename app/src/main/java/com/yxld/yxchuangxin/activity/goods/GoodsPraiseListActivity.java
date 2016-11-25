package com.yxld.yxchuangxin.activity.goods;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.ForumDestailDynamicItemAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.PraiseController;
import com.yxld.yxchuangxin.controller.impl.PraiseControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallComment;
import com.yxld.yxchuangxin.listener.ResultListener;

/***
 * @ClassName: GoodsPraiseListActivity 
 * @Description: 商品评价列表界面 
 * @author wwx
 * @date 2016年3月27日 下午3:35:27 
 */
public class GoodsPraiseListActivity extends BaseActivity implements ResultListener<CxwyMallComment> {
	
	/** 全部 */
	private TextView tvGoods = null;
	/** 待付款 */
	private TextView tvComment = null;
	/** 待发货 */
	private TextView tvbad = null;
	/** 装载选项卡中文字（订单状态） */
	private List<TextView> listTextView = new ArrayList<TextView>();
	
	/** 评价接口实现类*/
	private PraiseController praiseController;
	private String goodId;
	
	private int level = 5;
	
	/** 总条数 */
	private int totals;
	
	private List<CxwyMallComment> listData = new ArrayList<CxwyMallComment>();
	
	private ForumDestailDynamicItemAdapter adapter;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.goods_praise_list_activity);
		getSupportActionBar().setTitle("评价列表");
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
		tvGoods = (TextView) findViewById(R.id.tvGoods);
		tvComment = (TextView) findViewById(R.id.tvComment);
		tvbad = (TextView) findViewById(R.id.tvbad);
		
		listTextView.add(tvGoods);
		listTextView.add(tvComment);
		listTextView.add(tvbad);

		tvGoods.setOnClickListener(this);
		tvComment.setOnClickListener(this);
		tvbad.setOnClickListener(this);
		
		if (adapter == null) {
			adapter = new ForumDestailDynamicItemAdapter(this,listData);
			xlistView.setAdapter(adapter);
		}
	}

	@Override
	protected void initDataFromLocal() {
		goodId = getIntent().getStringExtra("goodsId");
		if(goodId == null){
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
		case R.id.tvGoods:
			// 修改筛选条件为销量
			level = 5;
			changeTvBg(0);
			break;
		case R.id.tvComment:
			// 修改筛选条件为价格
			level = 3;
			changeTvBg(1);
			break;
		case R.id.tvbad:
			// 修改筛选条件为人气
			level = 1;
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
		if(praiseController == null){
			praiseController = new PraiseControllerImpl(); 
		}
		praiseController.getPraiseListFromGoodsID(mRequestQueue, new Object[]{5,1,goodId,level,Contains.user.getYezhuId()}, this);
	}

	@Override
	public void onResponse(CxwyMallComment info) {
		onMyResponse(info);
		totals = info.gettotal();
		listData = info.getCommentList();
		showView();
	}

	@Override
	public void onErrorResponse(String errMsg) {
		onError(errMsg);
	}

	/**
	 * 加载数据更新界面
	 */
	protected void showView() {
		// 第一次加载
		if (pageCode == 1) {
			if (isEmptyList(listData)) {
				showEmptyDataPage(true);
				return;
			}

			if (totals <= adapter.getListData().size()) {// 加载更多禁止
				xlistView.setPullLoadEnable(false);
			}
			adapter.setListData(listData);
		} else {// 不是第一次加载
			if ((isEmptyList(listData)) || totals <= adapter.getListData().size()) {// 加载更多禁止
				xlistView.setPullLoadEnable(false);
			}
			adapter.addData(listData);
		}
	}

}
