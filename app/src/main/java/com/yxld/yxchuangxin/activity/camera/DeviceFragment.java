package com.yxld.yxchuangxin.activity.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yxld.yxchuangxin.R;


/**
 * 作者：yishangfei on 2017/1/12 0012 14:30
 * 邮箱：yishangfei@foxmail.com
 */
public class DeviceFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_device,null);
        return view;
    }
}
