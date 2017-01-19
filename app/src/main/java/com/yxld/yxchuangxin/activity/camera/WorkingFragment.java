package com.yxld.yxchuangxin.activity.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.p2p.core.P2PHandler;
import com.yxld.yxchuangxin.R;

/**
 * 作者：yishangfei on 2017/1/12 0012 14:29
 * 邮箱：yishangfei@foxmail.com
 */
public class WorkingFragment extends Fragment implements View.OnClickListener{
    private TextView bufang,yuyin,baojing,zhuatu;
    private CameraActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_working,null);
        mainActivity = (CameraActivity) getActivity();
        bufang= (TextView) view.findViewById(R.id.bufang);
        yuyin= (TextView) view.findViewById(R.id.yuyin);
        baojing= (TextView) view.findViewById(R.id.baojing);
        zhuatu= (TextView) view.findViewById(R.id.zhuatu);
        bufang.setOnClickListener(this);
        yuyin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mainActivity.setMute(false);
                        return true;
                    case MotionEvent.ACTION_UP:
                        mainActivity.setMute(true);
                        return true;
                }
                return false;
            }
        });
        baojing.setOnClickListener(this);
        zhuatu.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bufang:
                if (bufang.getText().equals("布防")){
                    P2PHandler.getInstance().setRemoteDefence("5969657","123",1);
                    P2PHandler.getInstance().setBuzzer("5969657","123",1);
                    P2PHandler.getInstance().setMotion("5969657","123",1);
                    bufang.setText("撤防");
                    Toast.makeText(mainActivity, "布防成功", Toast.LENGTH_SHORT).show();
                }else {
                    P2PHandler.getInstance().setRemoteDefence("5969657","123",0);
                    P2PHandler.getInstance().setBuzzer("5969657","123",0);
                    P2PHandler.getInstance().setMotion("5969657","123",0);
                    bufang.setText("布防");
                    Toast.makeText(mainActivity, "撤防成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.baojing:

                Toast.makeText(getActivity(), "报警", Toast.LENGTH_SHORT).show();
                break;
            case R.id.zhuatu:
                mainActivity.captureScreen(-1);
                break;
        }
    }
}
