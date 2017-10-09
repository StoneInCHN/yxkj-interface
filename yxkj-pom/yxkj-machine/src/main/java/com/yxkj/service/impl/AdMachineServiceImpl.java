package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.AdMachine;
import com.yxkj.dao.AdMachineDao;
import com.yxkj.service.AdMachineService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("adMachineServiceImpl")
public class AdMachineServiceImpl extends BaseServiceImpl<AdMachine,Long> implements AdMachineService {

      @Resource(name="adMachineDaoImpl")
      public void setBaseDao(AdMachineDao adMachineDao) {
         super.setBaseDao(adMachineDao);
  }
}