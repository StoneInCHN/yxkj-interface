package com.yxkj.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 用户-消息关联
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:24:57
 */
@Entity
@Table(name = "t_enduser_message")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_enduser_message_sequence")
public class MsgEndUser extends BaseEntity {

  private static final long serialVersionUID = 8059526848027012082L;

  /** 接受消息的用户实体 */
  private EndUser endUser;

  /** 消息实体 */
  private MessageInfo message;

  /** 是否已经推送 */
  private Boolean isPush;

  /** 是否已读 */
  private Boolean isRead;

  @ManyToOne(fetch = FetchType.LAZY)
  public EndUser getEndUser() {
    return endUser;
  }

  public void setEndUser(EndUser endUser) {
    this.endUser = endUser;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public MessageInfo getMessage() {
    return message;
  }

  public void setMessage(MessageInfo message) {
    this.message = message;
  }

  public Boolean getIsRead() {
    return isRead;
  }

  public void setIsRead(Boolean isRead) {
    this.isRead = isRead;
  }

  public Boolean getIsPush() {
    return isPush;
  }

  public void setIsPush(Boolean isPush) {
    this.isPush = isPush;
  }


}
