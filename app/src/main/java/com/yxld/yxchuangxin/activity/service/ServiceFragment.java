package com.yxld.yxchuangxin.activity.service;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.BaseMapListApater;
import com.yxld.yxchuangxin.base.BaseFragment;

import cn.bingoogolapple.bgabanner.BGABanner;

@SuppressLint("InflateParams")
/**
 * @ClassName:  ServiceFragment 
 * @Description: 首页
 * @author wwx
 * @date 2016年3月9日 上午10:12:40 
 */
public class ServiceFragment extends BaseFragment {
	/** 首页轮播图 */
	private BGABanner ad_view1;
	private GridView mPerimeter;
	private GridView mProperty;

	/** 通知列表 */
	private ListView listView;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.serve_fragment, container, false);
		ad_view1 = (BGABanner) view.findViewById(R.id.ad_view1);
		listView = (ListView) view.findViewById(R.id.listView);
		mPerimeter = (GridView) view.findViewById(R.id.property);
		mProperty = (GridView) view.findViewById(R.id.service);
		initDataFromLocal();
		return view;
	}

	@Override
	protected void initDataFromLocal() {
		initData();
	}

	/**
	 * @Title: initData
	 * @Description: 创建模拟数据
	 * @return void
	 * @throws
	 */
	private void initData() {

		// 初始化通知信息
		ArrayList<HashMap<String, Object>> hootList = new ArrayList<HashMap<String, Object>>();
		for (int x = 0; x < 4; x++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("info", "文字内容文字内容文字内容文字内容");
			map.put("time", "2016/04/16");
			hootList.add(map);
		}

		BaseMapListApater apater = new BaseMapListApater(getActivity(),
				hootList, R.layout.server_msg_item, new String[] { "info",
						"time" }, new int[] { R.id.msg_info, R.id.msg_time });
		listView.setAdapter(apater);

		ArrayList<HashMap<String, Object>> hootLists = new ArrayList<HashMap<String, Object>>();
		// 初始化通知信息
		for (int x = 0; x < 6; x++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", "文字内容");
			hootLists.add(map);
		}

		BaseMapListApater apater1 = new BaseMapListApater(getActivity(),
				hootLists, R.layout.server_goods_item, new String[] { "name" },
				new int[] { R.id.name });
		mPerimeter.setAdapter(apater1);
		mProperty.setAdapter(apater1);
	}
}
