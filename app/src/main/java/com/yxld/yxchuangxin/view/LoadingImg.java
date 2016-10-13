package com.yxld.yxchuangxin.view;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.yxld.yxchuangxin.R;

/**
 * 图片加载类
 * 
 * @author Administrator
 * 
 */
public class LoadingImg {
	public static ImageLoader loader;
	public static DisplayImageOptions options;
	public static DisplayImageOptions option1 = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.mipmap.empty_photo).showImageOnFail(R.mipmap.empty_photo)
			.showImageForEmptyUri(R.mipmap.empty_photo)
			// 正在加载
			.cacheInMemory(true).considerExifParams(true).cacheOnDisc(true)
			.bitmapConfig(Bitmap.Config.ARGB_8888).build();

	public static ImageLoader LoadingImgs(Context context) {
		initImageLoader(context.getApplicationContext());
		loader = ImageLoader.getInstance();
		return loader;
	}

	// 配置图片加载器
	public static void initImageLoader(Context context) {
		@SuppressWarnings("deprecation")
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"unclePhone/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.defaultDisplayImageOptions(defaultOptions)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.discCacheFileCount(100)
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// 缓存的文件数量
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.discCache(new UnlimitedDiscCache(cacheDir))
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}
}
