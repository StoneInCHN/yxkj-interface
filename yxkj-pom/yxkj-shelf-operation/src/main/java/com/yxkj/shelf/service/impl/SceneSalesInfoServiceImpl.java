package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SceneSalesInfo;
import com.yxkj.shelf.dao.SceneSalesInfoDao;
import com.yxkj.shelf.service.SceneSalesInfoService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("sceneSalesInfoServiceImpl")
public class SceneSalesInfoServiceImpl extends BaseServiceImpl<SceneSalesInfo,Long> implements SceneSalesInfoService {

      @Resource(name="sceneSalesInfoDaoImpl")
      public void setBaseDao(SceneSalesInfoDao sceneSalesInfoDao) {
         super.setBaseDao(sceneSalesInfoDao);
  }
}