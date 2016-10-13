package com.yxld.yxchuangxin.activity.order;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.NoCouponAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallUseDaijinquan;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 不可用优惠券
 * Created by yishangfei on 2016/8/2 0002.
 */

public class NoCouponActivity extends BaseActivity implements ResultListener<CxwyMallUseDaijinquan>{
    private ListView coupon_list;
    private String url = API.URL_GET_NOYHQ;
    private NoCouponAdapter noCouponAdapter;
    private YeZhuController yeZhuController;
    private List<CxwyMallUseDaijinquan> list = new ArrayList<CxwyMallUseDaijinquan>();

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_nocoupon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("历史优惠券");
    }

    @Override
    protected void initView() {
        coupon_list= (ListView) findViewById(R.id.nocoupon_list);
        initDataFromNet();

    }



    @Override
    protected void initDataFromNet() {
        if (yeZhuController == null) {
            yeZhuController = new YeZhuControllerImpl();
        }
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("useDaijinquan.daijinquanUseYonghuid", Contains.cxwyMallUser.getUserId().toString());
        Log.d("geek", "可以用优惠券parm+"+parm.toString());
        yeZhuController.getAllNOYHQ(mRequestQueue, url, parm, this);
    }


    public void onResponse(CxwyMallUseDaijinquan info) {
        // 获取请求码
        Log.d("......", info.toString());
        if (info.status != STATUS_CODE_OK) {
            onError(info.MSG);
            return;
        }
        if (isEmptyList(info.getRows())) {
            ToastUtil.show(NoCouponActivity.this, "没有查询到记录");
        } else {
            list = info.getRows();
            noCouponAdapter = new NoCouponAdapter(list,this);
            coupon_list.setAdapter(noCouponAdapter);
        }
    }

    @Override
    public void onErrorResponse(String errMsg) {
        onError(errMsg);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            System.gc();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void initDataFromLocal() {

    }

    @Override
    public void onClick(View v) {

    }
}
