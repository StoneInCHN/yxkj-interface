package com.yxkj.dao.impl; 

import javax.persistence.Query;

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.ContainerKeeper;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.ContainerKeeperDao;

@Repository("containerKeeperDaoImpl")
public class ContainerKeeperDaoImpl extends  BaseDaoImpl<ContainerKeeper,Long> implements ContainerKeeperDao {

	@Override
	public void updatePassword(String cellPhoneNum, String password) {
		String jpql = "update ContainerKeeper c set c.loginPwd = ? where c.cellPhoneNum = ?";
		Query query = entityManager.createQuery(jpql).setParameter(1, password).setParameter(2, cellPhoneNum);
		query.executeUpdate();
	}

}