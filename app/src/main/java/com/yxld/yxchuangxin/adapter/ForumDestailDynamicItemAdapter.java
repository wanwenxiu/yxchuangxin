package com.yxld.yxchuangxin.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.entity.CxwyMallComment;

/**
 * @ClassName: ForumDestailDynamicItemAdapter 
 * @Description: 论坛评价
 * @author 1152008367@qq.com
 * @date 2015年11月23日 下午3:17:18 
 *
 */
@SuppressLint("InflateParams")
public class ForumDestailDynamicItemAdapter extends BaseAdapter {

	/** 运行上下文 */
	private Context mContext = null;
	/** 数据集合 */
	private List<CxwyMallComment> listData = new ArrayList<CxwyMallComment>();

	/** 视图容器 */
	private LayoutInflater listContainer;
	/** 自定义视图 */
	private ListItemView listItemView = null;

	 class ListItemView { // 自定义控件集合
		/** 头像 */
		public ImageView goodsImg;
		/** 用户名 */
		public TextView dynamicName;
		/** 设备*/
		public TextView dynamicDevice;
		/** 时间 */
		public TextView dynamicTime;
		/** 楼*/
		public TextView dynamicSum;
		/** 内容*/
		public TextView dynamicContent;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ForumDestailDynamicItemAdapter(Context context,List<CxwyMallComment> listData) {
		this.mContext = context;
		this.listData = listData;
		this.listContainer = LayoutInflater.from(mContext); // 创建视图容器并设置上下文
	}

	public int getCount() {
		return listData.size();
	}

	public Object getItem(int arg0) {
		return listData.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(
					R.layout.forum_destail_dynamic_item, null);
			listItemView = new ListItemView();
			listItemView.goodsImg = (ImageView) convertView
					.findViewById(R.id.dynamicImg);
			listItemView.dynamicName = (TextView) convertView
					.findViewById(R.id.dynamicName);
			listItemView.dynamicDevice = (TextView) convertView
					.findViewById(R.id.dynamicDevice);
			listItemView.dynamicContent = (TextView) convertView
					.findViewById(R.id.dynamicContent);
			listItemView.dynamicTime = (TextView) convertView
					.findViewById(R.id.dynamicTime);
			listItemView.dynamicSum = (TextView) convertView
					.findViewById(R.id.dynamicSum);
			
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		// 加载数据
		final CxwyMallComment comment = listData.get(position);
		if(comment.getPingjiaBody() == null || "".equals(comment.getPingjiaBody())){
			listItemView.dynamicContent.setText("用户未留下评论~~");
		}else{
			listItemView.dynamicContent.setText(comment.getPingjiaBody());
		}
		if(comment.getPingjiaName() == null || "".equals(comment.getPingjiaName())){
			listItemView.dynamicName.setText("匿名用户");
		}else{
			listItemView.dynamicName.setText(comment.getPingjiaName());
		}
		listItemView.dynamicTime.setText(comment.getPingjiaNowTime());
		listItemView.dynamicSum.setText(position+"楼");
		return convertView;
	}

	public List<CxwyMallComment> getListData() {
		return listData;
	}

	public void setListData(List<CxwyMallComment> listData) {
		this.listData = listData;
		notifyDataSetChanged();
	}

	/**
	 * 添加数据
	 * 
	 * @param list
	 */
	public void addData(List<CxwyMallComment> list) {
		if(list != null){
			this.listData.addAll(list);
			notifyDataSetChanged();
		}
	}
	
}