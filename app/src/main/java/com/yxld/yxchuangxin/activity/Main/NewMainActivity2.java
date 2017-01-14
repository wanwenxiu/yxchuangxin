package com.yxld.yxchuangxin.activity.Main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.sunfusheng.marqueeview.MarqueeView;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.index.AccessActivity;
import com.yxld.yxchuangxin.activity.index.ExpressActivity;
import com.yxld.yxchuangxin.activity.index.FeiYongListActivity;
import com.yxld.yxchuangxin.activity.index.YeZhuOpenDoorActivity;
import com.yxld.yxchuangxin.activity.index.selectimg.Repair;
import com.yxld.yxchuangxin.activity.login.WelcomeActivity;
import com.yxld.yxchuangxin.activity.mine.MemberActivity;
import com.yxld.yxchuangxin.base.AppConfig;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.PeiZhiController;
import com.yxld.yxchuangxin.controller.RepairController;
import com.yxld.yxchuangxin.controller.TongzhiController;
import com.yxld.yxchuangxin.controller.impl.PeiZhiControllerImpl;
import com.yxld.yxchuangxin.controller.impl.ReparirControllerImpl;
import com.yxld.yxchuangxin.controller.impl.TongzhiControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallPezhi;
import com.yxld.yxchuangxin.entity.RepairList;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.view.BadgeImageView;
import com.yxld.yxchuangxin.view.ImageCycleView;
import com.yxld.yxchuangxin.view.Utils;
import com.yxld.yxchuangxin.view.VerticalSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * @author wwx
 * @ClassName: NewMainActivity
 * @Description:新首页
 * @date 2016年5月4日 下午5:39:42
 */
