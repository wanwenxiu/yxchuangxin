package com.yxld.yxchuangxin.activity.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.order.SureOrderActivity;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.CartController;
import com.yxld.yxchuangxin.controller.GoodsController;
import com.yxld.yxchuangxin.controller.PraiseController;
import com.yxld.yxchuangxin.controller.impl.CartControllerImpl;
import com.yxld.yxchuangxin.controller.impl.GoodsControllerImpl;
import com.yxld.yxchuangxin.controller.impl.PraiseControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallComment;
import com.yxld.yxchuangxin.entity.CxwyMallProduct;
import com.yxld.yxchuangxin.entity.ShopList;
import com.yxld.yxchuangxin.entity.SureOrderEntity;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.view.SlideShowView;

/**
 * @ClassName: GoodsDestailActivity
 * @Description: 商品详情
 * @author 1152008367@qq.com
 * @date 2015年12月18日 上午9:15:47
 */
@SuppressLint("SetJavaScriptEnabled")
public class GoodsDestailActivity extends BaseActivity implements ResultListener<CxwyMallComment> {

	/** 去购物车的图标 */
	private ImageView ToCart = null;
	/** 广告轮播图 */
	private SlideShowView indexAdvs;

	/** 商品名*/
	private TextView goods_name;

	/** 商品价格*/
	private TextView goods_price;
	/** 商品地址*/
	private TextView address;
	/** 商品名*/
	private TextView goodNum;

	/** 商品评价总条数*/
	private TextView praiseTotal;
	/** 评价用户图像*/
	private ImageView praiseImg;
	/** 商品评价名称*/
	private TextView praiseName;
	/** 商品评级内容*/
	private TextView praiseBody,goBuy;

	/** 查看全部评价*/
	private TextView allPraise;

	/** 收藏按钮*/
	private ImageView collectImg;

	/** 减号按钮 */
	public ImageView SubtractNum;
	/** 当前数量*/
	public EditText cartGoodsNum;
	/** 加号按钮 */
	public ImageView AddNum;

	private WebView goodsDestailHtml;

	/** 加入购物车动画 */
	private ImageView mAnimImageView = null;
	private Animation mAnimation;
	/** 当前商品对象*/
	private CxwyMallProduct curGood = null;

	/** 是否已收藏 0 收藏 1 未收藏*/
	private int collectType = 1;

	/** 评价接口实现类*/
	private PraiseController praiseController;

	/** 购物车接口操作类*/
	private CartController cartController;

