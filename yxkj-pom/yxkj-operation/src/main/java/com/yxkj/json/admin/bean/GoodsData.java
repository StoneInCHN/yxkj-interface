package com.yxkj.json.admin.bean;

import java.math.BigDecimal;

public class GoodsData {   	
	/**
	 * 商品编号
	 */
	private String sn;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 净含量
	 */
	private String spec;
	/**
	 * 成本价
	 */
	private BigDecimal costPrice;
	/**
	 * 默认售价
	 */
	private BigDecimal salePrice;
	/**
	 * 商品图片链接
	 */
	private String url;
	
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

	
}
