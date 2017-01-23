package com.yxld.yxchuangxin.activity.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.orhanobut.logger.Logger;
import com.p2p.core.BaseMonitorActivity;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PSpecial.HttpErrorCode;
import com.p2p.core.P2PValue;
import com.p2p.core.P2PView;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.APPCamera;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.yoosee.P2PListener;
import com.yxld.yxchuangxin.yoosee.SettingListener;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.HashMap;
import java.util.Map;

import static com.p2p.core.MediaPlayer.mContext;
import static com.yxld.yxchuangxin.base.AppConfig.APPID;
import static com.yxld.yxchuangxin.base.AppConfig.APPToken;

/**
 * Created by yishangfei on 2016/8/9 0009.
 */

public class CameraActivity extends BaseMonitorActivity implements View.OnClickListener {
    public static String P2P_ACCEPT = "com.yoosee.P2P_ACCEPT";
    public static String P2P_READY = "com.yoosee.P2P_READY";
    public static String P2P_REJECT = "com.yoosee.P2P_REJECT";

    private YeZhuController yeZhuController;

    /**
     * 网络请求列队
     */
    protected RequestQueue mRequestQueue;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TopRightMenu mTopRightMenu;
    private ImageView image_link,image_mute,image_screen;//开始监控  静音   全屏
    private RelativeLayout rl_p2pview, control_bottom;

    private boolean isMute = false;
    private boolean isReject = false;
    private int screenWidth, screenHeight;
    private String callID, CallPwd, LoginID;//设备号  设备密码  登录返回
    //    private Button choose_video_format, defence_state, close_voice, send_voice, screenshot, hungup, iv_half_screen;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mRequestQueue = Volley.newRequestQueue(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initDataFromNet();
        initUI();
        getScreenWithHeigh();
        regFilter();
        callID = "5969657";//设备号
        CallPwd = "123";
        String pwd = P2PHandler.getInstance().EntryPassword(CallPwd);//经过转换后的设备密码
        P2PHandler.getInstance().call(LoginID, pwd, true, 1, callID, "", "", 2, callID);
    }

    public void getScreenWithHeigh() {
        // 获取屏幕的宽度和高度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.viewpage);
        viewPager.setAdapter(mAdapter);
//        control_bottom = (RelativeLayout) findViewById(R.id.control_bottom);
//        control_top = (RelativeLayout) findViewById(R.id.control_top);
        image_link = (ImageView) findViewById(R.id.image_link);
        image_link.setOnClickListener(this);
        image_screen = (ImageView) findViewById(R.id.image_screen);
        image_screen.setOnClickListener(this);
        image_mute = (ImageView) findViewById(R.id.image_mute);
        image_mute.setOnClickListener(this);
//        choose_video_format = (Button) findViewById(R.id.choose_video_format);
//        defence_state = (Button) findViewById(R.id.defence_state);
//        close_voice = (Button) findViewById(R.id.close_voice);
//        send_voice = (Button) findViewById(R.id.send_voice);
//        screenshot = (Button) findViewById(R.id.screenshot);
//        hungup = (Button) findViewById(R.id.hungup);
//        iv_half_screen = (Button) findViewById(R.id.iv_half_screen);
//        choose_video_format.setOnClickListener(this);
//        defence_state.setOnClickListener(this);
//        close_voice.setOnClickListener(this);
//        screenshot.setOnClickListener(this);
//        hungup.setOnClickListener(this);
//        iv_half_screen.setOnClickListener(this);


//        //对讲
//        send_voice.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        setMute(false);
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        setMute(true);
//                        return true;
//                }
//                return false;
//            }
//        });

        pView = (P2PView) findViewById(R.id.pview);
        rl_p2pview = (RelativeLayout) findViewById(R.id.r_p2pview);
        initP2PView(7, P2PView.LAYOUTTYPE_TOGGEDER);//7是设备类型(技威定义的)
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e("dxsTest", "config:" + newConfig.orientation);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main);
            setHalfScreen(false);
