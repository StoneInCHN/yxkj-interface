package com.yxkj.dao;

import com.yxkj.entity.OrderItemTmp;
import com.yxkj.framework.dao.BaseDao;

public interface OrderItemTmpDao extends BaseDao<OrderItemTmp, Long> {

	OrderItemTmp getOrderItemTmpByItemId(Long orderItemId);
}
