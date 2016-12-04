package com.yxld.yxchuangxin.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonRequest<T> extends Request<T> {
	private Gson mGson = new Gson();
	private Class<T> clazz;
	private Map<String, String> headers;
	private Map<String, String> params;
	private Listener<T> listener;

	private List<File> mFileParts;
	private Map<String, List<File>> mFilePartsMap;
	private String mFilePartName;
	private MultipartEntity entity = new MultipartEntity();

	/**
	 * Make a GET request and return a parsed object from JSON.
	 * 
	 * @param url
	 *            URL of the request to make
	 * @param clazz
	 *            Relevant class object, for Gson's reflection
	 */
	public GsonRequest(String url, Class<T> clazz, Listener<T> listener,
			ErrorListener errorListener) {
		super(Method.GET, url, errorListener);
		Log.d("reqUrl", url);
		this.clazz = clazz;
		this.listener = listener;
		mGson = new Gson();
	}

	/**
	 * Make a POST request and return a parsed object from JSON.
	 * 
	 * @param url
	 *            URL of the request to make
	 * @param clazz
	 *            Relevant class object, for Gson's reflection
	 */
	public GsonRequest(String url, Class<T> clazz, Map<String, String> params,
			Listener<T> listener, ErrorListener errorListener) {
		super(Method.POST, url, errorListener);
		Log.d("reqUrl", url + params.toString());
		this.clazz = clazz;
		this.params = params;
		this.listener = listener;
		this.headers = null;
		buildMultipartEntity();
		mGson = new Gson();
	}

	/**
	 * 创建文件传输流
	 */
	private void buildMultipartEntity() {
		//文件列表
		if (mFileParts != null && mFileParts.size() > 0) {
			for (File file : mFileParts) {
				entity.addPart(mFilePartName, new FileBody(file));
			}
		}
		//多组文件
		if(mFilePartsMap != null  && mFilePartsMap.size() > 0){
			Iterator<Entry<String, List<File>>> iter = mFilePartsMap.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, List<File>> entry =  iter.next();
				String key = entry.getKey();
				List<File> files = entry.getValue();
				if(files != null && files.size() > 0){
					for (int i = 0; i < files.size(); i++) {
						entity.addPart(key + i, new FileBody(files.get(i)));
					}
				}
			}
		}
		//普通的字符串
		try {
			if (params != null && params.size() > 0) {
				for (Entry<String, String> entry : params.entrySet()) {
					entity.addPart(
							entry.getKey(),
							new StringBody(entry.getValue(), Charset
									.forName("UTF-8")));
				}
			}
		} catch (UnsupportedEncodingException e) {
			VolleyLog.e("UnsupportedEncodingException");
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getBodyContentType() {
		return entity.getContentType().getValue();
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			entity.writeTo(bos);
			return bos.toByteArray();
		} catch (IOException e) {
			VolleyLog.e("IOException writing to ByteArrayOutputStream");
			deliverError(new VolleyError("输入数据异常"));
		} catch (OutOfMemoryError e){
			VolleyLog.e("IOException writing to ByteArrayOutputStream");
			deliverError(new VolleyError("您输入的图片过多"));
		}
		return null;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers != null ? headers : super.getHeaders();
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			Log.i("response", json);
			if (mGson.fromJson(json, clazz) == null) {
				return Response.error(new VolleyError("数据解析错误"));
			}
			return Response.success(mGson.fromJson(json, clazz),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error(new ParseError(e));
		}
	}
}
