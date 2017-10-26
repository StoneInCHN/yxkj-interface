package com.yxkj.dao.impl; 

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.VendingContainerDao;
import com.yxkj.entity.VendingContainer;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
@Repository("vendingContainerDaoImpl")
public class VendingContainerDaoImpl extends  BaseDaoImpl<VendingContainer,Long> implements VendingContainerDao {

	@Override
	public List<Map<String, Object>> findListBySceneId(Long sceneId) {
//		String sql = "";
//		@SuppressWarnings("rawtypes")
//		List list = entityManager.createNativeQuery(sql.toString()).setFlushMode(FlushModeType.COMMIT).getResultList();
//	    for (Object object : list) {
//	    	Object[] arrays = (Object[] )object;
//	    	Long count = ((BigInteger)arrays[0]).longValue();
//	    } 
	    return null;
	}

}