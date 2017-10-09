package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ShelfCategory;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.ShelfCategoryDao;
@Repository("shelfCategoryDaoImpl")
public class ShelfCategoryDaoImpl extends  BaseDaoImpl<ShelfCategory,Long> implements ShelfCategoryDao {

}