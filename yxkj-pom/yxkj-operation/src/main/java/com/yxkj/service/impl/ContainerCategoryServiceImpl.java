package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ContainerCategory;
import com.yxkj.dao.ContainerCategoryDao;
import com.yxkj.service.ContainerCategoryService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("containerCategoryServiceImpl")
public class ContainerCategoryServiceImpl extends BaseServiceImpl<ContainerCategory,Long> implements ContainerCategoryService {

      @Resource(name="containerCategoryDaoImpl")
      public void setBaseDao(ContainerCategoryDao containerCategoryDao) {
         super.setBaseDao(containerCategoryDao);
  }
}