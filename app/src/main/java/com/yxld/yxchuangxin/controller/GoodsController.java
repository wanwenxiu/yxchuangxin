package com.yxld.yxchuangxin.controller;

import java.util.Map;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.entity.FirstClassGoodsInfo;
import com.yxld.yxchuangxin.entity.SecondClassGoodsInfo;
import com.yxld.yxchuangxin.entity.ShopList;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: GoodsController 
 * @Description: 商品接口类 
 * @author wwx
 * @date 2016年3月11日 下午4:19:24 
 */
public interface GoodsController extends API{

	/**
	 * @Title: getAllFirstClassList 
	 * @Description: 获取一级分类集合
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getAllFirstClassList(RequestQueue mRequestQueue, ResultListener<FirstClassGoodsInfo> listener);

	
	/**
	 * @Title: getAllFirstClassList 
	 * @Description: 获取一级分类集合
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getAllSecondClassList(RequestQueue mRequestQueue, Object[] parm, ResultListener<SecondClassGoodsInfo> listener);

	/**
	 * @Title: getShopListById 
	 * @Description: 通过ID获取商品列表
	 * @param mRequestQueue
	 * @param params
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getShopListById(RequestQueue mRequestQueue, final Map<String, String> params, ResultListener<ShopList> listener);
	
	
	/**
	 * @Title: getShopListByKey 
	 * @Description: 通过键获取商品列表
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getShopListByKey(RequestQueue mRequestQueue, Map<String, String> parm,
						  final ResultListener<ShopList> listener);
	
	/**
	 * @Title: getCollectGoodsFromId 
	 * @Description: 收藏商品
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getCollectGoodsFromId(RequestQueue mRequestQueue, Map<String, String> parm,
							   final ResultListener<BaseEntity> listener);
	
	/**
	 * @Title: deleteCollectGoodsFromId 
	 * @Description: 删除收藏商品
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void deleteCollectGoodsFromId(RequestQueue mRequestQueue, Object[] parm, ResultListener<BaseEntity> listener);

	
	/**
	 * @Title: getIndexGoodsList 
	 * @Description: 获得首页推荐商品集合
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getIndexGoodsList(RequestQueue mRequestQueue, Map<String, String> parm, ResultListener<ShopList> listener);


}
