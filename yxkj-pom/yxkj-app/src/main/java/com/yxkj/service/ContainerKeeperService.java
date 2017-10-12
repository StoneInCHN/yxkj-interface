package com.yxkj.service; 

import com.yxkj.entity.ContainerKeeper;
import com.yxkj.framework.service.BaseService;

public interface ContainerKeeperService extends BaseService<ContainerKeeper,Long>{

	ContainerKeeper findByCellPhoneNum(String cellPhoneNum);
	
	void resetPassword(String cellPhoneNum, String password);

}