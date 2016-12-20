 package com.yxld.yxchuangxin.controller;


/**
 * @author wwx
 * @ClassName: API
 * @Description: 请求网络路径
 * @date 2016年3月11日 下午4:53:29
 */
public interface API {

    //域名
    String yuming = "http://www.hnchxwl.com/wygl";

    String menjinIP = "http://120.25.78.92/";
//  String menjinIP = "http://192.168.8.21:8080/";
  String IP_PRODUCT = "http://192.168.8.166:8080/wygl";
  String PIC = "http://192.168.8.166:8080";
  String uploadImage = "http://192.168.8.166:8080/wygl/mall/upload_uploadAndroidFile";
//   String IP_PRODUCT = "http://www.hnchxwl.com/wygl";
//   String PIC="http://www.hnchxwl.com";
//   String uploadImage = "http://www.hnchxwl.com/wygl/mall/upload_uploadAndroidFile";

    /**
     * 获取商品一级分类URL
     */
    String URL_GET_ALL_ATTENDANCE = IP_PRODUCT
            + "/mall/androidClassify_findClassifyOne";

  /** 根据一级分类获取二级分类 */
  String URL_GET_ALL_SECONDCLASS = IP_PRODUCT
            + "/mall/androidClassify_findByclassifyOne?classify.classifyId=%1$s";

    /**
     * 获取商品列表信息通过商品分类Id  未用上
     * rows=10&page=1&classify.classifyId=&sort=shangpinClassicTwo&order=asc&appxiaoqu=
     */
    String URL_GET_ALL_SHOPLIST_BY_ID = IP_PRODUCT
            + "/mall/androidProduct_findByProduct?";

    /**
     * 获取商品列表信息通过关键字 1202
     * rows=5&page=1&keys=%E7%8C%AA&sort=shangpinClassicTwo&order=asc&appxiaoqu=
     */
    String URL_GET_ALL_SHOPLIST_BY_KEY = IP_PRODUCT
            + "/mall/androidProduct_searchByKeys?";

  /***
   * 获取商品详情根据商品ID 1202
   */
    String URL_GETPRODUCT_BYGOODID = IP_PRODUCT
            + "/mall/androidProduct_findGoodsById?id=%1$s";

    /**
     * 添加商品至购物车
     */
    String URL_ADD_INFO_TO_CART = IP_PRODUCT + "/mall/cart_addCart";

    /**
     * 根据用户ID查找购物车信息
     */
    String URL_GET_INFO__CART_FROM_USERID = IP_PRODUCT
            + "/mall/cart_findByUerId?cart.cartSpare1=%1$s";

    /**
     * 根据购物车Id和购物车数量和购物车商品id,修改购物车信息
     */
    String URL_UPDATE_INFO__CART_FROM_ID = IP_PRODUCT
            + "/mall/cart_mergeCart?cart.cartId=%1$s&cart.cartNum=%2$s&cart.cartShangpNum=%3$s";

    /**
     * 根据购物车Id删除购物车信息
     */
    String URL_DELETE_INFO__CART_FROM_ID = IP_PRODUCT
            + "/mall/cart_deleteCart?";

    /**
     * 提交订单
     */
    String URL_ADD_ORDER = IP_PRODUCT + "/mall/androidOrder_addOrder?";

    /**
     * 根据业主id和订单状态获取订单列表
     */
    // http://192.168.0.114:8080/wygl/mall/order_findOrderByUserName?ord.dingdanUserName=1&ord.dingdanZhuangtai=已提交
    String URL_GET_ORDER_LIST_FROM_USER = IP_PRODUCT
            + "/mall/androidOrder_findOrderByUserName?";

    /**
     * 根据订单ID和订单状态修改订单状态
     */
    // http://192.168.0.114:8080/wygl/mall/order_mergeOrder?ord.dingdanId=51&ord.dingdanZhuangtai=已提交
    String URL_UPDATE_ORDER_STATE_FROM_ID = IP_PRODUCT
            + "/mall/androidOrder_mergeOrder?";

    /**
     * 根据订单ID修改用户退款流程（待发货状态下）
     */
    String URL_TUIKUAN_ORDER_STATE_FROM_ID = IP_PRODUCT
            + "/mall/androidOrder_TuikuanOrder?";

