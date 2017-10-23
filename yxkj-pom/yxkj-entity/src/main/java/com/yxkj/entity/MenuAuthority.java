package com.yxkj.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxkj.entity.base.OrderEntity;

/**
 * Entity - 菜单权限表
 * 
 */
@Entity
@Table(name = "t_menu_authority")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_menu_authority_sequence")
public class MenuAuthority extends OrderEntity {

  private static final long serialVersionUID = 132049976948653105L;

  /** 名称 */
  private String name;

  /** 路径 */
  private String path;
  
  /** 菜单icon */
  private String icon;
  
  /** 对应的组件 */
  private String component;
  
  /** 重定向路径 */
  private String redirect;
  
  /** 是否隐藏 */
  private Boolean hidden;

  /** 上级菜单 */
  private MenuAuthority parent;

  /** 下级菜单 */
  private Set<MenuAuthority> children = new HashSet<MenuAuthority>();
  
  public MenuAuthority(){

  }
  public MenuAuthority(String name, String path, String icon, String component, String redirect, Boolean hidden,  Set<MenuAuthority> children){
	  this.name = name;
	  this.path = path;
	  this.icon = icon;
	  this.component = component;
	  this.redirect = redirect;
	  this.hidden = hidden;
	  this.children = children;
  }
  @JsonProperty
  @NotEmpty
  @Length(max = 100)
  @Column(nullable = false, length = 100)
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  @JsonProperty
  @Column(nullable = false)
  public String getPath() {
	return path;
  }

  public void setPath(String path) {
	this.path = path;
  }
  
  @JsonProperty
  public String getIcon() {
	return icon;
  }

  public void setIcon(String icon) {
	this.icon = icon;
  }
  
  @JsonProperty
  public String getComponent() {
	return component;
  }

  public void setComponent(String component) {
	this.component = component;
  }
  
  @JsonProperty
  public String getRedirect() {
	return redirect;
  }

  public void setRedirect(String redirect) {
	this.redirect = redirect;
  }
  
  @ManyToOne(fetch = FetchType.LAZY)
  public MenuAuthority getParent() {
	return parent;
  }

  public void setParent(MenuAuthority parent) {
	this.parent = parent;
  }
  
  @JsonProperty
  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @OrderBy("order asc")
  public Set<MenuAuthority> getChildren() {
	return children;
  }

  public void setChildren(Set<MenuAuthority> children) {
	this.children = children;
  }
  
  @JsonProperty
  public Boolean getHidden() {
	return hidden;
  }

  public void setHidden(Boolean hidden) {
	this.hidden = hidden;
  }

}
