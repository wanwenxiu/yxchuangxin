package com.yxld.yxchuangxin.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yishangfei on 2016/9/9 0009.
 */

public class YinlLianWebViewActivity extends BaseActivity {
    private WebView webView;
    private ProgressBar bar;

    private String name;
    private String address;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        address = bundle.getString("address");
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     }

    @Override
    protected void initView() {
        webView= (WebView) findViewById(R.id.WwebView);
        bar= (ProgressBar) findViewById(R.id.WProgressBar);
        init();
        webView.getSettings().setCacheMode(
                WebSettings.LOAD_NO_CACHE);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
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
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDefaultFontSize(16);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setDefaultTextEncodingName("UTF -8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLayoutAlgorithm(
                WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        // WebView加载web资源
        webView.loadUrl(address);
        webView.addJavascriptInterface(new PayJavaScriptInterface(), "js");
        webView.loadUrl("javascript:callFromJava('1')");
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
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
            if (webView.canGoBack()) {
                webView.goBack();// 返回上一页面
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d("geek","返回键");
            if (webView != null && webView.canGoBack()) {
                finish();
            } else {
                finish();// 退出程序
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private class PayJavaScriptInterface {

        @JavascriptInterface
        public void jumpActivity(double param) {
            Contains.pay=4;
            Intent intent = new Intent(YinlLianWebViewActivity.this, PayWaySelectActivity.class);
            intent.putExtra("orderMoney", String.valueOf(param));
            intent.putExtra("orderShop","车位使用费");
            intent.putExtra("orderDetails","车位使用费");
            intent.putExtra("orderBianhao",System.currentTimeMillis()+"");
            intent.putExtra("paystatus","车费支付");
            startActivityForResult(intent, 100);
            Logger.d("PayJavaScriptInterface jumpActivity()");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        JSONObject node = new JSONObject();
        try {
            node.put("status", data.getStringExtra("backstring"));
            node.put("platform", data.getStringExtra("lx"));
            node.put("numberId", data.getStringExtra("bianhao") );
            webView.loadUrl("javascript:callFromJava(" + node  + ")");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
