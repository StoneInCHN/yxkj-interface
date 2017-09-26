package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.CompanyShelf;
import com.yxkj.shelf.dao.CompanyShelfDao;
import com.yxkj.shelf.service.CompanyShelfService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("companyShelfServiceImpl")
public class CompanyShelfServiceImpl extends BaseServiceImpl<CompanyShelf,Long> implements CompanyShelfService {

      @Resource(name="companyShelfDaoImpl")
      public void setBaseDao(CompanyShelfDao companyShelfDao) {
         super.setBaseDao(companyShelfDao);
  }
}