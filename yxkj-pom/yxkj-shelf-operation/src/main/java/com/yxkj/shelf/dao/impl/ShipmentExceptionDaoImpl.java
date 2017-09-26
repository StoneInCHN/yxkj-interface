package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ShipmentException;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.ShipmentExceptionDao;
@Repository("shipmentExceptionDaoImpl")
public class ShipmentExceptionDaoImpl extends  BaseDaoImpl<ShipmentException,Long> implements ShipmentExceptionDao {

}