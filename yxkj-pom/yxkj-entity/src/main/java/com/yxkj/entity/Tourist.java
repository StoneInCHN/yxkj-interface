package com.yxkj.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.Gender;
import com.yxkj.entity.commonenum.CommonEnum.UserChannel;

/**
 * Entity - 游客
 * 
 * @author Andrea
 * @version 2017年9月19日 上午9:40:06
 */
@Entity
@Table(name = "t_tourist", indexes = {@Index(name = "sceneIdIndex", columnList = "sceneId")})
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
   * 微信账号昵称
   */
  private String wechatNickName;

  /**
   * 支付宝账号
   */
  private String alipayName;

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
   * 场景ID
   */
  private Long sceneId;


  @Column(length = 50)
  public String getAlipayName() {
    return alipayName;
  }

  public void setAlipayName(String alipayName) {
    this.alipayName = alipayName;
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


  @Column(length = 50)
  public String getWechatNickName() {
    return wechatNickName;
  }

  public void setWechatNickName(String wechatNickName) {
    this.wechatNickName = wechatNickName;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
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

}
