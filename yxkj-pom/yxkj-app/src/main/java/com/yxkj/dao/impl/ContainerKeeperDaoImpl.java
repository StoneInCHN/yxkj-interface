package com.yxkj.dao.impl; 

import javax.persistence.Query;

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ContainerKeeper;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.ContainerKeeperDao;

@Repository("containerKeeperDaoImpl")
public class ContainerKeeperDaoImpl extends  BaseDaoImpl<ContainerKeeper,Long> implements ContainerKeeperDao {
    
    @Override
    public ContainerKeeper findByCellPhoneNum(String cellPhoneNum) {
      String jpql = "FROM ContainerKeeper c WHERE c.cellPhoneNum = :cellPhoneNum";
      Query query = entityManager.createQuery(jpql).setParameter("cellPhoneNum", cellPhoneNum);
      return (ContainerKeeper) query.getSingleResult();
    }

	@Override
	public void updatePassword(String cellPhoneNum, String password) {
		String jpql = "update ContainerKeeper c set c.loginPwd = :password where c.cellPhoneNum = :cellPhoneNum";
		Query query = entityManager.createQuery(jpql).setParameter("password", password).setParameter("cellPhoneNum", cellPhoneNum);
		query.executeUpdate();
	}

}