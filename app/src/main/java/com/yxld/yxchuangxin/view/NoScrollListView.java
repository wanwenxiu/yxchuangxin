package com.yxld.yxchuangxin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @ClassName: NoScrollListView 
 * @Description: 自定义无滑动的listview 
 * @author 1152008367@qq.com
 * @date 2015年12月21日 上午11:20:47 
 */
public class NoScrollListView extends ListView {

	public NoScrollListView(Context context) {
		super(context);
	}

	public NoScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
