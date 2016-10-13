package com.yxld.yxchuangxin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @ClassName: CustomScrollView
 * @Description: 监听滑动事件的scrollview
 * @author 1152008367@qq.com
 * @date 2016年2月15日 下午2:03:29
 *
 */
public class CustomScrollView extends ScrollView {

	private CustomScrollViewListener customScrollViewListener;

	public CustomScrollView(Context context) {
		super(context);
	}

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setCustomScrollViewListener(
			CustomScrollViewListener customScrollViewListener) {
		this.customScrollViewListener = customScrollViewListener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (customScrollViewListener != null) {
			customScrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}

	/**
	 * @ClassName: CustomScrollViewListener
	 * @Description: 自定义监听滑动事件Scrollview
	 * @author 1152008367@qq.com
	 * @date 2016年2月15日 下午2:05:31
	 */
	public interface CustomScrollViewListener {
		void onScrollChanged(CustomScrollView scrollView, int x, int y,
							 int oldx, int oldy);
	}

}
