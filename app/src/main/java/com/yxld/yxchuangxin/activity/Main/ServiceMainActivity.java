package com.yxld.yxchuangxin.activity.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.index.ExpressActivity;
import com.yxld.yxchuangxin.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WWX on 2016/5/24 0024.
 * 社区助手主界面
 */
public class ServiceMainActivity extends BaseActivity implements View.OnClickListener{
    private String[]titles=new String[]{"到家","车主服务","支付","生活助手","娱乐","出行/旅行"};
    private TabLayout tab_layout;
    private ViewPager info_viewpager;
    private List<Fragment> fragments;
    private CNKFixedPagerAdapter mPagerAdater;

    private ImageView fhhn,jrtt,hx;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_service_main);
        getSupportActionBar().setTitle("欣周边");
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        initViews();
        initValidata();
    }

    private void initViews(){
        tab_layout=(TabLayout)findViewById(R.id.tab_layout);
        info_viewpager=(ViewPager)findViewById(R.id.info_viewpager);
        fhhn= (ImageView) findViewById(R.id.fhhn);
        jrtt= (ImageView) findViewById(R.id.jrtt);
        hx= (ImageView) findViewById(R.id.hx);
        fhhn.setOnClickListener(this);
        jrtt.setOnClickListener(this);
        hx.setOnClickListener(this);

    }
    private void initValidata(){
        fragments=new ArrayList<>();
        for(int i=0;i<titles.length;i++){
            ItemServiceFragment oneFragment=new ItemServiceFragment();
            Bundle args = new Bundle();
            args.putInt("arg", i);
            oneFragment.setArguments(args);
            fragments.add(oneFragment);
        }
        //创建Fragment的 ViewPager 自定义适配器
        mPagerAdater=new CNKFixedPagerAdapter(getSupportFragmentManager());
        //设置显示的标题
        mPagerAdater.setTitles(titles);
        //设置需要进行滑动的页面Fragment
        mPagerAdater.setFragments(fragments);

        info_viewpager.setAdapter(mPagerAdater);
        tab_layout.setupWithViewPager(info_viewpager);

        //设置Tablayout
        //设置TabLayout模式 -该使用Tab数量比较多的情况
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(ServiceMainActivity.this, // context
                WebViewActivity.class);// 跳转的activity\
        switch (v.getId()){
            case R.id.fhhn:
                Bundle fhhn = new Bundle();
                fhhn.putString("name", "凤凰湖南");
                fhhn.putString("address","http://hunan.ifeng.com");
                intent.putExtras(fhhn);
                startActivity(intent);
                break;
            case R.id.jrtt:
                Bundle jrtt = new Bundle();
                jrtt.putString("name", "今日头条");
                jrtt.putString("address","http://www.toutiao.com");
                intent.putExtras(jrtt);
                startActivity(intent);
                break;
            case R.id.hx:
                Bundle hx = new Bundle();
                hx.putString("name", "虎嗅网");
                hx.putString("address","http://www.huxiu.com");
                intent.putExtras(hx);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void initDataFromLocal() {
        selectView();
    }

    private void selectView(){
        int position = getIntent().getIntExtra("tag", 0);
        Log.d("geek","position="+position);
        info_viewpager.setCurrentItem(position);
    }
}
