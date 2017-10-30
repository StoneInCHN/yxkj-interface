package com.yxkj.json.bean;

public class KeeperNotice {
  
  private String sceneSn;
  
  private String type;
  
  private Integer noticeCount;
  
  private String Content;
  
  private String noticeTime;

  public String getSceneSn() {
    return sceneSn;
  }

  public void setSceneSn(String sceneSn) {
    this.sceneSn = sceneSn;
  }

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
