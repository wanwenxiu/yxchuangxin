package com.yxld.yxchuangxin.controller.impl;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.yxld.yxchuangxin.controller.AppVersionController;
import com.yxld.yxchuangxin.entity.CxwyAppVersion;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: AppVersionControllerImpl 
 * @Description: 获取版本信息实现类 
 * @author wwx
 * @date 2016年5月5日 下午3:08:22 
 */
public class AppVersionControllerImpl implements AppVersionController {

	@Override
	public void getAppVersionInfo(RequestQueue mRequestQueue, Object[] parm,
			final ResultListener<CxwyAppVersion> listener) {
		GsonRequest<CxwyAppVersion> gsonRequest = new GsonRequest<CxwyAppVersion>(String.format(URL_APP_GETVERSION, parm), CxwyAppVersion.class, new Listener<CxwyAppVersion>() {

			@Override
			public void onResponse(CxwyAppVersion response) {
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
		gsonRequest.setTag(URL_APP_GETVERSION);
		mRequestQueue.add(gsonRequest);
	}

}
