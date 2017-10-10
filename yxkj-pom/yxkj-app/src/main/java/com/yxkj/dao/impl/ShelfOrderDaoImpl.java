package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ShelfOrder;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.ShelfOrderDao;
@Repository("shelfOrderDaoImpl")
public class ShelfOrderDaoImpl extends  BaseDaoImpl<ShelfOrder,Long> implements ShelfOrderDao {

}