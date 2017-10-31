package com.yxkj.service; 

import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.service.BaseService;

public interface VendingContainerService extends BaseService<VendingContainer,Long>{

		VendingContainer getByImei(String deviceNo);
}
