package com.yxkj.service.impl;

import javax.annotation.Resource;

import com.yxkj.dao.SceneDao;
import com.yxkj.framework.filter.Filter;
import com.yxkj.json.admin.request.AdMachineRequest;
import org.springframework.stereotype.Service;

import com.yxkj.entity.AdMachine;
import com.yxkj.dao.AdMachineDao;
import com.yxkj.service.AdMachineService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Service("adMachineServiceImpl")
public class AdMachineServiceImpl extends BaseServiceImpl<AdMachine, Long>
    implements AdMachineService {


  private AdMachineDao adMachineDao;

  @Resource(name = "sceneDaoImpl")
  private SceneDao sceneDao;

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

  @Override
  public void batchUpdateAdMachine(AdMachineRequest request) {

    List<Filter> filters = new ArrayList<>();
    Filter sceneFilter = new Filter("scene.id", Filter.Operator.in, request.getSceneIds().toArray());

    filters.add(sceneFilter);
    List<AdMachine> adMachineList = adMachineDao.findList(null, null, filters, null);

    for (AdMachine adMachine : adMachineList) {

      if (request.getAdvA() != null) {
        adMachine.setAdvA(request.getAdvA());
      }
      if (request.getAdvB() != null) {
        adMachine.setAdvB(request.getAdvB());
      }
      if (request.getAdvC() != null) {
        adMachine.setAdvC(request.getAdvC());
      }
      if (request.getAdvD() != null) {
        adMachine.setAdvD(request.getAdvD());
      }
      if (request.getAdvE() != null) {
        adMachine.setAdvE(request.getAdvE());
      }

    }

    adMachineDao.merge(adMachineList);
  }
}
