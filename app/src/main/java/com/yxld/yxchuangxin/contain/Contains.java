package com.yxld.yxchuangxin.contain;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yxld.yxchuangxin.entity.AppYezhuFangwu;
import com.yxld.yxchuangxin.entity.CxwyMallAdd;
import com.yxld.yxchuangxin.entity.CxwyMallCart;
import com.yxld.yxchuangxin.entity.CxwyMallComment;
import com.yxld.yxchuangxin.entity.CxwyMallProduct;
import com.yxld.yxchuangxin.entity.CxwyMallUser;
import com.yxld.yxchuangxin.entity.CxwyYezhu;
import com.yxld.yxchuangxin.entity.SureOrderEntity;

/**
 * 静态常量类
 * 
 * @author wwx
 */
public class Contains {
	/**
	 * 判断程序有没有被系统KILL掉的字符串
	 */
	public static String isKill = "no";

	/** 加载图片 */
	public static ImageLoader loadingImg = null;
	/** 获取报修时详细内容*/
	public static String repairContextStr = "";
	/** 获取报修时地址内容*/
	public static String repairAddressStr = "";
	/** 报修时项目名*/
	public static String repairXiangmu ="";
	/** 报修时区域*/
	public static String repairQuyu ="专有区域";
	
//	/** 购物车集合 */
	public static List<CxwyMallCart> CartList = new ArrayList<CxwyMallCart>();

	/** 收货地址 */
	public static CxwyMallAdd defuleAddress = new CxwyMallAdd();

//	/** 用户信息 */
//	public static CxwyMallUser cxwyMallUser = new CxwyMallUser();

	/** 业主信息 */
	public static CxwyYezhu user = new CxwyYezhu();

	/** 业主房屋中间表 */
	public static List<AppYezhuFangwu> appYezhuFangwus = new ArrayList<AppYezhuFangwu>();

	/** 订单中的商品评论集合 */
	public static List<CxwyMallComment> curCommData = new ArrayList<CxwyMallComment>();

	/** 经度 */
	public static double longitude = 0.0;
	/** 纬度 */
	public static double Latitude = 0.0;
	/** 定位小区*/
	public static String AoiName =null;
	/** 定位城市*/
	public static String locationCity = null;
	
	/** 选择小区名字*/
	public static String  curSelectXiaoQuName = "";
	/** 选择小区ID*/
	public static int  curSelectXiaoQuId = 0;

	/** 确认订单集合*/
	public static List<SureOrderEntity> sureOrderList = new ArrayList<SureOrderEntity>();

	/** token*/
	public static String token="";


	/** Pay Callback Server URL **/
	public final static String URL_PAY_CALLBACK = "http://222.240.1.133/WechatPayServer";
	// 微信开放平台审核通过的应用APPID
	public static final String WX_APP_ID = "wx474645d31f239239";
	// 微信支付分配的商户号
	public static final String WX_MCH_ID = "1353654702";


	public static String orderId="";
	public static String payPrice="";
	public static int pay=0;
	public static String orderBianhao="";
}