//            control_top.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
//            control_bottom.setVisibility(View.VISIBLE);
//            以下代码是因为 方案商设备类型很多,视频比例也比较多
            //客户更具自己的视频比例调整画布大小
            //这里的实现比较绕,如果能弄清楚这部分原理,客户可自行修改此处代码
            if (P2PView.type == 1) {
                if (P2PView.scale == 0) {
                    isFullScreen = false;
                    pView.halfScreen();//刷新画布比例
                } else {
                    isFullScreen = true;
                    pView.fullScreen();
                }
            } else {
                //这里本应该用设备类型判断,如果只有一种类型可不用这么麻烦
                if (7 == P2PValue.DeviceType.NPC) {
                    isFullScreen = false;
                    pView.halfScreen();
                } else {
                    isFullScreen = true;
                    pView.fullScreen();
                }
            }
            RelativeLayout.LayoutParams parames = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            rl_p2pview.setLayoutParams(parames);//调整画布容器宽高(比例)
        } else {
            setHalfScreen(true);
//            control_top.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
//            control_bottom.setVisibility(View.GONE);
            if (isFullScreen) {
                isFullScreen = false;
                pView.halfScreen();
            }
            //这里简写,只考虑了16:9的画面类型  大部分设备画面比例是这种
//            int Heigh = screenWidth * 9 / 16;
            RelativeLayout.LayoutParams parames = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
//            parames.height = Heigh;
            rl_p2pview.setLayoutParams(parames);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_link:
                callID = "5969657";//设备号
                CallPwd = "123";
                String pwd = P2PHandler.getInstance().EntryPassword(CallPwd);//经过转换后的设备密码
                P2PHandler.getInstance().call(LoginID, pwd, true, 1, callID, "", "", 2, callID);
                break;
//            case R.id.choose_video_format:
//                P2PHandler.getInstance().setVideoMode(P2PValue.VideoMode.VIDEO_MODE_HD);
//                break;
//            case R.id.defence_state:
//                P2PHandler.getInstance().setVideoMode(P2PValue.VideoMode.VIDEO_MODE_SD);
//                break;
//            case R.id.screenshot:
//                // 参数是一个标记,截图回调会原样返回这个标记
//                //注意SD卡权限
//                captureScreen(-1);
//                break;
//            case R.id.close_voice:
            case R.id.image_mute: //静音
                changeMuteState();
                break;
            case R.id.image_screen://全屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
//            case R.id.hungup: //挂断
//                reject();
//            case R.id.iv_half_screen:  //竖屏
//                break;
        }
    }

    //挂断
    public void reject() {
        if (!isReject) {
            isReject = true;
            P2PHandler.getInstance().reject();
        }
    }

    FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        private String[] mTitles = new String[]{"设备", "操作"};

        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return new DeviceFragment();
            }
            return new WorkingFragment();
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

    };


    private void changeMuteState() {
        isMute = !isMute;
        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (manager != null) {
            if (isMute) {
                Log.d("666","静音");
                image_mute.setImageResource(R.mipmap.icon_mute);
                manager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            } else {
                Log.d("666","声音 ");
                image_mute.setImageResource(R.mipmap.icon_sound);
                manager.setStreamVolume(AudioManager.STREAM_MUSIC, manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
            }
        }
    }


    public void regFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(P2P_REJECT);
        filter.addAction(P2P_ACCEPT);
        filter.addAction(P2P_READY);
        registerReceiver(mReceiver, filter);
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(P2P_ACCEPT)) {
                int[] type = intent.getIntArrayExtra("type");
                P2PView.type = type[0];
                P2PView.scale = type[1];
                Toast.makeText(context, "监控数据接收", Toast.LENGTH_SHORT).show();
                Log.e("dxsTest", "监控数据接收:" + callID);
                P2PHandler.getInstance().openAudioAndStartPlaying(1);//打开音频并准备播放，calllType与call时type一致
            } else if (intent.getAction().equals(P2P_READY)) {
                Toast.makeText(context, "监控准备,开始监控", Toast.LENGTH_SHORT).show();
                pView.sendStartBrod();
            } else if (intent.getAction().equals(P2P_REJECT)) {
                Toast.makeText(context, "监控挂断", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        P2PHandler.getInstance().reject();
        P2PHandler.getInstance().p2pDisconnect();
        super.onDestroy();

    }

    @Override
    protected void onP2PViewSingleTap() {

    }

    @Override
    protected void onP2PViewFilling() {

    }


    @Override
    protected void onCaptureScreenResult(boolean isSuccess, int prePoint) {
        if (isSuccess) {
            Toast.makeText(this, R.string.screenshot_success, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.screenshot_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onVideoPTS(long videoPTS) {

    }

    @Override
    public int getActivityInfo() {
        return 0;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View tianjia = findViewById(R.id.add);
        switch (item.getItemId()) {
            case R.id.add:
                mTopRightMenu = new TopRightMenu(CameraActivity.this);
                mTopRightMenu
                        .setHeight(480)     //默认高度480
                        .setWidth(320)      //默认宽度wrap_content
                        .showIcon(true)     //显示菜单图标，默认为true
                        .dimBackground(true)           //背景变暗，默认为true
                        .needAnimationStyle(true)   //显示动画，默认为true
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                        .addMenuItem(new com.zaaach.toprightmenu.MenuItem(R.mipmap.icon_shengbo, "声波添加"))
                        .addMenuItem(new com.zaaach.toprightmenu.MenuItem(R.mipmap.icon_shengbo, "wifi添加"))
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                switch (position) {
                                    case 0:
                                        Intent config = new Intent(CameraActivity.this, CameraConfigActivity.class);
                                        startActivity(config);
                                        break;
                                    case 1:
                                        break;
                                }
                            }
                        })
                        .showAsDropDown(tianjia, -225, 0);
                break;
            case android.R.id.home:
                finish();
                System.gc();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //色相头登录
    private void initDataFromNet() {
        if (yeZhuController == null) {
            yeZhuController = new YeZhuControllerImpl();
        }
        String sha256=StringUitl.encryptSHA256ToString(APPID+Contains.user.getYezhuShouji());
        String md5=StringUitl.Md5(sha256+3+sha256);
        Logger.d("我的包名是："+getPackageName());
        Logger.d("SHA256加密 ："+sha256);
        Logger.d("MD5 32bit："+md5);
        Map<String, String> map = new HashMap<String, String>();
        map.put("AppVersion","56492291");
        map.put("AppOS","3");//0.其它 1.windows 2.iOS 3.android 4.arm_linux
        map.put("AppName","xinshequ");//app的英文名
        map.put("Language","zh-Hans");//语言的缩写
        map.put("ApiVersion","1");//固定传1
        map.put("AppID",APPID);//技威公司分配
        map.put("AppToken",APPToken);//技威公司分配
        map.put("PackageName",getPackageName());//包名
        map.put("Option","3");//传3即可
        map.put("PlatformType","3");//传3即可
        map.put("UnionID",sha256);//UnionID=sha256(AppID+唯一序列号);
        map.put("User",""); //传空即可
        map.put("UserPwd",""); //传空即可
        map.put("Token",""); //传空即可
        map.put("StoreID","" ); //传空即可
        map.put("Sign",md5);//Sign=md5(UnionID+PlatformType+UnionID),md5是md5_32bit的加密算法
        yeZhuController.getAllCamera(mRequestQueue, map, listener);
    }

    private ResultListener<APPCamera> listener=new ResultListener<APPCamera>() {
        @Override
        public void onResponse(APPCamera info) {
            Logger.d("打印的网络数据是"+info.toString());
            switch (info.getError_code()){
                case HttpErrorCode.ERROR_0:
                    int code1 = Integer.parseInt(info.getP2PVerifyCode1());
                    int code2 = Integer.parseInt(info.getP2PVerifyCode2());
                    boolean connect = P2PHandler.getInstance().p2pConnect(info.getUserID(), code1, code2);
                    if (connect) {
                        P2PHandler.getInstance().p2pInit(mContext, new P2PListener(), new SettingListener());
                        LoginID = info.getUserID();
                    } else {
                        Toast.makeText(CameraActivity.this, "" + connect, Toast.LENGTH_LONG).show();
                    }
                    break;
                case HttpErrorCode.ERROR_998:
                    initDataFromNet();
                    break;
                case HttpErrorCode.ERROR_10902011:
                    Toast.makeText(CameraActivity.this, "用户不存在", Toast.LENGTH_LONG).show();
                    break;
                default:
                    String msg = String.format("登录失败测试版(%s)", info.getError_code());
                    Toast.makeText(CameraActivity.this, msg, Toast.LENGTH_LONG).show();
                    break;
            }
        }

        @Override
        public void onErrorResponse(String errMsg) {

        }
    };
}
