package com.yxkj.entity;

import javax.persistence.*;

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
   * 优享空间
   */
  private Scene scene;


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
  private String advA;

  /**
   * 视频B
   */
  private String advB;

  /**
   * 图片1
   */
  private String advC;

  /**
   * 图片2
   */
  private String advD;

  /**
   * 图片3
   */
  private String advE;


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

  @Column(length = 200)
  public String getAdvA() {
    return advA;
  }

  public void setAdvA(String advA) {
    this.advA = advA;
  }

  @Column(length = 200)
  public String getAdvB() {
    return advB;
  }

  public void setAdvB(String advB) {
    this.advB = advB;
  }

  @Column(length = 200)
  public String getAdvC() {
    return advC;
  }

  public void setAdvC(String advC) {
    this.advC = advC;
  }

  @Column(length = 200)
  public String getAdvD() {
    return advD;
  }

  public void setAdvD(String advD) {
    this.advD = advD;
  }

  @Column(length = 200)
  public String getAdvE() {
    return advE;
  }

  public void setAdvE(String advE) {
    this.advE = advE;
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

  @ManyToOne
  public Scene getScene() {
    return scene;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
  }
}
