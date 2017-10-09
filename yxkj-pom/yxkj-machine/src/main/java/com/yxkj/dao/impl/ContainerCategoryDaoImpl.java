package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ContainerCategory;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.ContainerCategoryDao;
@Repository("containerCategoryDaoImpl")
public class ContainerCategoryDaoImpl extends  BaseDaoImpl<ContainerCategory,Long> implements ContainerCategoryDao {

}