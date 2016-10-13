package com.yxld.yxchuangxin.controller;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.entity.CxwyMallOrder;
import com.yxld.yxchuangxin.entity.CxwyMallSale;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.util.Map;

/**
 * @ClassName: OrderController 
 * @Description: 订单接口实现类
 * @author wwx
 * @date 2016年3月26日 下午3:34:40 
 *
 */
public interface OrderController extends API{
	
	/**
	 * @Title: addOrder 
	 * @Description: 添加订单
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void addOrder(RequestQueue mRequestQueue, Map<String, String> parm,
				  final ResultListener<BaseEntity> listener);
	
	/**
	 * @Title: getOrderListOrder 
	 * @Description: 
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getOrderListOrder(RequestQueue mRequestQueue, Map<String, String> parm,
						   final ResultListener<CxwyMallOrder> listener);
	
	/**
	 * @Title: updateOrderState 
	 * @Description: 
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void updateOrderState(RequestQueue mRequestQueue, Map<String, String> parm,
						  final ResultListener<BaseEntity> listener);

	/**
	 * @Title: updateOrderState
	 * @Description:
	 * @param mRequestQueue
	 * @param parm
	 * @param listener
	 * @return void
	 * @throws
	 */
	void TuiKuanOrderState(RequestQueue mRequestQueue, Map<String, String> parm,
						   final ResultListener<BaseEntity> listener);
	
	/**
	 * @Title: getOrderDestailFromID 
	 * @Description: 根据订单ID获取
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getOrderDestailFromID(RequestQueue mRequestQueue, Object[] parm, ResultListener<CxwyMallSale> listener);


	/**
	 * @Title: YuEPayOrderFromID
	 * @Description: 根据订单ID进行余额支付
	 * @param mRequestQueue
	 * @param listener
	 * @return void
	 * @throws
	 */
	void YuEPayOrderFromID(RequestQueue mRequestQueue, Object[] parm, ResultListener<BaseEntity> listener);

	/**
	 * @Title: getOrderDestailFromID
	 * @Description: 根据订单ID获取库存信息，判断是否可以付款
	 * @param mRequestQueue
	 * @param listener
	 * @return void
	 * @throws
	 */
	void getOrderKuncunFromID(RequestQueue mRequestQueue, Object[] parm, ResultListener<BaseEntity> listener);

}
