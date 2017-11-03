package com.yxkj.json.bean;

public class KeeperNoticeItem {
  
  /**
   * 优享空间编号
   */
  private String sceneSn;
  
  /**
   * 消息标题
   */
  private String title;
  
  /**
   * 消息内容
   */
  private String content;
  
  /**
   * 发送时间
   */
  private String sendDate;

  public String getSceneSn() {
    return sceneSn;
  }

  public void setSceneSn(String sceneSn) {
    this.sceneSn = sceneSn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getSendDate() {
    return sendDate;
  }

  public void setSendDate(String sendDate) {
    this.sendDate = sendDate;
  }

}
