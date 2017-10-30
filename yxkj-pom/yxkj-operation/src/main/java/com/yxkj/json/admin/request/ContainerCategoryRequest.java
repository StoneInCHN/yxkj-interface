package com.yxkj.json.admin.request;

import com.yxkj.entity.commonenum.CommonEnum.CntrTemp;
import com.yxkj.entity.commonenum.CommonEnum.CntrType;
import com.yxkj.json.base.BaseRequest;

public class ContainerCategoryRequest extends BaseRequest{		

    /**
     * 货柜类别名称
     */
    private String cateName;
    
    /**
     * 货道总数(物理货道数)
     */
    private Integer totalChannel;
	
    /**
     * 单个货道容量
     */
    private Integer capacity;
    /**
     * 货柜温度
     */
    private CntrTemp cTemp;    
    /**
     * 货柜类型
     */
    private CntrType cntrType;
    
    /**
     * 备注
     */
    private String remark;

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Integer getTotalChannel() {
		return totalChannel;
	}

	public void setTotalChannel(Integer totalChannel) {
		this.totalChannel = totalChannel;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public CntrTemp getcTemp() {
		return cTemp;
	}

	public void setcTemp(CntrTemp cTemp) {
		this.cTemp = cTemp;
	}

	public CntrType getCntrType() {
		return cntrType;
	}

	public void setCntrType(CntrType cntrType) {
		this.cntrType = cntrType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
