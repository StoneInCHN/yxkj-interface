package com.yxkj.service.impl; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource; 
import javax.print.attribute.standard.Fidelity;

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
import com.yxkj.framework.filter.Filter;
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

	/**
	 * 新增货柜（普通货柜）
	 */
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
		container.setWarningPer(data.getWarningPer());
		VendingContainer centralContainer = getScencCentral(scene);
		container.setParent(centralContainer);
		save(container);
		if (category != null && category.getTotalChannel() != null && category.getTotalChannel() > 0) {
			for (int i = 0; i < category.getTotalChannel(); i++) {
				ContainerChannel channel = new ContainerChannel();
				channel.setCntr(container);
	      		channel.setOfflineLocalLock(0);
	      		channel.setOfflineRemoteLock(0);
	      		channel.setOnlineLock(0);
				containerChannelDao.persist(channel);
			}
		}		
	}
	/**
	 * 更新货柜（普通货柜）
	 */
	@Override
	public void updateContainer(VendingContainerRequest request) {
		VendingContainer container = find(request.getId());
		VendingContainerData data = request.getContainerData();
		container.setSn(data.getSn());
		container.setStatus(data.getStatus());
		container.setWarningPer(data.getWarningPer());
		update(container);
	}
	/**
	 * 删除货柜（及其货道）
	 * @param ids
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteContainer(Long[] ids) {
		for (Long id : ids) {
			VendingContainer container = find(id);
			for (ContainerChannel channel : container.getCntrChannel()) {
				containerChannelDao.remove(channel);
			}
			delete(container);
		}
	}
	/**
	 * 获取某个场景的中控柜
	 * @param scene
	 * @return
	 */
	@Override
	public VendingContainer getScencCentral(Scene scene) {
	  	List<Filter> filters = new ArrayList<Filter>();
	  	filters.add(Filter.eq("scene", scene));
	  	filters.add(Filter.isNull("parent"));
	  	VendingContainer centralContainer = findFirst(filters, null);
		return centralContainer;
	}
	/**
	 * 获取某个场景的中控柜(根据场景ID)
	 * @param scene
	 * @return
	 */
	@Override
	public VendingContainer getScencCentral(Long sceneId) {
		Scene scene = sceneDao.find(sceneId);
		if (scene != null) {
			return getScencCentral(scene);
		}
		return null;
	}

}