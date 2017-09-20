package com.yxkj.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.WarningCon;

/**
 * Entity - 预警值管理
 * 
 * @author Andrea
 * @version 2017年9月20日 下午6:47:55
 */
@Entity
@Table(name = "t_warn_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_warn_config_sequence")
public class WarningConfig extends BaseEntity {


  private static final long serialVersionUID = -1649056464007668623L;

  /**
   * 货柜类型
   */
  private ContainerCategory cntrCategory;

  /**
   * 预警条件
   */
  private WarningCon warnCon;

  /**
   * 预警数量
   */
  private Integer count;

  public ContainerCategory getCntrCategory() {
    return cntrCategory;
  }

  public void setCntrCategory(ContainerCategory cntrCategory) {
    this.cntrCategory = cntrCategory;
  }

  public WarningCon getWarnCon() {
    return warnCon;
  }

  public void setWarnCon(WarningCon warnCon) {
    this.warnCon = warnCon;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

}
