package com.yxkj.service.impl; 


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.entity.ContainerKeeper;
import com.yxkj.dao.ContainerKeeperDao;
import com.yxkj.service.ContainerKeeperService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("containerKeeperServiceImpl")
public class ContainerKeeperServiceImpl extends BaseServiceImpl<ContainerKeeper,Long> implements ContainerKeeperService {

	  @Resource(name="containerKeeperDaoImpl")
	  private ContainerKeeperDao containerKeeperDao;
	
      @Resource(name="containerKeeperDaoImpl")
      public void setBaseDao(ContainerKeeperDao containerKeeperDao) {
         super.setBaseDao(containerKeeperDao);
      }
      
    @Override
	public ContainerKeeper findByCellPhoneNum(String cellPhoneNum) {
  		return containerKeeperDao.findByCellPhoneNum(cellPhoneNum);
  	}

	@Override
	public void resetPassword(String cellPhoneNum, String password) {
		containerKeeperDao.updatePassword(cellPhoneNum, password);
	}

}