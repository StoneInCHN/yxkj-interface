package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ContainerKeeper;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.ContainerKeeperDao;
@Repository("containerKeeperDaoImpl")
public class ContainerKeeperDaoImpl extends  BaseDaoImpl<ContainerKeeper,Long> implements ContainerKeeperDao {

}