package com.yxkj.service.impl;

import javax.annotation.Resource;

import com.yxkj.common.client.ReceiverClient;
import com.yxkj.dao.SceneDao;
import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.filter.Filter;
import com.yxkj.json.admin.request.AdMachineRequest;
import com.yxkj.service.CmdService;
import org.springframework.stereotype.Service;

import com.yxkj.entity.AdMachine;
import com.yxkj.dao.AdMachineDao;
import com.yxkj.service.AdMachineService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("adMachineServiceImpl")
public class AdMachineServiceImpl extends BaseServiceImpl<AdMachine, Long>
    implements AdMachineService {


  private AdMachineDao adMachineDao;

  @Resource(name = "cmdServiceImpl")
  private CmdService cmdService;

  @Resource(name = "adMachineDaoImpl")
  public void setBaseDao(AdMachineDao adMachineDao) {
    super.setBaseDao(adMachineDao);
    this.adMachineDao = adMachineDao;
  }

  @Override
  public AdMachine updateAdMachine(AdMachineRequest request) {
    AdMachine oldMachine = adMachineDao.find(request.getId());
    Map<String, String> contentMap = new HashMap<>();
    if (request.getAdvA() != null) {
      oldMachine.setAdvA(request.getAdvA());
      contentMap.put("video_top", request.getAdvA());
    }
    if (request.getAdvB() != null) {
      oldMachine.setAdvB(request.getAdvB());
      contentMap.put("video_bottom", request.getAdvB());
    }
    if (request.getAdvC() != null) {
      oldMachine.setAdvC(request.getAdvC());
      contentMap.put("img_left", request.getAdvC());
    }
    if (request.getAdvD() != null) {
      oldMachine.setAdvD(request.getAdvD());
      contentMap.put("img_center", request.getAdvD());
    }
    if (request.getAdvE() != null) {
      oldMachine.setAdvE(request.getAdvE());
      contentMap.put("img_right", request.getAdvE());
    }

    AdMachine newAdMachine = adMachineDao.merge(oldMachine);
    for (VendingContainer vendingContainer : newAdMachine.getScene().getVendingContainer()) {
      if (vendingContainer.getParent() == null) {
        String deviceNo = vendingContainer.getSn();
        cmdService.updateAdv(deviceNo, contentMap);
      }
    }

    return newAdMachine;
  }

  @Override
  public void batchUpdateAdMachine(AdMachineRequest request) {

    List<Filter> filters = new ArrayList<>();
    Filter sceneFilter =
        new Filter("scene.id", Filter.Operator.in, request.getSceneIds().toArray());

    filters.add(sceneFilter);
    List<AdMachine> adMachineList = adMachineDao.findList(null, null, filters, null);
    Map<String, String> contentMap = new HashMap<>();
    for (AdMachine adMachine : adMachineList) {

      if (request.getAdvA() != null) {
        adMachine.setAdvA(request.getAdvA());
        contentMap.put("video_top", request.getAdvA());
      }
      if (request.getAdvB() != null) {
        adMachine.setAdvB(request.getAdvB());
        contentMap.put("video_bottom", request.getAdvB());
      }
      if (request.getAdvC() != null) {
        adMachine.setAdvC(request.getAdvC());
        contentMap.put("img_left", request.getAdvC());
      }
      if (request.getAdvD() != null) {
        adMachine.setAdvD(request.getAdvD());
        contentMap.put("img_center", request.getAdvD());
      }
      if (request.getAdvE() != null) {
        adMachine.setAdvE(request.getAdvE());
        contentMap.put("img_right", request.getAdvE());
      }
      for (VendingContainer vendingContainer : adMachine.getScene().getVendingContainer()) {
        if (vendingContainer.getParent() == null) {
          String deviceNo = vendingContainer.getSn();
          cmdService.updateAdv(deviceNo, contentMap);
        }
      }
    }

    adMachineDao.merge(adMachineList);
  }
}
