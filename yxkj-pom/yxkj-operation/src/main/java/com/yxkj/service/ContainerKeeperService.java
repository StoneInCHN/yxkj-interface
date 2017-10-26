package com.yxkj.service; 

import com.yxkj.entity.ContainerKeeper;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.admin.request.PropertyKeeperRequest;

public interface ContainerKeeperService extends BaseService<ContainerKeeper,Long>{

	ContainerKeeper getContainerKeeperEntity(PropertyKeeperRequest request,
			Long id);

}