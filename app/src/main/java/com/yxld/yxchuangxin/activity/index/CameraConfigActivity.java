package com.yxld.yxchuangxin.activity.index;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mediatek.elian.ElianNative;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.yoosee.UDPHelper;

/**
 * @author wwx
 * @ClassName: CameraConfigActivity
 * @Description: 居家安防
 * @date 2016年5月27日 下午5:14:24
 */
public class CameraConfigActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_sendWifi;
    private UDPHelper mHelper;
    private ElianNative elain;
    private boolean isSendWifiStop = true;
    private TextView tv_log;
    private EditText et_password;
    private EditText et_ssid;
    byte type = 9;

    static {
        System.loadLibrary("elianjni");
    }


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_camera_config);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void initView() {
        tv_log = (TextView) findViewById(R.id.tv_log);
        btn_sendWifi = (Button) findViewById(R.id.btn_sendWifi);
        et_ssid = (EditText) findViewById(R.id.et_ssid);
        et_password = (EditText) findViewById(R.id.et_password);
        mHelper = new UDPHelper(9988);
        listen();
        mHelper.StartListen();
        btn_sendWifi.setOnClickListener(this);
        getConnectWifiSsid();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isSendWifiStop) {
            stopSendWifi();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sendWifi:
                hideKeyboard();
                sendWifi();
                break;
            default:
                break;
        }
    }

    private void sendWifi() {
        if (elain == null) {
            elain = new ElianNative();
        }
        String ssid = et_ssid.getText().toString();
        String pwd = et_password.getText().toString();
        if (pwd.equals("") || ssid.equals("")) {
            Toast.makeText(CameraConfigActivity.this, "请输入wifi SSID和密码", Toast.LENGTH_SHORT).show();
        } else {
            tv_log.append("\n 发包中...请等待");
            elain.InitSmartConnection(null, 1, 1);
            elain.StartSmartConnection(ssid, pwd, "", type);
            isSendWifiStop = false;
        }
    }

    /**
     * 停止发包
     */
    private void stopSendWifi() {
        if (elain != null) {
            tv_log.append("\n 停止发包");
            elain.StopSmartConnection();
            isSendWifiStop = true;
        }
    }

    private void listen() {

        mHelper.setCallBack(new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case UDPHelper.HANDLER_MESSAGE_BIND_ERROR:
                        tv_log.append("\n 配网失败");
                        break;
                    case UDPHelper.HANDLER_MESSAGE_RECEIVE_MSG:
                        String ip = msg.getData().getString("ip");
                        String contactId = msg.getData().getString("contactId");
                        String frag = msg.getData().getString("frag");
                        String ipFlag = msg.getData().getString("ipFlag");
                        tv_log.append("\n 配网成功 ip:" + ip);
                        break;
                }
            }
        });
    }

    private String getConnectWifiSsid() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.d("wifiInfo", wifiInfo.toString());
        Log.d("SSID", wifiInfo.getSSID());
        if (wifiInfo.getSSID()==null){
            Toast.makeText(CameraConfigActivity.this,"请连接wifi后进行首次发包",Toast.LENGTH_SHORT).show();
        }else {
            String ssid = wifiInfo.getSSID();
            ssid = ssid.substring(1, ssid.length() - 1);
            et_ssid.setText(ssid);
            et_password.requestFocus();
        }

        return wifiInfo.getSSID();
    }

    //隐藏键盘
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    @Override
    protected void initDataFromLocal() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
