package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.PropertyKeeperSalesReport;
import com.yxkj.dao.PropertyKeeperSalesReportDao;
import com.yxkj.service.PropertyKeeperSalesReportService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("propertyKeeperSalesReportServiceImpl")
public class PropertyKeeperSalesReportServiceImpl extends BaseServiceImpl<PropertyKeeperSalesReport,Long> implements PropertyKeeperSalesReportService {

      @Resource(name="propertyKeeperSalesReportDaoImpl")
      public void setBaseDao(PropertyKeeperSalesReportDao propertyKeeperSalesReportDao) {
         super.setBaseDao(propertyKeeperSalesReportDao);
  }
}