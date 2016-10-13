package com.yxld.yxchuangxin.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @ClassName: MyViewpager
 * @Description: 禁止滑动
 * @author wwx
 * @date 2016年3月18日 下午3:03:36
 */
@SuppressLint("ClickableViewAccessibility")
public class MyViewpager extends ViewPager {

	public MyViewpager(Context context) {
		super(context);
	}

	private boolean scrollble = false;

	public MyViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (!scrollble) {
			return true;
		}
		return super.onTouchEvent(ev);
	}

	public boolean isScrollble() {
		return scrollble;
	}

	public void setScrollble(boolean scrollble) {
		this.scrollble = scrollble;
	}
}
