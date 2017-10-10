package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.CompanyShelf;
import com.yxkj.dao.CompanyShelfDao;
import com.yxkj.service.CompanyShelfService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("companyShelfServiceImpl")
public class CompanyShelfServiceImpl extends BaseServiceImpl<CompanyShelf,Long> implements CompanyShelfService {

      @Resource(name="companyShelfDaoImpl")
      public void setBaseDao(CompanyShelfDao companyShelfDao) {
         super.setBaseDao(companyShelfDao);
  }
}