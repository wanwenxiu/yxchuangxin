package com.yxld.yxchuangxin.entity;

/**
 * @ClassName: SearchHistory 
 * @Description: 搜索历史
 * @author wwx
 * @date 2016年3月16日 上午11:03:47 
 */
public class SearchHistoryEntity {
	/** 用户Id*/
	private String u_id;
	/** 搜索字段*/
	private String u_search;


	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getU_search() {
		return u_search;
	}

	public void setU_search(String u_search) {
		this.u_search = u_search;
	}

	public SearchHistoryEntity(String u_id, String u_search) {
		super();

		this.u_id = u_id;
		this.u_search = u_search;
	}

	
	@Override
	public boolean equals(Object obj) {
		SearchHistoryEntity s = (SearchHistoryEntity) obj;
		return u_id.equals(s.u_id) && u_search.equals(u_search);
	}

	@Override
	public int hashCode() {
		String in = u_id + u_search;
		return in.hashCode();
	}

	
	@Override
	public String toString() {
		return "SearchHistory [u_id=" + u_id + ", u_search=" + u_search + "]";
	}
	
}
