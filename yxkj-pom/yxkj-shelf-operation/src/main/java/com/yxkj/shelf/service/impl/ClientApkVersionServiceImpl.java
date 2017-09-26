package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ClientApkVersion;
import com.yxkj.shelf.dao.ClientApkVersionDao;
import com.yxkj.shelf.service.ClientApkVersionService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("clientApkVersionServiceImpl")
public class ClientApkVersionServiceImpl extends BaseServiceImpl<ClientApkVersion,Long> implements ClientApkVersionService {

      @Resource(name="clientApkVersionDaoImpl")
      public void setBaseDao(ClientApkVersionDao clientApkVersionDao) {
         super.setBaseDao(clientApkVersionDao);
  }
}