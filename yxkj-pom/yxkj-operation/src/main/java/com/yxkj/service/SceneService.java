package com.yxkj.service;

import java.util.List;
import java.util.Map;

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
  /**
   * 新增场景，场景的中控，中控广告
   * @param request
   */
  void saveScene(SceneRequest request);
  /**
   * 更新场景，场景的中控
   * @param request
   */  
  void updateScene(SceneRequest request);
  /**
   * 获取某个场景的详情（包括中控信息）
   * @param id
   * @return
   */
  Map<String, Object> getSceneData(Long id);
  
  /**
   * 自动生成场景编号(10位数字，1000000001开始)
   * @return
   */
  String genSceneSn();
}
