package com.yxld.yxchuangxin.controller.impl;

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
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.controller.GoodsController;
import com.yxld.yxchuangxin.entity.FirstClassGoodsInfo;
import com.yxld.yxchuangxin.entity.SecondClassGoodsInfo;
import com.yxld.yxchuangxin.entity.ShopList;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.util.Map;

/**
 * @ClassName: GoodsControllerImpl 
 * @Description: 商品接口实现类 
 * @author wwx
 * @date 2016年3月11日 下午4:26:16 
 */


public class GoodsControllerImpl implements GoodsController{
	
	/** 解析对象*/
	private Gson gson = new Gson();

	@Override
	public void getAllFirstClassList(RequestQueue mRequestQueue,
			final ResultListener<FirstClassGoodsInfo> listener) {
		GsonRequest<FirstClassGoodsInfo> gsonRequest = new GsonRequest<FirstClassGoodsInfo>(
				URL_GET_ALL_ATTENDANCE, FirstClassGoodsInfo.class,
				new Listener<FirstClassGoodsInfo>() {

					@Override
					public void onResponse(FirstClassGoodsInfo response) {
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
		gsonRequest.setTag(URL_GET_ALL_ATTENDANCE);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getAllSecondClassList(RequestQueue mRequestQueue,
			Object[] parm, final ResultListener<SecondClassGoodsInfo> listener) {
		GsonRequest<SecondClassGoodsInfo> gsonRequest = new GsonRequest<SecondClassGoodsInfo>(String.format(URL_GET_ALL_SECONDCLASS, parm), SecondClassGoodsInfo.class, new Listener<SecondClassGoodsInfo>() {

			@Override
			public void onResponse(SecondClassGoodsInfo response) {
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
		gsonRequest.setTag(URL_GET_ALL_SECONDCLASS);
		mRequestQueue.add(gsonRequest);
	}
	
	@Override
	public void getShopListById(RequestQueue mRequestQueue, final Map<String, String> params,
			final ResultListener<ShopList> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,URL_GET_ALL_SHOPLIST_BY_ID,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						ShopList info = null;
						Log.d("geek","getShopListById response"+response.toString());
						if(response != null){
							info = gson.fromJson(response, ShopList.class);
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
		stringRequest.setTag(URL_GET_ALL_SHOPLIST_BY_ID);
		mRequestQueue.add(stringRequest);
	}

	@Override
	public void getShopListByKey(RequestQueue mRequestQueue,final Map<String, String> params,
			final ResultListener<ShopList> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,URL_GET_ALL_SHOPLIST_BY_KEY,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						ShopList info = null;
						Log.d("geek","查询关键字response"+response.toString());
						if(response != null){
							info = gson.fromJson(response, ShopList.class);
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
		stringRequest.setTag(URL_GET_ALL_SHOPLIST_BY_KEY);
		mRequestQueue.add(stringRequest);
	}

	@Override
	public void getCollectGoodsFromId(RequestQueue mRequestQueue,
			final Map<String, String> params, final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,URL_COLLECT_GOODS_FROM_ID,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
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
		stringRequest.setTag(URL_COLLECT_GOODS_FROM_ID);
		mRequestQueue.add(stringRequest);
	}

	@Override
	public void deleteCollectGoodsFromId(RequestQueue mRequestQueue,
			Object[] parm, final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(String.format(URL_DELETE_COLLECT_GOODS_FROM_ID, parm),
				BaseEntity.class, new Listener<BaseEntity>(){

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
		gsonRequest.setTag(URL_DELETE_COLLECT_GOODS_FROM_ID);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getIndexGoodsList(RequestQueue mRequestQueue,final Map<String, String> parm,
			final ResultListener<ShopList> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,URL_INDEX_GOODS_LIST,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						ShopList info = null;
						Log.d("geek","getIndexGoodsList"+response.toString());
						if(response != null){
							info = gson.fromJson(response, ShopList.class);
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
		stringRequest.setTag(URL_INDEX_GOODS_LIST);
		mRequestQueue.add(stringRequest);
	}
	
}
