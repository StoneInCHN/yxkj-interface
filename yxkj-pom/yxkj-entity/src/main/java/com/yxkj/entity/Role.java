package com.yxkj.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 运营平台角色
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:26:46
 */
@Entity
@Table(name = "t_role")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_role_sequence")
public class Role extends BaseEntity {

  private static final long serialVersionUID = -6614052029623997372L;

  /** 名称 */
  private String name;

  /** 是否内置 */
  private Boolean isSystem;

  /** 描述 */
  private String description;

//  /** 后台管理系统权限 */
//  private List<String> authorities = new ArrayList<String>();
  
  /** 菜单权限列表 */
  private List<MenuAuthority> authorities = new ArrayList<MenuAuthority>();
  

  /**
   * 获取名称
   * 
   * @return 名称
   */
  @JsonProperty
  @NotEmpty
  @Length(max = 200)
  @Column(nullable = false)
  @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO)
  public String getName() {
    return name;
  }

  /**
   * 设置名称
   * 
   * @param name 名称
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 获取是否内置
   * 
   * @return 是否内置
   */
  @Column(nullable = false, updatable = false)
  public Boolean getIsSystem() {
    return isSystem;
  }

  /**
   * 设置是否内置
   * 
   * @param isSystem 是否内置
   */
  public void setIsSystem(Boolean isSystem) {
    this.isSystem = isSystem;
  }

  /**
   * 获取描述
   * 
   * @return 描述
   */
  @JsonProperty
  @Length(max = 200)
  public String getDescription() {
    return description;
  }

  /**
   * 设置描述
   * 
   * @param description 描述
   */
  public void setDescription(String description) {
    this.description = description;
  }


  
  /**
   * 获取菜单权限
   * @return 权限
   */
  @JsonProperty
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "t_role_authority")  
  public List<MenuAuthority> getAuthorities() {
	return authorities;
  }

  public void setAuthorities(List<MenuAuthority> authorities) {
	this.authorities = authorities;
  }

//  /**
//   * 获取权限
//   * 
//   * @return 权限
//   */
//  @ElementCollection(fetch = FetchType.EAGER)
//  @CollectionTable(name = "t_role_authority")
//  public List<String> getAuthorities() {
//    return authorities;
//  }
//
//  /**
//   * 设置权限
//   * 
//   * @param authorities 权限
//   */
//  public void setAuthorities(List<String> authorities) {
//    this.authorities = authorities;
//  }


}
