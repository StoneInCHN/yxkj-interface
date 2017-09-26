package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Goods;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.GoodsDao;
@Repository("goodsDaoImpl")
public class GoodsDaoImpl extends  BaseDaoImpl<Goods,Long> implements GoodsDao {

}