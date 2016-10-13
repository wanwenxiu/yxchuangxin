package com.yxld.yxchuangxin.activity.index;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.view.ImageCycleView;
import com.zxing.encoding.EncodingHandler;

/**
 * @ClassName: VisitorInvitationActivity
 * @Description: 访客邀请
 * @author wwx
 * @date 2016年5月27日 下午5:14:24
 */
public class VisitorInvitationActivity extends BaseActivity {

	/** 确认按钮*/
	private TextView name;
	/** 确认按钮*/
	private TextView phone;
	/** 确认按钮*/
	private TextView sure;
	/** 确认生成*/
	private ImageView qrImgImageView;

	@Override
	protected void initContentView(Bundle savedInstanceState) {

		setContentView(R.layout.visitor_invitation_activity_layout);
		getSupportActionBar().setTitle("来访邀请");
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
		sure = (TextView)findViewById(R.id.sure);
		name = (TextView)findViewById(R.id.name);
		phone = (TextView)findViewById(R.id.phone);
		sure.setOnClickListener(this);
		qrImgImageView = (ImageView) findViewById(R.id.iv_qr_image);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.sure:  //确认 生成二维码
				try {
					String contentString = name.getText().toString()+phone.getText().toString();
					if (!contentString.equals("")) {
						//根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
						Bitmap qrCodeBitmap = EncodingHandler.createQRCode(contentString, 350);
						qrImgImageView.setImageBitmap(qrCodeBitmap);
					}else {
						Toast.makeText(VisitorInvitationActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
					}
				} catch (WriterException e) {
					e.printStackTrace();
				}

				break;
			default:
				break;
		}
	}

	@Override
	protected void initDataFromLocal() {

	}

}
