package com.yxkj.service.impl;

import javax.annotation.Resource;

import com.yxkj.json.admin.request.AdResourceRequest;
import org.springframework.stereotype.Service;

import com.yxkj.entity.AdResource;
import com.yxkj.dao.AdResourceDao;
import com.yxkj.service.AdResourceService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("adResourceServiceImpl")
public class AdResourceServiceImpl extends BaseServiceImpl<AdResource, Long>
    implements AdResourceService {

  private AdResourceDao adResourceDao;

  @Resource(name = "adResourceDaoImpl")
  public void setBaseDao(AdResourceDao adResourceDao) {
    super.setBaseDao(adResourceDao);
    this.adResourceDao = adResourceDao;
  }

  @Override
  public AdResource updateAdResource(AdResourceRequest request) {

    AdResource oldAdResource = adResourceDao.find(request.getId());
    oldAdResource.setFileName(request.getFileName());
    oldAdResource.setFileUrl(request.getFileUrl());
    oldAdResource.setRemark(request.getRemark());
    return adResourceDao.merge(oldAdResource);
  }
}
