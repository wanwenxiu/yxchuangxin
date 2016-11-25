package com.yxld.yxchuangxin.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.Main.WebViewActivity;
import com.yxld.yxchuangxin.activity.index.AccessActivity;
import com.yxld.yxchuangxin.activity.index.ChengyuanguanliActivity;
import com.yxld.yxchuangxin.activity.index.ExpressActivity;
import com.yxld.yxchuangxin.activity.index.FeiYongListActivity;
import com.yxld.yxchuangxin.activity.index.selectimg.Repair;
import com.yxld.yxchuangxin.activity.mine.AboutUsActivity;
import com.yxld.yxchuangxin.activity.mine.EmployerActivity;
import com.yxld.yxchuangxin.activity.mine.MemberActivity;
import com.yxld.yxchuangxin.activity.mine.MineVisionUpdateMainActivity;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.util.ToastUtil;
import com.yxld.yxchuangxin.view.MyGridView;

/**
 * Created by yishangfei on 2016/11/2 0002.
 */
public class Wuyeadapter extends BaseAdapter {
    private String[] title;
    private Context context;

    int[] icon = {R.mipmap.menjin, R.mipmap.cheliang, R.mipmap.anfangzaijia, R.mipmap.zufang};
    String[] name = {"门禁管理", "车辆管理", "居家安防", "房屋出租"};

    int[] icon1 = {R.mipmap.wuyefei, R.mipmap.shuifei, R.mipmap.dianfei, R.mipmap.tingchechang};
    String[] name1 = {"物业服务费", "水费", "电费", "车位费"};

    int[] icon2 = {R.mipmap.tongzhi, R.mipmap.baoxiu, R.mipmap.shouq, R.mipmap.kuaidi,R.mipmap.tousu,R.mipmap.manyi};
    String[] name2 = {"通知公告", "维修服务","授权放行","邮包查寄","投诉建议","满意度调查"};

    int[] icon3 = {R.mipmap.fangwu, R.mipmap.ruzhu, R.mipmap.zhanghao,R.mipmap.gengxin, R.mipmap.guanyu};
    String[] name3 = {"房屋信息", "入住成员","账号管理", "版本更新", "关于我们"};


