package com.yxkj.service.impl; 

import java.util.HashSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxkj.dao.ContainerKeeperDao;
import com.yxkj.entity.ContainerKeeper;
import com.yxkj.entity.Scene;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.admin.request.PropertyKeeperRequest;
import com.yxkj.service.ContainerKeeperService;
import com.yxkj.service.SceneService;
import com.yxkj.utils.GenerateRandom;

@Service("containerKeeperServiceImpl")
public class ContainerKeeperServiceImpl extends BaseServiceImpl<ContainerKeeper,Long> implements ContainerKeeperService {

	@Resource(name = "sceneServiceImpl")
	private SceneService sceneService;
	  
    @Resource(name="containerKeeperDaoImpl")
    public void setBaseDao(ContainerKeeperDao containerKeeperDao) {
         super.setBaseDao(containerKeeperDao);
    }

	@Override
	public ContainerKeeper getContainerKeeperEntity(
			PropertyKeeperRequest request, Long id) {
		ContainerKeeper keeper = null;
		if (id == null) {
			keeper = new ContainerKeeper();
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

			for (Long sceneId : request.getSceneIds()) {
				Scene scene = sceneService.find(sceneId);
				keeper.getScenes().add(scene);
			}
		}
		return keeper;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveKeeper(ContainerKeeper keeper) {
		save(keeper);
		for (Scene scene : keeper.getScenes()) {
			scene.setCntrKeeper(keeper);
			sceneService.update(scene);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateKeeper(PropertyKeeperRequest request) {
  	    ContainerKeeper keeper = find(request.getId());
		keeper.setAccountStatus(AccountStatus.ACTIVED);
		keeper.setCellPhoneNum(request.getCellPhoneNum());
		keeper.setRealName(request.getRealName());
		if (keeper != null) {
			if (keeper.getScenes() != null && keeper.getScenes().size() > 0) {
				for (Scene scene : keeper.getScenes()) {
					scene.setCntrKeeper(null);
					sceneService.update(scene);
				}
			}
			keeper.setScenes(new HashSet<Scene>());
			update(keeper);
			if (request.getSceneIds() != null) {
				for (Long sceneId : request.getSceneIds()) {
					Scene scene = sceneService.find(sceneId);
					scene.setCntrKeeper(keeper);
					sceneService.update(scene);
				}
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteKeeper(Long[] ids) {
		for (Long id : ids) {
			ContainerKeeper keeper = find(id);
			if (keeper != null && keeper.getScenes() != null && keeper.getScenes().size() > 0) {
				for (Scene scene : keeper.getScenes()) {
					scene.setCntrKeeper(null);
					sceneService.update(scene);
				}
			}
			delete(keeper);
		}
	}
}