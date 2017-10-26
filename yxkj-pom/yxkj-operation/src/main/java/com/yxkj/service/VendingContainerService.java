package com.yxkj.service; 

import java.util.List;
import java.util.Map;

import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.admin.request.VendingContainerRequest;

public interface VendingContainerService extends BaseService<VendingContainer,Long>{

	List<Map<String, Object>> findListBySceneId(Long sceneId);

	void saveContainer(VendingContainerRequest request);

	void updateContainer(VendingContainerRequest request);

}