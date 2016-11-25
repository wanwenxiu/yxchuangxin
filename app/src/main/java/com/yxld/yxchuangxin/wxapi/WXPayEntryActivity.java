package com.yxld.yxchuangxin.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.BaseEntity2;
import com.yxld.yxchuangxin.listener.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

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
        setContentView(R.layout.pay_result);
        mTextView = (TextView) this.findViewById(R.id.textView_WXPay_Result);
        Button btnClose = (Button) this.findViewById(R.id.btn_WXPay_Close);
        btnClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

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


//    private void surePay() {
//        if(payController == null){
//            payController = new PayControllerImpl();
//        }
//        progressDialog.show();
//
//        Map<String, String> parm = new HashMap<String, String>();
//        int count = -1;
//        for (int i = 0; i < adapter.getListData().size(); i++) {
//            if(adapter.getListData().get(i).isChecked()){
//                count++;
//                parm.put(TagListName+"["+count+"]."+TagIdName, adapter.getListData().get(i).getId()+"");
//            }
//        }
//        parm.put("i", urlType);
//        parm.put("payType",payType);
//        parm.put("userid",Contains.cxwyMallUser.getUserId()+"");
//        parm.put("payTotalPrice",adapter.getTotalPrice()+"");
//        Log.d("geek","支付"+parm.toString());
//        payController.getWuyePay(mRequestQueue, parm, payListener);
//    }

//    ResultListener<BaseEntity> payListener = new ResultListener<BaseEntity>(){
//
//        @Override
//        public void onResponse(BaseEntity info) {
//            Log.d("geek","支付"+info.toString());
//            progressDialog.hide();
//            if (info.status != STATUS_CODE_OK) {
//                onError(info.MSG);
//                return;
//            }else{
//                if(info.MSG.contains(",")){
//                    String[] price = info.MSG.split(",");
//                    info.MSG = price[0]+",当前余额为:"+price[1]+"元";
//                    Contains.cxwyMallUser.setUserIntegral(price[1]);
//                }
//            }
//        }
//
//        @Override
//        public void onErrorResponse(String errMsg) {
//            onError(errMsg);
//        }
//
//    };

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
            Log.d("geek", "支付结果 = " + baseResp.errCode + ", 原因=" + baseResp.errStr);
            String errStr = null;
            switch (baseResp.errCode) {
                case 0:
                    errStr = "已支付成功";
                    try {
                        JSONObject message = new JSONObject();
                        message.put("payresult", true);
                        // WXPayPlugin.mCallbackContext.sendPluginResult(new
                        // PluginResult(PluginResult.Status.OK, message));
                        //判断是什么付钱的
                        if (Contains.pay==1){
                           initSuccessPay();//下单支付
                        }else if (Contains.pay==2){
                            initDataFromNet();//充值支付
                        }else if(Contains.pay == 3){//物业缴费
                            Intent intentCart = new Intent(getResources().getString(
                                    R.string.wuyeweixinpay_broad));
                            sendBroadcast(intentCart);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case -1:
                    errStr = "支付失败，请检查";
                    try {
                        JSONObject message = new JSONObject();
                        message.put("payresult", false);
                        // WXPayPlugin.mCallbackContext.sendPluginResult(new
                        // PluginResult(PluginResult.Status.OK, message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case -2:
                    errStr = "支付已取消";
                    try {
                        JSONObject message = new JSONObject();
                        message.put("payresult", false);
                        // WXPayPlugin.mCallbackContext.sendPluginResult(new
                        // PluginResult(PluginResult.Status.OK, message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    try {
                        JSONObject message = new JSONObject();
                        message.put("payresult", false);
                        // WXPayPlugin.mCallbackContext.sendPluginResult(new
                        // PluginResult(PluginResult.Status.OK, message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
			Toast.makeText(WXPayEntryActivity.this,errStr,Toast.LENGTH_SHORT).show();
            mTextView.setText(errStr);
        }
    }
}
