package com.yxkj.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 货柜补货记录
 * 
 * @author Andrea
 * @version 2017年9月19日 下午5:42:39
 */
@Entity
@Table(name = "t_supp_rec")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_supp_rec_sequence")
public class SupplementRecord extends BaseEntity {

  private static final long serialVersionUID = -2197390136625086557L;

  /**
   * 补货货道
   */
  private ContainerChannel channel;

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
  private List<SupplementPic> suppPics = new ArrayList<SupplementPic>();

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

  @Valid
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  @CollectionTable(name = "t_supp_goods_image")
  public List<SupplementPic> getSuppPics() {
    return suppPics;
  }

  public void setSuppPics(List<SupplementPic> suppPics) {
    this.suppPics = suppPics;
  }

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



}
