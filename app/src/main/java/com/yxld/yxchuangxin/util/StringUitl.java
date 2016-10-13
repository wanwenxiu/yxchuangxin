package com.yxld.yxchuangxin.util;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @ClassName: StringUitl
 * @Description: 字符串工具类
 * @author wwx
 * @date 2015年7月22日 下午3:03:36
 *
 */
public class StringUitl {

	/**
	 * @Title: isEmepty
	 * @Description: 判断字符串是否为空和空串
	 * @param str
	 * @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isNoEmpty(String str) {
		if (str == null) {
			return false;
		} else if ("".equals(str)) {
			return false;
		}
		return true;
	}

	/**
	 * @Title: isEmpty
	 * @Description: 判断输入框EditText是否不为空和空串
	 * @param context
	 * @param edittext
	 * @param isEmptyStr
	 *            为空串提示语
	 * @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isNotEmpty(Context context, EditText edittext,
			String isEmptyStr) {
		if (edittext.getText() == null) {
			if(isEmptyStr != null){
				Toast.makeText(context, isEmptyStr, Toast.LENGTH_LONG).show();
			}
			return false;
		} else if ("".equals(edittext.getText().toString())) {
			if(isEmptyStr != null){
				Toast.makeText(context, isEmptyStr, Toast.LENGTH_LONG).show();
			}
			return false;
		}
		return true;
	}

	/**
	 * @Title: intToIp
	 * @Description: 转换为Ip地址
	 * @param i
	 * @return
	 * @return String
	 * @throws
	 */
	public static String intToIp(int i) {

		return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
				+ "." + (i >> 24 & 0xFF);
	}
	
	/**
	 * 验证字符串是否是手机号码
	 */
	public static boolean isMobileNum(String num) {
		String expression = "^((13[0-9])|(17[0-9])|(14[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$";
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(num);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Title: removeDuplicate
	 * @Description: 移除list重复元素
	 * @param list
	 * @return
	 * @return List
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List removeDuplicate(List list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
		return list;
	}

	// 获取当月的 天数
	public static int getCurrentMonthDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
}
