package com.yxld.yxchuangxin.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.goods.GoodsDestailActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.CartController;
import com.yxld.yxchuangxin.controller.impl.CartControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallProduct;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.CxUtil;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.view.LoadingImg;

/**
 * @ClassName: shopListAdapter
 * @Description: 商品列表适配器
 * @author wwx
 * @date 2016年3月17日 下午3:00:26
 */
@SuppressLint("InflateParams")
public class shopListAdapter extends BaseAdapter {
	
	private CartController cartController;
	/**
	 * 网络请求列队
	 */
	private RequestQueue mRequestQueues;

	private LayoutInflater mInflater;
	private List<CxwyMallProduct> mlist;
	private Context mContext;
	private int num;
	private Window window;
	private TextView views;
	private ImageView buyImg;// 这是在界面上跑的小图片
	private ViewGroup anim_mask_layout;// 动画层
	private RelativeLayout imgView;
	public int[] start_locations;

	/** 上一次点击时间*/
	private long lastClickTime = 0;

	public shopListAdapter(RequestQueue mRequestQueue,List<CxwyMallProduct> list, Context context, Window window,RelativeLayout imgView,TextView view) {
		this.mRequestQueues = mRequestQueue;
		this.mInflater = LayoutInflater.from(context);
		this.mlist = list;
		this.mContext = context;
		this.window = window;
		this.imgView = imgView;
		this.views = view;
		if (cartController == null) {
			cartController = new CartControllerImpl();
		}
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
			view = mInflater.inflate(R.layout.sales_item, null);
			// 找到控件
			holder.iv = (SimpleDraweeView) view.findViewById(R.id.recommendListImgs);
			holder.tv_name = (TextView) view
					.findViewById(R.id.recommendListName);
			 holder.recommendCunt = (TextView) view.findViewById(R.id.recommendCunt);
			holder.tv_money = (TextView) view.findViewById(R.id.recommendPrice);
			holder.bt_button = (ImageView) view
					.findViewById(R.id.cate_joinCart);
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
			}else{
				holder.iv.setImageURI(API.PIC+"/wygl/files/img/201605/empty_photo.png");
			}

			holder.tv_name.setText(curProduct.getShangpinShangpName()+"	"+curProduct.getShangpinGuige());
			holder.tv_money.setText("￥"+curProduct.getShangpinRmb() + " ");
			holder.recommendCunt.setText("库存:"+curProduct.getShangpinNum()+"");
			if(curProduct.getShangpinHave() != null && curProduct.getShangpinHave() == 0){
				holder.recommendCunt.setText("已缺货");
			}

