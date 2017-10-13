package com.yxkj.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.yxkj.entity.commonenum.CommonEnum.*;
import org.springframework.web.multipart.MultipartFile;

import com.yxkj.entity.base.BaseEntity;

/**
 * Entity - 货柜类别
 *
 * @author Andrea
 * @version 2017年9月19日 上午11:46:10
 */
@Entity
@Table(name = "t_cntr_category")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "t_cntr_category_sequence")
public class ContainerCategory extends BaseEntity {


    private static final long serialVersionUID = -3578676079608900213L;

    /**
     * 货柜类别名称
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
     * 单个货道容量
     */
    private Integer capacity;

    /**
     * 每行货道数
     */
    private Integer lineChannel;


    /**
     * 每列货道数
     */
    private Integer columnChannel;


    /**
     * 货道总数
     */
    private Integer totalChannel;

    /**
     * 货柜温度
     */
    private CntrTemp cTemp;

    /**
     * 货柜类型
     */
    private CntrType cntrType;
    /**
     * 备注
     */
    private String remark;


    public CntrTemp getcTemp() {
        return cTemp;
    }

    public void setcTemp(CntrTemp cTemp) {
        this.cTemp = cTemp;
    }

    @Column(length = 200)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getLineChannel() {
        return lineChannel;
    }

    public void setLineChannel(Integer lineChannel) {
        this.lineChannel = lineChannel;
    }

    public Integer getColumnChannel() {
        return columnChannel;
    }

    public void setColumnChannel(Integer columnChannel) {
        this.columnChannel = columnChannel;
    }

    public Integer getTotalChannel() {
        return totalChannel;
    }

    public void setTotalChannel(Integer totalChannel) {
        this.totalChannel = totalChannel;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

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


    public void setCatePicUrl(String catePicUrl) {
        this.catePicUrl = catePicUrl;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public CntrType getCntrType() {
        return cntrType;
    }

    public void setCntrType(CntrType cntrType) {
        this.cntrType = cntrType;
    }
}
