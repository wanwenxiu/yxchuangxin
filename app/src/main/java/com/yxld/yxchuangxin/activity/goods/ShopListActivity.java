package com.yxld.yxchuangxin.activity.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.shopListAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.GoodsController;
import com.yxld.yxchuangxin.controller.impl.GoodsControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallProduct;
import com.yxld.yxchuangxin.entity.ShopList;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: ShopListActivity
 * @Description: 商品列表界面
 * @author wwx
 * @date 2016年3月17日 上午9:48:10
 */
public class ShopListActivity extends BaseActivity implements
		View.OnClickListener, ResultListener<ShopList> {

	private int page = 5;

	/** 进入购物车按钮 */
	private RelativeLayout goodsDetals_cart;
	// 显示购物车数量的红色 小圈,当前商品数量
	private TextView goodsDetalsNum, now_goodsNum;
	// 返回顶部的按钮
	private ImageView return_top;
	/** 返回键 */
	private ImageView imgBack;
	/** 搜索*/
	private TextView searchTv;
	/** 销量 */
	private TextView tvNum = null;
	/** 价格 */
	private TextView tvPrice = null;
	/** 人气 */
	private TextView tvPopularity = null;
	/** 装载选项卡中文字（订单状态） */
	private List<TextView> listTextView = new ArrayList<TextView>();

	/** 通过ID 进入商品列表页面 */
	private final int byId = 0;
	/** 通过搜索关键字 进入商品列表页面 */
	private final int byKey = 1;
	/** 通过首页推荐进入商品列表 */
	private final int byIndex = 2;

	private int type = byId;
	private String searchFlg = "";
	private String calssId = "";

	private shopListAdapter adapter;
	private List<CxwyMallProduct> listData = new ArrayList<CxwyMallProduct>();
	private GoodsController goodsController;
	/** 总条数 */
	private int totals;

	/** 筛选条件 销量: shangpinXiaoliang 价格:shangpinRmb 人气:shangpinRenqi */
	private String goodsSort = "shangpinXiaoliang";
	/** 筛选条件 升序asc 倒序desc */
	private boolean goodsOrder = true;

	//DESC 从大到小排序 ACS 从小到大排序
	private String goodsOrderSelect = "asc";

	/** 记录上一次选中筛选条件标识 */
	private int lastPosition = 0;
	/** 跳转至商品详情*/
	private int JUMP_GOODSTAIL = 0;
	/** 从商品详情中点击购物车返回*/
	public static int JUMP_CART = 1;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_shop_list_layout);
		getSupportActionBar().setTitle("商品列表");
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
		goodsDetals_cart = (RelativeLayout) findViewById(R.id.goodsDetals_cart);
		goodsDetals_cart.setOnClickListener(this);
		goodsDetalsNum = (TextView) findViewById(R.id.goodsDetalsNum);
		now_goodsNum = (TextView) findViewById(R.id.now_goodsNum);
		return_top = (ImageView) findViewById(R.id.return_top);
		return_top.setOnClickListener(this);
		imgBack = (ImageView) findViewById(R.id.imgBack);
		imgBack.setOnClickListener(this);
		searchTv = (TextView) findViewById(R.id.searchTv);
		searchTv.setOnClickListener(this);
		tvNum = (TextView) findViewById(R.id.tvNum);
		tvPrice = (TextView) findViewById(R.id.tvPrice);
		tvPopularity = (TextView) findViewById(R.id.tvPopularity);

		listTextView.add(tvNum);
		listTextView.add(tvPrice);
		listTextView.add(tvPopularity);

		tvNum.setOnClickListener(this);
		tvPrice.setOnClickListener(this);
		tvPopularity.setOnClickListener(this);

		if (adapter == null) {
			adapter = new shopListAdapter(mRequestQueue,listData, this, this.getWindow(),
					goodsDetals_cart, goodsDetalsNum);
			xlistView.setAdapter(adapter);
		}
		
		xlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if((arg2-1)>=0){
					Bundle bundle = new Bundle();
					bundle.putSerializable("goods", adapter.getMlist().get(arg2-1));
					Intent intent = new Intent(ShopListActivity.this, GoodsDestailActivity.class);
					intent.putExtras(bundle);
					startActivityForResult(intent, JUMP_GOODSTAIL);
				}
			}
			
		});

		// 如果购物车不为空 购物车的数量不少于0的话 就显示装载购物车数量的容器
		if (Contains.CartList != null && Contains.CartList.size() != 0) {
			goodsDetalsNum.setVisibility(0);
			goodsDetalsNum.setText(Contains.CartList.size() + "");
		} else {
			// 否则就隐藏
			goodsDetalsNum.setVisibility(8);
		}

		xlistView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (xlistView.getLastVisiblePosition() >= 10) {
					((ImageView) findViewById(R.id.return_top))
							.setVisibility(0);
				} else {
					((ImageView) findViewById(R.id.return_top))
							.setVisibility(8);
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == JUMP_GOODSTAIL){ //从跳转至商品详情中返回
			if(resultCode == JUMP_CART){  //代表跳转至购物车
				Log.d("geek","进入跳转至购物车界面");
				Intent intentCart = new Intent(getResources().getString(
						R.string.index_broad));
				intentCart.putExtra("IndexJumpTag", 1);
				sendBroadcast(intentCart);
				finish();
			}
		}
	}

	/**
	 * 重新唤醒窗体的时候改变商品数量的数值
	 */
	protected void onRestart() {
		super.onResume();
		if (Contains.CartList != null && Contains.CartList.size() >= 0
				&& goodsDetalsNum != null) {
			goodsDetalsNum.setVisibility(0);
			goodsDetalsNum.setText(Contains.CartList.size() + "");
		}
	}

	@Override
	protected void initDataFromLocal() {
		// 获取类型
		type = getIntent().getIntExtra("type", byKey);
		// 获取分类id
		calssId = getIntent().getStringExtra("calssId");
		if (calssId == null) {
			calssId = "0";
		}
		// 获取搜索关键字
		searchFlg = getIntent().getStringExtra("searchFlg");
		if (searchFlg == null) {
			searchFlg = "";
		}else{
			searchTv.setText(searchFlg);
		}

		Log.d("geek","type="+type+"calssId="+calssId+"searchFlg="+searchFlg);
		initDataFromNet();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgBack:
			finish();
			break;
		case R.id.goodsDetals_cart:
			// 进入购物车
			Intent intentCart = new Intent(getResources().getString(
					R.string.index_broad));
			intentCart.putExtra("IndexJumpTag", 1);
			sendBroadcast(intentCart);
			finish();
			break;
		case R.id.searchTv:
			startActivity(SearchActivity.class);
			finish();
			break;
		case R.id.return_top:
			// // 返回顶部的按钮的点击事件
			xlistView.smoothScrollToPosition(0);
			break;
		case R.id.tvNum:
			// 修改筛选条件为销量
			goodsSort = "shangpinXiaoliang";
			pageCode = 0;
			changeTvBg(0);
			break;
		case R.id.tvPrice:
			// 修改筛选条件为价格
			goodsSort = "shangpinRmb";
			pageCode = 0;
			changeTvBg(1);
			break;
		case R.id.tvPopularity:
			// 修改筛选条件为人气
			goodsSort = "shangpinRenqi";
			pageCode = 0;
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

		// 如果上一次选中的跟这次选中的一致，则是降序
		if (lastPosition == position) {
			goodsOrder = !goodsOrder;
		} else {
			// 如果上一次选中的跟这次选中的不一致，则是升序
			goodsOrder = true;
		}

		if (goodsOrder) {
			goodsOrderSelect = "asc";
		} else {
			goodsOrderSelect = "desc";
		}

		// 加载网络数据
		initDataFromNet();

		//记录上一次选中筛选条件
		lastPosition = position;
	}

	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if (goodsController == null) {
			goodsController = new GoodsControllerImpl();
		}
		//根据商品Id查询
		if (type == byId) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("rows", page+"");
			params.put("page", pageCode + 1 + "");
			params.put("classify.classifyId", calssId);
			params.put("appxiaoqu",Contains.curSelectXiaoQuId+"");
			params.put("sort", goodsSort);
			params.put("order", goodsOrderSelect);
			Log.d("geek", "根据商品id查询 ="+params.toString());
			goodsController.getShopListById(mRequestQueue,params, this);
		}else if(type == byIndex){  //根据首页推荐进入app
			Map<String, String> params = new HashMap<String, String>();
			params.put("rows", page+"");
			params.put("page", pageCode + 1 + "");
			params.put("product.shangpinClassicShow", "0");
			params.put("appxiaoqu",Contains.curSelectXiaoQuId+"");
			params.put("sort", goodsSort);
			params.put("order", goodsOrderSelect);
			Log.d("geek", "根据首页推荐查询 ="+params.toString());
			goodsController.getIndexGoodsList(mRequestQueue, params, this);
		}
		else {
			//根据关键字查询
			Map<String, String> params = new HashMap<String, String>();
			params.put("rows", page+"");
			params.put("page", pageCode + 1 + "");
			params.put("keys", searchFlg);
			params.put("appxiaoqu",Contains.curSelectXiaoQuId+"");
			params.put("sort", goodsSort);
			params.put("order", goodsOrderSelect);
			Log.d("geek", "根据关键字查询 ="+params.toString());
			goodsController.getShopListByKey(mRequestQueue, params, this);
		}
	}

	/**
	 * 加载数据更新界面
	 */
	protected void showView() {
		// 第一次加载
		if (pageCode == 1) {
			if (isEmptyList(listData)) {
				showEmptyDataPage(true);
				now_goodsNum.setVisibility(View.GONE);
				return;
			}

			if (totals <= adapter.getMlist().size()) {// 加载更多禁止
				xlistView.setPullLoadEnable(false);
			}
			adapter.refreshData(listData);
		} else {// 不是第一次加载
			if ((isEmptyList(listData)) || totals <= adapter.getMlist().size()) {// 加载更多禁止
				xlistView.setPullLoadEnable(false);
			}
			adapter.addData(listData);
		}
		// 显示当前加载列数和总数
		now_goodsNum.setText(adapter.getMlist().size() + "/" + totals);
		now_goodsNum.setVisibility(View.VISIBLE);
	}

	@Override
	public void onResponse(ShopList info) {
		onMyResponse(info);
		totals = info.getTotal();
		listData = info.getProductList();
		showView();
	}

	@Override
	public void onErrorResponse(String errMsg) {
		onError(errMsg);
		now_goodsNum.setVisibility(View.GONE);
	}

}