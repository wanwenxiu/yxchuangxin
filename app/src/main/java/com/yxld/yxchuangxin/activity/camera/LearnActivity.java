package com.yxld.yxchuangxin.activity.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.p2p.core.P2PHandler;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.adapter.LearnAdapter;
import com.yxld.yxchuangxin.base.BaseActivity;
import com.yxld.yxchuangxin.entity.Level0Item;
import com.yxld.yxchuangxin.entity.Level1Item;
import com.yxld.yxchuangxin.entity.MsgEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * 作者：yishangfei on 2017/1/20 0020 09:20
 * 邮箱：yishangfei@foxmail.com
 * 学习设备
 */
public class LearnActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ArrayList<int[]> data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EventBus.getDefault().register(this);
        P2PHandler.getInstance().getDefenceArea("5969657", "123");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDataFromLocal() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(MsgEvent message) {
        data = message.data;
        switch (message.result){
            case 0:
                Toast.makeText(this, "学习成功啦~", Toast.LENGTH_SHORT).show();
                break;
            case 24:
                Toast.makeText(this, "该通道已学", Toast.LENGTH_SHORT).show();
                break;
            case 26:
                Toast.makeText(this, "学码超时", Toast.LENGTH_SHORT).show();
                break;
            case 32:
                Toast.makeText(this, "此码已被学", Toast.LENGTH_SHORT).show();
                break;
            case 37:
                Toast.makeText(this, "无效的码值", Toast.LENGTH_SHORT).show();
                break;
        }

        Log.d("...", "helloEventBus: ");
        ArrayList<MultiItemEntity> list = generateData();
        LearnAdapter learnAdapter = new LearnAdapter(list);
        recyclerView.setAdapter(learnAdapter);
    }

    private ArrayList<MultiItemEntity> generateData() {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int[] ww = data.get(i);
            Level0Item lv0 = new Level0Item( i);
            for (int j = 0; j < ww.length; j++) {
                Level1Item lv1 = new Level1Item(ww[j],i,j);
//                Log.d("...", "第"+i+"行，第" + j + "个=" + ww[j]);
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        return res;
    }

}
