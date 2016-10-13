package com.yxld.yxchuangxin.controller;

import java.util.Map;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.entity.Household;
import com.yxld.yxchuangxin.listener.ResultListener;

public interface ComplaintController extends API{
	/*
	 * 获取投诉项目
	 */
	void getHouseHold(RequestQueue mRequestQueue, ResultListener<Household> listener);
	
	/**
	 * @Title: getComplaintSubmit 
	 * @Description: 提交所有投诉数据
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getComplaintSubmit(RequestQueue mRequestQueue, Map<String, String> parm,
							final ResultListener<BaseEntity> listener);
}
