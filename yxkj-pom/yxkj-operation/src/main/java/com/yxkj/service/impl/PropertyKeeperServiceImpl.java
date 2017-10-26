package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.PropertyKeeper;
import com.yxkj.dao.PropertyKeeperDao;
import com.yxkj.service.PropertyKeeperService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("propertyKeeperServiceImpl")
public class PropertyKeeperServiceImpl extends BaseServiceImpl<PropertyKeeper,Long> implements PropertyKeeperService {

      @Resource(name="propertyKeeperDaoImpl")
      private PropertyKeeperDao propertyKeeperDao;

      @Resource(name="propertyKeeperDaoImpl")
      public void setBaseDao(PropertyKeeperDao propertyKeeperDao) {
         super.setBaseDao(propertyKeeperDao);
      }
}