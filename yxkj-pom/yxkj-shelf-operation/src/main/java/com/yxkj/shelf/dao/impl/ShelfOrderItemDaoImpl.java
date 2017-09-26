package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ShelfOrderItem;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.ShelfOrderItemDao;
@Repository("shelfOrderItemDaoImpl")
public class ShelfOrderItemDaoImpl extends  BaseDaoImpl<ShelfOrderItem,Long> implements ShelfOrderItemDao {

}