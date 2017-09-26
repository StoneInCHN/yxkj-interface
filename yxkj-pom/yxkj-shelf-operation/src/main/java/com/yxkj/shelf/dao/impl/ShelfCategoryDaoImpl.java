package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ShelfCategory;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.ShelfCategoryDao;
@Repository("shelfCategoryDaoImpl")
public class ShelfCategoryDaoImpl extends  BaseDaoImpl<ShelfCategory,Long> implements ShelfCategoryDao {

}