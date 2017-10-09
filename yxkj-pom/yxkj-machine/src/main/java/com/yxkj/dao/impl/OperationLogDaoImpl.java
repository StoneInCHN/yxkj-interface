package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.OperationLog;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.OperationLogDao;
@Repository("operationLogDaoImpl")
public class OperationLogDaoImpl extends  BaseDaoImpl<OperationLog,Long> implements OperationLogDao {

}