package com.yxld.yxchuangxin.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.OrderController;
import com.yxld.yxchuangxin.controller.impl.OrderControllerImpl;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.view.LoadingImg;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SureOrderActivity
 * @Description: 确认订单界面
 * @author wwx
 * @date 2016年3月8日 上午9:38:00
 */
public class SureOrderActivity extends BaseActivity implements ResultListener<BaseEntity> {

	/** 提交订单 */
	private TextView submitOrder;
	/** 收货人 */
	private TextView user_name;
	/** 联系电话 */
	private TextView user_phone;
	/** 收货地址 */
	private TextView user_addr;
	/** 默认地址提示布局 */
	private LinearLayout llAddrTip = null;
	/** 存放商品信息布局 */
	private LinearLayout goodsWarp = null;
	/** 订单付款方式 */
	private TextView pay_type;
	/** 订单商品金额 */
	private TextView goods_price;
	/** 订单运费金额 */
	private TextView order_other_price;
	/** 订单总金额 */
	private TextView order_total_price,yhje;
	/** 订单配送地址 */
	private TextView order_addr;
	/** 备注 */
	private EditText order_mark;
	/** 商品数量 */
	private TextView order_good_sum;

	/** 地址管理 */
	private RelativeLayout address_message;
	/** 商品数量*/
	private int goodNum;

	/** 存放商品图片路径数组 */
	private List<String> imgUrl = new ArrayList<String>();

	/** 用于请求参数 */
	private Map<String, String> map = new HashMap<String, String>();

	/** 总金额 */
	private double totalPrice;

	/** 最后总金额 */
	private double lasttotalPrice;

	/** 订单接口类*/
	private OrderController orderController;

	private int totlaNum;

	/** 商品名称 */
	private String shop = "";

	/** 商品规格 */
	private String details;

	private View yhq;

	String yhqId = null;

	private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

	/** 是否为大件商品 为0*/
	private int isDaJianGoods = 0;

