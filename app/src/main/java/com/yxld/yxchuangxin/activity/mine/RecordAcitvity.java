package com.yxld.yxchuangxin.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;

/**
 * Created by yishangfei on 2016/9/7 0007.
 * 金钱交易记录
 */

public class RecordAcitvity extends BaseActivity{
    private TabLayout mTablayout;
    private ViewPager mViewPager;

    private TabLayout.Tab recharge;
    private TabLayout.Tab expenditure;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        initEvents();
    }

    private void initEvents() {

        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == mTablayout.getTabAt(0)) {
                    // one.setIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.mine_pressed));
                    mViewPager.setCurrentItem(0);
                } else if (tab == mTablayout.getTabAt(1)) {
                    //   two.setIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.music_pressed));
                    mViewPager.setCurrentItem(1);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == mTablayout.getTabAt(0)) {

                } else if (tab == mTablayout.getTabAt(1)) {

                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initViews() {

        mTablayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            private String[] mTitles = new String[]{"充值", "支出"};

            @Override
            public Fragment getItem(int position) {
                if (position == 1) {
                    return new ExpenditureFragment();
                }
                return new RechargeFragment();
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }

        });

        mTablayout.setupWithViewPager(mViewPager);

        recharge = mTablayout.getTabAt(0);
        expenditure = mTablayout.getTabAt(1);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
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
