package com.yxkj.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.DeviceType;
import com.yxkj.entity.commonenum.CommonEnum.Gender;
import com.yxkj.entity.commonenum.CommonEnum.UserChannel;

/**
 * Entity - 游客
 * 
 * @author Andrea
 * @version 2017年9月19日 上午9:40:06
 */
@Entity
@Table(name = "t_tourist", indexes = {@Index(name = "sceneIdIndex", columnList = "sceneId"),
    @Index(name = "userNameIndex", columnList = "userName")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_tourist_sequence")
public class Tourist extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 用户识别码
   */
  private String userName;

  /**
   * 手机号
   */
  private String cellPhoneNum;

  /**
   * 性别
   */
  private Gender gender;

  /**
   * 账号昵称
   */
  private String nickName;

  /**
   * 用户获取渠道
   */
  private UserChannel userChannel;

  /**
   * 注册时间
   */
  private Date regTime;

  /**
   * 优享空间名称
   */
  private String sceneName;

  /**
   * 优享空间ID
   */
  private Long sceneId;

  /**
   * 公司ID
   */
  private Long companyId;

  /**
   * 公司名称
   */
  private String companyName;

  /**
   * 购买场景的设备
   */
  private DeviceType deviceType;


  public DeviceType getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(DeviceType deviceType) {
    this.deviceType = deviceType;
  }

  public Long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(Long companyId) {
    this.companyId = companyId;
  }

  @Column(length = 50)
  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public Date getRegTime() {
    return regTime;
  }

  public void setRegTime(Date regTime) {
    this.regTime = regTime;
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


  public UserChannel getUserChannel() {
    return userChannel;
  }

  public void setUserChannel(UserChannel userChannel) {
    this.userChannel = userChannel;
  }

  @JsonProperty
  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  @JsonProperty
  @Column(length = 50)
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @JsonProperty
  @Column(length = 20)
  public String getCellPhoneNum() {
    return cellPhoneNum;
  }

  public void setCellPhoneNum(String cellPhoneNum) {
    this.cellPhoneNum = cellPhoneNum;
  }

  @JsonProperty
  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

}
