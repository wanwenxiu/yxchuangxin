package com.yxld.yxchuangxin.activity.index.selectimg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.index.selectimg.util.AlbumHelper;
import com.yxld.yxchuangxin.activity.index.selectimg.util.Bimp;
import com.yxld.yxchuangxin.activity.index.selectimg.util.FileUtils;
import com.yxld.yxchuangxin.activity.index.selectimg.util.ImageItem;
import com.yxld.yxchuangxin.activity.index.selectimg.util.PublicWay;
import com.yxld.yxchuangxin.activity.index.selectimg.util.Res;
import com.yxld.yxchuangxin.activity.login.LoginActivity;
import com.yxld.yxchuangxin.activity.mine.RepairListActivity;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.RepairController;
import com.yxld.yxchuangxin.controller.impl.ReparirControllerImpl;
import com.yxld.yxchuangxin.http.FileUpload;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: Repair 
 * @Description: 报修
 * @author wwx
 * @date 2016年4月14日 上午9:46:31 
 */
public class RepairCopy extends BaseActivity {
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	/** 报修项目*/
	private Spinner private_spinner1;
	/** 报修地点*/
	private EditText address;
	
	/** 报修区域*/
	public RadioGroup quyu;
	/** 报修公共区域*/
	public RadioButton publics;
	/** 报修家庭区域*/
	public RadioButton privates;
	
	/** 报修*/
	public RadioGroup groupPublic;
	/** 小区域*/
	public RadioButton publicSmall;
	/** 大区域*/
	public RadioButton publicBig;
	
	/** 报修项目数组*/
	private String[] xiangmu = new String[]{};
	
	public static Bitmap bimap;

	/** 返回图片按钮 */
	private ImageView returnWrap = null;

	/** 上传图片成功 */
	public static final int uploadSuccess = 0;
	/** 上传图片失败 */
	public static final int uploadFaild = 1;

	/** 内容文本 */
	private static EditText edContext = null;
	
	private RepairController repairController;

	private TextView bxlt;

