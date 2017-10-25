package com.yxkj.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yxkj.dao.SceneDao;
import com.yxkj.entity.Scene;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.SceneService;

@Service("sceneServiceImpl")
public class SceneServiceImpl extends BaseServiceImpl<Scene, Long> implements SceneService {

  @Resource(name = "sceneDaoImpl")
  private SceneDao sceneDao;

  @Resource(name = "sceneDaoImpl")
  public void setBaseDao(SceneDao sceneDao) {
    super.setBaseDao(sceneDao);
  }


  @Override
  public List<Scene> getByKey(String key) {
    return sceneDao.getByKey(key);
  }
}
