package com.yxld.yxchuangxin.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;

/**
 * @ClassName: EmployerActivity 
 * @Description: 业主信息界面 
 * @author 易上飞
 * @date 2016年4月6日 下午4:32:23 
 */
public class EmployerActivity extends BaseActivity {
	private Spinner spinner1;
	private View fwxx;
	private TextView yz_name;
	private TextView yz_id;
	private TextView yz_sex;
	private TextView yz_lp;
	private TextView yz_ld;
	private TextView yz_dy;
	private TextView yz_fh;
	private TextView yz_jf;
	private TextView yz_rz;
	private TextView yz_tel;
	private TextView yz_phone1;
	private TextView yz_phone2;
	private TextView yz_fw;
	private TextView yz_sy;
	private TextView yz_hx;
	private TextView yz_mj;
	private TextView yz_card;
	private TextView yz_fdid;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_employer);
		getSupportActionBar().setTitle("业主房屋信息");
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
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		fwxx = (View) findViewById(R.id.fwxx);
		yz_name = (TextView) findViewById(R.id.yz_name);
		yz_id = (TextView) findViewById(R.id.yz_id);
		yz_sex = (TextView) findViewById(R.id.yz_sex);
		yz_lp = (TextView) findViewById(R.id.yz_lp);
		yz_ld = (TextView) findViewById(R.id.yz_ld);
		yz_dy = (TextView) findViewById(R.id.yz_dy);
		yz_fh = (TextView) findViewById(R.id.yz_fh);
		yz_jf = (TextView) findViewById(R.id.yz_jf);
		yz_rz = (TextView) findViewById(R.id.yz_rz);
		yz_tel = (TextView) findViewById(R.id.yz_tel);
		yz_phone1 = (TextView) findViewById(R.id.yz_phone1);
		yz_phone2 = (TextView) findViewById(R.id.yz_phone2);
		yz_fw = (TextView) findViewById(R.id.yz_fw);
		yz_sy = (TextView) findViewById(R.id.yz_sy);
		yz_hx = (TextView) findViewById(R.id.yz_hx);
		yz_mj = (TextView) findViewById(R.id.yz_mj);
		yz_card = (TextView) findViewById(R.id.yz_card);
		yz_fdid = (TextView) findViewById(R.id.yz_fdid);
		if (Contains.user !=null) {
			fwxx.setVisibility(View.GONE);
			xixi(0);
		} else {
			String[] fc = {};
			fc = new String[Contains.appYezhuFangwus.size()];
			for (int i = 0; i < Contains.appYezhuFangwus.size(); i++) {
				String fcxx = Contains.appYezhuFangwus.get(i).getXiangmuLoupan()
						+ Contains.appYezhuFangwus.get(i).getFwLoudong()
						+ "栋 "
						+ Contains.appYezhuFangwus.get(i).getFwDanyuan()
						+ "单元 "
						+ Contains.appYezhuFangwus.get(i).getFwFanghao()
								.toString() + "号";
				fc[i] = fcxx;
			}
			ArrayAdapter<Object> s = new ArrayAdapter<Object>(EmployerActivity.this,
					android.R.layout.simple_dropdown_item_1line, fc);
			spinner1.setAdapter(s);
			spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					xixi(position);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
		}

	}

	public void xixi(int position) {
		yz_name.setText(Contains.user.getYezhuName());
		yz_id.setText(Contains.user.getYezhuId().toString());
		yz_sex.setText(Contains.user.getYezhuSex());
		yz_lp.setText(Contains.appYezhuFangwus.get(position).getXiangmuLoupan());
		yz_ld.setText(Contains.appYezhuFangwus.get(position).getFwLoudong());
		yz_dy.setText(Contains.appYezhuFangwus.get(position).getFwDanyuan());
		yz_fh.setText(Contains.appYezhuFangwus.get(position).getFwDanyuan()
				.toString());
//		yz_jf.setText(Contains.cxwyYezhu.get(position).getYezhuJiaofangtime());
//		yz_rz.setText(Contains.cxwyYezhu.get(position).getYezhuRuzhutime());
//		yz_tel.setText(Contains.cxwyYezhu.get(position).getYezhuPhone());
		yz_phone1.setText(Contains.user.getYezhuShouji());
//		yz_phone2.setText(Contains.cxwyYezhu.get(position).getYezhuBianhao());
//		yz_fw.setText(Contains.cxwyYezhu.get(position).getYezhuStatus());
//		yz_sy.setText(Contains.cxwyYezhu.get(position).getYezhuStatus2());
//		yz_hx.setText(Contains.cxwyYezhu.get(position).getYezhuHuxing());

//		//如果是房东展示完整身份证信息
//		if(Contains.cxwyYezhu.get(position).getYezhuParentId() == 0){
//			String shenfenzheng = Contains.cxwyYezhu.get(position).getYezhuCardNum();
//			if(!TextUtils.isEmpty(shenfenzheng) && shenfenzheng.length() >= 18 ){
//				StringBuilder sb  =new StringBuilder();
//				for (int i = 0; i < shenfenzheng.length(); i++) {
//					char c = shenfenzheng.charAt(i);
//					if (i >= 6 && i <= 18) {
//						sb.append('*');
//					} else {
//						sb.append(c);
//					}
//				}
//				yz_card.setText(sb.toString());
//			}
//		}else{
//			yz_card.setText("******************");
//		}
//		yz_fdid.setText(Contains.cxwyYezhu.get(position).getYezhuParentId()
//				.toString());
	}

	@Override
	protected void initDataFromLocal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.returnWrap:
			finish();
			break;
		}
	}

}
