package com.yxkj.shelf.dao.impl; 

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Company;
import com.yxkj.entity.Sn;
import com.yxkj.entity.Sn.Type;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.CompanyDao;
@Repository("companyDaoImpl")
public class CompanyDaoImpl extends  BaseDaoImpl<Company,Long> implements CompanyDao {

	@Override
	public String genComSn() {
		  String jpql = "select sn from Sn sn where sn.type = :type";
	      Sn sn =
	              entityManager.createQuery(jpql, Sn.class).setFlushMode(FlushModeType.COMMIT)
	                  .setLockMode(LockModeType.PESSIMISTIC_WRITE).setParameter("type", Type.COMPANY_SN)
	                  .getSingleResult();
	          long lastValue = sn.getLastValue();
	          sn.setLastValue(lastValue + 1);
	          entityManager.merge(sn);
		return lastValue+"";
	}

}