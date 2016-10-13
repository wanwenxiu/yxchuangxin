package com.yxld.yxchuangxin.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.entity.CxwyMallClassify;
import com.yxld.yxchuangxin.view.LoadingImg;

/**
 * @ClassName: SecondClassListAdapter
 * @Description: 二级分类适配器
 * @author wwx
 * @date 2016年3月11日 上午9:42:50
 *
 */
@SuppressLint("InflateParams")
public class SecondClassListAdapter extends BaseAdapter {
	private Context mContext;
	private List<CxwyMallClassify> listDatas;

	/** 视图容器 */
	private LayoutInflater listContainer;
	/** 自定义视图 */
	private ViewHolder holder = null;

	public SecondClassListAdapter(List<CxwyMallClassify> listData,
			Context context) {
		this.mContext = context;
		this.listDatas = listData;
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
					R.layout.main_index_menu_list_item_layout, null);
			holder = new ViewHolder();
			holder.secondName = (TextView) convertView
					.findViewById(R.id.menu_tv);
			holder.secondImg = (ImageView) convertView
					.findViewById(R.id.menu_img);
			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 加载数据
		final CxwyMallClassify goodsVo = listDatas.get(position);

		holder.secondName.setText(goodsVo.getClassifyTwo());

		Log.d("geek","图片路径="+API.PIC+goodsVo.getClassifyImgSrc());

		//加载图片
		if(goodsVo.getClassifyImgSrc() != null && !"".equals(goodsVo.getClassifyImgSrc())){
			Contains.loadingImg.displayImage(API.PIC+goodsVo.getClassifyImgSrc(), holder.secondImg,
					LoadingImg.option1);
		}
		return convertView;
	}

	public List<CxwyMallClassify> getListDatas() {
		return listDatas;
	}

	public void setListDatas(List<CxwyMallClassify> listDatas) {
		this.listDatas = listDatas;
	}

	class ViewHolder {
		TextView secondName;
		ImageView secondImg;
	}
}
