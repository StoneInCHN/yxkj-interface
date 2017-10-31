package com.yxkj.json.admin.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	 * 管家管理的优享空间IDs
	 */
	private Long[] sceneIds = null;

	/**
	 * 物业 所选优享空间对应分润点 列表 
	 * 例如 [{"id":1},{"point":10}]
	 */
	private List<Map<String, Object>> idPoints = new ArrayList<Map<String, Object>>();
	

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

	public List<Map<String, Object>> getIdPoints() {
		return idPoints;
	}

	public void setIdPoints(List<Map<String, Object>> idPoints) {
		this.idPoints = idPoints;
	}

}
