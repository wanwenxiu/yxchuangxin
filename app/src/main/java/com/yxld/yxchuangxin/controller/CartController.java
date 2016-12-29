package com.yxld.yxchuangxin.controller;

import java.util.Map;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.entity.CxwyMallCart;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: CartController 
 * @Description: 购物车接口类
 * @author wwx
 * @date 2016年3月21日 上午11:59:26 
 */
public interface CartController extends API{
	
	/**
	 * @Title: addInfoToCart 
	 * @Description: 添加商品至购物车
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void addInfoToCart(RequestQueue mRequestQueue, Map<String, String> parm,
					   final ResultListener<BaseEntity> listener);
	
	/**
	 * @Title: getCartInfoFromUserID 
	 * @Description: 根据用户ID获取
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getCartInfoFromUserID(RequestQueue mRequestQueue, Object[] parm, ResultListener<CxwyMallCart> listener);

	
	/**
	 * @Title: getCartInfoFromUserID 
	 * @Description: 根据用户ID获取
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void updateCartInfoFromID(RequestQueue mRequestQueue, Object[] parm, ResultListener<BaseEntity> listener);

	/**
	 * @Title: deleteInfoToCart
	 * @Description: 删除商品至购物车
	 * @param mRequestQueue
	 * @param listener
	 * @return void
	 * @throws
	 */
	void deleteInfoToCart(RequestQueue mRequestQueue, Object[] parm, ResultListener<BaseEntity> listener);
}
