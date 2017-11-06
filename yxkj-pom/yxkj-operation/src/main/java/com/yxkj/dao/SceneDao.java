package com.yxkj.dao;

import java.util.List;

import com.yxkj.entity.Scene;
import com.yxkj.framework.dao.BaseDao;
import com.yxkj.json.admin.response.SceneProfile;

public interface SceneDao extends BaseDao<Scene, Long> {
  /**
   * 根据优享空间编号或地址名称查询
   * 
   * @param key
   * @return
   */
  List<Scene> getByKey(String key, Long pId);

  List<SceneProfile> getSceneListByKeeper(Long id);

  List<SceneProfile> getSceneListByProperty(Long id);
}
