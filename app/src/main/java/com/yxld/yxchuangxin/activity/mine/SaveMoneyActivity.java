package com.yxld.yxchuangxin.activity.mine;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.Main.WebViewActivity;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.BaseEntity2;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.PayResult;
import com.yxld.yxchuangxin.util.SignUtils;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.wxapi.WechatPay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import cn.refactor.library.SmoothCheckBox;

/**
 * @author 1152008367@qq.com
 * @ClassName: SaveMoneyActivity
 * @Description: 充值界面
 * @date 2015年8月12日 下午3:58:15
 */
@SuppressLint({"HandlerLeak", "DefaultLocale", "ShowToast"})
public class SaveMoneyActivity extends BaseActivity {
	/**
	 * BaseActivity
	 * 打印信息Tag
	 */
	private String LOG_TAG = "geek";
	/**
	 * 返回
	 */
	private ImageView returnWrap = null;
	/**
	 * 商品名称
	 */
	private TextView productSubject = null;
	/**
	 * 商品描述
	 */
	private TextView productDetails = null;
	/**
	 * 充值金额
	 */
	private EditText payPrice = null;
	/**
	 * 选择支付宝
	 */
	private SmoothCheckBox checkBoxAliPay;
	/**
	 * 选择微信
	 */
	private SmoothCheckBox checkBoxWeiXin;
	/**
	 *选择银联
	 */
	private SmoothCheckBox checkBoxUnionPay;
	/**
	 * 支付
	 */
	private Button pay = null;

	private String shop;
	private String details;

	private YeZhuController yeZhuController;

	private String url = API.URL_CHONGZHI;

	// 商户PID
	public  final String PARTNER = "2088121188417300";
	// 商户收款账号
	public  final String SELLER = "hnchxwl@163.com";
	// 商户私钥，pkcs8格式
	public  final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK5WXlFMEXppef4KaMuDX+GWZdK+VxlxLghJPJIhyelcJibVAmJZIAPKmVVEFhFilowin6KWtQ0SWIRRKEtt4zkTPtODdVh8aEBEzkdqWoiv99jOdRMz2GoeR4z5AoRfUTFTv6V7B09+C5UesF6EMJRzXfWvqQ9zyRjmogjwExtPAgMBAAECgYEAqw4FRwE7GP/K+a7e+egyOJan26p0rXr2bpzlOIC8qyKGMI3J5BOMrQupfRbsDCzOiDskpJP4mxXIEjPLNI9iZKxieStonOT829mDvuonnAE04JMkbFSD2l25nwfZ4MaX3hoNCLQCyyOhRWg5kToF2cnqIMZXZZWXmELJoTkCkukCQQDT1Ya0UZWnxPWImi+oe9+A57qPVLDu1nVEFCIREUQgIMhN/UEUw6+0lD+f8WA43uCF6ckqjAuGT/uDXB+/dSu9AkEA0q98S/o/lehnxrQRtt0go5d8c9dHsxkDA9X2BoalKcWc8vCdRSGLf1HNsi/HDq+XUecPKkJAWHtVN8qYPRUt+wJAJAEf4xgWyqwkW3JxdT6Qr3UzdVcct4uF5OtTGvmHTbqksPTBkgjsnVGxOrso8qGXIcupoGyrLMn9YsdOshj1NQJBAK3+BM2ONnLrwsBjt3loNutDUKEuOeVbk5TYX1zWV5Iew9YSBh+wa07TVOeB84daVcJq6qhAnHk2KZNwubdARX8CQCRwNRgAYE9ENAlxSIiBM2xhhzs2JK4Fty7++PoKbhD9uSWwDoZq06xoLAEX9YwLOOVZxiw3T1s3dcE9YTuriRE=";
	// 支付宝公钥
	public  final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuVl5RTBF6aXn+CmjLg1/hlmXSvlcZcS4ISTySIcnpXCYm1QJiWSADyplVRBYRYpaMIp+ilrUNEliEUShLbeM5Ez7Tg3VYfGhARM5HalqIr/fYznUTM9hqHkeM+QKEX1ExU7+lewdPfguVHrBehDCUc131r6kPc8kY5qII8BMbTwIDAQAB";
	public  final int SDK_PAY_FLAG = 1;

