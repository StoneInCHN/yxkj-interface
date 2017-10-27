package com.yxkj.json.admin.bean;

import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;

public class VendingContainerData {  
	
	/**所属场景Id*/
	private Long sceneId;
	
	/**是否中控*/
	private boolean center;
	
	/** 货柜类型Id */
	private Long categoryId;
	
	/**货柜编号(中控货柜则是imei编号)*/
	private String sn;
	
	/** 货柜状态 */
	private CommonStatus status;
	
    /** 货道总数 */
    private Integer totalChannel;

	public boolean isCenter() {
		return center;
	}

	public void setCenter(boolean center) {
		this.center = center;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public Integer getTotalChannel() {
		return totalChannel;
	}

	public void setTotalChannel(Integer totalChannel) {
		this.totalChannel = totalChannel;
	}

	public Long getSceneId() {
		return sceneId;
	}

	public void setSceneId(Long sceneId) {
		this.sceneId = sceneId;
	}
    
}
