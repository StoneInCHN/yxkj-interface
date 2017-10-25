package com.yxkj.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yxkj.entity.Goods;
import com.yxkj.entity.GoodsPic;
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
  
}
