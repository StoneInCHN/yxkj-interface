package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 机器apk升级记录
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:24:10
 */
@Entity
@Table(name = "t_machineapp_upgrade", indexes = {@Index(name = "sceneIdIndex",
    columnList = "sceneId")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_machineapp_upgrade_sequence")
public class MachineAppUpgrade extends BaseEntity {

  private static final long serialVersionUID = 3310941691877457267L;

  /**
   * 升级至版本号
   */
  private MachineApkVersion machineApkVersion;

  /**
   * 优享空间名称
   */
  private String sceneName;

  /**
   * Long
   */
  private Long sceneId;

  /**
   * 是否升级成功
   */
  private Boolean upgradeSucc;

  /**
   * 备注
   */
  private String remark;


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

  @ManyToOne(fetch = FetchType.LAZY)
  public MachineApkVersion getMachineApkVersion() {
    return machineApkVersion;
  }

  public void setMachineApkVersion(MachineApkVersion machineApkVersion) {
    this.machineApkVersion = machineApkVersion;
  }



}
