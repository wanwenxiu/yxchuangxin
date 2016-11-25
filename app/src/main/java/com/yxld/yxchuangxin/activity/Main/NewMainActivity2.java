package com.yxld.yxchuangxin.activity.Main;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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

import com.sunfusheng.marqueeview.MarqueeView;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.index.ExpressActivity;
import com.yxld.yxchuangxin.activity.index.VisitorInvitationActivity;
import com.yxld.yxchuangxin.activity.index.YeZhuOpenDoorActivity;
import com.yxld.yxchuangxin.activity.login.LoginActivity;
import com.yxld.yxchuangxin.activity.login.WelcomeActivity;
import com.yxld.yxchuangxin.base.AppConfig;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.AppVersionController;
import com.yxld.yxchuangxin.controller.PeiZhiController;
import com.yxld.yxchuangxin.controller.TongzhiController;
import com.yxld.yxchuangxin.controller.impl.AppVersionControllerImpl;
import com.yxld.yxchuangxin.controller.impl.PeiZhiControllerImpl;
import com.yxld.yxchuangxin.controller.impl.TongzhiControllerImpl;
import com.yxld.yxchuangxin.db.DBUtil;
import com.yxld.yxchuangxin.entity.CxwyAppVersion;
import com.yxld.yxchuangxin.entity.CxwyMallPezhi;
import com.yxld.yxchuangxin.entity.CxwyMallUser;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.CxUtil;
import com.yxld.yxchuangxin.util.SPUtils;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.util.UpdateManager;
import com.yxld.yxchuangxin.view.ImageCycleView;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

//import com.sunfusheng.marqueeview.MarqueeView;

/**
 * @author wwx
 * @ClassName: NewMainActivity
 * @Description:新首页
 * @date 2016年5月4日 下午5:39:42
 */
