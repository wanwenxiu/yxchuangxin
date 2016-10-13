package com.yxld.yxchuangxin.entity;

/**
 * CxwyMallProduct entity. @author MyEclipse Persistence Tools
 */

/**
 * @author wwx
 * @ClassName: CxwyMallProduct
 * @Description: 二级分类产品信息
 * @date 2016年3月14日 下午2:47:38
 */
@SuppressWarnings("serial")
public class CxwyMallProduct implements java.io.Serializable {
    // Fields
    private Integer shangpinId;//商品id
    private String shangpinShangpName;//商品名/标题名
    private String shangpinGuige;//规格
    private String shangpinRmb;//单价
    private Integer shangpinHave;//是否缺货0是缺货，1是有货
    private Integer shangpinNum;//商品库存
    private String shangpinBody;//详细介绍
    private Integer shangpinZhuangtai;//状态，是否上架0为是，1为否
    private String shangpinImgSrc1;//图片用；号拼接
    private String shangpinClassicOne;//一级分类
    private String shangpinClassicTwo;//二级分类
    private Integer shangpinClassicShow;//商品首页活动商品区展示，0为是，1为否

    private String shangpinProject;//所属项目
    private String shangpinNight;//是否夜间配送商品 0 否 1是
    private String shangpinUploadTime; //商品上传时间
    private String shangpinBianhao; //商品编号
    private String shangpinJinhuojia; //进货价
    private String shangpinBeiyong5;

//	private String shangpinBeiyong1;//所属项目
//	private String shangpinBeiyong2;//备用
//	private String shangpinBeiyong3;
//	private String shangpinBeiyong4;

    // Constructors

    /**
     * default constructor
     */
    public CxwyMallProduct() {
    }

    public CxwyMallProduct(Integer shangpinId, String shangpinShangpName, String shangpinRmb) {
        super();
        this.shangpinId = shangpinId;
        this.shangpinShangpName = shangpinShangpName;
        this.shangpinRmb = shangpinRmb;
    }

    public CxwyMallProduct(String shangpinShangpName, String shangpinGuige, String shangpinRmb, Integer shangpinHave, Integer shangpinNum, String shangpinBody, Integer shangpinZhuangtai, String shangpinImgSrc1, String shangpinClassicOne, String shangpinClassicTwo, Integer shangpinClassicShow, String shangpinProject, String shangpinNight, String shangpinUploadTime, String shangpinBianhao, String shangpinJinhuojia, String shangpinBeiyong5) {
        this.shangpinShangpName = shangpinShangpName;
        this.shangpinGuige = shangpinGuige;
        this.shangpinRmb = shangpinRmb;
        this.shangpinHave = shangpinHave;
        this.shangpinNum = shangpinNum;
        this.shangpinBody = shangpinBody;
        this.shangpinZhuangtai = shangpinZhuangtai;
        this.shangpinImgSrc1 = shangpinImgSrc1;
        this.shangpinClassicOne = shangpinClassicOne;
        this.shangpinClassicTwo = shangpinClassicTwo;
        this.shangpinClassicShow = shangpinClassicShow;
        this.shangpinProject = shangpinProject;
        this.shangpinNight = shangpinNight;
        this.shangpinUploadTime = shangpinUploadTime;
        this.shangpinBianhao = shangpinBianhao;
        this.shangpinJinhuojia = shangpinJinhuojia;
        this.shangpinBeiyong5 = shangpinBeiyong5;
    }

    public Integer getShangpinId() {
        return shangpinId;
    }

    public void setShangpinId(Integer shangpinId) {
        this.shangpinId = shangpinId;
    }

    public String getShangpinShangpName() {
        return shangpinShangpName;
    }

    public void setShangpinShangpName(String shangpinShangpName) {
        this.shangpinShangpName = shangpinShangpName;
    }

    public String getShangpinGuige() {
        return shangpinGuige;
    }

    public void setShangpinGuige(String shangpinGuige) {
        this.shangpinGuige = shangpinGuige;
    }

    public String getShangpinRmb() {
        return shangpinRmb;
    }

    public void setShangpinRmb(String shangpinRmb) {
        this.shangpinRmb = shangpinRmb;
    }

    public Integer getShangpinHave() {
        return shangpinHave;
    }

