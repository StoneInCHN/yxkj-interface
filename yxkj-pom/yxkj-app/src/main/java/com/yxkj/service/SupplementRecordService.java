package com.yxkj.service; 

import java.util.List;

import com.yxkj.entity.SupplementRecord;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.bean.SceneSupplementRecord;

public interface SupplementRecordService extends BaseService<SupplementRecord,Long>{

  List<SceneSupplementRecord> getSupplementRecordDetails(Long userId, String sceneSn);
  
}