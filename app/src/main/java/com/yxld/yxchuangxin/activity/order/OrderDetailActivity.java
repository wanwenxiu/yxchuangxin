package com.yxld.yxchuangxin.activity.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.BaseMapListApater;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.OrderController;
import com.yxld.yxchuangxin.controller.impl.OrderControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallOrder;
import com.yxld.yxchuangxin.entity.CxwyMallSale;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;

/**
 * @ClassName: OrderDetailActivity 
 * @Description: 订单详情
 * @author wwx
 * @date 2016年3月27日 下午4:31:10 
 */
public class OrderDetailActivity extends BaseActivity implements ResultListener<CxwyMallSale> {

	// 订单号的文本框,订单的状态,订单收货人姓名,订单收货人手机号码,收货人的详细地址,下单的事件,订单的总价
	// 买家店铺的文本,买家附言,交易方式,店家联系方式
	private TextView orderDetailsOrderNum, orderDetailsOrderCate,
			sureOrderUserName, sureOrderUserTell, sureOrderAdressInfo,
			orderTimeText, cartPriceConnt, robOrder_buyerText,
			orderTradingStyle,order_totalnum,orderpaytype,ordercancelinfo,orderPeisongPraise,orderyouhuiqjia,orderPeisongPhone;

	private ListView orderGoodList = null;
	
	private OrderController orderController;
	
	/** 最外层布局*/
	private RelativeLayout main;
	/** 获取订单ID*/
	private String orderId = "";

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_order_details);
		getSupportActionBar().setTitle("订单详情");
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
	protected void initView() {
		setTitle("订单详情");
		main = (RelativeLayout) findViewById(R.id.main);
		// 加载控件
		orderDetailsOrderNum = (TextView) findViewById(R.id.orderDetailsOrderNum);
		orderDetailsOrderCate = (TextView) findViewById(R.id.orderDetailsOrderCate);
		sureOrderUserName = (TextView) findViewById(R.id.sureOrderUserName);
		sureOrderUserTell = (TextView) findViewById(R.id.sureOrderUserTell);
		sureOrderAdressInfo = (TextView) findViewById(R.id.sureOrderAdressInfo);
		orderTimeText = (TextView) findViewById(R.id.orderTimeText);
		cartPriceConnt = (TextView) findViewById(R.id.cartPriceConnt);
		robOrder_buyerText = (TextView) findViewById(R.id.robOrder_buyerText);
		orderTradingStyle = (TextView) findViewById(R.id.orderTradingStyle);
		orderPeisongPhone = (TextView) findViewById(R.id.orderPeisongPhone);
		orderPeisongPraise = (TextView) findViewById(R.id.orderPeisongPraise);
		orderyouhuiqjia = (TextView) findViewById(R.id.orderyouhuiqjia);
		order_totalnum = (TextView)findViewById(R.id.order_totalnum);
		orderpaytype = (TextView) findViewById(R.id.orderpaytype);
		ordercancelinfo = (TextView)findViewById(R.id.ordercancelinfo);

		orderGoodList = (ListView) findViewById(R.id.orderGoodList);

		orderPeisongPhone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(StringUitl.isNoEmpty(orderPeisongPhone.getText().toString())){
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_CALL);
					//url:统一资源定位符
					//uri:统一资源标示符（更广）
					intent.setData(Uri.parse("tel:" + orderPeisongPhone.getText().toString()));
					//开启系统拨号器
					startActivity(intent);
				}
			}
		});
	}

	@Override
	protected void initDataFromLocal() {
		orderId = getIntent().getStringExtra("orderId");
		if(orderId != null && !"".equals(orderId)){
			main.setVisibility(View.VISIBLE);
			findViewById(R.id.ly_empty_data).setVisibility(View.GONE);
			initDataFromNet();
		}else {
			main.setVisibility(View.GONE);
			findViewById(R.id.ly_empty_data).setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if(orderController == null){
			orderController = new OrderControllerImpl();
		}
		orderController.getOrderDestailFromID(mRequestQueue, new Object[]{orderId}, this);
	}

	@Override
	public void onResponse(CxwyMallSale info) {
		// 获取请求码
		if (info.status != STATUS_CODE_OK) {
			onError(info.MSG);
			return;
		}
		initData(info.getSaleList(),info.getOrder(),info.getPhone());
		progressDialog.hide();
	}

	@Override
	public void onErrorResponse(String errMsg) {
		onError(errMsg);
	}
	
	private void initData(List<CxwyMallSale> info,CxwyMallOrder order,String phone) {
		if (info != null) {
			// 创建一个集合来装图片和文字
			ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
			// 装在图片和文字
			for (int x = 0; x < info.size(); x++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				
				map.put("orderDestail", info.get(x).getSaleShangpName());
				map.put("orderClass", "规格:" + info.get(x).getSaleGuige());
				map.put("orderPrice", "¥" + info.get(x).getSaleOneRmb());
				map.put("orderNum", "x" + info.get(x).getSaleNum());

				if(info.get(x).getSaleImgSrc()!= null && !"".equals(info.get(x).getSaleImgSrc())){
					if(info.get(x).getSaleImgSrc().indexOf(";") >0){
						String[] urlArray =  info.get(x).getSaleImgSrc().split(";");
						map.put("goodsImgs", API.PIC+urlArray[0]);
					}else{
						map.put("goodsImgs", API.PIC+info.get(x).getSaleImgSrc());
					}
				}
				
				lstImageItem.add(map);
			}
			BaseMapListApater sm = new BaseMapListApater(this, lstImageItem,
					R.layout.member_order_main_info_item, new String[] {
							"goodsImgs", "orderDestail", "orderClass",
							"orderPrice" ,"orderNum"},
					new int[] { R.id.goodsImg, R.id.orderDestail,
							R.id.orderClass, R.id.orderPrice,R.id.orderSum});
			orderGoodList.setAdapter(sm);
		}

		if (order != null) {
			// 订单号
			orderDetailsOrderNum.setText(order.getDingdanBianhao() + "");
			// 下单人姓名
			sureOrderUserName.setText(order.getDingdanName() + "");

			sureOrderAdressInfo.setText(order.getDingdanDizhi()+"");
			
			sureOrderUserTell.setText(order.getDingdanTel()+"");

			// 交易时间
			orderTimeText.setText(order.getDingdanXiadanTime() + "");

			cartPriceConnt.setText(order.getDingdanTotalRmb() + "元");

			// 订单价格
			if(order.getDingdanYouhuijia() != null && !"".equals(order.getDingdanYouhuijia())){
				orderyouhuiqjia.setText("-"+order.getDingdanYouhuijia()+"元");
			}
			if(order.getDingdanPeisongfei() != null && !"".equals(order.getDingdanPeisongfei())){
				orderPeisongPraise.setText("+"+order.getDingdanPeisongfei()+"元");
			}
			// 买家留言
			robOrder_buyerText.setText(order.getDingdanBeiyong3()+"");

			//支付方式
			orderpaytype.setText(order.getDingdanBeiyong1()+"");

			//配送人
			orderTradingStyle.setText(order.getDingdanBeiyong5());

			//订单状态
			orderDetailsOrderCate.setText(order.getDingdanZhuangtai()+"");
			if("待取货".equals(order.getDingdanZhuangtai())){
				orderDetailsOrderCate.setText("配送中");
			}

			//商品总数
			order_totalnum.setText(order.getDingdanGoodsnum()+"件");

			//取消原因
			ordercancelinfo.setText(order.getDingdanBeiyong4()+"");
		}

		if(phone != null && !"".equals(phone)){
			//配送人
			orderPeisongPhone.setText(phone);
		}
	}
	
}
