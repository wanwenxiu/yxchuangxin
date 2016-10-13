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
	 * 获得门禁列表
	 * @param mRequestQueue
	 * @param parm  楼盘、楼栋、单元
	 * @param listener
     */
	void GetDoorList(RequestQueue mRequestQueue, Map<String, String> parm,
					final ResultListener<BaseEntity> listener);

	/**
	 * 获得访客二维码
	 * @param mRequestQueue
	 * @param parm  tring name;
	String tel;
	String houses;
	String dong;
	String machineIP;
	 * @param listener
	 */
	void GetOPENDoorList(RequestQueue mRequestQueue, Map<String, String> parm,
					 final ResultListener<OpenDoorCode> listener);


	/**
	 * 获得业主开门二维码
	 * GetYEZHUDoorCODE
	 * @param mRequestQueue
	 * @param parm
	 * @param listener
     */
	void GetYEZHUDoorCODE(RequestQueue mRequestQueue, Map<String, String> parm,
						 final ResultListener<OpenDoorCode> listener);


	/**
	 * 获取生成二维码记录
	 * @param mRequestQueue
	 * @param parm  业主id
	 * @param listener
     */
	void GetCodeRecordList(RequestQueue mRequestQueue, Object[] parm, ResultListener<BarcodedataAndroid> listener);

}
