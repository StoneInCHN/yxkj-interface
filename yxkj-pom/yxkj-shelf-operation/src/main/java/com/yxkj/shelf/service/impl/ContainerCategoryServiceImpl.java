package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ContainerCategory;
import com.yxkj.shelf.dao.ContainerCategoryDao;
import com.yxkj.shelf.service.ContainerCategoryService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("containerCategoryServiceImpl")
public class ContainerCategoryServiceImpl extends BaseServiceImpl<ContainerCategory,Long> implements ContainerCategoryService {

      @Resource(name="containerCategoryDaoImpl")
      public void setBaseDao(ContainerCategoryDao containerCategoryDao) {
         super.setBaseDao(containerCategoryDao);
  }
}