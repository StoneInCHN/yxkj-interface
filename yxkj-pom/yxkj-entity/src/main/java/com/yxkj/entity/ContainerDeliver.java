package com.yxkj.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;
import com.yxkj.entity.commonenum.CommonEnum.Gender;

/**
 * Entity - 用户实体
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:27:59
 */
@Entity
@Table(name = "t_end_user", indexes = {@Index(name = "cellPhoneNumIndex",
    columnList = "cellPhoneNum")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_end_user_sequence")
public class ContainerDeliver extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * 用户名
   */
  private String userName;

  /**
   * 用户头像
   */
  private String userPhoto;

  /**
   * 手机号
   */
  private String cellPhoneNum;

  /**
   * 登录密码
   */
  private String loginPwd;

  /**
   * 账号状态
   */
  private AccountStatus accountStatus;
  /**
   * 昵称
   */
  private String nickName;
  /**
   * 年龄
   */
  private Integer age;
  /**
   * 性别
   */
  private Gender gender;

  /**
   * 地址
   */
  private String address;

  /**
   * 用户所在地区
   */
  private Area area;

  /**
   * 极光push注册ID
   */
  private String jpushRegId;


  /**
   * 配送员消息
   */
  private Set<MsgDeliver> msgDelivers = new HashSet<MsgDeliver>();

  /**
   * 是否开启消息推送
   */
  private Boolean isPushMsg;


  public Boolean getIsPushMsg() {
    return isPushMsg;
  }

  public void setIsPushMsg(Boolean isPushMsg) {
    this.isPushMsg = isPushMsg;
  }


  @ManyToOne(fetch = FetchType.LAZY)
  public Area getArea() {
    return area;
  }

  public void setArea(Area area) {
    this.area = area;
  }


  @Column(length = 200)
  public String getUserPhoto() {
    return userPhoto;
  }

  public void setUserPhoto(String userPhoto) {
    this.userPhoto = userPhoto;
  }



  public AccountStatus getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(AccountStatus accountStatus) {
    this.accountStatus = accountStatus;
  }

  @Column(length = 100)
  @JsonProperty
  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  @Column(length = 200)
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(length = 100)
  public String getJpushRegId() {
    return jpushRegId;
  }

  public void setJpushRegId(String jpushRegId) {
    this.jpushRegId = jpushRegId;
  }

  @Column(length = 50)
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Column(length = 20, nullable = false)
  @JsonProperty
  public String getCellPhoneNum() {
    return cellPhoneNum;
  }

  public void setCellPhoneNum(String cellPhoneNum) {
    this.cellPhoneNum = cellPhoneNum;
  }

  @Column(length = 50)
  public String getLoginPwd() {
    return loginPwd;
  }

  public void setLoginPwd(String loginPwd) {
    this.loginPwd = loginPwd;
  }

  @OneToMany(mappedBy = "deliver", cascade = CascadeType.ALL)
  public Set<MsgDeliver> getMsgDelivers() {
    return msgDelivers;
  }

  public void setMsgDelivers(Set<MsgDeliver> msgDelivers) {
    this.msgDelivers = msgDelivers;
  }

}
