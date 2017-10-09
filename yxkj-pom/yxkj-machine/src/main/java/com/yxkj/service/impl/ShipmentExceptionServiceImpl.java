package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ShipmentException;
import com.yxkj.dao.ShipmentExceptionDao;
import com.yxkj.service.ShipmentExceptionService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("shipmentExceptionServiceImpl")
public class ShipmentExceptionServiceImpl extends BaseServiceImpl<ShipmentException,Long> implements ShipmentExceptionService {

      @Resource(name="shipmentExceptionDaoImpl")
      public void setBaseDao(ShipmentExceptionDao shipmentExceptionDao) {
         super.setBaseDao(shipmentExceptionDao);
  }
}