package com.yxkj.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yxkj.entity.Goods;
import com.yxkj.entity.GoodsPic;
import com.yxkj.entity.SupplementList;
import com.yxkj.entity.SupplementRecord;
import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;
/**
 * 准备导出数据
 * @author luzhang
 *
 */
@Component("exportHelper")
public class ExportHelper {
	
  @Value("${system.project_deploy_url}")
  private String projectDeployUrl;	
	
  /**
   * 准备导出的Goods数据
   * 
   * @param response
   * @param lists
   * @return
   */
  public List<Map<String, String>> prepareExportGoods(List<Goods> lists) {
    List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
    for (Goods goods : lists) {
      Map<String, String> map = new HashMap<String, String>();
      map.put("sn", goods.getSn());
      map.put("name", goods.getName());
      map.put("fullName", goods.getFullName());
      map.put("spec", goods.getSpec());
      map.put("costPrice", goods.getCostPrice()!=null?goods.getCostPrice().toString():"");
      map.put("salePrice", goods.getSalePrice()!=null?goods.getSalePrice().toString():"");
      map.put("status", "");
      if (goods.getStatus() != null) {
        if (goods.getStatus() == CommonStatus.ACITVE) {
          map.put("status", "可用");
        }
        if (goods.getStatus() == CommonStatus.INACTIVE) {
          map.put("status", "不可用");
        }
      }
      map.put("smallUrl", "");
      map.put("largeUrl", "");
      if (goods.getGoodsPics() != null && goods.getGoodsPics().size() > 0) {
		  for (GoodsPic goodsPic : goods.getGoodsPics()) {
			if (goodsPic.getOrder()!= null && goodsPic.getOrder() == 0) {
				map.put("smallUrl", projectDeployUrl + goodsPic.getSource());
			}
			if (goodsPic.getOrder()!= null && goodsPic.getOrder() == 1) {
				map.put("largeUrl", projectDeployUrl + goodsPic.getSource());
			}				
		 }
      }
      mapList.add(map);
    }
    return mapList;
  }
  /**
   * 准备导出的SupplementRecord数据
   * 
   * @param response
   * @param lists
   * @return
   */
  public List<Map<String, String>> prepareExportSupplement(List<SupplementRecord> lists) {
	    List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
	    for (SupplementRecord record : lists) {
	      Map<String, String> map = new HashMap<String, String>();
	      map.put("sceneSn", record.getSceneSn());
	      map.put("sceneName", record.getSceneName());
	      map.put("cntrSn", record.getCntrSn());
	      map.put("channelSn", "");
	      if (record.getChannel() != null) {
	    	  map.put("channelSn", record.getChannel().getSn());
		  }
	      map.put("goodsSn", record.getGoodsSn());
	      map.put("goodsName", record.getGoodsName());
	      map.put("supplyCount", "");
	      if (record.getSupplyCount() != null) {
	    	  map.put("supplyCount", record.getSupplyCount()+"");
		  }
	      map.put("suppEndTime", "");
	      if (record.getSuppSum() != null) {
	    	  map.put("suppEndTime", TimeUtils.getDateFormatString("yyyy-MM-dd HH:mm:ss", record.getSuppSum().getSuppEndTime()));
		  }
	      map.put("suppPerson", "");
	      if (record.getSuppSum() != null) {
	    	  map.put("suppPerson", record.getSuppSum().getSuppName() + record.getSuppSum().getSuppMobile());
		  }
	      map.put("suppPic", "");
	      if (record.getSuppPic() != null) {
	    	  map.put("suppPic", record.getSuppPic().getSource());
		  }
	      mapList.add(map);
	    }
	    return mapList;
  }
  
  public List<Map<String, String>> prepareExportSupplementList(List<SupplementList> lists) {
	List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
    for (SupplementList record : lists) {
	      Map<String, String> map = new HashMap<String, String>();
	      map.put("sceneSn", record.getSceneSn());
	      map.put("sceneName", record.getSceneName());
	      map.put("cntrSn", record.getCntrSn());
	      map.put("channelSn", "");
	      if (record.getChannel() != null) {
	    	  map.put("channelSn", record.getChannel().getSn());
		  }
	      map.put("goodsSn", record.getGoodsSn());
	      map.put("goodsName", record.getGoodsName());
	      map.put("remainCount", "");
	      if (record.getRemainCount() != null) {
	    	  map.put("remainCount", record.getRemainCount()+"");
		  }
	      map.put("waitSupplyCount", "");
	      if (record.getWaitSupplyCount() != null) {
	    	  map.put("waitSupplyCount", record.getWaitSupplyCount()+"");
		  }
	      map.put("suppPerson", record.getSuppName() + record.getSuppMobile());
	      mapList.add(map);
	 }
	 return mapList;
  }
  
}
