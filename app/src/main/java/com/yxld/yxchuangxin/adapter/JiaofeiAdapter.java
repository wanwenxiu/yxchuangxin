package com.yxld.yxchuangxin.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.entity.WuyeRecordAndroid;

public class JiaofeiAdapter extends BaseAdapter {
	List<WuyeRecordAndroid> mlist;
	Context mContext;

	public JiaofeiAdapter(List<WuyeRecordAndroid> list, Context context) {
		mlist = list;
		mContext = context;
	}

	@Override
	public int getCount() {
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			// 获取list_item布局文件的视图
			LayoutInflater lif = LayoutInflater.from(mContext);
			// 构造视图
			convertView = lif.inflate(R.layout.activity_jiaofei_list_item, null);
			holder = new ViewHolder();
			holder.pay_time = (TextView) convertView
					.findViewById(R.id.pay_time);
			holder.payable_amount = (TextView) convertView
					.findViewById(R.id.payable_amount);
			holder.pay_type = (TextView) convertView
					.findViewById(R.id.pay_type);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 加载数据
		final WuyeRecordAndroid jf = mlist.get(position);

		holder.pay_type.setText(jf.getType());
		holder.payable_amount.setText("金额"+jf.getMmoney());
		holder.pay_time.setText("时间"+jf.getTime()+"-"+jf.getBeiyong());
		return convertView;
	}

	public List<WuyeRecordAndroid> getListDatas() {
		return mlist;
	}

	public void setListDatas(List<WuyeRecordAndroid> listDatas) {
		this.mlist = listDatas;
		notifyDataSetChanged();
	}

	class ViewHolder {
		TextView pay_type;
		TextView payable_amount;
		TextView pay_time;
	}

}
