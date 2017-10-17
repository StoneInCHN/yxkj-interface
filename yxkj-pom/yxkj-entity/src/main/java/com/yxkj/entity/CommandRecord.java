package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum;

/**
 * 下发指令记录表
 *
 * @author huyong
 * @since 2017/9/20
 */
@Entity
@Table(name = "t_command_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_command_record_sequence")
public class CommandRecord extends BaseEntity {


  private static final long serialVersionUID = 4591737944077098960L;

  private String cmdContent;

  private CommonEnum.CmdType cmdType;

  private String deviceNo;

  private CommonEnum.CmdStatus cmdStatus;


  @Column(length = 500)
  public String getCmdContent() {
    return cmdContent;
  }

  public void setCmdContent(String cmdContent) {
    this.cmdContent = cmdContent;
  }

  public CommonEnum.CmdType getCmdType() {
    return cmdType;
  }

  public void setCmdType(CommonEnum.CmdType cmdType) {
    this.cmdType = cmdType;
  }

  @Column(length = 50)
  public String getDeviceNo() {
    return deviceNo;
  }

  public void setDeviceNo(String deviceNo) {
    this.deviceNo = deviceNo;
  }

  public CommonEnum.CmdStatus getCmdStatus() {
    return cmdStatus;
  }

  public void setCmdStatus(CommonEnum.CmdStatus cmdStatus) {
    this.cmdStatus = cmdStatus;
  }
}
