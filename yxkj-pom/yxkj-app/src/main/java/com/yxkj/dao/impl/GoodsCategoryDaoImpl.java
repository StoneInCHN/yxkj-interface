package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.GoodsCategory;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.GoodsCategoryDao;
@Repository("goodsCategoryDaoImpl")
public class GoodsCategoryDaoImpl extends  BaseDaoImpl<GoodsCategory,Long> implements GoodsCategoryDao {

}