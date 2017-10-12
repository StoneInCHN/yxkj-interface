package com.yxkj.dao; 
import com.yxkj.entity.ContainerKeeper;
import com.yxkj.framework.dao.BaseDao;

public interface ContainerKeeperDao extends  BaseDao<ContainerKeeper,Long>{

	void updatePassword(String cellPhoneNum, String password);
}