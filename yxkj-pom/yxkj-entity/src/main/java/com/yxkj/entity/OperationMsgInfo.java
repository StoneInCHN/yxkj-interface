package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.OtherMsgType;

/**
 * Entity -运营平台消息
 * 
 * @author Andrea
 * @version 2017年10月31日 下午5:26:11
 */
@Entity
@Table(name = "t_opr_msg_info")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_opr_msg_info_sequence")
public class OperationMsgInfo extends BaseEntity {

  private static final long serialVersionUID = 1170442128165498366L;

  /** 消息标题 */
  private String title;

  /** 消息内容 */
  private String content;

  /**
   * 附加信息
   */
  private String remark;

  /**
   * 通知类型
   */
  private OtherMsgType type;


  public OtherMsgType getType() {
    return type;
  }

  public void setType(OtherMsgType type) {
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

  @Column(length = 1000)
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
