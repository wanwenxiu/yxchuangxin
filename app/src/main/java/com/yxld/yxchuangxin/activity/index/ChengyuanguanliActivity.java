package com.yxld.yxchuangxin.activity.index;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.ChengyuanListAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.AppYezhuFangwu;
import com.yxld.yxchuangxin.entity.CxwyYezhu;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ChengyuanguanliActivity
 * @Description: 成员管理
 * @author wwx
 * @date 2016年7月15日 下午14:14:24
 */
public class ChengyuanguanliActivity extends BaseActivity {

	/** 添加成员按钮*/
	private TextView addr;

	private Button submit;

	/** 成员列表*/
	private ListView  AuthorizedList;

	/** 适配器*/
	private ChengyuanListAdapter adapter;

	/** 成员数据*/
	private List<AppYezhuFangwu> listdata = new ArrayList<AppYezhuFangwu>();

	/** 业主接口实现类*/
	private YeZhuController yezhuController;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.authorized_release_activity);
		getSupportActionBar().setTitle("成员管理");
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
		addr = (TextView) findViewById(R.id.addr);
		//addr.setText(Contains.appYezhuFangwus.get(Contains.curFangwu).getXiangmuLoupan()+"-"+Contains.user.getYezhuName());
		addr.setText(Contains.appYezhuFangwus.get(Contains.curFangwu).getXiangmuLoupan()
				+ Contains.appYezhuFangwus.get(Contains.curFangwu).getFwLoudong()
				+ "栋 "
				+ Contains.appYezhuFangwus.get(Contains.curFangwu).getFwDanyuan()
				+ "单元 "
				+ Contains.appYezhuFangwus.get(Contains.curFangwu).getFwFanghao()
				.toString() + "号");
		AuthorizedList = (ListView) findViewById(R.id.AuthorizedList);
		adapter = new ChengyuanListAdapter(listdata,this,handler);
		AuthorizedList.setAdapter(adapter);
		AuthorizedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("geek","position="+position);
			}
		});
		submit = (Button) findViewById(R.id.submit);
		submit.setVisibility(View.VISIBLE);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(Contains.appYezhuFangwus != null && Contains.appYezhuFangwus.size() != 0 &&
						Contains.appYezhuFangwus.get(0) != null && Contains.appYezhuFangwus.get(0).getFwId() != null){
					startActivity(ChengyuanguanliAddActivity.class);
				}else{
					ToastUtil.show(ChengyuanguanliActivity.this,"房屋信息不完善");
				}
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		initDataFromNet();
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	protected void initDataFromLocal() {

	}

	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if(yezhuController == null){
			yezhuController = new YeZhuControllerImpl();
		}
		if(Contains.appYezhuFangwus != null && Contains.appYezhuFangwus.size() >0 && Contains.appYezhuFangwus.get(Contains.curFangwu) != null
				&& Contains.appYezhuFangwus.get(Contains.curFangwu).getFwId() != null
				&& !"".equals(Contains.appYezhuFangwus.get(Contains.curFangwu).getFwId())){
				yezhuController.getAllChengyuanList(mRequestQueue, new Object[]{Contains.appYezhuFangwus.get(Contains.curFangwu).getFwId()}, new ResultListener<AppYezhuFangwu>() {
					@Override
					public void onResponse(AppYezhuFangwu info) {
						if (isEmptyList(info.getRows())) {
							ToastUtil.show(ChengyuanguanliActivity.this, "没有成员信息");
							listdata.clear();
							adapter.setListDatas(listdata);
						} else {
							//yz.yezhu_id,yz.yezhu_shouji, yz.yezhu_name,fwyz.fwyzType
							listdata = info.getRows();
							adapter.setListDatas(listdata);
						}
						progressDialog.hide();
					}

					@Override
					public void onErrorResponse(String errMsg) {
						onError(errMsg);
					}
				});

		}else{
			ToastUtil.show(this,"业主房屋信息不完善");
		}

	}
	private android.os.Handler handler = new android.os.Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 0){
				int id = msg.arg1;
				Log.d("geek","id");
				yezhuController.getDeleteChengyuanList(mRequestQueue, new Object[]{id,Contains.appYezhuFangwus.get(Contains.curFangwu).getFwId()}, new ResultListener<BaseEntity>() {
					@Override
					public void onResponse(BaseEntity info) {
						// 获取请求码
						if (info.status != STATUS_CODE_OK) {
							onError(info.MSG);
							return;
						}
						initDataFromNet();
					}

					@Override
					public void onErrorResponse(String errMsg) {
						onError(errMsg);
					}
				});
			}
		}
	};

}
