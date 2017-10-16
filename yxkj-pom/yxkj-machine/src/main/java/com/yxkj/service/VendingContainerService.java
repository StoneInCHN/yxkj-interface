package com.yxkj.service;

import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.service.BaseService;

public interface VendingContainerService extends BaseService<VendingContainer, Long> {
  /**
   * 根据IMEI查询货柜中控
   * 
   * @param imei
   * @return
   */
  VendingContainer getByImei(String imei);
}
