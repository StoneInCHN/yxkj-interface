package com.yxkj.service.impl; 

import java.util.HashSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

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
		}else {
			keeper = find(id);
			if (keeper != null) {
				keeper.setAccountStatus(AccountStatus.ACTIVED);
				keeper.setCellPhoneNum(request.getCellPhoneNum());
				keeper.setRealName(request.getRealName());
				keeper.setScenes(new HashSet<Scene>());
				for (Long sceneId : request.getSceneIds()) {
					Scene scene = sceneService.find(sceneId);
					keeper.getScenes().add(scene);
				}
			}
		}
		return keeper;
	}
}