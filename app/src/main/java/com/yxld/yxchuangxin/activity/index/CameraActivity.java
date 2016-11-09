package com.yxld.yxchuangxin.activity.index;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.p2p.core.BaseMonitorActivity;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PView;
import com.p2p.core.utils.MD5;
import com.p2p.core.utils.MyUtils;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.yoosee.P2PListener;
import com.yxld.yxchuangxin.yoosee.SettingListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import top.wefor.circularanimlib.CircularAnimUtil;

/**
 * Created by yishangfei on 2016/8/9 0009.
 */

public class CameraActivity extends BaseMonitorActivity implements View.OnClickListener {

    /**
     * 授权列表
     */
    private ListView authorizedList;
    private static String CURRENT_SERVER = "http://api1.cloudlinks.cn/";
    private static String LOGIN_URL = CURRENT_SERVER + "Users/LoginCheck.ashx";
    public static String P2P_ACCEPT = "com.yoosee.P2P_ACCEPT";
    public static String P2P_READY = "com.yoosee.P2P_READY";
    public static String P2P_REJECT = "com.yoosee.P2P_REJECT";
    private Button btn_screenShot, btn_monitor;
    private String pwd, callId, callPwd;
    private TextView tv_content;
    private EditText et_callId, et_callPwd;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //进行音视频监控要在登陆之后进行
        new LoginTask("0810090", "111222").execute();
        initView();
    }


    private void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        btn_screenShot = (Button) findViewById(R.id.btn_screenShot);
        btn_monitor = (Button) findViewById(R.id.btn_monitor);
        btn_screenShot.setOnClickListener(this);
        btn_monitor.setOnClickListener(this);
        et_callId = (EditText) findViewById(R.id.et_callid);
        et_callPwd = (EditText) findViewById(R.id.et_callpwd);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }


    @Override
    protected void onCaptureScreenResult(boolean isSuccess, int prePoint) {
        if (isSuccess) {
            tv_content.append("\n 截图成功，默认保存路径为SD下面的screenshot");
        } else {
            tv_content.append("\n 截图失败");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_screenShot:
                tv_content.append("开始截屏。。。。");
                captureScreen(-1);
                break;
            case R.id.btn_monitor:
                hideKeyboard();
                callId = et_callId.getText().toString();//设备号
                callPwd = et_callPwd.getText().toString();
                if (callId.equals("") || callPwd.equals("")) {
                    Toast.makeText(CameraActivity.this, "请输入设备号和密码", Toast.LENGTH_SHORT).show();
                } else {
                    tv_content.setText("发送监控命令......");
                    pwd = P2PHandler.getInstance().EntryPassword(callPwd);//经过转换后的设备密码
                    String contactId = "0810090";//登陆的用户账号
                    P2PHandler.getInstance().call(contactId, pwd, true, 1, callId, "", "", 2, callId);
                }
                break;
            case R.id.fab:
                Intent fab = new Intent(this, CameraConfigActivity.class);
                startActivity(fab);
                break;
            default:
                break;
        }
    }

    public void regFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(P2P_REJECT);
        filter.addAction(P2P_ACCEPT);
        filter.addAction(P2P_READY);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(P2P_ACCEPT)) {
                tv_content.append("\n 监控数据接收");
            } else if (intent.getAction().equals(P2P_READY)) {
                tv_content.append("\n 监控准备,开始监控");
            } else if (intent.getAction().equals(P2P_REJECT)) {
                tv_content.append("\n 监控挂断");
            }
        }
    };

    @Override
    public int getActivityInfo() {
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        pView = (P2PView) findViewById(R.id.pview);
        initP2PView(7);
        //setMute(true);  //设置手机静音
        P2PHandler.getInstance().openAudioAndStartPlaying(1);//打开音频并准备播放，calllType与call时type一致
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    //摄像头登陆
    class LoginTask extends AsyncTask {
        String username;
        String password;

        public LoginTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
            return login(username, password);
        }

        @Override
        protected void onPostExecute(Object object) {
            try {
                parseObj(object);
                regFilter();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void parseObj(Object object) throws Exception {
        JSONObject json = (JSONObject) object;
        String error_code = json.getString("error_code");
        if (error_code.equals("0")) {
            String rCode1 = json.getString("P2PVerifyCode1");
            String rCode2 = json.getString("P2PVerifyCode2");
            P2PHandler.getInstance().p2pInit(this, new P2PListener(), new SettingListener());
            P2PHandler.getInstance().p2pConnect("0810090", Integer.parseInt(rCode1), Integer.parseInt(rCode2));
        }
    }

    public JSONObject login(String username, String password) {
        if (MyUtils.isNumeric(username)) {
            username = String.valueOf((Integer.parseInt(username) | 0x80000000));
        }
        JSONObject jObject = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        MD5 md = new MD5();

        params.add(new BasicNameValuePair("User", username));
        params.add(new BasicNameValuePair("Pwd", md.getMD5ofStr(password)));

        params.add(new BasicNameValuePair("VersionFlag", "1"));
        params.add(new BasicNameValuePair("AppOS", "3"));

        params.add(new BasicNameValuePair("AppVersion", "3014666"));
        try {
            jObject = new JSONObject(doPost(params, LOGIN_URL));
            Log.e("my", jObject.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jObject;
    }

    public String doPost(List<NameValuePair> params, String url) throws Exception {
        Log.e("my", "current-server:" + url);
        String result = null;
        // 鏂板缓HttpPost瀵硅薄
        HttpPost httpPost = new HttpPost(url);
        // 璁剧疆瀛楃闆�
        HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
        // 璁剧疆鍙傛暟瀹炰綋
        httpPost.setEntity(entity);
        // 鑾峰彇HttpClient瀵硅薄
        HttpClient httpClient = new DefaultHttpClient();
        // 杩炴帴瓒呮椂
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
        // 璇锋眰瓒呮椂
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
        try {
            HttpResponse httpResp = httpClient.execute(httpPost);
            int http_code;
            if ((http_code = httpResp.getStatusLine().getStatusCode()) == 200) {
                result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
                Log.e("my", "original http:" + result);
            } else {
                // result = "{\"error_code\":998}";
                throw new Exception();
            }
            JSONObject jObject = new JSONObject(result);
            int error_code = jObject.getInt("error_code");
            Log.e("leleTest", "error_code=" + error_code);
            if (error_code == 1 || error_code == 29 || error_code == 999) {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
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
    protected void onP2PViewSingleTap() {

    }


    @Override
    protected void onGoBack() {

    }

    @Override
    protected void onGoFront() {

    }

    @Override
    protected void onExit() {

    }
}