			if (holder.bt_button != null) {
				holder.bt_button.setTag(position);
				holder.bt_button
						.setOnClickListener(new setJoinCartClickListen());
			}
		}
		return view;
	}
	
	// 点击热门商品Item上面的购物车的按钮的时候 把那一条信息加入进购物车中
		class setJoinCartClickListen implements OnClickListener {

			@Override
			public void onClick(View v) {
				if(lastClickTime != 0 && (System.currentTimeMillis()-lastClickTime) < 1000){
                	Toast.makeText(mContext, "客官别急嘛 O(∩_∩)O", Toast.LENGTH_SHORT).show();
					return;
				}
				lastClickTime = System.currentTimeMillis();

				num = Integer.parseInt(v.getTag().toString());
				if( mlist.get(num).getShangpinNum() == 0){
					ToastUtil.show(mContext,"商品缺货哦");
					return;
				}
				if(mlist.get(num).getShangpinHave() != null &&mlist.get(num).getShangpinHave() == 0){
					ToastUtil.show(mContext,"商品已经缺货哦");
					return;
				}
				start_locations = new int[2];
				v.getLocationInWindow(start_locations);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
				//请求后台进行添加商品至购物车请求
				Map<String, String> params = new HashMap<String, String>();
				params.put("cart.cartShangpNum", mlist.get(num).getShangpinId()+"");
				params.put("cart.cartShangpName", mlist.get(num).getShangpinShangpName());
				params.put("cart.cartOneRmb", mlist.get(num).getShangpinRmb()+"");
				params.put("cart.cartImgSrc", mlist.get(num).getShangpinImgSrc1());
				params.put("cart.cartSpec", mlist.get(num).getShangpinGuige());
				params.put("cart.cartNum", "1");
				params.put("cart.cartSpare1",Contains.user.getYezhuId()+"");
				params.put("cart.cartSpare2", mlist.get(num).getShangpinBeiyong5());
				Log.d("geek", "商品列表添加购物车 params"+params.toString());
				cartController.addInfoToCart(mRequestQueues, params, addCartListener);
			}

		}
		
		private void setAnim(final View v, int[] start_location) {
			anim_mask_layout = null;
			anim_mask_layout = createAnimLayout();
			anim_mask_layout.addView(v);// 把动画小球添加到动画层
			final View view = addViewToAnimLayout(anim_mask_layout, v,
					start_location);
			int[] end_location = new int[2];// 这是用来存储动画结束位置的X、Y坐标
			imgView.getLocationInWindow(end_location);// shopCart是那个购物车

			// 计算位移
			int endX = end_location[0] - start_location[0];// 动画位移的X坐标
			int endY = end_location[1] - start_location[1];// 动画位移的y坐标
			TranslateAnimation translateAnimationX = new TranslateAnimation(0,
					endX + 30, 0, 0);
			translateAnimationX.setInterpolator(new LinearInterpolator());
			translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
			translateAnimationX.setFillAfter(true);

			TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
					0, endY);
			translateAnimationY.setInterpolator(new AccelerateInterpolator());
			translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
			translateAnimationX.setFillAfter(true);

			AnimationSet set = new AnimationSet(false);
			set.setFillAfter(false);
			set.addAnimation(translateAnimationY);
			set.addAnimation(translateAnimationX);
			set.setDuration(800);// 动画的执行时间
			view.startAnimation(set);
			// 动画监听事件
			set.setAnimationListener(new AnimationListener() {
				// 动画的开始
				@Override
				public void onAnimationStart(Animation animation) {
					v.setVisibility(View.VISIBLE);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				// 动画的结束
				@Override
				public void onAnimationEnd(Animation animation) {
					v.setVisibility(View.GONE);
					if (views.getVisibility() == View.GONE) {
						views.setVisibility(view.VISIBLE);
					}
					views.setText(Contains.cartTotalNum+"");
					CxUtil.actionAndAction(imgView, mContext);
				}
			});

		}

		/**
		 * @Description: 创建动画层
		 * @param
		 * @return void
		 * @throws
		 */
		private ViewGroup createAnimLayout() {
			ViewGroup rootView = (ViewGroup) window.getDecorView();
			LinearLayout animLayout = new LinearLayout(mContext);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			animLayout.setLayoutParams(lp);
			animLayout.setId(Integer.MAX_VALUE);
			animLayout.setBackgroundResource(android.R.color.transparent);
			rootView.addView(animLayout);
			return animLayout;
		}

		private View addViewToAnimLayout(final ViewGroup vg, final View view,
				int[] location) {
			int x = location[0];
			int y = location[1];
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.leftMargin = x;
			lp.topMargin = y;
			view.setLayoutParams(lp);
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
		TextView recommendCunt;
		TextView tv_money;
		ImageView bt_button;
	}
	
	/** 添加至购物车请求*/
	private ResultListener<BaseEntity> addCartListener = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			if (info.status != 0) {
					ToastUtil.show(mContext, ((BaseEntity) info).MSG);
				return;
			}
			Contains.cartTotalNum++;
			//请求成功，开始执行动画
			buyImg = new ImageView(mContext);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
			buyImg.setImageResource(R.mipmap.sign);// 设置buyImg的图片
			setAnim(buyImg, start_locations);// 开始执行动画
		}

		@Override
		public void onErrorResponse(String errMsg) {
			Toast.makeText(mContext, "添加失败", Toast.LENGTH_SHORT).show();
		}
	};
}