package com.yxld.yxchuangxin.controller.impl;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yxld.yxchuangxin.controller.DoorController;
import com.yxld.yxchuangxin.entity.OpenDoorCode;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.util.Map;

public class DoorControllerImpl implements DoorController{

	/** 解析对象*/
	private Gson gson = new Gson();

	@Override
	public void GetYEZHUDoorCODE(RequestQueue mRequestQueue,final Object[] parm,final  ResultListener<OpenDoorCode> listener) {
		Log.d("geek","GetYEZHUDoorCODE姓名编码后为"+parm[0]);
		GsonRequest<OpenDoorCode> gsonRequest = new GsonRequest<OpenDoorCode>(String.format(URL_GET_YEZHUOPENCODE, parm), OpenDoorCode.class, new Response.Listener<OpenDoorCode>() {

			@Override
			public void onResponse(OpenDoorCode response) {
				if (listener != null) {
					listener.onResponse(response);
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if (listener != null) {
					listener.onErrorResponse(error.getMessage());
				}
			}
		});
		gsonRequest.setShouldCache(true);
		gsonRequest.setTag(URL_GET_YEZHUOPENCODE);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void GetFangKeDoorCODE(RequestQueue mRequestQueue, final Object[] parm,final ResultListener<OpenDoorCode> listener) {
		Log.d("geek","GetFangKeDoorCODE姓名编码后为"+parm[0]);
		GsonRequest<OpenDoorCode> gsonRequest = new GsonRequest<OpenDoorCode>(String.format(URL_GET_FangKeOPENCODE, parm), OpenDoorCode.class, new Response.Listener<OpenDoorCode>() {

			@Override
			public void onResponse(OpenDoorCode response) {
				if (listener != null) {
					listener.onResponse(response);
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if (listener != null) {
					listener.onErrorResponse(error.getMessage());
				}
			}
		});
		gsonRequest.setShouldCache(true);
		gsonRequest.setTag(URL_GET_FangKeOPENCODE);
		mRequestQueue.add(gsonRequest);
	}


}
