package com.yxld.yxchuangxin.util;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * 正则：身份证号码15位
	 */
	public static final String REGEX_ID_CARD15     = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
	/**
	 * 正则：身份证号码18位
	 */
	public static final String REGEX_ID_CARD18     = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";

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

	public static String getMD5(String info)
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(info.getBytes("UTF-8"));
			byte[] encryption = md5.digest();

			StringBuffer strBuf = new StringBuffer();
			for (int i = 0; i < encryption.length; i++)
			{
				if (Integer.toHexString(0xff & encryption[i]).length() == 1)
				{
					strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
				}
				else
				{
					strBuf.append(Integer.toHexString(0xff & encryption[i]));
				}
			}

			return strBuf.toString();
		}
		catch (NoSuchAlgorithmException e)
		{
			return "";
		}
		catch (UnsupportedEncodingException e)
		{
			return "";
		}
	}

	public static int getCurYear() {
		Calendar a = Calendar.getInstance();
		return  a.get(Calendar.YEAR);
	}

	public boolean isNull(String str) {
		return (str == null) || (str.trim().length() == 0);
	}

	public boolean isIdCard(String num) {
		if (isNull(num)) {
//			ToastUtil.show(this,"身份证不能为空");
			return false;
		}

		if (num.length() == 18 || num.length() == 15) {
			return true;
		}
//		ToastUtil.show(this,"身份证长度不正确");
		return false;
	}

	/**
	 * 验证身份证号码15位
	 *
	 * @param input 待验证文本
	 * @return {@code true}: 匹配<br>{@code false}: 不匹配
	 */
	public static boolean isIDCard15(CharSequence input) {
		return isMatch(REGEX_ID_CARD15, input);
	}

	/**
	 * 验证身份证号码18位
	 *
	 * @param input 待验证文本
	 * @return {@code true}: 匹配<br>{@code false}: 不匹配
	 */
	public static boolean isIDCard18(CharSequence input) {
		return isMatch(REGEX_ID_CARD18, input);
	}

	/**
	 * 判断是否匹配正则
	 *
	 * @param regex 正则表达式
	 * @param input 要匹配的字符串
	 * @return {@code true}: 匹配<br>{@code false}: 不匹配
	 */
	public static boolean isMatch(String regex, CharSequence input) {
		return input != null && input.length() > 0 && Pattern.matches(regex, input);
	}

}
