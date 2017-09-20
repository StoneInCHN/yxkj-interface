package com.yxkj.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 货柜补货记录总汇
 * 
 * @author Andrea
 * @version 2017年9月20日 下午6:27:41
 */
@Entity
@Table(name = "t_supp_sum_rec")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_supp_sum_rec_sequence")
public class SupplementSumRec extends BaseEntity {

  private static final long serialVersionUID = -2197390136625086557L;


  /**
   * 优享空间编号
   */
  private String sceneSn;

  /**
   * 优享空间地址名称
   */
  private String sceneName;


  /**
   * 实际总补货数量
   */
  private Integer suppTotalCount;

  /**
   * 总待补货数量
   */
  private Integer waitSuppTotalCount;

  /**
   * 缺货数量
   */
  private Integer lackCount;

  /**
   * 补货时间
   */
  private Date supplyTime;

  /**
   * 补货人姓名
   */
  private String suppName;

  /**
   * 补货人手机号
   */
  private String suppMobile;

  /**
   * 补货人ID
   */
  private Long suppId;

  /**
   * 补货记录
   */
  private Set<SupplementRecord> suppRecords = new HashSet<SupplementRecord>();



  public Integer getLackCount() {
    return lackCount;
  }

  public void setLackCount(Integer lackCount) {
    this.lackCount = lackCount;
  }

  @OneToMany(mappedBy = "suppSum")
  public Set<SupplementRecord> getSuppRecords() {
    return suppRecords;
  }

  public void setSuppRecords(Set<SupplementRecord> suppRecords) {
    this.suppRecords = suppRecords;
  }

  public Integer getSuppTotalCount() {
    return suppTotalCount;
  }

  public void setSuppTotalCount(Integer suppTotalCount) {
    this.suppTotalCount = suppTotalCount;
  }

  public Integer getWaitSuppTotalCount() {
    return waitSuppTotalCount;
  }

  public void setWaitSuppTotalCount(Integer waitSuppTotalCount) {
    this.waitSuppTotalCount = waitSuppTotalCount;
  }

  public Long getSuppId() {
    return suppId;
  }

  public void setSuppId(Long suppId) {
    this.suppId = suppId;
  }


  @Column(length = 30)
  public String getSceneSn() {
    return sceneSn;
  }

  public void setSceneSn(String sceneSn) {
    this.sceneSn = sceneSn;
  }

  @Column(length = 30)
  public String getSceneName() {
    return sceneName;
  }

  public void setSceneName(String sceneName) {
    this.sceneName = sceneName;
  }


  public Date getSupplyTime() {
    return supplyTime;
  }

  public void setSupplyTime(Date supplyTime) {
    this.supplyTime = supplyTime;
  }

  @Column(length = 20)
  public String getSuppName() {
    return suppName;
  }

  public void setSuppName(String suppName) {
    this.suppName = suppName;
  }

  @Column(length = 20)
  public String getSuppMobile() {
    return suppMobile;
  }

  public void setSuppMobile(String suppMobile) {
    this.suppMobile = suppMobile;
  }

}
