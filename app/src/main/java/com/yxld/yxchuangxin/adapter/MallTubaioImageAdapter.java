package com.yxld.yxchuangxin.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.entity.CxwyMallPezhi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yishangfei on 2016/3/7.
 */
public class MallTubaioImageAdapter extends BaseAdapter{
	List<CxwyMallPezhi> mlist = new ArrayList<CxwyMallPezhi>();
	Context mContext;

	public MallTubaioImageAdapter(List<CxwyMallPezhi> list, Context context) {
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
			holder.icon = (SimpleDraweeView) view.findViewById(R.id.main_icon);
			holder.name = (TextView) view.findViewById(R.id.main_name);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// 填充数据
		CxwyMallPezhi peizhi = mlist.get(position);
		// 填充控件
		if(peizhi.getMallPeizhiBeiyong() !=null){
			holder.name.setText(peizhi.getMallPeizhiBeiyong());
		}
		if(peizhi.getMallPeizhiValue() != null){
			Uri uri = Uri.parse(API.PIC + peizhi.getMallPeizhiValue());
			holder.icon.setImageURI(uri);
			Log.d("geek", "mall getView: uri="+uri);
		}
		return view;
	}

	public void setmList(List<CxwyMallPezhi> mList) {
		if(mList == null || mList.size() == 0){
			return;
		}
		this.mlist = mList;
		notifyDataSetChanged();
	}

	class ViewHolder {
		SimpleDraweeView icon;
		TextView name;
	}
}
