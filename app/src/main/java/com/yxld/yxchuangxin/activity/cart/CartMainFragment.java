package com.yxld.yxchuangxin.activity.cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.goods.GoodsDestailActivity;
import com.yxld.yxchuangxin.activity.mine.MemberActivity;
import com.yxld.yxchuangxin.activity.order.SureOrderActivity;
import com.yxld.yxchuangxin.adapter.CartAdapter;
import com.yxld.yxchuangxin.base.BaseEntity;
import com.yxld.yxchuangxin.base.BaseFragment;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.CartController;
import com.yxld.yxchuangxin.controller.GoodsController;
import com.yxld.yxchuangxin.controller.impl.CartControllerImpl;
import com.yxld.yxchuangxin.controller.impl.GoodsControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallCart;
import com.yxld.yxchuangxin.entity.ProductInfo;
import com.yxld.yxchuangxin.entity.SureOrderEntity;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.util.CxUtil;
import com.yxld.yxchuangxin.util.StringUitl;
import com.yxld.yxchuangxin.util.ToastUtil;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wwx
 * @ClassName: CartMainFragment
 * @Description: 购物车
 * @date 2016年3月18日 上午10:00:25
 */
@SuppressLint("HandlerLeak")
public class CartMainFragment extends BaseFragment {
    /**
     * 更新全选按钮状态- false
     */
    public static final int updateNoCheck = 0;
    /**
     * 更新全选按钮状态 - true
     */
    public static final int updateAllCheck = 1;
    /**
     * 更新价格
     */
    public static final int updateCurPrise = 2;
    /**
     * 跳转至商品详情界面
     */
    public static final int jumpGoodsDestail = 3;

    /**
     * 购物车实现类
     */
    private CartController cartController;

    // 购物车适配器类
    CartAdapter cartApater;
    // 去购物文本按钮,编辑文本按钮
    TextView cartIsNullGo;
    // 购物车的返回按钮
    ImageView cart_returns;
    // 购物车总价格 确认订单一栏,购物车登陆框,购物车为空的提示, 删除商品的面板
    RelativeLayout cart_loginLips, cartIsNullLips, cart_editWrap;
    // 购物车删除按钮
    TextView cart_delete, cart_sure, cartPriceConnt;
    // 购物车全选按钮
    CheckBox cartIsAllSelect;
    /**
     * 列表
     */
    private ListView listView;

    private View view;

    boolean isFrist = true;

    private  String queryCartInfo = "";

    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private GoodsController goodsController;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart_main_activity_layout, container,
                false);
        init(view);
        getChildrenView();
        InitializationView();
