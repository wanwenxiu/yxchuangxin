package com.yxld.yxchuangxin.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.Main.NewMainActivity2;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.view.GuideBaseActivity;

/**
 * @ClassName: GuideActivity 
 * @Description: 广告页 
 * @author wwx
 * @date 2015年11月12日 下午4:58:53 
 */
@SuppressLint("ShowToast")
public class GuideActivity extends GuideBaseActivity {
	
	@Override
	protected void startJumpActivity() {
		if(Contains.user ==null || Contains.user.getYezhuId() == null){
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}else{
			Intent intent = new Intent(this, NewMainActivity2.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	protected void create() {
		
	}

	@Override
	protected void destory() {
	}

	@Override
	protected Integer[] adapterEnterImagesArray() {
		return null;
	}

	@Override
	protected boolean showPoint() {
		return false;
	}

	@Override
	protected Integer[] initImagesArray() {
		return new Integer[]{R.mipmap.welcome_opendoor,R.mipmap.welcome_jiaofei,R.mipmap.welcome_jiaju,R.mipmap.welcom_mall};
	}

	@Override
	protected Integer changeEnterImageBg() {
		return R.mipmap.open;
	}
	
}
