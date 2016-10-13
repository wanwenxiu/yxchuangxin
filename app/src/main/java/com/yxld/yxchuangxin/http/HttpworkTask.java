package com.yxld.yxchuangxin.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yxld.yxchuangxin.activity.index.selectimg.Repair;

public class HttpworkTask extends Thread {
	private static HttpClient httpClient;
	private final static int CONNECTIONTIMEOUT = 10000;//http链接超时
 	private final static int REQUESTTIMEOUT = 20000;//http数据请求超时
	
 	private Handler handler = null;
	
	private String url = null;
	private Map<String, Object> paras = null;//post的StringBody
	private Map<String, File> fileParas = null;//post的FileBody

	public HttpworkTask(String url, Map<String, Object> paras, Map<String, File> fileParas,Handler handler){
		this.url = url;
		this.paras = paras;
		this.fileParas = fileParas;
		this.handler = handler;
		Log.d("geek", "HttpworkTask  url=" + url+"，paras="+paras+"，fileParas="+fileParas);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		BufferedReader br = null;
		StringBuilder sBuilder = new StringBuilder();
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTIONTIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, REQUESTTIMEOUT);
		
		SchemeRegistry registry = new SchemeRegistry();  
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));  
		registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, registry), httpParams);HttpPost post = new HttpPost(url);
		MultipartEntity entity = new MultipartEntity();
		try {
			if (paras != null && !paras.isEmpty()) {
				for (Map.Entry<String, Object> item : paras.entrySet()) {
					entity.addPart(item.getKey(), new StringBody(item.getValue().toString(), Charset.forName("UTF-8")));
				}
			}

			if (fileParas != null && !fileParas.isEmpty()) {
				for (Map.Entry<String, File> item : fileParas.entrySet()) {
					if (item.getValue().exists()) {
						entity.addPart(item.getKey(), new FileBody(item.getValue()));
					}else{
					}
				}
			}
			post.setEntity(entity);

			HttpResponse response = httpClient.execute(post);

			int statecode = response.getStatusLine().getStatusCode();

			if (statecode == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				if (responseEntity != null) {
					InputStream is = responseEntity.getContent();
					br = new BufferedReader(new InputStreamReader(is));
					String tempStr;
					while ((tempStr = br.readLine()) != null) {
						sBuilder.append(tempStr);
					}
					br.close();
				}
			}
		} catch (Exception e) {
			Log.d("geek", "HttpworkTask  e=" + e);
			handler.sendEmptyMessage(Repair.uploadFaild);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		post.abort();
		//http返回的数据
		String resData = sBuilder.toString();
		if (resData != null) {
			Log.d("geek", "上传 "+""+ resData.toString());
			Message message = new Message();
			message.obj = resData.toString();
			message.what = Repair.uploadSuccess;
			handler.sendMessage(message);
		}
	}

	public static void shutdownHttp() {
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}
}