	/** 配送费 为0*/
	private int  peisongfei = 0;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(imgUrl != null){
			imgUrl = null;
		}
	}

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.sure_order_activity_layout);
		getSupportActionBar().setTitle("确认订单");
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
		if(Contains.defuleAddress != null){
			Log.d("geek",Contains.defuleAddress.toString());
			updateAddress(Contains.defuleAddress.getAddName(),
					Contains.defuleAddress.getAddTel(),Contains.defuleAddress.getAddSpare1()+Contains.defuleAddress.getAddVillage()+
							Contains.defuleAddress.getAddAdd());
		}
	}

	@Override
	protected void initView() {
		submitOrder = (TextView) findViewById(R.id.submitOrder);
		submitOrder.setOnClickListener(this);

		address_message = (RelativeLayout) findViewById(R.id.address_message);
		address_message.setOnClickListener(this);

		llAddrTip = (LinearLayout) findViewById(R.id.llAddrTip);

		goodsWarp = (LinearLayout) findViewById(R.id.goodsWarp);

		user_name = (TextView) findViewById(R.id.user_name);
		user_phone = (TextView) findViewById(R.id.user_phone);
		user_addr = (TextView) findViewById(R.id.user_addr);
		pay_type = (TextView) findViewById(R.id.pay_type);
		goods_price = (TextView) findViewById(R.id.goods_price);
		order_other_price = (TextView) findViewById(R.id.order_other_price);
		order_total_price = (TextView) findViewById(R.id.order_total_price);
		order_addr = (TextView) findViewById(R.id.order_addr);
		order_good_sum = (TextView) findViewById(R.id.order_good_sum);
		order_mark = (EditText) findViewById(R.id.order_mark);

		yhje = (TextView) findViewById(R.id.yhje);
		yhq=findViewById(R.id.yhq);
		yhq.setOnClickListener(this);
	}

	@Override
	protected void initDataFromLocal() {
		if(orderController == null){
			orderController = new OrderControllerImpl();
		}
		// 清空参数信息
		map.clear();
		imgUrl.clear();
		goodNum = 0;

		// saleList[0].saleShangpName=商品名称&saleList[0].saleNum=商品数量&saleList[0].saleGuige=规格&saleList[0].saleImgSrc=图片路径&saleList[0].saleShangpNum=商品id

		// 判断确认订单数据是否为空
		if (Contains.sureOrderList.size() != 0) {
			int count = -1;
			for (int i = 0; i < Contains.sureOrderList.size(); i++) {
				
				// 获取图片路径，用于构建图片
					if(Contains.sureOrderList.get(i).getGoodsSrc() != null && !"".equals(Contains.sureOrderList.get(i).getGoodsSrc())){
						if(Contains.sureOrderList.get(i).getGoodsSrc().indexOf(";") >0){
							String[] urlArray =  Contains.sureOrderList.get(i).getGoodsSrc().split(";");
							imgUrl.add(API.PIC+urlArray[0]);
						}else{
							imgUrl.add(API.PIC+Contains.sureOrderList.get(0).getGoodsSrc());
						}
					}
					
					count++;
					
					//获取商品数量
					goodNum += Integer.parseInt(Contains.sureOrderList.get(i).getGoodsNum());
					
					//叠加总金额
					totalPrice += (Double.parseDouble(Contains.sureOrderList.get(i)
							.getGoodsRmb()) * Integer.parseInt(Contains.sureOrderList.get(i).getGoodsNum()) );

					if(isDaJianGoods != 1){
						//判断是否是大件商品
						if(Contains.sureOrderList.get(i).getIsDajianGoods() != null && "1".equals(Contains.sureOrderList.get(i).getIsDajianGoods())){
							isDaJianGoods = 1;
						}
					}

				    lasttotalPrice = totalPrice;

					if(Contains.sureOrderList.get(i).getGoodsShop() != null && !"".equals(Contains.sureOrderList.get(i).getGoodsShop())){
						shop+=Contains.sureOrderList.get(i).getGoodsShop();
					}
					details+=Contains.sureOrderList.get(i).getGoodsDetails();
				// 拼接商品信息
					map.put("saleList[" + count + "].saleNum",
							Contains.sureOrderList.get(i).getGoodsNum() + "");
					map.put("saleList[" + count + "].saleShangpNum",
							Contains.sureOrderList.get(i).getGoodsId() + "");
					//商品总数量
					totlaNum += Integer.valueOf(Contains.sureOrderList.get(i).getGoodsNum());
					//购物车ID
					map.put("saleList[" + count + "].saleCardId", Contains.sureOrderList.get(i).getCartId()+"");
			}
		}

		refushView();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if(Contains.defuleAddress != null && Contains.defuleAddress.getAddName() != null
			&& Contains.defuleAddress.getAddTel() != null  && Contains.defuleAddress.getAddAdd() != null ){
			updateAddress(Contains.defuleAddress.getAddName(),
					Contains.defuleAddress.getAddTel(),Contains.defuleAddress.getAddSpare1()+Contains.defuleAddress.getAddVillage()+
							Contains.defuleAddress.getAddAdd());
		}

		refushPrice(MyCouponActivity.yhqjine);
	}

	/**
	 * @Title: refushView
	 * @Description: 刷新界面
	 * @return void
	 * @throws
	 */
	private void refushView() {
		Log.d("geek","refushView()");

		//为大件商品
		if(isDaJianGoods == 1){
			peisongfei = 3;
		}else{
			if(lasttotalPrice <20){
				peisongfei = 1;
			}else{
				peisongfei = 0;
			}
		}

		//修改运费价格
		order_other_price.setText("+¥" + peisongfei+".00");
		order_good_sum.setText("共" + goodNum + "件");
		goods_price.setText("¥" + decimalFormat.format(totalPrice));
		order_total_price.setText("实付款:¥" + decimalFormat.format(totalPrice+peisongfei));

		if(imgUrl.size() != 0){
			for (int i = 0; i < imgUrl.size(); i++) {
				 ImageView imageView = new ImageView(this);  
			     imageView.setLayoutParams(new LayoutParams(200,LayoutParams.MATCH_PARENT));  
			     imageView.setPadding(5, 0, 5, 0);
				 if(imgUrl.get(i) != null && !"".equals(imgUrl.get(i))){
					 LoadingImg.LoadingImgs(this).displayImage(imgUrl.get(i), imageView, LoadingImg.option1);
				 }else{
					 imageView.setImageResource(R.mipmap.plugin_camera_no_pictures);
				 }
			     goodsWarp.addView(imageView);
			}
		}
	}


	public void refushPrice(float yhjg){
		Log.d("geek","refushPrice lasttotalPrice"+lasttotalPrice);
		if((totalPrice - yhjg)<=0){
			lasttotalPrice = 0;
		}else{
			lasttotalPrice = totalPrice - yhjg;
		}

		//为大件商品
		if(isDaJianGoods == 1){
			peisongfei = 3;
		}else{
			if(lasttotalPrice <20){
				peisongfei = 1;
			}else{
				peisongfei = 0;
			}
		}

		//修改运费价格
		order_other_price.setText("+¥" + peisongfei+".00");

		goods_price.setText("¥" + decimalFormat.format(totalPrice));
		order_total_price.setText("实付款:¥" + decimalFormat.format(lasttotalPrice+peisongfei));
		yhje.setText("- ¥"+yhjg);

	}

	/***
	 * @Title: updateAddress
	 * @Description: 更新收货地址
	 * @param addr 地址
	 * @return void
	 * @throws
	 */
	private void updateAddress(String name, String phone, String addr) {

		// 更新默认地址 ,如果收货人、联系电话和地址为空，则显示选择地址提示框
		if (name == null || phone == null || addr == null) {
			user_name.setText("");
			user_name.setVisibility(View.GONE);
			user_phone.setText("");
			user_phone.setVisibility(View.GONE);
			user_addr.setText("");
			user_addr.setVisibility(View.GONE);
			llAddrTip.setVisibility(View.VISIBLE);
			return;
		}
		user_name.setVisibility(View.VISIBLE);
		user_name.setText(name);
		user_phone.setVisibility(View.VISIBLE);
		user_phone.setText(phone);
		user_addr.setVisibility(View.VISIBLE);
		user_addr.setText(addr);
		llAddrTip.setVisibility(View.GONE);
		
		order_addr.setText("送至:  "+addr+" "+name+"收");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submitOrder:
			// 如果已经选择收获地址。则存放订单信息
			if (llAddrTip.getVisibility() == View.GONE) {
				// 设置订单参数
				map.put("ord.dingdanName", user_name.getText().toString());
				map.put("ord.dingdanDizhi", user_addr.getText().toString());
				map.put("ord.dingdanTel", user_phone.getText().toString());
				map.put("ord.dingdanTotalRmb", decimalFormat.format(lasttotalPrice+peisongfei));
				map.put("ord.dingdanUserName", Contains.user.getYezhuShouji());
				map.put("ord.dingdanBeiyong1", "未支付");
				map.put("ord.dingdanImgSrc", Contains.sureOrderList.get(0).getGoodsSrc());
				map.put("ord.dingdanVillage", Contains.curSelectXiaoQuId+"");
				map.put("ord.dingdanUserId",Contains.user.getYezhuId()+"");
				map.put("ord.dingdanPeisongfei",peisongfei+"");
				map.put("ord.dingdanDajianpeisong",isDaJianGoods+"");

				//备注
				map.put("ord.dingdanBeiyong3", order_mark.getText().toString()+"");
				//商品总数
				map.put("ord.dingdanGoodsnum", totlaNum+"");

				//优惠券id和优惠金额
				if(MyCouponActivity.yhqId != 0 && MyCouponActivity.yhqjine != 0){
					map.put("ord.dingdanYouhuiquanId", MyCouponActivity.yhqId+"");
					map.put("ord.dingdanYouhuijia", MyCouponActivity.yhqjine+"");
				}

				progressDialog.show();
				orderController.addOrder(mRequestQueue, map, this);
			} else {
				ToastUtil.show(SureOrderActivity.this, "请选择收货地址");
			}
			Log.d("geek", "选中条目 拼接" + map.toString());
			break;
		case R.id.address_message:
			// 跳转至选择收获地址列表界面
			Bundle bundle = new Bundle();
			bundle.putString("AddressTag", "sureOrder");
			startActivity(AddressListActivity.class, bundle);
			break;
		case R.id.yhq:
			Intent yhq=new Intent(SureOrderActivity.this,MyCouponActivity.class);
			yhq.putExtra("curMoney",decimalFormat.format(lasttotalPrice));
			startActivity(yhq);
			break;
		default:
			break;
		}
	}



	@Override
	public void onResponse(BaseEntity info) {
		// 获取请求码
		if (info.status != STATUS_CODE_OK) {
			onError(info.MSG);
			return;
		}

		String orderss = info.MSG;
		Log.d("geek","下单完成返回订单id 和订单编号 ="+orderss);
		String[] orderid = orderss.split(",");
		if(StringUitl.isNoEmpty(orderid[0])){
			//请求成功跳转界面
			Intent intent = new Intent(SureOrderActivity.this, PayWaySelectActivity.class);
			intent.putExtra("orderId", orderid[0]);
			intent.putExtra("orderMoney",decimalFormat.format(lasttotalPrice+peisongfei));
			intent.putExtra("orderShop",shop);
			intent.putExtra("orderDetails",details);
			intent.putExtra("orderBianhao",orderid[1]);
			intent.putExtra("paystatus","商城支付");
			startActivity(intent);
			finish();
		}
		progressDialog.hide();
	}

	@Override
	public void onErrorResponse(String errMsg) {
		onError("请求超时");
	}
}
