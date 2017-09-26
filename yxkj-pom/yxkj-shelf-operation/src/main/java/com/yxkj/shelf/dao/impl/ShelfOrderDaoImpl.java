package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ShelfOrder;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.ShelfOrderDao;
@Repository("shelfOrderDaoImpl")
public class ShelfOrderDaoImpl extends  BaseDaoImpl<ShelfOrder,Long> implements ShelfOrderDao {

}