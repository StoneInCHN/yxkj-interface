package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ContainerCategory;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.ContainerCategoryDao;
@Repository("containerCategoryDaoImpl")
public class ContainerCategoryDaoImpl extends  BaseDaoImpl<ContainerCategory,Long> implements ContainerCategoryDao {

}