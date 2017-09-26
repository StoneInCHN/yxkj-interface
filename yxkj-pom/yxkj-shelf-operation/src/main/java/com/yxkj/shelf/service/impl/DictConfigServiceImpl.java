package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.DictConfig;
import com.yxkj.shelf.dao.DictConfigDao;
import com.yxkj.shelf.service.DictConfigService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("dictConfigServiceImpl")
public class DictConfigServiceImpl extends BaseServiceImpl<DictConfig,Long> implements DictConfigService {

      @Resource(name="dictConfigDaoImpl")
      public void setBaseDao(DictConfigDao dictConfigDao) {
         super.setBaseDao(dictConfigDao);
  }
}