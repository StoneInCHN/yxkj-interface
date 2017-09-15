package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.SystemType;

/**
 * 支付方式配置(不同系统支付方式单独配置)
 * 
 */
@Entity
@Table(name = "t_payment_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_payment_config_sequence")
public class PaymentConfig extends BaseEntity {


  private static final long serialVersionUID = -1684057707764082356L;

  /**
   * 系统名称
   */
  private SystemType systemType;
  /**
   * 支付方式
   */
  private String configValue;

  /**
   * 排序
   */
  private Integer configOrder;

  /**
   * 是否启用
   */
  private Boolean isEnabled;

  /**
   * 描述
   */
  private String remark;



  public SystemType getSystemType() {
    return systemType;
  }

  public void setSystemType(SystemType systemType) {
    this.systemType = systemType;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  public Integer getConfigOrder() {
    return configOrder;
  }

  public void setConfigOrder(Integer configOrder) {
    this.configOrder = configOrder;
  }

  @Column(length = 100)
  public String getConfigValue() {
    return configValue;
  }

  public void setConfigValue(String configValue) {
    this.configValue = configValue;
  }

  @Column(length = 200)
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
