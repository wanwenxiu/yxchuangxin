package com.yxld.yxchuangxin.util;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.util.Log;

public class ImagesLoader {

	private LruCache<String, Bitmap> cache;

	private static final int THREAD_COUNT = 3;
	// 128k

	public final static String PATH = "http://yishangfei.top/cx/resource/images/";

	// 如果在屏幕上网格数量特别多，就可能导致开辟过多的线程
	private ExecutorService pool;

	public ImagesLoader() {
		pool = Executors.newFixedThreadPool(THREAD_COUNT);
		// 获取当前APP运行期间可以占用的最大内存
		long maxMemory = Runtime.getRuntime().maxMemory();// 32
		cache = new LruCache<String, Bitmap>((int) (maxMemory / 8)) {

			// 告诉cache如何计算每一个value的大小
			@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}
		};
	}



	public void loadImage(final String imagePath, final UIListener listener) {

		Bitmap bitmap = cache.get(imagePath);

		if (bitmap != null) {
			// 如果缓存中已经包含了这张图片，直接拿来更新UI
			listener.updateImage(bitmap);
		} else {
			Log.d("...", "download:" + imagePath);

			pool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.MILLISECONDS.sleep(500);
						URL url = new URL(PATH+ imagePath);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setConnectTimeout(5000); //设置连接超时为5秒
						conn.setRequestMethod("GET"); //设定请求方式
  						conn.connect();
						DataInputStream dis = new DataInputStream(conn.getInputStream());
						if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
							InputStream in = conn.getInputStream();
							Bitmap bm = BitmapFactory.decodeStream(in);

							cache.put(imagePath, bm);
							listener.updateImage(bm);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}



}