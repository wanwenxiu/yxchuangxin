package com.yxld.yxchuangxin.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.entity.SearchHistoryEntity;

/**
 * @ClassName: SearchHistoryListAdapter 
 * @Description: 搜索历史适配器
 * @author wwx
 * @date 2016年3月16日 上午11:19:57 
 */
@SuppressLint("InflateParams")
public class SearchHistoryListAdapter extends BaseAdapter {
	private Context mContext;
	private List<SearchHistoryEntity> listDatas;

	/** 视图容器 */
	private LayoutInflater listContainer;
	/** 自定义视图 */
	private ViewHolder holder = null;

	public SearchHistoryListAdapter(List<SearchHistoryEntity> listData,
			Context context) {
		this.mContext = context;
		this.listDatas = listData;
		this.listContainer = LayoutInflater.from(mContext); // 创建视图容器并设置上下文

	}

	// 是获取显示数据的数量
	@Override
	public int getCount() {
		return listDatas.size();
	}

	// 获得当前位置的元素
	@Override
	public Object getItem(int arg0) {
		return listDatas.get(arg0);
	}

	// 获取当前控件的id号
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(
					R.layout.search_list_item, null);
			holder = new ViewHolder();
			holder.secondName = (TextView) convertView
					.findViewById(R.id.standard_text);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 加载数据
		final SearchHistoryEntity goodsVo = listDatas.get(position);

		holder.secondName.setText(goodsVo.getU_search());
		return convertView;
	}

	public List<SearchHistoryEntity> getListDatas() {
		return listDatas;
	}

	public void setListDatas(List<SearchHistoryEntity> listDatas) {
		this.listDatas = listDatas;
	}

	class ViewHolder {
		TextView secondName;
	}
}
