package com.yxld.yxchuangxin.controller;

import java.util.Map;

import com.android.volley.RequestQueue;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.entity.LoginEntity;
import com.yxld.yxchuangxin.listener.ResultListener;

public interface LoginController extends API{
	
	
	/**
	 * @Title: getRegisterAlready 
	 * @Description: 判断账号是否已经注册
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getRegisterAlready(RequestQueue mRequestQueue, Object[] parm, ResultListener<BaseEntity> listener);

	/**
	 * @Title: getRegister 
	 * @Description: 提交注册数据
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getRegister(RequestQueue mRequestQueue, Object[] parm, ResultListener<BaseEntity> listener);
	
	
	/**
	 * @Title: getLogin 
	 * @Description: 提交登录数据
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getLogin(RequestQueue mRequestQueue, Object[] parm, ResultListener<LoginEntity> listener);
	
	/**
	 * @Title: getUpdatePaw 
	 * @Description: 修改密码
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getUpdatePwd(RequestQueue mRequestQueue, Object[] parm, ResultListener<BaseEntity> listener);
	
	/**
	 * @Title: getUpdateName 
	 * @Description: 修改昵称 
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getUpdateName(RequestQueue mRequestQueue, Map<String, String> parm, ResultListener<BaseEntity> listener);
	
	/**
	 * @Title: getUpdateCard 
	 * @Description: 修改身份证
	 * @param mRequestQueue
	 * @param listener    
	 * @return void
	 * @throws
	 */
	void getUpdateCard(RequestQueue mRequestQueue, Object[] parm, ResultListener<BaseEntity> listener);
}
