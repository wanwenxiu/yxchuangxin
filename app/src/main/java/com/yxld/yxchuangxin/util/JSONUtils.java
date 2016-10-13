package com.yxld.yxchuangxin.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yxld.yxchuangxin.entity.ImageBean;

/**
 * Created by yishangfei on 2016/3/7.
 */
public class JSONUtils {
    public static List<ImageBean> jsonToObject(String json) {
        List<ImageBean> list = null;
        if (json != null && json.trim().length() > 0) {
            try {
                list = new ArrayList<ImageBean>();
                JSONArray arr = new JSONArray(json);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject object = arr.getJSONObject(i);
                    ImageBean image = new ImageBean();
                    image.setId(object.getInt("id"));
                    image.setName(object.getString("name"));
                    image.setImagePath(object.getString("imagePath"));
                    list.add(image);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
