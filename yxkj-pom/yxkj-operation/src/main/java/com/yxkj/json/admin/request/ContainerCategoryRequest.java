package com.yxkj.json.admin.request;

import com.yxkj.entity.commonenum.CommonEnum.CntrTemp;
import com.yxkj.json.base.BaseRequest;

public class ContainerCategoryRequest extends BaseRequest{		

    /**
     * 货柜类别名称
     */
    private String cateName;
	
    /**
     * 单个货道容量
     */
    private Integer capacity;
    /**
     * 货柜温度
     */
    private CntrTemp cTemp;
    
    private String remark;

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


}
