package com.yxkj.service; 

import com.yxkj.entity.ContainerCategory;
import com.yxkj.framework.service.BaseService;

public interface ContainerCategoryService extends BaseService<ContainerCategory,Long>{
	/**
	 * 获取中控 货柜类型
	 * @return
	 */
	ContainerCategory getCentralCategory();

}