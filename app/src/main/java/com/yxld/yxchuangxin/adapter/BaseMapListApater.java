/**   
 * @Title: TodayHootAdpter.java 
 * @Package com.nmore.unclephone.adpter 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author wjysky520@gmail.com   
 * @date 2015年7月24日 下午5:00:16 
 * @version V1.0   
 */
package com.yxld.yxchuangxin.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.view.LoadingImg;

/**
 * @ClassName: BaseMapListApater
 * @Description: 公用的适配器
 * @author nmore_liu
 * @date 2015年7月24日 下午5:00:16
 * 
 */
public class BaseMapListApater extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<HashMap<String, Object>> list;
	private int layoutID;
	private String flag[];
	private int ItemIDs[];
	public Bundle bundle;
	Context context;
	int goodsPosition;

	public BaseMapListApater(Context context,
			ArrayList<HashMap<String, Object>> list, int layoutID,
			String flag[], int ItemIDs[]) {
		this.mInflater = LayoutInflater.from(context);
		this.list = list;
		this.layoutID = layoutID;
		this.flag = flag;
		this.ItemIDs = ItemIDs;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(layoutID, null);
		for (int i = 0; i < flag.length; i++) {
			if (convertView.findViewById(ItemIDs[i]) instanceof ImageView) {
				ImageView iv = (ImageView) convertView.findViewById(ItemIDs[i]);
				if (ItemIDs[i] == R.id.goodsImg ) {
					String imgUrl = (String) list.get(position).get(flag[i]);
					if(imgUrl==null || "".equals(imgUrl)){
						iv.setVisibility(View.GONE);
					}else{
						Contains.loadingImg.displayImage(imgUrl
								+ "", iv, LoadingImg.option1);
					}
				}else{
					int imgRid = (Integer) list.get(position).get(flag[i]);
					iv.setImageResource(imgRid);
				}
			} else if (convertView.findViewById(ItemIDs[i]) instanceof TextView) {
				TextView tv = (TextView) convertView.findViewById(ItemIDs[i]);
				String text = (String) list.get(position).get(flag[i]);
				tv.setText(text);
			}
			else {
				
			}
		}
		return convertView;
	}

	public ArrayList<HashMap<String, Object>> getList() {
		return list;
	}

	public void setList(ArrayList<HashMap<String, Object>> list) {
		this.list = list;
	}
}