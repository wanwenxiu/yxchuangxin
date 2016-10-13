package com.yxld.yxchuangxin.activity.Main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WWX on 2016/5/24 0024.
 * 社区物业主界面
 */
public class WuYeMainActivity  extends BaseActivity {
    private String[]titles=new String[]{"  我的物业  ","  专享服务  ","  我的社区  ","  个人中心  "};
    private TabLayout tab_layout;
    private ViewPager info_viewpager;
    private List<Fragment> fragments;
    private CNKFixedPagerAdapter mPagerAdater;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wuye_main);
        getSupportActionBar().setTitle("欣物业");
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
    }
    private void initValidata(){
        fragments=new ArrayList<>();
        for(int i=0;i<titles.length;i++){
            ItemWuyeFragment oneFragment=new ItemWuyeFragment();
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
//        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab_layout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onClick(View v) {

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
