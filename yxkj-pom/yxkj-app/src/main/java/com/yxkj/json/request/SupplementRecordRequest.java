package com.yxkj.json.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yxkj.json.bean.SupplyRecord;


public class SupplementRecordRequest {

  private Long userId;

  private String sceneSn;

  private Long cntrId;

  private List<SupplyRecord> supplementRecords;

  /**
   * 上传的补货图片
   */
  private MultipartFile suppPic;


  public MultipartFile getSuppPic() {
    return suppPic;
  }

  public void setSuppPic(MultipartFile suppPic) {
    this.suppPic = suppPic;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getSceneSn() {
    return sceneSn;
  }

  public void setSceneSn(String sceneSn) {
    this.sceneSn = sceneSn;
  }

  public Long getCntrId() {
    return cntrId;
  }

  public void setCntrId(Long cntrId) {
    this.cntrId = cntrId;
  }

  public List<SupplyRecord> getSupplementRecords() {
    return supplementRecords;
  }

  public void setSupplementRecords(List<SupplyRecord> supplementRecords) {
    this.supplementRecords = supplementRecords;
  }

  @Override
  public String toString() {
    return "SupplementRecordRequest [userId=" + userId + ", sceneSn=" + sceneSn + ", cntrId="
        + cntrId + "]";
  }
}
