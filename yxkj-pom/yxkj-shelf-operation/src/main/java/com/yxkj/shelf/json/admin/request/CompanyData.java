package com.yxkj.shelf.json.admin.request;

import java.util.List;

public class CompanyData {
	/**
	 * 公司编号
	 */
	private String sn;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 公司地址
	 */
	private List<Long> area;
	/**
	 * area.getFullName用于展示
	 */
	private String areaFullName;
	/**
	 * 联系人
	 */
	private String contactPerson;
	/**
	 * 联系电话
	 */
	private String contactPhone;
	/**
	 * 公司全称
	 */
	private String fullName;
	/**
	 * 公司展示名称
	 */
	private String displayName;
	/**
	 * 公司货架情况
	 */
	private List<GoodsShelveRow> goodsShelves;
	/**
	 * 备注
	 */
	private String remark;
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Long> getArea() {
		return area;
	}
	public void setArea(List<Long> area) {
		this.area = area;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public List<GoodsShelveRow> getGoodsShelves() {
		return goodsShelves;
	}
	public void setGoodsShelves(List<GoodsShelveRow> goodsShelves) {
		this.goodsShelves = goodsShelves;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAreaFullName() {
		return areaFullName;
	}
	public void setAreaFullName(String areaFullName) {
		this.areaFullName = areaFullName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
