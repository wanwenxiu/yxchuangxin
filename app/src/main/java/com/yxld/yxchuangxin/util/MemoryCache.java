package com.yxld.yxchuangxin.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class MemoryCache implements ImageCache 
{
  private LruCache<String, Bitmap> mCache;
  
  public MemoryCache()
  {
    int maxSize=(int)Runtime.getRuntime().maxMemory()/8;
    mCache=new LruCache<String, Bitmap>(maxSize){
      @Override
      protected int sizeOf(String key, Bitmap value) {
        return value.getHeight()*value.getRowBytes();
      }
    };
  }

  @Override
  public Bitmap getBitmap(String key) {
    return mCache.get(key);
  }

  @Override
  public void putBitmap(String key, Bitmap value) {
    mCache.put(key, value);
  }
}
