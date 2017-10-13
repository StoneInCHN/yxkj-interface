package com.yxkj.shelf.json.admin.request;

import com.yxkj.entity.commonenum.CommonEnum.ShelfOrderStatus;

public class ShelfOrderData {   	
	/**
	 * 订单编号
	 */
	private String sn;
	
	/**
	 * 支付方式
	 */
	private String paymentType;

	/**
	 * 订单状态
	 */
	private ShelfOrderStatus[] status;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	
}
