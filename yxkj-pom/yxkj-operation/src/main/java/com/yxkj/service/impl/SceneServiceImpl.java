package com.yxkj.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxkj.dao.SceneDao;
import com.yxkj.entity.AdMachine;
import com.yxkj.entity.Scene;
import com.yxkj.entity.Sn;
import com.yxkj.entity.Sn.Type;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.admin.bean.SceneData;
import com.yxkj.json.admin.request.SceneRequest;
import com.yxkj.service.AdMachineService;
import com.yxkj.service.AreaService;
import com.yxkj.service.SceneService;
import com.yxkj.service.SnService;

@Service("sceneServiceImpl")
public class SceneServiceImpl extends BaseServiceImpl<Scene, Long> implements SceneService {

  @Resource(name = "sceneDaoImpl")
  private SceneDao sceneDao;
  
  @Resource(name = "areaServiceImpl")
  private AreaService areaService;
  
  @Resource(name = "snServiceImpl")
  private SnService snService;
  
  @Resource(name = "adMachineServiceImpl")
  private AdMachineService adMachineService;

  @Resource(name = "sceneDaoImpl")
  public void setBaseDao(SceneDao sceneDao) {
    super.setBaseDao(sceneDao);
  }


  @Override
  public List<Scene> getByKey(String key) {
    return sceneDao.getByKey(key);
  }


  @Override
  public Scene getSceneEnity(SceneRequest request, Long id) {
	  SceneData data = request.getSceneData();
	  Scene scene = null;
	  if (id == null) {
		  scene = new Scene();
		  scene.setSn(genSceneSn());
	  }else {
		  scene = find(id);
	  	  scene.setSn(data.getSn());
	  }
	  if (scene != null) {
		  scene.setAddress(data.getAddress());
	  	  List<Long> areas = data.getArea();
	  	  if (areas != null && areas.size() > 0) {
	  		  Long areaId = areas.get(areas.size()-1);
	  		scene.setArea(areaService.find(areaId));
		  }
	  	  scene.setName(data.getName());
	  	  scene.setOpenTime(data.getOpenTime());
	  	  scene.setLongitude(data.getLongitude());
	  	  scene.setLatitude(data.getLatitude());
	  	  scene.setHasStore(data.getHasStore());
	  }
	  return scene;
  }


  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void saveScene(Scene scene) {
	save(scene);
	AdMachine adMachine = new AdMachine();
	adMachine.setScene(scene);
	adMachineService.save(adMachine);
  }


  @Override
  public String genSceneSn() {
	  String snStr = "";
	  List<Filter> filters = new ArrayList<Filter>();
	  filters.add(Filter.eq("type", Type.SCENE_SN));
	  Sn sn = snService.findFirst(filters, null);
	  snStr = sn.getLastValue().toString();
	  while (snStr.length() < 10) {
		  snStr = "0"+snStr;
	  }
	  snStr = "1"+snStr;
	  return snStr;
  }
}
