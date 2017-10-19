package com.yxkj.service; 

import java.util.List;
import java.util.Map;

import com.yxkj.entity.SupplementList;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.base.WaitSupplyContainerGoods;
import com.yxkj.json.base.WaitSupplyGoods;
import com.yxkj.json.base.WaitSupplyGoodsDetails;
import com.yxkj.json.base.WaitSupplyList;

public interface SupplementListService extends BaseService<SupplementList,Long>{

  WaitSupplyList getWaitSupplyListBySuppId(Long suppId, String pageNo, int pageSize);
  
  Map<String, String> getWaitSupplySceneList(Long suppId);
  
  List<String> getWaitSupplyGoodsCategoryList(Long  suppId);
  
  List<WaitSupplyGoods> getWaitSupplyGoodList(Long suppId, String sceneSn, String cateName, String pageNo, int pageSize);
  
  WaitSupplyGoodsDetails getWaitSupplyGoodsDetails(Long suppId, String goodsSn);
  
  List<WaitSupplyContainerGoods> getWaitSupplyContainerGoods(Long suppId, Long cntrId, String pageNo, int pageSize);
  
}