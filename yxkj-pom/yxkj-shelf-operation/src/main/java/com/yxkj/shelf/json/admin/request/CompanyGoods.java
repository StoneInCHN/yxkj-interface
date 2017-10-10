package com.yxkj.shelf.json.admin.request;

import java.util.List;


public class CompanyGoods {
	/**
	 * 公司编号
	 */
	private Long companyId;
		
	/**
	 * 选中的商品Ids
	 */
	private List<Long> selectKeys;
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public List<Long> getSelectKeys() {
		return selectKeys;
	}
	public void setSelectKeys(List<Long> selectKeys) {
		this.selectKeys = selectKeys;
	}



	
}
