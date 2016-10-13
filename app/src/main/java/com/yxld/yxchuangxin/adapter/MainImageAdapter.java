package com.yxld.yxchuangxin.adapter;

import java.util.List;

import com.yxld.yxchuangxin.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainImageAdapter extends BaseAdapter{
	List<MainIndexBean> mlist;
	Context mContext;

	public MainImageAdapter(List<MainIndexBean> list, Context context) {
		// TODO Auto-generated constructor stub
		mlist = list;
		mContext = context;
	}

	// 是获取显示数据的数量
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	// 获得当前位置的元素
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mlist.get(arg0);
	}

	// 获取当前控件的id号
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	// 创建list里面子元素
	@Override
	public View getView(int position, // 当前要构造的位置
			View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		final int p = position;
		if (view == null) {
			holder = new ViewHolder();
			// 视图构造器
			LayoutInflater lif = LayoutInflater.from(mContext);
			// 构造视图
			view = lif.inflate(R.layout.activity_main_item, null);
			// 找到控件
			holder.icon = (ImageView) view.findViewById(R.id.main_icon);
			holder.name = (TextView) view.findViewById(R.id.main_name);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// 填充数据
		MainIndexBean sb = mlist.get(position);
		// 填充控件
		holder.icon.setImageResource(sb.getIcon());
		holder.name.setText(sb.getName());

		return view;
	}

	class ViewHolder {
		ImageView icon;
		TextView name;
	}
}
