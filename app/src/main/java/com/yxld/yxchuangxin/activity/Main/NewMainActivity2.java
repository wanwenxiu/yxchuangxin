package com.yxld.yxchuangxin.activity.Main;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
//import com.sunfusheng.marqueeview.MarqueeView;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.index.ExpressActivity;
import com.yxld.yxchuangxin.activity.index.VisitorInvitationActivity;
import com.yxld.yxchuangxin.activity.index.YeZhuOpenDoorActivity;
import com.yxld.yxchuangxin.activity.mine.AboutUsActivity;
import com.yxld.yxchuangxin.activity.mine.MemberActivity;
import com.yxld.yxchuangxin.activity.mine.MineVisionUpdateMainActivity;
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
import com.yxld.yxchuangxin.entity.CxwyAppVersion;
import com.yxld.yxchuangxin.entity.CxwyMallPezhi;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.CxUtil;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.util.UpdateManager;
import com.yxld.yxchuangxin.view.ImageCycleView;

import android.widget.RelativeLayout.LayoutParams;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @author wwx
 * @ClassName: NewMainActivity
 * @Description:新首页
 * @date 2016年5月4日 下午5:39:42
 */
public class NewMainActivity2 extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener ,BGABanner.Adapter{

//    private BGABanner indexAdvs;

    private ImageCycleView imageCycleView;

    private LinearLayout buttomwarp;

    private ImageView img3, img1, img2,mine;

    private LinearLayout wuyeWarp, serviceWarp, goMall;

    private SwipeRefreshLayout main;

    private TextView  curPlace, secondaryActions, secondaryActionsDestail,marqueeTv;

    private AppVersionController versionController;
    private PeiZhiController PeiZhiController;
    private TongzhiController tongzhiController;

    private ArrayList<String> urls = new ArrayList<>();

    private CxwyAppVersion entity;

//    /** 跑马灯效果-ViewFinder控件 */
//    private ViewFlipper viewFlipper;
//    /** 跑马灯效果 - 设置进入动画 */
//    private TranslateAnimation inAnim;
//    /** 跑马灯效果- 设置退出动画 */
//    private TranslateAnimation outAnim;
//    /** 线性布局 - 文字的跑马灯效果 */
//    private LinearLayout ll_tv_type = null;

    /**
     * 动态获取定位权限
     */
    public final static int REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE = 124;
    private String[] secondaryActionstv = {"我的物业 >>", "专享服务>>", "邮包查寄 >>", "个人中心 >>", "投诉建议 >>"};

    private String[] secondaryActionstvDestail = {"包含车辆识别、居家安防、放心出入、授权放行栏目",
            "您的专属维修专家，解决日常报修烦恼。处理过程实时跟踪，报修结果及时反馈",
            "邮包信息我来查，精确及时到您家。快递寄件请找我，各大物流随您挑",
            "包含房屋信息、入住成员管理、房屋出租、版本更新、关于我们栏目",
            "您的困惑，督促我们日常工作的完善。您的建议，引导我们服务品质的提升"};

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

    private MyTask mTask;

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
                curPlace.setText(Contains.cxwyMallUser.getUserSpare1()+"");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Contains.curSelectXiaoQu == null || "".equals(Contains.curSelectXiaoQu)) {
            curPlace.setText("定位失败,请手动选择小区");
            curPlace.setText(Contains.cxwyMallUser.getUserSpare1()+"");
        } else {
            curPlace.setText(Contains.curSelectXiaoQu);
        }
        refreshLogInfo();
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.new_main_activity_layouts);
        AppConfig.setMainActivity(this);
        String alias=Contains.cxwyMallUser.getUserTel().toString();
        String account=Contains.cxwyMallUser.getUserTel().toString();
        MiPushClient.setAlias(NewMainActivity2.this, account, null);
        MiPushClient.setUserAccount(NewMainActivity2.this,alias, null);

        initView();
        setToorBar(false);
        getlunbotubiao();
        initDataFromNet();
    }

    /**
     * 初始化跑马灯效果
     */
    private void initFlipper() {
//        createViewFlipper();
//        addText("各位亲爱的业主，2016年10月26日19点开始停水!请大家做好停水准备！以免造成损失！");
//        addText("各位业主，请速到物业中心缴纳");
//        addText("祝亲们元旦快乐，新年心想事成!");
//        ll_tv_type.addView(viewFlipper);
//        // 切换所有的View，切换会循环进行
//        viewFlipper.startFlipping();
//        ll_tv_type.setVisibility(View.VISIBLE);
    }


    /**
     * 初始化ViewFlipper参数
     */
    @SuppressWarnings("deprecation")
    public void createViewFlipper() {
//        ViewFlipper flipper = new ViewFlipper(this);
//        android.widget.LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        flipper.setLayoutParams(lp);
//        // 设置View之间切换的时间间隔
//        flipper.setFlipInterval(5000);
//        flipper.setInAnimation(getInAnim());
//        flipper.setOutAnimation(getOutAnim());
//        viewFlipper = flipper;
//        viewFlipper.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("geek","viewFlipper onClick()");
//                Intent tz = new Intent();
//                tz.setClass(NewMainActivity2.this, // context
//                        WebViewActivity.class);// 跳转的activity
//                Bundle tz1 = new Bundle();
//                tz1.putString("name", "通知活动");
//                tz1.putString("address", "http://222.240.1.133/wygl/tongzhi.jsp");
//                tz.putExtras(tz1);
//                startActivity(tz);
//            }
//        });
    }

    /**
     * 跑马灯文字进入动画
     *
     * @return
     */