	/** 商品接口操作类*/
	private GoodsController goodController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.goods_destail_activity);
		getSupportActionBar().setTitle("商品详情");
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
		ToCart = (ImageView) findViewById(R.id.ToCart);
		ToCart.setOnClickListener(this);

		indexAdvs = (SlideShowView) findViewById(R.id.ad_view);

		findViewById(R.id.returnImg).setOnClickListener(this);
		findViewById(R.id.addCart).setOnClickListener(this);

		mAnimImageView = (ImageView) findViewById(R.id.cart_anim_icon);
		goods_name = (TextView) findViewById(R.id.goods_name);
		goods_price = (TextView) findViewById(R.id.goods_price);
		address = (TextView) findViewById(R.id.address);
		goodNum = (TextView) findViewById(R.id.goodNum);

		praiseTotal = (TextView) findViewById(R.id.praiseTotal);
		praiseName = (TextView) findViewById(R.id.praiseName);
		praiseBody = (TextView) findViewById(R.id.praiseBody);
		praiseImg = (ImageView) findViewById(R.id.praiseImg);

		allPraise = (TextView) findViewById(R.id.allPraise);
		allPraise.setOnClickListener(this);

		collectImg = (ImageView) findViewById(R.id.collectImg);
		collectImg.setOnClickListener(this);

		SubtractNum = (ImageView)findViewById(R.id.cartOut);
		SubtractNum.setOnClickListener(this);
		AddNum = (ImageView)findViewById(R.id.cart_Add);
		AddNum.setOnClickListener(this);
		cartGoodsNum = (EditText)findViewById(R.id.cart_goods_Num);

		goBuy = (TextView)findViewById(R.id.goBuy);
		goBuy.setOnClickListener(this);

		goodsDestailHtml = (WebView) findViewById(R.id.webview);
	}

	@Override
	protected void initDataFromLocal() {
		Intent intent = this.getIntent();
		curGood = (CxwyMallProduct)intent.getSerializableExtra("goods");
		Log.d("geek", "curGood ="+curGood.toString());

		initdata();
		initAnim();

		initDataFromNet();

		WebSettings webSettings = goodsDestailHtml.getSettings();
		// 设置WebView属性，能够执行JavaScript脚本
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置支持缩放
		webSettings.setBuiltInZoomControls(false);
		// 加载需要显示的网页
		goodsDestailHtml.loadUrl(API.URL_GOODS_DESTAIL_WEB+curGood.getShangpinId());
		// 设置Web视图 监听
		goodsDestailHtml.setWebViewClient(new webViewClient());
	}

	private void initdata() {
		ArrayList<String> urls = new ArrayList<>();

		if(curGood.getShangpinImgSrc1() != null && !"".equals(curGood.getShangpinImgSrc1())){
			String[] urlArray =  curGood.getShangpinImgSrc1().split(";");
			for (int i = 0; i <urlArray.length; i++) {
				urls.add(API.PIC+urlArray[i]);
			}
		}

		indexAdvs.setImageUris(urls, getApplication(),true);
		//LoadingImg.LoadingImgs(this).displayImage(urls.get(0),indexAdvs,LoadingImg.option1);
		Log.d("geek", "GoodsDestailActivity initdata（）urls"+urls.toString());
		/*indexindexAdvsAdvs.setImageResources(urls,
				new ImageCycleView.ImageCycleViewListener() {
					@Override
					public void onImageClick(int position, View imageView) {
						Toast.makeText(GoodsDestailActivity.this,
								"首页轮播图第" + position + "张", Toast.LENGTH_LONG)
								.show();
					}
			}, 0);*/

		if(curGood != null){
			goods_name.setText(curGood.getShangpinShangpName()+"("+curGood.getShangpinGuige()+")");
			goods_price.setText("￥ "+curGood.getShangpinRmb()+"");
			if(curGood.getShangpinHave() == 0){
				goodNum.setText("缺货");
			}else{
				goodNum.setText("剩余"+curGood.getShangpinNum()+"件");
			}
			address.setText(curGood.getShangpinProject()+"");
		}
	}

	private void initAnim() {
		mAnimation = AnimationUtils.loadAnimation(this, R.anim.cart_anim);
		mAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mAnimImageView.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.returnImg:
				finish();
				break;
			case R.id.addCart:
				if(cartGoodsNum.getText().toString() == null || "".equals(cartGoodsNum.getText().toString()) || "0".equals(cartGoodsNum.getText().toString())){
					ToastUtil.show(GoodsDestailActivity.this,"请输入正确数量");
					return;
				}
				joinCartUrl();
				break;
			case R.id.allPraise:
				if(allPraise.getText().toString().equals("暂无评价")){
					return;
				}
				Bundle bundle = new Bundle();
				bundle.putString("goodsId", curGood.getShangpinId()+"");
				startActivity(GoodsPraiseListActivity.class,bundle);
				break;
			case R.id.collectImg:
				progressDialog.show();
				if(goodController == null){
					goodController = new GoodsControllerImpl();
				}
				//当前状态为已收藏时，调用删除收藏接口，否则调用收藏接口
				if(collectType == 0){
					goodController.deleteCollectGoodsFromId(mRequestQueue, new Object[]{"",curGood.getShangpinId(),Contains.user.getYezhuId()}, addCollectListener);
				}else{
					Map<String, String> params = new HashMap<String, String>();
					params.put("collection.collectionShangpId", curGood.getShangpinId()+ "");
					params.put("collection.collectionShangpName", curGood.getShangpinShangpName());
					params.put("collection.collectionShangpOneRmb",curGood.getShangpinRmb()+"");
					params.put("collection.collectionShangpSpec", curGood.getShangpinGuige());
					params.put("collection.collectionImgSrc", curGood.getShangpinImgSrc1());
					params.put("collection.collectionUserId",Contains.user.getYezhuId()+"");
					Log.d("geek","收藏商品 params"+params.toString());
					goodController.getCollectGoodsFromId(mRequestQueue, params, addCollectListener);
				}
				break;
			case R.id.cartOut:
				if(cartGoodsNum.getText().toString() == null || "".equals(cartGoodsNum.getText().toString()) || "0".equals(cartGoodsNum.getText().toString())){
					ToastUtil.show(GoodsDestailActivity.this,"请输入正确数量");
					return;
				}
				setCount(false);
				break;
			case R.id.cart_Add:
				if(cartGoodsNum.getText().toString() == null || "".equals(cartGoodsNum.getText().toString()) || "0".equals(cartGoodsNum.getText().toString())){
					ToastUtil.show(GoodsDestailActivity.this,"请输入正确数量");
					return;
				}
				setCount(true);
				break;
			case R.id.goBuy:
				if(cartGoodsNum.getText().toString() == null || "".equals(cartGoodsNum.getText().toString()) || "0".equals(cartGoodsNum.getText().toString())){
					ToastUtil.show(GoodsDestailActivity.this,"请输入正确数量");
					return;
				}
				//拼接确认订单集合
				Contains.sureOrderList.clear();
				SureOrderEntity entity = new SureOrderEntity(curGood.getShangpinId()+"",cartGoodsNum.getText().toString(),  "", curGood.getShangpinImgSrc1(), curGood.getShangpinRmb() + "",curGood.getShangpinShangpName(), curGood.getShangpinGuige());
				Contains.sureOrderList.add(entity);
				Log.d("geek", "立即购买 Contains.sureOrderList"+Contains.sureOrderList.toString());
				startActivity(SureOrderActivity.class);
				break;
			case R.id.ToCart:
				// 进入购物车
				Intent intentCart = new Intent(getResources().getString(
						R.string.index_broad));
				intentCart.putExtra("IndexJumpTag", 1);
				sendBroadcast(intentCart);
				setResult(ShopListActivity.JUMP_CART);
				finish();
				break;
			default:
				break;
		}
	}

	/**
	 * @Title: setCount
	 * @Description: 修改商品数量
	 * @param operate
	 * @return void
	 * @throws
	 */
	private void setCount(boolean operate){
		int count = Integer.parseInt(cartGoodsNum.getText().toString());
		if(operate){
			count++;
		}
		else{
			if(count==1){

			}else{
				count--;
			}
		}
		cartGoodsNum.setText(count+"");
	}

	@Override
	protected void initDataFromNet() {
		super.initDataFromNet();
		if(praiseController == null){
			praiseController = new PraiseControllerImpl();
		}
		praiseController.getPraiseListFromGoodsID(mRequestQueue, new Object[]{5,1,curGood.getShangpinId(),"",Contains.user.getYezhuId()}, this);
	}

	@Override
	public void onResponse(CxwyMallComment info) {
		// 获取请求码
		if (info.status != STATUS_CODE_OK) {
			onError(info.MSG);
			return;
		}

		if(info.getCommentList()!= null && info.getCommentList().size() != 0){
			praiseName.setText(info.getCommentList().get(0).getPingjiaName());
			praiseName.setVisibility(View.VISIBLE);

			praiseBody.setText(info.getCommentList().get(0).getPingjiaBody());
			praiseBody.setVisibility(View.VISIBLE);

			praiseImg.setVisibility(View.VISIBLE);

			allPraise.setText("查看全部评价");
		}else{
			praiseName.setVisibility(View.GONE);
			praiseBody.setVisibility(View.GONE);
			praiseImg.setVisibility(View.GONE);
			allPraise.setText("暂无评价");
		}
		praiseTotal.setText("宝贝评价（"+ info.gettotal() +"）");

		collectType = info.getcollection();
		if(collectType == 1){
			collectImg.setImageResource(R.mipmap.collect_good_x);
		}else{
			collectImg.setImageResource(R.mipmap.collect_good_y);
		}

		progressDialog.hide();
	}

	@Override
	public void onErrorResponse(String errMsg) {
		onError(errMsg);
	}

	/**
	 * @Title: joinCartUrl
	 * @Description: 加入购物车请求
	 * @return void
	 * @throws
	 */
	private void joinCartUrl() {
		if(!progressDialog.isShowing()){
			progressDialog.show();
		}
		if (cartController == null) {
			cartController = new CartControllerImpl();
		}
		//请求后台进行添加商品至购物车请求
		Map<String, String> params = new HashMap<String, String>();
		params.put("cart.cartShangpNum",curGood.getShangpinId()+"");
		params.put("cart.cartShangpName", curGood.getShangpinShangpName());
		params.put("cart.cartOneRmb", curGood.getShangpinRmb()+"");
		params.put("cart.cartImgSrc", curGood.getShangpinImgSrc1());
		params.put("cart.cartNum", cartGoodsNum.getText().toString());
		params.put("cart.cartSpare1", Contains.user.getYezhuId()+"");
		cartController.addInfoToCart(mRequestQueue, params, addCartListener);
	}

	/** 添加至购物车请求*/
	private ResultListener<BaseEntity> addCartListener = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			if(progressDialog.isShowing()){
				progressDialog.hide();
			}
			if (info.status != 0) {
				ToastUtil.show(GoodsDestailActivity.this, ((BaseEntity) info).MSG);
				return;
			}
			mAnimImageView.setVisibility(View.VISIBLE);
			mAnimImageView.startAnimation(mAnimation);
		}

		@Override
		public void onErrorResponse(String errMsg) {
			Toast.makeText(GoodsDestailActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
		}
	};

	/** 添加至收藏请求*/
	private ResultListener<BaseEntity> addCollectListener = new ResultListener<BaseEntity>() {

		@Override
		public void onResponse(BaseEntity info) {
			progressDialog.hide();
			if (info.status != 0) {
				ToastUtil.show(GoodsDestailActivity.this, ((BaseEntity) info).MSG);
				return;
			}

			if(collectType == 1){
				collectType = 0;
				collectImg.setImageResource(R.mipmap.collect_good_y);
			}else{
				collectType = 1;
				collectImg.setImageResource(R.mipmap.collect_good_x);
			}
		}

		@Override
		public void onErrorResponse(String errMsg) {
			progressDialog.hide();
			Toast.makeText(GoodsDestailActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
		}
	};

	// Web视图
	private class webViewClient extends WebViewClient {

		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			Log.d("geek", "url"+url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
		}

		@Override
		public void onPageFinished(WebView view, String url) {
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
									String description, String failingUrl) {
		}
	}
}
