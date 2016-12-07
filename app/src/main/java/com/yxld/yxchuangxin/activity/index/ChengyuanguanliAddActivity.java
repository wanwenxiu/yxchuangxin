package com.yxld.yxchuangxin.activity.index;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import static com.xiaomi.push.thrift.a.P;

/**
 * @ClassName: ChengyuanguanliAddActivity
 * @Description: 成员添加管理
 * @author wwx
 * @date 2016年7月15日 下午17:14:24
 */
public class ChengyuanguanliAddActivity extends BaseActivity {

	/** 业主接口实现类*/
	private YeZhuController yezhuController;

	private EditText name;
	private EditText tel;
	private RadioGroup sex;
	private EditText idcard;
	private RadioGroup guanxi;
	private  TextView submit;

	private String curSex = "0";
	private String curGuanxi = "1";

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.chengyuanadd_layout);
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
		name = (EditText) findViewById(R.id.name);
		tel = (EditText) findViewById(R.id.tel);
		sex = (RadioGroup) findViewById(R.id.sex);
		idcard = (EditText) findViewById(R.id.IDcard);
		guanxi = (RadioGroup) findViewById(R.id.guanxi);
		submit = (TextView) findViewById(R.id.submit);

		sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.man){
					curSex = "0";
				}else{
					curSex = "1";
				}
			}
		});

		guanxi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.family){
					curGuanxi = "1";
				}else{
					curGuanxi = "2";
				}
			}
		});


		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(StringUitl.isNotEmpty(ChengyuanguanliAddActivity.this,name,"请输入成员姓名")){
					if(StringUitl.isNotEmpty(ChengyuanguanliAddActivity.this,tel,"请输入成员电话")){
							if(isIdCard(idcard.getText().toString())){
									initDataFromNet();
						}
					}
				}
			}
		});
	}

	public boolean isIdCard(String num) {
		if (isNull(num)) {
			ToastUtil.show(this,"身份证不能为空");
			return false;
		}

		if (num.length() == 18 || num.length() == 15) {
			return true;
		}
		ToastUtil.show(this,"身份证长度不正确");
		return false;
	}

	public boolean isNull(String str) {
		return (str == null) || (str.trim().length() == 0);
	}


	@Override
	protected void initDataFromLocal() {

	}

	@Override
	public void onClick(View v) {
	}

	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if(yezhuController == null){
			yezhuController = new YeZhuControllerImpl();
		}

/** 用于请求参数 */
		Map<String, String> map = new HashMap<String, String>();
		map.put("yezhuId", Contains.user.getYezhuId()+"");
		map.put("fwyzType", curGuanxi);
		map.put("yezhuCardNum", idcard.getText().toString()+"");
		map.put("yezhuName", name.getText().toString()+"");
		map.put("yezhuSex", curSex);
		map.put("yezhuShouji", tel.getText().toString()+"");
		map.put("fwyzFw",Contains.appYezhuFangwus.get(0).getFwId()+"");
		map.put("yezhuGzdw", Contains.user.getYezhuGzdw());
		Log.d("geek","map="+map.toString());

		yezhuController.addChengyuan(mRequestQueue, map, new ResultListener<BaseEntity>() {
			@Override
			public void onResponse(BaseEntity info) {
				// 获取请求码
				if (info.status != STATUS_CODE_OK) {
					onError(info.MSG);
					return;
				}
				if(info.getMSG().equals("保存成功")){
					Toast.makeText(ChengyuanguanliAddActivity.this, "成员信息添加成功", Toast.LENGTH_SHORT).show();
				}
				progressDialog.hide();
				finish();
			}

			@Override
			public void onErrorResponse(String errMsg) {
				onError(errMsg);
			}
		});

	}
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 0){
				int id = msg.arg1;
				Log.d("geek","id");
				yezhuController.getDeleteChengyuanList(mRequestQueue, new Object[]{id}, new ResultListener<BaseEntity>() {
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(handler != null){
			handler.removeCallbacksAndMessages(null);
		}
	}
}
