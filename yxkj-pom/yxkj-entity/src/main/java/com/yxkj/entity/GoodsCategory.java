package com.yxkj.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 商品类别
 * 
 * @author Andrea
 * @version 2017年9月15日 上午11:45:09
 */
@Entity
@Table(name = "t_goods_category")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_goods_category_sequence")
public class GoodsCategory extends BaseEntity {


  private static final long serialVersionUID = 1L;

  /**
   * 商品类别名称
   */
  private String cateName;

  /**
   * 排序
   */
  private Integer cateOrder;

  /**
   * 是否生效
   */
  private Boolean isActive;

  /**
   * 类别图片地址
   */
  private String catePicUrl;

  /**
   * 类别图片
   */
  private MultipartFile catePic;

  /**
   * 商品
   */
  private Set<Goods> goodsList = new HashSet<Goods>();

  @Column(length = 200)
  public String getCatePicUrl() {
    return catePicUrl;
  }

  public void setCategPicUrl(String catePicUrl) {
    this.catePicUrl = catePicUrl;
  }

  @Transient
  public MultipartFile getCatePic() {
    return catePic;
  }

  public void setCatePic(MultipartFile catePic) {
    this.catePic = catePic;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public String getCateName() {
    return cateName;
  }

  public void setCateName(String cateName) {
    this.cateName = cateName;
  }

  public Integer getCateOrder() {
    return cateOrder;
  }

  public void setCateOrder(Integer cateOrder) {
    this.cateOrder = cateOrder;
  }

  @OneToMany(mappedBy = "category")
  public Set<Goods> getGoodsList() {
    return goodsList;
  }

  public void setGoodsList(Set<Goods> goodsList) {
    this.goodsList = goodsList;
  }

  public void setCatePicUrl(String catePicUrl) {
    this.catePicUrl = catePicUrl;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

}
