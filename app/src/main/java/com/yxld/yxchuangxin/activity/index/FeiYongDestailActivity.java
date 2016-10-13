package com.yxld.yxchuangxin.activity.index;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.FeiYongDestailAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.PayController;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.PayControllerImpl;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.WuyeRecordAndroid;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.PayResult;
import com.yxld.yxchuangxin.util.SignUtils;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.wxapi.WechatPay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @ClassName: FeiYongDestailActivity
 * @Description: 缴纳详情
 * @author wwx
 * @date 2016年4月13日 上午11:22:39
 */
@SuppressLint("HandlerLeak")
public class FeiYongDestailActivity extends BaseActivity implements
		ResultListener<WuyeRecordAndroid> {
	/** 更新全选按钮状态- false */
	public static final int updateNoCheck = 0;
	/** 更新全选按钮状态 - true */
	public static final int updateAllCheck = 1;
	/** 更新价格 */
	public static final int updateCurPrise = 2;

	/** 房屋信息 */
	private TextView address;
	/** 欠费 */
	private TextView qianfei;
	/** 列表 */
	private ListView list;
	/** 全选*/
	private CheckBox AllSelect;
	/** 总价*/
	private TextView priceConnt,yhje;
	/** 确认缴费*/
	private TextView sure;
	/** 优惠券列表*/
	private RelativeLayout yhq;

	/** 业主接口实现类 */
	private YeZhuController yezhuController;
	/** 支付接口实现类*/
	private PayController payController;
	/** 数据集合 */
	private List<WuyeRecordAndroid> listData = new ArrayList<WuyeRecordAndroid>();
	/** 适配器 */
	private FeiYongDestailAdapter adapter;
	
	/** 查询费用类型 例如 ：物业 ，水，电，等*/
	private String type = "物业";
	
	/** 拼接参数list */
//	private String TagListName = "wylist";
//	/** 拼接参数id*/
//	private String TagIdName = "wuyeId";
	private String TagListName = "sdushulist";
	/** 拼接参数id*/
	private String TagIdName = "shuidushuId";
	/** 拼接参数 标识类型*/
	private String urlType = "1";

	private String payType = "1";
	
	/** 查询接口*/
	private String url = API.URL_PAYMENT_RECORDS_WUYE;

	/**
	 * 选择支付宝
	 */
	private CheckBox checkBoxbalance = null;

	/**
	 * 选择支付宝
	 */
	private CheckBox checkBoxAliPay = null;
	/**
	 * 选择微信
	 */
	private CheckBox checkBoxWeiXin = null;

	// 商户PID
	public  final String PARTNER = "2088121188417300";
	// 商户收款账号
	public  final String SELLER = "hnchxwl@163.com";
	// 商户私钥，pkcs8格式
	public  final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK5WXlFMEXppef4KaMuDX+GWZdK+VxlxLghJPJIhyelcJibVAmJZIAPKmVVEFhFilowin6KWtQ0SWIRRKEtt4zkTPtODdVh8aEBEzkdqWoiv99jOdRMz2GoeR4z5AoRfUTFTv6V7B09+C5UesF6EMJRzXfWvqQ9zyRjmogjwExtPAgMBAAECgYEAqw4FRwE7GP/K+a7e+egyOJan26p0rXr2bpzlOIC8qyKGMI3J5BOMrQupfRbsDCzOiDskpJP4mxXIEjPLNI9iZKxieStonOT829mDvuonnAE04JMkbFSD2l25nwfZ4MaX3hoNCLQCyyOhRWg5kToF2cnqIMZXZZWXmELJoTkCkukCQQDT1Ya0UZWnxPWImi+oe9+A57qPVLDu1nVEFCIREUQgIMhN/UEUw6+0lD+f8WA43uCF6ckqjAuGT/uDXB+/dSu9AkEA0q98S/o/lehnxrQRtt0go5d8c9dHsxkDA9X2BoalKcWc8vCdRSGLf1HNsi/HDq+XUecPKkJAWHtVN8qYPRUt+wJAJAEf4xgWyqwkW3JxdT6Qr3UzdVcct4uF5OtTGvmHTbqksPTBkgjsnVGxOrso8qGXIcupoGyrLMn9YsdOshj1NQJBAK3+BM2ONnLrwsBjt3loNutDUKEuOeVbk5TYX1zWV5Iew9YSBh+wa07TVOeB84daVcJq6qhAnHk2KZNwubdARX8CQCRwNRgAYE9ENAlxSIiBM2xhhzs2JK4Fty7++PoKbhD9uSWwDoZq06xoLAEX9YwLOOVZxiw3T1s3dcE9YTuriRE=";
	// 支付宝公钥
	public  final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuVl5RTBF6aXn+CmjLg1/hlmXSvlcZcS4ISTySIcnpXCYm1QJiWSADyplVRBYRYpaMIp+ilrUNEliEUShLbeM5Ez7Tg3VYfGhARM5HalqIr/fYznUTM9hqHkeM+QKEX1ExU7+lewdPfguVHrBehDCUc131r6kPc8kY5qII8BMbTwIDAQAB";
	public  final int SDK_PAY_FLAG = 1;

//	/** 总金额 */
//	private float totalPrice;
//
//	/** 最后总金额 */
//	private float lasttotalPrice;

	/** 自定义广播 */
	private WuyePayWeixinBroad myReceiver = null;

	//微信支付
	Handler createOrderHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				String result = (String) msg.obj;
				WechatPay.pay(FeiYongDestailActivity.this, result);
			}
		};
	};


	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.feiyong_destail_activity_layout);
		boolean wx= isWeixinAvilible(this);
		if (wx==false){
			Toast.makeText(this, "微信没安装,您无法使用微信支付", Toast.LENGTH_SHORT).show();
		}
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
		address = (TextView) findViewById(R.id.address);
		qianfei = (TextView) findViewById(R.id.qianfei);
		list = (ListView) findViewById(R.id.ListView);
		AllSelect = (CheckBox) findViewById(R.id.AllSelect);
		priceConnt = (TextView) findViewById(R.id.priceConnt);
		sure =(TextView) findViewById(R.id.sure);
		sure.setOnClickListener(this);
		yhje = (TextView) findViewById(R.id.yhje);
		yhq = (RelativeLayout) findViewById(R.id.yhq);
		yhq.setOnClickListener(this);
		checkBoxbalance = (CheckBox) findViewById(R.id.checkBoxbalance);
		checkBoxAliPay = (CheckBox) findViewById(R.id.checkBoxAliPay);
		checkBoxWeiXin = (CheckBox) findViewById(R.id.checkBoxWeiXin);

		// 余额支付 选择事件
		checkBoxbalance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// 当前选中为支付宝支付
					checkBoxWeiXin.setChecked(false);
					checkBoxAliPay.setChecked(false);
				}
			}
		});

		// 支付宝支付 选择事件
		checkBoxAliPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// 当前选中为支付宝支付
					checkBoxWeiXin.setChecked(false);
					checkBoxbalance.setChecked(false);
				}
			}
		});

		// 微信支付 选择事件
		checkBoxWeiXin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					// 当前选中为微信支付
					checkBoxAliPay.setChecked(false);
					checkBoxbalance.setChecked(false);
				}
			}
		});

		adapter = new FeiYongDestailAdapter(this, listData, handler);
		list.setAdapter(adapter);
		
		// 全部选中确认下单按钮 点击选中全部的商品
		AllSelect.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isChecked = AllSelect.isChecked();
				for (int i = 0; i < adapter.getListData().size(); i++) {
					adapter.getListData().get(i).setChecked(isChecked);
				}
				if (adapter != null) {
					adapter.setListData(adapter.getListData());
					adapter.notifyDataSetChanged();
				}
				handler.sendEmptyMessage(updateCurPrise);
			}
		});
	}

	@Override
	protected void initDataFromLocal() {
		if (Contains.cxwyYezhu != null && Contains.cxwyYezhu.size() != 0) {
			address.setText("房间:"
					+ Contains.cxwyYezhu.get(0).getYezhuLoupan()
					+ Contains.cxwyYezhu.get(0).getYezhuLoudong() + "栋"
					+ Contains.cxwyYezhu.get(0).getYezhuDanyuan() + "单元"
					+ Contains.cxwyYezhu.get(0).getYezhuFanghao());
		}
		type = getIntent().getStringExtra("curType");
		if(!StringUitl.isNoEmpty(type)){
			type = "物业";
		}
		initViewData();
		initDataFromNet();

		// 注册广播
		myReceiver = new WuyePayWeixinBroad();
		IntentFilter intentFilter = new IntentFilter(getResources().getString(
				R.string.wuyeweixinpay_broad));
		registerReceiver(myReceiver, intentFilter);
	}


	/**
	 * @ClassName: MyMallIndexJumpBroad
	 * @Description: 跳转广播
	 * @author wwx
	 * @date 2016年3月18日 下午3:20:27
	 */
	class WuyePayWeixinBroad extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
				ToastUtil.show(context,"收到微信支付成功广播");
				surePay();
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.returnWrap:
			finish();
			break;
		case R.id.sure:
			//surePay();
			if(checkBoxbalance.isChecked()){
				payType = "1";
				surePay();
			}
			else if (checkBoxAliPay.isChecked()) {
				payType = "2";
				/**
				 * call alipay sdk pay. 调用SDK支付
				 */
				if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
					new AlertDialog.Builder(FeiYongDestailActivity.this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER").setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
					return;
				}
				String orderInfo = getOrderInfo("缴纳"+type+"费", "长沙创新物联有限公司", "0.01"+"");

				/**
				 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
				 */
				String sign = sign(orderInfo);
				try {
					/**
					 * 仅需对sign 做URL编码
					 */
					sign = URLEncoder.encode(sign, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				/**
				 * 完整的符合支付宝参数规范的订单信息
				 */
				final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

				Runnable payRunnable = new Runnable() {

					@Override
					public void run() {
						// 构造PayTask 对象
						PayTask alipay = new PayTask(FeiYongDestailActivity.this);
						// 调用支付接口，获取支付结果
						String result = alipay.pay(payInfo, true);

						Message msg = new Message();
						msg.what = SDK_PAY_FLAG;
						msg.obj = result;
						mHandler.sendMessage(msg);
					}
				};

				// 必须异步调用
				Thread payThread = new Thread(payRunnable);
				payThread.start();
			} else if (checkBoxWeiXin.isChecked()) {
				payType = "3";
				Contains.pay = 3;
				// 使用微信进行支付
				new CreateOrderThread().start();
			}
			break;
//		case R.id.yhq:
//			Intent yhq=new Intent(FeiYongDestailActivity.this,MyCouponActivity.class);
//			yhq.putExtra("curMoney",totalPrice);
//			startActivity(yhq);
//			break;
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("geek","物业缴费onRestart ");
//		refushPrice(MyCouponActivity.yhqjine);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("geek","物业缴费onResume ");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("geek","物业缴费onStart ");
	}

	//	public void refushPrice(float yhjg){
//		if((totalPrice - yhjg)<=0){
//			lasttotalPrice = 0;
//		}else{
//			lasttotalPrice = totalPrice - yhjg;
//		}
//		priceConnt.setText("总计：￥" + lasttotalPrice + "元（优惠"+yhjg+"元）");
//		yhje.setText("- ¥"+yhjg);
//	}
	
	/**
	 * @Title: surePay 
	 * @Description:物业缴费支付    
	 * @return void
	 * @throws
	 */
	private void surePay() {
		if(payController == null){
			payController = new PayControllerImpl();
		}
		progressDialog.show();
		
		Map<String, String> parm = new HashMap<String, String>();
		int count = -1;
		for (int i = 0; i < adapter.getListData().size(); i++) {
			if(adapter.getListData().get(i).isChecked()){
				count++;
				parm.put(TagListName+"["+count+"]."+TagIdName, adapter.getListData().get(i).getId()+"");
			}
		}
		parm.put("i", urlType);
		parm.put("payType",payType);
		parm.put("userid",Contains.cxwyMallUser.getUserId()+"");
		parm.put("payTotalPrice",adapter.getTotalPrice()+"");
		Log.d("geek","支付"+parm.toString());
		payController.getWuyePay(mRequestQueue, parm, payListener);
	}
	
	ResultListener<BaseEntity> payListener = new ResultListener<BaseEntity>(){

		@Override
		public void onResponse(BaseEntity info) {
			Log.d("geek","支付"+info.toString());
			progressDialog.hide();
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
			}else{
				if(info.MSG.contains(",")){
					String[] price = info.MSG.split(",");
					info.MSG = price[0]+",当前余额为:"+price[1]+"元";
					Contains.cxwyMallUser.setUserIntegral(price[1]);
				}
				new SweetAlertDialog(FeiYongDestailActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText("支付成功").setContentText(info.MSG).setConfirmText("确认").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
					@Override
					public void onClick(SweetAlertDialog sDialog) {
						finish();
					}
				}).show();
			}
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError(errMsg);
		}
		
	};

	/** 更新界面文字*/
	private void initViewData() {

		if(type.equals("物业服务")){
			url = API.URL_PAYMENT_RECORDS_WUYE;
			TagListName = "sdushulist";
			TagIdName = "shuidushuId";
			urlType = "3";
		}else if(type.equals("水")){
			url = API.URL_PAYMENT_RECORDS_WATER;
			TagListName = "sdushulist";
			TagIdName = "shuidushuId";
			urlType = "1";
		}else if(type.equals("电")){
			url = API.URL_PAYMENT_RECORDS_ELECTRICITY;
			TagListName = "sdushulist";
			TagIdName = "shuidushuId";
			urlType = "2";
		}

		getSupportActionBar().setTitle(type+"缴费");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		AllSelect.setChecked(false);
		if (yezhuController == null) {
			yezhuController = new YeZhuControllerImpl();
		}

		Map<String, String> parm = new HashMap<String, String>();
		parm.put("i", "2");
		parm.put("loupan", Contains.cxwyYezhu.get(0).getYezhuLoupan());
		parm.put("loudong", Contains.cxwyYezhu.get(0).getYezhuLoudong());
		parm.put("danyuan", Contains.cxwyYezhu.get(0).getYezhuDanyuan());
		parm.put("fanghao", Contains.cxwyYezhu.get(0).getYezhuFanghao() + "");

		yezhuController.getYeZhuWuYeList(mRequestQueue,url, parm, this);
	}

	@Override
	public void onResponse(WuyeRecordAndroid info) {
		// 获取请求码
		if (info.status != STATUS_CODE_OK) {
			onError(info.MSG);
			return;
		}
		if (isEmptyList(info.getRows())) {
			ToastUtil.show(FeiYongDestailActivity.this, "没有查询到记录");
			listData.clear();;
			adapter.setListData(listData);
		} else {
			listData = info.getRows();
			adapter.setListData(listData);
		}
		qianfei.setText("未交款余款:" + info.getQWYFZS());
		progressDialog.hide();
	}

	@Override
	public void onErrorResponse(String errMsg) {
		onError(errMsg);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case updateNoCheck:
				AllSelect.setChecked(false);
				break;
			case updateAllCheck:
				AllSelect.setChecked(true);
				break;
			case updateCurPrise:
				priceConnt.setText("总计：￥" + adapter.getTotalPrice() + "元");
//				totalPrice = adapter.getTotalPrice();
//				lasttotalPrice = totalPrice;
//				refushPrice(0);
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myReceiver);
		if(myReceiver != null){
			myReceiver = null;
		}
		if(handler != null){
			handler.removeCallbacksAndMessages(null);
		}
	}


	/**
	 * get the sdk version. 获取SDK版本号
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 */
	private String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 *
	 * @param content 待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					PayResult payResult = new PayResult((String) msg.obj);
					/**
					 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
					 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
					 * docType=1) 建议商户依赖异步通知
					 */
					String resultInfo = payResult.getResult();// 同步返回需要验证的信息

					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					if (TextUtils.equals(resultStatus, "9000")) {
						Toast.makeText(FeiYongDestailActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
						surePay();
					} else {
						// 判断resultStatus 为非"9000"则代表可能支付失败
						// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(FeiYongDestailActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

						} else {
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							Toast.makeText(FeiYongDestailActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
						}
					}
					break;
				}
				default:
					break;
			}
		}

		;
	};

	public class CreateOrderThread extends Thread {
		@Override
		public void run() {
			final String trade_no = System.currentTimeMillis()+""; // 每次交易号不一样
			final String total_fee = "0.01";
			final String subject = "缴纳"+type+"费";
			String result = WechatPay.createOrder(trade_no, total_fee, subject);
			Message msg = createOrderHandler.obtainMessage();
			msg.what = 0;
			msg.obj = result;
			createOrderHandler.sendMessage(msg);
		}
	}

	public static boolean isWeixinAvilible(Context context) {
		final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				if (pn.equals("com.tencent.mm")) {
					return true;
				}
			}
		}

		return false;
	}
}
