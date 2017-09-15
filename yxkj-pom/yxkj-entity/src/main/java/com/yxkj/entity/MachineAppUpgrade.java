package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

@Entity
@Table(name = "t_machineapp_upgrade", indexes = {@Index(name = "containerSnIndex",
    columnList = "containerSn")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_machineapp_upgrade_sequence")
public class MachineAppUpgrade extends BaseEntity {

  private static final long serialVersionUID = 3310941691877457267L;

  /**
   * 升级至版本号
   */
  private String machineApkVersion;

  /**
   * 货柜编号
   */
  private String containerSn;

  /**
   * 是否升级成功
   */
  private Boolean upgradeSucc;

  /**
   * 备注
   */
  private String remark;


  @Column(length = 20)
  public String getMachineApkVersion() {
    return machineApkVersion;
  }

  public void setMachineApkVersion(String machineApkVersion) {
    this.machineApkVersion = machineApkVersion;
  }

  @Column(length = 100)
  public String getContainerSn() {
    return containerSn;
  }

  public void setContainerSn(String containerSn) {
    this.containerSn = containerSn;
  }

  public Boolean getUpgradeSucc() {
    return upgradeSucc;
  }

  public void setUpgradeSucc(Boolean upgradeSucc) {
    this.upgradeSucc = upgradeSucc;
  }

  @Column(length = 200)
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }


}
