package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.SystemType;

/**
 * Entity - app广告
 * 
 * @author Andrea
 * @version 2017年9月15日 下午5:40:14
 */
@Entity
@Table(name = "t_ad_banner")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_ad_banner_sequence")
public class AdBanner extends BaseEntity {


  private static final long serialVersionUID = 1L;

  /**
   * banner link URL
   */
  private String linkUrl;

  /**
   * banner排序位置
   */
  private Integer bannerOrder;

  /**
   * 标题
   */
  private String title;

  // /**
  // * 内容
  // */
  // private String content;

  /**
   * 是否生效
   */
  private Boolean isActive;

  /**
   * 系统类型
   */
  private SystemType systemType;

  /**
   * 备注
   */
  private String remark;


  @Column(length = 100)
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public SystemType getSystemType() {
    return systemType;
  }

  public void setSystemType(SystemType systemType) {
    this.systemType = systemType;
  }

  @Column(length = 100)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  // @Column(length = 1000)
  // public String getContent() {
  // return content;
  // }
  //
  // public void setContent(String content) {
  // this.content = content;
  // }

  @Column(length = 200)
  public String getLinkUrl() {
    return linkUrl;
  }

  public void setLinkUrl(String linkUrl) {
    this.linkUrl = linkUrl;
  }

  public Integer getBannerOrder() {
    return bannerOrder;
  }

  public void setBannerOrder(Integer bannerOrder) {
    this.bannerOrder = bannerOrder;
  }


  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

}
