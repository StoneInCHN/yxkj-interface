package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Company;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.CompanyDao;
@Repository("companyDaoImpl")
public class CompanyDaoImpl extends  BaseDaoImpl<Company,Long> implements CompanyDao {

}