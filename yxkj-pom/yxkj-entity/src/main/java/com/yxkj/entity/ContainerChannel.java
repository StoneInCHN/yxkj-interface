package com.yxkj.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.ChannelGoodsStatus;

/**
 * Entity - 货柜货道
 * 
 * @author Andrea
 * @version 2017年9月19日 下午3:42:39
 */
@Entity
@Table(name = "t_cntr_channel")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_cntr_channel_sequence")
public class ContainerChannel extends BaseEntity {

  private static final long serialVersionUID = -2197390136625086557L;

  /**
   * 所属商品
   */
  private Goods goods;

  /**
   * 商品实际金额
   */
  private BigDecimal price;

  /**
   * 货道编号
   */
  private String sn;

  /**
   * 物理货道号
   */
  private String channelNum;

  /**
   * 货道容量
   */
  private Integer capacity;

  /**
   * 线上锁定的商品数量
   */
  private Integer onlineLock;
  
  /**
   * 线下扫描，输码锁定的商品数量
   */
  private Integer offlineRemoteLock;

  /**
   * 线下中控锁定的商品数量
   */
  private Integer offlineLocalLock;
  
  /**
   * 剩余货量
   */
  private Integer surplus;

  /**
   * 预警值
   */
  private Integer warning;

  /**
   * 货道商品状态
   */
  @Transient
  private ChannelGoodsStatus chgsStatus;

  /**
   * 货道二维码url
   */
  private String qrCodeUrl;

  /**
   * 所属货柜
   */
  private VendingContainer cntr;



  @ManyToOne(fetch = FetchType.LAZY)
  public VendingContainer getCntr() {
    return cntr;
  }

  public void setCntr(VendingContainer cntr) {
    this.cntr = cntr;
  }

  @Column(length = 200)
  public String getQrCodeUrl() {
    return qrCodeUrl;
  }

  public void setQrCodeUrl(String qrCodeUrl) {
    this.qrCodeUrl = qrCodeUrl;
  }

  public ChannelGoodsStatus getChgsStatus() {
	if (this.surplus < this.warning) {
		return ChannelGoodsStatus.LACK;
	}else {
		return ChannelGoodsStatus.SUFFICIENT;
	}
  }

  public void setChgsStatus(ChannelGoodsStatus chgsStatus) {
    this.chgsStatus = chgsStatus;
  }


  public Integer getCapacity() {
    return capacity;
  }


  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }


  public Integer getSurplus() {
    return surplus;
  }


  public void setSurplus(Integer surplus) {
    this.surplus = surplus;
  }


  public Integer getWarning() {
    return warning;
  }


  public void setWarning(Integer warning) {
    this.warning = warning;
  }


  @Column(length = 50)
  public String getSn() {
    return sn;
  }


  public void setSn(String sn) {
    this.sn = sn;
  }

  @Column(length = 50)
  public String getChannelNum() {
    return channelNum;
  }


  public void setChannelNum(String channelNum) {
    this.channelNum = channelNum;
  }


  @ManyToOne
  public Goods getGoods() {
    return goods;
  }


  public void setGoods(Goods goods) {
    this.goods = goods;
  }


  @Column(scale = 2, precision = 10)
  public BigDecimal getPrice() {
    return price;
  }


  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public Integer getOnlineLock() {
    return onlineLock;
  }

  public void setOnlineLock(Integer onlineLock) {
    this.onlineLock = onlineLock;
  }

  public Integer getOfflineRemoteLock() {
    return offlineRemoteLock;
  }

  public void setOfflineRemoteLock(Integer offlineRemoteLock) {
    this.offlineRemoteLock = offlineRemoteLock;
  }

  public Integer getOfflineLocalLock() {
    return offlineLocalLock;
  }

  public void setOfflineLocalLock(Integer offlineLocalLock) {
    this.offlineLocalLock = offlineLocalLock;
  }
}
