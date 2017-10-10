package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ClientApkVersion;
import com.yxkj.dao.ClientApkVersionDao;
import com.yxkj.service.ClientApkVersionService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("clientApkVersionServiceImpl")
public class ClientApkVersionServiceImpl extends BaseServiceImpl<ClientApkVersion,Long> implements ClientApkVersionService {

      @Resource(name="clientApkVersionDaoImpl")
      public void setBaseDao(ClientApkVersionDao clientApkVersionDao) {
         super.setBaseDao(clientApkVersionDao);
  }
}