package com.yxld.yxchuangxin.activity.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.FeiYongListAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.WuyeRecordAndroid;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;

/**
 * @ClassName: JiaoFeiListActivity 
 * @Description: 缴费列表
 * @author wwx
 * @date 2016年4月13日 上午11:51:54 
 */
public class FeiYongListActivity extends BaseActivity implements ResultListener<WuyeRecordAndroid> {
	
	/** 未交金额*/
	private TextView weijiao;
	/** 预交金额*/
	@SuppressWarnings("unused")
	private TextView yujiao;
	/** 全部账单*/
	private TextView tvAll;
	/** 未交账单*/
	private TextView tvWeijiao;
	/** 预交物业费*/
	private TextView bottom1;
	/** 缴纳物业费*/
	private TextView bottom2;
	/** 地址*/
	private TextView address;
	/** 列表*/
	private ListView listview;
	
	/** 装载选项卡中文字（订单状态） */
	private List<TextView> listTextView = new ArrayList<TextView>();
	
	/** 业主接口实现类*/
	private YeZhuController yezhuController;
	/** 数据集合*/
	private List<WuyeRecordAndroid> listData = new ArrayList<WuyeRecordAndroid>();
	/** 适配器*/
	private FeiYongListAdapter adapter;
	
	/** 查询列表标识 1为查询所有 2为查询未缴*/
	private int tag = 1;
	
	/** 查询费用类型 例如 ：物业 ，水，电，机动车停放服务,天然气等*/
	private String type = "物业";
	
	/** 查询接口*/
	private String url = API.URL_PAYMENT_RECORDS_WUYE;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.feiyong_list_activity_layout);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		super.onStart();
		initDataFromNet();
	}

	@Override
	protected void initView() {
		weijiao = (TextView) findViewById(R.id.weijiao);
		yujiao = (TextView) findViewById(R.id.yujiao);
		tvAll = (TextView) findViewById(R.id.tvAll);
		tvWeijiao = (TextView) findViewById(R.id.tvWeijiao);
		bottom1 = (TextView) findViewById(R.id.bottom1);
		bottom2 = (TextView) findViewById(R.id.bottom2);
		address = (TextView) findViewById(R.id.address);
		listview = (ListView) findViewById(R.id.ListView);
		
		adapter = new FeiYongListAdapter(listData, this);
		listview.setAdapter(adapter);
		
		tvAll.setOnClickListener(this);
		tvWeijiao.setOnClickListener(this);
		
		bottom1.setOnClickListener(this);
		bottom2.setOnClickListener(this);
		
		listTextView.add(tvAll);
		listTextView.add(tvWeijiao);
		
	}

	@Override
	protected void initDataFromLocal() {
		if(Contains.cxwyYezhu != null && Contains.cxwyYezhu.size() != 0){
			address.setText("房间:"+Contains.cxwyYezhu.get(0).getYezhuLoupan()+
					Contains.cxwyYezhu.get(0).getYezhuLoudong()+"栋"+Contains.cxwyYezhu.get(0).getYezhuDanyuan()+
					"单元"+Contains.cxwyYezhu.get(0).getYezhuFanghao());
		}
		type = getIntent().getStringExtra("curType");
		if(!StringUitl.isNoEmpty(type)){
			type = "物业";
		}
		initViewData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvAll:
			// 修改筛选条件全部账单
			changeTvBg(0);
			break;
		case R.id.tvWeijiao:
			// 修改筛选条件未缴账单
			changeTvBg(1);
			break;
		case R.id.bottom1:
			ToastUtil.show(FeiYongListActivity.this, "敬请期待");
			break;
		case R.id.bottom2:
			if(listData.size() == 0){
				ToastUtil.show(FeiYongListActivity.this,"暂没有可缴费数据哦");
				return;
			}
			Bundle bundle = new Bundle();
			bundle.putString("curType", type);
			startActivity(FeiYongDestailActivity.class,bundle);
			break;
		default:
			break;
		}
	}

	/**
	 * @Title: changeTvBg
	 * @Description: 改变textView背景
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	private void changeTvBg(int position) {
		for (int i = 0; i < listTextView.size(); i++) {
			if (i == position) {
				listTextView.get(i).setBackgroundResource(
						R.drawable.order_border);
				listTextView.get(i).setTextColor(
						getResources().getColor(R.color.color_main_color));
			} else {
				listTextView.get(i).setBackgroundResource(0);
				listTextView.get(i).setTextColor(
						getResources().getColor(R.color.black));
			}
		}
		
		tag = position+1;
		initDataFromNet();
	}
	
	/** 更新界面文字*/
	private void initViewData() {
		bottom1.setText("预交"+type+"费");
		bottom2.setText("缴纳"+type+"费");
		
		if(type.equals("物业服务")){
			url = API.URL_PAYMENT_RECORDS_WUYE;
		}else if(type.equals("水")){
			url = API.URL_PAYMENT_RECORDS_WATER;
		}else if(type.equals("电")){
			url = API.URL_PAYMENT_RECORDS_ELECTRICITY;
		}
		getSupportActionBar().setTitle(type+"缴费");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if (yezhuController == null) {
			yezhuController = new YeZhuControllerImpl();
		}
		
		listData.clear();
		adapter.setListDatas(listData);
		
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("i", tag+"");
		parm.put("loupan", Contains.cxwyYezhu.get(0).getYezhuLoupan());
		parm.put("loudong", Contains.cxwyYezhu.get(0).getYezhuLoudong());
		parm.put("danyuan", Contains.cxwyYezhu.get(0).getYezhuDanyuan());
		parm.put("fanghao", Contains.cxwyYezhu.get(0).getYezhuFanghao()+ "");
		
		yezhuController.getYeZhuWuYeList(mRequestQueue,url,parm, this);
	}

	@Override
	public void onResponse(WuyeRecordAndroid info) {
		// 获取请求码
		if (info.status != STATUS_CODE_OK) {
			onError(info.MSG);
			return;
		}
		if (isEmptyList(info.getRows())) {
			ToastUtil.show(FeiYongListActivity.this, "没有查询到记录");
		} else {
			listData = info.getRows();
			adapter.setListDatas(listData);
			weijiao.setText(info.getQWYFZS());
		}
		progressDialog.hide();
	}

	@Override
	public void onErrorResponse(String errMsg) {
		onError(errMsg);
	}

}
