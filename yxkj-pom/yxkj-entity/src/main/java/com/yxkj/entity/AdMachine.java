package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 中控广告
 * 
 * @author Andrea
 * @version 2017年9月15日 下午5:40:14
 */
@Entity
@Table(name = "t_ad_machine")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_ad_machine_sequence")
public class AdMachine extends BaseEntity {


  private static final long serialVersionUID = 1L;

  /**
   * 优享空间名称
   */
  private String sceneName;

  /**
   * 优享空间ID
   */
  private Long sceneId;

  /**
   * 中控柜ID
   */
  private Long cntrId;

  /**
   * 中控柜编号
   */
  private String cntrSn;

  /**
   * 标题
   */
  private String title;


  /**
   * 是否生效
   */
  private Boolean isActive;

  /**
   * 视频A
   */
  private String adA;

  /**
   * 视频B
   */
  private String adB;

  /**
   * 图片1
   */
  private String adC;

  /**
   * 图片2
   */
  private String adD;

  /**
   * 图片3
   */
  private String adE;


  /**
   * 备注
   */
  private String remark;


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

  @Column(length = 50)
  public String getSceneName() {
    return sceneName;
  }

  public void setSceneName(String sceneName) {
    this.sceneName = sceneName;
  }

  public Long getSceneId() {
    return sceneId;
  }

  public void setSceneId(Long sceneId) {
    this.sceneId = sceneId;
  }

  @Column(length = 200)
  public String getAdA() {
    return adA;
  }

  public void setAdA(String adA) {
    this.adA = adA;
  }

  @Column(length = 200)
  public String getAdB() {
    return adB;
  }

  public void setAdB(String adB) {
    this.adB = adB;
  }

  @Column(length = 200)
  public String getAdC() {
    return adC;
  }

  public void setAdC(String adC) {
    this.adC = adC;
  }

  @Column(length = 200)
  public String getAdD() {
    return adD;
  }

  public void setAdD(String adD) {
    this.adD = adD;
  }

  @Column(length = 200)
  public String getAdE() {
    return adE;
  }

  public void setAdE(String adE) {
    this.adE = adE;
  }

  @Column(length = 100)
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }


  @Column(length = 100)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

}
