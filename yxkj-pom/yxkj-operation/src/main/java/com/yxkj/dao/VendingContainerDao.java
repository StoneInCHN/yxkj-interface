package com.yxkj.dao; 
import java.util.List;
import java.util.Map;

import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.dao.BaseDao;

public interface VendingContainerDao extends  BaseDao<VendingContainer,Long>{

	List<Map<String, Object>> findListBySceneId(Long sceneId);

}