public class NewMainActivity2 extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ImageCycleView imageCycleView;

    private LinearLayout buttomwarp;

    private ImageView img3, img1, img2;
    private BadgeImageView mine;

    private LinearLayout wuyeWarp, serviceWarp, goMall;

    private VerticalSwipeRefreshLayout main;

    private TextView curPlace, marqueeTv;

    private MarqueeView secondaryActions;

    private PeiZhiController PeiZhiController;
    private TongzhiController tongzhiController;
    private ArrayList<String> urls = new ArrayList<>();

    private RepairController repairController;
    private static final double EARTH_RADIUS = 6378137.0;
    /**
     * 测试门禁
     */
    private LinearLayout menjin;

    /**
     * 动态获取定位权限
     */
    public final static int REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE = 124;

    public static List<String> logList = new CopyOnWriteArrayList<String>();
    /**
     * 记录二级菜单
     */
    private int count = 0;

    /**
     * 上次登录用户
     */
    private final String LAST_LOGIN_USER_ID = "lastLoginUserId";
    /**
     * 上次登录用户是否保存密码
     */
    private final String CB_SAVE_PWD = "cb_save_pwd";

    //高德定位
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.new_main_activity_layouts);
        AppConfig.setMainActivity(this);
        //当业主信息为空时或者业主手机号码为空，结束当前页进入欢迎页面
        if (Contains.user == null || Contains.user.getYezhuShouji() == null) {
            finish();
            AppConfig.getInstance().exit();
            startActivity(WelcomeActivity.class);
            return;
        }
        String alias = Contains.user.getYezhuShouji().toString();
        String account = Contains.user.getYezhuShouji().toString();
        MiPushClient.setAlias(NewMainActivity2.this, account, null);
        MiPushClient.setUserAccount(NewMainActivity2.this, alias, null);
        initView();
        setToorBar(false);

        //请求轮播图接口
        getlunbotubiao();
        //请求通知接口
        initTongzhi();
    }

    @Override
    protected void initView() {
        buttomwarp = (LinearLayout) findViewById(R.id.buttomwarp);
        main = (VerticalSwipeRefreshLayout) findViewById(R.id.main);
        mine = (BadgeImageView) findViewById(R.id.mine);
        mine.setOnClickListener(this);

        curPlace = (TextView) findViewById(R.id.curPlace);
        if (Contains.appYezhuFangwus != null && Contains.appYezhuFangwus.size() > 0) {
            curPlace.setText(Contains.appYezhuFangwus.get(0).getXiangmuLoupan());
        }
        if (Contains.user != null && Contains.user.getYezhuType() != null && Contains.user.getYezhuType() == 1 && "".equals(Contains.curSelectXiaoQuName)) {
            //初始化定位
            initLocation();
            startLocation();
        }
        curPlace.setOnClickListener(this);

        secondaryActions = (MarqueeView) findViewById(R.id.secondaryActions);
        List<String> info = new ArrayList<>();
        info.add("我的物业 >>\n" +
                "\n\n包含车辆识别、居家安防、放心出入、授权放行栏目");
        info.add("专享服务>>\n" +
                "\n\n您的专属维修专家，解决日常报修烦恼。处理过程实时跟踪，报修结果及时反馈");
        info.add("邮包查寄 >>\n" +
                "\n\n邮包信息我来查，精确及时到您家。快递寄件请找我，各大物流随您挑");
        info.add("个人中心 >>\n" +
                "\n\n包含房屋信息、入住成员管理、房屋出租、版本更新、关于我们栏目");
        info.add("投诉建议 >>\n" +
                "\n\n您的困惑，督促我们日常工作的完善。您的建议，引导我们服务品质的提升");
        if(secondaryActions != null && info != null){
            secondaryActions.startWithList(info);
        }
        secondaryActions.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                switch (position){
                    case 0: //我的物业
                        if (Contains.user == null || Contains.user.getYezhuType() == null || Contains.user.getYezhuType() != 0 || Contains.appYezhuFangwus.size() == 0) {
                            ToastUtil.show(NewMainActivity2.this, "业主信息不完善");
                            return;
                        }
                        startActivity(WuyeActivity.class);
                        break;
                    case 1: //专享服务
                        if (Contains.user == null || Contains.user.getYezhuType() == null || Contains.user.getYezhuType() != 0 || Contains.appYezhuFangwus.size() == 0) {
                            ToastUtil.show(NewMainActivity2.this, "业主信息不完善");
                            return;
                        }
                        startActivity(Repair.class);
                        break;
                    case 2: //邮包查寄
                            startActivity(ExpressActivity.class);
                        break;
                    case 3: //个人中心
//                        startActivity(MemberActivity.class);
                        if (Contains.user == null || Contains.user.getYezhuType() == null || Contains.user.getYezhuType() != 0 || Contains.appYezhuFangwus.size() == 0) {
                            ToastUtil.show(NewMainActivity2.this, "业主信息不完善");
                            return;
                        }
                        startActivity(WuyeActivity.class);
                        break;
                    case 4: //投诉建议
                        if (Contains.user == null || Contains.user.getYezhuType() == null || Contains.user.getYezhuType() != 0 || Contains.appYezhuFangwus.size() == 0) {
                            ToastUtil.show(NewMainActivity2.this, "业主信息不完善");
                            return;
                        }
                        if (Contains.user == null || Contains.appYezhuFangwus.size() == 0 || Contains.appYezhuFangwus.get(0) == null || Contains.appYezhuFangwus.get(0).getFwLoupanId() == null
                                || "".equals(Contains.appYezhuFangwus.get(0).getFwLoupanId())) {
                            Toast.makeText(NewMainActivity2.this, "需要在后台去配置您的业主信息", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent ts = new Intent();
                            ts.setClass(NewMainActivity2.this, // context
                                    WebViewActivity.class);// 跳转的activity
                            Bundle ts1 = new Bundle();
                            ts1.putString("name", "投诉建议");
                            ts1.putString("address", API.IP_PRODUCT + "/tousujianyi.jsp?yezhuid=" + Contains.user.getYezhuId() + "&tousuXiangmuId=" + Contains.appYezhuFangwus.get(0).getFwLoupanId());
                            ts.putExtras(ts1);
                            startActivity(ts);
                        }
                        break;
                }
            }
        });

        marqueeTv = (TextView) findViewById(R.id.marqueeTv);
        marqueeTv.setOnClickListener(this);

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);

        wuyeWarp = (LinearLayout) findViewById(R.id.wuyeWarp);
        serviceWarp = (LinearLayout) findViewById(R.id.serviceWarp);
        goMall = (LinearLayout) findViewById(R.id.goMall);

        wuyeWarp.setOnClickListener(this);
        serviceWarp.setOnClickListener(this);
        goMall.setOnClickListener(this);
        main.setOnRefreshListener(this);
        main.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        main.setProgressViewOffset(false,0,100);
        imageCycleView = (ImageCycleView) findViewById(R.id.indexAdvs);
    }

    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                main.setRefreshing(false);
                if (Contains.user == null || Contains.user.getYezhuType() == null || Contains.user.getYezhuType() != 0 || Contains.appYezhuFangwus.size() == 0) {
                    ToastUtil.show(NewMainActivity2.this, "业主信息不完善");
                    return;
                }

                Intent intent = new Intent(NewMainActivity2.this,
                        YeZhuOpenDoorActivity.class);
                ActivityOptions opts = null;
                opts = ActivityOptions.makeCustomAnimation(
                        NewMainActivity2.this, R.anim.slide_up_in, R.anim.slide_down_out);
                startActivity(intent, opts.toBundle());
            }
        }, 500);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.wuyeWarp:
                if (Contains.user == null || Contains.user.getYezhuType() == null || Contains.user.getYezhuType() != 0 || Contains.appYezhuFangwus.size() == 0) {
                    ToastUtil.show(NewMainActivity2.this, "业主信息不完善");
                    return;
                }
                startActivity(WuyeActivity.class);

                break;
            case R.id.serviceWarp:
                bundle.putInt("tag", 0);
                startActivity(ServiceMainActivity.class, bundle);
                break;
            case R.id.goMall:
                if(Contains.curSelectXiaoQuId == 0 || Contains.curSelectXiaoQuName == null || "".equals(Contains.curSelectXiaoQuName) ){
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("游客身份").setContentText("请手动选择小区").setConfirmText("确认").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            startActivity(SelectPlaceActivity.class);
                            sDialog.dismissWithAnimation();
                        }
                    }).show();
                    return;
                }
                startActivity(MallMainActivity.class);
                break;
            case R.id.marqueeTv:
                if (marqueeTv == null || marqueeTv.getText() == null
                        || "".equals(marqueeTv.getText().toString())
                        || "没有通知活动".equals(marqueeTv.getText().toString())) {
                    ToastUtil.show(NewMainActivity2.this, "没有通知活动");
                    return;
                }
            case R.id.mine: //右上角按钮
                if (Contains.curSelectXiaoQuName != null && !"".equals(Contains.curSelectXiaoQuName)
                        && Contains.curSelectXiaoQuId != 0) {
                    Contains.badgeImageView=0;
                    int  xiaoqu =Contains.curSelectXiaoQuId;
                    Intent tz = new Intent();
                    tz.setClass(NewMainActivity2.this, // context
                            WebViewActivity.class);// 跳转的activity
                    Bundle tz1 = new Bundle();

                    tz1.putString("name", "通知活动");
                    tz1.putString("address", API.IP_PRODUCT + "/MyJsp.jsp?luopan=" + xiaoqu);
                    tz.putExtras(tz1);
                    startActivity(tz, tz1);
                }else {
                    Toast.makeText(this, "您没有选择小区", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.curPlace:
                //0为业主 1为游客 游客可以选择小区
                if(Contains.user.getYezhuType() == 1){
                    startActivity(SelectPlaceActivity.class);
                }else{
                    ToastUtil.show(NewMainActivity2.this,"您已预留小区信息");
                }
                break;
            case R.id.secondaryActions:
                jumpSecondView();
                break;
        }
    }

    /**
     * 跳转二级页面
     */
    private void jumpSecondView() {
    }

    /**
     * 启动Activity
     *
     * @param clazz
     */
    protected <T> void startActivity(Class<T> clazz) {
        Intent intent = new Intent(this, clazz);
        try {
            startActivity(intent);
        } catch (Exception e) {
            ToastUtil.show(this, "敬请期待！");
        }
    }

    @Override
    protected void initDataFromNet() {
    }

    /**
     * 请求通知接口
     */
    private void initTongzhi() {
        if (tongzhiController == null) {
            tongzhiController = new TongzhiControllerImpl();
        }

        Map<String, String> params = new HashMap<String, String>();

        if (Contains.curSelectXiaoQuName != null && !"".equals(Contains.curSelectXiaoQuName)
                && Contains.curSelectXiaoQuId != 0) {
            params.put("luopan", Contains.curSelectXiaoQuId + "");
        } else {
            params.put("luopan", "");
        }
        Log.d("geek","获取App通知参数params="+params.toString());
        tongzhiController.getAppTongzhiInfo(mRequestQueue, params, tongzhiLinstener);
    }

    private ResultListener<BaseEntity> tongzhiLinstener = new ResultListener<BaseEntity>() {
        @Override
        public void onResponse(BaseEntity info) {
            Log.d("geek", "门禁 info=" + info.toString());
            String tongzhi = info.MSG;
            marqueeTv.setText(tongzhi);
            progressDialog.hide();
        }

        @Override
        public void onErrorResponse(String errMsg) {
            onError(errMsg);
            progressDialog.hide();
        }
    };

    /**
     * 请求轮播图接口
     */
    private void getlunbotubiao() {
        if (PeiZhiController == null) {
            PeiZhiController = new PeiZhiControllerImpl();
        }

        PeiZhiController.getAllMainLbList(mRequestQueue, new Object[]{}, new ResultListener<CxwyMallPezhi>() {
            @Override
            public void onResponse(CxwyMallPezhi info) {
                if (info.status != STATUS_CODE_OK) {
                    onError(info.MSG);
                    return;
                }

                if (!isEmptyList(info.getLblist())) {
                    urls.clear();
                    for (int i = 0; i < info.getLblist().size(); i++) {
                        urls.add(API.PIC + info.getLblist().get(i).getMallPeizhiValue());
                    }

                    Log.d("geek", "首页轮播图路径urls=" + urls.toString());
                    imageCycleView.setImageResources(urls,
                            new ImageCycleView.ImageCycleViewListener() {
                                @Override
                                public void onImageClick(int position, View imageView) {
                                    if (position == 0) {  //手机开门
                                        if (Contains.user == null || Contains.user.getYezhuType() == null || Contains.user.getYezhuType() != 0 || Contains.appYezhuFangwus.size() == 0) {
                                            ToastUtil.show(NewMainActivity2.this, "业主信息不完善");
                                            return;
                                        }
                                        startActivity(AccessActivity.class);
                                    } else if (position == 1) {  //物业缴费
                                        if (Contains.appYezhuFangwus == null || Contains.appYezhuFangwus.size() == 0) {
                                            ToastUtil.show(NewMainActivity2.this, "请配置房屋信息再进行查询");
                                            return;
                                        }
                                        startActivity(FeiYongListActivity.class);
                                    } else if (position == 2) {  //居家安防
                                        ToastUtil.show(NewMainActivity2.this, "即将上线");
                                    } else if (position == 3) {  //便利商店
                                        if(Contains.curSelectXiaoQuId == 0 || Contains.curSelectXiaoQuName == null || "".equals(Contains.curSelectXiaoQuName) ){
                                            new SweetAlertDialog(NewMainActivity2.this, SweetAlertDialog.WARNING_TYPE).setTitleText("游客身份").setContentText("请手动选择小区").setConfirmText("确认").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    startActivity(SelectPlaceActivity.class);
                                                    sDialog.dismissWithAnimation();
                                                }
                                            }).show();
                                            return;
                                        }
                                        startActivity(MallMainActivity.class);
                                    }
                                }
                            }, 0);
                }
            }

            @Override
            public void onErrorResponse(String errMsg) {
                onError("暂未获取到最新图片");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("geek", "首页destory()");
        AppConfig.setMainActivity(null);
        destroyLocation();
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if((System.currentTimeMillis()-exitTime) > 2000){
//                Toast.makeText(getApplicationContext(), "再按一次在后台运行", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                Intent home = new Intent(Intent.ACTION_MAIN);
//                home.addCategory(Intent.CATEGORY_HOME);
//                startActivity(home);
////                finish();
////                System.exit(0);
//            }
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void dingwei() {
        super.initDataFromNet();
        if (repairController == null) {
            repairController = new ReparirControllerImpl();
        }
        repairController.getProject(mRequestQueue, listener);
    }

    private ResultListener<RepairList> listener = new ResultListener<RepairList>() {

        @Override
        public void onResponse(RepairList info) {
            // 获取请求
            if (info.status != STATUS_CODE_OK) {
                onError(info.MSG);
                return;
            }
            if (isEmptyList(info.getRows())) {
                onError("获取失败");
            }else {
                double latitude1;
                double longitude1;
                double sum;
                double[] str = new double[info.getRows().size()];
                for(int i=0;i<info.getRows().size();i++){
                    //纬度  latitude
                    String latitude =info.getRows().get(i).getXiangmuLatitude();
                    //纬度  longitude
                    String longitude=info.getRows().get(i).getXiangmuLongitude();
                    try {
                        latitude1 = Double.valueOf(latitude);
                        longitude1 = Double.valueOf(longitude);
                    } catch (NumberFormatException e) {
                        latitude1 = 0;

                        longitude1=0;
                    }
                    sum=  getDistance(Contains.longitude,Contains.Latitude,longitude1,latitude1);
                    Log.d("geek","sum ="+sum);
                    str[i]=sum;
                }

                double min=str[0];
                int n =0 ;
                for (int i=0;i<str.length;i++)
                {
                    if(str[i]<min)   // 判断最小值
                    { min=str[i];
                        n =i;
                    }
                }
                Log.d("geek", "数组的最小值是："+min+".数组的位置是："+(n));
                Contains.curSelectXiaoQuName=info.getRows().get(n).getXiangmuLoupan();
                Contains.curSelectXiaoQuId=info.getRows().get(n).getXiangmuId();
                curPlace.setText(Contains.curSelectXiaoQuName);
                stopLocation();
                destroyLocation();
            }
        }

        @Override
        public void onErrorResponse(String errMsg) {
            onError("请求失败");
        }
    };

    // 返回单位是米
    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    @Override
    protected void initDataFromLocal() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Contains.curSelectXiaoQuName == null || "".equals(Contains.curSelectXiaoQuName)) {
            curPlace.setText("定位失败,请手动选择小区");
            if(Contains.appYezhuFangwus !=null && Contains.appYezhuFangwus.size() > 0){
                curPlace.setText( Contains.appYezhuFangwus.get(0).getXiangmuLoupan());
            }
        } else {
            curPlace.setText(Contains.curSelectXiaoQuName);
        }
        refreshLogInfo();
    }

    /**
     * 初始化定位
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
                String result = Utils.getLocationStr(loc);
//                Logger.d(result);
            } else {
                Log.d("...", "定位失败");
            }
        }
    };

    /**
     * 开始定位
     */
    private void startLocation(){
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
        dingwei();
    }

    /**
     * 停止定位
     */
    private void stopLocation(){
        // 停止定位
        if (null != locationClient) {
           locationClient.stopLocation();
        }
    }


    /**
     * 销毁定位
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.stopLocation();
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }


    @Override
    public void refreshLogInfo() {
        super.refreshLogInfo();
        if (Contains.badgeImageView==1){
            mine.setBadgeCount(0);
            mine.setBadgeShown(true);
        }else {
            mine.setBadgeShown(false);
        }
        Log.d("...", Contains.badgeImageView+"...................");
    }
}