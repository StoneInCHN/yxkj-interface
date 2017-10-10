package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ShelfOrderItem;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.ShelfOrderItemDao;
@Repository("shelfOrderItemDaoImpl")
public class ShelfOrderItemDaoImpl extends  BaseDaoImpl<ShelfOrderItem,Long> implements ShelfOrderItemDao {

}