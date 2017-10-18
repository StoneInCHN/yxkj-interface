package com.yxkj.shelf.dao; 
import com.yxkj.entity.Company;
import com.yxkj.shelf.framework.dao.BaseDao;

public interface CompanyDao extends  BaseDao<Company,Long>{

	String genComSn();

}