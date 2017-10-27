package com.yxkj.service;

import java.util.List;

import com.yxkj.entity.Scene;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.admin.request.SceneRequest;

public interface SceneService extends BaseService<Scene, Long> {
  /**
   * 根据优享空间编号或地址名称查询
   * 
   * @param key
   * @return
   */
  List<Scene> getByKey(String key);

  Scene getSceneEnity(SceneRequest request, Long id);

  void saveScene(Scene scene);
  
  String genSceneSn();
}
