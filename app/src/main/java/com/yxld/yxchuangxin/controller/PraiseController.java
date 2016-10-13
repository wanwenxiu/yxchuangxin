package com.yxld.yxchuangxin.controller;

import java.util.Map;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.entity.CxwyMallComment;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * @ClassName: PraiseController 
 * @Description: 评价接口类 
 * @author wwx
 * @date 2016年3月11日 下午4:19:24 
 */
public interface PraiseController extends API{
	/**
	 * @Title: getPraiseGoodsFromUser 
	 * @Description: 提价评论
	 * @param mRequestQueue
	 * @param parm
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getPraiseGoodsFromUser(RequestQueue mRequestQueue, Map<String, String> parm,
								final ResultListener<BaseEntity> listener);
	
	/**
	 * @Title: getPraiseListFromGoodsID 
	 * @Description: 获取商品评论集合
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getPraiseListFromGoodsID(RequestQueue mRequestQueue, Object[] parm, ResultListener<CxwyMallComment> listener);

}
