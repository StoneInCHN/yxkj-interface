package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.GoodsPic;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.GoodsPicDao;
@Repository("goodsPicDaoImpl")
public class GoodsPicDaoImpl extends  BaseDaoImpl<GoodsPic,Long> implements GoodsPicDao {

}