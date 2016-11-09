package com.yxld.yxchuangxin.activity.index;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseFragment;

/**
 * Created by yishangfei on 2016/11/5 0005.
 * 我的建议
 */
public class SuggestFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.suggest_activity_layout, container, false);
        return view;
    }

    @Override
    protected void initDataFromLocal() {

    }
}
