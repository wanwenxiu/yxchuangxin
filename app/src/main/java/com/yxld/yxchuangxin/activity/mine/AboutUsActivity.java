package com.yxld.yxchuangxin.activity.mine;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.util.StringUitl;

import butterknife.OnClick;

/**
 * 关于我们
 * Created by wwx on 2016/6/7 0007.
 */
public class AboutUsActivity extends BaseActivity{
    private TextView nianfen;

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
        nianfen = (TextView) findViewById(R.id.textView2);
        nianfen.setText("Copyright  ©2016-"+StringUitl.getCurYear()+"   湖南创欣物联科技有限公司");
    }

    @Override
    protected void initDataFromLocal() {

    }

    @Override
    public void onClick(View v) {

    }
}
