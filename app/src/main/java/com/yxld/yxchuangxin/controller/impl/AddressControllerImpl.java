package com.yxld.yxchuangxin.controller.impl;

import java.util.Map;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.controller.AddressController;
import com.yxld.yxchuangxin.entity.CxwyMallAdd;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: AddressControllerImpl 
 * @Description: 收获地址接口实现类 
 * @author wwx
 * @date 2016年4月5日 上午11:52:40 
 */
public class AddressControllerImpl implements AddressController {
	private Gson gson = new Gson();

	@Override
	public void addAddress(RequestQueue mRequestQueue,
			final Map<String, String> params, final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,URL_ADD_ADDRESS,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","新增收获地址"+response );
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
				return params;
			}
		};
		//stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		stringRequest.setTag(URL_ADD_ADDRESS);
		mRequestQueue.add(stringRequest);
	}

	@Override
	public void getAddressListFromID(RequestQueue mRequestQueue, Object[] parm,
			final ResultListener<CxwyMallAdd> listener) {
		GsonRequest<CxwyMallAdd> gsonRequest = new GsonRequest<CxwyMallAdd>(String.format(URL_GET_ADDRESS_LIST_FROM_USER, parm), CxwyMallAdd.class, new Listener<CxwyMallAdd>() {

			@Override
			public void onResponse(CxwyMallAdd response) {
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
		gsonRequest.setShouldCache(true);
		gsonRequest.setTag(URL_GET_ADDRESS_LIST_FROM_USER);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void deleteAddressFromID(RequestQueue mRequestQueue, Object[] parm,
			final ResultListener<BaseEntity> listener) {
		GsonRequest<CxwyMallAdd> gsonRequest = new GsonRequest<CxwyMallAdd>(String.format(URL_DELETE_ADDRESS_FROM_ID, parm), CxwyMallAdd.class, new Listener<CxwyMallAdd>() {

			@Override
			public void onResponse(CxwyMallAdd response) {
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
		gsonRequest.setShouldCache(true);
		gsonRequest.setTag(URL_DELETE_ADDRESS_FROM_ID);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void defuleAddressFromID(RequestQueue mRequestQueue, Object[] parm,
			final ResultListener<BaseEntity> listener) {
		GsonRequest<CxwyMallAdd> gsonRequest = new GsonRequest<CxwyMallAdd>(String.format(URL_DEFULE_ADDRESS_FROM_ID, parm), CxwyMallAdd.class, new Listener<CxwyMallAdd>() {

			@Override
			public void onResponse(CxwyMallAdd response) {
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
		gsonRequest.setShouldCache(true);
		gsonRequest.setTag(URL_DEFULE_ADDRESS_FROM_ID);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void updateAddress(RequestQueue mRequestQueue,
			final Map<String, String> params, final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,URL_UPDATE_ADDRESS,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","修改收获地址"+response );
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
				return params;
			}
		};
		stringRequest.setTag(URL_UPDATE_ADDRESS);
		mRequestQueue.add(stringRequest);	
	}

}
