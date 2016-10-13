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
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.controller.PraiseController;
import com.yxld.yxchuangxin.entity.CxwyMallComment;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: GoodsControllerImpl 
 * @Description: 商品接口实现类 
 * @author wwx
 * @date 2016年3月11日 下午4:26:16 
 */


public class PraiseControllerImpl implements PraiseController{
	
	/** 解析对象*/
	private Gson gson = new Gson();

	@Override
	public void getPraiseGoodsFromUser(RequestQueue mRequestQueue,
			final Map<String, String> params, final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest =new StringRequest(
				Method.POST,URL_PRAISE_GOODS_FROM_USER,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("geek","提交评论response"+ response);
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
		stringRequest.setTag(URL_PRAISE_GOODS_FROM_USER);
		mRequestQueue.add(stringRequest);
	}

	@Override
	public void getPraiseListFromGoodsID(RequestQueue mRequestQueue,
			Object[] parm, final ResultListener<CxwyMallComment> listener) {
		GsonRequest<CxwyMallComment> gsonRequest = new GsonRequest<CxwyMallComment>(String.format(URL_PRAISE_LIST_FROM_GOOD, parm), CxwyMallComment.class, new Listener<CxwyMallComment>() {

			@Override
			public void onResponse(CxwyMallComment response) {
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
		gsonRequest.setTag(URL_PRAISE_LIST_FROM_GOOD);
		mRequestQueue.add(gsonRequest);
	}
}
