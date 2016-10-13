package com.yxld.yxchuangxin.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyMainPagerAdapter extends FragmentPagerAdapter {
	public List<Fragment> fs;

	public MyMainPagerAdapter(FragmentManager fm) {
		super(fm);
		fs=new ArrayList<Fragment>();
	}

	@Override
	public Fragment getItem(int arg0) {
		return fs != null ? fs.get(arg0) : null;
	}

	@Override
	public int getCount() {
		return fs != null ? fs.size() : 0;
	}
	
}
