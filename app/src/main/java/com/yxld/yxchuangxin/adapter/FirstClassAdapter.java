package com.yxld.yxchuangxin.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.entity.CxwyMallClassify;

/**
 * @ClassName: FirstClassAdapter 
 * @Description: 一级分类适配器 
 * @author wwx
 * @date 2016年3月10日 下午4:37:03 
 */
@SuppressLint("InflateParams")
public class FirstClassAdapter extends BaseAdapter {
   private  Context mContext;
   private  List<CxwyMallClassify> listDatas;
   
   /** 视图容器 */
	private LayoutInflater listContainer;
	/** 自定义视图 */
	private ViewHolder holder = null;

    public FirstClassAdapter(List<CxwyMallClassify> listData, Context context) {
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

    @SuppressWarnings("deprecation")
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.textview_item,
					null);
			holder = new ViewHolder();
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.firstClassName);

			// 设置控件集到convertView
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		//加载数据
		final CxwyMallClassify goodsVo = listDatas.get(position);
		
		holder.tv_name.setText(goodsVo.getClassifyOne()+"");
		
		//没选中
		if(!goodsVo.isCheck()){
			holder.tv_name.setBackgroundResource(R.drawable.class_left_list_selecter);
			holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.black));
		}else{
			holder.tv_name.setBackgroundColor(mContext.getResources().getColor(R.color.white));
			holder.tv_name.setTextColor(mContext.getResources().getColor(R.color.color_main_color));
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
        TextView tv_name;
    }
}