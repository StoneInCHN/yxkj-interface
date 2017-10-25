package com.yxkj.service.impl;

import javax.annotation.Resource;

import com.yxkj.json.admin.request.AdMachineRequest;
import org.springframework.stereotype.Service;

import com.yxkj.entity.AdMachine;
import com.yxkj.dao.AdMachineDao;
import com.yxkj.service.AdMachineService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("adMachineServiceImpl")
public class AdMachineServiceImpl extends BaseServiceImpl<AdMachine, Long>
    implements AdMachineService {

  @Resource(name = "adMachineDaoImpl")
  private AdMachineDao adMachineDao;

  @Resource(name = "adMachineDaoImpl")
  public void setBaseDao(AdMachineDao adMachineDao) {
    super.setBaseDao(adMachineDao);
    this.adMachineDao = adMachineDao;
  }

  @Override
  public AdMachine updateAdMachine(AdMachineRequest request) {
    AdMachine oldMachine = adMachineDao.find(request.getId());
    if (request.getAdvA() != null)
      oldMachine.setAdvA(request.getAdvA());
    if (request.getAdvB() != null)
      oldMachine.setAdvB(request.getAdvB());
    if (request.getAdvC() != null)
      oldMachine.setAdvC(request.getAdvC());
    if (request.getAdvD() != null)
      oldMachine.setAdvD(request.getAdvD());
    if (request.getAdvE() != null)
      oldMachine.setAdvE(request.getAdvE());
    return adMachineDao.merge(oldMachine);
  }
}
