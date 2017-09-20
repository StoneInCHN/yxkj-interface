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

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;
import com.yxkj.entity.commonenum.CommonEnum.Gender;

/**
 * Entity - 管家实体
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:27:59
 */
@Entity
@Table(name = "t_container_keeper", indexes = {@Index(name = "cellPhoneNumIndex",
    columnList = "cellPhoneNum")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_container_keeper_sequence")
public class ContainerKeeper extends BaseEntity {

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
  private Set<MsgKeeper> msgKeepers = new HashSet<MsgKeeper>();

  /**
   * 是否开启消息推送
   */
  private Boolean isPushMsg;


  /**
   * 管家管理的优享空间
   */
  private Set<Scene> scenes = new HashSet<Scene>();

  @OneToMany(mappedBy = "cntrKeeper", cascade = CascadeType.PERSIST)
  public Set<Scene> getScenes() {
    return scenes;
  }

  public void setScenes(Set<Scene> scenes) {
    this.scenes = scenes;
  }

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

  @OneToMany(mappedBy = "keeper", cascade = CascadeType.ALL)
  public Set<MsgKeeper> getMsgKeepers() {
    return msgKeepers;
  }

  public void setMsgKeepers(Set<MsgKeeper> msgKeepers) {
    this.msgKeepers = msgKeepers;
  }

}
