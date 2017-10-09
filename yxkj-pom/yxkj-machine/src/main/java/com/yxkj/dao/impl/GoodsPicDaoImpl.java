package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.GoodsPic;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.GoodsPicDao;
@Repository("goodsPicDaoImpl")
public class GoodsPicDaoImpl extends  BaseDaoImpl<GoodsPic,Long> implements GoodsPicDao {

}