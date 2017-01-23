package com.yxld.yxchuangxin.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaomi.mipush.sdk.MiPushClient;
import com.yxld.yxchuangxin.MainActivity;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.login.LoginActivity;
import com.yxld.yxchuangxin.base.AppConfig;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.db.DBUtil;
import com.yxld.yxchuangxin.entity.CxwyMallUser;
import com.yxld.yxchuangxin.util.CxUtil;
import com.yxld.yxchuangxin.util.SPUtils;
import com.yxld.yxchuangxin.util.ToastUtil;

import static com.yxld.yxchuangxin.R.id.phone;

/**
 * @ClassName: MemberActivity 
 * @Description: 账号设置
 * @author 易上飞
 * @date 2016年4月13日 上午9:31:12 
 */
public class MemberActivity extends BaseActivity {
	private TextView account_id;
	private TextView account_card,yz_zhenshiname,yz_chuangxinhao;
	private View securityAccountWrap;
	private View menberChuangXinWrap;
	
	private RelativeLayout mineVisionUpdate;
	
	/**退出登录*/
	private TextView loginOut;
	private SharedPreferences sp;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_member);
		sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		getSupportActionBar().setTitle("账号设置");
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
		account_id = (TextView) findViewById(R.id.yz_name);
		account_card = (TextView) findViewById(R.id.account_card);
		yz_zhenshiname = (TextView) findViewById(R.id.yz_zhenshiname);
		yz_chuangxinhao = (TextView) findViewById(R.id.yz_chuangxinhao);

		if(Contains.user == null || Contains.appYezhuFangwus == null ||Contains.appYezhuFangwus.size() ==0){
			yz_zhenshiname.setText("业主信息未完善");
		}else{
			yz_zhenshiname.setText(Contains.user.getYezhuName());
		}

		if(Contains.user == null || Contains.user.getYezhuChuangxinhao() == null || "".equals(Contains.user.getYezhuChuangxinhao())){
			yz_chuangxinhao.setText("");
		}else{
			yz_chuangxinhao.setText(Contains.user.getYezhuChuangxinhao());
		}

		securityAccountWrap=findViewById(R.id.securityAccountWrap);
		menberChuangXinWrap=findViewById(R.id.menberChuangXinWrap);
		menberChuangXinWrap.setOnClickListener(this);
		
		mineVisionUpdate = (RelativeLayout)findViewById(R.id.mineVisionUpdate);
		
		loginOut = (TextView) findViewById(R.id.loginOut);
		loginOut.setOnClickListener(this);

		String phone=Contains.user.getYezhuShouji();

		if(!TextUtils.isEmpty(phone) && phone.length() >= 11 ){
			StringBuilder sb  =new StringBuilder();
			for (int i = 0; i < phone.length(); i++) {
				char c = phone.charAt(i);
				if (i >= 3 && i <= 6) {
					sb.append('*');
				} else {
					sb.append(c);
				}
			}
			account_id.setText(sb.toString());
		}
		
		account_card.setOnClickListener(this);
		securityAccountWrap.setOnClickListener(this);
		
		mineVisionUpdate.setOnClickListener(this);
	}

	@Override
	protected void initDataFromLocal() {
	}

	@Override
	protected void onStart() {
		super.onStart();
		String card=Contains.user.getYezhuCardNum();

		if(!TextUtils.isEmpty(card) && card.length() >= 18 ){
			StringBuilder sb  =new StringBuilder();
			for (int i = 0; i < card.length(); i++) {
				char c = card.charAt(i);
				if (i >= 6 && i <= 18) {
					sb.append('*');
				} else {
					sb.append(c);
				}
			}
			account_card.setText(sb.toString());
		}

		if(Contains.user == null || Contains.user.getYezhuChuangxinhao() == null || "".equals(Contains.user.getYezhuChuangxinhao())){
			yz_chuangxinhao.setText("");
		}else{
			yz_chuangxinhao.setText(Contains.user.getYezhuChuangxinhao());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menberChuangXinWrap:
			if(Contains.user != null && Contains.user.getYezhuChuangxinhao() != null && !"".equals(Contains.user.getYezhuChuangxinhao()) && !Contains.user.getYezhuChuangxinhao().startsWith("cx")){
				ToastUtil.show(MemberActivity.this,"你已经修改过创欣号，不能重复修改");
				return;
			}
			Intent NameWrap = new Intent(MemberActivity.this, UpdateName.class);
			startActivity(NameWrap);
			break;
		case R.id.securityAccountWrap:
			Intent AccountWrap = new Intent(MemberActivity.this, UpdatePwd.class);
			startActivity(AccountWrap);
			break;
		case R.id.loginOut:
			//退出登录信息
			CxUtil.clearData(sp);
			finish();
			AppConfig.getInstance().exit();
			startActivity(LoginActivity.class);
			break;
		case R.id.mineVisionUpdate:
			startActivity(MineVisionUpdateMainActivity.class);
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
