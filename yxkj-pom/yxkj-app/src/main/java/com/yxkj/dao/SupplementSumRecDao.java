package com.yxkj.dao; 
import java.util.List;

import com.yxkj.entity.SupplementSumRec;
import com.yxkj.framework.dao.BaseDao;

public interface SupplementSumRecDao extends  BaseDao<SupplementSumRec,Long>{
  
  Object[] findUnfinishedSupplyRecord(Long suppId, String sceneSn);

  SupplementSumRec findSupplementSumRecordBySceneSn(Long suppId, String sceneSn);
  
  List<Object> findSupplyDate(Long suppId, int pageNo, int pageSize);
  
  List<Object[]> findSupplementSumRecord(Long suppId, String date);
  
}