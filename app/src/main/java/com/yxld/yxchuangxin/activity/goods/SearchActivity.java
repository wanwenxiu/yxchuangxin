/**   
 * @Title: SearchActivity.java 
 * @Package com.nmore.unclephone.activity.search 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author wjysky520@gmail.com   
 * @date 2015年8月4日 下午5:55:01 
 * @version V1.0   
 */
package com.yxld.yxchuangxin.activity.goods;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.SearchHistoryListAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.db.DBUtil;
import com.yxld.yxchuangxin.entity.SearchHistoryEntity;
import com.yxld.yxchuangxin.util.StringUitl;

/**
 * @ClassName: SearchActivity
 * @Description: 搜索界面
 * @author wwx
 * @date 2016年3月16日 上午11:58:23
 */
public class SearchActivity extends BaseActivity {
	/** 通过搜索关键字 进入商品列表页面*/
	private final int byKey = 1;
	
	/** 搜索历史布局 */
	private LinearLayout llSearchHistory = null;
	/** 暂无搜索历史 */
	private TextView tvNoSearchHistory = null;
	/** 清空搜索历史 */
	private TextView clearSearchHistory = null;
	/**
	 * 装载搜索历史的控件
	 */
	private ListView searchList;
	/**
	 * 搜索按钮 点击进行搜索
	 */
	private TextView searchSubmit;
	/**
	 * 搜索框的控件 搜索的内容
	 */
	private EditText searchText;

	/** 返回按钮 */
	private ImageView returnWrap;
	/** 搜索历史适配器 */
	private SearchHistoryListAdapter adapterSearch;
	/** 获取保存在数据库中的搜索数据 */
	private List<SearchHistoryEntity> historyEntities;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_search);
		getSupportActionBar().setTitle("商品搜索");
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
	protected void initView() {
		returnWrap = (ImageView) findViewById(R.id.returnImg);
		returnWrap.setOnClickListener(this);

		searchText = (EditText) findViewById(R.id.searchText);
		searchList = (ListView) findViewById(R.id.searchList);
		searchList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				GoSearch(historyEntities.get(arg2).getU_search());
			}

		});
		searchSubmit = (TextView) findViewById(R.id.searchSubmit);
		searchSubmit.setOnClickListener(this);

		clearSearchHistory = (TextView) findViewById(R.id.clearSearchHistory);
		clearSearchHistory.setOnClickListener(this);

		tvNoSearchHistory = (TextView) findViewById(R.id.tvNoSearchHistory);
		llSearchHistory = (LinearLayout) findViewById(R.id.llSearchHistory);
	}

	@Override
	protected void initDataFromLocal() {
		getSearchHistory("1");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.returnImg:
			finish();
			break;
		case R.id.clearSearchHistory:// 清除搜索历史
			dbUtil.clearData(SearchHistoryEntity.class);
			tvNoSearchHistory.setVisibility(View.VISIBLE);
			llSearchHistory.setVisibility(View.GONE);
			clearSearchHistory.setVisibility(View.GONE);
			searchList.setVisibility(View.GONE);
			break;
		case R.id.searchSubmit: // 搜索按钮
			if (StringUitl.isNotEmpty(SearchActivity.this, searchText, null)) {
				GoSearch(searchText.getText().toString());
			}

			break;
		default:
			break;
		}
	}

	/**
	 * @Title: GoSearch
	 * @Description: 进入搜索
	 * @param str
	 * @return void
	 * @throws
	 */
	private void GoSearch(String str) {
		// 进入搜索
		Bundle bundle = new Bundle();
		bundle.putInt("type", byKey);
		bundle.putString("searchFlg", str);
		startActivity(ShopListActivity.class, bundle);
		
		SearchHistoryEntity entity = new SearchHistoryEntity("1", str);
		
		long result = dbUtil.insert(entity, entity.getU_id());
		Log.d("geek", "==null插入搜索历史result = " + result);
		
		finish();
	}

	/**
	 * 查询搜索历史
	 * 
	 * @param string
	 */
	@SuppressWarnings("unchecked")
	public void getSearchHistory(String uId) {
		if (dbUtil == null) {
			dbUtil = new DBUtil(this);
		}
		historyEntities = dbUtil.query(SearchHistoryEntity.class, uId);
		if(historyEntities != null){
			historyEntities = StringUitl.removeDuplicate(historyEntities);
		}
	
		if (historyEntities == null || historyEntities.size() <= 0) {
			tvNoSearchHistory.setVisibility(View.VISIBLE);
			llSearchHistory.setVisibility(View.GONE);
			clearSearchHistory.setVisibility(View.GONE);
			searchList.setVisibility(View.GONE);
		} else {
			tvNoSearchHistory.setVisibility(View.GONE);
			llSearchHistory.setVisibility(View.VISIBLE);
			clearSearchHistory.setVisibility(View.VISIBLE);
			searchList.setVisibility(View.VISIBLE);
			if (adapterSearch == null) {
				adapterSearch = new SearchHistoryListAdapter(historyEntities,
						this);
				searchList.setAdapter(adapterSearch);
			}
			adapterSearch.setListDatas(historyEntities);
		}
	}
}
