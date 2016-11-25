package com.yxld.yxchuangxin.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.entity.CxwyYezhu;

import java.util.List;

/**
 * @ClassName: ChengyuanListAdapter
 * @Description: 成员管理列表
 * @author wwx
 * @date 2016年7月15日 下午3:01:44
 */
@SuppressLint("InflateParams")
public class ChengyuanListAdapter extends BaseAdapter {
	private Context mContext;
	private List<CxwyYezhu> listDatas;

	/** 视图容器 */
	private LayoutInflater listContainer;
	/** 自定义视图 */
	private ViewHolder holder = null;

	/** handler*/
	private Handler handler;

	public ChengyuanListAdapter(List<CxwyYezhu> listData,
								Context context,Handler mhandler) {
		this.mContext = context;
		this.listDatas = listData;
		this.handler = mhandler;
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
					R.layout.authorized_release_activity_item_layout, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.name);
			holder.tel = (TextView) convertView
					.findViewById(R.id.time);
			holder.type = (TextView) convertView
					.findViewById(R.id.state);
			holder.type.setTextColor(Color.RED);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 加载数据
		final CxwyYezhu yezhu = listDatas.get(position);

		holder.tel.setText(yezhu.getYezhuShouji());
		holder.name.setText(yezhu.getYezhuName());
//		if(yezhu.getYezhuGuanxi() != null && !"".equals(yezhu.getYezhuGuanxi()) ){
//			holder.name.setText(yezhu.getYezhuName()+"("+yezhu.getYezhuGuanxi()+")");
//		}
		holder.type.setText("删除");

		holder.type.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.arg1 = yezhu.getYezhuId();
				msg.what = 0;
				handler.sendMessage(msg);
			}
		});
		return convertView;
	}

	public List<CxwyYezhu> getListDatas() {
		return listDatas;
	}

	public void setListDatas(List<CxwyYezhu> listDatas) {
		this.listDatas = listDatas;
		notifyDataSetChanged();
	}

	class ViewHolder {
		TextView name;
		TextView tel;
		TextView type;
	}
}
