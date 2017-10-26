package com.yxkj.service; 

import com.yxkj.entity.PropertyKeeper;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.admin.request.PropertyKeeperRequest;

public interface PropertyKeeperService extends BaseService<PropertyKeeper,Long>{

	PropertyKeeper getPropertyKeeperEntity(PropertyKeeperRequest request,
			Long id);
	void saveKeeper(PropertyKeeper keeper);

	void updateKeeper(PropertyKeeperRequest request);

	void deleteKeeper(Long[] ids);
}