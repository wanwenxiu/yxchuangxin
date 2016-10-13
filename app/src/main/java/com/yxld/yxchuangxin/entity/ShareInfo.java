package com.yxld.yxchuangxin.entity;

/**
 * @author WWX
 * 分享
 */
public class ShareInfo {
	public ShareInfo ShareInfo;

	/** 标题*/
	public String Title;

	/** 内容*/
	public String ShareCon;

	/** 图片路径*/
	public String ImgUrl;

	public ShareInfo() {
	}

	public ShareInfo(String title, String shareCon, String imgUrl) {
		Title = title;
		ShareCon = shareCon;
		ImgUrl = imgUrl;
	}

	@Override
	public String toString() {
		return "ShareInfo{" +
				"ShareInfo=" + ShareInfo +
				", Title='" + Title + '\'' +
				", ShareCon='" + ShareCon + '\'' +
				", ImgUrl='" + ImgUrl + '\'' +
				'}';
	}
}
