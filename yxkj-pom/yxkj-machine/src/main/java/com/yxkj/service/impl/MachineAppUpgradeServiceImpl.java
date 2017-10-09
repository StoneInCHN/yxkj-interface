package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.MachineAppUpgrade;
import com.yxkj.dao.MachineAppUpgradeDao;
import com.yxkj.service.MachineAppUpgradeService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("machineAppUpgradeServiceImpl")
public class MachineAppUpgradeServiceImpl extends BaseServiceImpl<MachineAppUpgrade,Long> implements MachineAppUpgradeService {

      @Resource(name="machineAppUpgradeDaoImpl")
      public void setBaseDao(MachineAppUpgradeDao machineAppUpgradeDao) {
         super.setBaseDao(machineAppUpgradeDao);
  }
}