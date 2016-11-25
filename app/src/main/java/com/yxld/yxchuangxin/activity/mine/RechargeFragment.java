package com.yxld.yxchuangxin.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.order.CouponActivity;
import com.yxld.yxchuangxin.adapter.CouponAdapter;
import com.yxld.yxchuangxin.adapter.RechargeAdapter;
import com.yxld.yxchuangxin.base.BaseFragment;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallUseDaijinquan;
import com.yxld.yxchuangxin.entity.CxwyMallUserBalance;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.view.ListItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yishangfei on 2016/9/7 0007.
 * 充值
 */
public class RechargeFragment extends BaseFragment implements View.OnClickListener,ResultListener<CxwyMallUserBalance> {
    private RecyclerView mRecyclerView;
    private RechargeAdapter mListAdapter;
    private YeZhuController yeZhuController;
    private List<CxwyMallUserBalance> list = new ArrayList<CxwyMallUserBalance>();

    private String url = API.URL_GET_RECHARGE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_recharge, container, false);
        initview(view);
        initDataFromNet();
        return view;
    }

    @Override
    protected void initDataFromNet() {
        if (yeZhuController == null) {
            yeZhuController = new YeZhuControllerImpl();
        }
        Map<String, String> parm = new HashMap<String, String>();
        parm.put("balance.balanceUserId", Contains.user.getYezhuId().toString());
        parm.put("balance.balanceType", "充值");
        yeZhuController.getAllRecharge(mRequestQueue, url, parm, this);
    }

    @Override
    public void onResponse(CxwyMallUserBalance info) {
        // 获取请求码
        Log.d("......", info.toString());
        if (info.status != STATUS_CODE_OK) {
            onError(info.MSG);
            return;
        }
        if (isEmptyList(info.getRows())) {
            ToastUtil.show(getActivity(), "没有查询到记录");
        } else {
            list = info.getRows();
            mListAdapter = new RechargeAdapter(getActivity(), list);
            mRecyclerView.setAdapter(mListAdapter);
            /**
             * 设置布局管理器，listview风格则设置为LinearLayoutManager
             * gridview风格则设置为GridLayoutManager
             * pu瀑布流风格的设置为StaggeredGridLayoutManager
             */
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            // 设置item分
            mRecyclerView.addItemDecoration(new ListItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            // 设置item动画
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    @Override
    public void onErrorResponse(String errMsg) {
        onError(errMsg);
    }

    @Override
    protected void initDataFromLocal() {

    }

    private void initview(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

    }
}
