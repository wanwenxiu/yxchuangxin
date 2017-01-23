package com.yxld.yxchuangxin.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.entity.CxwyBaoxiu;

/**
 * @ClassName: RepairListItemAdapter
 * @Description: 报修适配器
 * @author wwx
 * @date 2015年12月15日 上午10:58:31
 */
@SuppressLint("InflateParams")
public class RepairListItemAdapter extends BaseAdapter {
	
	private List<CxwyBaoxiu> listOrderDatas = new ArrayList<CxwyBaoxiu>();

	/** 运行上下文 */
	private Context mContext = null;
	/** 视图容器 */
	private LayoutInflater listContainer;
	/** 自定义视图 */
	private ListItemView listItemView = null;
	
	public RepairListItemAdapter(Context mContext,List<CxwyBaoxiu> listOrderDatas) {
		this.mContext = mContext;
		this.listOrderDatas = listOrderDatas;
		listContainer = LayoutInflater.from(mContext);
	}

	class ListItemView { // 自定义控件集合
		/** 报修单号*/
		private TextView repairId;
		/** 维修时间*/
		//private TextView repairTime;
		/** 维修状态 */
		public TextView repairState;
		public TextView repairDestail;
		/** 订单信息 */
		private GridView gvOrderInfo = null;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(
					R.layout.repair_list_item_layout, null);
			listItemView = new ListItemView();
			listItemView.repairId  = (TextView) convertView
					.findViewById(R.id.repairId);
//			listItemView.repairTime  = (TextView) convertView
//					.findViewById(R.id.repairTime);
			listItemView.repairState = (TextView) convertView
					.findViewById(R.id.repairState);
			listItemView.repairDestail = (TextView) convertView
					.findViewById(R.id.repairDestail);
			listItemView.gvOrderInfo = (GridView) convertView
					.findViewById(R.id.gvOrderInfo);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		
		CxwyBaoxiu order = listOrderDatas.get(position);
		listItemView.repairId.setText(order.getBaoxiuDanhao()+"		"+order.getBaoxiuLrdate());
		setState(order.getBaoxiuStatus());
		listItemView.repairDestail.setText(order.getBaoxiuProject());
		
		String src = order.getBaoxiuPicture();
		if(src==null ||"".equals(src)){
			listItemView.gvOrderInfo.setVisibility(View.GONE);
		}else{
			String[] srcArray;
			if(src.contains(";")){
				srcArray = src.split(";");
			}else{
				srcArray = new String[1];
				srcArray[0] = src;
			}
			listItemView.gvOrderInfo.setVisibility(View.VISIBLE);
			updateData(listItemView.gvOrderInfo,srcArray);
		}
		return convertView;
	}

	@Override
	public int getCount() {
		return listOrderDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return listOrderDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 更新二级分类详情数据的方法
	 */
	public void updateData(GridView listView,String[] srcs) {
		// 创建一个集合来装图片和文字
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		// 装在图片和文字
		for (int x = 0; x < srcs.length; x++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("goodsImg", API.PIC+srcs[x]);
			lstImageItem.add(map);
		}
		BaseMapListApater sm = new BaseMapListApater(mContext, lstImageItem,
				R.layout.imageview_item, new String[] { "goodsImg" },
				new int[] { R.id.goodsImg});
		listView.setAdapter(sm);
	}

	public List<CxwyBaoxiu> getListOrderDatas() {
		return listOrderDatas;
	}

	public void setListOrderDatas(List<CxwyBaoxiu> listOrderDatas) {
		this.listOrderDatas = listOrderDatas;
		notifyDataSetChanged();
	}

	private void  setState(String states){
	//	状态，1：待指派，2：已指派，3：查看现场中，4：申请材料，5：物业中心审批中，6：公司总部审批中，7：维修中，8：物业中心查验中，9：公司总部查验中，10：回访中，11：关闭
		if("1".equals(states)){
			listItemView.repairState.setText("待指派");
		}else if("2".equals(states)){
			listItemView.repairState.setText("已指派");
		}else if("3".equals(states)){
			listItemView.repairState.setText("查看现场中");
		}else if("4".equals(states)){
			listItemView.repairState.setText("申请材料");
		}else if("5".equals(states)){
			listItemView.repairState.setText("物业中心审批中");
		}else if("6".equals(states)){
			listItemView.repairState.setText("公司总部审批中");
		}else if("7".equals(states)){
			listItemView.repairState.setText("维修中");
		}else if("8".equals(states)){
			listItemView.repairState.setText("物业中心查验中");
		}else if("9".equals(states)){
			listItemView.repairState.setText("公司总部查验中");
		}else if("10".equals(states)){
			listItemView.repairState.setText("回访中");
		}else if("11".equals(states)){
			listItemView.repairState.setText("关闭");
		}else{
			listItemView.repairState.setText(states);
		}
	}
	
	/**
	 * 添加数据
	 * 
	 * @param list
	 */
	public void addData(List<CxwyBaoxiu> list) {
		if(list != null){
			this.listOrderDatas.addAll(list);
			notifyDataSetChanged();
		}
	}
	
	
}