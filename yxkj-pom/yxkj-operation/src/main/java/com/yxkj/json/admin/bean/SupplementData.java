package com.yxkj.json.admin.bean;

import java.util.Date;

public class SupplementData {  
	/**
	 * 商品编号(条码)
	 */
	private String goodsSn;
	/**
	 * 商品名称
	 */
	private String goodName;
	/**
	 * 优享空间ID(场景ID)
	 */
	private Long sceneId;
	/**
	 * 开始日期
	 */
	private Date startDate;
	/**
	 * 结束日期
	 */
	private Date endDate;
	
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public Long getSceneId() {
		return sceneId;
	}
	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
