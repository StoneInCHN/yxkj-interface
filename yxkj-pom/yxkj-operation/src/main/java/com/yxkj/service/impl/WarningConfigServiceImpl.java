package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.WarningConfig;
import com.yxkj.dao.WarningConfigDao;
import com.yxkj.service.WarningConfigService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("warningConfigServiceImpl")
public class WarningConfigServiceImpl extends BaseServiceImpl<WarningConfig,Long> implements WarningConfigService {

      @Resource(name="warningConfigDaoImpl")
      public void setBaseDao(WarningConfigDao warningConfigDao) {
         super.setBaseDao(warningConfigDao);
  }
}