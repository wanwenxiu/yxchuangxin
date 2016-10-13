package com.yxld.yxchuangxin.controller.impl;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.controller.TongzhiController;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: TongzhiControllerImpl
 * @Description: 获取通知实现类
 * @author wwx
 * @date 2016年11月3日 下午17:08:22
 */
public class TongzhiControllerImpl implements TongzhiController {


	@Override
	public void getAppTongzhiInfo(RequestQueue mRequestQueue, Object[] parm,final ResultListener<BaseEntity> listener) {
		GsonRequest<BaseEntity> gsonRequest = new GsonRequest<BaseEntity>(String.format(URL_GET_NEWMAINTONGZHI, parm), BaseEntity.class, new Listener<BaseEntity>() {

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
		gsonRequest.setTag(URL_GET_NEWMAINTONGZHI);
		mRequestQueue.add(gsonRequest);
	}
}
