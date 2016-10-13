package com.yxld.yxchuangxin.entity;

import java.util.List;

import com.yxld.yxchuangxin.base.BaseEntity;

/**
 * CxwyMallOrder entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class CxwyMallOrder extends BaseEntity implements java.io.Serializable {
	
	private List<CxwyMallOrder> orderList;
	private List<CxwyMallSale> saleList;
	
	private int total;

	// Fields
	private Integer dingdanId;//订单id
	private String dingdanName;//姓名
	private String dingdanDizhi;//地址
	private String dingdanTel;//电话
	private String dingdanXiadanTime;//下单时间
	private String dingdanArrivedTime;//到达时间
	private String dingdanOverTime;//使用时间
	private Float dingdanTotalRmb;//总金额
	private String dingdanUserName;//用户名
	private String dingdanZhuangtai;//订单状态
	private String dingdanImgSrc;//订单图片
	private Integer dingdanUserId;//用户id
	private String dingdanBeiyong1;//付款方式
	private String dingdanBeiyong2;//前台删除标记,0是未删除，1为删除
	private String dingdanBeiyong3;//订单备注
	private String dingdanBeiyong4;//取消原因

	private String dingdanVillage;  //订单小区

	private String dingdanBianhao; //订单编号
	private Integer dingdanGoodsnum; //订单中商品数量
	private String dingdanShouliren; //订单受理人
	private Integer dingdanPeisongrenid; //订单配送人id
	private String dingdanBeiyong5; //订单配送人名

	private String dingdanPaisongtime; //派单时间
	private String dingdanBeiyong6;

	/** 订单中使用优惠券id*/
	private Integer dingdanYouhuiquanId;
	/** 订单中使用优惠价格*/
	private String dingdanYouhuijia;

	private String dingdanPayOrderhao; //第三方支付商户订单号
	private String dingdanPayJiaoyihao; //第三方支付交易号

	// Constructors

	/** default constructor */
	public CxwyMallOrder() {
	}

	public CxwyMallOrder(Integer dingdanId, String dingdanName, String dingdanDizhi, String dingdanTel, String dingdanXiadanTime, String dingdanArrivedTime, String dingdanOverTime, Float dingdanTotalRmb, String dingdanUserName, String dingdanZhuangtai, String dingdanImgSrc, Integer dingdanUserId, String dingdanBeiyong1, String dingdanBeiyong2, String dingdanBeiyong3, String dingdanBeiyong4, String dingdanBianhao, Integer dingdanGoodsnum, String dingdanShouliren, Integer dingdanPeisongrenid, String dingdanBeiyong5) {
		this.dingdanId = dingdanId;
		this.dingdanName = dingdanName;
		this.dingdanDizhi = dingdanDizhi;
		this.dingdanTel = dingdanTel;
		this.dingdanXiadanTime = dingdanXiadanTime;
		this.dingdanArrivedTime = dingdanArrivedTime;
		this.dingdanOverTime = dingdanOverTime;
		this.dingdanTotalRmb = dingdanTotalRmb;
		this.dingdanUserName = dingdanUserName;
		this.dingdanZhuangtai = dingdanZhuangtai;
		this.dingdanImgSrc = dingdanImgSrc;
		this.dingdanUserId = dingdanUserId;
		this.dingdanBeiyong1 = dingdanBeiyong1;
		this.dingdanBeiyong2 = dingdanBeiyong2;
		this.dingdanBeiyong3 = dingdanBeiyong3;
		this.dingdanBeiyong4 = dingdanBeiyong4;
		this.dingdanBianhao = dingdanBianhao;
		this.dingdanGoodsnum = dingdanGoodsnum;
		this.dingdanShouliren = dingdanShouliren;
		this.dingdanPeisongrenid = dingdanPeisongrenid;
		this.dingdanBeiyong5 = dingdanBeiyong5;
	}

	public String getDingdanBianhao() {
		return dingdanBianhao;
	}

	public void setDingdanBianhao(String dingdanBianhao) {
		this.dingdanBianhao = dingdanBianhao;
	}

	public Integer getDingdanGoodsnum() {
		return dingdanGoodsnum;
	}

	public void setDingdanGoodsnum(Integer dingdanGoodsnum) {
		this.dingdanGoodsnum = dingdanGoodsnum;
	}

	public String getDingdanShouliren() {
		return dingdanShouliren;
	}

	public void setDingdanShouliren(String dingdanShouliren) {
		this.dingdanShouliren = dingdanShouliren;
	}

	public Integer getDingdanPeisongrenid() {
		return dingdanPeisongrenid;
	}

	public void setDingdanPeisongrenid(Integer dingdanPeisongrenid) {
		this.dingdanPeisongrenid = dingdanPeisongrenid;
	}

	public String getDingdanBeiyong5() {
		return dingdanBeiyong5;
	}

	public void setDingdanBeiyong5(String dingdanBeiyong5) {
		this.dingdanBeiyong5 = dingdanBeiyong5;
	}

	// Property accessors

	public Integer getDingdanId() {
		return this.dingdanId;
	}

	public void setDingdanId(Integer dingdanId) {
		this.dingdanId = dingdanId;
	}

	public String getDingdanName() {
		return this.dingdanName;
	}

	public void setDingdanName(String dingdanName) {
		this.dingdanName = dingdanName;
	}

	public String getDingdanDizhi() {
		return this.dingdanDizhi;
	}

	public void setDingdanDizhi(String dingdanDizhi) {
		this.dingdanDizhi = dingdanDizhi;
	}

	public String getDingdanTel() {
		return this.dingdanTel;
	}

	public void setDingdanTel(String dingdanTel) {
		this.dingdanTel = dingdanTel;
	}

	public String getDingdanXiadanTime() {
		return this.dingdanXiadanTime;
	}

	public void setDingdanXiadanTime(String dingdanXiadanTime) {
		this.dingdanXiadanTime = dingdanXiadanTime;
	}

	public String getDingdanArrivedTime() {
		return this.dingdanArrivedTime;
	}

	public void setDingdanArrivedTime(String dingdanArrivedTime) {
		this.dingdanArrivedTime = dingdanArrivedTime;
	}

	public String getDingdanOverTime() {
		return this.dingdanOverTime;
	}

	public void setDingdanOverTime(String dingdanOverTime) {
		this.dingdanOverTime = dingdanOverTime;
	}

	public Float getDingdanTotalRmb() {
		return this.dingdanTotalRmb;
	}

	public void setDingdanTotalRmb(Float dingdanTotalRmb) {
		this.dingdanTotalRmb = dingdanTotalRmb;
	}

	public String getDingdanUserName() {
		return this.dingdanUserName;
	}

	public void setDingdanUserName(String dingdanUserName) {
		this.dingdanUserName = dingdanUserName;
	}

	public String getDingdanZhuangtai() {
		return this.dingdanZhuangtai;
	}

	public void setDingdanZhuangtai(String dingdanZhuangtai) {
		this.dingdanZhuangtai = dingdanZhuangtai;
	}

	public String getDingdanImgSrc() {
		return this.dingdanImgSrc;
	}

	public void setDingdanImgSrc(String dingdanImgSrc) {
		this.dingdanImgSrc = dingdanImgSrc;
	}

	public Integer getDingdanUserId() {
		return this.dingdanUserId;
	}

	public void setDingdanUserId(Integer dingdanUserId) {
		this.dingdanUserId = dingdanUserId;
	}

	public String getDingdanBeiyong1() {
		return this.dingdanBeiyong1;
	}

	public void setDingdanBeiyong1(String dingdanBeiyong1) {
		this.dingdanBeiyong1 = dingdanBeiyong1;
	}

	public String getDingdanBeiyong2() {
		return this.dingdanBeiyong2;
	}

	public void setDingdanBeiyong2(String dingdanBeiyong2) {
		this.dingdanBeiyong2 = dingdanBeiyong2;
	}

	public String getDingdanBeiyong3() {
		return this.dingdanBeiyong3;
	}

	public void setDingdanBeiyong3(String dingdanBeiyong3) {
		this.dingdanBeiyong3 = dingdanBeiyong3;
	}

	public String getDingdanBeiyong4() {
		return this.dingdanBeiyong4;
	}

	public void setDingdanBeiyong4(String dingdanBeiyong4) {
		this.dingdanBeiyong4 = dingdanBeiyong4;
	}

	public List<CxwyMallOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<CxwyMallOrder> orderList) {
		this.orderList = orderList;
	}
	public List<CxwyMallSale> getSaleList() {
		return saleList;
	}

	public void setSaleList(List<CxwyMallSale> saleList) {
		this.saleList = saleList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getDingdanVillage() {
		return dingdanVillage;
	}

	public void setDingdanVillage(String dingdanVillage) {
		this.dingdanVillage = dingdanVillage;
	}

	public String getDingdanPaisongtime() {
		return dingdanPaisongtime;
	}

	public void setDingdanPaisongtime(String dingdanPaisongtime) {
		this.dingdanPaisongtime = dingdanPaisongtime;
	}

	public String getDingdanBeiyong6() {
		return dingdanBeiyong6;
	}

	public void setDingdanBeiyong6(String dingdanBeiyong6) {
		this.dingdanBeiyong6 = dingdanBeiyong6;
	}

	public CxwyMallOrder(Integer dingdanId, String dingdanName, String dingdanDizhi, String dingdanTel, String dingdanXiadanTime, String dingdanArrivedTime, String dingdanOverTime, Float dingdanTotalRmb, String dingdanUserName, String dingdanZhuangtai, String dingdanImgSrc, Integer dingdanUserId, String dingdanBeiyong1, String dingdanBeiyong2, String dingdanBeiyong3, String dingdanBeiyong4, String dingdanVillage, String dingdanBianhao, Integer dingdanGoodsnum, String dingdanShouliren, Integer dingdanPeisongrenid, String dingdanBeiyong5, String dingdanPaisongtime, String dingdanBeiyong6, Integer dingdanYouhuiquanId, String dingdanYouhuijia) {
		this.dingdanId = dingdanId;
		this.dingdanName = dingdanName;
		this.dingdanDizhi = dingdanDizhi;
		this.dingdanTel = dingdanTel;
		this.dingdanXiadanTime = dingdanXiadanTime;
		this.dingdanArrivedTime = dingdanArrivedTime;
		this.dingdanOverTime = dingdanOverTime;
		this.dingdanTotalRmb = dingdanTotalRmb;
		this.dingdanUserName = dingdanUserName;
		this.dingdanZhuangtai = dingdanZhuangtai;
		this.dingdanImgSrc = dingdanImgSrc;
		this.dingdanUserId = dingdanUserId;
		this.dingdanBeiyong1 = dingdanBeiyong1;
		this.dingdanBeiyong2 = dingdanBeiyong2;
		this.dingdanBeiyong3 = dingdanBeiyong3;
		this.dingdanBeiyong4 = dingdanBeiyong4;
		this.dingdanVillage = dingdanVillage;
		this.dingdanBianhao = dingdanBianhao;
		this.dingdanGoodsnum = dingdanGoodsnum;
		this.dingdanShouliren = dingdanShouliren;
		this.dingdanPeisongrenid = dingdanPeisongrenid;
		this.dingdanBeiyong5 = dingdanBeiyong5;
		this.dingdanPaisongtime = dingdanPaisongtime;
		this.dingdanBeiyong6 = dingdanBeiyong6;
		this.dingdanYouhuiquanId = dingdanYouhuiquanId;
		this.dingdanYouhuijia = dingdanYouhuijia;
	}

	public Integer getDingdanYouhuiquanId() {
		return dingdanYouhuiquanId;
	}

	public void setDingdanYouhuiquanId(Integer dingdanYouhuiquanId) {
		this.dingdanYouhuiquanId = dingdanYouhuiquanId;
	}

	public String getDingdanYouhuijia() {
		return dingdanYouhuijia;
	}

	public void setDingdanYouhuijia(String dingdanYouhuijia) {
		this.dingdanYouhuijia = dingdanYouhuijia;
	}

	public String getDingdanPayOrderhao() {
		return dingdanPayOrderhao;
	}

	public void setDingdanPayOrderhao(String dingdanPayOrderhao) {
		this.dingdanPayOrderhao = dingdanPayOrderhao;
	}

	public String getDingdanPayJiaoyihao() {
		return dingdanPayJiaoyihao;
	}

	public void setDingdanPayJiaoyihao(String dingdanPayJiaoyihao) {
		this.dingdanPayJiaoyihao = dingdanPayJiaoyihao;
	}

	@Override
	public String toString() {
		return "CxwyMallOrder{" +
				"orderList=" + orderList +
				", saleList=" + saleList +
				", total=" + total +
				", dingdanId=" + dingdanId +
				", dingdanName='" + dingdanName + '\'' +
				", dingdanDizhi='" + dingdanDizhi + '\'' +
				", dingdanTel='" + dingdanTel + '\'' +
				", dingdanXiadanTime='" + dingdanXiadanTime + '\'' +
				", dingdanArrivedTime='" + dingdanArrivedTime + '\'' +
				", dingdanOverTime='" + dingdanOverTime + '\'' +
				", dingdanTotalRmb=" + dingdanTotalRmb +
				", dingdanUserName='" + dingdanUserName + '\'' +
				", dingdanZhuangtai='" + dingdanZhuangtai + '\'' +
				", dingdanImgSrc='" + dingdanImgSrc + '\'' +
				", dingdanUserId=" + dingdanUserId +
				", dingdanBeiyong1='" + dingdanBeiyong1 + '\'' +
				", dingdanBeiyong2='" + dingdanBeiyong2 + '\'' +
				", dingdanBeiyong3='" + dingdanBeiyong3 + '\'' +
				", dingdanBeiyong4='" + dingdanBeiyong4 + '\'' +
				", dingdanVillage='" + dingdanVillage + '\'' +
				", dingdanBianhao='" + dingdanBianhao + '\'' +
				", dingdanGoodsnum=" + dingdanGoodsnum +
				", dingdanShouliren='" + dingdanShouliren + '\'' +
				", dingdanPeisongrenid=" + dingdanPeisongrenid +
				", dingdanBeiyong5='" + dingdanBeiyong5 + '\'' +
				", dingdanPaisongtime='" + dingdanPaisongtime + '\'' +
				", dingdanBeiyong6='" + dingdanBeiyong6 + '\'' +
				", dingdanYouhuiquanId=" + dingdanYouhuiquanId +
				", dingdanYouhuijia='" + dingdanYouhuijia + '\'' +
				", dingdanPayOrderhao='" + dingdanPayOrderhao + '\'' +
				", dingdanPayJiaoyihao='" + dingdanPayJiaoyihao + '\'' +
				'}';
	}
}