package com.yxld.yxchuangxin.wxapi;

import android.app.Activity;
import android.util.Log;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.util.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WechatPay {
	/**
	 * 生成订单的方法
	 * 
	 * @param tradeNo 交易号
	 * @param totalFee 支付金额
	 * @param subject 详细描述
	 * @return
	 */
	public static String createOrder(String tradeNo, String totalFee, String subject,String payType) {
		Log.d("geek", "createOrder: 'createOrder"+subject);
		String result = "";
		//http://www..../WechatPayServer/UnifiedOrderServlet?trade_no=" + tradeNo + "&total_fee=" + totalFee + "&subject=" + subject
		String URL_PREPAY = Contains.URL_PAY_CALLBACK + "/UnifiedOrderServlet";

		try {
			subject = URLEncoder.encode(subject, "UTF-8");
			payType = URLEncoder.encode(payType, "UTF-8");
			String url = URL_PREPAY + "?trade_no=" + tradeNo + "&total_fee=" + totalFee + "&subject=" + subject +"&payType="+payType;
//			String url = URL_PREPAY + "?trade_no=" + tradeNo + "&total_fee=" + totalFee + "&subject=" + subject;
			Log.d("geek", "createOrder: 'url="+url);
			result = HttpUtils.doGet(url);
			Log.d("geek","微信创建订单 result="+result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 支付的方法
	 * 
	 * @param activity
	 * @param result 服务器生成订单返回的json字符串
	 * 
	 */
	public static void pay(Activity activity, String result) {
		IWXAPI api = WXAPIFactory.createWXAPI(activity, Contains.WX_APP_ID); // 将该app注册到微信
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			PayReq payReq = new PayReq();
//			payReq.appId = Contains.WX_APP_ID;
//			payReq.partnerId = Contains.WX_MCH_ID;
//			payReq.prepayId = jsonObject.getString("prepay_id");
//			payReq.nonceStr = jsonObject.getString("nonce_str");
//			payReq.timeStamp = jsonObject.getString("timestamp");
//			payReq.packageValue = jsonObject.getString("package");
//			payReq.sign = jsonObject.getString("sign");
			payReq.appId = Contains.WX_APP_ID;
			payReq.partnerId = Contains.WX_MCH_ID;
			payReq.prepayId = jsonObject.getString("prepayid");
			payReq.nonceStr = jsonObject.getString("noncestr");
			payReq.timeStamp = jsonObject.getString("timestamp");
			payReq.packageValue = jsonObject.getString("package");
			payReq.sign = jsonObject.getString("sign");
			Log.d("geek", "pay: payReq="+payReq.toString());
			api.sendReq(payReq);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
