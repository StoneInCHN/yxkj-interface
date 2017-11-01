package com.yxkj.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.RemindType;

/**
 * Entity - 配送员提醒通知
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:24:42
 */
@Entity
@Table(name = "t_deliver_remind_msg")
// @Indexed(index = "messageInfo")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_deliver_remind_msg_sequence")
public class KeeperRemindMsg extends BaseEntity {

  private static final long serialVersionUID = 1170442128165498366L;

  /** 消息标题 */
  private String title;

  /** 消息内容 */
  private String content;

  /**
   * 附加信息
   */
  private String remark;

  /** 消息、配送员对应关系实体 */
  private Set<MsgKeeper> msgKeepers = new HashSet<MsgKeeper>();

  /**
   * 通知类型
   */
  private RemindType type;

  public RemindType getType() {
    return type;
  }

  public void setType(RemindType type) {
    this.type = type;
  }

  @Column(length = 200)
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Column(length = 100)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Column(length = 500)
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @OneToMany(mappedBy = "message", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  public Set<MsgKeeper> getMsgKeepers() {
    return msgKeepers;
  }

  public void setMsgKeepers(Set<MsgKeeper> msgKeepers) {
    this.msgKeepers = msgKeepers;
  }

}
