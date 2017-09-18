package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.yxkj.entity.base.BaseEntity;
import com.yxkj.entity.commonenum.CommonEnum.FileType;

/**
 * Entity - 广告库
 * 
 * @author Andrea
 * @version 2017年9月15日 下午5:40:14
 */
@Entity
@Table(name = "t_ad_resource")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_ad_resource_sequence")
public class AdResource extends BaseEntity {

  private static final long serialVersionUID = -411179888401580443L;

  /**
   * 文件名称
   */
  private String fileName;

  /**
   * 文件URL
   */
  private String fileUrl;

  /**
   * 备注
   */
  private String remark;

  /**
   * 文件
   */
  private MultipartFile adFile;

  /**
   * 广告类型
   */
  private FileType fileType;


  public FileType getFileType() {
    return fileType;
  }

  public void setFileType(FileType fileType) {
    this.fileType = fileType;
  }

  @Column(length = 200)
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Column(length = 100)
  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Column(length = 200)
  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  @Transient
  public MultipartFile getAdFile() {
    return adFile;
  }

  public void setAdFile(MultipartFile adFile) {
    this.adFile = adFile;
  }

}
