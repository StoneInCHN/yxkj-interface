package com.yxkj.dao; 

import java.util.List;

import com.yxkj.entity.SupplementList;
import com.yxkj.framework.dao.BaseDao;

public interface SupplementListDao extends  BaseDao<SupplementList,Long>{

  List<Object[]> findWaitSupplyScene(Long suppId, int pageNo, int pageSize);
  
  List<Object[]> findCentralVendingContainer(String sceneSn);
  
  List<Object[]> findChildrenVendingContainer(Long id);
  
  Integer findWaitSupplyCount(Long cntrId);
  
  List<Object[]> findWaitSupplySceneList(Long suppId);
  
  List<Object[]> findWaitSupplyGoodsCategoryList(Long suppId);

  List<Object[]> findWaitSupplyGoodsList(Long suppId, String sceneSn, Long cateId, int pageNo, int pageSize);
  
  List<Object[]> findWaitSupplyGoodsDetails(Long suppId, String goodsSn);
  
  List<Object[]> getWaitSupplyContainerGoods(Long suppId, Long cntrId, int pageNo, int pageSize);
  
  Object findGoodsPicByGoodsSn(String goodsSn);
  
}