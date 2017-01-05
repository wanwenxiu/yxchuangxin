package com.yxld.yxchuangxin.activity.Main;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.order.CouponActivity;
import com.yxld.yxchuangxin.adapter.CouponAdapter;
import com.yxld.yxchuangxin.adapter.Wuyeadapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;

public class WuyeActivity extends BaseActivity{
    private ListView listView;
    String[] title = { "我的物业","缴费服务","综合服务","个人中心"};
    private Wuyeadapter testadapter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView= (ListView) findViewById(R.id.listview);
        testadapter =new Wuyeadapter(title,this,mRequestQueue);
        listView.setAdapter(testadapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDataFromLocal() {

    }

    @Override
    public void onClick(View v) {

    }

}
