package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SceneSalesInfo;
import com.yxkj.dao.SceneSalesInfoDao;
import com.yxkj.service.SceneSalesInfoService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("sceneSalesInfoServiceImpl")
public class SceneSalesInfoServiceImpl extends BaseServiceImpl<SceneSalesInfo,Long> implements SceneSalesInfoService {

      @Resource(name="sceneSalesInfoDaoImpl")
      public void setBaseDao(SceneSalesInfoDao sceneSalesInfoDao) {
         super.setBaseDao(sceneSalesInfoDao);
  }
}