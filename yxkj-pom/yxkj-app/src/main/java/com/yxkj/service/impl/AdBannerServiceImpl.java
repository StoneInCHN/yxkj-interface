package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.AdBanner;
import com.yxkj.dao.AdBannerDao;
import com.yxkj.service.AdBannerService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("adBannerServiceImpl")
public class AdBannerServiceImpl extends BaseServiceImpl<AdBanner,Long> implements AdBannerService {

      @Resource(name="adBannerDaoImpl")
      public void setBaseDao(AdBannerDao adBannerDao) {
         super.setBaseDao(adBannerDao);
  }
}