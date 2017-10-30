package com.yxkj.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 货柜补货记录
 * 
 * @author Andrea
 * @version 2017年9月19日 下午5:42:39
 */
@Entity
@Table(name = "t_supp_rec", indexes = {@Index(name = "sceneIdIndex", columnList = "sceneId")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_supp_rec_sequence")
public class SupplementRecord extends BaseEntity {

  private static final long serialVersionUID = -2197390136625086557L;

  /**
   * 补货货道
   */
  private ContainerChannel channel;

  /**
   * 优享空间ID
   */
  private Long sceneId;

  /**
   * 优享空间编号
   */
  private String sceneSn;

  /**
   * 优享空间地址名称
   */
  private String sceneName;

  /**
   * 商品编号
   */
  private String goodsSn;

  /**
   * 商品名称
   */
  private String goodsName;

  /**
   * 实际补货数量
   */
  private Integer supplyCount;

  /**
   * 待补货数量
   */
  private Integer waitSupplyCount;


  /**
   * 补货人ID
   */
  private Long suppId;

  /**
   * 管家上传的补货图片
   */
  private SupplementPic suppPic;

  /**
   * 货柜ID
   */
  private Long cntrId;

  /**
   * 货柜编号
   */
  private String cntrSn;

  /**
   * 补货汇总记录(按一组机器)
   */
  private SupplementSumRec suppSum;
  
  public SupplementRecord() {}
  
  public SupplementRecord(ContainerChannel channel, Long sceneId, String sceneSn, String sceneName,
      String goodsSn, String goodsName, Integer supplyCount, Integer waitSupplyCount, Long suppId,
      Long cntrId, String cntrSn) {
    super();
    this.channel = channel;
    this.sceneId = sceneId;
    this.sceneSn = sceneSn;
    this.sceneName = sceneName;
    this.goodsSn = goodsSn;
    this.goodsName = goodsName;
    this.supplyCount = supplyCount;
    this.waitSupplyCount = waitSupplyCount;
    this.suppId = suppId;
    this.cntrId = cntrId;
    this.cntrSn = cntrSn;
  }

  public Long getSceneId() {
    return sceneId;
  }

  public void setSceneId(Long sceneId) {
    this.sceneId = sceneId;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public SupplementSumRec getSuppSum() {
    return suppSum;
  }

  public void setSuppSum(SupplementSumRec suppSum) {
    this.suppSum = suppSum;
  }

  public Long getCntrId() {
    return cntrId;
  }

  public void setCntrId(Long cntrId) {
    this.cntrId = cntrId;
  }

  @Column(length = 50)
  public String getCntrSn() {
    return cntrSn;
  }

  public void setCntrSn(String cntrSn) {
    this.cntrSn = cntrSn;
  }

  public Long getSuppId() {
    return suppId;
  }

  public void setSuppId(Long suppId) {
    this.suppId = suppId;
  }

  @ManyToOne
  public SupplementPic getSuppPic() {
    return suppPic;
  }

  public void setSuppPic(SupplementPic suppPic) {
    this.suppPic = suppPic;
  }

  @ManyToOne
  public ContainerChannel getChannel() {
    return channel;
  }

  public void setChannel(ContainerChannel channel) {
    this.channel = channel;
  }

  @Column(length = 30)
  public String getSceneSn() {
    return sceneSn;
  }

  public void setSceneSn(String sceneSn) {
    this.sceneSn = sceneSn;
  }

  @Column(length = 30)
  public String getSceneName() {
    return sceneName;
  }

  public void setSceneName(String sceneName) {
    this.sceneName = sceneName;
  }

  @Column(length = 30)
  public String getGoodsSn() {
    return goodsSn;
  }

  public void setGoodsSn(String goodsSn) {
    this.goodsSn = goodsSn;
  }

  @Column(length = 100)
  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public Integer getSupplyCount() {
    return supplyCount;
  }

  public void setSupplyCount(Integer supplyCount) {
    this.supplyCount = supplyCount;
  }


  public Integer getWaitSupplyCount() {
    return waitSupplyCount;
  }

  public void setWaitSupplyCount(Integer waitSupplyCount) {
    this.waitSupplyCount = waitSupplyCount;
  }

  @Override
  public String toString() {
    return "SupplementRecord [channel=" + channel + ", sceneId=" + sceneId + ", sceneSn=" + sceneSn
        + ", sceneName=" + sceneName + ", goodsSn=" + goodsSn + ", goodsName=" + goodsName
        + ", supplyCount=" + supplyCount + ", waitSupplyCount=" + waitSupplyCount + ", suppId="
        + suppId + ", suppPic=" + suppPic + ", cntrId=" + cntrId + ", cntrSn=" + cntrSn
        + ", suppSum=" + suppSum + "]";
  }

}
