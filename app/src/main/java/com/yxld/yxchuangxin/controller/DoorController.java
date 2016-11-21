package com.yxld.yxchuangxin.controller;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.entity.BarcodedataAndroid;
import com.yxld.yxchuangxin.entity.Door;
import com.yxld.yxchuangxin.entity.OpenDoorCode;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.util.Map;

/**
 * 门禁接口实现类
 */
public interface DoorController extends API{




	/**
	 * 获得业主开门二维码
	 * GetYEZHUDoorCODE
	 * @param mRequestQueue
	 * @param parm
	 * @param listener
	 */
	void GetYEZHUDoorCODE(RequestQueue mRequestQueue,Object[] parm,
						  final ResultListener<OpenDoorCode> listener);


	/**
	 * 获得访客开门二维码
	 * GetFangKeDoorCODE
	 * @param mRequestQueue
	 * @param parm
	 * @param listener
	 */
	void GetFangKeDoorCODE(RequestQueue mRequestQueue,Object[] parm,
						  final ResultListener<OpenDoorCode> listener);

}
