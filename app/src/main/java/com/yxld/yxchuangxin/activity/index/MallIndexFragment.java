package com.yxld.yxchuangxin.activity.index;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yxld.yxchuangxin.R;
import com.yxld.yxchuangxin.activity.goods.GoodsDestailActivity;
import com.yxld.yxchuangxin.activity.goods.SearchActivity;
import com.yxld.yxchuangxin.activity.goods.ShopListActivity;
import com.yxld.yxchuangxin.adapter.MallIndexGoodsListAdapter;
import com.yxld.yxchuangxin.adapter.MallTubaioImageAdapter;
import com.yxld.yxchuangxin.base.BaseFragment;
import com.yxld.yxchuangxin.contain.Contains;
import com.yxld.yxchuangxin.controller.API;
import com.yxld.yxchuangxin.controller.GoodsController;
import com.yxld.yxchuangxin.controller.PeiZhiController;
import com.yxld.yxchuangxin.controller.impl.GoodsControllerImpl;
import com.yxld.yxchuangxin.controller.impl.PeiZhiControllerImpl;
import com.yxld.yxchuangxin.entity.CxwyMallPezhi;
import com.yxld.yxchuangxin.entity.CxwyMallProduct;
import com.yxld.yxchuangxin.entity.ShopList;
import com.yxld.yxchuangxin.listener.ResultListener;
import com.yxld.yxchuangxin.view.ImageCycleView;
import com.yxld.yxchuangxin.view.SlideShowView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yishangfei on 2016/3/4.
 */
