package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.PropertyKeeper;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.PropertyKeeperDao;
@Repository("propertyKeeperDaoImpl")
public class PropertyKeeperDaoImpl extends  BaseDaoImpl<PropertyKeeper,Long> implements PropertyKeeperDao {

}