public class NewMainActivity2 extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ImageCycleView imageCycleView;

    private LinearLayout buttomwarp;

    private ImageView img3, img1, img2,mine;

    private LinearLayout wuyeWarp, serviceWarp, goMall;

    private SwipeRefreshLayout main;

    private TextView  curPlace,marqueeTv;

    private MarqueeView secondaryActions,secondaryActionsDestail;

    private AppVersionController versionController;
    private PeiZhiController PeiZhiController;
    private TongzhiController tongzhiController;

    private ArrayList<String> urls = new ArrayList<>();

    private CxwyAppVersion entity;

    /** 测试门禁*/
    private  LinearLayout menjin;

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

    @Override
    protected void initDataFromLocal() {
        if (StringUitl.isNoEmpty(Contains.AoiName)) {
            curPlace.setText(Contains.AoiName);
            Contains.curSelectXiaoQu = Contains.AoiName;
        } else {
            if (StringUitl.isNoEmpty(Contains.locationCity)) {
                curPlace.setText(Contains.locationCity);
                Contains.curSelectXiaoQu = Contains.locationCity;
            } else {
                curPlace.setText("定位失败,请手动选择小区");
                if(Contains.appYezhuFangwus !=null && Contains.appYezhuFangwus.get(0) != null){
                    curPlace.setText( Contains.appYezhuFangwus.get(0).getXiangmuLoupan());
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Contains.curSelectXiaoQu == null || "".equals(Contains.curSelectXiaoQu)) {
            curPlace.setText("定位失败,请手动选择小区");
            if(Contains.appYezhuFangwus !=null && Contains.appYezhuFangwus.get(0) != null){
                curPlace.setText( Contains.appYezhuFangwus.get(0).getXiangmuLoupan());
            }
        } else {
            curPlace.setText(Contains.curSelectXiaoQu);
        }
        refreshLogInfo();
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.new_main_activity_layouts);
        AppConfig.setMainActivity(this);
        if (Contains.user == null || Contains.user.getYezhuShouji() == null) {
            finish();
            AppConfig.getInstance().exit();
            startActivity(WelcomeActivity.class);
            return;
        }
        String alias=Contains.user.getYezhuShouji().toString();
        String account=Contains.user.getYezhuShouji().toString();
        MiPushClient.setAlias(NewMainActivity2.this, account, null);
        MiPushClient.setUserAccount(NewMainActivity2.this,alias, null);

        initView();
        setToorBar(false);
        getlunbotubiao();
        initDataFromNet();
    }

    @Override
    protected void initView() {
        buttomwarp = (LinearLayout) findViewById(R.id.buttomwarp);
        main = (SwipeRefreshLayout) findViewById(R.id.main);
        mine = (ImageView) findViewById(R.id.mine);
        mine.setOnClickListener(this);
        curPlace = (TextView) findViewById(R.id.curPlace);
        if(Contains.appYezhuFangwus !=null && Contains.appYezhuFangwus.get(0) != null){
            curPlace.setText( Contains.appYezhuFangwus.get(0).getXiangmuLoupan());
        }
        curPlace.setOnClickListener(this);

        secondaryActions = (MarqueeView) findViewById(R.id.secondaryActions);
        List<String> info = new ArrayList<>();
        info.add("我的物业 >>");
        info.add("专享服务>>");
        info.add("邮包查寄 >>");
        info.add("个人中心 >>");
        info.add("投诉建议 >>");
        secondaryActions.startWithList(info);

        secondaryActionsDestail = (MarqueeView) findViewById(R.id.secondaryActionsDestail);
        List<String> info1 = new ArrayList<>();
        info1.add("包含车辆识别、居家安防、放心出入、授权放行栏目");
        info1.add("您的专属维修专家，解决日常报修烦恼。处理过程实时跟踪，报修结果及时反馈");
        info1.add("邮包信息我来查，精确及时到您家。快递寄件请找我，各大物流随您挑");
        info1.add("包含房屋信息、入住成员管理、房屋出租、版本更新、关于我们栏目");
        info1.add("您的困惑，督促我们日常工作的完善。您的建议，引导我们服务品质的提升");
        secondaryActionsDestail.startWithList(info1);
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

        imageCycleView = (ImageCycleView) findViewById(R.id.indexAdvs);
    }

    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                main.setRefreshing(false);
                if(Contains.user == null || Contains.appYezhuFangwus.size() ==0){
                    ToastUtil.show(NewMainActivity2.this,"业主信息不完善");
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
                startActivity(WuyeActivity.class);
                break;
            case R.id.serviceWarp:
                bundle.putInt("tag", 0);
                startActivity(ServiceMainActivity.class, bundle);
                break;
            case R.id.goMall:
                startActivity(MallMainActivity.class);
                break;
            case R.id.marqueeTv:
                if(marqueeTv == null || marqueeTv.getText() == null
                        || "".equals(marqueeTv.getText().toString())
                        || "没有通知活动".equals(marqueeTv.getText().toString())){
                    ToastUtil.show(NewMainActivity2.this,"没有通知活动");
                    return;
                }
            case R.id.mine: //右上角按钮
                String xiaoqu = "";
                if(Contains.appYezhuFangwus !=null && Contains.appYezhuFangwus.get(0) != null){
                    xiaoqu= Contains.appYezhuFangwus.get(0).getXiangmuLoupan();
                }
                Intent tz = new Intent();
                tz.setClass(NewMainActivity2.this, // context
                        WebViewActivity.class);// 跳转的activity
                Bundle tz1 = new Bundle();
                tz1.putString("name", "通知活动");
                tz1.putString("address",API.IP_PRODUCT+"/MyJsp.jsp?luopan="+xiaoqu);
                tz.putExtras(tz1);
                startActivity(tz,tz1);
                break;
            case R.id.curPlace:
                startActivity(SelectPlaceActivity.class);
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
        //获取版本信息
        if (versionController == null) {
            versionController = new AppVersionControllerImpl();
        }
        versionController.getAppVersionInfo(mRequestQueue, new Object[]{}, versionListener);

        if(tongzhiController == null){
            tongzhiController = new TongzhiControllerImpl();
        }

        Map<String, String> params = new HashMap<String, String>();

        if(Contains.appYezhuFangwus !=null && Contains.appYezhuFangwus.get(0) != null){
            params.put("luopan", Contains.appYezhuFangwus.get(0).getFwLoupanId()+"");
        }else{
            params.put("luopan", "");
        }
        tongzhiController.getAppTongzhiInfo(mRequestQueue,params,tongzhiLinstener);
    }

    private ResultListener<CxwyAppVersion> versionListener = new ResultListener<CxwyAppVersion>() {

        @Override
        public void onResponse(CxwyAppVersion info) {
            progressDialog.hide();
            if (info.status != STATUS_CODE_OK) {
                onError(info.MSG);
                return;
            }
            if (info.getVer() != null) {
                entity = info.getVer();
                Log.d("geek", " 版本entity=" + entity.toString());
                String curVersion = CxUtil.getVersion(NewMainActivity2.this);
                String newVersion = entity.getVersionUId();
                Log.d("geek", "curVersion=" + curVersion + ",newVersion=" + newVersion);
                if (Float.valueOf(newVersion) > Float.valueOf(curVersion)) {
                    checkPermission(REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
        }

        @Override
        public void onErrorResponse(String errMsg) {
            onError(errMsg);
        }

    };

    private ResultListener<BaseEntity> tongzhiLinstener = new ResultListener<BaseEntity>() {
        @Override
        public void onResponse(BaseEntity info) {
            Log.d("geek","门禁 info="+info.toString());
            String tongzhi =  info.MSG;
            marqueeTv.setText(tongzhi);
        }

        @Override
        public void onErrorResponse(String errMsg)
        {
            onError(errMsg);
        }
    };

    /**
     * 调用版本更新方法
     */
    private void alertUpdate() {
        // 这里来检测版本是否需要更新
        UpdateManager mUpdateManager = new UpdateManager(NewMainActivity2.this, API.PIC + entity.getVersionDownloadUrl());
        mUpdateManager.checkUpdateInfo(entity.getVersionUId(), entity.getVersionExplain(), entity.getVersionIsCompulsory());
    }

    /**
     * 请求权限
     *
     * @param id         请求授权的id 唯一标识即可
     * @param permission 请求的权限
     */
    protected void checkPermission(int id, String permission) {
        // 版本判断
        if (Build.VERSION.SDK_INT >= 23) {
            // 减少是否拥有权限
            int checkPermissionResult = getApplication().checkSelfPermission(
                    permission);
            if (checkPermissionResult != PackageManager.PERMISSION_GRANTED) {
                // 弹出对话框接收权限
                requestPermissions(new String[]{permission}, id);
                return;
            } else {
                // 获取到权限
                alertUpdate();
            }
        } else {
            // 获取到权限
            alertUpdate();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 获取到权限
                alertUpdate();
            } else {
                // 没有获取到权限
                Toast.makeText(NewMainActivity2.this, "没有获取到自动更新权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getlunbotubiao(){
        if(PeiZhiController == null){
            PeiZhiController = new PeiZhiControllerImpl();
        }

        PeiZhiController.getAllMainLbList(mRequestQueue, new Object[]{}, new ResultListener<CxwyMallPezhi>() {
            @Override
            public void onResponse(CxwyMallPezhi info) {
                if (info.status != STATUS_CODE_OK) {
                    onError(info.MSG);
                    return;
                }

                if(!isEmptyList(info.getLblist())) {
                    urls.clear();
                    for (int i = 0; i < info.getLblist().size(); i++) {
                        urls.add(API.PIC+info.getLblist().get(i).getMallPeizhiValue());
                    }

                    Log.d("geek","首页轮播图路径urls="+urls.toString());
                    imageCycleView.setImageResources(urls,
                            new ImageCycleView.ImageCycleViewListener() {
                                @Override
                                public void onImageClick(int position, View imageView) {
                                    if (position == 0) {
                                        if(Contains.user == null || Contains.appYezhuFangwus.size() ==0){
                                            ToastUtil.show(NewMainActivity2.this,"业主信息不完善");
                                            return;
                                        }
                                        startActivity(YeZhuOpenDoorActivity.class);
                                    } else if (position == 1) {
                                        if(Contains.user == null || Contains.appYezhuFangwus.size() ==0){
                                            ToastUtil.show(NewMainActivity2.this,"业主信息不完善");
                                            return;
                                        }
                                        startActivity(VisitorInvitationActivity.class);
                                    } else if (position == 2){
                                        startActivity(ExpressActivity.class);
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
        Log.d("geek","首页destory()");
        AppConfig.setMainActivity(null);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
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

}