package com.yxkj.shelf.json.admin.request;

import java.util.Date;

import com.yxkj.entity.commonenum.CommonEnum.ShelfOrderStatus;

public class ShelfOrderData {   
	/**
	 * 所属公司编号
	 */
	private String companySn;
	/**
	 * 所属公司名称
	 */
	private String companyName;
	/**
	 * 支付方式
	 */
	private String paymentType;

	/**
	 * 订单状态
	 */
	private ShelfOrderStatus[] status;
	
	
	private Date beginDate;
	
	private Date endDate;

	public String getCompanySn() {
		return companySn;
	}

	public void setCompanySn(String companySn) {
		this.companySn = companySn;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public ShelfOrderStatus[] getStatus() {
		return status;
	}

	public void setStatus(ShelfOrderStatus[] status) {
		this.status = status;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
}
