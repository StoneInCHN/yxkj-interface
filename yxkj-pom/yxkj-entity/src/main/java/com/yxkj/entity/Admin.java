package com.yxkj.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;

/**
 * Entity-运营管理员
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:22:29
 */
@Entity
@Table(name = "t_admin")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_admin_sequence")
public class Admin extends BaseEntity {

  private static final long serialVersionUID = -7519486823153844426L;


  /** 用户名 */
  private String userName;

  /** 密码 */
  private String password;

  /** E-mail */
  private String email;

  /** 姓名 */
  private String name;


  /** 最后登录日期 */
  private Date loginDate;

  /** 最后登录IP */
  private String loginIp;

  /** 帐号状态 */
  private AccountStatus adminStatus;

  /** 角色 */
  private Set<Role> roles = new HashSet<Role>();

  /**
   * 是否为内置账户
   */
  private Boolean isSystem;

  
  /**
   * 获取用户名
   * 
   * @return 用户名
   */
  @JsonProperty
  @NotEmpty(groups = Save.class)
  @Length(min = 2, max = 20)
  @Column(nullable = false, updatable = false, unique = true, length = 100)
  public String getUserName() {
	return userName;
  }
  /**
   * 设置用户名
   * 
   * @param userName 用户名
   */
  public void setUserName(String userName) {
	this.userName = userName;
  }

/**
   * 获取密码
   * 
   * @return 密码
   */
  @NotEmpty(groups = Save.class)
  @Pattern(regexp = "^[^\\s&\"<>]+$")
  @Length(min = 4, max = 20)
  @Column(nullable = false)
  public String getPassword() {
    return password;
  }

  /**
   * 设置密码
   * 
   * @param password 密码
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * 获取E-mail
   * 
   * @return E-mail
   */
  @NotEmpty
  @Email
  @Length(max = 200)
  @Column(nullable = false)
  @JsonProperty
  public String getEmail() {
    return email;
  }

  /**
   * 设置E-mail
   * 
   * @param email E-mail
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * 获取姓名
   * 
   * @return 姓名
   */
  @Length(max = 200)
  public String getName() {
    return name;
  }

  /**
   * 设置姓名
   * 
   * @param name 姓名
   */
  public void setName(String name) {
    this.name = name;
  }



  /**
   * 获取最后登录日期
   * 
   * @return 最后登录日期
   */
  @JsonProperty
  public Date getLoginDate() {
    return loginDate;
  }

  /**
   * 设置最后登录日期
   * 
   * @param loginDate 最后登录日期
   */
  public void setLoginDate(Date loginDate) {
    this.loginDate = loginDate;
  }

  /**
   * 获取最后登录IP
   * 
   * @return 最后登录IP
   */
  @JsonProperty
  public String getLoginIp() {
    return loginIp;
  }

  /**
   * 设置最后登录IP
   * 
   * @param loginIp 最后登录IP
   */
  public void setLoginIp(String loginIp) {
    this.loginIp = loginIp;
  }


  /**
   * 获取账号状态
   * 
   * @return 账号状态
   */
  @Column(nullable = false)
  public AccountStatus getAdminStatus() {
    return adminStatus;
  }

  /**
   * 设置账号状态
   * 
   * @param adminStatus 账号状态
   */
  public void setAdminStatus(AccountStatus adminStatus) {
    this.adminStatus = adminStatus;
  }

  /**
   * 获取角色
   * 
   * @return 角色
   */
  @JsonProperty
  @NotEmpty
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "t_admin_role")
  public Set<Role> getRoles() {
    return roles;
  }

  /**
   * 设置角色
   * 
   * @param roles 角色
   */
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  @Column(length = 4)
  public Boolean getIsSystem() {
    return isSystem;
  }

  public void setIsSystem(Boolean isSystem) {
    this.isSystem = isSystem;
  }


}
