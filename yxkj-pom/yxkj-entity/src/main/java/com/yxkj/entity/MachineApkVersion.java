package com.yxkj.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 机器中控apk版本
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:23:49
 */
@Entity
@Table(name = "t_machine_apk_version")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_machine_apk_version_sequence")
public class MachineApkVersion extends BaseEntity {

  private static final long serialVersionUID = 3310941691877457267L;

  /**
   * 版本号
   */
  private String versionName;

  /**
   * 版本序列号(用于比较是否是新版本)
   */
  private Integer versionCode = 0;
  /**
   */
  private String apkPath;

  /**
   * apk文件
   */
  private MultipartFile file;


  /**
   * 版本更新内容
   */
  private String updateContent;

  /**
   * 升级记录
   */
  private Set<MachineAppUpgrade> machineAppUpgrades = new HashSet<MachineAppUpgrade>();


  @OneToMany(mappedBy = "machineApkVersion")
  @JsonProperty
  public Set<MachineAppUpgrade> getMachineAppUpgrades() {
    return machineAppUpgrades;
  }

  public void setMachineAppUpgrades(Set<MachineAppUpgrade> machineAppUpgrades) {
    this.machineAppUpgrades = machineAppUpgrades;
  }

  @Column(length = 20)
  @JsonProperty
  public String getVersionName() {
    return versionName;
  }

  public void setVersionName(String versionName) {
    this.versionName = versionName;
  }

  @Column(length = 20)
  public Integer getVersionCode() {
    return versionCode;
  }

  public void setVersionCode(Integer versionCode) {
    this.versionCode = versionCode;
  }

  @Column(length = 200)
  @JsonProperty
  public String getApkPath() {
    return apkPath;
  }

  public void setApkPath(String apkPath) {
    this.apkPath = apkPath;
  }


  @Column(length = 500)
  @JsonProperty
  public String getUpdateContent() {
    return updateContent;
  }

  public void setUpdateContent(String updateContent) {
    this.updateContent = updateContent;
  }

  @Transient
  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }

}
