package com.yxkj.dao; 
import java.util.List;

import com.yxkj.entity.SupplementRecord;
import com.yxkj.framework.dao.BaseDao;

public interface SupplementRecordDao extends  BaseDao<SupplementRecord,Long>{

  List<SupplementRecord> findRecordByCntrId(Long suppId, Long cntrId);
  
  List<Object[]> findSupplementVendingContainerBySceneSn(Long suppId, String sceneSn);
  
  List<Object[]> findSupplementGoodsRecordByCntrId(Long suppId, String cntrId);
}