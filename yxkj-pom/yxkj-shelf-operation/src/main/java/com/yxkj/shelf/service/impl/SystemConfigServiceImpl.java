package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SystemConfig;
import com.yxkj.shelf.dao.SystemConfigDao;
import com.yxkj.shelf.service.SystemConfigService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("systemConfigServiceImpl")
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfig,Long> implements SystemConfigService {

      @Resource(name="systemConfigDaoImpl")
      public void setBaseDao(SystemConfigDao systemConfigDao) {
         super.setBaseDao(systemConfigDao);
  }
}