package com.yxkj.entity.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.groups.Default;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxkj.lucene.DateBridgeImpl;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE)
@EntityListeners(EntityListener.class)
@MappedSuperclass
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = -67188388306700736L;

  /** "ID"属性名称 */
  public static final String ID_PROPERTY_NAME = "id";

  /** "创建日期"属性名称 */
  public static final String CREATE_DATE_PROPERTY_NAME = "createDate";

  /** "修改日期"属性名称 */
  public static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";

  /**
   * 保存验证组
   */
  public interface Save extends Default {

  }

  /**
   * 更新验证组
   */
  public interface Update extends Default {

  }

  /** ID */
  private Long id;

  /** 创建日期 */
  private Date createDate;

  /** 修改日期 */
  private Date modifyDate;

  /**
   * 获取ID
   * 
   * @return ID
   */
  @JsonProperty
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
  @Field(store = Store.NO, index = Index.YES, analyze = Analyze.NO)
  public Long getId() {
    return id;
  }

  /**
   * 设置ID
   * 
   * @param id ID
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * 获取创建日期
   * 
   * @return 创建日期
   */
  @JsonProperty
  @Column(nullable = false, updatable = false)
  @Field(index = Index.YES, store = Store.NO, analyze = Analyze.NO)
  @FieldBridge(impl = DateBridgeImpl.class)
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * 设置创建日期
   * 
   * @param createDate 创建日期
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * 获取修改日期
   * 
   * @return 修改日期
   */  
  @Column(nullable = false)
  public Date getModifyDate() {
    return modifyDate;
  }

  /**
   * 设置修改日期
   * 
   * @param modifyDate 修改日期
   */
  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  /**
   * 重写equals方法
   * 
   * @param obj 对象
   * @return 是否相等
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    if (!BaseEntity.class.isAssignableFrom(obj.getClass())) {
      return false;
    }
    BaseEntity other = (BaseEntity) obj;
    return getId() != null ? getId().equals(other.getId()) : false;
  }

  /**
   * 重写hashCode方法
   * 
   * @return hashCode
   */
  @Override
  public int hashCode() {
    int hashCode = 17;
    hashCode += null == getId() ? 0 : getId().hashCode() * 31;
    return hashCode;
  }

}
