package com.yxld.yxchuangxin.activity.index;

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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.orhanobut.logger.Logger;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.order.PayWaySelectActivity;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.PayController;
import com.yxld.yxchuangxin.controller.impl.PayControllerImpl;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.PayResult;
import com.yxld.yxchuangxin.util.SignUtils;
import com.yxld.yxchuangxin.view.AmountView;
import com.yxld.yxchuangxin.wxapi.WechatPay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.refactor.library.SmoothCheckBox;

import static com.yxld.yxchuangxin.R.id.money;
import static com.yxld.yxchuangxin.R.id.month;
import static com.yxld.yxchuangxin.R.id.sure;
import static com.yxld.yxchuangxin.R.id.yz_zhenshiname;
import static com.yxld.yxchuangxin.contain.Contains.orderBianhao;

/**
 * @author wwx
 * @ClassName: FeiYongDestailActivity
 * @Description: 缴纳详情
 * @date 2016年4月13日 上午11:22:39
 */
@SuppressLint("HandlerLeak")
public class FeiYongDestailActivity extends BaseActivity {
    private TextView address, price, real_price;//地址  价格  实时价格
    private View zfb,wx;
    private Button sure;// 确认缴费
    private String name, position, fwid, bianhao;
    private int wymonth;
    private int month = 0;
    private Double money, mianji, jg, fee;
    private SmoothCheckBox checkBoxAliPay, checkBoxWeiXin;// 选择支付宝 选择微信
    private AmountView amount_view;
    private PayController payController;
    private Handler handler = new MyHandler();
    private static final int UPDATE_PRICE = 1;
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");


