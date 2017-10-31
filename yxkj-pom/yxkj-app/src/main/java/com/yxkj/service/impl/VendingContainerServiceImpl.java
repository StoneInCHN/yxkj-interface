package com.yxkj.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.entity.VendingContainer;
import com.yxkj.dao.VendingContainerDao;
import com.yxkj.service.VendingContainerService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("vendingContainerServiceImpl")
public class VendingContainerServiceImpl extends BaseServiceImpl<VendingContainer, Long>
    implements VendingContainerService {


  private VendingContainerDao vendingContainerDao;

  @Resource(name = "vendingContainerDaoImpl")
  public void setBaseDao(VendingContainerDao vendingContainerDao) {
    super.setBaseDao(vendingContainerDao);
    this.vendingContainerDao = vendingContainerDao;
  }

  @Override
  public VendingContainer getByImei(String deviceNo) {
    return vendingContainerDao.getByImei(deviceNo);
  }
}
