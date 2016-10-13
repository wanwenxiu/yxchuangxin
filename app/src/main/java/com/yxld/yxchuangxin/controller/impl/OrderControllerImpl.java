package com.yxld.yxchuangxin.controller.impl;

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
import com.yxld.yxchuangxin.controller.OrderController;
import com.yxld.yxchuangxin.entity.CxwyMallOrder;
import com.yxld.yxchuangxin.entity.CxwyMallSale;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.util.Map;

/**
 * @ClassName: GoodsControllerImpl 
 * @Description: 商品接口实现类 
 * @author wwx
 * @date 2016年3月11日 下午4:26:16 
 */
public class OrderControllerImpl implements OrderController{

	/** 解析对象*/
	private Gson gson = new Gson();

	@Override
	public void addOrder(RequestQueue mRequestQueue, final Map<String, String> params,
			final ResultListener<BaseEntity> listener) {
		Log.d("geek", "请求添加订单操作addOrder（）");
		StringRequest stringRequest =new StringRequest(Method.POST,URL_ADD_ORDER,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","提交订单response ="+response);
						BaseEntity info = null;
						if(response != null){
							info = gson.fromJson(response, BaseEntity.class);
						}
						if (listener != null) {
							listener.onResponse(info);
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("geek", "onErrorResponse error"+error.toString());
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
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		stringRequest.setTag(URL_ADD_ORDER);
		mRequestQueue.add(stringRequest);			
	}

	@Override
	public void getOrderListOrder(RequestQueue mRequestQueue,
			final Map<String, String> params, final ResultListener<CxwyMallOrder> listener) {
		StringRequest stringRequest =new StringRequest(Method.POST,URL_GET_ORDER_LIST_FROM_USER,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","提交订单response ="+response);
						CxwyMallOrder info = null;
						if(response != null){
							info = gson.fromJson(response, CxwyMallOrder.class);
						}
						if (listener != null) {
							listener.onResponse(info);
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("geek", "onErrorResponse error"+error.toString());
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
		stringRequest.setTag(URL_GET_ORDER_LIST_FROM_USER);
		mRequestQueue.add(stringRequest);			
	}

	@Override
	public void updateOrderState(RequestQueue mRequestQueue,
			final Map<String, String> params, final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest =new StringRequest(Method.POST,URL_UPDATE_ORDER_STATE_FROM_ID,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","提交订单response ="+response);
						BaseEntity info = null;
						if(response != null){
							info = gson.fromJson(response, BaseEntity.class);
						}
						if (listener != null) {
							listener.onResponse(info);
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("geek", "onErrorResponse error"+error.toString());
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
		stringRequest.setTag(URL_UPDATE_ORDER_STATE_FROM_ID);
		mRequestQueue.add(stringRequest);	
	}

	@Override
	public void TuiKuanOrderState(RequestQueue mRequestQueue,final Map<String, String> params,final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest =new StringRequest(Method.POST,URL_TUIKUAN_ORDER_STATE_FROM_ID,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","提交订单response ="+response);
						BaseEntity info = null;
						if(response != null){
							info = gson.fromJson(response, BaseEntity.class);
						}
						if (listener != null) {
							listener.onResponse(info);
						}
					}
				}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("geek", "onErrorResponse error"+error.toString());
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
		stringRequest.setTag(URL_TUIKUAN_ORDER_STATE_FROM_ID);
		mRequestQueue.add(stringRequest);
	}

	@Override
	public void getOrderDestailFromID(RequestQueue mRequestQueue,
			Object[] parm, final ResultListener<CxwyMallSale> listener) {
		GsonRequest<CxwyMallSale> gsonRequest = new GsonRequest<CxwyMallSale>(String.format(URL_GET_ORDER_DESTAIL_FROM_ID, parm), CxwyMallSale.class, new Listener<CxwyMallSale>() {

			@Override
			public void onResponse(CxwyMallSale response) {
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
		gsonRequest.setTag(URL_GET_ORDER_DESTAIL_FROM_ID);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void YuEPayOrderFromID(RequestQueue mRequestQueue, Object[] parm, final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(
				String.format(URL_YU_E_PAY_ORDER_STATE_FROM_ID, parm),
				BaseEntity.class, new Listener<BaseEntity>() {

			@Override
			public void onResponse(BaseEntity response) {
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
		gsonRequest.setTag(URL_YU_E_PAY_ORDER_STATE_FROM_ID);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getOrderKuncunFromID(RequestQueue mRequestQueue, Object[] parm,final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(
				String.format(URL_GET_ORDER_KUNCUN_FROM_ID, parm),
				BaseEntity.class, new Listener<BaseEntity>() {

			@Override
			public void onResponse(BaseEntity response) {
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
		gsonRequest.setTag(URL_GET_ORDER_KUNCUN_FROM_ID);
		mRequestQueue.add(gsonRequest);
	}

}
