package com.yxld.yxchuangxin.entity;

import java.util.List;

import com.yxld.yxchuangxin.base.BaseEntity;


/**
 * CxwyBaoxiu entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class CxwyBaoxiu extends BaseEntity implements java.io.Serializable {

	  // Fields    
	private List<CxwyBaoxiu> rows;
	
	private String baoxiuPicture ;          //图片

     private Integer baoxiuId;  //id
     private String baoxiuHouses; //楼盘
     private String baoxiuName; //姓名
     private String baoxiuFloor; //楼栋
     private String baoxiuUnit;  //单元
     private String baoxiuRoom;  //房号
     private String baoxiuDepartment; //部门
     private String baoxiuPost;    //职位
     private String baoxiuPlace;  //报修地点
     private String baoxiuPhone;  //电话
     private String baoxiuLrdate; //录入日期
     private String baoxiuLeixing; //报修内型
     private String baoxiuLutime; //录入时间
     private String baoxiuProperty; //区域性质
     private String baoxiuProject;  //报修项目
     private String baoxiuOrdersn;  //派单人
     private String baoxiuPrincipal; //负责人
     private String baoxiuAuditor;   //总部审核人
     private String baoxiuContract;  //委外单位承修
     private String baoxiuRproject;  //维修项目  
     private String baoxiuBegintime;  //开始时间
     private String baoxiuEndtime;    //结束时间
     private String baoxiuStuffmoney;  //材料费用
     private String baoxiuLabourmoney; //人工费
     private String baoxiuOthermoney;  // 其他
     private String baoxiuRevenue;   // 税收
     private String baoxiuTotal;   //合计
     private String baoxiuXmopinion;  //物业负责人意见
     private String baoxiuXmtime;   //物业意见日期
     private String baoxiuZbopinion; //总部审核人意见
     private String baoxiuZbtime;   //总部日期
     private String baoxiuWxname;   //维修人签字
     private String baoxiuWxtime;  //维修人日期
     private String baoxiuPdmane;  //派单人
     private String baoxiuPdtime;   //派单时间
     private String baoxiuSanitation; //环卫
     private String baoxiuPoliteness; //礼貌
     private String baoxiuCommunicate;//沟通
     private String baoxiuProficiency;//流程熟练度
     private String baoxiuTimeliness; //是否及时
     private String baoxiuDanhao;//单号
     private String baoxiuStatus; //状态 
     private String baoxiuBiezhu1; //备注1 
     private String baoxiuBiezhu2; //备注2
     private String baoxiuBiezhu3;//备注3

    // Constructors

    /** default constructor */
    public CxwyBaoxiu() {
    }
   
    public CxwyBaoxiu(String baoxiuPicture, Integer baoxiuId,
			String baoxiuHouses, String baoxiuName, String baoxiuFloor,
			String baoxiuUnit, String baoxiuRoom, String baoxiuDepartment,
			String baoxiuPost, String baoxiuPlace, String baoxiuPhone,
			String baoxiuLrdate, String baoxiuLeixing, String baoxiuLutime,
			String baoxiuProperty, String baoxiuProject, String baoxiuOrdersn,
			String baoxiuPrincipal, String baoxiuAuditor,
			String baoxiuContract, String baoxiuRproject,
			String baoxiuBegintime, String baoxiuEndtime,
			String baoxiuStuffmoney, String baoxiuLabourmoney,
			String baoxiuOthermoney, String baoxiuRevenue, String baoxiuTotal,
			String baoxiuXmopinion, String baoxiuXmtime,
			String baoxiuZbopinion, String baoxiuZbtime, String baoxiuWxname,
			String baoxiuWxtime, String baoxiuPdmane, String baoxiuPdtime,
			String baoxiuSanitation, String baoxiuPoliteness,
			String baoxiuCommunicate, String baoxiuProficiency,
			String baoxiuTimeliness, String baoxiuDanhao, String baoxiuStatus,
			String baoxiuBiezhu1, String baoxiuBiezhu2, String baoxiuBiezhu3) {
		super();
		this.baoxiuPicture = baoxiuPicture;
		this.baoxiuId = baoxiuId;
		this.baoxiuHouses = baoxiuHouses;
		this.baoxiuName = baoxiuName;
		this.baoxiuFloor = baoxiuFloor;
		this.baoxiuUnit = baoxiuUnit;
		this.baoxiuRoom = baoxiuRoom;
		this.baoxiuDepartment = baoxiuDepartment;
		this.baoxiuPost = baoxiuPost;
		this.baoxiuPlace = baoxiuPlace;
		this.baoxiuPhone = baoxiuPhone;
		this.baoxiuLrdate = baoxiuLrdate;
		this.baoxiuLeixing = baoxiuLeixing;
		this.baoxiuLutime = baoxiuLutime;
		this.baoxiuProperty = baoxiuProperty;
		this.baoxiuProject = baoxiuProject;
		this.baoxiuOrdersn = baoxiuOrdersn;
		this.baoxiuPrincipal = baoxiuPrincipal;
		this.baoxiuAuditor = baoxiuAuditor;
		this.baoxiuContract = baoxiuContract;
		this.baoxiuRproject = baoxiuRproject;
		this.baoxiuBegintime = baoxiuBegintime;
		this.baoxiuEndtime = baoxiuEndtime;
		this.baoxiuStuffmoney = baoxiuStuffmoney;
		this.baoxiuLabourmoney = baoxiuLabourmoney;
		this.baoxiuOthermoney = baoxiuOthermoney;
		this.baoxiuRevenue = baoxiuRevenue;
		this.baoxiuTotal = baoxiuTotal;
		this.baoxiuXmopinion = baoxiuXmopinion;
		this.baoxiuXmtime = baoxiuXmtime;
		this.baoxiuZbopinion = baoxiuZbopinion;
		this.baoxiuZbtime = baoxiuZbtime;
		this.baoxiuWxname = baoxiuWxname;
		this.baoxiuWxtime = baoxiuWxtime;
		this.baoxiuPdmane = baoxiuPdmane;
		this.baoxiuPdtime = baoxiuPdtime;
		this.baoxiuSanitation = baoxiuSanitation;
		this.baoxiuPoliteness = baoxiuPoliteness;
		this.baoxiuCommunicate = baoxiuCommunicate;
		this.baoxiuProficiency = baoxiuProficiency;
		this.baoxiuTimeliness = baoxiuTimeliness;
		this.baoxiuDanhao = baoxiuDanhao;
		this.baoxiuStatus = baoxiuStatus;
		this.baoxiuBiezhu1 = baoxiuBiezhu1;
		this.baoxiuBiezhu2 = baoxiuBiezhu2;
		this.baoxiuBiezhu3 = baoxiuBiezhu3;
	}





	// Property accessors

    public List<CxwyBaoxiu> getRows() {
		return rows;
	}





	public void setRows(List<CxwyBaoxiu> rows) {
		this.rows = rows;
	}





	public String getBaoxiuPicture() {
		return baoxiuPicture;
	}





	public void setBaoxiuPicture(String baoxiuPicture) {
		this.baoxiuPicture = baoxiuPicture;
	}





	public Integer getBaoxiuId() {
        return this.baoxiuId;
    }
    
    public void setBaoxiuId(Integer baoxiuId) {
        this.baoxiuId = baoxiuId;
    }

    public String getBaoxiuHouses() {
        return this.baoxiuHouses;
    }
    
    public void setBaoxiuHouses(String baoxiuHouses) {
        this.baoxiuHouses = baoxiuHouses;
    }

    public String getBaoxiuName() {
        return this.baoxiuName;
    }
    
    public void setBaoxiuName(String baoxiuName) {
        this.baoxiuName = baoxiuName;
    }

    public String getBaoxiuFloor() {
        return this.baoxiuFloor;
    }
    
    public void setBaoxiuFloor(String baoxiuFloor) {
        this.baoxiuFloor = baoxiuFloor;
    }

    public String getBaoxiuUnit() {
        return this.baoxiuUnit;
    }
    
    public void setBaoxiuUnit(String baoxiuUnit) {
        this.baoxiuUnit = baoxiuUnit;
    }

    public String getBaoxiuRoom() {
        return this.baoxiuRoom;
    }
    
    public void setBaoxiuRoom(String baoxiuRoom) {
        this.baoxiuRoom = baoxiuRoom;
    }

    public String getBaoxiuDepartment() {
        return this.baoxiuDepartment;
    }
    
    public void setBaoxiuDepartment(String baoxiuDepartment) {
        this.baoxiuDepartment = baoxiuDepartment;
    }

    public String getBaoxiuPlace() {
        return this.baoxiuPlace;
    }
    
    public void setBaoxiuPlace(String baoxiuPlace) {
        this.baoxiuPlace = baoxiuPlace;
    }

    public String getBaoxiuPhone() {
        return this.baoxiuPhone;
    }
    
    public void setBaoxiuPhone(String baoxiuPhone) {
        this.baoxiuPhone = baoxiuPhone;
    }

    public String getBaoxiuLrdate() {
        return this.baoxiuLrdate;
    }
    
    public void setBaoxiuLrdate(String baoxiuLrdate) {
        this.baoxiuLrdate = baoxiuLrdate;
    }

    public String getBaoxiuLutime() {
        return this.baoxiuLutime;
    }
    
    public void setBaoxiuLutime(String baoxiuLutime) {
        this.baoxiuLutime = baoxiuLutime;
    }

    public String getBaoxiuProperty() {
        return this.baoxiuProperty;
    }
    
    public void setBaoxiuProperty(String baoxiuProperty) {
        this.baoxiuProperty = baoxiuProperty;
    }

    public String getBaoxiuProject() {
        return this.baoxiuProject;
    }
    
    public void setBaoxiuProject(String baoxiuProject) {
        this.baoxiuProject = baoxiuProject;
    }

    public String getBaoxiuOrdersn() {
        return this.baoxiuOrdersn;
    }
    
    public void setBaoxiuOrdersn(String baoxiuOrdersn) {
        this.baoxiuOrdersn = baoxiuOrdersn;
    }

    public String getBaoxiuPrincipal() {
        return this.baoxiuPrincipal;
    }
    
    public void setBaoxiuPrincipal(String baoxiuPrincipal) {
        this.baoxiuPrincipal = baoxiuPrincipal;
    }

    public String getBaoxiuAuditor() {
        return this.baoxiuAuditor;
    }
    
    public void setBaoxiuAuditor(String baoxiuAuditor) {
        this.baoxiuAuditor = baoxiuAuditor;
    }

    public String getBaoxiuContract() {
        return this.baoxiuContract;
    }
    
    public void setBaoxiuContract(String baoxiuContract) {
        this.baoxiuContract = baoxiuContract;
    }

    public String getBaoxiuRproject() {
        return this.baoxiuRproject;
    }
    
    public void setBaoxiuRproject(String baoxiuRproject) {
        this.baoxiuRproject = baoxiuRproject;
    }

    public String getBaoxiuBegintime() {
        return this.baoxiuBegintime;
    }
    
    public void setBaoxiuBegintime(String baoxiuBegintime) {
        this.baoxiuBegintime = baoxiuBegintime;
    }

    public String getBaoxiuEndtime() {
        return this.baoxiuEndtime;
    }
    
    public void setBaoxiuEndtime(String baoxiuEndtime) {
        this.baoxiuEndtime = baoxiuEndtime;
    }

    public String getBaoxiuStuffmoney() {
        return this.baoxiuStuffmoney;
    }
    
    public void setBaoxiuStuffmoney(String baoxiuStuffmoney) {
        this.baoxiuStuffmoney = baoxiuStuffmoney;
    }

    public String getBaoxiuLabourmoney() {
        return this.baoxiuLabourmoney;
    }
    
    public void setBaoxiuLabourmoney(String baoxiuLabourmoney) {
        this.baoxiuLabourmoney = baoxiuLabourmoney;
    }

    public String getBaoxiuOthermoney() {
        return this.baoxiuOthermoney;
    }
    
    public void setBaoxiuOthermoney(String baoxiuOthermoney) {
        this.baoxiuOthermoney = baoxiuOthermoney;
    }

    public String getBaoxiuRevenue() {
        return this.baoxiuRevenue;
    }
    
    public void setBaoxiuRevenue(String baoxiuRevenue) {
        this.baoxiuRevenue = baoxiuRevenue;
    }

    public String getBaoxiuTotal() {
        return this.baoxiuTotal;
    }
    
    public void setBaoxiuTotal(String baoxiuTotal) {
        this.baoxiuTotal = baoxiuTotal;
    }

    public String getBaoxiuXmopinion() {
        return this.baoxiuXmopinion;
    }
    
    public void setBaoxiuXmopinion(String baoxiuXmopinion) {
        this.baoxiuXmopinion = baoxiuXmopinion;
    }

    public String getBaoxiuXmtime() {
        return this.baoxiuXmtime;
    }
    
    public void setBaoxiuXmtime(String baoxiuXmtime) {
        this.baoxiuXmtime = baoxiuXmtime;
    }

    public String getBaoxiuZbopinion() {
        return this.baoxiuZbopinion;
    }
    
    public void setBaoxiuZbopinion(String baoxiuZbopinion) {
        this.baoxiuZbopinion = baoxiuZbopinion;
    }

    public String getBaoxiuZbtime() {
        return this.baoxiuZbtime;
    }
    
    public void setBaoxiuZbtime(String baoxiuZbtime) {
        this.baoxiuZbtime = baoxiuZbtime;
    }

    public String getBaoxiuWxname() {
        return this.baoxiuWxname;
    }
    
    public void setBaoxiuWxname(String baoxiuWxname) {
        this.baoxiuWxname = baoxiuWxname;
    }

    public String getBaoxiuWxtime() {
        return this.baoxiuWxtime;
    }
    
    public void setBaoxiuWxtime(String baoxiuWxtime) {
        this.baoxiuWxtime = baoxiuWxtime;
    }

    public String getBaoxiuPdmane() {
        return this.baoxiuPdmane;
    }
    
    public void setBaoxiuPdmane(String baoxiuPdmane) {
        this.baoxiuPdmane = baoxiuPdmane;
    }

    public String getBaoxiuPdtime() {
        return this.baoxiuPdtime;
    }
    
    public void setBaoxiuPdtime(String baoxiuPdtime) {
        this.baoxiuPdtime = baoxiuPdtime;
    }

    public String getBaoxiuSanitation() {
        return this.baoxiuSanitation;
    }
    
    public void setBaoxiuSanitation(String baoxiuSanitation) {
        this.baoxiuSanitation = baoxiuSanitation;
    }

    public String getBaoxiuPoliteness() {
        return this.baoxiuPoliteness;
    }
    
    public void setBaoxiuPoliteness(String baoxiuPoliteness) {
        this.baoxiuPoliteness = baoxiuPoliteness;
    }

    public String getBaoxiuCommunicate() {
        return this.baoxiuCommunicate;
    }
    
    public void setBaoxiuCommunicate(String baoxiuCommunicate) {
        this.baoxiuCommunicate = baoxiuCommunicate;
    }

    public String getBaoxiuProficiency() {
        return this.baoxiuProficiency;
    }
    
    public void setBaoxiuProficiency(String baoxiuProficiency) {
        this.baoxiuProficiency = baoxiuProficiency;
    }

    public String getBaoxiuTimeliness() {
        return this.baoxiuTimeliness;
    }
    
    public void setBaoxiuTimeliness(String baoxiuTimeliness) {
        this.baoxiuTimeliness = baoxiuTimeliness;
    }

    public String getBaoxiuBiezhu1() {
        return this.baoxiuBiezhu1;
    }
    
    public void setBaoxiuBiezhu1(String baoxiuBiezhu1) {
        this.baoxiuBiezhu1 = baoxiuBiezhu1;
    }

    public String getBaoxiuBiezhu2() {
        return this.baoxiuBiezhu2;
    }
    
    public void setBaoxiuBiezhu2(String baoxiuBiezhu2) {
        this.baoxiuBiezhu2 = baoxiuBiezhu2;
    }

    public String getBaoxiuBiezhu3() {
        return this.baoxiuBiezhu3;
    }
    
    public void setBaoxiuBiezhu3(String baoxiuBiezhu3) {
        this.baoxiuBiezhu3 = baoxiuBiezhu3;
    }


	public String getBaoxiuDanhao() {
		return baoxiuDanhao;
	}


	public void setBaoxiuDanhao(String baoxiuDanhao) {
		this.baoxiuDanhao = baoxiuDanhao;
	}


	public String getBaoxiuStatus() {
		return baoxiuStatus;
	}


	public void setBaoxiuStatus(String baoxiuStatus) {
		this.baoxiuStatus = baoxiuStatus;
	}


	public String getBaoxiuLeixing() {
		return baoxiuLeixing;
	}


	public void setBaoxiuLeixing(String baoxiuLeixing) {
		this.baoxiuLeixing = baoxiuLeixing;
	}


	public String getBaoxiuPost() {
		return baoxiuPost;
	}


	public void setBaoxiuPost(String baoxiuPost) {
		this.baoxiuPost = baoxiuPost;
	}

	@Override
	public String toString() {
		return "CxwyBaoxiu [rows=" + rows + ", baoxiuPicture=" + baoxiuPicture
				+ ", baoxiuId=" + baoxiuId + ", baoxiuHouses=" + baoxiuHouses
				+ ", baoxiuName=" + baoxiuName + ", baoxiuFloor=" + baoxiuFloor
				+ ", baoxiuUnit=" + baoxiuUnit + ", baoxiuRoom=" + baoxiuRoom
				+ ", baoxiuDepartment=" + baoxiuDepartment + ", baoxiuPost="
				+ baoxiuPost + ", baoxiuPlace=" + baoxiuPlace
				+ ", baoxiuPhone=" + baoxiuPhone + ", baoxiuLrdate="
				+ baoxiuLrdate + ", baoxiuLeixing=" + baoxiuLeixing
				+ ", baoxiuLutime=" + baoxiuLutime + ", baoxiuProperty="
				+ baoxiuProperty + ", baoxiuProject=" + baoxiuProject
				+ ", baoxiuOrdersn=" + baoxiuOrdersn + ", baoxiuPrincipal="
				+ baoxiuPrincipal + ", baoxiuAuditor=" + baoxiuAuditor
				+ ", baoxiuContract=" + baoxiuContract + ", baoxiuRproject="
				+ baoxiuRproject + ", baoxiuBegintime=" + baoxiuBegintime
				+ ", baoxiuEndtime=" + baoxiuEndtime + ", baoxiuStuffmoney="
				+ baoxiuStuffmoney + ", baoxiuLabourmoney=" + baoxiuLabourmoney
				+ ", baoxiuOthermoney=" + baoxiuOthermoney + ", baoxiuRevenue="
				+ baoxiuRevenue + ", baoxiuTotal=" + baoxiuTotal
				+ ", baoxiuXmopinion=" + baoxiuXmopinion + ", baoxiuXmtime="
				+ baoxiuXmtime + ", baoxiuZbopinion=" + baoxiuZbopinion
				+ ", baoxiuZbtime=" + baoxiuZbtime + ", baoxiuWxname="
				+ baoxiuWxname + ", baoxiuWxtime=" + baoxiuWxtime
				+ ", baoxiuPdmane=" + baoxiuPdmane + ", baoxiuPdtime="
				+ baoxiuPdtime + ", baoxiuSanitation=" + baoxiuSanitation
				+ ", baoxiuPoliteness=" + baoxiuPoliteness
				+ ", baoxiuCommunicate=" + baoxiuCommunicate
				+ ", baoxiuProficiency=" + baoxiuProficiency
				+ ", baoxiuTimeliness=" + baoxiuTimeliness + ", baoxiuDanhao="
				+ baoxiuDanhao + ", baoxiuStatus=" + baoxiuStatus
				+ ", baoxiuBiezhu1=" + baoxiuBiezhu1 + ", baoxiuBiezhu2="
				+ baoxiuBiezhu2 + ", baoxiuBiezhu3=" + baoxiuBiezhu3
				+ ", status=" + status + ", MSG=" + MSG + "]";
	}

}