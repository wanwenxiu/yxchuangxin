package com.yxld.yxchuangxin.controller;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.util.Map;

/**
 * @ClassName: TongzhiController
 * @Description: 获取通知信息
 * @author wwx
 * @date 2016年11月3日 下午17:06:18
 */
public interface TongzhiController extends API{
	
	/**
	 * @Title: getAppTongzhiInfo
	 * @Description: 获取通知
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getAppTongzhiInfo(RequestQueue mRequestQueue, final Map<String, String> parm, ResultListener<BaseEntity> listener);

}
