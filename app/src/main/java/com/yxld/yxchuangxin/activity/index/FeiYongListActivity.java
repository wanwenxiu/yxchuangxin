package com.yxld.yxchuangxin.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.orhanobut.logger.Logger;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.Main.WebViewActivity;
import com.yxld.yxchuangxin.activity.order.PayWaySelectActivity;
import com.yxld.yxchuangxin.adapter.FeiYongListAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyJfWyRecord;
import com.yxld.yxchuangxin.entity.CxwyMallUserBalance;
import com.yxld.yxchuangxin.entity.WyFwApp;
import com.yxld.yxchuangxin.listener.ResultListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @ClassName: JiaoFeiListActivity
 * @Description: 缴费列表
 * @author wwx
 * @date 2016年4月13日 上午11:51:54
 */
public class FeiYongListActivity extends BaseActivity implements MaterialSpinner.OnItemSelectedListener{
	private TextView weijiao,feiyong_heji;
	private Button bottom2;
	private MaterialSpinner address;
	private RecyclerView recyclerview;
	private YeZhuController yeZhuController;
	private ArrayAdapter adapter;
	private String[] fwid;
	private String[] time;
	private double[] acreages;//面积
	private double[] meters;//每平米价格
	private Object[] isfees;//是否缴纳滞纳金
	private double[] ratios;//滞纳金比例
	private double acreagess,meterss,fee;
	private String ffwid;
	private int wymonth;
	private List<CxwyJfWyRecord> list = new ArrayList<CxwyJfWyRecord>();
	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.feiyong_list_activity_layout);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void initView() {
		weijiao= (TextView) findViewById(R.id.weijiao);
		feiyong_heji= (TextView) findViewById(R.id.feiyong_heji);
		bottom2= (Button) findViewById(R.id.bottom2);
		address= (MaterialSpinner) findViewById(R.id.address);
		recyclerview= (RecyclerView) findViewById(R.id.feiyong_recyclerview);
		address.setOnItemSelectedListener(this);
		bottom2.setOnClickListener(this);
		progressDialog.show();
		initFangwu();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		initFangwu();
	}

	@Override
	public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
		String fid=fwid[position];
		String times=time[position];
		acreagess=acreages[position];//面积
		meterss=meters[position];//每平米价格
		Object isfeess=isfees[position];//是否缴纳滞纳金
		double ratioss=ratios[position];//滞纳金比例
		Logger.d(fid);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str1 = times;
		String str2 = sdf.format(new Date());
		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		try {
			bef.setTime(sdf.parse(str1));
			aft.setTime(sdf.parse(str2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
		int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
		wymonth=result+month;
		double money;
		if (isfeess.equals("0")){
			fee = acreagess * meterss * ratioss;
			money=acreagess * meterss*wymonth+fee;
		}else {
			  money=acreagess * meterss*wymonth;
		}
		Logger.d(wymonth);
		Logger.d(acreagess);
		Logger.d(meterss);
		Logger.d(ratioss);
		Logger.d(money);
        //价格打印为正数是要缴费的价格  负数是已经提前缴费的价格
		if (money>0){
			feiyong_heji.setText("您还有未交金额");
			weijiao.setText(money+"");
		}else if (money<0){
			feiyong_heji.setText("您提前预交金额");
			weijiao.setText(Math.abs(money)+"");
		}else {
			feiyong_heji.setText("您暂时已经全部缴清");
			weijiao.setText("");
		}

		ffwid=fid;
		yeZhuController.getWUYE(mRequestQueue,
				new Object[] {ffwid}, yezhulistener);


	}
	//业主房屋查询
     private void initFangwu(){
		 if (yeZhuController == null) {
			 yeZhuController = new YeZhuControllerImpl();
		 }

		 yeZhuController.getHouse(mRequestQueue,
				 new Object[] { Contains.user.getYezhuId() }, listener);
	 }

	private ResultListener<WyFwApp> listener = new ResultListener<WyFwApp>() {

		@Override
		public void onResponse(WyFwApp info) {
			progressDialog.hide();
			Log.d("...", info.toString());
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
		}
			if (info.MSG.equals("查找成功")) {
				progressDialog.hide();
				fwid=new String[info.getHouse().size()];
				time=new String[info.getHouse().size()];
				acreages=new double[info.getHouse().size()];
				meters=new double[info.getHouse().size()];
				isfees=new Object[info.getHouse().size()];
				ratios=new double[info.getHouse().size()];
				String[] str=new String[info.getHouse().size()];

				for (int i = 0; i< info.getHouse().size(); i++) {
					String address="房间:"+info.getHouse().get(i).getFwLoupanName()+""+info.getHouse().get(i).getFwLoudong()+"栋"+info.getHouse().get(i).getFwFanghao();
					String id= info.getHouse().get(i).getFwId();
					String times;
					if (info.getHouse().get(i).getJfWyUseEndTime()==null){
						 times=info.getHouse().get(i).getFwJiaofangTime();
					}else {
						 times=info.getHouse().get(i).getJfWyUseEndTime();
					}
					double acreage= Double.parseDouble(info.getHouse().get(i).getFwMianji().toString());//面积
					double meter=Double.parseDouble(info.getHouse().get(i).getJfWyTypeFeiyong().toString());//每平米价格
					Object isfee=info.getHouse().get(i).getJfWyIsLateFees();//是否缴纳滞纳金
					double ratio = Double.parseDouble(info.getHouse().get(i).getJfWyTypeLateFees().toString());//滞纳金比例
					fwid[i]=id;
					str[i]=address;
					time[i]=times;
					acreages[i]=acreage;
					meters[i]=meter;
					isfees[i]=isfee;
					ratios[i]=ratio;
				}
				address.setItems(str);
				ffwid=fwid[0];
				yeZhuController.getWUYE(mRequestQueue,
						new Object[] {ffwid}, yezhulistener);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String str1 = time[0];
				String str2 = sdf.format(new Date());
				Calendar bef = Calendar.getInstance();
				Calendar aft = Calendar.getInstance();
				try {
					bef.setTime(sdf.parse(str1));
					aft.setTime(sdf.parse(str2));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
				int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
				wymonth=result+month;
				double money;
				if (isfees[0].equals("0")){
					fee = acreages[0] * meters[0] * ratios[0];
					money= acreages[0] * meters[0]*wymonth+fee;
				}else {
					money= acreages[0] * meters[0]*wymonth;
				}
				if (money>0){
					feiyong_heji.setText("您还有未交金额");
					weijiao.setText(money+"");
				}else if (money<0){
					feiyong_heji.setText("您提前预交金额");
					weijiao.setText(Math.abs(money)+"");
				}else {
					feiyong_heji.setText("您已经全部缴清");
					weijiao.setText("0.00");
				}

			}
		}

		@Override
		public void onErrorResponse(String errMsg) {
			progressDialog.hide();
			onError(errMsg);
		}
	};

	private ResultListener<CxwyJfWyRecord> yezhulistener = new ResultListener<CxwyJfWyRecord>() {

		@Override
		public void onResponse(CxwyJfWyRecord info) {
			progressDialog.hide();
			Log.d("...", info.toString());
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
			}
			if (info.MSG.equals("查找成功")) {
				list=info.getHouse();
				FeiYongListAdapter feiYongListAdapter = new FeiYongListAdapter(FeiYongListActivity.this,list);
				recyclerview.setAdapter(feiYongListAdapter);
				recyclerview.setLayoutManager(new LinearLayoutManager(FeiYongListActivity.this));
			}
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError(errMsg);
		}
	};

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void initDataFromLocal() {
	}

	@Override
	public void onClick(View v) {
		double mianji;
		if (acreagess < 1 || meterss< 1){
			acreagess=acreages[0];//面积
			meterss=meters[0];//每平米价格
			mianji =acreagess *meterss;
		}else {
			mianji =acreagess *meterss;
		}
		Intent xinxi = new Intent(this,FeiYongDestailActivity.class);
		Bundle xx = new Bundle();
		xx.putString("fwid",ffwid);
		xx.putDouble("fee",fee);
		xx.putInt("wymonth",wymonth);
		xx.putString("name",feiyong_heji.getText().toString() );
		xx.putDouble("mianji",mianji);
		xx.putDouble("money",Double.parseDouble(weijiao.getText().toString()) );
		xx.putString("position",address.getText().toString() );
		xinxi.putExtras(xx);
		startActivity(xinxi, xx);
	}
}

