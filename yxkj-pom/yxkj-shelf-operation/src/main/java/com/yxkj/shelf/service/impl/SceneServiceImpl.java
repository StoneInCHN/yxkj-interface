package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Scene;
import com.yxkj.shelf.dao.SceneDao;
import com.yxkj.shelf.service.SceneService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("sceneServiceImpl")
public class SceneServiceImpl extends BaseServiceImpl<Scene,Long> implements SceneService {

      @Resource(name="sceneDaoImpl")
      public void setBaseDao(SceneDao sceneDao) {
         super.setBaseDao(sceneDao);
  }
}