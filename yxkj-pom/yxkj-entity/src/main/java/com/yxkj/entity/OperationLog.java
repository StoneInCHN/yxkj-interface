package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yxkj.entity.base.BaseEntity;


/**
 * Entity - 运营平台操作日志
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:25:31
 */
@Entity
@Table(name = "t_operation_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_operation_log_sequence")
public class OperationLog extends BaseEntity {

  private static final long serialVersionUID = 4107710872816009334L;

  /** "日志内容"属性名称 */
  public static final String LOG_CONTENT_ATTRIBUTE_NAME = OperationLog.class.getName() + ".CONTENT";

  /** 操作 */
  private String operation;

  /** 操作员 */
  private String operator;

  /**
   * 操作员ID
   */
  private Long oprId;

  /** 内容 */
  private String content;

  /** 请求参数 */
  private String parameter;

  /** IP */
  private String ip;


  public Long getOprId() {
    return oprId;
  }

  public void setOprId(Long oprId) {
    this.oprId = oprId;
  }

  /**
   * 获取操作
   * 
   * @return 操作
   */
  @Column(nullable = false, updatable = false)
  @JsonProperty
  public String getOperation() {
    return operation;
  }

  /**
   * 设置操作
   * 
   * @param operation 操作
   */
  public void setOperation(String operation) {
    this.operation = operation;
  }

  /**
   * 获取操作员
   * 
   * @return 操作员
   */
  @Column(updatable = false)
  @JsonProperty
  public String getOperator() {
    return operator;
  }

  /**
   * 设置操作员
   * 
   * @param operator 操作员
   */
  public void setOperator(String operator) {
    this.operator = operator;
  }

  /**
   * 获取内容
   * 
   * @return 内容
   */
  @Column(length = 3000, updatable = false)
  @JsonProperty
  public String getContent() {
    return content;
  }

  /**
   * 设置内容
   * 
   * @param content 内容
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * 获取请求参数
   * 
   * @return 请求参数
   */
  @Lob
  @Column(updatable = false)
  public String getParameter() {
    return parameter;
  }

  /**
   * 设置请求参数
   * 
   * @param parameter 请求参数
   */
  public void setParameter(String parameter) {
    this.parameter = parameter;
  }

  /**
   * 获取IP
   * 
   * @return IP
   */
  @Column(nullable = false, updatable = false)
  @JsonProperty
  public String getIp() {
    return ip;
  }

  /**
   * 设置IP
   * 
   * @param ip IP
   */
  public void setIp(String ip) {
    this.ip = ip;
  }

}
