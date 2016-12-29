package com.yxld.yxchuangxin.controller.impl;

import java.util.Map;

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
import com.yxld.yxchuangxin.controller.CartController;
import com.yxld.yxchuangxin.entity.CxwyMallCart;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: GoodsControllerImpl 
 * @Description: 商品接口实现类 
 * @author wwx
 * @date 2016年3月11日 下午4:26:16 
 */


public class CartControllerImpl implements CartController{
	
	/** 解析对象*/
	private Gson gson = new Gson();

	@Override
	public void addInfoToCart(RequestQueue mRequestQueue,
			final Map<String, String> params, final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,URL_ADD_INFO_TO_CART,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","加入"+response );
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
		stringRequest.setTag(URL_ADD_INFO_TO_CART);
		mRequestQueue.add(stringRequest);		
	}
	
	@Override
	public void getCartInfoFromUserID(RequestQueue mRequestQueue,
			Object[] parm, final ResultListener<CxwyMallCart> listener) {
		GsonRequest<CxwyMallCart> gsonRequest = new GsonRequest<CxwyMallCart>(String.format(URL_GET_INFO__CART_FROM_USERID, parm), CxwyMallCart.class, new Listener<CxwyMallCart>() {

			@Override
			public void onResponse(CxwyMallCart response) {
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
		gsonRequest.setTag(URL_GET_INFO__CART_FROM_USERID);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void updateCartInfoFromID(RequestQueue mRequestQueue, Object[] parm,
			final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(String.format(URL_UPDATE_INFO__CART_FROM_ID, parm), BaseEntity.class, new Listener<BaseEntity>() {

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
		gsonRequest.setTag(URL_UPDATE_INFO__CART_FROM_ID);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void deleteInfoToCart(RequestQueue mRequestQueue, Object[] parm,final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(String.format(URL_DELETE_INFO__CART_FROM_ID, parm), BaseEntity.class, new Listener<BaseEntity>() {

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
		gsonRequest.setTag(URL_DELETE_INFO__CART_FROM_ID);
		mRequestQueue.add(gsonRequest);
	}

}
