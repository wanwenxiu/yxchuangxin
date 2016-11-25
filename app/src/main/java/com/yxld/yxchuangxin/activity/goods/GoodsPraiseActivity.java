package com.yxld.yxchuangxin.activity.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hp.hpl.sparta.Text;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.GoodsPraiseAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.PraiseController;
import com.yxld.yxchuangxin.controller.impl.PraiseControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallComment;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.view.NoScrollListView;

/**
 * @ClassName: AppPraiseActivity
 * @Description: 评价晒单
 * @author 1152008367@qq.com
 * @date 2015年9月9日 上午9:16:10
 */
public class GoodsPraiseActivity extends BaseActivity implements
		ResultListener<BaseEntity> {

	private TextView submitPraise = null;
	private GoodsPraiseAdapter adapter;
	private NoScrollListView listview;

	private PraiseController praiseContaoller;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submitPraise: // 去评论
			initDataFromNet();
			break;
		default:
			break;
		}
	}

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.goods_praise_layout);
		getSupportActionBar().setTitle("评价晒单");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void initView() {
		// 加载控件
		submitPraise = (TextView) findViewById(R.id.submitPraise);
		submitPraise.setOnClickListener(this);

		listview = (NoScrollListView) findViewById(R.id.ListView);
	}

	@Override
	protected void initDataFromLocal() {
		Log.d("geek",
				"评价  Contains.curCommData" + Contains.curCommData.toString());
		adapter = new GoodsPraiseAdapter(Contains.curCommData, this);
		listview.setAdapter(adapter);
	}

	@Override
	protected void initDataFromNet() {
		List<CxwyMallComment> mData = adapter.getmData();
		if (mData == null || mData.size() == 0) {
			return;
		}
		// 判断是否都已经选择评分
		for (int i = 0; i < mData.size(); i++) {
			CxwyMallComment comment = mData.get(i);
			if (comment.getPingjiaLevel() == null
					|| comment.getPingjiaLevel() == 0) {
				ToastUtil.show(this, "请选择评价等级");
				return;
			}
		}

		if (praiseContaoller == null) {
			praiseContaoller = new PraiseControllerImpl();
		}

		progressDialog.show();
		int count = -1;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < mData.size(); i++) {
			count++;
			map.put("commentList[" + count + "].pingjiaShangpNum", mData.get(i)
					.getPingjiaShangpNum() + "");
			map.put("commentList[" + count + "].pingjiaLevel", mData.get(i)
					.getPingjiaLevel() + "");
			map.put("commentList[" + count + "].pingjiaBody", mData.get(i)
					.getPingjiaBody() + "");
			map.put("commentList[" + count + "].pingjiaBeiyong1", mData.get(i)
					.getPingjiaBeiyong1() + ""); //商品名称
		}
		//用户id 订单id
		map.put("comment.pingjiaName", Contains.cxwyMallUser.getUserTel()+"");

		map.put("comment.pingjiaBeiyong2", mData.get(0).getPingjiaBeiyong2());
		Log.d("geek", mData.toString() + ",map" + map.toString());
		praiseContaoller.getPraiseGoodsFromUser(mRequestQueue, map, this);
	}

	@Override
	public void onResponse(BaseEntity info) {
		// 获取请求码
		if (info.status != STATUS_CODE_OK) {
			onError(info.MSG);
			return;
		}
		progressDialog.hide();
		finish();
	}

	@Override
	public void onErrorResponse(String errMsg) {
		onError(errMsg);
	}
}
