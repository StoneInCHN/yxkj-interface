package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.CommandRecord;
import com.yxkj.dao.CommandRecordDao;
import com.yxkj.service.CommandRecordService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("commandRecordServiceImpl")
public class CommandRecordServiceImpl extends BaseServiceImpl<CommandRecord,Long> implements CommandRecordService {

      @Resource(name="commandRecordDaoImpl")
      public void setBaseDao(CommandRecordDao commandRecordDao) {
         super.setBaseDao(commandRecordDao);
  }
}