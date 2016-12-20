package com.yxld.yxchuangxin.controller.impl;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.controller.LoginController;
import com.yxld.yxchuangxin.entity.LoginEntity;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

public class LoginControllerImpl implements LoginController {

	@Override
	public void getRegister(RequestQueue mRequestQueue, Object[] parm,
			final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(
				String.format(URL_GET_ALL_REGISTER, parm), BaseEntity.class,
				new Listener<BaseEntity>() {

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
		gsonRequest.setTag(URL_GET_ALL_REGISTER);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getRegisterAlready(RequestQueue mRequestQueue, Object[] parm,
			final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(
				String.format(URL_GET_ALL_REGISTER_ALREADY, parm),
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
		gsonRequest.setTag(URL_GET_ALL_REGISTER_ALREADY);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getLogin(RequestQueue mRequestQueue, Object[] parm,
			final ResultListener<LoginEntity> listener) {
		GsonRequest<LoginEntity> gsonRequest = new GsonRequest<LoginEntity>(
				String.format(URL_GET_ALL_LOGIN, parm), LoginEntity.class,
				new Listener<LoginEntity>() {

					@Override
					public void onResponse(LoginEntity response) {
						Log.d("2222",23424342+"0");
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
		gsonRequest.setTag(URL_GET_ALL_LOGIN);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getUpdatePwd(RequestQueue mRequestQueue, Object[] parm,
			final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(
				String.format(URL_GET_ALL_UPDATE_PWD, parm), BaseEntity.class,
				new Listener<BaseEntity>() {

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
		gsonRequest.setTag(URL_GET_ALL_UPDATE_PWD);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getUpdateName(RequestQueue mRequestQueue, final Map<String, String> parm,
			final ResultListener<BaseEntity> listener) {
		StringRequest stringRequest = new StringRequest(Method.POST,
				URL_GET_ALL_UPDATE_NAME, new Response.Listener<String>() {

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
		stringRequest.setTag(URL_GET_ALL_UPDATE_NAME);
		mRequestQueue.add(stringRequest);
}

	@Override
	public void getUpdateCard(RequestQueue mRequestQueue, Object[] parm,
			final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(
				String.format(URL_GET_ALL_UPDATE_CARD, parm), BaseEntity.class,
				new Listener<BaseEntity>() {

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
		gsonRequest.setTag(URL_GET_ALL_UPDATE_CARD);
		mRequestQueue.add(gsonRequest);		
	}

	@Override
	public void getExistShouji(RequestQueue mRequestQueue, Object[] parm,final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(
				String.format(URL_GET_EXIST_SHOUJI, parm), BaseEntity.class,
				new Listener<BaseEntity>() {

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
		gsonRequest.setTag(URL_GET_EXIST_SHOUJI);
		mRequestQueue.add(gsonRequest);
	}

	@Override
	public void getFindPwd(RequestQueue mRequestQueue, Object[] parm, final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(
				String.format(URL_GET_FIND_PWD, parm), BaseEntity.class,
				new Listener<BaseEntity>() {

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
		gsonRequest.setTag(URL_GET_FIND_PWD);
		mRequestQueue.add(gsonRequest);
	}


}