    public Wuyeadapter(String[] title, Context context) {
        this.title = title;
        this.context = context;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return title[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater lif = LayoutInflater.from(context);
            convertView = lif.inflate(R.layout.item_listview, null);
            viewHolder.mName = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.mGridView = (MyGridView) convertView.findViewById(R.id.gridview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mName.setText(title[position]);
        switch (title[position]) {
            case "我的物业":
                viewHolder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("...", "我的物业" + position);
                        switch (position) {
                            case 0://门禁管理
                                if(Contains.user == null || Contains.appYezhuFangwus.size() ==0){
                                    ToastUtil.show(context,"业主信息不完善");
                                    return;
                                }
                                startActivity(AccessActivity.class);
                                break;
                            case 1://车辆管理
                                ToastUtil.show(context, "敬请期待");
                                break;
                            case 2:
                                ToastUtil.show(context, "敬请期待");
                               // startActivity(CameraActivity.class);
                                break;
                            case 3:
                                if (Contains.user == null || Contains.appYezhuFangwus.size() == 0
                                        || Contains.cxwyMallUser.getUserIdCard() == null
                                        || Contains.cxwyMallUser.getUserTel() == null) {
                                    ToastUtil.show(context, "请至物业完善业主身份证和手机号码信息再进行查询");
                                    return;
                                }else {
                                    Intent cz = new Intent();
                                    cz.setClass(context, // context
                                            WebViewActivity.class);// 跳转的activity
                                    Bundle cz1 = new Bundle();
                                    cz1.putString("name", "房屋出租");
                                    cz1.putString("address", API.IP_PRODUCT+"/houseRent.jsp?shenfenzheng="+Contains.cxwyMallUser.getUserIdCard()+"&shouji="+Contains.cxwyMallUser.getUserTel());
                                    cz.putExtras(cz1);
                                    context.startActivity(cz);
                                }
                                break;
                        }
                    }
                });

                break;
            case "缴费服务":
                viewHolder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        Log.d("...", "缴费服务" + position);
                        switch (position) {
                            case 0:
//                                ToastUtil.show(context, "敬请期待");
                                if(Contains.user == null || Contains.appYezhuFangwus.size() ==0){
                                    ToastUtil.show(context, "请配置房屋信息再进行查询");
                                    return;
                                }
                                bundle.putString("curType", "物业服务");
                                startActivity(FeiYongListActivity.class, bundle);
                                break;
                            case 1:
                                ToastUtil.show(context, "敬请期待");
//                                if (Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0) {
//                                    ToastUtil.show(context, "请配置房屋信息再进行查询");
//                                    return;
//                                }
//                                bundle.putString("curType", "水");
//                                startActivity(FeiYongListActivity.class, bundle);
                                break;
                            case 2:
                                ToastUtil.show(context, "敬请期待");
//                                if (Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0) {
//                                    ToastUtil.show(context, "请配置房屋信息再进行查询");
//                                    return;
//                                }
//                                bundle.putString("curType", "电");
//                                startActivity(FeiYongListActivity.class, bundle);
                                break;
                            case 3:
                                ToastUtil.show(context, "敬请期待");
//                                if (Contains.cxwyYezhu == null || Contains.cxwyYezhu.size() == 0) {
//                                    ToastUtil.show(context, "请配置房屋信息再进行查询");
//                                    return;
//                                }
//                                bundle.putString("curType", "机动车停放服务");
//                                startActivity(FeiYongListActivity.class, bundle);
                                break;
                        }
                    }
                });

                break;
            case "综合服务":
                viewHolder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("...", "综合服务" + position);
                        switch (position) {
                            case 0://通知公告
                                String xiaoqu = "";
                                if(Contains.cxwyMallUser != null){
                                    xiaoqu= Contains.cxwyMallUser.getUserSpare1();
                                }
                                Intent tz = new Intent();
                                tz.setClass(context, // context
                                        WebViewActivity.class);// 跳转的activity
                                Bundle tz1 = new Bundle();
                                tz1.putString("name", "通知活动");
                                tz1.putString("address",API.IP_PRODUCT+"/MyJsp.jsp?luopan="+xiaoqu);
//                              tz1.putString("address", "http://192.168.0.114:8080/wygl/tongzhi.jsp");
                                tz.putExtras(tz1);
                                context.startActivity(tz,tz1);
                                break;
                            case 1://维修服务
//                                ToastUtil.show(context, "敬请期待");
                                if(Contains.user == null || Contains.appYezhuFangwus.size() ==0){
                                    Toast.makeText(context, "需要在后台去配置您的业主信息", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                startActivity(Repair.class);
                                break;
                            case 2:
                                ToastUtil.show(context, "敬请期待");
                                break;
                            case 3:
                                startActivity(ExpressActivity.class);
                                break;
                            case 4://投诉建议
                                if(Contains.user == null || Contains.appYezhuFangwus.size() ==0){
                                Toast.makeText(context, "需要在后台去配置您的业主信息", Toast.LENGTH_SHORT).show();
                            }else{
                                Intent ts = new Intent();
                                ts.setClass(context, // context
                                        WebViewActivity.class);// 跳转的activity
                                Bundle ts1 = new Bundle();
                                ts1.putString("name", "投诉建议");
//                                ts1.putString("address", API.IP_PRODUCT+"/tousujianyi.jsp?yezhuid="+Contains.cxwyYezhu.get(0).getYezhuId());
                                ts.putExtras(ts1);
                                context.startActivity(ts);
                            }
                                break;
                            case 5://满意度调查
                                ToastUtil.show(context, "敬请期待");
                                break;
                        }
                    }
                });

                break;

        case "个人中心":
        viewHolder.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("...", "个人中心" + position);
                switch (position) {
                    case 0:
                        if(Contains.user == null || Contains.appYezhuFangwus.size() ==0){
                            startActivity(EmployerActivity.class);
                        } else {
                            Toast.makeText(context, "业主信息尚未完善", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        if(Contains.user == null || Contains.appYezhuFangwus.size() ==0){
                            startActivity(ChengyuanguanliActivity.class);
                        } else {
                            ToastUtil.show(context, "您没有权限查看");
                        }
                        break;
                    case 2:
                        startActivity(MemberActivity.class);
                        break;
                    case 3:
                        startActivity(MineVisionUpdateMainActivity.class);
                        break;
                    case 4:
                        startActivity(AboutUsActivity.class);
                        break;
                }
            }
        });
        break;

    }


    switch(position) {
        case 0:
            Wuyeadapter1 testadapter = new Wuyeadapter1(icon, name, context);
            viewHolder.mGridView.setAdapter(testadapter);
            break;
        case 1:
            Wuyeadapter1 testadapter1 = new Wuyeadapter1(icon1, name1, context);
            viewHolder.mGridView.setAdapter(testadapter1);
            break;
        case 2:
            Wuyeadapter1 testadapter2 = new Wuyeadapter1(icon2, name2, context);
            viewHolder.mGridView.setAdapter(testadapter2);
            break;
        case 3:
            Wuyeadapter1 testadapter3 = new Wuyeadapter1(icon3, name3, context);
            viewHolder.mGridView.setAdapter(testadapter3);
            break;
    }

    return convertView;
}

class ViewHolder {
    TextView mName;
    MyGridView mGridView;
}

    /**
     * 启动Activity
     * @param <T>
     * @param clazz
     */
    protected <T> void startActivity(Class<T> clazz) {
        Intent intent = new Intent(context, clazz);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "敬请期待！"+ clazz.getSimpleName()
                    + "未注册！",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 启动Activity
     *
     * @param clazz
     */
    protected <T> void startActivity(Class<T> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(bundle);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "敬请期待！"+ clazz.getSimpleName()
                    + "未注册！",Toast.LENGTH_SHORT).show();
        }
    }

}
