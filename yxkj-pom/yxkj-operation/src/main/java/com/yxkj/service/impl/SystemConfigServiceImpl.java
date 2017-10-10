package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SystemConfig;
import com.yxkj.dao.SystemConfigDao;
import com.yxkj.service.SystemConfigService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("systemConfigServiceImpl")
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfig,Long> implements SystemConfigService {

      @Resource(name="systemConfigDaoImpl")
      public void setBaseDao(SystemConfigDao systemConfigDao) {
         super.setBaseDao(systemConfigDao);
  }
}