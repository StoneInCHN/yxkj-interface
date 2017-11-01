package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.OperationMsgInfo;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.OperationMsgInfoDao;
@Repository("operationMsgInfoDaoImpl")
public class OperationMsgInfoDaoImpl extends  BaseDaoImpl<OperationMsgInfo,Long> implements OperationMsgInfoDao {

}