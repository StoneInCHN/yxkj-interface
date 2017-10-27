package com.yxkj.dao; 
import java.util.List;

import com.yxkj.entity.Scene;
import com.yxkj.framework.dao.BaseDao;

public interface SceneDao extends  BaseDao<Scene,Long>{

  List<Object[]> findWaitSupplyScene(Long suppId, int pageNo, int pageSize);
  
  Object findSceneNameBySn(String sn);
  
}