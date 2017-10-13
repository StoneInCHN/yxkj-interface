package com.yxkj.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.dao.VendingContainerDao;
import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.VendingContainerService;

@Service("vendingContainerServiceImpl")
public class VendingContainerServiceImpl extends BaseServiceImpl<VendingContainer, Long> implements
    VendingContainerService {

  @Resource(name = "vendingContainerDaoImpl")
  private VendingContainerDao vendingContainerDao;

  @Resource(name = "vendingContainerDaoImpl")
  public void setBaseDao(VendingContainerDao vendingContainerDao) {
    super.setBaseDao(vendingContainerDao);
  }

  @Override
  public VendingContainer getByImei(String imei) {
    return vendingContainerDao.getByImei(imei);
  }
}