public class MallIndexFragment extends BaseFragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout swipe_container;
    private ArrayList<String> urls;
    private TextView ckgd;
    private GridView mGridView1;
    public  MallIndexGoodsListAdapter indexGoodsList;
    public  TextView title;
    private ScrollView scrollView;

    private SlideShowView mall_lunbo;
    /** 搜素框*/
    private ImageView search;
    
    private GridView mallgv;

    private PeiZhiController PeiZhiController;
    private GoodsController goodsController;
    private List<CxwyMallPezhi> msctbList;

    /**菜单分类适配器*/
    private MallTubaioImageAdapter adapter;

    /**首页推荐商品*/
    private List<CxwyMallProduct> indexListData = new ArrayList<CxwyMallProduct>();

    private final String save_mall_imgurl = "save_mall_imgurl";
    private SharedPreferences sp;
    private  Gson gson = new Gson();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(urls != null){
            urls = null;
        }

        if(msctbList != null){
            msctbList = null;
        }

        if(mGridView1 != null){
            mGridView1 = null;
        }

        if(adapter != null){
            adapter = null;
        }

        if(PeiZhiController != null){
            PeiZhiController = null;
        }

        if(goodsController != null){
            goodsController = null;
        }

        if(swipe_container != null){
            swipe_container = null;
        }

        if(indexGoodsList != null ){
            indexGoodsList = null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("geek","MallIndexFragment onCreateView");
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        /** */
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        swipe_container = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(this);
        swipe_container.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        ckgd = (TextView) view.findViewById(R.id.ckgd);
        ckgd.setOnClickListener(this);
        mallgv = (GridView) view.findViewById(R.id.mallgrid);
        title = (TextView) view.findViewById(R.id.title);

        if(msctbList == null){
            msctbList = new ArrayList<CxwyMallPezhi>();
        }
         adapter = new MallTubaioImageAdapter(msctbList, getActivity());
         mallgv.setAdapter(adapter);

         mallgv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	if(position == 3){
            		 startActivity(ShopListActivity.class);
            		 return;
            	}
            	Bundle bundle = new Bundle();
        		bundle.putInt("type", 1);
        		bundle.putString("searchFlg", msctbList.get(position).getMallPeizhiBeiyong());
                Log.d("geek","关键字"+msctbList.get(position).getMallPeizhiBeiyong());
        		startActivity(ShopListActivity.class, bundle);
            }
        });

        mGridView1=(GridView)view.findViewById(R.id.grid_image1);

        if(indexListData == null){
            indexListData = new ArrayList<>();
        }
        indexGoodsList=new MallIndexGoodsListAdapter(indexListData,getActivity());
        mGridView1.setAdapter(indexGoodsList);

        mGridView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("goods", indexListData.get(arg2));
				startActivity(GoodsDestailActivity.class, bundle);
			}
		});

        search = (ImageView) view.findViewById(R.id.search);
        search.setOnClickListener(this);

        if(urls == null){
            urls = new ArrayList<>();
        }

        mall_lunbo = (SlideShowView) view.findViewById(R.id.mall_lunbo);

        scrollView = (ScrollView) view.findViewById(R.id.scrollView2);
        scrollView.scrollTo(0, 0);
        if(sp.getString(save_mall_imgurl, "") != null && !"".equals(sp.getString(save_mall_imgurl, "")))
        {
            //默认本地存在图片
            Log.d("geek","默认本地存在图片 ");
            String tupian = sp.getString(save_mall_imgurl, "");
            CxwyMallPezhi peuzhi = gson.fromJson(tupian, CxwyMallPezhi.class);
            setPeizhiImg(peuzhi,true);
        }else{
            Log.d("geek","默认本地不不不存在图片 ");
            progressDialog.show();
            getlunbotubiao();
        }
        initDataFromNet();
        return view;
    }

    public void onRefresh() {
        initDataFromNet();
        getlunbotubiao();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ckgd:
                //获取首页推荐商品
                Bundle bundle = new Bundle();
                bundle.putInt("type", 2);
                startActivity(ShopListActivity.class,bundle);
               break;
            case R.id.search:
            	startActivity(SearchActivity.class);
            	break;
        }
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("geek","商城首页 onStop()");
    }

	@Override
	protected void initDataFromLocal() {

	}

    @Override
    protected void initDataFromNet() {
        if (goodsController == null) {
            goodsController = new GoodsControllerImpl();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("rows", "6");
        params.put("page", "0");
        params.put("product.shangpinClassicShow", "0");
        params.put("appxiaoqu", Contains.curSelectXiaoQuId+"");
        // 请求获取首页推荐商品
        Log.d("geek","获取首页推荐商品"+params.toString());
        goodsController.getIndexGoodsList(mRequestQueue, params, menuListener);
    }

    private ResultListener<ShopList> menuListener = new ResultListener<ShopList>() {
        @Override
        public void onResponse(ShopList info) {
            if (info.status != STATUS_CODE_OK) {
                onError(info.MSG);
                return;
            }

            if(isEmptyList(info.getProductList())) {
                onError("");
            }else {
                indexListData.clear();
                try{
                    indexListData = info.getProductList();
                    indexGoodsList.refreshData(indexListData);
                }catch (Exception e){

                }
            }
            progressDialog.hide();
            if(swipe_container != null){
                swipe_container.setRefreshing(false);
            }
        }

        @Override
        public void onErrorResponse(String errMsg) {
            if(swipe_container != null ){
                swipe_container.setRefreshing(false);
            }
            onError("");
        }
    };

    private void getlunbotubiao(){
        if(PeiZhiController == null){
            PeiZhiController = new PeiZhiControllerImpl();
        }
        PeiZhiController.getAllScLbTbList(mRequestQueue, new Object[]{}, new ResultListener<CxwyMallPezhi>() {
            @Override
            public void onResponse(CxwyMallPezhi info) {
                if(swipe_container != null){
                    swipe_container.setRefreshing(false);
                }
                if(info.status != STATUS_CODE_OK) {
                    onError(info.MSG);
                    return;
                }
                setPeizhiImg(info,false);
            }

            @Override
            public void onErrorResponse(String errMsg) {
                if(swipe_container != null){
                    swipe_container.setRefreshing(false);
                }
            }
        });
    }

    private void setPeizhiImg(CxwyMallPezhi info,boolean isRequest) {
        if(!isEmptyList(info.getLblist())) {
            if(urls != null && urls.size() >0){
                urls.clear();
            }
                for (int i = 0; i < info.getLblist().size(); i++) {
                    if(urls != null && info.getLblist().get(i) != null && info.getLblist().get(i).getMallPeizhiValue() != null && !"".equals(info.getLblist().get(i).getMallPeizhiValue())){
                        urls.add(API.PIC+info.getLblist().get(i).getMallPeizhiValue());
                    }
                }
            if(urls != null && urls.size() >0){
                mall_lunbo.setImageUris(urls,getActivity(),false);
            }
        }

        if(!isEmptyList(info.getTblist())) {
            msctbList = info.getTblist();
            if(msctbList != null){
                adapter.setmList(msctbList);
            }
        }
        String imgToStr = gson.toJson(info);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(save_mall_imgurl, imgToStr);
        editor.commit();
        Log.d("geek","商城首页 setPeizhiImg");
        if(isRequest){
            Log.d("geek","商城首页 setPeizhiImg "+isRequest);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("geek","商城首页 onPause()");
    }

}
