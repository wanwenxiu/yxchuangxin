/**
 * 
 */
package com.yxld.yxchuangxin.view;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.yxld.yxchuangxin.contain.Contains;

/**
 * 辅助工具类
 * 
 * @创建时间： 2015年11月24日 上午11:46:50
 * @项目名称： AMapLocationDemo2.x
 * @author hongming.wang
 * @文件名称: Utils.java
 * @类型名称: Utils
 */
public class Utils {
	/**
	 * 开始定位
	 */
	public final static int MSG_LOCATION_START = 0;
	/**
	 * 定位完成
	 */
	public final static int MSG_LOCATION_FINISH = 1;
	/**
	 * 停止定位
	 */
	public final static int MSG_LOCATION_STOP = 2;

	public final static String KEY_URL = "URL";
	public final static String URL_H5LOCATION = "file:///android_asset/location.html";

	/**
	 * 根据定位结果返回定位信息的字符串
	 * 
	 * @param
	 * @return
	 */
	public synchronized static String getLocationStr(AMapLocation location) {
		if (null == location) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		// errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
		if (location.getErrorCode() == 0) {
			Contains.longitude = location.getLongitude();
			Contains.Latitude = location.getLatitude();
//			Contains.locationCity = location.getCity();
//			Contains.AoiName = location.getAoiName();
			if (location.getProvider().equalsIgnoreCase(
					android.location.LocationManager.GPS_PROVIDER)) {
			} else {
				// 提供者是GPS时是没有以下信息的
				sb.append(location.getPoiName());
			}
		} else {
			// 定位失败
			// sb.append("定位失败" + "\n");
			// sb.append("错误码:" + location.getErrorCode() + "\n");
			// sb.append("错误信息:" + location.getErrorInfo() + "\n");
			// sb.append("错误描述:" + location.getLocationDetail() + "\n");
			sb.append(location.getErrorInfo());

		}
		return sb.toString();
	}
}
