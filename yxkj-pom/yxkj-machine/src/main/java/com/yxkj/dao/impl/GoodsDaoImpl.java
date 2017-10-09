package com.yxkj.dao.impl;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.GoodsDao;
import com.yxkj.entity.Goods;
import com.yxkj.framework.dao.impl.BaseDaoImpl;

@Repository("goodsDaoImpl")
public class GoodsDaoImpl extends BaseDaoImpl<Goods, Long> implements GoodsDao {

}
