package com.yxkj.json.admin.bean;

import java.math.BigDecimal;

public class ContainerChannelData {  
	
	  /**所属货柜Id*/
	  private Long containerId;
	  
	  /**
	   * 货道编号
	   */
	  private String sn;
	
	  /**
	   * 商品Id
	   */
	  private Long goodsId;

	  /**
	   * 商品实际金额
	   */
	  private BigDecimal price;
	  
	  /**
	   * 货道容量
	   */
	  private Integer capacity;

	  /**
	   * 预警值
	   */
	  private Integer warning;
	  
	  /**
	   * 剩余货量
	   */
	  private Integer surplus;

	public Long getContainerId() {
		return containerId;
	}

	public void setContainerId(Long containerId) {
		this.containerId = containerId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getWarning() {
		return warning;
	}

	public void setWarning(Integer warning) {
		this.warning = warning;
	}

	public Integer getSurplus() {
		return surplus;
	}

	public void setSurplus(Integer surplus) {
		this.surplus = surplus;
	}

}
