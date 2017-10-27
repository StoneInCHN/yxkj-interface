package com.yxkj.service; 

import java.util.List;
import java.util.Map;

import com.yxkj.entity.Scene;
import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.admin.request.VendingContainerRequest;

public interface VendingContainerService extends BaseService<VendingContainer,Long>{
	
	List<Map<String, Object>> findListBySceneId(Long sceneId);
	
	/**
	 * 新增货柜（普通货柜）
	 */
	void saveContainer(VendingContainerRequest request);
	/**
	 * 更新货柜（普通货柜）
	 */
	void updateContainer(VendingContainerRequest request);
	/**
	 * 获取某个场景的中控柜
	 * @param scene
	 * @return
	 */
	VendingContainer getScencCentral(Scene scene);
	/**
	 * 获取某个场景的中控柜(根据场景ID)
	 * @param scene
	 * @return
	 */
	VendingContainer getScencCentral(Long sceneId);
	/**
	 * 删除货柜（及其货道）
	 * @param ids
	 */
	void deleteContainer(Long[] ids);

}