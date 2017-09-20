package com.yxkj.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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
   * 补货数量
   */
  private Integer supplyCount;

  /**
   * 剩余数量
   */
  private Integer remainCount;

  /**
   * 补货时间
   */
  private Date supplyTime;

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

  /**
   * 管家上传的补货图片
   */
  private List<SupplementPic> suppPics = new ArrayList<SupplementPic>();

  /**
   * 补货是否完成
   */
  private Boolean suppFinish;


  public Integer getRemainCount() {
    return remainCount;
  }

  public void setRemainCount(Integer remainCount) {
    this.remainCount = remainCount;
  }

  public Boolean getSuppFinish() {
    return suppFinish;
  }

  public void setSuppFinish(Boolean suppFinish) {
    this.suppFinish = suppFinish;
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

  public Date getSupplyTime() {
    return supplyTime;
  }

  public void setSupplyTime(Date supplyTime) {
    this.supplyTime = supplyTime;
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



}