    //支付宝
    public final String PARTNER = "2088121188417300";
    public final String SELLER = "hnchxwl@163.com";
    public final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK5WXlFMEXppef4KaMuDX+GWZdK+VxlxLghJPJIhyelcJibVAmJZIAPKmVVEFhFilowin6KWtQ0SWIRRKEtt4zkTPtODdVh8aEBEzkdqWoiv99jOdRMz2GoeR4z5AoRfUTFTv6V7B09+C5UesF6EMJRzXfWvqQ9zyRjmogjwExtPAgMBAAECgYEAqw4FRwE7GP/K+a7e+egyOJan26p0rXr2bpzlOIC8qyKGMI3J5BOMrQupfRbsDCzOiDskpJP4mxXIEjPLNI9iZKxieStonOT829mDvuonnAE04JMkbFSD2l25nwfZ4MaX3hoNCLQCyyOhRWg5kToF2cnqIMZXZZWXmELJoTkCkukCQQDT1Ya0UZWnxPWImi+oe9+A57qPVLDu1nVEFCIREUQgIMhN/UEUw6+0lD+f8WA43uCF6ckqjAuGT/uDXB+/dSu9AkEA0q98S/o/lehnxrQRtt0go5d8c9dHsxkDA9X2BoalKcWc8vCdRSGLf1HNsi/HDq+XUecPKkJAWHtVN8qYPRUt+wJAJAEf4xgWyqwkW3JxdT6Qr3UzdVcct4uF5OtTGvmHTbqksPTBkgjsnVGxOrso8qGXIcupoGyrLMn9YsdOshj1NQJBAK3+BM2ONnLrwsBjt3loNutDUKEuOeVbk5TYX1zWV5Iew9YSBh+wa07TVOeB84daVcJq6qhAnHk2KZNwubdARX8CQCRwNRgAYE9ENAlxSIiBM2xhhzs2JK4Fty7++PoKbhD9uSWwDoZq06xoLAEX9YwLOOVZxiw3T1s3dcE9YTuriRE=";
    public final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuVl5RTBF6aXn+CmjLg1/hlmXSvlcZcS4ISTySIcnpXCYm1QJiWSADyplVRBYRYpaMIp+ilrUNEliEUShLbeM5Ez7Tg3VYfGhARM5HalqIr/fYznUTM9hqHkeM+QKEX1ExU7+lewdPfguVHrBehDCUc131r6kPc8kY5qII8BMbTwIDAQAB";
    public final int SDK_PAY_FLAG = 1;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.feiyong_destail_activity_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fwid = bundle.getString("fwid");
        fee = bundle.getDouble("fee");
        wymonth = bundle.getInt("wymonth");
        name = bundle.getString("name");
        mianji = bundle.getDouble("mianji");
        money = bundle.getDouble("money");
        position = bundle.getString("position");
        Contains.fwid=fwid;
        Contains.fee=fee;
        Contains.wymonth=wymonth;
        Logger.d(wymonth);
    }

    @Override
    protected void initView() {
        address = (TextView) findViewById(R.id.address);
        price = (TextView) findViewById(R.id.price);
        real_price = (TextView) findViewById(R.id.real_price);
        sure = (Button) findViewById(R.id.sure);
        zfb=findViewById(R.id.zfb);
        wx=findViewById(R.id.wx);
        amount_view = (AmountView) findViewById(R.id.amount_view);
        address.setText(position);
        sure.setOnClickListener(this);
        amount_view.setGoods_storage(12);
        zfb.setOnClickListener(this);
        wx.setOnClickListener(this);
        checkBoxAliPay = (SmoothCheckBox) findViewById(R.id.checkBoxAliPay);
        checkBoxWeiXin = (SmoothCheckBox) findViewById(R.id.checkBoxWeiXin);
        checkBoxAliPay.setChecked(true);
        if (name.equals("您还有未交金额")) {
            real_price.setText("支付金额:" + decimalFormat.format(money));
        } else {
            real_price.setText("支付金额:0.00");
        }
        if (name.equals("您还有未交金额")) {
            price.setText("您有欠款:" + decimalFormat.format(money) + "元");
        } else {
            price.setText("您已预交:" + decimalFormat.format(money) + "元");
        }

        amount_view.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, final int amount) {
                new Thread(new Runnable() {
                    @Override
                    public void run() { //　新建一个线程，并新建一个Message的对象，是用Handler的对象发送这个Message
                        Message msg = new Message();
                        msg.what = UPDATE_PRICE; // 用户自定义的一个值，用于标识不同类型的消息
                        msg.arg1 = amount;
                        handler.sendMessage(msg); // 发送消息
                    }
                }).start();
            }
        });
        // 支付宝支付 选择事件
        checkBoxAliPay.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(SmoothCheckBox buttonView, boolean isChecked) {
                if (isChecked) {
                    // 当前选中为支付宝支付
                    checkBoxWeiXin.setChecked(false);
                }
            }
        });

        // 微信支付 选择事件
        checkBoxWeiXin.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(SmoothCheckBox buttonView, boolean isChecked) {
                if (isChecked) {
                    // 当前选中为微信支付
                    checkBoxAliPay.setChecked(false);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure:
                if (checkBoxAliPay.isChecked()) {
                    /**
                     * call alipay sdk pay. 调用SDK支付
                     *
                     */
                    if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
                        new AlertDialog.Builder(FeiYongDestailActivity.this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                finish();
                            }
                        }).show();
                        return;
                    }
                    String fq = real_price.getText().toString();
                    fq = fq.substring(5, fq.length());
                    bianhao = System.currentTimeMillis() + "";
