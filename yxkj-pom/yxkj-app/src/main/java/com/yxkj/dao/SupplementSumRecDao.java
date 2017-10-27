package com.yxkj.dao; 
import java.util.List;

import com.yxkj.entity.SupplementSumRec;
import com.yxkj.framework.dao.BaseDao;

public interface SupplementSumRecDao extends  BaseDao<SupplementSumRec,Long>{

  SupplementSumRec findSupplementSumRecordBySceneSn(Long suppId, String sceneSn);
  
  List<SupplementSumRec> findSupplementSumRecord(int pageNo, int pageSize, Long suppId);
}