    /**
     * 根据订单ID 进行余额支付
     */
    // http://192.168.0.114:8080/wygl/mall/order_payYue?ord.dingdanId=51
    String URL_YU_E_PAY_ORDER_STATE_FROM_ID = IP_PRODUCT
            + "/mall/androidOrder_payYue?ord.dingdanId=%1$s";

    /**
     * 根据订单ID查询订单详情
     */
    // http://localhost:8080/wygl/mall/sale_findSaleByOrder?sale.saleDingdanId=47
    String URL_GET_ORDER_DESTAIL_FROM_ID = IP_PRODUCT
            + "/mall/androidOrder_findSaleByOrder?sale.saleDingdanId=%1$s";


    /**
     * 根据订单ID查询库存信息，判断是否可以付款
     */
    // http://localhost:8080/wygl/mall/sale_findSaleByOrder?sale.saleDingdanId=47
    String URL_GET_ORDER_KUNCUN_FROM_ID = IP_PRODUCT
            + "/mall/androidOrder_findOrderKucunByordid?ord.dingdanId=%1$s";

//    /**
//     * 注册接口  1202
//     */
//    String URL_GET_ALL_REGISTER = IP_PRODUCT
//            + "/mall/androidUser_addUser?shouji=%1$s&pwd=%2$s";

  /**
   * 注册接口
   */
  String URL_GET_ALL_REGISTER = IP_PRODUCT
          + "/mall/androidUser_addUser?shouji=%1$s&pwd=%2$s&chuangxinhao=%3$s";

  /**
   * 判断是否已注册
   */
  String URL_GET_ALL_REGISTER_ALREADY = IP_PRODUCT
          + "/mall/androidUser_isExistYonghu?shouji=%1$s&chuangxinhao=%2$s";

  /**
   * 判断手机号码是否注册
   */
  String URL_GET_EXIST_SHOUJI = IP_PRODUCT
          + "/mall/androidUser_isExistYonghuByShouji?shouji=%1$s";


  /**
   * 找回密码
   */
  String URL_GET_FIND_PWD = IP_PRODUCT
          + "/mall/androidUser_findPwd?shouji=%1$s&pwd=%2$s";

    /**
     * 用户评价商品
     */
    String URL_PRAISE_GOODS_FROM_USER = IP_PRODUCT
            + "/mall/comment_addComment?";

    /**
     * 获取商品评价列表 根据商品ID
     */
    String URL_PRAISE_LIST_FROM_GOOD = IP_PRODUCT
            + "/mall/comment_findComment?rows=%1$s&page=%2$s&comment.pingjiaShangpNum=%3$s&comment.pingjiaLevel=%4$s&comment.pingjiaBeiyong4=%5$s";

//    /**
//     * 判断是否已注册  1202
//     */
//    String URL_GET_ALL_REGISTER_ALREADY = IP_PRODUCT
//            + "/mall/androidUser_isExistYonghu?shouji=%1$s";
    /**
     * 登录接口  1202
     */
    String URL_GET_ALL_LOGIN = IP_PRODUCT
            + "/mall/androidUser_findUser?shouji=%1$s&pwd=%2$s";
    /**
     * 修改密码 1202
     */
    String URL_GET_ALL_UPDATE_PWD = IP_PRODUCT
            + "/mall/androidUser_changePassWord?yezhu.yezhuShouji=%1$s&yezhu.yezhuPwd=%2$s&newPassWord=%3$s";

    /**
     * 修改昵称
     */
    String URL_GET_ALL_UPDATE_NAME = IP_PRODUCT + "/mall/androidUser_mergeUserName?";

    /**
     * 修改身份证
     */
    String URL_GET_ALL_UPDATE_CARD = IP_PRODUCT
            + "/mall/androidUser_mergeUser?user.userId=%1$s&user.userIdCard=%2$s";

    /**
     * 收藏商品
     */
    String URL_COLLECT_GOODS_FROM_ID = IP_PRODUCT
            + "/mall/collection_addCollection?";

