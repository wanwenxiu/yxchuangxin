package com.yxld.yxchuangxin.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.index.FeiYongDestailActivity;
import com.yxld.yxchuangxin.entity.WuyeRecordAndroid;


/**
 * @ClassName: FeiYongDestailAdapter 
 * @Description: 费用详情适配器 
 * @author 1152008367@qq.com
 * @date 2015年8月19日 下午2:21:02 
 */
@SuppressLint("InflateParams")
public class FeiYongDestailAdapter extends BaseAdapter {
	/**
	 * 打印信息Tag
	 */
	private String LOG_TAG = "geek";
	/** 运行上下文 */
	private Context mContext = null;
	
	/** 数据集合 */
	private List<WuyeRecordAndroid> listData = new ArrayList<WuyeRecordAndroid>();
	/** 视图容器 */
	private LayoutInflater listContainer;
	/** 自定义视图 */
	private ListItemView listItemView = null;
	/** 更新视图*/
	private Handler handler = null;
	
	private float totalPrice; 

	 class ListItemView { // 自定义控件集合
		/** 选中状态*/
		public CheckBox cartGoodsChecked;
		/** 商品名称 */
		public TextView time;
		/** 商品价格 */
		public TextView price;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param list
	 * @param handler
	 */
	public FeiYongDestailAdapter(Context context,
			List<WuyeRecordAndroid> list,Handler handler) {
		this.mContext = context;
		this.listData = list;
		this.handler = handler;
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
			convertView = listContainer.inflate(R.layout.feiyong_destail_list_item,
					null);
			listItemView = new ListItemView();
			listItemView.cartGoodsChecked = (CheckBox) convertView
					.findViewById(R.id.cartIsSelect);
			listItemView.time = (TextView) convertView
					.findViewById(R.id.time);
			listItemView.price = (TextView) convertView
					.findViewById(R.id.price);

			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		
		//加载数据
		final WuyeRecordAndroid goodsVo = listData.get(position);
		Log.d(LOG_TAG, "goods ="+goodsVo);
		if(goodsVo.getTime() != null){
			listItemView.time.setText(goodsVo.getTime()+"-"+goodsVo.getBeiyong());
		}
		if(goodsVo.getMmoney()!= null){
			listItemView.price.setText(goodsVo.getMmoney());
		}
		
		listItemView.cartGoodsChecked.setChecked(goodsVo.isChecked());
		
		//设置点击事件
		listItemView.cartGoodsChecked.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				listData.get(position).setChecked(isChecked);
				if(!isChecked){
					handler.sendEmptyMessage(FeiYongDestailActivity.updateNoCheck);
				}
				int count = 0;
				totalPrice = 0.0f;
				for (int i = 0; i < listData.size(); i++) {
					WuyeRecordAndroid goodsVo =  listData.get(i);
					if(goodsVo.isChecked()){
						totalPrice += Float.parseFloat(listData.get(i).getMmoney());
						count++;
					}
				}
				if(count == listData.size()){
					handler.sendEmptyMessage(FeiYongDestailActivity.updateAllCheck);
				}
				handler.sendEmptyMessage(FeiYongDestailActivity.updateCurPrise);
			}
		}
			);
		return convertView;
	}

	public List<WuyeRecordAndroid> getListData() {
		return listData;
	}

	public void setListData(List<WuyeRecordAndroid> listData) {
		this.listData = listData;
		notifyDataSetChanged();
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}