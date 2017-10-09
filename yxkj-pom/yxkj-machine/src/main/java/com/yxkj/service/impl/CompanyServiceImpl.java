package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.Company;
import com.yxkj.dao.CompanyDao;
import com.yxkj.service.CompanyService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("companyServiceImpl")
public class CompanyServiceImpl extends BaseServiceImpl<Company,Long> implements CompanyService {

      @Resource(name="companyDaoImpl")
      public void setBaseDao(CompanyDao companyDao) {
         super.setBaseDao(companyDao);
  }
}