    /**
     * 删除收藏商品
     */
    String URL_DELETE_COLLECT_GOODS_FROM_ID = IP_PRODUCT
            + "/mall/collection_deleteCollection?collection.collectionId=%1$s&collection.collectionShangpId=%2$s&collection.collectionUserId=%3$s";

    /**
     * 获取首页推荐商品集合rows=%1$s&page=%2$s&product.shangpinClassicShow=%3$s
     * 1202
     */
    String URL_INDEX_GOODS_LIST = IP_PRODUCT
            + "/mall/androidProduct_findHomeProduct?";

    /**
     * 获取商品详情网页
     */
    public static String URL_GOODS_DESTAIL_WEB = IP_PRODUCT
            + "/pages/mall/productDetail.jsp?productId=";

    /**
     * 新增收获地址
     */
    String URL_ADD_ADDRESS = IP_PRODUCT + "/mall/add_saveAdd?";

    /**
     * 修改收获地址
     */
    String URL_UPDATE_ADDRESS = IP_PRODUCT + "/mall/add_mergeAdd?";

    /**
     * 根据用户查询收获地址列表
     */
    String URL_GET_ADDRESS_LIST_FROM_USER = IP_PRODUCT
            + "/mall/add_findAdd?add.addSpare2=%1$s";

    /**
     * 根据地址Id删除地址
     */
    String URL_DELETE_ADDRESS_FROM_ID = IP_PRODUCT
            + "/mall/add_deleteAdd?add.addId=%1$s";

    /**
     * 根据地址id设置默认地址
     */
    String URL_DEFULE_ADDRESS_FROM_ID = IP_PRODUCT
            + "/mall/add_setDefaultAdd?add.addId=%1$s";


    /**
     * 充值接口
     */
    String URL_CHONGZHI = IP_PRODUCT
            + "/mall/androidUser_yonghuRecharge.action";

    /**
     * 查询所有余额 （1202 存在）
     */
    String URL_YUE = IP_PRODUCT
            + "/mall/androidUser_findUserInfoByid?";

    /**
     * 获取首页轮播图
     */
    String URL_GET_MAIN_LUNBO = IP_PRODUCT
            + "/mall/androidPeizhi_findAppMainLunBoTu";

    /**
     * 获取商城轮播和图标
     */
    String URL_GET_SC_LUNBO_TUBIAO = IP_PRODUCT
            + "/mall/androidPeizhi_findAppScLunBoTu";


    /**
     * 优惠券
     */
    String URL_GET_YHQ = IP_PRODUCT
            + "/mall/androidUsedaijinquan_findAllUserDaijinquan?";

    /**
     * 不可用优惠券
     */
    String URL_GET_NOYHQ = IP_PRODUCT
            + "/mall/androidUsedaijinquan_findAllUserDaijinquanGuoqiShiyong?";

    /**
     * 获取版本信息
     */
    String URL_APP_GETVERSION = IP_PRODUCT
            + "/mall/androidApp_findNewVersion";

    /*
     * 获取所有报修项目列表 1202
     */
    String URL_GET_ALL_COMPLAINT = IP_PRODUCT
            + "/daily/androidComm_findAllXm.action";

    /*
     * 提交私有楼盘数据到服务器
     */
    String URL_GET_ALL_PRIVARE_LOUPAN = IP_PRODUCT
            + "/daily/androidComm_findLoudong.action?id=%1$s";

    /*
     * 提交私有楼栋数据到服务器
     */
    String URL_GET_ALL_PRIVARE_DANYUAN = IP_PRODUCT
            + "/daily/androidComm_findDanyuan.action?id=%1$s";

//	/*
// * 提交公开报修数据到服务器
// */
//	String URL_GET_ALL_PUBLIC_SUBMIT = IP_PRODUCT
//			+ "/daily/inreqair_inbaoxiu.action?";

    /*
     * 提交私有报修数据到服务器
     */
    String URL_GET_ALL_PRIVATE_SUBMIT = IP_PRODUCT
            + "/daily/androidBaoxiu_savebaoxiu.action?";

    /*
     * 提交物业管理投诉数据到服务器
     */
    String URL_GET_ALL_COMPAINT_WUYE_SUBMIT = IP_PRODUCT
            + "/daily/androidTousu_tousu.action?";

