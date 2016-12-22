package com.yxld.yxchuangxin.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.order.OrderListActivity;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.OrderController;
import com.yxld.yxchuangxin.controller.PayController;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.OrderControllerImpl;
import com.yxld.yxchuangxin.controller.impl.PayControllerImpl;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.BaseEntity2;
import com.yxld.yxchuangxin.listener.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = WXPayEntryActivity.class.getSimpleName();

    private IWXAPI api;

    private TextView mTextView;

    private OrderController orderController;

    private YeZhuController yeZhuController;

    private PayController payController;

    private String url = API.URL_CHONGZHI;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
//        setContentView(R.layout.pay_result);
//        mTextView = (TextView) this.findViewById(R.id.textView_WXPay_Result);
//        Button btnClose = (Button) this.findViewById(R.id.btn_WXPay_Close);
//        btnClose.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        // should place after UI initialize
        api = WXAPIFactory.createWXAPI(this, Contains.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDataFromLocal() {

    }


    private void initSuccessPay() {
        if (orderController == null) {
            orderController = new OrderControllerImpl();
        }
        /** 用于请求参数 */
        Map<String, String> map = new HashMap<String, String>();
        map.put("ord.dingdanId", Contains.orderId);
        map.put("ord.dingdanZhuangtai", "待发货");
        map.put("ord.dingdanBeiyong1", "微信支付");
        map.put("ord.dingdanPayJiaoyihao", Contains.orderBianhao);

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


    @Override
    protected void initDataFromNet() {
        if (yeZhuController == null) {
            yeZhuController = new YeZhuControllerImpl();
        }
//        Map<String, String> parms = new HashMap<String, String>();
//        parms.put("user.userTel", Contains.u.getUserTel());
//        parms.put("user.userIdCard", Contains.cxwyMallUser.getUserIdCard());
//        parms.put("user.userIntegral", Contains.payPrice);
//        yeZhuController.getAllChongzhi(mRequestQueue, url, parms, listening);
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
                Toast.makeText(WXPayEntryActivity.this,info.MSG,Toast.LENGTH_SHORT).show();
//                Contains.cxwyMallUser.setUserIntegral(info.curMoney);
                finish();
            }
        }

        @Override
        public void onErrorResponse(String errMsg) {
            onError(errMsg);
        }
    };


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
        if (Contains.wymonth < 0) {
            yue = Contains.month;
        }else {
            yue=Contains.wymonth+Contains.month;
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
        //当前月的最后一天
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        Date endTime = cal.getTime();
        String endTime1 = datef.format(endTime);
        Logger.d(endTime1);
        String add=addDay(endTime1, Contains.month) + " 00:00:00";
        Logger.d(add);
        progressDialog.show();
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("wp.fwId",Contains.fwid);//缴费房屋id(必填)
        parm.put("wp.month",add );//缴费之后的使用截止时间(必填，格式：2016-12-31 00:00:00 )
        parm.put("wp.s", yue+"");//月数，缴几个月的物业费(必填,用数字 1，2，3。。。)
        parm.put("wp.jfUser", jiaofei);//缴费人名称(选填，APP端使用业主名称)
        parm.put("wp.jsType","2" );//缴费结算方式:0现金；1支付宝；2微信；3银联
        parm.put("wp.znj", Contains.fee.toString());//滞纳金(必填，如果没有，则传递0)
        parm.put("wp.remark","0" );//备注(选填)
        parm.put("wp.chanquanren",jiaofei);//产权人(必填，用户业主本身)
        parm.put("jfWyRecLiushui",Contains.orderBianhao);//流水号（选填）
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
    public void onClick(View v) {

    }


    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent");

        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG, "onReq");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d("geek", "onPayFinish, errCode = " + baseResp.errCode);
        Log.d("geek", "onPayFinish, errStr = " + baseResp.errStr);
        Log.d("geek", "onPayFinish, transaction = " + baseResp.transaction);
        Log.d("geek", "onPayFinish, openId = " + baseResp.openId);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                 if(baseResp.errCode==0){
                     Contains.weixinPayresult= 0;
                     Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                     //判断是什么付钱的
                     if (Contains.pay==1){
                         initSuccessPay();//下单支付
                     }else if (Contains.pay==2){
                         initDataFromNet();//充值支付
                     }else if(Contains.pay == 3){//物业缴费
                         surePay();
                     }else if (Contains.pay==4){ //车位费付款
                         finish();
                     }
                 }else {
                     Toast.makeText(this, "支付失败,请重试", Toast.LENGTH_SHORT).show();
                     Contains.weixinPayresult= 1;
                 }
            finish();
            Logger.d("onResp  Contains.pay="+ Contains.pay);
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
}
