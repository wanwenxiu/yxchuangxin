package com.yxld.yxchuangxin.activity.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.Main.WebViewActivity;
import com.yxld.yxchuangxin.activity.order.AddressListActivity;
import com.yxld.yxchuangxin.activity.order.CouponActivity;
import com.yxld.yxchuangxin.activity.order.OrderListActivity;
import com.yxld.yxchuangxin.base.BaseFragment;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.YeZhuController;
import com.yxld.yxchuangxin.controller.impl.YeZhuControllerImpl;
import com.yxld.yxchuangxin.entity.BaseEntity2;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("InflateParams")
/**
 * @ClassName: MallMineFragment
 * @Description: 商城个人中心
 * @author wwx
 * @date 2016年3月7日 下午3:48:32 
 */
public class MallMineFragment extends BaseFragment {

    /**
     * 全部订单
     */
    private RelativeLayout allOrderWarp;
    /**
     * 待付款
     */
    private TextView llytj;
    /**
     * 待发货
     */
    private TextView lldfh;
    /**
     * 待收货
     */
    private TextView lldsh;
    /**
     * 已收货
     */
    private TextView llysh;
    /**
     * 退货
     */
    private TextView llth;

//    /**
//     * 用户名
//     */
//    private TextView user_name;

    /**
     * 头像
     */
//    private CircularImage touxiang;
    private TextView touxiang;

    /** 余额*/
//    private TextView yue;

    private View coupon;

    private View jianyi;

    private YeZhuController yeZhuController;
    private String url = API.URL_YUE;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_mall_activity_layout, null);
        init(view);
        return view;
    }

    private void init(View view) {
        allOrderWarp = (RelativeLayout) view.findViewById(R.id.allOrderWarp);
        allOrderWarp.setOnClickListener(this);
        llytj = (TextView) view.findViewById(R.id.llytj);
        llytj.setOnClickListener(this);
        lldsh = (TextView) view.findViewById(R.id.lldsh);
        lldsh.setOnClickListener(this);
        lldfh = (TextView) view.findViewById(R.id.lldfh);
        lldfh.setOnClickListener(this);
        llysh = (TextView) view.findViewById(R.id.llysh);
        llysh.setOnClickListener(this);
        llth = (TextView) view.findViewById(R.id.llth);
        llth.setOnClickListener(this);
        touxiang = (TextView) view.findViewById(R.id.touxiang);
        touxiang.setText(Contains.user.getYezhuName()); // animate
        touxiang.setOnClickListener(this);
        coupon=view.findViewById(R.id.coupon);
        coupon.setOnClickListener(this);
        jianyi=view.findViewById(R.id.jianyi);
        jianyi.setOnClickListener(this);

        view.findViewById(R.id.address).setOnClickListener(this);

        view.findViewById(R.id.updatePwd).setOnClickListener(this);

//        view.findViewById(R.id.mineMoney).setOnClickListener(this);

        view.findViewById(R.id.mineInfo).setOnClickListener(this);

        view.findViewById(R.id.record).setOnClickListener(this);
//        yue = (TextView) view.findViewById(R.id.yue);

//        user_name = (TextView) view.findViewById(R.id.user_name);
//        user_name.setText("用户账号:" + Contains.cxwyMallUser.getUserTel());
//        if(Contains.cxwyMallUser.getUserIntegral() != null && !"".equals(Contains.cxwyMallUser.getUserIntegral())){
//            yue.setText("余额充值("+Contains.cxwyMallUser.getUserIntegral()+")元");
//        }else {
//            yue.setText("余额充值(0元)");
//        }
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        super.onClick(v);
        switch (v.getId()) {
            case R.id.touxiang:
                startActivity(MemberActivity.class);
                break;
            case R.id.allOrderWarp:
                bundle.putInt("ORDERTYPE", 0);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.llytj:
                bundle.putInt("ORDERTYPE", 1);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.lldfh:
                bundle.putInt("ORDERTYPE", 2);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.lldsh:
                bundle.putInt("ORDERTYPE", 3);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.llysh:
                bundle.putInt("ORDERTYPE", 4);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.llth:
                bundle.putInt("ORDERTYPE", 5);
                startActivity(OrderListActivity.class, bundle);
                break;
            case R.id.address:
                startActivity(AddressListActivity.class);
                break;
            case R.id.updatePwd:
                startActivity(UpdatePwd.class);
                break;
//            case R.id.mineMoney:
//                startActivity(SaveMoneyActivity.class);
//                break;
            case R.id.mineInfo:
                ToastUtil.show(getActivity(), "敬请期待");
                break;
            case R.id.coupon:
               Intent coupon=new Intent(getActivity(), CouponActivity.class);
                startActivity(coupon);
                break;
            case R.id.record:
                Intent record=new Intent(getActivity(), RecordAcitvity.class);
                startActivity(record);
                break;
            case R.id.jianyi:
                Intent jy = new Intent();
                jy.setClass(getActivity(), // context
                        WebViewActivity.class);// 跳转的activity
                Bundle jy1 = new Bundle();
                jy1.putString("name", "商城建议");
                jy1.putString("address", "http://222.240.1.133/wygl/malljianyi.jsp?malluserid="+Contains.user.getYezhuId());
                jy.putExtras(jy1);
                startActivity(jy);
                break;

            default:
                break;
        }
    }

    @Override
    protected void initDataFromLocal() {

    }

    @Override
    protected void initDataFromNet() {
        if (yeZhuController == null) {
            yeZhuController = new YeZhuControllerImpl();
        }
        Map<String, String> parms = new HashMap<String, String>();
        parms.put("id", Contains.user.getYezhuId()+"");
        yeZhuController.getAllChongzhi(mRequestQueue, url, parms, listening);
    }

    private ResultListener<BaseEntity2> listening = new ResultListener<BaseEntity2>() {

        @Override
        public void onResponse(BaseEntity2 info) {
            // 获取请求码
            Log.d("...", info.toString());
            if (info.status != STATUS_CODE_OK) {
                onError(info.MSG);
                return;
            }
            if (info.MSG !=null && !"".equals(info.MSG) ) {
                Toast.makeText(getActivity(),info.MSG,Toast.LENGTH_SHORT).show();
//                Contains.cxwyMallUser.setUserIntegral(info.curMoney);
            }
        }

        @Override
        public void onErrorResponse(String errMsg) {
            onError(errMsg);
        }
    };

//    @Override
//    public void onStart() {
//        super.onStart();
//        if(Contains.cxwyMallUser.getUserIntegral() != null && !"".equals(Contains.cxwyMallUser.getUserIntegral())){
//            yue.setText("余额充值("+Contains.cxwyMallUser.getUserIntegral()+")元");
//        }else {
//            yue.setText("余额充值(0元)");
//        }
//    }

}
