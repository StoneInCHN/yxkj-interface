package com.yxkj.service.impl;

import javax.annotation.Resource;

import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.json.request.MachineInfoRequest;
import org.springframework.stereotype.Service;

import com.yxkj.dao.VendingContainerDao;
import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.VendingContainerService;

@Service("vendingContainerServiceImpl")
public class VendingContainerServiceImpl extends BaseServiceImpl<VendingContainer, Long>
    implements VendingContainerService {

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

  @Override
  public void initMachineStatus(MachineInfoRequest request) {
    VendingContainer vendingContainer = vendingContainerDao.getByImei(request.getDeviceNo());
    vendingContainer.setVolume(request.getVolume());

    vendingContainerDao.merge(vendingContainer);
  }

  @Override
  public void updateConnectStatus(MachineInfoRequest request) {
    VendingContainer vendingContainer = vendingContainerDao.getByImei(request.getDeviceNo());
    vendingContainer.setStatus(request.getConnectStatus() ? CommonEnum.CommonStatus.ACITVE
        : CommonEnum.CommonStatus.INACTIVE);

    vendingContainerDao.merge(vendingContainer);
  }
}
