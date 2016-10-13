package com.yxld.yxchuangxin.controller.impl;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.controller.ComplaintController;
import com.yxld.yxchuangxin.entity.FirstClassGoodsInfo;
import com.yxld.yxchuangxin.entity.Household;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;



public class ComplaintControllerImpl implements ComplaintController {

	@Override
	public void getHouseHold(RequestQueue mRequestQueue,
			final ResultListener<Household> listener) {
		// TODO Auto-generated method stub
		GsonRequest<Household> gsonRequest = new GsonRequest<Household>(
				URL_GET_ALL_COMPLAINT, Household.class,
				new Listener<Household>() {

					@Override
					public void onResponse(Household response) {
						if (listener != null) {
							listener.onResponse(response);
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						if (listener != null) {
							listener.onErrorResponse(error.getMessage());
						}
					}
				});
		gsonRequest.setTag(URL_GET_ALL_COMPLAINT);
		mRequestQueue.add(gsonRequest);               
	}

	@Override
	public void getComplaintSubmit(RequestQueue mRequestQueue,
			final Map<String, String> parm,
			final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest = new StringRequest(Method.POST,
				URL_GET_ALL_COMPAINT_WUYE_SUBMIT, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek", "测试response" + response);
						BaseEntity info = new BaseEntity();
						if (response != null) {
							JSONObject jsonObject;
							try {
								jsonObject = new JSONObject(response);
								Log.d("geek",
										"测试jsonObject" + jsonObject.toString());
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
		stringRequest.setTag(URL_GET_ALL_COMPAINT_WUYE_SUBMIT);
		mRequestQueue.add(stringRequest);
	}
}
