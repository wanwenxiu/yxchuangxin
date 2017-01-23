package com.yxld.yxchuangxin.activity.index;

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
 * Created by yishangfei on 2016/11/5 0005.
 * 门禁管理
 */
public class AccessActivity extends BaseActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabLayout.Tab Code, Visiting;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_access);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Code = tabLayout.getTabAt(0);
        Visiting = tabLayout.getTabAt(1);
    }

    FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        private String[] mTitles = new String[]{"业主二维码", "来访邀请"};

        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return new VisitingFragment();
            }
            return new CodeFragment();
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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
