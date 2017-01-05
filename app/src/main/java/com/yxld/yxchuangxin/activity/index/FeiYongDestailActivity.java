package com.yxld.yxchuangxin.activity.index;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.FeiYongListAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.AppWuYeFei;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.util.Collections;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;

/**
 * @author wwx
 * @ClassName: FeiYongDestailActivity
 * @Description: 缴纳详情
 * @date 2016年4月13日 上午11:22:39
 */
@SuppressLint("HandlerLeak")
public class FeiYongDestailActivity extends BaseActivity {
    private String fwid;
    private YeZhuController yeZhuController;
    private RecyclerView recyclerView;
    private FeiYongListAdapter headerAndFooterAdapter;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.feiyong_destail_activity_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        fwid = intent.getStringExtra("fwid");
        Logger.d(fwid);
    }

    @Override
    protected void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        iniDetail();

    }

    //业主房屋查询
    private void iniDetail() {
        if (yeZhuController == null) {
            yeZhuController = new YeZhuControllerImpl();
        }

        yeZhuController.getDETAIL(mRequestQueue,
                new Object[]{fwid}, listener);
    }

    private ResultListener<AppWuYeFei> listener = new ResultListener<AppWuYeFei>() {

        @Override
        public void onResponse(AppWuYeFei info) {
            progressDialog.hide();
            Log.d("...", info.toString());
            if (info.status != STATUS_CODE_OK) {
                onError(info.MSG);
                return;
            }
            if (info.MSG.equals("查找成功")) {
                progressDialog.hide();
                Collections.reverse(info.getHouse());
                headerAndFooterAdapter = new FeiYongListAdapter(info.getHouse());
                headerAndFooterAdapter.openLoadAnimation(SCALEIN);
                recyclerView.addItemDecoration(new DividerItemDecoration(FeiYongDestailActivity.this,
                        DividerItemDecoration.VERTICAL));
                recyclerView.setAdapter(headerAndFooterAdapter);
                View footerView = getView(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        headerAndFooterAdapter.addFooterView(getView(getRemoveFooterListener(), "click me to remove me"));
                    }
                }, "click me to add new footer");
                footerView = getLayoutInflater().inflate(R.layout.footer_view, (ViewGroup) recyclerView.getParent(), false);
                headerAndFooterAdapter.addFooterView(footerView, 0);
                recyclerView.setAdapter(headerAndFooterAdapter);
            }
        }

        @Override
        public void onErrorResponse(String errMsg) {
            progressDialog.hide();
            onError(errMsg);
        }
    };


    private View getView(View.OnClickListener listener, String text) {
        View view = getLayoutInflater().inflate(R.layout.footer_view, (ViewGroup) recyclerView.getParent(), false);
        view.setOnClickListener(listener);
        return view;
    }

    private View.OnClickListener getRemoveFooterListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerAndFooterAdapter.removeFooterView(v);
            }
        };
    }

    @Override
    protected void initDataFromLocal() {

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
