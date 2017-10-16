package com.yxkj.shelf.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yxkj.entity.Company;
import com.yxkj.entity.Goods;
import com.yxkj.entity.GoodsPic;
import com.yxkj.entity.ShelfOrder;
import com.yxkj.entity.Tourist;
import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;
import com.yxkj.entity.commonenum.CommonEnum.Gender;
import com.yxkj.entity.commonenum.CommonEnum.ShelfOrderStatus;
import com.yxkj.entity.commonenum.CommonEnum.UserChannel;
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
      map.put("goodsUrl", "");
      if (goods.getGoodsPics() != null && goods.getGoodsPics().size() > 0) {
    	  GoodsPic goodsPic = goods.getGoodsPics().get(0);
    	  if (goodsPic !=null ) {
    		  map.put("goodsUrl", projectDeployUrl + goodsPic.getSource());
    		  System.out.println(projectDeployUrl + goodsPic.getSource());
		}
      }
      mapList.add(map);
    }
    return mapList;
  }
  /**
   * 准备导出的Tourist数据
   * 
   * @param response
   * @param lists
   * @return
   */
  public List<Map<String, String>> prepareExportTourist(List<Tourist> lists) {
    List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
    for (Tourist tourist : lists) {
      Map<String, String> map = new HashMap<String, String>();
      map.put("id", tourist.getId().toString());
      map.put("userName", tourist.getUserName());
      map.put("cellPhoneNum", tourist.getCellPhoneNum());
      map.put("companyName", tourist.getCompanyName());
      map.put("nickName", tourist.getNickName());
      map.put("regTime", tourist.getRegTime()!=null?TimeUtils.getDateFormatString("yyyy-MM-dd HH:mm:ss", tourist.getRegTime()):"");
      map.put("gender", "");
      if (tourist.getGender() != null) {
          if (tourist.getGender() == Gender.FEMALE) {
            map.put("gender", "女");
          }
          if (tourist.getGender() == Gender.MALE) {
            map.put("gender", "男");
          }
      }
      
      map.put("userChannel", "");
      if (tourist.getUserChannel() != null) {
          if (tourist.getUserChannel() == UserChannel.ALIPAY) {
            map.put("userChannel", "支付宝");
          }
          if (tourist.getUserChannel() == UserChannel.WECHAT) {
              map.put("userChannel", "微信");
            }
      }
      mapList.add(map);
    }
    return mapList;
  }
  /**
   * 准备导出的Company数据
   * 
   * @param response
   * @param lists
   * @return
   */
  public List<Map<String, String>> prepareExportCompany(List<Company> lists) {
    List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
    for (Company company : lists) {
      Map<String, String> map = new HashMap<String, String>();
      map.put("sn", company.getSn());
      map.put("fullName", company.getFullName());
      map.put("displayName", company.getDisplayName());
      map.put("contactPerson", company.getContactPerson());
      map.put("contactPhone", company.getContactPerson());
      map.put("area", company.getArea()!=null?company.getArea().getFullName():"");
      map.put("address", company.getAddress());
      map.put("remark", company.getRemark());
      mapList.add(map);
    }
    return mapList;
  }
  /**
   * 准备导出的ShelfOrder数据
   * 
   * @param response
   * @param lists
   * @return
   */
  public List<Map<String, String>> prepareExportShelfOrder(List<ShelfOrder> lists) {
    List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
    for (ShelfOrder shelfOrder : lists) {
      Map<String, String> map = new HashMap<String, String>();
      map.put("sn", shelfOrder.getSn());
      map.put("company", shelfOrder.getComp()!=null?shelfOrder.getComp().getDisplayName():"");
      map.put("touristNickName", shelfOrder.getTourist()!=null?shelfOrder.getTourist().getNickName():"");
      map.put("touristUserName", shelfOrder.getTourist()!=null?shelfOrder.getTourist().getUserName():"");
      map.put("paymentType", shelfOrder.getPaymentType());
      map.put("paymentTime", shelfOrder.getPaymentTime()!=null?TimeUtils.getDateFormatString("yyyy-MM-dd HH:mm:ss", shelfOrder.getPaymentTime()):"");
      map.put("amount", shelfOrder.getAmount()!=null?shelfOrder.getAmount().toString():"");
      map.put("goodsCount", shelfOrder.getGoodsCount()+"");
      map.put("status", "");
      if (shelfOrder.getStatus() != null) {
          if (shelfOrder.getStatus() == ShelfOrderStatus.UNPAID) {
            map.put("status", "未交易");
          }
          if (shelfOrder.getStatus() == ShelfOrderStatus.PAID) {
              map.put("status", "交易成功");
          }
          if (shelfOrder.getStatus() == ShelfOrderStatus.PAID_FAILED) {
              map.put("status", "交易失败");
          }
      }
      mapList.add(map);
    }
    return mapList;
  }
  
}
