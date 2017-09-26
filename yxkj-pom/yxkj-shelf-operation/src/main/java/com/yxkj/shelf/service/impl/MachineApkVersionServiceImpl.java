package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.MachineApkVersion;
import com.yxkj.shelf.dao.MachineApkVersionDao;
import com.yxkj.shelf.service.MachineApkVersionService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("machineApkVersionServiceImpl")
public class MachineApkVersionServiceImpl extends BaseServiceImpl<MachineApkVersion,Long> implements MachineApkVersionService {

      @Resource(name="machineApkVersionDaoImpl")
      public void setBaseDao(MachineApkVersionDao machineApkVersionDao) {
         super.setBaseDao(machineApkVersionDao);
  }
}