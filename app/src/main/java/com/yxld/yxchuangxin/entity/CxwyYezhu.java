package com.yxld.yxchuangxin.entity;

import com.yxld.yxchuangxin.base.BaseEntity;

import java.util.List;

/**
 * CxwyYezhu entity. @author MyEclipse Persistence Tools
 */

public class CxwyYezhu extends BaseEntity implements java.io.Serializable {

	private List<CxwyYezhu> rows;

	// Fields
	private Integer yezhuId;//id
	private String yezhuCardNum;//省份证
	private String yezhuName;//业主姓名
	private String yezhuSex;//性别
	private Integer yezhuFanghao;//房号
	private String yezhuLoudong;//楼栋
	private String yezhuLoupan;//楼盘
	private String yezhuDanyuan;//单元
	private String yezhuJiaofangtime;//交房时间
	private String yezhuPhone;//座机
	private String yezhuShouji;//手机
	private String yezhuGuanxi;//关系
	private Integer yezhuParentId;//业主ｉｄ
	private String yezhuRuzhutime;//入住时间
	private String yezhuLikaitime;//离开时间
	private String yezhuSfzSrc1;//身份证照片
	private String yezhuSfzSrc2;//身份证照片
	private String yezhuStatus;//房屋状态
	private String yezhuStatus2;//使用状态
	private String yezhuBianhao;//手机2
	private String yezhuHuxing;//户型
	private String yezhuMianji;//面积
	private String yezhuZuoji2;//座机２
	private String yezhuBiezhu1;//备注１
	private String yezhuBeizhu2;//楼盘ID

	// Constructors

	/** default constructor */
	public CxwyYezhu() {
	}

	/** full constructor */
	public CxwyYezhu(String yezhuCardNum, String yezhuName, String yezhuSex,
			Integer yezhuFanghao, String yezhuLoudong, String yezhuLoupan,
			String yezhuDanyuan, String yezhuJiaofangtime, String yezhuPhone,
			String yezhuShouji, String yezhuGuanxi, Integer yezhuParentId,
			String yezhuRuzhutime, String yezhuLikaitime, String yezhuSfzSrc1,
			String yezhuSfzSrc2, String yezhuStatus, String yezhuStatus2,
			String yezhuBianhao, String yezhuHuxing, String yezhuMianji,
			String yezhuZuoji2, String yezhuBiezhu1, String yezhuBeizhu2) {
		this.yezhuCardNum = yezhuCardNum;
		this.yezhuName = yezhuName;
		this.yezhuSex = yezhuSex;
		this.yezhuFanghao = yezhuFanghao;
		this.yezhuLoudong = yezhuLoudong;
		this.yezhuLoupan = yezhuLoupan;
		this.yezhuDanyuan = yezhuDanyuan;
		this.yezhuJiaofangtime = yezhuJiaofangtime;
		this.yezhuPhone = yezhuPhone;
		this.yezhuShouji = yezhuShouji;
		this.yezhuGuanxi = yezhuGuanxi;
		this.yezhuParentId = yezhuParentId;
		this.yezhuRuzhutime = yezhuRuzhutime;
		this.yezhuLikaitime = yezhuLikaitime;
		this.yezhuSfzSrc1 = yezhuSfzSrc1;
		this.yezhuSfzSrc2 = yezhuSfzSrc2;
		this.yezhuStatus = yezhuStatus;
		this.yezhuStatus2 = yezhuStatus2;
		this.yezhuBianhao = yezhuBianhao;
		this.yezhuHuxing = yezhuHuxing;
		this.yezhuMianji = yezhuMianji;
		this.yezhuZuoji2 = yezhuZuoji2;
		this.yezhuBiezhu1 = yezhuBiezhu1;
		this.yezhuBeizhu2 = yezhuBeizhu2;
	}

	// Property accessors

	public Integer getYezhuId() {
		return this.yezhuId;
	}

	public void setYezhuId(Integer yezhuId) {
		this.yezhuId = yezhuId;
	}

	public String getYezhuCardNum() {
		return this.yezhuCardNum;
	}

	public void setYezhuCardNum(String yezhuCardNum) {
		this.yezhuCardNum = yezhuCardNum;
	}

	public String getYezhuName() {
		return this.yezhuName;
	}

	public void setYezhuName(String yezhuName) {
		this.yezhuName = yezhuName;
	}

	public String getYezhuSex() {
		return this.yezhuSex;
	}

	public void setYezhuSex(String yezhuSex) {
		this.yezhuSex = yezhuSex;
	}

	public Integer getYezhuFanghao() {
		return this.yezhuFanghao;
	}

	public void setYezhuFanghao(Integer yezhuFanghao) {
		this.yezhuFanghao = yezhuFanghao;
	}

	public String getYezhuLoudong() {
		return this.yezhuLoudong;
	}

	public void setYezhuLoudong(String yezhuLoudong) {
		this.yezhuLoudong = yezhuLoudong;
	}

	public String getYezhuLoupan() {
		return this.yezhuLoupan;
	}

	public void setYezhuLoupan(String yezhuLoupan) {
		this.yezhuLoupan = yezhuLoupan;
	}

	public String getYezhuDanyuan() {
		return this.yezhuDanyuan;
	}

	public void setYezhuDanyuan(String yezhuDanyuan) {
		this.yezhuDanyuan = yezhuDanyuan;
	}

