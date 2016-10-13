package com.yxld.yxchuangxin.activity.Main;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.index.ExpressActivity;
import com.yxld.yxchuangxin.activity.index.VisitorInvitationActivity;
import com.yxld.yxchuangxin.activity.index.phoneOpenDoorActivity;
import com.yxld.yxchuangxin.activity.mine.AboutUsActivity;
import com.yxld.yxchuangxin.activity.mine.MemberActivity;
import com.yxld.yxchuangxin.activity.mine.MineVisionUpdateMainActivity;
import com.yxld.yxchuangxin.base.AppConfig;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.AppVersionController;
import com.yxld.yxchuangxin.controller.PeiZhiController;
import com.yxld.yxchuangxin.controller.impl.AppVersionControllerImpl;
import com.yxld.yxchuangxin.controller.impl.PeiZhiControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyAppVersion;
import com.yxld.yxchuangxin.entity.CxwyMallPezhi;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.CxUtil;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.util.UpdateManager;
import com.yxld.yxchuangxin.view.ImageCycleView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wwx
 * @ClassName: NewMainActivity
 * @Description:新首页
 * @date 2016年5月4日 下午5:39:42
 */
public class NewMainActivity2 extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ImageCycleView indexAdvs;

    private LinearLayout buttomwarp;

    private ImageView img3, img1, img2;

    private LinearLayout wuyeWarp, serviceWarp, goMall;

    private SwipeRefreshLayout main;

    private TextView mine, curPlace, secondaryActions, secondaryActionsDestail;

    private AppVersionController versionController;
    private PeiZhiController PeiZhiController;

    private  ArrayList<String> urls = new ArrayList<>();

    private CxwyAppVersion entity;
    /**
     * 动态获取定位权限
     */
    public final static int REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE = 124;

    private String[] secondaryActionstv = {"我的物业 >>", "费用缴纳 >>", "放心出入 >>", "邮包查询 >>", "报修 >>", "投诉建议 >>", "社区 >>"};

    private String[] secondaryActionstvDestail = {"我的物业详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述",
            "费用缴纳详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述",
            "放心出入详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述",
            "邮包查询描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述",
            "报修详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述",
            "投诉建议详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述",
            "社区详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述详情描述"};

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

    @Override
    protected void initView() {
        buttomwarp = (LinearLayout) findViewById(R.id.buttomwarp);
        main = (SwipeRefreshLayout) findViewById(R.id.main);
        mine = (TextView) findViewById(R.id.mine);
        mine.setOnClickListener(this);
        curPlace = (TextView) findViewById(R.id.curPlace);
        curPlace.setText(Contains.cxwyMallUser.getUserSpare1()+"");
        curPlace.setOnClickListener(this);
        secondaryActions = (TextView) findViewById(R.id.secondaryActions);
        secondaryActions.setOnClickListener(this);

        secondaryActions.setText(secondaryActionstv[0]);

        secondaryActionsDestail = (TextView) findViewById(R.id.secondaryActionsDestail);
        secondaryActionsDestail.setText(secondaryActionstvDestail[0]);

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
        indexAdvs = (ImageCycleView) findViewById(R.id.indexAdvs);

        mTask = new MyTask();
        mTask.execute();
    }

    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { main.setRefreshing(false);

                Intent intent = new Intent(NewMainActivity2.this,
                        phoneOpenDoorActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("name", "1");
                intent.putExtras(bundle1);
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
            case R.id.mine:
                PopupMenu popup = new PopupMenu(NewMainActivity2.this, mine);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menumine:
                                Log.d("geek", "个人中心");
                                startActivity(MemberActivity.class);
                                break;
                            case R.id.menumsg:
                                ToastUtil.show(NewMainActivity2.this, "通知敬请期待");
                                break;
                            case R.id.menuact:
                                ToastUtil.show(NewMainActivity2.this, "活动敬请期待");
                                break;
                            case R.id.menuversion:
                                startActivity(MineVisionUpdateMainActivity.class);
                                break;
                            case R.id.aboutus:
                                startActivity(AboutUsActivity.class);
//                               if(dbUtil == null){
//                                   dbUtil = new DBUtil(NewMainActivity2.this);
//                               }
//                               dbUtil.clearData(CxwyMallUser.class);
//                               Contains.cxwyMallUser = null;
//                               //保存用户ID和账号至配置文件中
//                               SPUtils.put(NewMainActivity2.this, CB_SAVE_PWD, false);
//                               SPUtils.put(NewMainActivity2.this, LAST_LOGIN_USER_ID, "");
//                               AppConfig.exit();
//                               startActivity(LoginActivity.class);
//                               Log.d("geek","退出登录");
                                break;
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
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
        Bundle bundle = new Bundle();
        Log.d("geek", "count=" + count);
        switch (count) {
            case 0: //我的物业
                bundle.putInt("tag", 0);
                startActivity(WuYeMainActivity.class, bundle);
                break;
            case 1: //费用缴纳
                bundle.putInt("tag", 1);
                startActivity(WuYeMainActivity.class, bundle);
                break;
            case 2: //安全出入
                bundle.putInt("tag", 2);
                startActivity(WuYeMainActivity.class, bundle);
                break;
            case 3: //报修邮包查询
                // bundle.putInt("tag",3);
                startActivity(ExpressActivity.class, bundle);
                break;
            case 4: //报修
                bundle.putInt("tag", 4);
                startActivity(WuYeMainActivity.class, bundle);
                break;
            case 5: //投诉建议
                bundle.putInt("tag", 5);
                startActivity(WuYeMainActivity.class, bundle);
                break;
            case 6: //社区
                bundle.putInt("tag", 6);
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

    private  class MyTask extends AsyncTask<Object, Integer, Double> {
       // private boolean mRun = true;

        @Override
        protected Double doInBackground(Object... params) {

            //一秒更新一次
           // while (mRun) {
                try {
                    //Log.d("geek","首页mRun ="+mRun);
                    Thread.sleep(10000);
                    publishProgress(++count);
                    if (count == 6) {
                        count = -1;
                    }
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
            Log.d("geek","首页index ="+index);
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
                    indexAdvs.setImageResources(urls,
                            new ImageCycleView.ImageCycleViewListener() {
                                @Override
                                public void onImageClick(int position, View imageView) {
                                    if (position == 0) {
                                        Intent intent = new Intent(NewMainActivity2.this,
                                                phoneOpenDoorActivity.class);
                                        Bundle bundle1 = new Bundle();
                                        bundle1.putString("name", "1");
                                        intent.putExtras(bundle1);
                                        startActivity(intent, bundle1);
                                    } else if (position == 1) {
                                        startActivity(VisitorInvitationActivity.class);
                                    } else {
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