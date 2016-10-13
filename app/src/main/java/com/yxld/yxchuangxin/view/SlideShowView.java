package com.yxld.yxchuangxin.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.yxld.yxchuangxin.R;


/**
 * ViewPager实现的轮播图广告自定义视图；
 *  既支持自动轮播页面也支持手势滑动切换页面,可以动态设置图片的张数
 * @author Chao Gong
 *
 */
@SuppressLint("HandlerLeak")
public class SlideShowView extends FrameLayout {
	private final boolean isAutoPlay = true; 
//    private List<Integer> imageRids;
    private List<String> imageUris;
    private List<ImageView> imageViewsList;
    private List<ImageView> dotViewsList;
    private LinearLayout mLinearLayout;
    private ViewPager mViewPager;
    private int currentItem  = 0;
    private ScheduledExecutorService scheduledExecutorService;
    private Context contexts;
    
    private ArrayList<Integer> listInt = new ArrayList<Integer>();
    
    private boolean isShowBigImg = false;
    
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
    	@Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mViewPager.setCurrentItem(currentItem);
        }
    };
    public SlideShowView(Context context) {
        this(context,null);
    }
    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
       
        initUI(context);
        
//        if(!(imageRids.size()<=0))
//        {
//        	System.out.println("XXXXXXXXXXXX");
//        	 setImageSrc(imageRids,contexts);
//        }
        
//        if(!(imageUris.size()<=0))
//        {
//        	System.out.println("XXXXXXXXXXXX");
//        	 setImageUris(imageUris,contexts);
//        }
        	
        if(isAutoPlay){
            startPlay();
        }
        
    }
    private void initUI(Context context){
    	  imageViewsList = new ArrayList<ImageView>();
          dotViewsList = new ArrayList<ImageView>();
          imageUris=new ArrayList<String>();
//          imageRids = new ArrayList<Integer>();
          //imageLoaderWraper=ImageLoaderWraper.getInstance(context.getApplicationContext());
          LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
          mLinearLayout=(LinearLayout)findViewById(R.id.linearlayout);
          mViewPager = (ViewPager) findViewById(R.id.viewPager);
    }
    
//    /**
//     * @Title: setImageSrc
//     * @Description: 根据Rid
//     * @param imageRid
//     * @param context
//     * @return void
//     * @throws
//     */
//    public void setImageSrc(List<Integer> imageRid,Context context,boolean isShowBig){
//    	isShowBigImg = false;
//    	isShowBigImg = isShowBig;
//    	contexts = context;
//    	for(int i=0;i<imageRid.size();i++)
//    	{
//    		imageRids.add(imageRid.get(i));
//    	}
//    	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(5, 0, 0, 0);
//        for(int i=0;i<imageRids.size();i++){
//        	ImageView imageView=new ImageView(getContext());
//        	imageView.setScaleType(ScaleType.FIT_XY);
//        	imageView.setBackgroundResource(imageRids.get(i));
//        	imageViewsList.add(imageView);
//        	ImageView viewDot =  new ImageView(getContext());
//        	  if(i == 0){
//        		  viewDot.setBackgroundResource(R.mipmap.cicle_banner_dian_focus);
//              }else{
//            	  viewDot.setBackgroundResource(R.mipmap.cicle_banner_dian_blur);
//              }
//        	viewDot.setLayoutParams(lp);
//        	dotViewsList.add(viewDot);
//        	mLinearLayout.addView(viewDot);
//
//        	listInt.add(imageRids.get(i));
//      }
//        mViewPager.setFocusable(true);
//        mViewPager.setAdapter(new MyPagerAdapter());
//        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
//    }
    
    
    /**
     * @Title: setImageUris
     * @Description: 根据URL_IMAGE设置图片
     * @param imageuris
     * @param context
     * @return void
     * @throws
     */
    public void setImageUris(List<String> imageuris,Context context,boolean isShowBig){
        isShowBigImg = false;
		isShowBigImg = isShowBig;
    	Log.d("geek", "setImageUris ="+imageuris.size()+"---"+imageuris.toString());
    	contexts = context;
    	imageUris.clear();
    	dotViewsList.clear();
    	mLinearLayout.removeAllViews();
    	for(int i=0;i<imageuris.size();i++)
    	{
    		imageUris.add(imageuris.get(i));
    	}
    	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 0, 0, 0);
        for(int i=0;i<imageUris.size();i++){
        	ImageView imageView=new ImageView(getContext());
        	imageView.setScaleType(ScaleType.FIT_XY);
            LoadingImg.LoadingImgs(context).displayImage(
					imageuris.get(i) + "",
							imageView, LoadingImg.option1);
        	imageViewsList.add(imageView);
        	ImageView viewDot =  new ImageView(getContext());
        	  if(i == 0){
        		  viewDot.setBackgroundResource(R.mipmap.cicle_banner_dian_focus);
              }else{
            	  viewDot.setBackgroundResource(R.mipmap.cicle_banner_dian_blur);
              }
        	viewDot.setLayoutParams(lp);
        	dotViewsList.add(viewDot);
        	mLinearLayout.addView(viewDot);

            if(imageUris.size() == 1){
                mLinearLayout.setVisibility(View.GONE);
            }else{
                mLinearLayout.setVisibility(View.VISIBLE);
            }
      }
        mViewPager.setFocusable(true);
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
    }
   
    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
    }
 
    @SuppressWarnings("unused")
	private void stopPlay(){
        scheduledExecutorService.shutdown();
    }
    /** 
     * ����ѡ�е�tip�ı��� 
     * @param selectItems 
     */  
    private void setImageBackground(int selectItems){  
        for(int i=0; i<dotViewsList.size(); i++){  
            if(i == selectItems){  
            	dotViewsList.get(i).setBackgroundResource(R.mipmap.cicle_banner_dian_focus);
            }else{  
            	dotViewsList.get(i).setBackgroundResource(R.mipmap.cicle_banner_dian_blur);
            }  
        }  
    }  
    private class MyPagerAdapter  extends PagerAdapter{

        @Override
        public void destroyItem(View container, int position, Object object) {
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager)container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, final int position) {
            ((ViewPager)container).addView(imageViewsList.get(position));
        	imageViewsList.get(position).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					if(isShowBigImg){
//						Intent intent = new Intent(contexts, GalleryImgActivity.class);
//						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						intent.putExtra("list", listInt);
//						intent.putExtra("position", position);
//						contexts.startActivity(intent);
//					}
				}
			});
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {
            
        }
        
    }
  
    
    private class MyPageChangeListener implements OnPageChangeListener{

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
            case 1:
                isAutoPlay = false;
                break;
            case 2:
                isAutoPlay = true;
                break;
            case 0:
                if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                    mViewPager.setCurrentItem(0);
                }
                
                else if (mViewPager.getCurrentItem() == 0 && !isAutoPlay) {
                    mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1);
                }
                break;
        }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            
        }

        @Override
        public void onPageSelected(int pos) {
//            setImageBackground(pos % imageRids.size());
        	 setImageBackground(pos % imageUris.size());
        }
        
    }
    
    
    private class SlideShowTask implements Runnable{

        @Override
        public void run() {
            synchronized (mViewPager) {
                currentItem = (currentItem+1)%imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
        
    }
	@SuppressWarnings("unused")
	private void destoryBitmaps() {

        for (int i = 0; i < imageViewsList.size(); i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                drawable.setCallback(null);
            }
        }
    }
	
	
}
