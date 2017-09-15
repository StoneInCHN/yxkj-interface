package com.yxkj.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
public class EndUser extends BaseEntity {

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
   * 用户二维码
   */
  private String qrImage;

  /**
   * 用户收藏的商品
   */
  private Set<Goods> favoriteGoods = new HashSet<Goods>();


  /**
   * 微信账号昵称
   */
  private String wechatNickName;

  /**
   * 用户消息
   */
  private Set<MsgEndUser> msgEndUsers = new HashSet<MsgEndUser>();


  @Column(length = 200)
  public String getQrImage() {
    return qrImage;
  }

  public void setQrImage(String qrImage) {
    this.qrImage = qrImage;
  }

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


  @Column(length = 50)
  public String getWechatNickName() {
    return wechatNickName;
  }

  public void setWechatNickName(String wechatNickName) {
    this.wechatNickName = wechatNickName;
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


  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinTable(name = "t_endUser_favorite_goods")
  @OrderBy("createDate desc")
  public Set<Goods> getFavoriteGoods() {
    return favoriteGoods;
  }

  public void setFavoriteGoods(Set<Goods> favoriteGoods) {
    this.favoriteGoods = favoriteGoods;
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


  @OneToMany(mappedBy = "endUser", cascade = CascadeType.ALL)
  public Set<MsgEndUser> getMsgEndUsers() {
    return msgEndUsers;
  }

  public void setMsgEndUsers(Set<MsgEndUser> msgEndUsers) {
    this.msgEndUsers = msgEndUsers;
  }

}
