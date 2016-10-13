package com.yxld.yxchuangxin.activity.clazz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.goods.SearchActivity;
import com.yxld.yxchuangxin.activity.goods.ShopListActivity;
import com.yxld.yxchuangxin.adapter.FirstClassAdapter;
import com.yxld.yxchuangxin.adapter.SecondClassListAdapter;
import com.yxld.yxchuangxin.base.BaseFragment;
import com.yxld.yxchuangxin.controller.GoodsController;
import com.yxld.yxchuangxin.controller.impl.GoodsControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallClassify;
import com.yxld.yxchuangxin.entity.FirstClassGoodsInfo;
import com.yxld.yxchuangxin.entity.SecondClassGoodsInfo;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ClazzMainFragment
 * @Description: 分类
 * @author wwx
 * @date 2016年3月10日 下午4:05:00
 */
public class ClazzMainFragment extends BaseFragment {
	/** 通过ID 进入商品列表页面*/
	private final int byId = 0;
	
	/** 搜索*/
	private TextView searchTv,allgoods;

	/** 一级分类列表 */
	private ListView firstClassList;
	/** 一级分类列表适配器 */
	private FirstClassAdapter firstClassAdapter;

	/** 二级分类列表 */
	private GridView secondClassList;
	/** 二级分类列表适配器 */
	private SecondClassListAdapter secondAdapter;

	/** 一级分类数据 */
	private List<CxwyMallClassify> listFirstClassData ;
	/** 二级分类数据 */
	private List<CxwyMallClassify> listSecondDatas;
	private GoodsController goodsController;

	private int fristPosition = 0;

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(goodsController != null){
			goodsController = null;
		}
		if(listFirstClassData != null){
			listFirstClassData = null;
		}
		if(listSecondDatas != null){
			listSecondDatas = null;
		}
		if(firstClassAdapter != null){
			firstClassAdapter = null;
		}
		if(secondAdapter != null){
			secondAdapter = null;
		}


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mall_fragment, container, false);
		init(view);
		initDataFromNet();
		return view;
	}

	/**
	 * @Title: init
	 * @Description: 初始化视图
	 * @param view
	 * @return void
	 * @throws
	 */
	private void init(View view) {
		Log.d("geek","分类 init（）");
		searchTv = (TextView) view.findViewById(R.id.searchTv);
		searchTv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(SearchActivity.class);
			}
		});

		allgoods = (TextView) view.findViewById(R.id.allgoods);
		allgoods.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//传递二级分类Id给商品列表
				Bundle bundle = new Bundle();
				bundle.putInt("type", byId);
				bundle.putString("calssId", listFirstClassData.get(fristPosition).getClassifyId()+"");
				startActivity(ShopListActivity.class, bundle);
			}
		});

		// 一级分类列表
		firstClassList = (ListView) view.findViewById(R.id.firstClassList);

		if(listFirstClassData == null){
			listFirstClassData = new ArrayList<>();
		}
		firstClassAdapter = new FirstClassAdapter(listFirstClassData,
				getActivity());
		firstClassList.setAdapter(firstClassAdapter);

		firstClassList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				fristPosition = position;
				for (int i = 0; i < listFirstClassData.size(); i++) {
					if (i == position) {
						listFirstClassData.get(position).setCheck(true);
					} else {
						listFirstClassData.get(i).setCheck(false);
					}
				}
				firstClassAdapter.setListDatas(listFirstClassData);
				firstClassAdapter.notifyDataSetChanged();
				
				listSecondDatas.clear();
				secondAdapter.setListDatas(listSecondDatas);
				secondAdapter.notifyDataSetChanged();
				
				//根据一级分类查询二级分类
				progressDialog.show();
				goodsController
						.getAllSecondClassList(mRequestQueue,
								new Object[] {
										listFirstClassData.get(position)
												.getClassifyId()},
								listenerSecond);
			}
		});

		// 二级分类
		secondClassList = (GridView) view.findViewById(R.id.secondClassList);
		if(listSecondDatas == null){
			listSecondDatas = new ArrayList<>();
		}
		secondAdapter = new SecondClassListAdapter(listSecondDatas, getActivity());
		secondClassList.setAdapter(secondAdapter);
		secondClassList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//传递二级分类Id给商品列表
				Bundle bundle = new Bundle();
				bundle.putInt("type", byId);
				bundle.putString("calssId", listSecondDatas.get(arg2).getClassifyId()+"");
				startActivity(ShopListActivity.class, bundle);
			}
		});
		
	}

	@Override
	public void firstLoading() {
		super.firstLoading();
		initDataFromNet();
		isLoaded = true;
	}

	/**
	 * @Title: initDataFromNet
	 * @Description: 获取网络数据
	 * @return void
	 * @throws
	 */
	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		Log.d("geek","分类 initDataFromNet（）");
		if (goodsController == null) {
			goodsController = new GoodsControllerImpl();
		}
		goodsController.getAllFirstClassList(mRequestQueue, listener);
	}
	
	private ResultListener<FirstClassGoodsInfo> listener = new ResultListener<FirstClassGoodsInfo>() {

		@Override
		public void onResponse(FirstClassGoodsInfo info) {
			// 获取请求码
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
			}
			if (isEmptyList(info.getClassifyOne())) {
				onError("没有二级分类");
			} else {
				listFirstClassData.clear();
				listFirstClassData = info.getClassifyOne();
				listFirstClassData.get(0).setCheck(true);
				firstClassAdapter.setListDatas(listFirstClassData);
				firstClassAdapter.notifyDataSetChanged();
				
				//根据一级分类查询二级分类
				goodsController
						.getAllSecondClassList(mRequestQueue,
								new Object[] {
										listFirstClassData.get(0)
												.getClassifyId()},
								listenerSecond);
			}

		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError("请求失败");
		}
	};

	private ResultListener<SecondClassGoodsInfo> listenerSecond = new ResultListener<SecondClassGoodsInfo>() {

		@Override
		public void onResponse(SecondClassGoodsInfo info) {
			Log.d(".....", info.toString());
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
			}

			if (isEmptyList(info.getClassifyTwo())) {
				//onError("没有详细分类");
				resetView();
			} else {
				listSecondDatas.clear();
				listSecondDatas = info.getClassifyTwo();
				Log.d("geek","listSecondDatas ="+listSecondDatas.toString());
				secondAdapter.setListDatas(listSecondDatas);
				secondAdapter.notifyDataSetChanged();
				resetView();
			}
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError(errMsg);
		}
	};

	@Override
	protected void initDataFromLocal() {
		
	}
}
