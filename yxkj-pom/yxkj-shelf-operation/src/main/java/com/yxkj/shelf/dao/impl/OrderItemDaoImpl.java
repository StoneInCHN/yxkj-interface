package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.OrderItem;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.OrderItemDao;
@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends  BaseDaoImpl<OrderItem,Long> implements OrderItemDao {

}