package com.yxld.yxchuangxin.listener;

/**
 * @ClassName: ResultListener 
 * @Description: volley框架回调监听
 * @author wwx
 * @date 2016年3月13日 下午7:35:50 
 * 
 * @param <T>
 */
public interface ResultListener<T> {
	public void onResponse(T info);
	public void onErrorResponse(String errMsg);
}
