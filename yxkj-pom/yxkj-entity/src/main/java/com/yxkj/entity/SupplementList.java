package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 待补货清单
 * 
 * @author Andrea
 * @version 2017年9月20日 下午5:35:58
 */
@Entity
@Table(name = "t_supp_list", indexes = {@Index(name = "sceneIdIndex", columnList = "sceneId")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_supp_list_sequence")
public class SupplementList extends BaseEntity {


  private static final long serialVersionUID = -2197390136625086557L;

  /**
   * 待补货货道
   */
  private ContainerChannel channel;

  /**
   * 货柜ID
   */
  private Long cntrId;

  /**
   * 货柜编号
   */
  private String cntrSn;

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
   * 待补货数量
   */
  private Integer waitSupplyCount;

  /**
   * 剩余数量
   */
  private Integer remainCount;

  /**
   * 补货人姓名
   */
  private String suppName;

  /**
   * 补货人手机号
   */
  private String suppMobile;

  /**
   * 补货人ID
   */
  private Long suppId;



  public Long getSceneId() {
    return sceneId;
  }

  public void setSceneId(Long sceneId) {
    this.sceneId = sceneId;
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

  public Integer getRemainCount() {
    return remainCount;
  }

  public void setRemainCount(Integer remainCount) {
    this.remainCount = remainCount;
  }


  public Long getSuppId() {
    return suppId;
  }

  public void setSuppId(Long suppId) {
    this.suppId = suppId;
  }

  @OneToOne
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


  @Column(length = 20)
  public String getSuppName() {
    return suppName;
  }

  public void setSuppName(String suppName) {
    this.suppName = suppName;
  }

  @Column(length = 20)
  public String getSuppMobile() {
    return suppMobile;
  }

  public void setSuppMobile(String suppMobile) {
    this.suppMobile = suppMobile;
  }

  public Integer getWaitSupplyCount() {
    return waitSupplyCount;
  }

  public void setWaitSupplyCount(Integer waitSupplyCount) {
    this.waitSupplyCount = waitSupplyCount;
  }


}
