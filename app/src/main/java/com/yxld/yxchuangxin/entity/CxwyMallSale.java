package com.yxld.yxchuangxin.entity;

import com.yxld.yxchuangxin.base.BaseEntity;

import java.util.List;

/**
 * CxwyMallSale entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class CxwyMallSale extends BaseEntity implements java.io.Serializable {

	private List<CxwyMallSale> saleList;
	private CxwyMallOrder order;
	
	// Fields
	private Integer saleId;//id
	private String saleTime;
	private Integer saleShangpNum;//商品id
	private Integer saleDingdanId;//订单id
	private String saleShangpName;//商品名称
	private Integer saleNum;//数量
	private String saleGuige;//规格
	private Float saleTotalRmb;//总金额
	private Float saleOneRmb;//单价
	private String saleImgSrc;//图片
	private String saleUseTime;

	private String saleProject; //下单小区
	private String saleCardId;//暂时存放下单时购物车id
	private String saleOrderState; //订单状态
	private String saleOrderBianhao; //订单编号

	private String saleJinhuojia; //进货价

//	private String saleBeiyong1;//备用字段
//	private String saleBeiyong2;
//	private String saleBeiyong3;
//	private String saleBeiyong4;

	// Constructors

	/** default constructor */
	public CxwyMallSale() {
	}

	public List<CxwyMallSale> getSaleList() {
		return saleList;
	}

	public void setSaleList(List<CxwyMallSale> saleList) {
		this.saleList = saleList;
	}

	public CxwyMallOrder getOrder() {
		return order;
	}

	public void setOrder(CxwyMallOrder order) {
		this.order = order;
	}

	public Integer getSaleId() {
		return saleId;
	}

	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}

	public String getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}

	public Integer getSaleShangpNum() {
		return saleShangpNum;
	}

	public void setSaleShangpNum(Integer saleShangpNum) {
		this.saleShangpNum = saleShangpNum;
	}

	public Integer getSaleDingdanId() {
		return saleDingdanId;
	}

	public void setSaleDingdanId(Integer saleDingdanId) {
		this.saleDingdanId = saleDingdanId;
	}

	public String getSaleShangpName() {
		return saleShangpName;
	}

	public void setSaleShangpName(String saleShangpName) {
		this.saleShangpName = saleShangpName;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}

	public String getSaleGuige() {
		return saleGuige;
	}

	public void setSaleGuige(String saleGuige) {
		this.saleGuige = saleGuige;
	}

	public Float getSaleTotalRmb() {
		return saleTotalRmb;
	}

	public void setSaleTotalRmb(Float saleTotalRmb) {
		this.saleTotalRmb = saleTotalRmb;
	}

	public Float getSaleOneRmb() {
		return saleOneRmb;
	}

	public void setSaleOneRmb(Float saleOneRmb) {
		this.saleOneRmb = saleOneRmb;
	}

	public String getSaleImgSrc() {
		return saleImgSrc;
	}

	public void setSaleImgSrc(String saleImgSrc) {
		this.saleImgSrc = saleImgSrc;
	}

	public String getSaleUseTime() {
		return saleUseTime;
	}

	public void setSaleUseTime(String saleUseTime) {
		this.saleUseTime = saleUseTime;
	}

	public String getSaleProject() {
		return saleProject;
	}

	public void setSaleProject(String saleProject) {
		this.saleProject = saleProject;
	}

	public String getSaleCardId() {
		return saleCardId;
	}

	public void setSaleCardId(String saleCardId) {
		this.saleCardId = saleCardId;
	}

	public String getSaleOrderState() {
		return saleOrderState;
	}

	public void setSaleOrderState(String saleOrderState) {
		this.saleOrderState = saleOrderState;
	}

	public String getSaleOrderBianhao() {
		return saleOrderBianhao;
	}

	public void setSaleOrderBianhao(String saleOrderBianhao) {
		this.saleOrderBianhao = saleOrderBianhao;
	}

	public String getSaleJinhuojia() {
		return saleJinhuojia;
	}

	public void setSaleJinhuojia(String saleJinhuojia) {
		this.saleJinhuojia = saleJinhuojia;
	}

	public CxwyMallSale(String saleTime, Integer saleShangpNum, Integer saleDingdanId, String saleShangpName, Integer saleNum, String saleGuige, Float saleTotalRmb, Float saleOneRmb, String saleImgSrc, String saleUseTime, String saleProject, String saleCardId, String saleOrderState, String saleOrderBianhao, String saleJinhuojia) {
		this.saleTime = saleTime;
		this.saleShangpNum = saleShangpNum;
		this.saleDingdanId = saleDingdanId;
		this.saleShangpName = saleShangpName;
		this.saleNum = saleNum;
		this.saleGuige = saleGuige;
		this.saleTotalRmb = saleTotalRmb;
		this.saleOneRmb = saleOneRmb;
		this.saleImgSrc = saleImgSrc;
		this.saleUseTime = saleUseTime;
		this.saleProject = saleProject;
		this.saleCardId = saleCardId;
		this.saleOrderState = saleOrderState;
		this.saleOrderBianhao = saleOrderBianhao;
		this.saleJinhuojia = saleJinhuojia;
	}

	@Override
	public String toString() {
		return "CxwyMallSale{" +
				"saleList=" + saleList +
				", order=" + order +
				", saleId=" + saleId +
				", saleTime='" + saleTime + '\'' +
				", saleShangpNum=" + saleShangpNum +
				", saleDingdanId=" + saleDingdanId +
				", saleShangpName='" + saleShangpName + '\'' +
				", saleNum=" + saleNum +
				", saleGuige='" + saleGuige + '\'' +
				", saleTotalRmb=" + saleTotalRmb +
				", saleOneRmb=" + saleOneRmb +
				", saleImgSrc='" + saleImgSrc + '\'' +
				", saleUseTime='" + saleUseTime + '\'' +
				", saleProject='" + saleProject + '\'' +
				", saleCardId='" + saleCardId + '\'' +
				", saleOrderState='" + saleOrderState + '\'' +
				", saleOrderBianhao='" + saleOrderBianhao + '\'' +
				", saleJinhuojia='" + saleJinhuojia + '\'' +
				'}';
	}
}