package com.yxld.yxchuangxin.activity.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.cart.CartMainFragment;
import com.yxld.yxchuangxin.activity.clazz.ClazzMainFragment;
import com.yxld.yxchuangxin.activity.index.MallIndexFragment;
import com.yxld.yxchuangxin.activity.mine.MallMineFragment;
import com.yxld.yxchuangxin.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WWX on 2016/5/25 0024.
 * 商城主界面
 */
public class MallMainActivity extends BaseActivity {
    private String[]titles=new String[]{"商城","分类","购物车","我的","搜索"};
    private TabLayout tab_layout;
    private ViewPager info_viewpager;
    private List<Fragment> fragments;
    private CNKFixedPagerAdapter mPagerAdater;

    /** 自定义广播 */
    private MyMallIndexJumpBroad myReceiver = null;

    private static MallIndexFragment mallIndexFragment =  new MallIndexFragment();
    private static ClazzMainFragment clazzMainFragment =  new ClazzMainFragment();
    private static CartMainFragment cartMainFragment =  new CartMainFragment();
    private static MallMineFragment mallMineFragment =  new MallMineFragment();

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wuye_main);
        getSupportActionBar().setTitle("欣商城");
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
            if(i==0){
                fragments.add(mallIndexFragment);
            }else if(i==1){
                fragments.add(clazzMainFragment);
            }else if(i == 2){
                fragments.add(cartMainFragment);
            }else if (i==3){
                fragments.add(mallMineFragment);
            }else{

            }
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
        tab_layout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initDataFromLocal() {
    // 注册广播
        myReceiver = new MyMallIndexJumpBroad();
        IntentFilter intentFilter = new IntentFilter(getResources().getString(
                R.string.index_broad));
        registerReceiver(myReceiver, intentFilter);
    }

    /**
     * @ClassName: MyMallIndexJumpBroad
     * @Description: 跳转广播
     * @author wwx
     * @date 2016年3月18日 下午3:20:27
     */
    class MyMallIndexJumpBroad extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int tag = intent.getIntExtra("IndexJumpTag", 1);
            Log.d("geek", "CX 广播 MallMainActivity -IndexJumpTag = " + tag);
            selectView(tag);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       unregisterReceiver(myReceiver);
        if(titles != null){
            titles = null;
        }
        if(fragments != null){
            fragments.clear();
            fragments = null;
        }
        if(info_viewpager != null){
            info_viewpager = null;
        }

        if(mPagerAdater != null){
            mPagerAdater = null;
        }

        if(myReceiver != null){
            myReceiver = null;
        }
    }


    @Override
    public void onClick(View v) {

    }

    private void selectView(int position){
        Log.d("geek","position="+position);
        info_viewpager.setCurrentItem(position);
    }
}
