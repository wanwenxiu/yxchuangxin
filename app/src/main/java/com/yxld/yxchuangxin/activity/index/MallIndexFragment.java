package com.yxld.yxchuangxin.activity.index;

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
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yishangfei on 2016/3/4.
 */
public class MallIndexFragment extends BaseFragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout swipe_container;
    private ImageCycleView ad_view;
    private ArrayList<String> urls;
    private TextView ckgd;
    private  GridView mGridView1;
    public MallIndexGoodsListAdapter indexGoodsList;
    public  TextView title;
    private ScrollView scrollView;
    
    /** 搜素框*/
    private ImageView search;
    
    private GridView mallgv;

    private PeiZhiController PeiZhiController;
    private GoodsController goodsController;
    private List<CxwyMallPezhi> msctbList;

    /**菜单分类适配器*/
    private MallTubaioImageAdapter adapter;

    /**首页推荐商品*/
    private List<CxwyMallProduct> indexListData;

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("geek","商城首页 ondestory()");
        if(urls != null){
            urls = null;
        }

        if(msctbList != null){
            msctbList = null;
        }

        if(indexListData != null){
            indexListData = null;
        }

        if(ad_view != null){
            ad_view = null;
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
        Log.d("geek","商城首页 onCreate()");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
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

        ad_view = (ImageCycleView) view.findViewById(R.id.ad_view);
        search = (ImageView) view.findViewById(R.id.search);
        search.setOnClickListener(this);

        if(urls == null){
            urls = new ArrayList<>();
        }
        ad_view.setImageResources(urls, new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void onImageClick(int position, View imageView) {
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_LONG).show();
            }
        }, 0);
        
        scrollView = (ScrollView) view.findViewById(R.id.scrollView2);
        scrollView.scrollTo(0, 0);
        getlunbotubiao();
        initDataFromNet();
        return view;
    }

    public void onRefresh() {
        initDataFromNet();
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
    	ad_view.startImageCycle();
        Log.d("geek","商城首页 onResume()");
    }

    @Override
    public void onStop() {
        super.onStop();
        ad_view.stopImageCycle();
        Log.d("geek","商城首页 onStop()");
    }

	@Override
	protected void initDataFromLocal() {

	}

    @Override
    protected void initDataFromNet() {
        super.initDataFromNet();
        if (goodsController == null) {
            goodsController = new GoodsControllerImpl();
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("rows", "6");
        params.put("page", "0");
        params.put("product.shangpinClassicShow", "0");
        params.put("appxiaoqu", Contains.curSelectXiaoQu);
        // 请求获取首页推荐商品
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
                onError("没有推荐商品");
            }else {
                indexListData.clear();
                indexListData = info.getProductList();
                indexGoodsList.refreshData(indexListData);
            }
            progressDialog.hide();
            swipe_container.setRefreshing(false);
        }

        @Override
        public void onErrorResponse(String errMsg) {
            swipe_container.setRefreshing(false);
            onError("数据获取失败");
        }
    };

    private void getlunbotubiao(){
        if(PeiZhiController == null){
            PeiZhiController = new PeiZhiControllerImpl();
        }

        PeiZhiController.getAllScLbTbList(mRequestQueue, new Object[]{}, new ResultListener<CxwyMallPezhi>() {
            @Override
            public void onResponse(CxwyMallPezhi info) {
                if (info.status != STATUS_CODE_OK) {
                    onError(info.MSG);
                    return;
                }

                if(!isEmptyList(info.getLblist())) {
                    for (int i = 0; i < info.getLblist().size(); i++) {
                        urls.add(API.PIC+info.getLblist().get(i).getMallPeizhiValue());
                    }
                    ad_view.setImageResources(urls, new ImageCycleView.ImageCycleViewListener() {
                        @Override
                        public void onImageClick(int position, View imageView) {
                            Toast.makeText(getActivity(), "" + position, Toast.LENGTH_LONG).show();
                        }
                    }, 0);
                }

                if(!isEmptyList(info.getTblist())) {
                    msctbList = info.getTblist();
                    adapter.setmList(msctbList);
                }
            }

            @Override
            public void onErrorResponse(String errMsg) {
                onError("暂未获取到最新图片");
            }
        });
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.d("geek","商城首页 onPause()");
    }




}
