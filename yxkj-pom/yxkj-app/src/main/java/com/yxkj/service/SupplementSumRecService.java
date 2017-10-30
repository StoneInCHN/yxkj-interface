package com.yxkj.service; 

import java.util.List;

import com.yxkj.entity.SupplementSumRec;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.bean.SumSupplementRecord;

public interface SupplementSumRecService extends BaseService<SupplementSumRec,Long>{
  
  List<SumSupplementRecord> findSupplySumRecord(Long suppId, String pageNo, int pageSize);
  
}