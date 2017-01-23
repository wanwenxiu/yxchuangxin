package com.p2p.core;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;

import com.p2p.core.MediaPlayer.IVideoPTS;
import com.p2p.core.global.P2PConstants;
import com.p2p.core.utils.MyUtils;

import java.io.IOException;

public abstract class BaseMonitorActivity extends BaseP2PviewActivity implements IVideoPTS {
    private final int MSG_SHOW_CAPTURERESULT = 0x00002;
    private final int MINX = 50;
    private final int MINY = 25;
    private final int USR_CMD_OPTION_PTZ_TURN_LEFT = 0;
    private final int USR_CMD_OPTION_PTZ_TURN_RIGHT = 1;
    private final int USR_CMD_OPTION_PTZ_TURN_UP = 2;
    private final int USR_CMD_OPTION_PTZ_TURN_DOWN = 3;
    public boolean isFullScreen = false;
    public boolean isLand = true;// 是否全屏
    public boolean isHalfScreen = true;

    private String TAG = "BaseMonitorActivity";

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化P2PView
     *
     * @param type       宽高类型
     * @param layoutType 布局类型默认分离使用（call与P2Pview分开）
     */
    public void initP2PView(int type, int layoutType) {
        initP2PView(type, layoutType, new GestureListener());
    }

    public void initScaleView(Activity activity, int windowWidth,
                              int windowHeight) {
        pView.setmActivity(activity);
        pView.setScreen_W(windowHeight);
        pView.setScreen_H(windowWidth);
        pView.initScaleView();
    }

    public void setMute(boolean bool) {
        try {
            MediaPlayer.getInstance()._SetMute(bool);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //可供子类使用
    public void DoubleTap(MotionEvent e) {
        //四画面才响应
        if (pView != null && pView.isPanorama() && pView.getShapeType() == P2PView.SHAPE_QUAD) {
            if (pView.getIsFourFace()) {
                pView.ZoomOutPanom(e.getX(), e.getY());
            } else {
                pView.ZoomINPanom(e.getX(), e.getY());
            }
        }
    }

    public void setIsLand(boolean isLan) {
        this.isLand = isLan;
    }

    public void setHalfScreen(boolean isHalfScreen) {
        this.isHalfScreen = isHalfScreen;
    }

    private class GestureListener extends
            GestureDetector.SimpleOnGestureListener {
        private String TAG = "GestureListener";

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            onP2PViewSingleTap();
            try {
                MediaPlayer.getInstance()._OnGesture(0, 1, 0, 0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (isLand && !isHalfScreen) {
                if (!isFullScreen) {
                    isFullScreen = true;
                    pView.fullScreen();
                } else {
                    isFullScreen = false;
                    pView.halfScreen();
                }
            }
            DoubleTap(e);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//			Log.d(TAG, "onScroll event1 = "+ e1 + "  event2 = "+ e2+  " distanceX = "+ distanceX + " distanceY = "+ distanceY+ "\n");

            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

            Log.d(TAG, "onLongPress >>>");

            try {
                MediaPlayer.getInstance()._OnGesture(3, 1, e.getX(), e.getY());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (pView != null && pView.isPanorama() && pView.getShapeType() == P2PView.SHAPE_QUAD) {
                pView.setIsFourFace(false);
            }
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            int id = -1;
            float distance = 0;
            boolean ishorizontal = false;
            if ((Math.abs(e2.getX() - e1.getX())) > (Math.abs(e2.getY()
                    - e1.getY()))) {
                ishorizontal = true;
            }

            if (ishorizontal) {
                distance = e2.getX() - e1.getX();
                if (Math.abs(distance) > MyUtils.dip2px(
                        BaseMonitorActivity.this, MINX)) {
                    if (distance > 0) {
                        id = USR_CMD_OPTION_PTZ_TURN_RIGHT;
                    } else {
                        id = USR_CMD_OPTION_PTZ_TURN_LEFT;
                    }
                }
            } else {
                distance = e2.getY() - e1.getY();
                if (Math.abs(distance) > MyUtils.dip2px(
                        BaseMonitorActivity.this, MINY)) {
                    if (distance > 0) {
                        id = USR_CMD_OPTION_PTZ_TURN_UP;
                    } else {
                        id = USR_CMD_OPTION_PTZ_TURN_DOWN;
                    }
                }
            }

            if (id != -1) {
                MediaPlayer.getInstance().native_p2p_control(id);
            } else {
                Log.e("TAG","id == -1");
            }

            try {
                MediaPlayer.getInstance()._OnGesture(2, 1, velocityX, velocityY);
                if(pView!=null&&pView.isPanorama180()){
                    if(velocityX>=0){
                        P2PHandler.getInstance().setAutoCruise(1,3);
                    }else{
                        P2PHandler.getInstance().setAutoCruise(1,2);
                    }
                    onP2PViewFilling();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    protected abstract void onP2PViewSingleTap();
    protected abstract void onP2PViewFilling();

}
