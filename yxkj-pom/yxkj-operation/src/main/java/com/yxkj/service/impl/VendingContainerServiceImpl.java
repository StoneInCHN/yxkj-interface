package com.yxkj.service.impl; 

import java.util.List;
import java.util.Map;

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxkj.entity.ContainerCategory;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.Scene;
import com.yxkj.entity.VendingContainer;
import com.yxkj.dao.ContainerCategoryDao;
import com.yxkj.dao.ContainerChannelDao;
import com.yxkj.dao.SceneDao;
import com.yxkj.dao.VendingContainerDao;
import com.yxkj.service.SceneService;
import com.yxkj.service.VendingContainerService;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.admin.bean.VendingContainerData;
import com.yxkj.json.admin.request.VendingContainerRequest;

@Service("vendingContainerServiceImpl")
public class VendingContainerServiceImpl extends BaseServiceImpl<VendingContainer,Long> implements VendingContainerService {

	@Resource(name = "vendingContainerDaoImpl")
	private VendingContainerDao vendingContainerDao;
	
	@Resource(name = "sceneDaoImpl")
	private SceneDao sceneDao;
	
	@Resource(name = "containerCategoryDaoImpl")
	private ContainerCategoryDao containerCategoryDao;
	
	@Resource(name = "containerChannelDaoImpl")
	private ContainerChannelDao containerChannelDao;	  
	
    @Resource(name="vendingContainerDaoImpl")
    public void setBaseDao(VendingContainerDao vendingContainerDao) {
         super.setBaseDao(vendingContainerDao);
    }

	@Override
	public List<Map<String, Object>> findListBySceneId(Long sceneId) {
		return vendingContainerDao.findListBySceneId(sceneId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveContainer(VendingContainerRequest request) {
		VendingContainerData data = request.getContainerData();
		Scene scene = sceneDao.find(data.getSceneId());
		ContainerCategory category = containerCategoryDao.find(data.getCategoryId());
		VendingContainer container = new VendingContainer();
		container.setScene(scene);
		container.setCategory(category);
		container.setSn(data.getSn());
		container.setStatus(data.getStatus());
		save(container);
		if (!data.isCenter() && data.getTotalChannel() != null && data.getTotalChannel() > 0) {//普通货柜
			for (int i = 0; i < data.getTotalChannel(); i++) {
				ContainerChannel channel = new ContainerChannel();
				channel.setCntr(container);
				containerChannelDao.persist(channel);
			}
		}		
	}

	@Override
	public void updateContainer(VendingContainerRequest request) {
		VendingContainer container = find(request.getId());
		VendingContainerData data = request.getContainerData();
		ContainerCategory category = containerCategoryDao.find(data.getCategoryId());
		container.setCategory(category);
		container.setSn(data.getSn());
		container.setStatus(data.getStatus());
		update(container);
	}
}