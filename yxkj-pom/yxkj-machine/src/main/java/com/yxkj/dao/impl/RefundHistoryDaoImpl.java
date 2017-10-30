package com.yxkj.dao.impl;

import org.springframework.stereotype.Service;

import com.yxkj.dao.RefundHistoryDao;
import com.yxkj.entity.RefundHistory;
import com.yxkj.framework.dao.impl.BaseDaoImpl;

@Service(value = "refundHistoryDaoImpl")
public class RefundHistoryDaoImpl extends BaseDaoImpl<RefundHistory, Long> implements
		RefundHistoryDao {
}
