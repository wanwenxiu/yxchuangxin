package com.yxld.yxchuangxin.controller;

import java.util.Map;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.entity.CxwyMallAdd;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: AddressController 
 * @Description: 地址实现类 
 * @author wwx
 * @date 2016年4月5日 上午11:51:22 
 */
public interface AddressController extends API{
	
	/**
	 * @Title: addAddress 
	 * @Description: 添加地址
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void addAddress(RequestQueue mRequestQueue, Map<String, String> parm,
					final ResultListener<BaseEntity> listener);
	

	/**
	 * @Title: updateAddress 
	 * @Description: 修改地址
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void updateAddress(RequestQueue mRequestQueue, Map<String, String> parm,
					   final ResultListener<BaseEntity> listener);
	
	
	/**
	 * @Title: getAddressListFromID 
	 * @Description: 根据用户获取地址列表
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getAddressListFromID(RequestQueue mRequestQueue, Object[] parm, ResultListener<CxwyMallAdd> listener);
	
	/**
	 * @Title: deleteAddressFromID 
	 * @Description: 删除地址
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void deleteAddressFromID(RequestQueue mRequestQueue, Object[] parm, ResultListener<BaseEntity> listener);
	
	/**
	 * @Title: defuleAddressFromID 
	 * @Description: 设置默认地址
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void defuleAddressFromID(RequestQueue mRequestQueue, Object[] parm, ResultListener<BaseEntity> listener);

}
