package com.yxld.yxchuangxin.controller.impl;

import java.util.Map;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.controller.PayController;
import com.yxld.yxchuangxin.listener.ResultListener;

public class PayControllerImpl implements PayController{
	
	/** 解析对象*/
	private Gson gson = new Gson();

	@Override
	public void getWuyePay(RequestQueue mRequestQueue,
			final Map<String, String> parm, final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,URL_WUYE_PAY,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","支付response"+response.toString());
						BaseEntity info = null;
						if(response != null){
							info = gson.fromJson(response, BaseEntity.class);
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
			protected Map<String, String> getParams()
					throws AuthFailureError {
				return parm;
			}
		};
		stringRequest.setTag(URL_WUYE_PAY);
		mRequestQueue.add(stringRequest);		
	}

}
