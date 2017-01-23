package com.p2p.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.p2p.core.global.P2PConstants;
import com.p2p.core.utils.MyUtils;

import java.io.IOException;

/**
 * Created by USER on 2016/12/15.
 */

public abstract class BaseP2PviewActivity extends BaseCoreActivity implements MediaPlayer.ICapture,MediaPlayer.IVideoPTS{
    public P2PView pView;
    public static int mVideoFrameRate = 15;
    private boolean isBaseRegFilter = false;
    private int PrePoint=-1;
    public boolean bFlagPanorama = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseRegFilter();
        MediaPlayer.getInstance().setCaptureListener(this);
        MediaPlayer.getInstance().setVideoPTSListener(this);
        String mac = MyUtils.getLocalMacAddress(this);
        String imei = MyUtils.getIMEI(this);
        MediaPlayer.native_init_hardMessage(mac, imei);
    }

    public void baseRegFilter(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(P2PConstants.P2P_WINDOW.Action.P2P_WINDOW_READY_TO_START);
        this.registerReceiver(baseReceiver, filter);
        isBaseRegFilter = true;
    }

    /**
     * 初始化P2PView
     * @param type 宽高类型
     * @param layoutType  布局类型默认分离使用（call与P2Pview分开）
     */
    public void initP2PView(int type,int layoutType,GestureDetector.SimpleOnGestureListener listener){
        if(pView!=null){pView.setLayoutType(layoutType);}
        pView.setCallBack();
        pView.setGestureDetector(new GestureDetector(this,listener, null, true));
        pView.setDeviceType(type);
    }

    private BroadcastReceiver baseReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context arg0, Intent intent) {
            if(intent.getAction().equals(P2PConstants.P2P_WINDOW.Action.P2P_WINDOW_READY_TO_START)){
                final MediaPlayer mPlayer = MediaPlayer.getInstance();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MediaPlayer.nativeInit(mPlayer);
                        try {
                            mPlayer.setDisplay(pView);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mPlayer.start(mVideoFrameRate);
                    }
                }).start();
            }
        }
    };

    @Override
    public void vCaptureResult(final int result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(result==1){
                    onCaptureScreenResult(true,PrePoint);
                }else {
                    onCaptureScreenResult(false,PrePoint);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBaseRegFilter) {
            this.unregisterReceiver(baseReceiver);
            isBaseRegFilter = false;
        }
    }

    /**
     * -1是普通截图，0~4是预置位截图
     * @param prePoint
     */
    public void captureScreen(int prePoint) {
        this.PrePoint=prePoint;
        onPreCapture(1,prePoint);
        try {
            MediaPlayer.getInstance()._CaptureScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置是否全景
     * @param subType
     */
    public void setPanorama(int subType) {
        boolean isparam=false;
        if(pView!=null){
            pView.setPanorama(subType);
            isparam=pView.isPanorama();
        }
        try {
            MediaPlayer.getInstance()._setPanorama(isparam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void vVideoPTS(long videoPTS) {
        if(pView!=null&&pView.isPanorama()){
            onVideoPTS(videoPTS);
        }
    }

    @Override
    public void vSendRendNotify(int MsgType, int MsgAction) {
        if(pView!=null&&pView.isPanorama()){
            pView.FilpAction(MsgType, MsgAction);
        }

    }
    protected abstract void onCaptureScreenResult(boolean isSuccess,int prePoint);
    protected abstract void onVideoPTS(long videoPTS);
    public void onPreCapture(int mark,int prepoint){

    }
}
