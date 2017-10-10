package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.OperationLog;
import com.yxkj.dao.OperationLogDao;
import com.yxkj.service.OperationLogService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("operationLogServiceImpl")
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLog,Long> implements OperationLogService {

      @Resource(name="operationLogDaoImpl")
      public void setBaseDao(OperationLogDao operationLogDao) {
         super.setBaseDao(operationLogDao);
  }
}