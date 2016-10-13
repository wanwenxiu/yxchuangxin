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
import com.yxld.yxchuangxin.entity.WuyeRecordAndroid;

/**
 * @ClassName: QianFeiListAdapter 
 * @Description: 欠费列表 
 * @author wwx
 * @date 2016年4月7日 下午4:53:44 
 */
@SuppressLint("InflateParams")
public class FeiYongListAdapter extends BaseAdapter {
	private Context mContext;
	private List<WuyeRecordAndroid> listDatas;

	/** 视图容器 */
	private LayoutInflater listContainer;
	/** 自定义视图 */
	private ViewHolder holder = null;

	public FeiYongListAdapter(List<WuyeRecordAndroid> listData,
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
					R.layout.feiyong_list_item_layout, null);
			holder = new ViewHolder();
			holder.time = (TextView) convertView
					.findViewById(R.id.time);
			holder.destail = (TextView) convertView
					.findViewById(R.id.destail);
			holder.type = (TextView) convertView
					.findViewById(R.id.type);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 加载数据
		final WuyeRecordAndroid goodsVo = listDatas.get(position);

		holder.time.setText(goodsVo.getTime()+"-"+goodsVo.getBeiyong());
		holder.type.setText(goodsVo.getType());
		holder.destail.setText("本期费用合计:"+goodsVo.getMmoney());
		
		if (goodsVo.getType() != null) {
			if (goodsVo.getType().contains("欠")) {
				holder.type.setTextColor(mContext.getResources()
						.getColor(R.color.red));
			} else {
				holder.type.setTextColor(mContext.getResources()
						.getColor(R.color.color_7AC356));
			}
		}
		return convertView;
	}

	public List<WuyeRecordAndroid> getListDatas() {
		return listDatas;
	}

	public void setListDatas(List<WuyeRecordAndroid> listDatas) {
		this.listDatas = listDatas;
		notifyDataSetChanged();
	}

	class ViewHolder {
		TextView time;
		TextView destail;
		TextView type;
	}
}
