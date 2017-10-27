package com.yxkj.service; 

import java.util.List;
import java.util.Map;

import com.yxkj.entity.SupplementList;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.bean.SupplementSumRecord;
import com.yxkj.json.bean.SupplyRecord;
import com.yxkj.json.bean.WaitSupplyContainerGoods;
import com.yxkj.json.bean.WaitSupplyGoods;
import com.yxkj.json.bean.WaitSupplyGoodsDetails;
import com.yxkj.json.bean.WaitSupplyList;

public interface SupplementListService extends BaseService<SupplementList,Long>{

  WaitSupplyList getWaitSupplyListBySuppId(Long suppId, String pageNo, int pageSize);
  
  List<Map<String, String>> getWaitSupplySceneList(Long suppId);
  
  List<Map<String,Object>> getWaitSupplyGoodsCategoryList(Long  suppId);
  
  List<WaitSupplyGoods> getWaitSupplyGoodList(Long suppId, String sceneSn, Long cateId, String pageNo, int pageSize);
  
  WaitSupplyGoodsDetails getWaitSupplyGoodsDetails(Long suppId, String goodsSn);
  
  List<WaitSupplyContainerGoods> getWaitSupplyContainerGoods(Long suppId, Long cntrId, String pageNo, int pageSize);
  
  void uploadSupplementPic(Long suppId, Long cntrId, String picFileName);
  
  void commitSupplyRecords(Long userId, String sceneSn, List<SupplyRecord> records) throws Exception;
  
  List<SupplementSumRecord> getSupplementSumRecord(String pageNo, String pageSize, Long suppId);
}