package com.yxld.yxchuangxin.entity;

import android.graphics.Bitmap;

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

	public Bitmap bitmap;

	public ShareInfo() {
	}

	public ShareInfo(String title, String shareCon, String imgUrl) {
		Title = title;
		ShareCon = shareCon;
		ImgUrl = imgUrl;
	}

	public ShareInfo(com.yxld.yxchuangxin.entity.ShareInfo shareInfo, String title, String shareCon, String imgUrl, Bitmap bitmap) {
		ShareInfo = shareInfo;
		Title = title;
		ShareCon = shareCon;
		ImgUrl = imgUrl;
		this.bitmap = bitmap;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getShareCon() {
		return ShareCon;
	}

	public void setShareCon(String shareCon) {
		ShareCon = shareCon;
	}

	public String getImgUrl() {
		return ImgUrl;
	}

	public void setImgUrl(String imgUrl) {
		ImgUrl = imgUrl;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	public String toString() {
		return "ShareInfo{" +
				"ShareInfo=" + ShareInfo +
				", Title='" + Title + '\'' +
				", ShareCon='" + ShareCon + '\'' +
				", ImgUrl='" + ImgUrl + '\'' +
				", bitmap=" + bitmap +
				'}';
	}
}