	//微信支付
	Handler createOrderHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				String result = (String) msg.obj;
				WechatPay.pay(SaveMoneyActivity.this, result);
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle("充值");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.save_money_main);
		boolean wx= isWeixinAvilible(this);
		if (wx==false){
			Toast.makeText(this, "微信没安装,您无法使用微信支付", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void initView() {

		// 加载控件
		returnWrap = (ImageView) findViewById(R.id.returnWrap);
		productSubject = (TextView) findViewById(R.id.product_subject);
		productDetails = (TextView) findViewById(R.id.product_details);
		payPrice = (EditText) findViewById(R.id.payPrice);
		checkBoxAliPay = (SmoothCheckBox) findViewById(R.id.checkBoxAliPay);
		checkBoxWeiXin = (SmoothCheckBox) findViewById(R.id.checkBoxWeiXin);
		checkBoxUnionPay= (SmoothCheckBox) findViewById(R.id.checkBoxUnionPay);
		pay = (Button) findViewById(R.id.pay);
		shop = productSubject.getText().toString();
		details = productDetails.getText().toString();
		Money.setPricePoint(payPrice);

		// 支付宝支付 选择事件
		checkBoxAliPay.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
				if (isChecked) {
					// 当前选中为支付宝支付
					checkBoxWeiXin.setChecked(false);
					checkBoxUnionPay.setChecked(false);
				}
			}
		});

		// 微信支付 选择事件
		checkBoxWeiXin.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
				if (isChecked) {
					// 当前选中为微信支付
					checkBoxAliPay.setChecked(false);
					checkBoxUnionPay.setChecked(false);
				}
			}
		});

		// 银联支付 选择事件
		checkBoxUnionPay.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
				if (isChecked){
					checkBoxAliPay.setChecked(false);
					checkBoxWeiXin.setChecked(false);
				}
			}
		});

		// 支付 点击事件
		pay.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				if (StringUitl.isNotEmpty(SaveMoneyActivity.this, payPrice, "请输入充值金额")) {

					if (checkBoxAliPay.isChecked()) {
						/**
						 * call alipay sdk pay. 调用SDK支付
						 */
						if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
							new AlertDialog.Builder(SaveMoneyActivity.this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER").setPositiveButton("确定", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialoginterface, int i) {
									//
									finish();
								}
							}).show();
							return;
						}
						String orderInfo = getOrderInfo(shop, details, payPrice.getText().toString());

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
								PayTask alipay = new PayTask(SaveMoneyActivity.this);
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
						// 使用微信进行支付
						Contains.pay=2;
						new CreateOrderThread().start();
					}else if (checkBoxUnionPay.isChecked()) {
						String a=payPrice.getText().toString();
						float b=Float.parseFloat(a)*100;
						int money=(int)b;
						Intent intent = new Intent();
						intent.setClass(SaveMoneyActivity.this, // context
								WebViewActivity.class);// 跳转的activity\
						Bundle ylzf = new Bundle();
						ylzf.putString("name", "银联支付");
						ylzf.putString("address","http://222.240.1.133/CHINAPAY_DEMO/signServlet.do?BusiType=0001&Version=20140728&CommodityMsg=wwxtest&MerPageUrl=http://222.240.1.133/CHINAPAY_DEMO/pgReturn.do&MerBgUrl=http://222.240.1.133/CHINAPAY_DEMO/bgReturn.do&MerId=531121608230001&" +
								"MerOrderNo="+System.currentTimeMillis()+""+"&OrderAmt="+money+"&TranDate="+getNowDateShort()+"&TranTime="+getTimeShort()+"&MerResv="+Contains.cxwyMallUser.getUserId()+"u"+money+"");
						intent.putExtras(ylzf);
						startActivity(intent);
					}
				}
			}
		});
	}

	/**
	 * 得到现在时间
	 *
	 * @return 字符串 yyyyMMdd
	 */
	public static String getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getTimeShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	@Override
	protected void initDataFromNet() {
		if (yeZhuController == null) {
			yeZhuController = new YeZhuControllerImpl();
		}
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("user.userTel", Contains.cxwyMallUser.getUserTel());
		parms.put("user.userIdCard", Contains.cxwyMallUser.getUserIdCard());
		parms.put("user.userIntegral", payPrice.getText().toString());
		yeZhuController.getAllChongzhi(mRequestQueue, url, parms, listening);
	}

	private ResultListener<BaseEntity2> listening = new ResultListener<BaseEntity2>() {

		@Override
		public void onResponse(BaseEntity2 info) {
			// 获取请求码
			Log.d("...", info.toString());
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
			}
			if (info.MSG !=null && !"".equals(info.MSG) ) {
				Toast.makeText(SaveMoneyActivity.this,info.MSG,Toast.LENGTH_SHORT).show();
				Contains.cxwyMallUser.setUserIntegral(info.curMoney);
				finish();
			}
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError(errMsg);
		}
	};


	@Override
	protected void initDataFromLocal() {

	}

	@Override
	public void onClick(View v) {

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
						Toast.makeText(SaveMoneyActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
						initDataFromNet();
					} else {
						// 判断resultStatus 为非"9000"则代表可能支付失败
						// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(SaveMoneyActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

						} else {
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							Toast.makeText(SaveMoneyActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

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

	//微信支付
	public class CreateOrderThread extends Thread {
		@Override
		public void run() {
			final String trade_no = System.currentTimeMillis()+""; // 每次交易号不一样
			final String total_fee = payPrice.getText().toString();
			final String subject = shop;
			Contains.payPrice=payPrice.getText().toString();

			String result = WechatPay.createOrder(trade_no, total_fee, subject);
			Message msg = createOrderHandler.obtainMessage();
			msg.what = 0;
			msg.obj = result;
			createOrderHandler.sendMessage(msg);
		}
	}

	public static class Money {
		public static void setPricePoint(final EditText editText) {

			editText.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before,
										  int count) {
					if (s.toString().contains(".")) {
						if (s.length() - 1 - s.toString().indexOf(".") > 2) {
							s = s.toString().subSequence(0,
									s.toString().indexOf(".") + 3);
							editText.setText(s);
							editText.setSelection(s.length());
						}
					}
					if (s.toString().trim().substring(0).equals(".")) {
						s = "0" + s;
						editText.setText(s);
						editText.setSelection(2);
					}
					if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
						if (!s.toString().substring(1, 2).equals(".")) {
							editText.setText(s.subSequence(0, 1));
							editText.setSelection(1);
							return;
						}
					}
				}
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				}
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
				}
			});
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
