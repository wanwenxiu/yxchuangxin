package com.yxld.yxchuangxin.controller;

import java.util.Map;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.entity.CxwyBaoxiu;
import com.yxld.yxchuangxin.entity.CxwyWxxiangmu;
import com.yxld.yxchuangxin.entity.QiniuToken;
import com.yxld.yxchuangxin.entity.RepairList;
import com.yxld.yxchuangxin.entity.RepairLoupan;
import com.yxld.yxchuangxin.listener.ResultListener;

public interface RepairController extends API{

	/**
	 * @Title: getAllFirstClassList 
	 * @Description: 获取报修项目
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getProject(RequestQueue mRequestQueue, ResultListener<RepairList> listener);
	
	/**
	 * @Title: getAllFirstClassList 
	 * @Description: 获取报修项目
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getRepairXiangmu(RequestQueue mRequestQueue, ResultListener<CxwyWxxiangmu> listener);
	
//	/**
//	 * @Title: getShopListByKey
//	 * @Description: 提交所有公有数据
//	 * @param mRequestQueue
//	 * @param parm
//	 * @param listener
//	 * @return void
//	 * @throws
//	 */
//	void getRepairSubmit(RequestQueue mRequestQueue, Map<String, String> parm,
//						 final ResultListener<BaseEntity> listener);
	
	/**
	 * @Title: getRepairLoupan 
	 * @Description: 提交楼盘私有数据
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getRepairLoupan(RequestQueue mRequestQueue, Object[] parm, ResultListener<RepairLoupan> listener);
	
	/**
	 * @Title: getRepairLoupan 
	 * @Description: 提交房号私有数据
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getRepairFanghao(RequestQueue mRequestQueue, Object[] parm, ResultListener<RepairLoupan> listener);

	
	/**
	 * @Title: getShopListByKey 
	 * @Description: 提交所有私有数据
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getRepairPSubmit(RequestQueue mRequestQueue, Map<String, String> parm,
						  final ResultListener<BaseEntity> listener);
	
	/**
	 * @Title: getRepairAllList 
	 * @Description:查询所有报修信息
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getRepairAllList(RequestQueue mRequestQueue, Map<String, String> parm,
						  final ResultListener<CxwyBaoxiu> listener);
	
	/**
	 * @Title: getRepairOtherList 
	 * @Description:查询其他报修信息
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getRepairOtherList(RequestQueue mRequestQueue, Map<String, String> parm,
							final ResultListener<CxwyBaoxiu> listener);


	/**
	 * @Title: getQiniuToken
	 * @Description: 七牛token
	 * @param mRequestQueue
	 * @param listener
	 * @return void
	 * @throws
	 */
	void getQiniuToken(RequestQueue mRequestQueue, ResultListener<QiniuToken> listener);

}