//    public Animation getInAnim() {
//        if (inAnim == null) {
//            inAnim = new TranslateAnimation(800, 0, 0, 0);
//            inAnim.setDuration(5000);
//            inAnim.setFillAfter(false);
//            inAnim.setStartOffset(0);
//        }
//        return inAnim;
//    }

    /**
     * 跑马灯文字退出动画
     *
     * @return
     */
//    public Animation getOutAnim() {
//        if (outAnim == null) {
//            outAnim = new TranslateAnimation(0, -1000, 0, 0);
//            outAnim.setDuration(3000);
//            outAnim.setFillAfter(false);
//            outAnim.setStartOffset(0);
//        }
//        return outAnim;
//    }

    /**
     * 创建跑马灯中文字消息
     * @param text
     */
    @SuppressWarnings("deprecation")
    public void addText(String text) {
//        TextView tv_add = new TextView(this);
//        tv_add.setTextColor(Color.WHITE);
//        tv_add.setText(text);
//        tv_add.setTextSize(14);
//        tv_add.setGravity(Gravity.CENTER_VERTICAL);
//        android.widget.LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        tv_add.setPadding(10, 0, 0, 0);
//        viewFlipper.addView(tv_add, lp);
    }

    @Override
    protected void initView() {
        buttomwarp = (LinearLayout) findViewById(R.id.buttomwarp);
        main = (SwipeRefreshLayout) findViewById(R.id.main);
        mine = (ImageView) findViewById(R.id.mine);
        mine.setOnClickListener(this);
        curPlace = (TextView) findViewById(R.id.curPlace);
        curPlace.setText(Contains.cxwyMallUser.getUserSpare1()+"");
        curPlace.setOnClickListener(this);
        secondaryActions = (TextView) findViewById(R.id.secondaryActions);
        secondaryActions.setOnClickListener(this);

        secondaryActions.setText(secondaryActionstv[0]);


        secondaryActionsDestail = (TextView) findViewById(R.id.secondaryActionsDestail);
        secondaryActionsDestail.setText(secondaryActionstvDestail[0]);

//        ll_tv_type = (LinearLayout) findViewById(R.id.ll_tv_type);
//        initFlipper();

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
//        indexAdvs = (BGABanner) findViewById(R.id.indexAdvs);
//        indexAdvs.setData(Arrays.asList(""),null);
//        indexAdvs.setOnItemClickListener(new BGABanner.OnItemClickListener() {
//            @Override
//            public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
//                if (position == 0) {
//                    startActivity(YeZhuOpenDoorActivity.class);
//                } else if (position == 1) {
//                    startActivity(VisitorInvitationActivity.class);
//                } else if (position == 2){
//                    startActivity(ExpressActivity.class);
//                }
//            }
//        });

        imageCycleView = (ImageCycleView) findViewById(R.id.indexAdvs);


        mTask = new MyTask();
        mTask.execute();
    }

    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { main.setRefreshing(false);

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
                bundle.putInt("tag", 0);
                startActivity(WuYeMainActivity.class, bundle);
                break;
            case R.id.serviceWarp:
                bundle.putInt("tag", 0);
                startActivity(ServiceMainActivity.class, bundle);
                break;
            case R.id.goMall:
                startActivity(MallMainActivity.class);
                break;
            case R.id.marqueeTv:
            case R.id.mine: //右上角按钮
                Intent tz = new Intent();
                tz.setClass(NewMainActivity2.this, // context
                        WebViewActivity.class);// 跳转的activity
                Bundle tz1 = new Bundle();
                tz1.putString("name", "通知活动");
                tz1.putString("address", "http://222.240.1.133/wygl/tongzhi.jsp");
                tz.putExtras(tz1);
                startActivity(tz);
                break;

//                PopupMenu popup = new PopupMenu(NewMainActivity2.this, mine);
//                //Inflating the Popup using xml file
//                popup.getMenuInflater()
//                        .inflate(R.menu.popup_menu, popup.getMenu());
//
//                //registering popup with OnMenuItemClickListener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.menumine:
//                                Log.d("geek", "个人中心");
//                                startActivity(MemberActivity.class);
//                                break;
//                            case R.id.menumsg:
//                                ToastUtil.show(NewMainActivity2.this, "通知敬请期待");
//                                break;
//                            case R.id.menuact:
//                                ToastUtil.show(NewMainActivity2.this, "活动敬请期待");
//                                break;
//                            case R.id.menuversion:
//                                startActivity(MineVisionUpdateMainActivity.class);
//                                break;
//                            case R.id.aboutus:
//                                startActivity(AboutUsActivity.class);
//                                break;
//                        }
//                        return true;
//                    }
//                });
//
//                popup.show(); //showing popup menu
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
        Bundle bundle = new Bundle();
        Log.d("geek", "jumpSecondView count=" + count);
        switch (count) {
            case 0: //我的物业
                bundle.putInt("tag", 0);
                startActivity(WuYeMainActivity.class, bundle);
                break;
            case 1: //维修服务
                bundle.putInt("tag", 1);
                startActivity(WuYeMainActivity.class, bundle);
                break;
            case 2: //邮包查寄
                startActivity(ExpressActivity.class, bundle);
                break;
            case 3: //个人中心
                bundle.putInt("tag", 3);
                startActivity(WuYeMainActivity.class, bundle);
                break;
            case 4: //投诉建议
                bundle.putInt("tag", 2);
                startActivity(WuYeMainActivity.class, bundle);
                break;

        }
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

        tongzhiController.getAppTongzhiInfo(mRequestQueue,new Object[]{},tongzhiLinstener);
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
           // marqueeTv.setText("各位亲爱的业主，2016年10月26日19点开始停水!请大家做好停水准备！以免造成损失！      各位业主，请速到物业中心缴纳!     ");
            marqueeTv.setText(tongzhi);
        }

        @Override
        public void onErrorResponse(String errMsg) {
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

    @Override
    public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
        Glide.with(NewMainActivity2.this)
                .load(model)
                .placeholder(R.drawable.holder)
                .error(R.drawable.holder)
                .into((ImageView) view);
    }

    private  class MyTask extends AsyncTask<Object, Integer, Double> {
       // private boolean mRun = true;

        @Override
        protected Double doInBackground(Object... params) {

            //一秒更新一次
           // while (mRun) {
                try {
                   // Log.d("geek","首页mRun ="+mRun);
                    Thread.sleep(3000);
                    publishProgress(++count);
                    Log.d("geek","doInBackground count ="+count);
//                  if (count == 4) {
//                        count = -1;
//                  }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
           // }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int index = values[0];
            Log.d("geek","onProgressUpdate index ="+index);
            if(secondaryActions!= null && secondaryActionstv != null && secondaryActionstv[index] != null){
                secondaryActions.setText(secondaryActionstv[index]);
                secondaryActionsDestail.setText(secondaryActionstvDestail[index]);
                if (curPlace.getText().toString().equals("") || "定位失败,请手动选择小区".equals(curPlace.getText().toString())) {
                    initDataFromLocal();
                }
            }
        }

        @Override
        protected void onPostExecute(Double result) {
            //异步任务执行结束
            Log.d("geek","异步任务执行结束");
        }
    };

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
                                        startActivity(YeZhuOpenDoorActivity.class);
                                    } else if (position == 1) {
                                        startActivity(VisitorInvitationActivity.class);
                                    } else if (position == 2){
                                        startActivity(ExpressActivity.class);
                                    }
                                }
                            }, 0);

//                    indexAdvs.setAdapter(NewMainActivity2.this);
//                    indexAdvs.setData(urls, null);
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
        secondaryActionstv = null;
        secondaryActionstvDestail = null;
        urls = null;

        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
           // mTask.mRun = false;
            mTask.cancel(true);  //  如果Task还在运行，则先取消它
            //mTask = null;
        }
        AppConfig.setMainActivity(null);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    public void refreshLogInfo() {
//        String AllLog = "";
//        for (String log : logList) {
//            AllLog = AllLog + log + "\n\n";
//        }
//        //Toast.makeText(NewMainActivity2.this,AllLog,Toast.LENGTH_SHORT).show();
//    }

}