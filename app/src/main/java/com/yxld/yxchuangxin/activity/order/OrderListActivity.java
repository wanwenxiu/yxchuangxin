package com.yxld.yxchuangxin.activity.order;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.OrderListItemAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.OrderController;
import com.yxld.yxchuangxin.controller.impl.OrderControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallOrder;
import com.yxld.yxchuangxin.entity.CxwyMallSale;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: OrderListActivity 
 * @Description: 订单列表
 * @author wwx
 * @date 2016年3月8日 下午4:38:46 
 */
public class OrderListActivity extends BaseActivity {
	private final int UPDATE_ORDER_STATE = 0;
	/** 订单数据 */
	private List<CxwyMallOrder> listOrderData = new ArrayList<CxwyMallOrder>();
	/** 商品信息数据 */
	private List<CxwyMallSale> listSaleData = new ArrayList<CxwyMallSale>();
	
	private OrderListItemAdapter adapter ;
	/** 全部 */
	private TextView tvAll = null;
	/** 待付款 */
	private TextView tvytj = null;
	/** 待发货 */
	private TextView tvdfh = null;
	/** 待收货 */
	private TextView tvdsh = null;
	/** 待评价 */
	private TextView tvdpj = null;
	/** 待退货 */
	private TextView tvdth = null;
	/** 装载选项卡中文字（订单状态） */
	private List<TextView> listTextView = new ArrayList<TextView>();
	
	/** 订单接口类*/
	private OrderController orderController;
	
	/** 总条数 */
	private int totals;
	
	private String state ="";

