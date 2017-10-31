package com.yxkj.dao; 
import java.util.List;

import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.dao.BaseDao;

public interface VendingContainerDao extends  BaseDao<VendingContainer,Long>{

  List<Object[]> findCentralVendingContainer(String sceneSn);
  
  List<Object[]> findChildrenVendingContainer(Long id);

  VendingContainer getByImei(String imei);
}
