package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.MachineApkVersion;
import com.yxkj.dao.MachineApkVersionDao;
import com.yxkj.service.MachineApkVersionService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("machineApkVersionServiceImpl")
public class MachineApkVersionServiceImpl extends BaseServiceImpl<MachineApkVersion,Long> implements MachineApkVersionService {

      @Resource(name="machineApkVersionDaoImpl")
      public void setBaseDao(MachineApkVersionDao machineApkVersionDao) {
         super.setBaseDao(machineApkVersionDao);
  }
}