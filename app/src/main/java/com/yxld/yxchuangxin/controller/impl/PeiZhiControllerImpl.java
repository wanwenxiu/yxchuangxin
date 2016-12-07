package com.yxld.yxchuangxin.controller.impl;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.yxld.yxchuangxin.controller.PeiZhiController;
import com.yxld.yxchuangxin.entity.CxwyMallPezhi;
import com.yxld.yxchuangxin.http.GsonRequest;
import com.yxld.yxchuangxin.listener.ResultListener;

/**
 * wwx
 */
public class PeiZhiControllerImpl implements PeiZhiController{
    @Override
    public void getAllScLbTbList(RequestQueue mRequestQueue, Object[] parm,final  ResultListener<CxwyMallPezhi> listener) {
        GsonRequest<CxwyMallPezhi> gsonRequest = new GsonRequest<CxwyMallPezhi>(String.format(URL_GET_SC_LUNBO_TUBIAO, parm), CxwyMallPezhi.class, new Response.Listener<CxwyMallPezhi>() {

            @Override
            public void onResponse(CxwyMallPezhi response) {
                if (listener != null) {
                    listener.onResponse(response);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onErrorResponse(error.getMessage());
                }
            }
        });
        gsonRequest.setShouldCache(true);
        gsonRequest.setTag(URL_GET_SC_LUNBO_TUBIAO);
        mRequestQueue.add(gsonRequest);
    }

    @Override
    public void getAllMainLbList(RequestQueue mRequestQueue, Object[] parm,final ResultListener<CxwyMallPezhi> listener) {
        GsonRequest<CxwyMallPezhi> gsonRequest = new GsonRequest<CxwyMallPezhi>(String.format(URL_GET_MAIN_LUNBO, parm), CxwyMallPezhi.class, new Response.Listener<CxwyMallPezhi>() {

            @Override
            public void onResponse(CxwyMallPezhi response) {
                if (listener != null) {
                    listener.onResponse(response);
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onErrorResponse(error.getMessage());
                }
            }
        });
        gsonRequest.setShouldCache(true);
        gsonRequest.setTag(URL_GET_MAIN_LUNBO);
        mRequestQueue.add(gsonRequest);
    }
}
