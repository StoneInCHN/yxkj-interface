package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.CompanyShelf;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.CompanyShelfDao;
@Repository("companyShelfDaoImpl")
public class CompanyShelfDaoImpl extends  BaseDaoImpl<CompanyShelf,Long> implements CompanyShelfDao {

}