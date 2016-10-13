package com.yxld.yxchuangxin.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.yxld.yxchuangxin.R;

/**
 * 引导界面基础类
 * 
 * @author wwx
 * 
 */
@SuppressLint({ "WorldReadableFiles", "HandlerLeak" })
public abstract class GuideBaseActivity extends Activity implements
		OnTouchListener {

	/** 屏幕可用区域 高 */
	@SuppressWarnings("unused")
	private int dh = 0;
	/** 立即体验图片 left */
	private int mLeft = 0;
	/** 立即体验图片 top */
	private int mTop = 0;
	/** 立即体验图片 width */
	private int mWidth = 0;
	/** 立即体验图片 height */
	private int mHeight = 0;

	/** 到达最后一张 */
	private static final int TO_THE_END = 0;
	/** 离开最后一张 */
	private static final int LEAVE_FROM_END = 1;
	/** 图片view集合 */
	private List<View> guides = new ArrayList<View>();
	/** ViewPager对象 */
	private ViewPager pager = null;
	/** 最外层布局 */
	private RelativeLayout mRlGuideMain = null;
	/** 点击体验 */
	private ImageView ivStart = null;
	/** 底部圆点 */
	private ImageView curPoint = null;
	/** 存储圆点的容器 */
	private LinearLayout pointContain = null;
	/** 跳过 */
	private ImageView pass = null;
	/** 位移量 */
	private int offset = 0;
	/** 记录当前的位置 */
	private int curPos = 0;
	/** 图片集 */
	private Integer[] mImagesArray = null;
	/** 手指初始位置 */
	private int lastX = 0;

	private SharedPreferences sp = null;
	/** 记录是否安装次数 */
	private int installCount = 0;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		create();
		sp = getSharedPreferences("COUNT", MODE_WORLD_READABLE);
		installCount = sp.getInt("COUNT", 0);
		Editor e = sp.edit();
		e.putInt("COUNT", ++installCount);
		e.commit();
		if (installCount > 1) {
			startJumpActivity();
			return;
		}

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide_base_activity);
		findViewById();
		mImagesArray = initImagesArray();
		if (mImagesArray != null) {
			initView();
		} else {
			mRlGuideMain.setBackgroundResource(R.mipmap.background_logo);
			pager.setOnTouchListener(this);
			ivStart.setVisibility(View.VISIBLE);
			curPoint.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		initDot();
		ImageView iv = null;
		guides.clear();
		for (int i = 0; i < mImagesArray.length; i++) {
			iv = initImageView(mImagesArray[i]);
			guides.add(iv);
		}

		// 当curDot的所在的树形层次将要被绘出时此方法被调用
		curPoint.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					public boolean onPreDraw() {
						// 获取圆点图片的宽度
						offset = curPoint.getWidth();
						return true;
					}
				});

		final GuidePagerAdapter adapter = new GuidePagerAdapter(guides);
		pager.setAdapter(adapter);
		pager.clearAnimation();
		pager.setOnTouchListener(this);
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				int pos = position % mImagesArray.length;

				pointMoveAnim(pos);

				if (pos == mImagesArray.length - 1) {
					handler.sendEmptyMessageDelayed(TO_THE_END, 500);

				} else if (curPos == mImagesArray.length - 1) {
					handler.sendEmptyMessageDelayed(LEAVE_FROM_END, 100);
				}

				curPos = pos;
				super.onPageSelected(position);
			}
		});

		if (mImagesArray != null && mImagesArray.length == 1) {
			handler.sendEmptyMessageDelayed(TO_THE_END, 500);
		}

		boolean isshow = showPoint();
		if (!isshow) {
			Log.d("geek", "isshow=" + isshow);
			findViewById(R.id.fl).setVisibility(View.INVISIBLE);
		} else {
			Log.d("geek", "isshow=" + isshow);
			findViewById(R.id.fl).setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 加载布局及控件
	 */
	private void findViewById() {
		mRlGuideMain = (RelativeLayout) findViewById(R.id.rl_guide_main);
		pointContain = (LinearLayout) this.findViewById(R.id.dot_contain);
		pager = (ViewPager) findViewById(R.id.contentPager);
		curPoint = (ImageView) findViewById(R.id.cur_dot);
		ivStart = (ImageView) findViewById(R.id.open);
		pass = (ImageView) findViewById(R.id.pass);
		pass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startJumpActivity();
			}
		});

		getAdapterData();

		Integer resId = changeEnterImageBg();
		if (resId != null) {
			ivStart.setImageResource(resId);
			setViewParams(ivStart, mLeft, mTop, mWidth, mHeight);
		}

		ivStart.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				startJumpActivity();
			}
		});
	}

	private void getAdapterData() {
		Integer[] mAdapterDate = adapterEnterImagesArray();
		if (mAdapterDate != null) {
			for (int i = 0; i < mAdapterDate.length; i++) {
				if (mAdapterDate[i] != null) {
					if (i == 0) {
						mLeft = mAdapterDate[0];
					} else if (i == 1) {
						mTop = mAdapterDate[1];
					} else if (i == 2) {
						mWidth = mAdapterDate[2];
					} else if (i == 3) {
						mHeight = mAdapterDate[3];
					}
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		destory();
		super.onDestroy();
	}

	/**
	 * 开始跳转Activity
	 */
	protected abstract void startJumpActivity();

	/**
	 * onCreate
	 */
	protected abstract void create();

	/**
	 * onDestory
	 */
	protected abstract void destory();

	/**
	 * 初始化引导图片数组，传入图片Rid数组
	 * 
	 * @return
	 */
	protected abstract Integer[] initImagesArray();

	/**
	 * 修改进入图片样式
	 * 
	 * @return
	 */
	protected abstract Integer changeEnterImageBg();

	/**
	 * 适配引导图片位置，引导图片left、top、width、height。
	 * 
	 * @return
	 */
	protected abstract Integer[] adapterEnterImagesArray();

	/**
	 * 是否显示底部原点
	 * 
	 * @return
	 */
	protected abstract boolean showPoint();

	/**
	 * 初始化点
	 * 
	 * @return
	 */
	private boolean initDot() {

		if (mImagesArray.length > 0) {
			ImageView dotView = null;
			for (int i = 0; i < mImagesArray.length; i++) {
				dotView = new ImageView(this);
				dotView.setImageResource(R.mipmap.point);
				dotView.setLayoutParams(new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
				pointContain.addView(dotView);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 初始化介绍图片
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private ImageView initImageView(int id) {
		ImageView iv = new ImageView(this);
		iv.setImageResource(id);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);
		iv.setLayoutParams(params);
		iv.setScaleType(ScaleType.FIT_XY);
		return iv;
	}

	/**
	 * 移动时当前点移动到相邻的点的动画
	 * 
	 * @param position
	 * 
	 * */
	private void pointMoveAnim(int position) {
		AnimationSet animationSet = new AnimationSet(true);
		TranslateAnimation tAnim = new TranslateAnimation(offset * curPos,
				offset * position, 0, 0);
		animationSet.addAnimation(tAnim);
		animationSet.setDuration(300);
		animationSet.setFillAfter(true);
		curPoint.startAnimation(animationSet);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == TO_THE_END)
				ivStart.setVisibility(View.VISIBLE);
			else if (msg.what == LEAVE_FROM_END)
				ivStart.setVisibility(View.GONE);
		}
	};

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			if (mImagesArray != null) {
				if ((lastX - event.getX()) > 100
						&& (curPos == (mImagesArray.length - 1))) {
					startJumpActivity();
				}
			} else {
				if ((lastX - event.getX()) > 100) {
					startJumpActivity();
				}
			}
			break;
		default:
			break;
		}
		return false;
	}

	// ViewPager 适配器
	class GuidePagerAdapter extends PagerAdapter {

		/** 图片view集合 */
		private List<View> views = null;

		public GuidePagerAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(View view, int i, Object object) {
			((ViewPager) view).removeView(views.get(i));
		}

		@Override
		public void finishUpdate(View view) {
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(View view, int i) {
			// 将当前视图添加到view中，返回当前View
			((ViewPager) view).addView(views.get(i));
			return views.get(i);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (object);
		}

		@Override
		public void restoreState(Parcelable parcelable, ClassLoader classLoader) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View view) {

		}
	}

	/**
	 * 设置视图参数
	 * 
	 * @param view
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 */
	private void setViewParams(View view, Integer left, Integer top,
			Integer width, Integer height) {
		LayoutParams lp = (LayoutParams) view
				.getLayoutParams();

		if (lp == null) {
			if (width == null)
				width = LayoutParams.WRAP_CONTENT;

			if (height == null)
				height = LayoutParams.WRAP_CONTENT;

			lp = (LayoutParams) new LayoutParams(width, height);
		}
		view.setLayoutParams(lp);
	}
}
