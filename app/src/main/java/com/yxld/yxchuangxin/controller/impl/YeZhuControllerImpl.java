package com.yxld.yxchuangxin.controller.impl;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.entity.Base1Entity;
import com.yxld.yxchuangxin.entity.CxwyMallUseDaijinquan;
import com.yxld.yxchuangxin.entity.CxwyMallUser;
import com.yxld.yxchuangxin.entity.CxwyMallUserBalance;
import com.yxld.yxchuangxin.entity.CxwyYezhu;
import com.yxld.yxchuangxin.entity.WuyeRecordAndroid;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.util.Map;

/**
 * @ClassName: YeZhuControllerImpl 
 * @Description: 业主实现类 
 * @author wwx
 * @date 2016年3月11日 下午4:26:16 
 */

public class YeZhuControllerImpl implements YeZhuController{


	/** 解析对象*/
	private Gson gson = new Gson();

	@Override
	public void getYeZhuWuYeList(RequestQueue mRequestQueue,String url,
			final Map<String, String> params, final ResultListener<WuyeRecordAndroid> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","欠费列表"+response );
						WuyeRecordAndroid info = null;
						if(response != null){
							info = gson.fromJson(response, WuyeRecordAndroid.class);
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
		stringRequest.setTag(url);
		mRequestQueue.add(stringRequest);				
	}

	@Override
	public void getAllPaymentRecords(RequestQueue mRequestQueue, String url,
			final Map<String, String> params, final ResultListener<WuyeRecordAndroid> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","获取用户缴费欠费列表"+response );
						WuyeRecordAndroid info = null;
						if(response != null){
							info = gson.fromJson(response, WuyeRecordAndroid.class);
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
		stringRequest.setTag(url);
		mRequestQueue.add(stringRequest);	
	}

	@Override
	public void getAllChengyuanList(RequestQueue mRequestQueue, Object[] parm,final ResultListener<CxwyYezhu> listener) {
		GsonRequest<CxwyYezhu> gsonRequest = new GsonRequest<CxwyYezhu>(String.format(URL_findall_chengyuan, parm), CxwyYezhu.class, new Response.Listener<CxwyYezhu>() {

			@Override
			public void onResponse(CxwyYezhu response) {
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
		gsonRequest.setTag(URL_findall_chengyuan);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getDeleteChengyuanList(RequestQueue mRequestQueue, Object[] parm,final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(String.format(URL_delete_chengyuan, parm), BaseEntity.class, new Response.Listener<BaseEntity>() {

			@Override
			public void onResponse(BaseEntity response) {
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
		gsonRequest.setTag(URL_delete_chengyuan);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void addChengyuan(RequestQueue mRequestQueue, String url,final Map<String, String> params,final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","获取用户缴费欠费列表"+response );
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
		stringRequest.setTag(url);
		mRequestQueue.add(stringRequest);
	}

	@Override
	public void getAllChongzhi(RequestQueue mRequestQueue, String url,
							   final Map<String, String> params, final ResultListener<Base1Entity> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Base1Entity info = null;
						if(response != null){
							info = gson.fromJson(response, Base1Entity.class);
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
		stringRequest.setTag(url);
		mRequestQueue.add(stringRequest);
	}


	@Override
	public void getAllYue(RequestQueue mRequestQueue, String url,
							   final Map<String, String> params, final ResultListener<CxwyMallUser> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						CxwyMallUser info = null;
						if(response != null){
							info = gson.fromJson(response, CxwyMallUser.class);
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
		stringRequest.setTag(url);
		mRequestQueue.add(stringRequest);
	}

	public void getAllYHQ(RequestQueue mRequestQueue, String url, final Map<String, String> parm, final ResultListener<CxwyMallUseDaijinquan> listener) {
		StringRequest stringRequest =new StringRequest(
				Request.Method.POST,url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						CxwyMallUseDaijinquan info = null;
						if(response != null){
							info = gson.fromJson(response, CxwyMallUseDaijinquan.class);
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
		stringRequest.setTag(url);
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		mRequestQueue.add(stringRequest);
	}


	public void getAllNOYHQ(RequestQueue mRequestQueue, String url, final Map<String, String> parm, final ResultListener<CxwyMallUseDaijinquan> listener) {
		StringRequest stringRequest =new StringRequest(
				Request.Method.POST,url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						CxwyMallUseDaijinquan info = null;
						if(response != null){
							info = gson.fromJson(response, CxwyMallUseDaijinquan.class);
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
		stringRequest.setTag(url);
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		mRequestQueue.add(stringRequest);
	}

	public void getAllRecharge(RequestQueue mRequestQueue, String url, final Map<String, String> parm, final ResultListener<CxwyMallUserBalance> listener) {
		StringRequest stringRequest =new StringRequest(
				Request.Method.POST,url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						CxwyMallUserBalance info = null;
						if(response != null){
							info = gson.fromJson(response, CxwyMallUserBalance.class);
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
		stringRequest.setTag(url);
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		mRequestQueue.add(stringRequest);
	}

	public void getAllExpenditure(RequestQueue mRequestQueue, String url, final Map<String, String> parm, final ResultListener<CxwyMallUserBalance> listener) {
		StringRequest stringRequest =new StringRequest(
				Request.Method.POST,url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						CxwyMallUserBalance info = null;
						if(response != null){
							info = gson.fromJson(response, CxwyMallUserBalance.class);
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
		stringRequest.setTag(url);
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		mRequestQueue.add(stringRequest);
	}
}
