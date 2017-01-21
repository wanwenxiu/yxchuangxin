package com.yxld.yxchuangxin.activity.order;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.orhanobut.logger.Logger;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.OrderController;
import com.yxld.yxchuangxin.controller.impl.OrderControllerImpl;
import com.yxld.yxchuangxin.activity.order.YinlLianWebViewActivity;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.PayResult;
import com.yxld.yxchuangxin.util.SignUtils;
import com.yxld.yxchuangxin.util.StringUitl;
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

import static com.yxld.yxchuangxin.controller.API.yuming_api;

/**
 * @author wwx
 * @ClassName: PayWaySelectActivity
 * @Description: 收银台，选择付款方式
 * @date 2016年3月8日 上午11:02:23
 */
public class PayWaySelectActivity extends BaseActivity {

    /**
     * 订单接口类
     */
    private OrderController orderController;

    /**
     * 付款的方式列表
     */
    private ListView payWayList;

    //	private String[] payName = {"账户余额支付", "微信支付", "支付宝支付","银联支付"};
    private String[] payName = {"微信支付", "支付宝支付", "银联支付"};
    //	private String[] payDestail = {"使用账户中的余额支付", "微信安全支付", "支付宝安全支付","银联安全支付"};
    private String[] payDestail = {"微信安全支付", "支付宝安全支付", "银联安全支付"};
    //
//	private Integer[] payImg = {R.mipmap.balance_icon, R.mipmap.weixin_icon, R.mipmap.alipay_icon,R.mipmap.unionpay_icon};
    private Integer[] payImg = {R.mipmap.weixin_icon, R.mipmap.alipay_icon, R.mipmap.unionpay_icon};

    /**
     * 获取订单ID
     */
    private String orderId = "";

    /**
     * 获取订单总价
     */
    private String orderMoney = "";

    private String orderShop = "";

    private String orderDetails = "";

    private String orderBianhao = "";

    /**支付功能点区分：*/
    private String paystatus = "";

    private TextView money;

    // 商户PID
    public final String PARTNER = "2088121188417300";
    // 商户收款账号
    public final String SELLER = "hnchxwl@163.com";
    // 商户私钥，pkcs8格式
    public final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK5WXlFMEXppef4KaMuDX+GWZdK+VxlxLghJPJIhyelcJibVAmJZIAPKmVVEFhFilowin6KWtQ0SWIRRKEtt4zkTPtODdVh8aEBEzkdqWoiv99jOdRMz2GoeR4z5AoRfUTFTv6V7B09+C5UesF6EMJRzXfWvqQ9zyRjmogjwExtPAgMBAAECgYEAqw4FRwE7GP/K+a7e+egyOJan26p0rXr2bpzlOIC8qyKGMI3J5BOMrQupfRbsDCzOiDskpJP4mxXIEjPLNI9iZKxieStonOT829mDvuonnAE04JMkbFSD2l25nwfZ4MaX3hoNCLQCyyOhRWg5kToF2cnqIMZXZZWXmELJoTkCkukCQQDT1Ya0UZWnxPWImi+oe9+A57qPVLDu1nVEFCIREUQgIMhN/UEUw6+0lD+f8WA43uCF6ckqjAuGT/uDXB+/dSu9AkEA0q98S/o/lehnxrQRtt0go5d8c9dHsxkDA9X2BoalKcWc8vCdRSGLf1HNsi/HDq+XUecPKkJAWHtVN8qYPRUt+wJAJAEf4xgWyqwkW3JxdT6Qr3UzdVcct4uF5OtTGvmHTbqksPTBkgjsnVGxOrso8qGXIcupoGyrLMn9YsdOshj1NQJBAK3+BM2ONnLrwsBjt3loNutDUKEuOeVbk5TYX1zWV5Iew9YSBh+wa07TVOeB84daVcJq6qhAnHk2KZNwubdARX8CQCRwNRgAYE9ENAlxSIiBM2xhhzs2JK4Fty7++PoKbhD9uSWwDoZq06xoLAEX9YwLOOVZxiw3T1s3dcE9YTuriRE=";
    // 支付宝公钥
    public final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuVl5RTBF6aXn+CmjLg1/hlmXSvlcZcS4ISTySIcnpXCYm1QJiWSADyplVRBYRYpaMIp+ilrUNEliEUShLbeM5Ez7Tg3VYfGhARM5HalqIr/fYznUTM9hqHkeM+QKEX1ExU7+lewdPfguVHrBehDCUc131r6kPc8kY5qII8BMbTwIDAQAB";
    public final int SDK_PAY_FLAG = 1;

