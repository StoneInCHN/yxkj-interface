package com.yxkj.service.impl; 

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxkj.dao.PropertyKeeperDao;
import com.yxkj.entity.PropertyKeeper;
import com.yxkj.entity.Scene;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.admin.request.PropertyKeeperRequest;
import com.yxkj.service.PropertyKeeperService;
import com.yxkj.service.SceneService;
import com.yxkj.utils.GenerateRandom;

@Service("propertyKeeperServiceImpl")
public class PropertyKeeperServiceImpl extends BaseServiceImpl<PropertyKeeper,Long> implements PropertyKeeperService {
	@Resource(name = "sceneServiceImpl")
	private SceneService sceneService;
      
	@Resource(name="propertyKeeperDaoImpl")
    private PropertyKeeperDao propertyKeeperDao;

    @Resource(name="propertyKeeperDaoImpl")
    public void setBaseDao(PropertyKeeperDao propertyKeeperDao) {
       super.setBaseDao(propertyKeeperDao);
    }
      
  	@Override
  	public PropertyKeeper getPropertyKeeperEntity(
  			PropertyKeeperRequest request, Long id) {
  		PropertyKeeper keeper = null;
  		if (id == null) {
  			keeper = new PropertyKeeper();
  			keeper.setAccountStatus(AccountStatus.ACTIVED);
  			keeper.setCellPhoneNum(request.getCellPhoneNum());
  			boolean exists = false;
  		    do {
  			    String newUserName = new GenerateRandom().createPassWord(13);//生成随机用户名
  			    keeper.setUserName(newUserName);
  			    exists = exists(Filter.eq("userName", newUserName));//检查是否存在
  			} while (exists);

  		    String newPwd = new GenerateRandom().createPassWord(10);//生成随机密码
  		    keeper.setLoginPwd(newPwd);
  		    keeper.setRealName(request.getRealName());

  			for (Map<String, Object> idPoint : request.getIdPoints()) {
  				if (idPoint.get("id") != null && idPoint.get("point") != null) {
  	  				Long sceneId = Long.parseLong(idPoint.get("id").toString());
  	  				Scene scene = sceneService.find(sceneId);
  	  				BigDecimal fenRunPoint = new BigDecimal(idPoint.get("point").toString());
  	  			    scene.setFenRunPoint(fenRunPoint);
  	  				keeper.getScenes().add(scene);
				}
  			}
  		}else {
  			keeper = find(id);
  			if (keeper != null) {
  				keeper.setAccountStatus(AccountStatus.ACTIVED);
  				keeper.setCellPhoneNum(request.getCellPhoneNum());
  				keeper.setRealName(request.getRealName());
  				keeper.setScenes(new HashSet<Scene>());
  	  			for (Map<String, Object> idPoint : request.getIdPoints()) {
  	  				if (idPoint.get("id") != null && idPoint.get("point") != null) {
  	  	  				Long sceneId = Long.parseLong(idPoint.get("id").toString());
  	  	  				Scene scene = sceneService.find(sceneId);
  	  	  				BigDecimal fenRunPoint = new BigDecimal(idPoint.get("point").toString());
  	  	  			    scene.setFenRunPoint(fenRunPoint);
  	  	  				keeper.getScenes().add(scene);
  					}
  	  			}
  			}
  		}
  		return keeper;
  	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveKeeper(PropertyKeeper keeper) {
		save(keeper);
		for (Scene scene : keeper.getScenes()) {
			scene.setPropertyKeeper(keeper);
			sceneService.update(scene);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateKeeper(PropertyKeeperRequest request) {
  	    PropertyKeeper keeper = find(request.getId());
		keeper.setAccountStatus(AccountStatus.ACTIVED);
		keeper.setCellPhoneNum(request.getCellPhoneNum());
		keeper.setRealName(request.getRealName());
		if (keeper != null) {
			if (keeper.getScenes() != null && keeper.getScenes().size() > 0) {
				for (Scene scene : keeper.getScenes()) {
					scene.setPropertyKeeper(null);
					scene.setFenRunPoint(null);
					sceneService.update(scene);
				}
			}
			keeper.setScenes(new HashSet<Scene>());
			update(keeper);
	  		for (Map<String, Object> idPoint : request.getIdPoints()) {
  	  				if (idPoint.get("id") != null && idPoint.get("point") != null) {
  	  	  				Long sceneId = Long.parseLong(idPoint.get("id").toString());
  	  	  				Scene scene = sceneService.find(sceneId);
  	  	  				BigDecimal fenRunPoint = new BigDecimal(idPoint.get("point").toString());
  	  	  			    scene.setFenRunPoint(fenRunPoint);
  	  	  			    scene.setPropertyKeeper(keeper);
  	  	  			    sceneService.update(scene);
  					}
  	  		}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteKeeper(Long[] ids) {
		for (Long id : ids) {
			PropertyKeeper keeper = find(id);
			if (keeper != null && keeper.getScenes() != null && keeper.getScenes().size() > 0) {
				for (Scene scene : keeper.getScenes()) {
					scene.setPropertyKeeper(null);
					sceneService.update(scene);
				}
			}
			delete(keeper);
		}
	}
}