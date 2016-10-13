package com.yxld.yxchuangxin.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.MainImageAdapter.ViewHolder;
import com.yxld.yxchuangxin.entity.CxwyMallAdd;
import com.yxld.yxchuangxin.entity.ImageBean;
import com.yxld.yxchuangxin.util.ImagesLoader;
import com.yxld.yxchuangxin.util.UIListener;

/**
 * Created by yishangfei on 2016/3/7.
 */
public class MallImageAdapter extends BaseAdapter{
	List<MainIndexBean> mlist;
	Context mContext;

	public MallImageAdapter(List<MainIndexBean> list, Context context) {
		mlist = list;
		mContext = context;
	}

	// 是获取显示数据的数量
	@Override
	public int getCount() {
		return mlist.size();
	}

	// 获得当前位置的元素
	@Override
	public Object getItem(int arg0) {
		return mlist.get(arg0);
	}

	// 获取当前控件的id号
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	// 创建list里面子元素
	@Override
	public View getView(int position, // 当前要构造的位置
			View view, ViewGroup arg2) {
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

	public void setmList(List<MainIndexBean> mList) {
		this.mlist = mList;
		notifyDataSetChanged();
	}

	class ViewHolder {
		ImageView icon;
		TextView name;
	}
}
