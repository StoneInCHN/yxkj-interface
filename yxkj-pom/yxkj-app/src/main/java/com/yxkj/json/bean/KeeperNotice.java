package com.yxkj.json.bean;

public class KeeperNotice {
  
  /**
   * 消息类型
   */
  private String type;
  
  /**
   * 未读消息数
   */
  private Integer noticeCount;
  
  /**
   * 最新消息
   */
  private String Content;
  
  /**
   * 通知时间
   */
  private String noticeTime;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getNoticeCount() {
    return noticeCount;
  }

  public void setNoticeCount(Integer noticeCount) {
    this.noticeCount = noticeCount;
  }

  public String getContent() {
    return Content;
  }

  public void setContent(String content) {
    Content = content;
  }

  public String getNoticeTime() {
    return noticeTime;
  }

  public void setNoticeTime(String noticeTime) {
    this.noticeTime = noticeTime;
  }
  
}
