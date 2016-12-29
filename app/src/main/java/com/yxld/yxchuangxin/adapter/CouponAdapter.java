package com.yxld.yxchuangxin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.entity.CxwyMallUseDaijinquan;

import java.util.List;

/**
 * Created by yishangfei on 2016/8/2 0002.
 */
public class CouponAdapter extends BaseAdapter {
	List<CxwyMallUseDaijinquan> mlist;
	Context mContext;

	public CouponAdapter(List<CxwyMallUseDaijinquan> list, Context context) {
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
		// TODO Auto-generated method stub
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
			view = lif.inflate(R.layout.activity_coupon_item, null);
			// 找到控件
			holder.tv_money = (TextView) view.findViewById(R.id.coupon_money);
			holder.tv_red1 = (TextView) view.findViewById(R.id.red1);
			holder.tv_red2 = (TextView) view.findViewById(R.id.red2);
			holder.tv_red3 = (TextView) view.findViewById(R.id.red3);
			holder.tv_red4 = (TextView) view.findViewById(R.id.red4);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// 填充数据
		CxwyMallUseDaijinquan sb = mlist.get(position);
		// 填充控件
		String aa=sb.getDaijinquanUseShoujihao();
		int n=4;
		String b=aa.substring(aa.length()-n,aa.length());
		holder.tv_money.setText(sb.getDaijinquanUseJine()+"元");
		holder.tv_red1.setText(sb.getDaijinquanUseType());
		holder.tv_red2.setText("满"+sb.getDaijinquanUseShiyongjia()+"元可用");
		holder.tv_red3.setText("限"+b+"使用");
		holder.tv_red4.setText(sb.getDaijinquanUseEndtime()+"到期");
		return view;
	}

	class ViewHolder {
		TextView tv_money;
		TextView tv_red1;
		TextView tv_red2;
		TextView tv_red3;
		TextView tv_red4;
	}
}
