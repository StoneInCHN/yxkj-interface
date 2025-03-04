package com.yxkj.json.request;

import java.util.List;

import com.yxkj.json.base.BaseRequest;

/**
 * 商品req
 * 
 * @author Andrea
 * @version 2017年10月9日 下午3:55:38
 */
public class GoodsInfoReq extends BaseRequest {

  /**
   * 商品对应的货道编号
   */
  private String cSn;

  /**
   * 货柜中控标识
   */
  private String cImei;

  /**
   * 商品类别ID
   */
  private Long cateId;

  /**
   * 商品
   */
  private List<String> gList;


  public List<String> getgList() {
    return gList;
  }

  public void setgList(List<String> gList) {
    this.gList = gList;
  }

  public Long getCateId() {
    return cateId;
  }

  public void setCateId(Long cateId) {
    this.cateId = cateId;
  }

  public String getcImei() {
    return cImei;
  }

  public void setcImei(String cImei) {
    this.cImei = cImei;
  }

  public String getcSn() {
    return cSn;
  }

  public void setcSn(String cSn) {
    this.cSn = cSn;
  }


}
