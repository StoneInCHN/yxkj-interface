package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ContainerKeeper;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.ContainerKeeperDao;
@Repository("containerKeeperDaoImpl")
public class ContainerKeeperDaoImpl extends  BaseDaoImpl<ContainerKeeper,Long> implements ContainerKeeperDao {

}