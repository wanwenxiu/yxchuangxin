package com.yxld.yxchuangxin.controller;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.entity.CxwyMallPezhi;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: PeiZhiController
 * @Description: 配置接口类
 * @author wwx
 * @date 2016年7月21日 下午4:06:27
 */
public interface PeiZhiController extends API{
	

	/**
	 * @Title: getAllScLbTbList
	 * @Description: 获取商城图标和轮播集合
	 * @param mRequestQueue
	 * @param listener
	 * @return void
	 * @throws
	 */
	void getAllScLbTbList(RequestQueue mRequestQueue, Object[] parm, ResultListener<CxwyMallPezhi> listener);


	/**
	 * @Title: getAllMainLbList
	 * @Description: 获取首页轮播集合
	 * @param mRequestQueue
	 * @param listener
	 * @return void
	 * @throws
	 */
	void getAllMainLbList(RequestQueue mRequestQueue, Object[] parm, ResultListener<CxwyMallPezhi> listener);


}
