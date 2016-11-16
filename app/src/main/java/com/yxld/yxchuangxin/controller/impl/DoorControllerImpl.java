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
import com.yxld.yxchuangxin.entity.Door;
import com.yxld.yxchuangxin.entity.OpenDoorCode;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.util.Map;

public class DoorControllerImpl implements DoorController{


	/** 解析对象*/
	private Gson gson = new Gson();

	@Override
	public void GetDoorList(RequestQueue mRequestQueue,
			final Map<String, String> params, final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,URL_GET_DOORLISTBY,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						//Log.d("geek","GetDoorList response"+response.toString());
						BaseEntity info = null;
						//Log.d("geek","GetDoorList response"+response.toString());
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
				return params;
			}
		};
		stringRequest.setTag(URL_GET_DOORLISTBY);
		mRequestQueue.add(stringRequest);
	}

	@Override
	public void GetOPENDoorList(RequestQueue mRequestQueue,final  Map<String, String> params,final ResultListener<OpenDoorCode> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,URL_GET_OPENDOORCODE,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						OpenDoorCode info = null;
						Log.d("geek","GetOPENDoorList response"+response.toString());
						if(response != null){
							info = gson.fromJson(response, OpenDoorCode.class);
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
				return params;
			}
		};
		stringRequest.setTag(URL_GET_OPENDOORCODE);
		mRequestQueue.add(stringRequest);
	}

	@Override
	public void GetYEZHUDoorCODE(RequestQueue mRequestQueue, final Map<String, String> params,final  ResultListener<OpenDoorCode> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,URL_GET_YEZHUOPENCODE,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						OpenDoorCode info = null;
						Log.d("geek","GetYEZHUDoorCODE response"+response.toString());
						if(response != null){
							info = gson.fromJson(response, OpenDoorCode.class);
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
				return params;
			}
		};
		stringRequest.setTag(URL_GET_YEZHUOPENCODE);
		mRequestQueue.add(stringRequest);
	}


	@Override
	public void GetCodeRecordList(RequestQueue mRequestQueue, Object[] parm,final ResultListener<BarcodedataAndroid> listener) {
		GsonRequest<BarcodedataAndroid> gsonRequest = new GsonRequest<BarcodedataAndroid>(String.format(URL_GET_CODERECORD, parm),
				BarcodedataAndroid.class, new Response.Listener<BarcodedataAndroid>(){

			@Override
			public void onResponse(BarcodedataAndroid response) {
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
		gsonRequest.setTag(URL_GET_CODERECORD);
		mRequestQueue.add(gsonRequest);
	}

}
