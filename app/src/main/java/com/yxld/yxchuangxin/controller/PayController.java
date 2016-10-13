package com.yxld.yxchuangxin.controller;

import java.util.Map;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.listener.ResultListener;

public interface PayController extends API{
	
	/**
	 * @Title: getWuyePay 
	 * @Description: 物业缴费支付
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getWuyePay(RequestQueue mRequestQueue, Map<String, String> parm,
					final ResultListener<BaseEntity> listener);
}
