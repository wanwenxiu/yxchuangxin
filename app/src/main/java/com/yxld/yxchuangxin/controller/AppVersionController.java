package com.yxld.yxchuangxin.controller;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.entity.CxwyAppVersion;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: AppVersionController 
 * @Description: 获取版本信息
 * @author wwx
 * @date 2016年5月5日 下午3:06:18 
 *
 */
public interface AppVersionController extends API{
	
	/**
	 * @Title: deleteAddressFromID 
	 * @Description: 删除地址
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getAppVersionInfo(RequestQueue mRequestQueue, Object[] parm, ResultListener<CxwyAppVersion> listener);

}
