package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ShipmentException;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.ShipmentExceptionDao;
@Repository("shipmentExceptionDaoImpl")
public class ShipmentExceptionDaoImpl extends  BaseDaoImpl<ShipmentException,Long> implements ShipmentExceptionDao {

}