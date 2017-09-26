package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.OperationLog;
import com.yxkj.shelf.dao.OperationLogDao;
import com.yxkj.shelf.service.OperationLogService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("operationLogServiceImpl")
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLog,Long> implements OperationLogService {

      @Resource(name="operationLogDaoImpl")
      public void setBaseDao(OperationLogDao operationLogDao) {
         super.setBaseDao(operationLogDao);
  }
}