//        initDataFromNet();
        return view;
    }

    private void init(View view) {
        cartIsNullLips = (RelativeLayout) view
                .findViewById(R.id.cartIsNullLips);
        // 购物车的底部菜单
        cart_sure = (TextView) view.findViewById(R.id.cart_sure);
        cart_sure.setOnClickListener(this);
        // 删除按钮
        cart_delete = (TextView) view.findViewById(R.id.cart_delete);
        cart_delete.setOnClickListener(this);
        // 全选按钮
        cartIsAllSelect = (CheckBox) view.findViewById(R.id.cartIsAllSelect);
        // 删除商品的面板
        cart_editWrap = (RelativeLayout) view.findViewById(R.id.cart_editWrap);
        cartPriceConnt = (TextView) view.findViewById(R.id.cartPriceConnt);
        cart_returns = (ImageView) view.findViewById(R.id.cart_returns);
        listView = (ListView) view.findViewById(R.id.cartGoodList);
    }

    @Override
    public void onResume() {
        Log.d("geek","购物车 onResume（）");
        super.onResume();

//        if(!isFrist){
        initDataFromNet();
//        }
    }

    @Override
    public void firstLoading() {
        super.firstLoading();
    }

    @Override
    protected void initDataFromNet() {
        super.initDataFromNet();
        Log.d("geek","购物车 initDataFromNet（）");
        if (cartIsAllSelect != null) {
            cartIsAllSelect.setChecked(false);
            cartPriceConnt.setText("总计：￥" + "0.00元");
        }
        if (cartController == null) {
            cartController = new CartControllerImpl();
        }
        // 获取购物车里面的商品list(如果购物车不为空就加载购物车的数据)
        cartController.getCartInfoFromUserID(mRequestQueue,
                new Object[]{Contains.user.getYezhuId()}, new ResultListener<CxwyMallCart>() {

                    @Override
                    public void onResponse(CxwyMallCart info) {
                        Log.d("geek","购物车 initDataFromNet（） onResponse");
                        progressDialog.hide();
//                        if(!queryCartInfo.equals(info.toString())){
//                            queryCartInfo = info.toString();
//                        }else{
//                            return;
//                        }

                        Log.d("geek","queryCartInfo"+queryCartInfo);

                        // 把数据放进控件 更新购物车商品列表的UI
                        // 获取请求码
                        if (info.status != STATUS_CODE_OK) {
                            onError(info.MSG);
                            return;
                        }
                        if (isEmptyList(info.getCart())) {
                            Contains.CartList.clear();
                            resetView();
                            getGoodList();
                        } else {
                            Contains.CartList = info.getCart();
                            resetView();
                            getGoodList();
                        }

                    }

                    @Override
                    public void onErrorResponse(String errMsg) {
                        onError(errMsg);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        Map<String, String> map = new HashMap<String, String>();
        getCurSelectGoods(map);
        Log.d("geek", "选中条目 map" + map.toString() + "size" + map.size());

        switch (v.getId()) {
            case R.id.cart_delete:// 删除按钮点击事件
                //如果选择了商品，则进行下单或删除操作
                if (map.size() != 0) {
                    cartController.deleteInfoToCart(mRequestQueue, map,
                            new ResultListener<BaseEntity>() {

                                @Override
                                public void onResponse(BaseEntity info) {
                                    // 把数据放进控件 更新购物车商品列表的UI
                                    // 获取请求码
                                    if (info.status != STATUS_CODE_OK) {
                                        onError(info.MSG);
                                        return;
                                    }
                                    initDataFromNet();
                                }

                                @Override
                                public void onErrorResponse(String errMsg) {
                                    onError(errMsg);
                                }
                            });
                } else {
                }

                break;
            case R.id.cart_sure: //下单按钮
                //如果选择了商品，则进行下单或删除操作
                if (map.size() != 0) {
                    startActivity(SureOrderActivity.class);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 计算当前选中商品
     *
     * @param map
     */
    private void getCurSelectGoods(Map<String, String> map) {
        Contains.sureOrderList.clear();
        // 判断是否已选择商品
        int count = -1;
        for (int i = 0; i < Contains.CartList.size(); i++) {
            if (Contains.CartList.get(i).isChecked()) {
                count++;
                Log.d("geek", "选中条目" + Contains.CartList.get(i).toString());
                map.put("cartList[" + count + "].cartId", Contains.CartList
                        .get(i).getCartId() + "");

                //拼接确认订单集合
                SureOrderEntity entity = new SureOrderEntity(Contains.CartList
                        .get(i).getCartShangpNum(), Contains.CartList
                        .get(i).getCartNum(), Contains.CartList
                        .get(i).getCartId() + "", Contains.CartList
                        .get(i).getCartImgSrc(), Contains.CartList
                        .get(i).getCartOneRmb() + "",Contains.CartList
                        .get(i).getCartShangpName(), Contains.CartList
                        .get(i).getCartSpec());
                Contains.sureOrderList.add(entity);

            }
        }
    }

    // 获取购物车里面的商品list
    public void getGoodList() {
        // 判断购物车是否为空
        if (CxUtil.cartIsNull()) {
            // 如果购物车为空就隐藏加入订单的控件 和显示总价格的控件
            cartIsNullLips.setVisibility(View.VISIBLE);
            cart_editWrap.setVisibility(View.GONE);
            ((ListView) view.findViewById(R.id.cartGoodList)).setVisibility(View.GONE);
        } else {
            ((ListView) view.findViewById(R.id.cartGoodList)).setVisibility(View.VISIBLE);
            cart_editWrap.setVisibility(View.VISIBLE);
            cartIsNullLips.setVisibility(View.GONE);
        }

        // 实例化一个购物车适配器的类
        cartApater = new CartAdapter(mRequestQueue, progressDialog, getActivity(),
                Contains.CartList, mHandler);
        // 把内容装载进去
        listView.setAdapter(cartApater);
        listView.setLayoutAnimation(CxUtil.getListAnim());

//        if(isFrist){
//            isFrist = false;
//        }
    }

    // 初始化窗体
    public void InitializationView() {
        // 判断用户是否已经登陆
        if (CxUtil.isLoginOk()) {
            // 如果用户已经登陆 就隐藏登陆框
            cart_loginLips = (RelativeLayout) view
                    .findViewById(R.id.cart_loginLips);
            cart_loginLips.setVisibility(View.GONE);
            cartIsNullLips.setPadding(0, 80, 0, 0);
        }
    }

    // 加载子控件
    public void getChildrenView() {
        // 给登陆按钮注册点击事件
        ((TextView) view.findViewById(R.id.cart_toLogin))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // // 跳转到登陆页面
                        // Intent intent = new Intent(CartMainFragment.this,
                        // LoginActivity.class);
                        // intent.putExtra("activityName", "cart");
                        // startActivity(intent);
                    }
                });
        // 给去购物按钮注册点击事件 点击的时候跳转到首页
        ((TextView) view.findViewById(R.id.cartIsNullGo))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Intent intent = new Intent();
                        // intent.setClass(CartMainFragment.this,
                        // MenuActivity.class);
                        // intent.putExtra("activityName", "fromCart");
                        // startActivity(intent);
                    }
                });
        // 购物车返回按钮的点击事件
        cart_returns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
            }
        });
        // 全部选中确认下单按钮 点击选中全部的商品
        cartIsAllSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = cartIsAllSelect.isChecked();
                for (int i = 0; i < Contains.CartList.size(); i++) {
                    Contains.CartList.get(i).setChecked(isChecked);
                }
                if (cartApater != null) {
                    cartApater.setListData(Contains.CartList);
                    cartApater.notifyDataSetChanged();
                }
                mHandler.sendEmptyMessage(updateCurPrise);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d("geek", "购物车 setUserVisibleHint");
            // initDataFromNet();
        }
    }

    @Override
    protected void initDataFromLocal() {

    }

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case updateNoCheck:
                    cartIsAllSelect.setChecked(false);
                    break;
                case updateAllCheck:
                    cartIsAllSelect.setChecked(true);
                    break;
                case updateCurPrise:
                    Log.d("geek", "购物车 mHandler updateCurPrise");
                    double curPrise = 0;
                    for (int i = 0; i < Contains.CartList.size(); i++) {
                        CxwyMallCart goodsVo = Contains.CartList.get(i);
                        if (goodsVo.isChecked() && StringUitl.isNoEmpty(goodsVo.getCartOneRmb())) {
                            double prise = Double.parseDouble(goodsVo.getCartOneRmb());
                            curPrise += prise
                                    * Integer.parseInt(goodsVo.getCartNum());
                        }
                    }
                    cartPriceConnt.setText("总计：￥" + decimalFormat.format(curPrise)+ "元");
                    break;
                case jumpGoodsDestail:
                    int goodsId = msg.arg1;
                    Log.d("geek", "goodsId: "+goodsId);
                    requestGoodsDestail(goodsId+"");
                   break;
                default:
                    break;
            }
        }

        ;
    };


    /**
     * 根据商品ID获取商品详情
     * @param goodId
     */
    private void  requestGoodsDestail(String goodId){
        if(goodsController == null){
            goodsController = new GoodsControllerImpl();
        }
        Log.d("geek", "获取商品goodId="+goodId);
        goodsController.getProductByGoodId(mRequestQueue,new Object[]{goodId},goodsRequest);
    }

    private ResultListener<ProductInfo>  goodsRequest = new ResultListener<ProductInfo>() {
        @Override
        public void onResponse(ProductInfo info) {
            if(info.getStatus() != 0){
                onError(info.MSG);
                return;
            }

            Log.d("geek", "获取商品info="+info.toString());
            if(info.getProduct() != null){
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods", info.getProduct());
                startActivity(GoodsDestailActivity.class, bundle);
            }
        }

        @Override
        public void onErrorResponse(String errMsg) {
            onError(errMsg);
        }
    };

}
