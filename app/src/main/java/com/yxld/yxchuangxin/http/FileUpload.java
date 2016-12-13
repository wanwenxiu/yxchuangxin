package com.yxld.yxchuangxin.http;

/**
 * Created by Administrator on 2016/8/20 0020.
 */

import android.os.Handler;

import com.yxld.yxchuangxin.activity.index.selectimg.Repair;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 本类可以完成一个或多个文件的提交。 结构非常简单直接看代码就可以了。
 *
 * @author janken<br> */
public class FileUpload {
    /**
     * 提交文件的地址.
     */
    private String url = null;
    /**
     * 要提交的文件.
     */
    private List<File> files = null;

    private Handler handler = null;

    /**
     * 构造方法。
     *
     * @param url
     *            这个URL必须是get提交方式的URL，即这个URL不能带有任何参数信息。eg：'http://<span style="color: #000000;">localhost</span>:8080/FileUploadServer/file/upload.do'<br>     */
    public FileUpload(String url,Handler handler) {
        this.url = url;
        files = new ArrayList<File>();
        this.handler = handler;
    }

    /**
     * 通过这个方法来添加要提交的文件。
     *
     *
     * @param file
     *            提交的文件,如果文件为空或者不存在或者不可读，则不提交这个文件，重复的文件只提交一次。
     */
    public void addFile(File file) {
        if (file == null || !file.exists() || !file.canRead()) {
            return;
        } else {
            for (int i = 0; i < files.size(); i++) {
                if (file.getPath().equalsIgnoreCase(files.get(i).getPath())) {
                    return;
                }
            }
            files.add(file);
        }
    }

    /**
     * 提交的方法，该方法为每个文件创建一个请求连接进行提交。
     * @throws Exception
     */
    public void upload() throws Exception {
        for (int i = 0; i < files.size(); i++) {
            HttpClient httpClient = new DefaultHttpClient();
            try {
                FileEntity entity = new FileEntity(files.get(i),
                        "binary/octet-stream");
                StringBuilder curUrl = new StringBuilder(url);
                curUrl.append("?androidFileName=" + files.get(i).getName());
                HttpPost httppost = new HttpPost(curUrl.toString());
                httppost.setEntity(entity);

                HttpResponse response = httpClient.execute(httppost);
                int resultCode = response.getStatusLine().getStatusCode();
                if (resultCode == HttpStatus.SC_OK) {

                }else{
                    handler.sendEmptyMessage(Repair.uploadFaild);
                    throw new Exception("上传文件" + files.get(i).getPath()
                            + "失败.错误代码是：" + resultCode + ";原因描述是："
                            + response.getStatusLine().getReasonPhrase());
                }
            } finally {
                if (httpClient != null
                        && httpClient.getConnectionManager() != null) {
                    httpClient.getConnectionManager().shutdown();
                }
            }
        }

        handler.sendEmptyMessage(Repair.uploadSuccess);
    }
}
