package com.yxld.yxchuangxin.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.CouponAdapter;
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
 * 优惠券
 * Created by yishangfei on 2016/8/2 0002.
 */
public class CouponActivity extends BaseActivity implements View.OnClickListener,ResultListener<CxwyMallUseDaijinquan>{
    private ListView coupon_list;
    private TextView history_red;
    private String url = API.URL_GET_YHQ;
    private CouponAdapter couponAdapter;
    private YeZhuController yeZhuController;
    private List<CxwyMallUseDaijinquan> list = new ArrayList<CxwyMallUseDaijinquan>();
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_coupon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initView() {
        coupon_list= (ListView) findViewById(R.id.coupon_list);
        history_red= (TextView) findViewById(R.id.history_red);
        history_red.setOnClickListener(this);
        initDataFromNet();
    }

    @Override
    protected void initDataFromNet() {
        if (yeZhuController == null) {
            yeZhuController = new YeZhuControllerImpl();
        }
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("useDaijinquan.daijinquanUseYezhuid", Contains.user.getYezhuId().toString());
        parm.put("useDaijinquan.daijinquanUseState", "0");
        Log.d("geek", "可以用优惠券parm+"+parm.toString());
        yeZhuController.getAllYHQ(mRequestQueue, url, parm, this);
    }


    public void onResponse(CxwyMallUseDaijinquan info) {
        // 获取请求码
        Log.d("geek", info.toString());
        if (info.status != STATUS_CODE_OK) {
            onError(info.MSG);
            return;
        }
        if (isEmptyList(info.getRows())) {
            ToastUtil.show(CouponActivity.this, "没有查询到记录");
        } else {
            list = info.getRows();
            couponAdapter = new CouponAdapter(list,this);
            coupon_list.setAdapter(couponAdapter);
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
        switch (v.getId()){
            case R.id.history_red:
                Intent history=new Intent(CouponActivity.this,NoCouponActivity.class);
                startActivity(history);
                break;
        }
    }
}
