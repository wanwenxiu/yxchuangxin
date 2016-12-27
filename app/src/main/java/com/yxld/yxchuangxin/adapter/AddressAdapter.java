package com.yxld.yxchuangxin.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.order.addAddressActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.entity.CxwyMallAdd;

/**
 * @ClassName: AddressAdapter 
 * @Description: 收货地址适配器
 * @author wwx
 * @date 2016年4月5日 下午2:40:00 
 *
 */
@SuppressLint("InflateParams")
public class AddressAdapter extends BaseAdapter {
	/** 删除地址*/
	private final int ADDRESS_DELETE  = 1;
	/** 默认地址*/
	private final int ADDRESS_DEFALU  = 2;
	private List<CxwyMallAdd> mList;
	Context mContext;
	private Handler mhandler;

	public AddressAdapter(List<CxwyMallAdd> list, Context context,Handler handler) {
		mList = list;
		mContext = context;
		mhandler = handler;
	}

	// 是获取显示数据的数量
	@Override
	public int getCount() {
		return mList.size();
	}

	// 获得当前位置的元素
	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	// 获取当前控件的id号
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	// 创建list里面子元素
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, // 当前要构造的位置
						View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			// 视图构造器
			LayoutInflater lif = LayoutInflater.from(mContext);
			// 构造视图
			view = lif.inflate(R.layout.delivery_address_item, null);
			// 找到控件
			holder.tv_name = (TextView) view.findViewById(R.id.delivery_address_name);
			holder.tv_tel = (TextView) view.findViewById(R.id.delivery_address_tel);
			holder.tv_address = (TextView) view.findViewById(R.id.delivery_address);
			holder.delete = (TextView) view.findViewById(R.id.delete);
			holder.update = (TextView) view.findViewById(R.id.update);
			holder.defule = (TextView) view.findViewById(R.id.defule);
			holder.img = (ImageView) view.findViewById(R.id.img);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// 填充数据
		final CxwyMallAdd sb = mList.get(position);
		// 填充控件
		holder.tv_name.setText(sb.getAddName());
		holder.tv_tel.setText(sb.getAddTel());
		holder.tv_address.setText(sb.getAddSpare1()+" "+sb.getAddVillage()+sb.getAddAdd());

		holder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.arg1 = sb.getAddId();
				msg.what = ADDRESS_DELETE;
				mhandler.sendMessage(msg);
			}
		});
		
		holder.update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("address", sb);
				intent.setClass(mContext, addAddressActivity.class);
				mContext.startActivity(intent);
			}
		});
		
		holder.defule.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.arg1 = sb.getAddId();
				msg.what = ADDRESS_DEFALU;
				mhandler.sendMessage(msg);				
			}
		});
		
		holder.img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.arg1 = sb.getAddId();
				msg.what = ADDRESS_DEFALU;
				mhandler.sendMessage(msg);			
			}
		});
		
		//0为默认地址，1不位默认地址
		if(sb.getAddStatus() == 0){
			holder.defule.setText("默认地址");
			holder.defule.setTextColor(mContext.getResources().getColor(R.color.color_main_color));
			holder.defule.setClickable(false);
			holder.img.setImageResource(R.mipmap.cart_06);
			holder.img.setClickable(false);
			Contains.defuleAddress = sb;
		}else{
			holder.defule.setText("设为默认");
			holder.defule.setTextColor(mContext.getResources().getColor(R.color.black));
			holder.defule.setClickable(true);
			holder.img.setImageResource(R.mipmap.cart_03);
			holder.img.setClickable(true);
		}
		
		return view;
	}

	class ViewHolder {
		TextView tv_name;
		TextView tv_tel;
	    TextView tv_address;
	    TextView delete;
	    TextView update;
	    TextView defule;
	    ImageView img;
	}

	public List<CxwyMallAdd> getmList() {
		return mList;
	}

	public void setmList(List<CxwyMallAdd> mList) {
		this.mList = mList;
		notifyDataSetChanged();
	}
	
}