package com.yxld.yxchuangxin.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.entity.CxwyMallProduct;
import com.yxld.yxchuangxin.view.LoadingImg;

/**
 * @ClassName: shopListAdapter
 * @Description: 商品列表适配器
 * @author wwx
 * @date 2016年3月17日 下午3:00:26
 */
@SuppressLint("InflateParams")
public class MallIndexGoodsListAdapter extends BaseAdapter {
	
	
	private LayoutInflater mInflater;
	private List<CxwyMallProduct> mlist;
	private Context mContext;

	public MallIndexGoodsListAdapter(List<CxwyMallProduct> list, Context context) {
		this.mInflater = LayoutInflater.from(context);
		this.mlist = list;
		this.mContext = context;
	}

	// 是获取显示数据的数量
	@Override
	public int getCount() {
//		if(mlist.size()>6){
//			return  6;
//		}else{
			return mlist.size();
//		}
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
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			// 构造视图
			view = mInflater.inflate(R.layout.main_index_goods_list_item, null);
			// 找到控件
			holder.iv = (SimpleDraweeView) view.findViewById(R.id.goodsImg);
			holder.tv_name = (TextView) view
					.findViewById(R.id.name);
			holder.hot = (ImageView) view.findViewById(R.id.hot);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if (this.mlist != null) {

			CxwyMallProduct curProduct = mlist.get(position);

			// 加载图片
			if (curProduct.getShangpinImgSrc1() != null
					&& !"".equals(curProduct.getShangpinImgSrc1())) {
				Uri uri = Uri.parse(API.PIC + curProduct.getShangpinImgSrc1().split(";")[0]);
				holder.iv.setImageURI(uri);
//				LoadingImg.LoadingImgs(mContext).displayImage(
//						API.PIC + curProduct.getShangpinImgSrc1().split(";")[0], holder.iv,
//						LoadingImg.option1);
			}
			holder.tv_name.setText("¥"+curProduct.getShangpinRmb()+"");
			
			if(position == 0){
				holder.hot.setVisibility(View.VISIBLE);
			}else{
				holder.hot.setVisibility(View.GONE);
			}
		}
		return view;
	}
	

	/**
	 * 添加数据
	 * 
	 * @param list
	 */
	public void addData(List<CxwyMallProduct> list) {
		if(list != null){
			this.mlist.addAll(list);
			notifyDataSetChanged();
		}
	}

	/**
	 * 刷新列表数据
	 * 
	 * @param list
	 */
	public void refreshData(List<CxwyMallProduct> list) {
		this.mlist = list;
		notifyDataSetChanged();
	}

	public List<CxwyMallProduct> getMlist() {
		return mlist;
	}

	class ViewHolder {
		SimpleDraweeView iv;
		TextView tv_name;
		ImageView hot;
	}
}