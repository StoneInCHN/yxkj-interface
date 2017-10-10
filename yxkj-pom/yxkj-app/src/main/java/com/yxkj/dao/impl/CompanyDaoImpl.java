package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Company;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.CompanyDao;
@Repository("companyDaoImpl")
public class CompanyDaoImpl extends  BaseDaoImpl<Company,Long> implements CompanyDao {

}