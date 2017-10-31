package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.PropertyKeeperSalesReport;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.PropertyKeeperSalesReportDao;
@Repository("propertyKeeperSalesReportDaoImpl")
public class PropertyKeeperSalesReportDaoImpl extends  BaseDaoImpl<PropertyKeeperSalesReport,Long> implements PropertyKeeperSalesReportDao {

}