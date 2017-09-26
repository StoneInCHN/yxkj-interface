package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.AdMachine;
import com.yxkj.shelf.dao.AdMachineDao;
import com.yxkj.shelf.service.AdMachineService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("adMachineServiceImpl")
public class AdMachineServiceImpl extends BaseServiceImpl<AdMachine,Long> implements AdMachineService {

      @Resource(name="adMachineDaoImpl")
      public void setBaseDao(AdMachineDao adMachineDao) {
         super.setBaseDao(adMachineDao);
  }
}