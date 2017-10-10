package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.AdResource;
import com.yxkj.dao.AdResourceDao;
import com.yxkj.service.AdResourceService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("adResourceServiceImpl")
public class AdResourceServiceImpl extends BaseServiceImpl<AdResource,Long> implements AdResourceService {

      @Resource(name="adResourceDaoImpl")
      public void setBaseDao(AdResourceDao adResourceDao) {
         super.setBaseDao(adResourceDao);
  }
}