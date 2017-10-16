package com.yxkj.dao;

import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.dao.BaseDao;

public interface VendingContainerDao extends BaseDao<VendingContainer, Long> {

  /**
   * 根据IMEI查询货柜中控
   * 
   * @param imei
   * @return
   */
  VendingContainer getByImei(String imei);
}
