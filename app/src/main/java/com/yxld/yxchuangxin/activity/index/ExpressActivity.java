package com.yxld.yxchuangxin.activity.index;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;

/**
 * @author wwx
 * @ClassName: Express
 * @Description: 快递
 * @date 2016年4月14日 下午5:13:17
 */
@SuppressLint("SetJavaScriptEnabled")
public class ExpressActivity extends BaseActivity {

	private WebView express_webView;
	private ProgressBar bar;
	@Override
	protected void initContentView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_express);
		getSupportActionBar().setTitle("邮包查询");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void initView() {
		bar = (ProgressBar) findViewById(R.id.myProgressBar);
		express_webView = (WebView) findViewById(R.id.express_webView);
		init();
		express_webView.getSettings().setCacheMode(
				WebSettings.LOAD_CACHE_ELSE_NETWORK);
		express_webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				if (newProgress == 100) {
					// 网页加载完成
					bar.setVisibility(View.GONE);
				} else {
					// 加载中
					if (View.GONE == bar.getVisibility()) {
						bar.setVisibility(View.VISIBLE);
					}
					bar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}
		});
	}

	private void init() {
		WebSettings webSettings = express_webView.getSettings();
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDefaultFontSize(16);
		webSettings.setDisplayZoomControls(false);
		webSettings.setSupportZoom(true);
		//自适应屏幕
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		express_webView.getSettings().setDefaultTextEncodingName("UTF -8");
		express_webView.getSettings().setJavaScriptEnabled(true);
		express_webView.getSettings().setAllowContentAccess(true);
		express_webView.getSettings().setAppCacheEnabled(false);
		express_webView.getSettings().setUseWideViewPort(true);
		express_webView.getSettings().setLoadWithOverviewMode(true);
		express_webView.getSettings().setLayoutAlgorithm(
				WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		// WebView加载web资源
		express_webView.loadUrl("http://m.kuaidihelp.com/");
		;
		// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		express_webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (express_webView.canGoBack()) {
				express_webView.goBack();// 返回上一页面
				return true;
			} else {
				finish();// 退出程序
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void initDataFromLocal() {

	}

	@Override
	public void onClick(View v) {

	}

}
