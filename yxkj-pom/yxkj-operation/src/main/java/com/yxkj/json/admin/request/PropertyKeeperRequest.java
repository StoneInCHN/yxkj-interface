package com.yxkj.json.admin.request;

import com.ibm.icu.math.BigDecimal;
import com.yxkj.json.base.BaseRequest;


public class PropertyKeeperRequest extends BaseRequest{
	/**
	 * 管家姓名
	 */
	private String realName;

	/**
	 * 手机号
	 */
	private String cellPhoneNum;
	
	/**
	 * 分润点(物业)
	 */
	private BigDecimal fenRunPoint;

	/**
	 * 管家管理的优享空间IDs
	 */
	private Long[] sceneIds = null;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCellPhoneNum() {
		return cellPhoneNum;
	}

	public void setCellPhoneNum(String cellPhoneNum) {
		this.cellPhoneNum = cellPhoneNum;
	}

	public Long[] getSceneIds() {
		return sceneIds;
	}

	public void setSceneIds(Long[] sceneIds) {
		this.sceneIds = sceneIds;
	}

	public BigDecimal getFenRunPoint() {
		return fenRunPoint;
	}

	public void setFenRunPoint(BigDecimal fenRunPoint) {
		this.fenRunPoint = fenRunPoint;
	}
	
	
}
