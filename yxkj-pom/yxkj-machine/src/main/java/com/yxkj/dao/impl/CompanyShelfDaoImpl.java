package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.CompanyShelf;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.CompanyShelfDao;
@Repository("companyShelfDaoImpl")
public class CompanyShelfDaoImpl extends  BaseDaoImpl<CompanyShelf,Long> implements CompanyShelfDao {

}