    /**
     * 获取报修项目
     */
    String URL_REPAIR_XIANGMU = IP_PRODUCT
            + "/daily/androidWxpoject_findxm.action";

    /**
     * 查询全部报修
     */
    String URL_REPAIR_ALL = IP_PRODUCT
            + "/daily/androidBaoxiu_findbaoxiu.action?";

    /**
     * 查询其他报修
     */
    String URL_REPAIR_OTHER = IP_PRODUCT
            + "/daily/androidBaoxiu_findbaox.action?";

    /**
     * 根据业主id获取业主成员列表 http://localhost:8080/wygl/daily/androidHousehold_findrz?parentid=0
     */
    String URL_findall_chengyuan = IP_PRODUCT
            + "/daily/androidHousehold_findrz?fwyzFw=%1$s";

    /**
     * 根据删除业主成员  1202
     */
    String URL_delete_chengyuan = IP_PRODUCT
            + "/daily/androidHousehold_deletecy?yezhuId=%1$s";

    /**
     * 根据保存业主成员  1202
     */
    String URL_add_chengyuan = IP_PRODUCT
            + "/daily/androidHousehold_saveyezhu?";

    /**
     * 物业费缴费记录
     */
    String URL_PAYMENT_RECORDS_WUYE = IP_PRODUCT
            + "/sdushu/androidSdushu_findAllYezhuNameWuye.action?";

    /**
     * 水费缴费记录
     */
    String URL_PAYMENT_RECORDS_WATER = IP_PRODUCT
            + "/sdushu/androidSdushu_findAllYezhuNameShui.action?";

    /**
     * 电费缴费记录
     */
    String URL_PAYMENT_RECORDS_ELECTRICITY = IP_PRODUCT
            + "/sdushu/androidSdushu_findAllYezhuNameDian.action?";


    /**
     * 物业付款
     */
    String URL_WUYE_PAY = IP_PRODUCT
            + "/sdushu/androidSdushu_jiaoFeiyong?";


    /**
     * 获取用户所有缴费记录
     */
    String URL_ALL_PAYMENT_RECORDS = IP_PRODUCT
            + "/wuye/androidWuye_findAllYezhuYJCost.action?";

    /**
     * 获取用户所有欠费记录
     */
    String URL_ALL_QIANFEI_RECORDS = IP_PRODUCT
            + "/wuye/androidWuye_findAllYezhuDJAndQFCost.action?";

    /**
     * 充值记录
     */
    String URL_GET_RECHARGE = IP_PRODUCT
            + "/mall/androidUser_findYonghuBalancereCord.action?";
    /**
     * 消费记录
     */
    String URL_GET_EXPENDITURE = IP_PRODUCT
            + "/mall/androidUser_findYonghuBalancereCord.action?";

//    /**
//     * 获取业主开门二维码   /业主姓名/业主电话/业主角色/楼盘ID/楼栋/单元120.25.78.92
//     */
//    String URL_GET_YEZHUOPENCODE = menjinIP + "door/coeds/getcode";

    /**
     * 获取业主开门二维码   /业主姓名/业主电话/业主角色/楼盘ID/楼栋/单元120.25.78.92
     */
    String URL_GET_YEZHUOPENCODE = menjinIP + "door/coeds/getcode?bName=%1$s&bPhone=%2$s&bRole=%3$s&building=%4$s&buildingHouse=%5$s&buildingUnit=%6$s";

    /**
     * 获取业主访客二维码  coed/getcodes/{bName}/{bPhone}/{bRole}/{name}/{phone}/{role}/{building}/{buildingHouse}/{buildingUnit}
     */
    String URL_GET_FangKeOPENCODE = menjinIP + "door/coeds/getcodes?bName=%1$s&bPhone=%2$s&bRole=%3$s&name=%4$s&phone=%5$s&role=%6$s&building=%7$s&buildingHouse=%8$s&buildingUnit=%9$s";

    /**
     * 获取首页通知活动标题
     */
    String URL_GET_NEWMAINTONGZHI = IP_PRODUCT
            + "/tongzhi/tongzhi_findTitle.action?";
}