    public void setShangpinHave(Integer shangpinHave) {
        this.shangpinHave = shangpinHave;
    }

    public Integer getShangpinNum() {
        return shangpinNum;
    }

    public void setShangpinNum(Integer shangpinNum) {
        this.shangpinNum = shangpinNum;
    }

    public String getShangpinBody() {
        return shangpinBody;
    }

    public void setShangpinBody(String shangpinBody) {
        this.shangpinBody = shangpinBody;
    }

    public Integer getShangpinZhuangtai() {
        return shangpinZhuangtai;
    }

    public void setShangpinZhuangtai(Integer shangpinZhuangtai) {
        this.shangpinZhuangtai = shangpinZhuangtai;
    }

    public String getShangpinImgSrc1() {
        return shangpinImgSrc1;
    }

    public void setShangpinImgSrc1(String shangpinImgSrc1) {
        this.shangpinImgSrc1 = shangpinImgSrc1;
    }

    public String getShangpinClassicOne() {
        return shangpinClassicOne;
    }

    public void setShangpinClassicOne(String shangpinClassicOne) {
        this.shangpinClassicOne = shangpinClassicOne;
    }

    public String getShangpinClassicTwo() {
        return shangpinClassicTwo;
    }

    public void setShangpinClassicTwo(String shangpinClassicTwo) {
        this.shangpinClassicTwo = shangpinClassicTwo;
    }

    public Integer getShangpinClassicShow() {
        return shangpinClassicShow;
    }

    public void setShangpinClassicShow(Integer shangpinClassicShow) {
        this.shangpinClassicShow = shangpinClassicShow;
    }

    public String getShangpinProject() {
        return shangpinProject;
    }

    public void setShangpinProject(String shangpinProject) {
        this.shangpinProject = shangpinProject;
    }

    public String getShangpinNight() {
        return shangpinNight;
    }

    public void setShangpinNight(String shangpinNight) {
        this.shangpinNight = shangpinNight;
    }

    public String getShangpinUploadTime() {
        return shangpinUploadTime;
    }

    public void setShangpinUploadTime(String shangpinUploadTime) {
        this.shangpinUploadTime = shangpinUploadTime;
    }

    public String getShangpinBianhao() {
        return shangpinBianhao;
    }

    public void setShangpinBianhao(String shangpinBianhao) {
        this.shangpinBianhao = shangpinBianhao;
    }

    public String getShangpinJinhuojia() {
        return shangpinJinhuojia;
    }

    public void setShangpinJinhuojia(String shangpinJinhuojia) {
        this.shangpinJinhuojia = shangpinJinhuojia;
    }

    public String getShangpinBeiyong5() {
        return shangpinBeiyong5;
    }

    public void setShangpinBeiyong5(String shangpinBeiyong5) {
        this.shangpinBeiyong5 = shangpinBeiyong5;
    }

    @Override
    public String toString() {
        return "CxwyMallProduct{" +
                "shangpinId=" + shangpinId +
                ", shangpinShangpName='" + shangpinShangpName + '\'' +
                ", shangpinGuige='" + shangpinGuige + '\'' +
                ", shangpinRmb='" + shangpinRmb + '\'' +
                ", shangpinHave=" + shangpinHave +
                ", shangpinNum=" + shangpinNum +
                ", shangpinBody='" + shangpinBody + '\'' +
                ", shangpinZhuangtai=" + shangpinZhuangtai +
                ", shangpinImgSrc1='" + shangpinImgSrc1 + '\'' +
                ", shangpinClassicOne='" + shangpinClassicOne + '\'' +
                ", shangpinClassicTwo='" + shangpinClassicTwo + '\'' +
                ", shangpinClassicShow=" + shangpinClassicShow +
                ", shangpinProject='" + shangpinProject + '\'' +
                ", shangpinNight='" + shangpinNight + '\'' +
                ", shangpinUploadTime='" + shangpinUploadTime + '\'' +
                ", shangpinBianhao='" + shangpinBianhao + '\'' +
                ", shangpinJinhuojia='" + shangpinJinhuojia + '\'' +
                ", shangpinBeiyong5='" + shangpinBeiyong5 + '\'' +
                '}';
    }
}