	public String getYezhuJiaofangtime() {
		return this.yezhuJiaofangtime;
	}

	public void setYezhuJiaofangtime(String yezhuJiaofangtime) {
		this.yezhuJiaofangtime = yezhuJiaofangtime;
	}

	public String getYezhuPhone() {
		return this.yezhuPhone;
	}

	public void setYezhuPhone(String yezhuPhone) {
		this.yezhuPhone = yezhuPhone;
	}

	public String getYezhuShouji() {
		return this.yezhuShouji;
	}

	public void setYezhuShouji(String yezhuShouji) {
		this.yezhuShouji = yezhuShouji;
	}

	public String getYezhuGuanxi() {
		return this.yezhuGuanxi;
	}

	public void setYezhuGuanxi(String yezhuGuanxi) {
		this.yezhuGuanxi = yezhuGuanxi;
	}

	public Integer getYezhuParentId() {
		return this.yezhuParentId;
	}

	public void setYezhuParentId(Integer yezhuParentId) {
		this.yezhuParentId = yezhuParentId;
	}

	public String getYezhuRuzhutime() {
		return this.yezhuRuzhutime;
	}

	public void setYezhuRuzhutime(String yezhuRuzhutime) {
		this.yezhuRuzhutime = yezhuRuzhutime;
	}

	public String getYezhuLikaitime() {
		return this.yezhuLikaitime;
	}

	public void setYezhuLikaitime(String yezhuLikaitime) {
		this.yezhuLikaitime = yezhuLikaitime;
	}

	public String getYezhuSfzSrc1() {
		return this.yezhuSfzSrc1;
	}

	public void setYezhuSfzSrc1(String yezhuSfzSrc1) {
		this.yezhuSfzSrc1 = yezhuSfzSrc1;
	}

	public String getYezhuSfzSrc2() {
		return this.yezhuSfzSrc2;
	}

	public void setYezhuSfzSrc2(String yezhuSfzSrc2) {
		this.yezhuSfzSrc2 = yezhuSfzSrc2;
	}

	public String getYezhuStatus() {
		return this.yezhuStatus;
	}

	public void setYezhuStatus(String yezhuStatus) {
		this.yezhuStatus = yezhuStatus;
	}

	public String getYezhuStatus2() {
		return this.yezhuStatus2;
	}

	public void setYezhuStatus2(String yezhuStatus2) {
		this.yezhuStatus2 = yezhuStatus2;
	}

	public String getYezhuBianhao() {
		return this.yezhuBianhao;
	}

	public void setYezhuBianhao(String yezhuBianhao) {
		this.yezhuBianhao = yezhuBianhao;
	}

	public String getYezhuHuxing() {
		return this.yezhuHuxing;
	}

	public void setYezhuHuxing(String yezhuHuxing) {
		this.yezhuHuxing = yezhuHuxing;
	}

	public String getYezhuMianji() {
		return this.yezhuMianji;
	}

	public void setYezhuMianji(String yezhuMianji) {
		this.yezhuMianji = yezhuMianji;
	}

	public String getYezhuZuoji2() {
		return this.yezhuZuoji2;
	}

	public void setYezhuZuoji2(String yezhuZuoji2) {
		this.yezhuZuoji2 = yezhuZuoji2;
	}

	public String getYezhuBiezhu1() {
		return this.yezhuBiezhu1;
	}

	public void setYezhuBiezhu1(String yezhuBiezhu1) {
		this.yezhuBiezhu1 = yezhuBiezhu1;
	}

	public String getYezhuBeizhu2() {
		return this.yezhuBeizhu2;
	}

	public void setYezhuBeizhu2(String yezhuBeizhu2) {
		this.yezhuBeizhu2 = yezhuBeizhu2;
	}

	public List<CxwyYezhu> getRows() {
		return rows;
	}

	public void setRows(List<CxwyYezhu> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "CxwyYezhu [yezhuId=" + yezhuId + ", yezhuCardNum="
				+ yezhuCardNum + ", yezhuName=" + yezhuName + ", yezhuSex="
				+ yezhuSex + ", yezhuFanghao=" + yezhuFanghao
				+ ", yezhuLoudong=" + yezhuLoudong + ", yezhuLoupan="
				+ yezhuLoupan + ", yezhuDanyuan=" + yezhuDanyuan
				+ ", yezhuJiaofangtime=" + yezhuJiaofangtime + ", yezhuPhone="
				+ yezhuPhone + ", yezhuShouji=" + yezhuShouji
				+ ", yezhuGuanxi=" + yezhuGuanxi + ", yezhuParentId="
				+ yezhuParentId + ", yezhuRuzhutime=" + yezhuRuzhutime
				+ ", yezhuLikaitime=" + yezhuLikaitime + ", yezhuSfzSrc1="
				+ yezhuSfzSrc1 + ", yezhuSfzSrc2=" + yezhuSfzSrc2
				+ ", yezhuStatus=" + yezhuStatus + ", yezhuStatus2="
				+ yezhuStatus2 + ", yezhuBianhao=" + yezhuBianhao
				+ ", yezhuHuxing=" + yezhuHuxing + ", yezhuMianji="
				+ yezhuMianji + ", yezhuZuoji2=" + yezhuZuoji2
				+ ", yezhuBiezhu1=" + yezhuBiezhu1 + ", yezhuBeizhu2="
				+ yezhuBeizhu2 + "]";
	}
}