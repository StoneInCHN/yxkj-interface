package com.yxkj.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yxkj.dao.AdResourceDao;
import com.yxkj.entity.AdResource;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.admin.request.AdResourceRequest;
import com.yxkj.service.AdResourceService;
import com.yxkj.service.FileService;

@Service("adResourceServiceImpl")
public class AdResourceServiceImpl extends BaseServiceImpl<AdResource, Long>
    implements AdResourceService {

  @Resource(name = "fileServiceImpl")
  private FileService fileService;
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

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void batchUpload(Map<String, MultipartFile> fileMap, CommonEnum.FileType fileType) {
    for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
      MultipartFile mf = entity.getValue();
      String displayPath = fileService.saveImage(mf, CommonEnum.ImageType.AD_RESOURCE);

      AdResource adResource = new AdResource();
      adResource.setFileType(fileType);
      adResource.setFileName(mf.getName());
      adResource.setFileUrl(displayPath);

      adResourceDao.persist(adResource);
    }
  }
}
