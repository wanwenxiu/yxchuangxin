package com.yxld.yxchuangxin.controller.impl;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.controller.TongzhiController;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * @ClassName: TongzhiControllerImpl
 * @Description: 获取通知实现类
 * @author wwx
 * @date 2016年11月3日 下午17:08:22
 */
public class TongzhiControllerImpl implements TongzhiController {


	@Override
	public void getAppTongzhiInfo(RequestQueue mRequestQueue, final Map<String, String> parm, final ResultListener<BaseEntity> listener) {
//		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(String.format(URL_GET_NEWMAINTONGZHI, parm), BaseEntity.class, new Listener<BaseEntity>() {
//
//			@Override
//			public void onResponse(BaseEntity response) {
//				if (listener != null) {
//					listener.onResponse(response);
//				}
//			}
//		}, new ErrorListener() {
//
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				if (listener != null) {
//					listener.onErrorResponse(error.getMessage());
//				}
//			}
//		});
//		gsonRequest.setShouldCache(true);
//		gsonRequest.setTag(URL_GET_NEWMAINTONGZHI);
//		mRequestQueue.add(gsonRequest);

		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				URL_GET_NEWMAINTONGZHI, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				Log.d("geek", "测试通知response" + response);
				BaseEntity info = new BaseEntity();
				if (response != null) {
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(response);
						Log.d("geek",
								"测试测试通知jsonObject" + jsonObject.toString());
						// 获取状态码
						info.status = jsonObject.getInt("status");
						// 获取详细信息
						info.MSG = jsonObject.getString("MSG");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if (listener != null) {
					listener.onResponse(info);
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if (listener != null) {
					listener.onErrorResponse(error.getMessage());
				}
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return parm;
			}
		};
		stringRequest.setTag(URL_GET_NEWMAINTONGZHI);
		mRequestQueue.add(stringRequest);
	}
}
