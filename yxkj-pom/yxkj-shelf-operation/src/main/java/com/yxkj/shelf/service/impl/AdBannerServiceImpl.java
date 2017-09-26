package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.AdBanner;
import com.yxkj.shelf.dao.AdBannerDao;
import com.yxkj.shelf.service.AdBannerService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("adBannerServiceImpl")
public class AdBannerServiceImpl extends BaseServiceImpl<AdBanner,Long> implements AdBannerService {

      @Resource(name="adBannerDaoImpl")
      public void setBaseDao(AdBannerDao adBannerDao) {
         super.setBaseDao(adBannerDao);
  }
}