package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.GoodsCategory;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.GoodsCategoryDao;
@Repository("goodsCategoryDaoImpl")
public class GoodsCategoryDaoImpl extends  BaseDaoImpl<GoodsCategory,Long> implements GoodsCategoryDao {

}