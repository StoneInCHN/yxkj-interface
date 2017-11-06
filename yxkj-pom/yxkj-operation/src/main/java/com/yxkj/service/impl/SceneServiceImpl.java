package com.yxkj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxkj.common.log.LogUtil;
import com.yxkj.dao.SceneDao;
import com.yxkj.entity.AdMachine;
import com.yxkj.entity.Scene;
import com.yxkj.entity.Sn;
import com.yxkj.entity.Sn.Type;
import com.yxkj.entity.VendingContainer;
import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.admin.bean.SceneData;
import com.yxkj.json.admin.request.SceneRequest;
import com.yxkj.json.admin.response.SceneProfile;
import com.yxkj.service.AdMachineService;
import com.yxkj.service.AreaService;
import com.yxkj.service.ContainerCategoryService;
import com.yxkj.service.SceneService;
import com.yxkj.service.SnService;
import com.yxkj.service.VendingContainerService;

@Service("sceneServiceImpl")
public class SceneServiceImpl extends BaseServiceImpl<Scene, Long> implements SceneService {

  @Resource(name = "sceneDaoImpl")
  private SceneDao sceneDao;

  @Resource(name = "areaServiceImpl")
  private AreaService areaService;

  @Resource(name = "snServiceImpl")
  private SnService snService;

  @Resource(name = "containerCategoryServiceImpl")
  private ContainerCategoryService containerCategoryService;

  @Resource(name = "vendingContainerServiceImpl")
  private VendingContainerService vendingContainerService;

  @Resource(name = "adMachineServiceImpl")
  private AdMachineService adMachineService;

  @Resource(name = "sceneDaoImpl")
  public void setBaseDao(SceneDao sceneDao) {
    super.setBaseDao(sceneDao);
  }


  @Override
  public List<Scene> getByKey(String key, Long pId) {
    return sceneDao.getByKey(key, pId);
  }

  /**
   * 新增场景，场景的中控，中控广告
   * 
   * @param request
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void saveScene(SceneRequest request) {
    // 场景
    Scene scene = new Scene();
    SceneData data = request.getSceneData();
    scene.setSn(genSceneSn());
    scene.setName(data.getName());
    scene.setAddress(data.getAddress());
    List<Long> areas = data.getArea();
    if (areas != null && areas.size() > 0) {
      Long areaId = areas.get(areas.size() - 1);
      scene.setArea(areaService.find(areaId));
    }
    scene.setOpenTime(data.getOpenTime());
    scene.setLongitude(data.getLongitude());
    scene.setLatitude(data.getLatitude());
    scene.setHasStore(data.getHasStore());
    scene.setConCount(1);
    save(scene);
    // 中控
    VendingContainer centralContainer = new VendingContainer();
    centralContainer.setCategory(containerCategoryService.getCentralCategory());
    centralContainer.setParent(null);
    centralContainer.setRebootDay(data.getRebootDay());
    centralContainer.setRebootTime(data.getRebootTime());
    centralContainer.setScene(scene);
    centralContainer.setSn(data.getImei());
    centralContainer.setStatus(CommonStatus.ACITVE);
    centralContainer.setVolume(data.getVolume());
    vendingContainerService.save(centralContainer);
    // 中控广告
    AdMachine adMachine = new AdMachine();
    adMachine.setScene(scene);
    adMachine.setCntrId(centralContainer.getId());
    adMachine.setCntrSn(centralContainer.getSn());
    adMachineService.save(adMachine);
  }

  /**
   * 更新场景，场景的中控
   * 
   * @param request
   */
  @Override
  public void updateScene(SceneRequest request) {
    // 场景
    Scene scene = sceneDao.find(request.getId());
    if (scene != null) {
      SceneData data = request.getSceneData();
      scene.setSn(genSceneSn());
      scene.setName(data.getName());
      scene.setAddress(data.getAddress());
      List<Long> areas = data.getArea();
      if (areas != null && areas.size() > 0) {
        Long areaId = areas.get(areas.size() - 1);
        scene.setArea(areaService.find(areaId));
      }
      scene.setOpenTime(data.getOpenTime());
      scene.setLongitude(data.getLongitude());
      scene.setLatitude(data.getLatitude());
      scene.setHasStore(data.getHasStore());
      update(scene);
      // 中控
      VendingContainer centralContainer = vendingContainerService.getScencCentral(scene);
      centralContainer.setRebootDay(data.getRebootDay());
      centralContainer.setRebootTime(data.getRebootTime());
      centralContainer.setSn(data.getImei());
      centralContainer.setStatus(data.getStatus());
      centralContainer.setVolume(data.getVolume());
      vendingContainerService.update(centralContainer);

      // 普通货柜 服务状态
      List<VendingContainer> containers = scene.getVendingContainer();
      if (containers.size() > 1) {
        for (int i = 0; i < containers.size(); i++) {
          VendingContainer container = containers.get(i);
          container.setStatus(data.getStatus());
          vendingContainerService.update(container);
        }
      }
    } else {
      LogUtil.debug(this.getClass(), "updateScene", "不能通过场景ID找到场景");
    }
  }

  /**
   * 获取某个场景的详情（包括中控信息）
   * 
   * @param id
   * @return
   */
  @Override
  public Map<String, Object> getSceneData(Long id) {
    Map<String, Object> resultMap = new HashMap<String, Object>();
    Scene scene = find(id);
    if (scene != null) {
      resultMap.put("sn", scene.getSn());
      resultMap.put("name", scene.getName());
      resultMap.put("openTime", scene.getOpenTime());
      if (scene.getArea() != null) {
        resultMap.put("area", scene.getArea().getFullName());
      }
      resultMap.put("address", scene.getAddress());
      resultMap.put("hasStore", scene.getHasStore());
      VendingContainer centralContainer = vendingContainerService.getScencCentral(scene);
      if (centralContainer != null) {
        resultMap.put("centralStatus", centralContainer.getStatus());
        resultMap.put("centralVolume", centralContainer.getVolume());
        resultMap.put("centralImei", centralContainer.getSn());
        resultMap.put("centralRebootDay", centralContainer.getRebootDay());
        resultMap.put("centralRebootTime", centralContainer.getRebootTime());
      }
    }
    return resultMap;
  }

  /**
   * 自动生成场景编号(10位数字，1000000001开始)
   * 
   * @return
   */
  @Override
  public String genSceneSn() {
    String snStr = "";
    List<Filter> filters = new ArrayList<Filter>();
    filters.add(Filter.eq("type", Type.SCENE_SN));
    Sn sn = snService.findFirst(filters, null);
    snStr = sn.getLastValue().toString();
    while (snStr.length() < 9) {
      snStr = "0" + snStr;
    }
    snStr = "1" + snStr;
    return snStr;
  }


  @Override
  public List<SceneProfile> getSceneListByKeeper(Long id) {
	return sceneDao.getSceneListByKeeper(id);
  }
  
  @Override
  public List<SceneProfile> getSceneListByProperty(Long id) {
	return sceneDao.getSceneListByProperty(id);
  }
}
