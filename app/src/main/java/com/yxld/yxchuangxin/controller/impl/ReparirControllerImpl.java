package com.yxld.yxchuangxin.controller.impl;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.controller.RepairController;
import com.yxld.yxchuangxin.entity.CxwyBaoxiu;
import com.yxld.yxchuangxin.entity.CxwyWxxiangmu;
import com.yxld.yxchuangxin.entity.RepairList;
import com.yxld.yxchuangxin.entity.RepairLoupan;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

public class ReparirControllerImpl implements RepairController {

	/** 解析对象 */
	private Gson gson = new Gson();

	@Override
	public void getProject(RequestQueue mRequestQueue,
			final ResultListener<RepairList> listener) {
		GsonRequest<RepairList> gsonRequest = new GsonRequest<RepairList>(
				URL_GET_ALL_COMPLAINT, RepairList.class,
				new Listener<RepairList>() {

					@Override
					public void onResponse(RepairList response) {
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

//	@Override
//	public void getRepairSubmit(RequestQueue mRequestQueue,
//			final Map<String, String> parm,
//			final ResultListener<BaseEntity> listener) {
//		StringRequest stringRequest = new StringRequest(Method.POST,
//				URL_GET_ALL_PUBLIC_SUBMIT, new Response.Listener<String>() {
//
//					@Override
//					public void onResponse(String response) {
//						Log.d("geek", "测试response" + response);
//						BaseEntity info = new BaseEntity();
//						if (response != null) {
//							JSONObject jsonObject;
//							try {
//								jsonObject = new JSONObject(response);
//								Log.d("geek",
//										"测试jsonObject" + jsonObject.toString());
//								// 获取状态码
//								info.status = jsonObject.getInt("status");
//								// 获取详细信息
//								info.MSG = jsonObject.getString("MSG");
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//						}
//						if (listener != null) {
//							listener.onResponse(info);
//						}
//					}
//				}, new Response.ErrorListener() {
//
//					@Override
//					public void onErrorResponse(VolleyError error) {
//						if (listener != null) {
//							listener.onErrorResponse(error.getMessage());
//						}
//					}
//				}) {
//			@Override
//			protected Map<String, String> getParams() throws AuthFailureError {
//				return parm;
//			}
//		};
//		stringRequest.setTag(URL_GET_ALL_PUBLIC_SUBMIT);
//		mRequestQueue.add(stringRequest);
//	}

	@Override
	public void getRepairLoupan(RequestQueue mRequestQueue, Object[] parm,
			final ResultListener<RepairLoupan> listener) {
		GsonRequest<RepairLoupan> gsonRequest = new GsonRequest<RepairLoupan>(
				String.format(URL_GET_ALL_PRIVARE_LOUPAN, parm),
				RepairLoupan.class, new Listener<RepairLoupan>() {

					@Override
					public void onResponse(RepairLoupan response) {
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
		gsonRequest.setTag(URL_GET_ALL_PRIVARE_LOUPAN);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getRepairFanghao(RequestQueue mRequestQueue, Object[] parm,
			final ResultListener<RepairLoupan> listener) {
		GsonRequest<RepairLoupan> gsonRequest = new GsonRequest<RepairLoupan>(
				String.format(URL_GET_ALL_PRIVARE_DANYUAN, parm),
				RepairLoupan.class, new Listener<RepairLoupan>() {

					@Override
					public void onResponse(RepairLoupan response) {
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
		gsonRequest.setTag(URL_GET_ALL_PRIVARE_DANYUAN);
		mRequestQueue.add(gsonRequest);
		
	}
	
	
	@Override
	public void getRepairPSubmit(RequestQueue mRequestQueue,
			final Map<String, String> parm,
			final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest = new StringRequest(Method.POST,
				URL_GET_ALL_PRIVATE_SUBMIT, new Response.Listener<String>() {

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
		stringRequest.setTag(URL_GET_ALL_PRIVATE_SUBMIT);
		mRequestQueue.add(stringRequest);
	}

	@Override
	public void getRepairXiangmu(RequestQueue mRequestQueue,
			final ResultListener<CxwyWxxiangmu> listener) {
		GsonRequest<CxwyWxxiangmu> gsonRequest = new GsonRequest<CxwyWxxiangmu>(
				URL_REPAIR_XIANGMU, CxwyWxxiangmu.class,
				new Listener<CxwyWxxiangmu>() {

					@Override
					public void onResponse(CxwyWxxiangmu response) {
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
		gsonRequest.setTag(URL_REPAIR_XIANGMU);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getRepairAllList(RequestQueue mRequestQueue,
			final Map<String, String> parm, final ResultListener<CxwyBaoxiu> listener) {
		StringRequest stringRequest = new StringRequest(Method.POST,
				URL_REPAIR_ALL, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Logger.d("getRepairAllList ");
						CxwyBaoxiu info = null;
						if(response != null){
							Log.d("geek","所有报修信息"+response );
							info = gson.fromJson(response, CxwyBaoxiu.class);
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
		stringRequest.setTag(URL_REPAIR_ALL);
		mRequestQueue.add(stringRequest);
	}

	@Override
	public void getRepairOtherList(RequestQueue mRequestQueue,
			final Map<String, String> parm, final ResultListener<CxwyBaoxiu> listener) {
		StringRequest stringRequest = new StringRequest(Method.POST,
				URL_REPAIR_OTHER, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","其他报修信息"+response );
						CxwyBaoxiu info = null;
						if(response != null){
							info = gson.fromJson(response, CxwyBaoxiu.class);
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
		stringRequest.setTag(URL_REPAIR_OTHER);
		mRequestQueue.add(stringRequest);
	}
}
