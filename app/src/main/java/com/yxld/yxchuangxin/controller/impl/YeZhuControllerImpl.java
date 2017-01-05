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
import com.yxld.yxchuangxin.entity.AppWuYeFei;
import com.yxld.yxchuangxin.entity.AppYezhuFangwu;
import com.yxld.yxchuangxin.entity.BaseEntity2;
import com.yxld.yxchuangxin.entity.CxwyJfWyRecord;
import com.yxld.yxchuangxin.entity.CxwyMallUseDaijinquan;
import com.yxld.yxchuangxin.entity.CxwyMallUser;
import com.yxld.yxchuangxin.entity.CxwyMallUserBalance;
import com.yxld.yxchuangxin.entity.CxwyYezhu;
import com.yxld.yxchuangxin.entity.WuyeRecordAndroid;
import com.yxld.yxchuangxin.entity.WyFwApp;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

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
	public void getAllChengyuanList(RequestQueue mRequestQueue, Object[] parm,final ResultListener<AppYezhuFangwu> listener) {
		GsonRequest<AppYezhuFangwu> gsonRequest = new GsonRequest<AppYezhuFangwu>(String.format(URL_findall_chengyuan, parm), AppYezhuFangwu.class, new Response.Listener<AppYezhuFangwu>() {

			@Override
			public void onResponse(AppYezhuFangwu response) {
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
	public void addChengyuan(RequestQueue mRequestQueue, final Map<String, String> params,final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest = new StringRequest(Method.POST,
				URL_add_chengyuan, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("geek", "添加成员测试response" + response);
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
				return params;
			}
		};
		stringRequest.setTag(URL_add_chengyuan);
		mRequestQueue.add(stringRequest);
	}

	@Override
	public void getAllChongzhi(RequestQueue mRequestQueue, String url,
							   final Map<String, String> params, final ResultListener<BaseEntity2> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						BaseEntity2 info = null;
						if(response != null){
							info = gson.fromJson(response, BaseEntity2.class);
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
				Method.POST,url,
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
				Method.POST,url,
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
				Method.POST,url,
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
				Method.POST,url,
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


	@Override
	public void getHouse(RequestQueue mRequestQueue, Object[] parm,final ResultListener<WyFwApp> listener) {
		GsonRequest<WyFwApp> gsonRequest = new GsonRequest<WyFwApp>(String.format(URL_HOUSE, parm), WyFwApp.class, new Response.Listener<WyFwApp>() {

			@Override
			public void onResponse(WyFwApp response) {
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
		gsonRequest.setTag(URL_HOUSE);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getWUYE(RequestQueue mRequestQueue, Object[] parm,final ResultListener<CxwyJfWyRecord> listener) {
		GsonRequest<CxwyJfWyRecord> gsonRequest = new GsonRequest<CxwyJfWyRecord>(String.format(URL_PAYMENT_RECORDS_WUYE, parm), CxwyJfWyRecord.class, new Response.Listener<CxwyJfWyRecord>() {

			@Override
			public void onResponse(CxwyJfWyRecord response) {
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
		gsonRequest.setTag(URL_PAYMENT_RECORDS_WUYE);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getDETAIL(RequestQueue mRequestQueue, Object[] parm,final ResultListener<AppWuYeFei> listener) {
		GsonRequest<AppWuYeFei> gsonRequest = new GsonRequest<AppWuYeFei>(String.format(URL_DETAIL, parm), AppWuYeFei.class, new Response.Listener<AppWuYeFei>() {

			@Override
			public void onResponse(AppWuYeFei response) {
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
		gsonRequest.setTag(URL_DETAIL);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getManYiDuTiaoChaExist(RequestQueue mRequestQueue, Object[] parm,final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(String.format(URL_GET_MANYIDUTIAOCHAEXIST, parm), BaseEntity.class, new Response.Listener<BaseEntity>() {

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
		gsonRequest.setTag(URL_GET_MANYIDUTIAOCHAEXIST);
		mRequestQueue.add(gsonRequest);
	}


}
