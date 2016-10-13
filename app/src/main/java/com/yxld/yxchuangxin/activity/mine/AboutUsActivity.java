package com.yxld.yxchuangxin.activity.mine;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;

import butterknife.OnClick;

/**
 * 关于我们
 * Created by wwx on 2016/6/7 0007.
 */
public class AboutUsActivity extends BaseActivity{

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.aboutus);
        getSupportActionBar().setTitle("关于我们");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
