package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.RefundHistory;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.RefundHistoryDao;
@Repository("refundHistoryDaoImpl")
public class RefundHistoryDaoImpl extends  BaseDaoImpl<RefundHistory,Long> implements RefundHistoryDao {

}