package com.yxld.yxchuangxin.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.entity.CxwyMallComment;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.view.LoadingImg;

@SuppressLint("InflateParams")
public class GoodsPraiseAdapter extends BaseAdapter {
	private Context mContext;

	private Integer index = -1;

	private List<CxwyMallComment> mDatas;// 存储的EditText值

	public GoodsPraiseAdapter(List<CxwyMallComment> mData, Context context) {
		this.mContext = context;
		this.mDatas = mData;
	}

	// 是获取显示数据的数量
	@Override
	public int getCount() {
		return mDatas.size();
	}

	// 获得当前位置的元素
	@Override
	public Object getItem(int arg0) {
		return mDatas.get(arg0);
	}

	// 获取当前控件的id号
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	// 创建list里面子元素
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public View getView(final int position, // 当前要构造的位置
			View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			// 视图构造器
			LayoutInflater lif = LayoutInflater.from(mContext);
			// 构造视图
			view = lif.inflate(R.layout.goods_praise_item_layout, null);
			// 找到控件
			holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
			holder.goods_praise = (RatingBar) view
					.findViewById(R.id.goods_praise);
			holder.goods_praise.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				
				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating,
						boolean fromUser) {
					Log.d("geek", "onRatingChanged"+rating);
					mDatas.get(position).setPingjiaLevel((int)rating);
				}
			});
			holder.goodImgs = (ImageView) view.findViewById(R.id.goodImgs);
			holder.edComment = (EditText) view.findViewById(R.id.edComment);
			holder.edComment.setTag(position);
			holder.edComment.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_UP) {
						index = (Integer) v.getTag();
					}
					return false;
				}
			});
			class MyTextWatcher implements TextWatcher {
				public MyTextWatcher(ViewHolder holder) {
					mHolder = holder;
				}

				private ViewHolder mHolder;

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
				}

				@Override
				public void afterTextChanged(Editable s) {
					if (s != null && !"".equals(s.toString())) {
						int position = (Integer) mHolder.edComment.getTag();
						mDatas.get(position).setPingjiaBody(s.toString());// 当EditText数据发生改变的时候存到data变量中
					}
				}
			}
			holder.edComment.addTextChangedListener(new MyTextWatcher(holder));
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
			holder.edComment.setTag(position);
		}
		// 填充数据
		CxwyMallComment sb = mDatas.get(position);
		// 填充控件 获取商品名称
		holder.tv_name.setText(sb.getPingjiaBeiyong1());

		holder.edComment.clearFocus();
		if (index != -1 && index == position) {
			holder.edComment.requestFocus();
		}

		String url = API.PIC+sb.getPingjiaImgSrc1();
		if(StringUitl.isNoEmpty(url)){
			if(url.contains(";")){
				url = url.replace(";","");
			}
			Contains.loadingImg.displayImage(url, holder.goodImgs,LoadingImg.option1);
		}

		return view;
	}

	public List<CxwyMallComment> getmData() {
		return mDatas;
	}

	public void setmData(List<CxwyMallComment> mData) {
		this.mDatas = mData;
	}

	class ViewHolder {
		TextView tv_name;
		EditText edComment;
		RatingBar goods_praise;
		ImageView goodImgs;
	}
}