package com.yxld.yxchuangxin.controller.impl;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.controller.DoorController;
import com.yxld.yxchuangxin.entity.BarcodedataAndroid;
import com.yxld.yxchuangxin.entity.OpenDoorCode;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.util.Map;

public class DoorControllerImpl implements DoorController{




	@Override
	public void GetYEZHUDoorCODE(RequestQueue mRequestQueue, Object[] parm,final  ResultListener<OpenDoorCode> listener) {
		GsonRequest<OpenDoorCode> gsonRequest = new GsonRequest<OpenDoorCode>(String.format(URL_GET_YEZHUOPENCODE, parm),
				OpenDoorCode.class, new Response.Listener<OpenDoorCode>(){

			@Override
			public void onResponse(OpenDoorCode response) {
				Log.d("geek","业主二维码 onResponse ="+response.toString());
				if (listener != null) {
					listener.onResponse(response);
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("geek","业主二维码 onErrorResponse response ="+error.toString());
				if (listener != null) {
					listener.onErrorResponse(error.getMessage());
				}
			}
		});
		gsonRequest.setTag(URL_GET_YEZHUOPENCODE);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void GetFangKeDoorCODE(RequestQueue mRequestQueue, Object[] parm,final ResultListener<OpenDoorCode> listener) {
		GsonRequest<OpenDoorCode> gsonRequest = new GsonRequest<OpenDoorCode>(String.format(URL_GET_FangKeOPENCODE, parm),
				OpenDoorCode.class, new Response.Listener<OpenDoorCode>(){

			@Override
			public void onResponse(OpenDoorCode response) {
				Log.d("geek","访客二维码 onResponse ="+response.toString());
				if (listener != null) {
					listener.onResponse(response);
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("geek","访客二维码 onErrorResponse response ="+error.toString());
				if (listener != null) {
					listener.onErrorResponse(error.getMessage());
				}
			}
		});
		gsonRequest.setTag(URL_GET_FangKeOPENCODE);
		mRequestQueue.add(gsonRequest);
	}


}
