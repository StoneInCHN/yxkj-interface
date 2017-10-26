package com.yxkj.entity;

import java.math.BigDecimal;
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

/**
 * Entity - 物业实体
 * 
 */
@Entity
@Table(name = "t_property_keeper", 
indexes = {@Index(name = "cellPhoneNumIndex", columnList = "cellPhoneNum"),
		   @Index(name = "userNameIndex", columnList = "userName")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_container_keeper_sequence")
public class PropertyKeeper extends BaseEntity {

  private static final long serialVersionUID = 1L;
  
  /**
	* 姓名
  */
  private String realName;

  /**
   * 用户名
   */
  private String userName;


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
   * 分润点(物业)
   */
  private BigDecimal fenRunPoint;

  /**
   * 地址
   */
  private String address;

  /**
   * 所在地区
   */
  private Area area;

  /**
   * 管理的优享空间
   */
  private Set<Scene> scenes = new HashSet<Scene>();

  @OneToMany(mappedBy = "cntrKeeper", cascade = CascadeType.PERSIST)
  public Set<Scene> getScenes() {
    return scenes;
  }

  public void setScenes(Set<Scene> scenes) {
    this.scenes = scenes;
  }


  @ManyToOne(fetch = FetchType.LAZY)
  public Area getArea() {
    return area;
  }

  public void setArea(Area area) {
    this.area = area;
  }


  public AccountStatus getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(AccountStatus accountStatus) {
    this.accountStatus = accountStatus;
  }

  @Column(length = 200)
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(length = 50, unique = true)
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
  
  @Column(length = 50)
  public String getRealName() {
	return realName;
  }

  public void setRealName(String realName) {
	this.realName = realName;
  }
  
  @Column(scale = 4, precision = 10)
  public BigDecimal getFenRunPoint() {
	return fenRunPoint;
  }

  public void setFenRunPoint(BigDecimal fenRunPoint) {
	this.fenRunPoint = fenRunPoint;
  }

}
