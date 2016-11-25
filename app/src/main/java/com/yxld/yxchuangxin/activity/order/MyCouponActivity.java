package com.yxld.yxchuangxin.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.MyCouponAdapter;
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
 * 我的优惠券
 * Created by yishangfei on 2016/8/2 0002.
 */
public class MyCouponActivity extends BaseActivity implements ResultListener<CxwyMallUseDaijinquan> {
    /**
     * 查询接口
     */
    private String url = API.URL_GET_YHQ;
    private ListView coupon_list;
    private MyCouponAdapter myCouponAdapter;
    private YeZhuController yeZhuController;
    private List<CxwyMallUseDaijinquan> list = new ArrayList<CxwyMallUseDaijinquan>();

    /** 优惠券金额*/
    public static  float yhqjine =0;
    /** 优惠券id*/
    public static int yhqId = 0;

    float curmonery;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mycoupon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initView() {
        coupon_list= (ListView) findViewById(R.id.mycoupon_list);
        coupon_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                float djqje = Float.parseFloat(list.get(position).getDaijinquanUseShiyongjia());
                Log.d("geek","最低价为"+djqje+",当前价curmonery"+curmonery);
                if(djqje<=curmonery){
                    yhqjine = Float.parseFloat(list.get(position).getDaijinquanUseJine());
                    yhqId = list.get(position).getDaijinquanUseId();
                    finish();
                }else{
                    ToastUtil.show(MyCouponActivity.this,"最低使用价格为"+list.get(position).getDaijinquanUseShiyongjia()+"元");
                }
            }
        });
        initDataFromNet();
    }

    @Override
    protected void initDataFromNet() {
        if (yeZhuController == null) {
            yeZhuController = new YeZhuControllerImpl();
        }
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("useDaijinquan.daijinquanUseYonghuid", Contains.user.getYezhuId().toString());
        parm.put("useDaijinquan.daijinquanUseState", "0");
        yeZhuController.getAllYHQ(mRequestQueue, url, parm, this);
    }


    public void onResponse(CxwyMallUseDaijinquan info) {
        // 获取请求码
        Log.d("......", info.toString());
        if (info.status != STATUS_CODE_OK) {
            onError(info.MSG);
            return;
        }
        if (isEmptyList(info.getRows())) {
            ToastUtil.show(MyCouponActivity.this, "没有查询到记录");
        } else {
            list = info.getRows();
            myCouponAdapter = new MyCouponAdapter(list,this);
            coupon_list.setAdapter(myCouponAdapter);
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
        curmonery = getIntent().getFloatExtra("curMoney",0);
    }

    @Override
    public void onClick(View v) {

    }
}