	@Override
	protected void initContentView(Bundle savedInstanceState) {

		getSupportActionBar().setTitle("我要报修");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			exitThis(false);
		}
		return super.onOptionsItemSelected(item);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.repair_private,
				null);
		setContentView(parentView);
		
		private_spinner1 = (Spinner)findViewById(R.id.private_spinner1);
		address = (EditText) findViewById(R.id.address);
		quyu = (RadioGroup)findViewById(R.id.quyu);
		publics = (RadioButton) findViewById(R.id.publics);
		privates = (RadioButton) findViewById(R.id.privates);
		
		groupPublic = (RadioGroup) findViewById(R.id.groupPublic);
		publicSmall = (RadioButton) findViewById(R.id.publicSmall);
		publicBig = (RadioButton) findViewById(R.id.privatesBig);
		bxlt= (TextView) findViewById(R.id.bxlt);
		bxlt.setOnClickListener(this);
		Log.d("geek", "sssssss"+Contains.repairQuyu);
		quyu.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(publics.getId() == checkedId){
					groupPublic.setVisibility(View.VISIBLE);
					Contains.repairQuyu = "1";
					findViewById(R.id.addwarp).setVisibility(View.VISIBLE);
				}else if(privates.getId() == checkedId){
					groupPublic.setVisibility(View.GONE);
					Contains.repairQuyu = "3";
					findViewById(R.id.addwarp).setVisibility(View.GONE);
				}
			}
		});
		//小修 1 中大修 2 专有部位3
		groupPublic.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(publicBig.getId() == checkedId){
					Contains.repairQuyu = "2";
				}else if(publicSmall.getId() == checkedId){
					Contains.repairQuyu = "1";
				}
			}
		});
		
		
		Init();
		if (Contains.user == null) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		if (Bimp.tempSelectBitmap != null) {
			Log.d("geek", "论坛SeleteImgMainActivity onCreate "
					+ Bimp.tempSelectBitmap.size());
		} else {
			Log.d("geek",
					"论坛SeleteImgMainActivity onCreate Bimp.tempSelectBitmap.size()==null");
		}
		
		initDataFromNet();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("geek", "onStart sssssss"+Contains.repairQuyu);
		if (!Contains.repairContextStr.equals("") && edContext != null) {
			edContext.setText(Contains.repairContextStr + "");
			edContext.setSelection(Contains.repairContextStr.length());
		}
		if (!Contains.repairAddressStr.equals("") && address != null) {
			address.setText(Contains.repairAddressStr + "");
			address.setSelection(Contains.repairAddressStr.length());
		}
		
		if(Contains.repairQuyu.equals("3")){
			privates.setChecked(true);
			groupPublic.setVisibility(View.GONE);
		}else if(Contains.repairQuyu.equals("2")){
			publics.setChecked(true);
			publicBig.setChecked(true);
			groupPublic.setVisibility(View.VISIBLE);
		}else {
			publicSmall.setChecked(true);
			publics.setChecked(true);
			groupPublic.setVisibility(View.VISIBLE);
		}
	}

	@SuppressWarnings("deprecation")
	public void Init() {

		pop = new PopupWindow(this);
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		// 照相机
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		// 相册
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Contains.repairAddressStr = address.getText().toString();
				Contains.repairXiangmu = (String) private_spinner1.getSelectedItem();
				
				AlbumHelper helper = AlbumHelper.getHelper();
				helper.init(getApplicationContext());
				if (!helper.getThumbnail()) {
					return;
				}
				Intent intent = new Intent(RepairCopy.this,
						AlbumActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in,
						R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});

		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("geek","position ="+position);
				Log.d("geek","Bimp.tempSelectBitmap.size() ="+Bimp.tempSelectBitmap.size());
				if (position == Bimp.tempSelectBitmap.size()) {
					if(Bimp.tempSelectBitmap.size() >=PublicWay.num){
						Toast.makeText(RepairCopy.this,"最多只能上传3张图片",Toast.LENGTH_SHORT).show();
						return;
					}
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							RepairCopy.this,
							R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				}
				else {
					Intent intent = new Intent(RepairCopy.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", position);
					startActivity(intent);
				}
			}
		});

		edContext = (EditText) findViewById(R.id.edContext);
		edContext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s != null && !"".equals(s.toString())) {
					Contains.repairContextStr = s.toString();
				}
			}
		});

		findViewById(R.id.activity_selectimg_send).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if(!Contains.repairQuyu.equals("3")){
							if ("".equals(address.getText().toString())) {
								Toast.makeText(getApplication(), "请输入报修地点", Toast.LENGTH_SHORT)
										.show();
								return;
							}
						}

						if ("".equals(edContext.getText().toString())) {
							Toast.makeText(getApplication(), "请输入报修详情", Toast.LENGTH_SHORT)
									.show();
							return;
						}

						progressDialog.show();

						//没有上传图片
						if (Bimp.tempSelectBitmap.size() == 0) {
							saveOrUpdateTalk();
							return;
						}

						final FileUpload fileUpload = new FileUpload(API.uploadImage,mHandler);
						for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
							String path = Bimp.tempSelectBitmap.get(i).toString();
								File file = new File(Bimp.tempSelectBitmap.get(
										i).getImagePath());
								if (!file.exists()) {
									continue;
								}
							fileUpload.addFile(file);
							Log.d("geek", i+" fileUpload path= "+file);
						}

							new Thread(new Runnable() {
								@Override
								public void run() {
									try {
									fileUpload.upload();
									}catch (Exception e){
										Log.d("geek", e.getMessage()+"eee");
										mHandler.sendEmptyMessage(uploadFaild);
									}
								}
							}).start();

					}
				});
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case uploadSuccess:
				saveOrUpdateTalk();
				break;
			case uploadFaild:
				if (progressDialog != null) {
					progressDialog.hide();
				}
				Toast.makeText(RepairCopy.this, "上传失败,请稍后再试!",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * @Title: saveOrUpdateTalk
	 * @Description: 请求服务器上传报修
	 * @return void
	 * @throws
	 */
	private void saveOrUpdateTalk() {
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("bx.baoxiuHouses", Contains.appYezhuFangwus.get(0).getXiangmuLoupan());
		parm.put("bx.baoxiuFloor",  Contains.appYezhuFangwus.get(0).getFwLoudong());
		parm.put("bx.baoxiuUnit", Contains.appYezhuFangwus.get(0).getFwDanyuan());
		parm.put("bx.baoxiuProperty", Contains.repairQuyu);
		parm.put("bx.baoxiuRoom", Contains.appYezhuFangwus.get(0).getFwFanghao()+"");
		parm.put("bx.baoxiuName", Contains.user.getYezhuName());
		parm.put("bx.baoxiuPhone", Contains.user.getYezhuShouji());
		if(Contains.repairAddressStr != null &&  !"".equals(Contains.repairAddressStr)){
			parm.put("bx.baoxiuPlace", Contains.repairAddressStr+"");
		}
		parm.put("bx.baoxiuProject",Contains.repairContextStr);

		Log.d("geek", "提交报修"+parm.toString());
		repairController.getRepairPSubmit(mRequestQueue, parm,Psubmitlistener);
	}

	 ResultListener<BaseEntity> Psubmitlistener  = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			progressDialog.hide();
			if (info.status != STATUS_CODE_OK) {
				onError(info.MSG);
				return;
			}
			ToastUtil.show(RepairCopy.this, "报修成功");
			exitThis(true);
		}

		@Override
		public void onErrorResponse(String errMsg) {
			onError(errMsg);
		}
	};
	
	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == 9) {
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.mipmap.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position)
						.getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				String path = FileUtils.saveBitmap(bm, fileName);
				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				if (path != null && !"".equals(path)) {
					takePhoto.setImagePath(path);
					Bimp.tempSelectBitmap.add(takePhoto);
				} else {
					Toast.makeText(RepairCopy.this, "",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitThis(false);
		}
		return true;
	}

	private void exitThis(boolean jump) {
		for (int i = 0; i < PublicWay.activityList.size(); i++) {
			if (null != PublicWay.activityList.get(i)) {
				PublicWay.activityList.get(i).finish();
			}
		}
		finish();
		if(jump){
			startActivity(RepairListActivity.class);
		}
		Contains.repairContextStr = "";
		Contains.repairAddressStr = "";
		Contains.repairQuyu = "3";
		Contains.repairXiangmu = "";
		Bimp.tempSelectBitmap.clear();
		Bimp.max = 0;
		if (bimap != null) {
			bimap = null;
		}

		if (pop != null) {
			if (pop.isShowing()) {
				pop.dismiss();
			}
			pop = null;
		}

	}

	@Override
	protected void initView() {
		
	}

	@Override
	protected void initDataFromLocal() {
		
	}

	@Override
	public void onClick(View v) {
		startActivity(RepairListActivity.class);
	}

	@Override
	protected void initDataFromNet() {
		if(repairController ==  null){
			repairController = new ReparirControllerImpl();
		}
		//repairController.getRepairXiangmu(mRequestQueue, listener);
	}
//	
//	private ResultListener<CxwyWxxiangmu> listener = new ResultListener<CxwyWxxiangmu>() {
//
//		@Override
//		public void onResponse(CxwyWxxiangmu info) {
//			progressDialog.hide();
//			// 获取请求码
//			if (info.status != STATUS_CODE_OK) {
//				onError(info.MSG);
//				return;
//			}
//			if (isEmptyList(info.getRows())) {
//				onError("获取失败");
//			} else {
//				xiangmu = new String[info.getRows().size()];
//				for (int i = 0; i < info.getRows().size(); i++) {
//					String xq = info.getRows().get(i).getName();
//					xiangmu[i] = xq;
//				}
//				ArrayAdapter aa = new ArrayAdapter(Repair.this,
//						android.R.layout.simple_dropdown_item_1line, xiangmu);
//				private_spinner1.setAdapter(aa);
//			}
//		}
//
//		@Override
//		public void onErrorResponse(String errMsg) {
//			onError("请求失败");
//		}
//	};
}
