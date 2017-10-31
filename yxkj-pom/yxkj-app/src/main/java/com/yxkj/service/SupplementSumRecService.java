package com.yxkj.service; 

import java.util.List;

import com.yxkj.entity.SupplementSumRec;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.bean.DailySumSupplementRecord;

public interface SupplementSumRecService extends BaseService<SupplementSumRec,Long>{
  
  List<DailySumSupplementRecord> findSupplySumRecord(Long suppId, String pageNo, int pageSize);
  
}