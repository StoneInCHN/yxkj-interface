package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.DictConfig;
import com.yxkj.dao.DictConfigDao;
import com.yxkj.service.DictConfigService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("dictConfigServiceImpl")
public class DictConfigServiceImpl extends BaseServiceImpl<DictConfig,Long> implements DictConfigService {

      @Resource(name="dictConfigDaoImpl")
      public void setBaseDao(DictConfigDao dictConfigDao) {
         super.setBaseDao(dictConfigDao);
  }
}