	private  int orderId;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(listOrderData != null){
			listOrderData = null;
		}
		if(listSaleData != null){
			listSaleData = null;
		}
		if(listTextView !=null){
			listTextView = null;
		}
		if(handler != null){
			handler.removeCallbacksAndMessages(null);
		}
	}

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.order_list_activity_layout);
		getSupportActionBar().setTitle("订单列表");
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
		changeTvBg(tag);
	}

	@Override
	protected void initView() {
		tvAll = (TextView) findViewById(R.id.tvAll);
		tvytj = (TextView) findViewById(R.id.tvytj);
		tvdfh = (TextView) findViewById(R.id.tvdfh);
		tvdsh = (TextView) findViewById(R.id.tvdsh);
		tvdpj = (TextView) findViewById(R.id.tvdpj);
		tvdth = (TextView) findViewById(R.id.tvth);
		listTextView.add(tvAll);
		listTextView.add(tvytj);
		listTextView.add(tvdfh);
		listTextView.add(tvdsh);
		listTextView.add(tvdpj);
		listTextView.add(tvdth);

		tvAll.setOnClickListener(this);
		tvytj.setOnClickListener(this);
		tvdfh.setOnClickListener(this);
		tvdsh.setOnClickListener(this);
		tvdpj.setOnClickListener(this);
		tvdth.setOnClickListener(this);
		
		if (adapter == null) {
			adapter = new OrderListItemAdapter(handler,this,listOrderData,listSaleData);
			xlistView.setAdapter(adapter);
		}
	}
	
	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if(orderController == null){
			orderController = new OrderControllerImpl();
		}
		
		/** 用于请求参数 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("rows", "5");
		map.put("page", pageCode + 1 + "");
		map.put("ord.dingdanUserId", Contains.user.getYezhuId()+"");
		map.put("ord.dingdanZhuangtai", state);
		Log.d("geek", "查询map"+map.toString());
		orderController.getOrderListOrder(mRequestQueue, map, listenerOrder);
	}

	@Override
	protected void initDataFromLocal() {
		tag = getIntent().getIntExtra("ORDERTYPE", 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvAll:
			changeTvBg(0);
			break;
		case R.id.tvytj:
			changeTvBg(1);
			break;
		case R.id.tvdfh:
			changeTvBg(2);
			break;
		case R.id.tvdsh:
			changeTvBg(3);
			break;
		case R.id.tvdpj:
			changeTvBg(4);
			break;
		case R.id.tvth:
			changeTvBg(5);
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
		//改变样式
		for(int i = 0; i < listTextView.size(); i++) {
			if (i == position) {
				listTextView.get(i).setBackgroundResource(
						R.drawable.order_border);
				listTextView.get(i).setTextColor(getResources().getColor(R.color.color_main_color));
			} else {
				listTextView.get(i).setBackgroundResource(0);
				listTextView.get(i).setTextColor(getResources().getColor(R.color.black));
			}
		}
		//首先清空list
		listOrderData.clear();
		adapter.setListOrderDatas(listOrderData);
		
		//根据position 传不同参数
		switch (position) {
		case 0:
			state = "";
			break;
		case 1:
			state = "待付款";
			break;
		case 2:
			state = "待发货";
			break;
		case 3:
			state = "待收货";
			break;
		case 4:
			state = "待评价";
			break;
		case 5:
			//模糊查询退货、退货中、退款
			state = "退";
			break;
		default:
			break;
		}
		//请求数据
		pageCode = 0;
		initDataFromNet();
	}

	private  ResultListener<CxwyMallOrder> listenerOrder = new ResultListener<CxwyMallOrder>() {

		@Override
		public void onResponse(CxwyMallOrder info) {
			onMyResponse(info);
			totals = info.getTotal();
			listOrderData = info.getOrderList();
			listSaleData = info.getSaleList();
			showView();
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError(errMsg);
		}
	};
	
	/**
	 * 加载数据更新界面
	 */
	protected void showView() {
		// 第一次加载
		if (pageCode == 1) {
			if (isEmptyList(listOrderData) && isEmptyList(listSaleData)) {
				showEmptyDataPage(true);
				return;
			}

			if (totals <= adapter.getListOrderDatas().size()) {// 加载更多禁止
				xlistView.setPullLoadEnable(false);
			}
			adapter.setListSaleData(listSaleData);
			adapter.setListOrderDatas(listOrderData);
		} else {// 不是第一次加载
			if ((isEmptyList(listOrderData)) || totals <= adapter.getListOrderDatas().size()) {// 加载更多禁止
				xlistView.setPullLoadEnable(false);
			}
			adapter.addSaleData(listSaleData);
			adapter.addData(listOrderData);
		}
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATE_ORDER_STATE:
				int id = msg.arg1;
				String state = (String) msg.obj;
				
				/** 用于请求参数 */
				Map<String, String> map = new HashMap<String, String>();
				map.put("ord.dingdanId", id+"");
				map.put("ord.dingdanZhuangtai", state);

				//点击为取消订单时，弹出取消订单原因。
				if(state.equals("取消订单")){
					cancelOrderDialog(map);
					return;
				}else if(state.equals("退款中")){
					TuikuanOrderDialog(map);
					return;
				}else if(state.equals("待发货")){
					orderId = id;
					//首先判断是否库存足够，
					orderController.getOrderKuncunFromID(mRequestQueue,new Object[]{id},listenerForOrderKuncun);
				}else if(state.equals("用户删除订单")){
					deleteOrderDialog(map);
				}else{
					Log.d("geek", "修改不是取消订单map"+map.toString());
					orderController.updateOrderState(mRequestQueue, map, listenerUpdateOrder);
				}
				break;
			default:
				break;
			}
		};
	};

	private  ResultListener<BaseEntity> listenerForOrderKuncun = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			Log.d("geek","是否可以付款 info="+info.toString());
			// 获取请求码
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				//首先清空list
				listOrderData.clear();
				adapter.setListOrderDatas(listOrderData);

				//请求数据
				pageCode = 0;
				initDataFromNet();
				return;
			}else{
				   String money = "";
					String shop = "";
					String details = "";
				    String dingdanbianhao = "";
					if(listSaleData != null && listSaleData.size()>0){
						for (int i = 0; i < listSaleData.size(); i++) {
							CxwyMallSale mallSale = listSaleData.get(i);
							if(mallSale.getSaleDingdanId() == orderId){
								shop += mallSale.getSaleShangpName();
								details += mallSale.getSaleGuige();
							}
						}

						for (int i= 0; i<listOrderData.size();i++){
							if(orderId == listOrderData.get(i).getDingdanId()){
								dingdanbianhao = listOrderData.get(i).getDingdanBianhao();
								money = listOrderData.get(i).getDingdanTotalRmb()+"";
								Log.d("...",dingdanbianhao);
							}
						}

						Intent intent = new Intent(OrderListActivity.this, PayWaySelectActivity.class);
						intent.putExtra("orderId", orderId+"");
						intent.putExtra("orderMoney",money);
						intent.putExtra("orderShop",shop );
						intent.putExtra("orderDetails",details );
						intent.putExtra("orderBianhao",dingdanbianhao);
						intent.putExtra("paystatus","商城支付");
						Contains.isAgenWeixinPay = 2;
						startActivity(intent);
						finish();
					}else{
						ToastUtil.show(OrderListActivity.this,"订单信息有误");
					}

			}


		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError(errMsg);
		}
	};
	
	private  ResultListener<BaseEntity> listenerUpdateOrder = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			Logger.d(info.toString());
			// 获取请求码
			if (info.status != STATUS_CODE_OK ) {
				if(info.MSG != null && !"".equals(info.MSG) && !info.MSG.equals("获取订单失败,请刷新重试")){
					onError(info.MSG);
				}
				//首先清空list
				listOrderData.clear();
				adapter.setListOrderDatas(listOrderData);

				//请求数据
				pageCode = 0;
				initDataFromNet();
				return;
			}

			//首先清空list
			listOrderData.clear();
			adapter.setListOrderDatas(listOrderData);
			
			//请求数据
			pageCode = 0;
			initDataFromNet();
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError(errMsg);
		}
	};
	private int tag;
	
	
	/**
	 * @Title: cancelOrderDialog 
	 * @Description: 取消订单对话框   
	 * @return void
	 * @throws
	 */
	private void cancelOrderDialog(final Map<String, String> map){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择取消原因");
        //    指定下拉列表的显示数据
         final String[] cities = {"信息拍写错误", "不想要了", "其它"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener()
        {
			@Override
            public void onClick(DialogInterface dialog, int which)
            {
				map.put("ord.dingdanBeiyong4", cities[which]);
				Log.d("geek", " 取消订单 原因map"+map.toString());
				orderController.updateOrderState(mRequestQueue, map, listenerUpdateOrder);
				
            }
        });
        builder.show();
	}

	/**
	 * @Title: deleteOrderDialog
	 * @Description: 用户删除订单对话框
	 * @return void
	 * @throws
	 */
	private void deleteOrderDialog(final Map<String, String> map){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示:订单删除后将无法恢复");
		//    指定下拉列表的显示数据
		final String[] cities = {"确定","取消"};
		//    设置一个下拉的列表选择项
		builder.setItems(cities, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if(which == 0){
					map.put("ord.dingdanBeiyong2", "1");
					Log.d("geek", " 前台用户删除"+map.toString());
					orderController.updateOrderState(mRequestQueue, map, listenerUpdateOrder);
				}
			}
		});
		builder.show();
	}

	/**
	 * 订单退款对话框
	 * @param map
     */
	private void TuikuanOrderDialog(final Map<String, String> map){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("选择退款原因");
		//    指定下拉列表的显示数据
		final String[] cities = {"信息拍写错误", "不想要了", "其它"};
		//    设置一个下拉的列表选择项
		builder.setItems(cities, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				map.put("ord.dingdanBeiyong4", cities[which]);
				Log.d("geek", " 退款 原因map"+map.toString());
				orderController.TuiKuanOrderState(mRequestQueue, map, listenerUpdateOrder);
			}
		});
		builder.show();
	}

}
