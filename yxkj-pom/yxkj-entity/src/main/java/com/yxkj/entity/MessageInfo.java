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

/**
 * Entity - 消息
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:24:42
 */
@Entity
@Table(name = "t_message_info")
// @Indexed(index = "messageInfo")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_message_info_sequence")
public class MessageInfo extends BaseEntity {

  private static final long serialVersionUID = 1170442128165498366L;

  /** 消息标题 */
  private String title;

  /** 消息内容 */
  private String content;

  /**
   * 附加信息
   */
  private String remark;

  /** 消息、会员对应关系实体 */
  private Set<MsgEndUser> msgUser = new HashSet<MsgEndUser>();


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

  @Column(length = 1000)
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @OneToMany(mappedBy = "message", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  public Set<MsgEndUser> getMsgUser() {
    return msgUser;
  }

  public void setMsgUser(Set<MsgEndUser> msgUser) {
    this.msgUser = msgUser;
  }

}
