package com.yxld.yxchuangxin.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.login.LoginActivity;
import com.yxld.yxchuangxin.base.AppConfig;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.db.DBUtil;
import com.yxld.yxchuangxin.entity.CxwyMallUser;
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
	private   final String LAST_LOGIN_USER_ID = "lastLoginUserId";
	private final String CB_SAVE_PWD = "cb_save_pwd";

	private TextView account_id;
	private TextView account_name;
	private TextView account_card,yz_zhenshiname;
	private View securityAccountWrap;
	private View menberNameWrap;
	
	private RelativeLayout mineVisionUpdate;
	
	/**退出登录*/
	private TextView loginOut;

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_member);
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
		account_name = (TextView) findViewById(R.id.yz_id);
		account_card = (TextView) findViewById(R.id.account_card);
		yz_zhenshiname = (TextView) findViewById(R.id.yz_zhenshiname);

		if (Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0) {
			yz_zhenshiname.setText("业主信息未完善");
		}else{
			yz_zhenshiname.setText(Contains.cxwyYezhu.get(0).getYezhuName());
		}

		securityAccountWrap=findViewById(R.id.securityAccountWrap);
		menberNameWrap=findViewById(R.id.menberNameWrap);
		
		mineVisionUpdate = (RelativeLayout)findViewById(R.id.mineVisionUpdate);
		
		loginOut = (TextView) findViewById(R.id.loginOut);
		loginOut.setOnClickListener(this);

		String phone=Contains.cxwyMallUser.getUserTel();

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
		
		account_name.setOnClickListener(this);
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
		account_name.setText(Contains.cxwyMallUser.getUserUserName());
		String card=Contains.cxwyMallUser.getUserIdCard();

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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.yz_id:
			Intent aname = new Intent(MemberActivity.this, UpdateName.class);
			startActivity(aname);
			break;
		case R.id.menberNameWrap:
			Intent NameWrap = new Intent(MemberActivity.this, UpdateName.class);
			startActivity(NameWrap);
			break;
		case R.id.securityAccountWrap:
			Intent AccountWrap = new Intent(MemberActivity.this, UpdatePwd.class);
			startActivity(AccountWrap);
			break;
		case R.id.loginOut:
			if(dbUtil == null){
				dbUtil = new DBUtil(this);
			}
			dbUtil.clearData(CxwyMallUser.class);
			Contains.cxwyMallUser = null;
			//保存用户ID和账号至配置文件中
			SPUtils.put(this, CB_SAVE_PWD, false);
			SPUtils.put(this, LAST_LOGIN_USER_ID, "");
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