//                    String orderInfo = getOrderInfo(bianhao, "物业费缴纳", "物业费缴纳", fq);
                    String orderInfo = getOrderInfo(bianhao, "物业费缴纳", "物业费缴纳", fq);
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
                    boolean wx = isWeixinAvilible(FeiYongDestailActivity.this);
                    if (wx == false) {
                        Toast.makeText(FeiYongDestailActivity.this, "微信没安装,您无法使用微信支付", Toast.LENGTH_SHORT).show();
                    } else {
                        Contains.month=month;
                        Contains.pay =3;
                        new CreateOrderThread().start();
                    }
                }
                break;
        }
    }

    // 定义一个内部类继承自Handler，并且覆盖handleMessage方法用于处理子线程传过来的消息
    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_PRICE: // 接受到消息之后，对UI控件进行修改
                    month = msg.arg1;
                    if (name.equals("您还有未交金额")) {
                        jg = mianji * month + money;
                        real_price.setText("支付金额:" + decimalFormat.format(jg));
                    } else {
                        jg = mianji * month;
                        real_price.setText("支付金额:" + decimalFormat.format(jg));
                    }
                    break;
            }
        }
    }

    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.MONTH, n);//增加一个月
            return sdf.format(cd.getTime());
        } catch (Exception e) {
            return null;
        }
    }
    //物业缴费支付
    private void surePay() {
        if (payController == null) {
            payController = new PayControllerImpl();
        }
        String jiaofei;
        if (Contains.user == null || Contains.appYezhuFangwus == null || Contains.appYezhuFangwus.size() == 0) {
            jiaofei = "业主信息未完善";
        } else {
            jiaofei = Contains.user.getYezhuName();
        }
        Logger.d(jiaofei);
        int yue;
        if (wymonth < 0) {
            yue = month;
        }else {
            yue=wymonth+month;
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
        //当前月的最后一天
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        Date endTime = cal.getTime();
        String endTime1 = datef.format(endTime);
        Logger.d(endTime1);
        String add=addDay(endTime1, month) + " 00:00:00";
        Logger.d(add);
        progressDialog.show();
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("wp.fwId",fwid);//缴费房屋id(必填)
		parm.put("wp.month",add );//缴费之后的使用截止时间(必填，格式：2016-12-31 00:00:00 )
		parm.put("wp.s", yue+"");//月数，缴几个月的物业费(必填,用数字 1，2，3。。。)
		parm.put("wp.jfUser", jiaofei);//缴费人名称(选填，APP端使用业主名称)
		parm.put("wp.jsType","1" );//缴费结算方式:0现金；1支付宝；2微信；3银联
		parm.put("wp.znj", fee.toString());//滞纳金(必填，如果没有，则传递0)
		parm.put("wp.remark","0" );//备注(选填)
		parm.put("wp.chanquanren",jiaofei);//产权人(必填，用户业主本身)
		parm.put("jfWyRecLiushui",bianhao);//流水号（选填）
		Log.d("geek","支付"+parm.toString());
		payController.getWuyePay(mRequestQueue, parm, payListener);
    }

    ResultListener<BaseEntity> payListener = new ResultListener<BaseEntity>() {

        @Override
        public void onResponse(BaseEntity info) {
            Log.d("geek", "支付" + info.toString());
            progressDialog.hide();
            if (info.status != STATUS_CODE_OK) {
                onError(info.MSG);
                return;
            }
        }

        @Override
        public void onErrorResponse(String errMsg) {
            onError(errMsg);
        }

    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void initDataFromLocal() {

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
        orderInfo += "&notify_url=" + "\"" + "http://222.240.1.133/wygl/notify_url.jsp" + "\"";
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
                        Log.d("geek", "支付成功result" + payResult.getResult());
                        surePay();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(FeiYongDestailActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(FeiYongDestailActivity.this, "支付失败,请重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
            }
        }

        ;
    };

    //微信支付
    public class CreateOrderThread extends Thread {
        @Override
        public void run() {
            String fq = real_price.getText().toString();
            fq = fq.substring(5, fq.length());
            final String trade_no = System.currentTimeMillis() + ""; // 每次交易号不一样
            Log.d("geek", trade_no);
            Contains.orderBianhao = trade_no;
            final String total_fee = fq;
            final String subject = "物业费缴纳";
            String result = WechatPay.createOrder(trade_no, total_fee, subject);
            Message msg = createOrderHandler.obtainMessage();
            msg.what = 0;
            msg.obj = result;
            createOrderHandler.sendMessage(msg);
        }
    }

    Handler createOrderHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String result = (String) msg.obj;
                WechatPay.pay(FeiYongDestailActivity.this, result);
            }
        }

        ;
    };

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
