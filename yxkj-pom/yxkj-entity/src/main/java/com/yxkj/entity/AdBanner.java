package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.yxkj.entity.base.BaseEntity;

/**
 * 广告
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
   * banner名称
   */
  private String bannerName;

  /**
   * banner URL
   */
  private String bannerUrl;

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

  /**
   * 内容
   */
  private String content;

  /**
   * 是否生效
   */
  private Boolean isActive;

  /**
   * banner文件
   */
  private MultipartFile bannerPicture;


  @Column(length = 100)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Column(length = 5000)
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

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

  @Column(length = 100)
  public String getBannerName() {
    return bannerName;
  }

  public void setBannerName(String bannerName) {
    this.bannerName = bannerName;
  }

  @Column(length = 200)
  public String getBannerUrl() {
    return bannerUrl;
  }

  public void setBannerUrl(String bannerUrl) {
    this.bannerUrl = bannerUrl;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  @Transient
  public MultipartFile getBannerPicture() {
    return bannerPicture;
  }

  public void setBannerPicture(MultipartFile bannerPicture) {
    this.bannerPicture = bannerPicture;
  }
}