    private String payOrderId = "";

    //微信支付
    Handler createOrderHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String result = (String) msg.obj;
                WechatPay.pay(PayWaySelectActivity.this, result);
            }
        }

        ;
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (payWayList != null) {
            payWayList = null;
        }

    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.payway_select_activity_layout);
        getSupportActionBar().setTitle("收银台");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Logger.d("PayWaySelectActivity onOptionsItemSelected paystatus"+paystatus);
            if (paystatus.equals("商城支付")){
                Bundle bundle = new Bundle();
                bundle.putInt("ORDERTYPE", 1);
                startActivity(OrderListActivity.class, bundle);
                finish();
                Contains.weixinPayresult = -1;
            }else if(paystatus.equals("车费支付")){
                Intent intent = new Intent();
                intent.putExtra("backstring","0");
                setResult(101, intent);
                finish();
                Contains.weixinPayresult = -1;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {
        payWayList = (ListView) findViewById(R.id.paywayList);
        money = (TextView) findViewById(R.id.money);
        initDataFromLocal();
    }

    @Override
    protected void initDataFromLocal() {
        orderId = getIntent().getStringExtra("orderId");

        orderMoney = getIntent().getStringExtra("orderMoney");

        orderShop = getIntent().getStringExtra("orderShop");

        orderBianhao = getIntent().getStringExtra("orderBianhao");

        paystatus = getIntent().getStringExtra("paystatus");
        Logger.d("initDataFromLocal paystatus"+paystatus);
        Contains.orderId = orderId;
        orderDetails = getIntent().getStringExtra("orderDetails");

        Log.d("geek", "orderId=" + orderId);
        if (orderMoney != null && !"".equals(orderMoney)) {
            money.setText("支付金额:" + orderMoney + "元");
        } else {
            money.setText("");
        }

        ArrayList<Map<String, String>> listMap = new ArrayList<Map<String, String>>();

        for (int i = 0; i < payName.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", payName[i]);
            map.put("destail", payDestail[i]);
            map.put("icon", payImg[i] + "");
            listMap.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, listMap, R.layout.payway_select_item, new String[]{"name", "destail", "icon"}, new int[]{R.id.payname, R.id.paydestail, R.id.img});

        payWayList.setAdapter(adapter);

        payWayList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				//余额支付
//				if (arg2 == 0) {
//					new SweetAlertDialog(PayWaySelectActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText("支付信息" + money.getText().toString()).setContentText("你是否确定采用余额支付").setCancelText("取消").setConfirmText("确定").showCancelButton(true).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//						@Override
//						public void onClick(SweetAlertDialog sDialog) {
//							sDialog.setTitleText("余额支付失败").setContentText("您已经取消此次支付").setConfirmText("OK").showCancelButton(false).setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(SweetAlertDialog.ERROR_TYPE);
//						}
//					}).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//						@Override
//						public void onClick(SweetAlertDialog sDialog) {
//							sDialog.dismissWithAnimation();
//							initDataFromNet();
////							sDialog.setTitleText("派单成功").setContentText("您已经成功此次派单").setConfirmText("OK").showCancelButton(false).setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//						}
//					}).show();
//				}else
                if (arg2 == 0) {
                    boolean wx = isWeixinAvilible(PayWaySelectActivity.this);
                    if (wx == false) {
                        Toast.makeText(PayWaySelectActivity.this, "微信没安装,您无法使用微信支付", Toast.LENGTH_SHORT).show();
                    }else {
                        // 使用微信进行支付
                        if(paystatus != null && !"".equals(paystatus) && paystatus.equals("车费支付")){
                            Contains.pay = 4;
                        }else{
                            Contains.pay = 1;
                        }
                        new CreateOrderThread().start();
                    }
                } else if (arg2 == 1) {
                    /**
                     * call alipay sdk pay. 调用SDK支付
                     *
                     */
                    if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
                        new AlertDialog.Builder(PayWaySelectActivity.this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                finish();
                            }
                        }).show();
                        return;
                    }
                    String orderInfo = getOrderInfo(orderBianhao, orderShop, orderDetails, orderMoney);

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
                            PayTask alipay = new PayTask(PayWaySelectActivity.this);
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
                } else if (arg2 == 2) {
                    if (paystatus.equals("商城支付")){
                        if (orderMoney == null || "".equals(orderMoney)) {
                            return;
                        }
                        float a = Float.parseFloat(orderMoney) * 100;
                        int money = (int) a;
                        Intent intent = new Intent();
                        intent.setClass(PayWaySelectActivity.this, // context
                                YinlLianWebViewActivity.class);// 跳转的activity\
                        Bundle ylzf = new Bundle();
                        ylzf.putString("name", "银联支付");
                        ylzf.putString("address", API.yuming_api+"/CHINAPAY_DEMO/signServlet.do?BusiType=0001&Version=20140728&CommodityMsg=wwxtest&MerPageUrl=http://www.hnchxwl.com/CHINAPAY_DEMO/pgReturn.do&MerBgUrl=http://www.hnchxwl.com/CHINAPAY_DEMO/bgReturn.do&MerId=531121608230001&" +
                                "MerOrderNo=" + orderBianhao + "&OrderAmt=" + money + "&TranDate=" + getNowDateShort() + "&TranTime=" + getTimeShort() + "&MerResv=1");
                        intent.putExtras(ylzf);
                        startActivity(intent);
                    }else {
                        Toast.makeText(PayWaySelectActivity.this, "暂时只支持微信支付宝支付", Toast.LENGTH_SHORT).show();
                    }
                } else {
//					ToastUtil.show(PayWaySelectActivity.this, payName[arg2] + "暂不支持支付，请选择余额支付");
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

    /**
     * 获取时间 小时 分 秒
     *
     * @return 字符串 HHmmss
     */
    public static String getTimeShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void initDataFromNet() {
        super.initDataFromNet();
        if (orderController == null) {
            orderController = new OrderControllerImpl();
        }
        orderController.YuEPayOrderFromID(mRequestQueue, new Object[]{orderId}, new ResultListener<BaseEntity>() {
            @Override
            public void onResponse(BaseEntity info) {
                progressDialog.hide();
                if (info.status != STATUS_CODE_OK) {
                    new SweetAlertDialog(PayWaySelectActivity.this, SweetAlertDialog.ERROR_TYPE).setTitleText("支付失败").setContentText(info.MSG).show();
                    return;
                } else {
                    String curMoney = info.MSG;
                    if (StringUitl.isNoEmpty(curMoney)) {
                        new SweetAlertDialog(PayWaySelectActivity.this, SweetAlertDialog.WARNING_TYPE).setTitleText("支付成功").setContentText("您已经成功支付,当前余额" + curMoney + "元").setConfirmText("确认").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Bundle bundle = new Bundle();
                                bundle.putInt("ORDERTYPE", 2);
                                startActivity(OrderListActivity.class, bundle);
                                finish();
                            }
                        }).show();
                    }

                }
            }

            @Override
            public void onErrorResponse(String errMsg) {
                onError(errMsg);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (paystatus.equals("商城支付")){
                Bundle bundle = new Bundle();
                bundle.putInt("ORDERTYPE", 1);
                startActivity(OrderListActivity.class, bundle);
                finish();
                Contains.weixinPayresult = -1;
            }else if(paystatus.equals("车费支付")){
                finish();
                Contains.weixinPayresult = -1;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initSuccessPay() {
        if (orderController == null) {
            orderController = new OrderControllerImpl();
        }
        /** 用于请求参数 */
        Map<String, String> map = new HashMap<String, String>();
        map.put("ord.dingdanId", orderId);
        map.put("ord.dingdanZhuangtai", "待发货");
        map.put("ord.dingdanBeiyong1", "支付宝支付");
        //map.put("ord.dingdanPayOrderhao",payOrderId);
        Log.d("geek", "调用更新订单接口" + map.toString());
        orderController.updateOrderState(mRequestQueue, map, listenerUpdateOrder);
    }


    private ResultListener<BaseEntity> listenerUpdateOrder = new ResultListener<BaseEntity>() {

        @Override
        public void onResponse(BaseEntity info) {
            // 获取请求码
            if (info.status != STATUS_CODE_OK) {
                onError(info.MSG);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("ORDERTYPE", 2);
            startActivity(OrderListActivity.class, bundle);
            finish();
        }

        @Override
        public void onErrorResponse(String errMsg) {
            onError(errMsg);
        }
    };


    private void initunionpayPay() {
        if (orderController == null) {
            orderController = new OrderControllerImpl();
        }
        /** 用于请求参数 */
        Map<String, String> map = new HashMap<String, String>();
        map.put("ord.dingdanId", orderId);
        map.put("ord.dingdanZhuangtai", "待发货");
        map.put("ord.dingdanBeiyong1", "银联支付");
        //map.put("ord.dingdanPayOrderhao",payOrderId);
        Log.d("geek", "调用更新订单接口" + map.toString());
        orderController.updateOrderState(mRequestQueue, map, listenerUnionpay);
    }


    private ResultListener<BaseEntity> listenerUnionpay = new ResultListener<BaseEntity>() {

        @Override
        public void onResponse(BaseEntity info) {
            // 获取请求码
            if (info.status != STATUS_CODE_OK) {
                onError(info.MSG);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("ORDERTYPE", 2);
            startActivity(OrderListActivity.class, bundle);
            finish();
        }

        @Override
        public void onErrorResponse(String errMsg) {
            onError(errMsg);
        }
    };


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
    private String getOrderInfo(String dingdanBianhao, String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + dingdanBianhao + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + API.IP_PRODUCT+"/notify_url.jsp" + "\"";
        //http://222.240.1.133/wygl/mall/androidOrder_alipayUpdateOrder
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

        Log.d("geek", "订单信息 = " + orderInfo);
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

        payOrderId = key;
        Log.d("geek", "商户订单号" + payOrderId);
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
                        Toast.makeText(PayWaySelectActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Log.d("geek", "支付成功result" + payResult.getResult());
                        if (paystatus.equals("商城支付")){
                            initSuccessPay();
                        }else {
                            Intent intent = new Intent();
                            intent.putExtra("backstring","0");
                            intent.putExtra("lx","ZFB");
                            intent.putExtra("bianhao",orderBianhao);
                            setResult(101, intent);
                            finish();
                        }

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayWaySelectActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayWaySelectActivity.this, "支付失败,请重试", Toast.LENGTH_SHORT).show();
                            if (paystatus.equals("商城支付")){

                            }else {
                                Intent intent = new Intent();
                                intent.putExtra("backstring","-1");
                                intent.putExtra("lx","ZFB");
                                intent.putExtra("bianhao",orderBianhao);
                                setResult(101, intent);
                                finish();
                            }
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
            final String trade_no = System.currentTimeMillis() + ""; // 每次交易号不一样
            Log.d("geek", trade_no);
            Contains.orderBianhao = trade_no;
            final String total_fee = orderMoney;
            final String subject = orderShop;
            String payType = "";
            if (paystatus.equals("商城支付")){
                payType = "mall_"+Contains.orderId;
            }else if(paystatus.equals("车费支付")){
                payType = "car_"+Contains.orderId;
            }
            String result = WechatPay.createOrder(trade_no, total_fee, subject,payType);
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

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("onStart"+paystatus+Contains.weixinPayresult+Contains.pay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("onResume"+Contains.pay);
        if (Contains.weixinPayresult==0 && paystatus != null && paystatus.equals("车费支付") && Contains.pay == 4){
            Intent intent = new Intent();
            intent.putExtra("backstring","0");
            intent.putExtra("lx","WX");
            intent.putExtra("bianhao",orderBianhao);
            setResult(101, intent);
            finish();
            Contains.weixinPayresult = -1;
        }else if (Contains.weixinPayresult==1 && paystatus != null && paystatus.equals("车费支付")  && Contains.pay == 4){
            Intent intent = new Intent();
            intent.putExtra("backstring","-1");
            intent.putExtra("lx","WX");
            intent.putExtra("bianhao",orderBianhao);
            setResult(101, intent);
            finish();
            Contains.weixinPayresult = -1;
        }else if (Contains.weixinPayresult== 0 && paystatus != null&&  paystatus.equals("商城支付") && Contains.pay==1){
            Bundle bundle = new Bundle();
            bundle.putInt("ORDERTYPE", 2);
            startActivity(OrderListActivity.class, bundle);
            finish();
            Contains.weixinPayresult = -1;
        }
    }
}