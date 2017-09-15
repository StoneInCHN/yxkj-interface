package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.DictKey;

/**
 * Entity - 数据字典
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:23:31
 */
@Entity
@Table(name = "t_dict_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_dict_config_sequence")
public class DictConfig extends BaseEntity {


  private static final long serialVersionUID = -1684057707764082356L;

  /**
   * 配置项名称
   */
  private DictKey configKey;
  /**
   * 配置项值
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

  public DictKey getConfigKey() {
    return configKey;
  }

  public void setConfigKey(DictKey configKey) {
    this.configKey = configKey;
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

  @Column(length = 5000)
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
