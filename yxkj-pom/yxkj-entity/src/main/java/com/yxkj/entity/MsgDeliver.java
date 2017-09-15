package com.yxkj.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 配送员-消息关联
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:24:57
 */
@Entity
@Table(name = "t_msg_deliver")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_msg_deliver_sequence")
public class MsgDeliver extends BaseEntity {

  private static final long serialVersionUID = 8059526848027012082L;

  /** 接受消息的配送员实体 */
  private ContainerDeliver deliver;

  /** 消息实体 */
  private DeliverRemindMsg message;

  /** 是否已经推送 */
  private Boolean isPush;

  /** 是否已读 */
  private Boolean isRead;


  @ManyToOne(fetch = FetchType.LAZY)
  public ContainerDeliver getDeliver() {
    return deliver;
  }

  public void setDeliver(ContainerDeliver deliver) {
    this.deliver = deliver;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  public DeliverRemindMsg getMessage() {
    return message;
  }

  public void setMessage(DeliverRemindMsg message) {
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
