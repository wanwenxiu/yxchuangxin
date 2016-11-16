package com.yxld.yxchuangxin.activity.index;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.ComplaintController;
import com.yxld.yxchuangxin.controller.impl.ComplaintControllerImpl;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;

/**
 * @ClassName: Complaint
 * @Description: 投诉
 * @author wwx
 * @date 2016年4月14日 下午5:14:24
 */
public class ComplaintActivity extends BaseActivity {

	private ComplaintController complaintController;
	private MaterialSpinner fenwei;
	private EditText destail;

	String[] scope = { "综合管理服务", "公共区域清洁卫生服务", "公共区域秩序维护服务", "公共区域绿化日常养护服务",
			"公共部位、公用设备、日常运行、报验维修服务" };

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.complaint);
		getSupportActionBar().setTitle("我的投诉");
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
		setTitle("投诉");
		findViewById(R.id.submit).setOnClickListener(this);
		fenwei = (MaterialSpinner) findViewById(R.id.fenwei);
		destail = (EditText) findViewById(R.id.edContext);

		fenwei.setItems(scope);

	}

	private ResultListener<BaseEntity> submitlistener = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			progressDialog.hide();
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
			}
			if(info.MSG != null){
				ToastUtil.show(ComplaintActivity.this, "投诉成功");
				finish();
			}else{
				onError("请求失败");
			}
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError("请求失败");
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.returnWrap:
				finish();
				break;
			case R.id.submit:
				initDataFromNet();
				break;
		}
	}

	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if (complaintController == null) {
			complaintController = new ComplaintControllerImpl();
		}

		if(Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0){
			ToastUtil.show(this, "请配置房屋信息再进行投诉");
			return;
		}
		if(StringUitl.isNotEmpty(this, destail, "请输入具体内容")){
			Map<String, String> parm = new HashMap<String, String>();
			parm.put("ts.tousuXiangmu", Contains.cxwyYezhu.get(0).getYezhuLoupan());
			parm.put("ts.tousuName", Contains.cxwyMallUser.getUserUserName());
			parm.put("ts.tousuShouji", Contains.cxwyMallUser.getUserTel());
			parm.put("ts.tousuNeirong", destail.getText().toString());
			parm.put("ts.tousuFanwei", fenwei.getText().toString());
			complaintController.getComplaintSubmit(mRequestQueue, parm, submitlistener);
		}
	}

	@Override
	protected void initDataFromLocal() {

	}

}
