package com.yxkj.json.admin.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;

public class SceneData {  
	  /**
	   * 空间编号
	   */
	  private String sn;

	  /**
	   * 空间地址名称
	   */
	  private String name;

	  /**
	   * 开通时间
	   */
	  private Date openTime;

	  /**
	   * 地址
	  */
	  private List<Long> area;

	  /**
	   * 详细地址
	   */
	  private String address;

	  /**
	   * 经度
	   */
	  private BigDecimal longitude;

	  /**
	   * 纬度
	   */
	  private BigDecimal latitude;
	  
	  /**
	   * 中控货柜 服务状态
	   */
	  private CommonStatus status;
	  
	  /**
	   * 中控货柜 音量
	   */
	  private String volume;
	  
	  /**
	   * 中控货柜 IMEI
	   */
	  private String imei;
	  
	  /**
	   * 中控 重启间隔天数
	   */
	  private Integer rebootDay;

	  /**
	   * 中控 默认重启时间
	   */
	  private String rebootTime;

	  /**
	   * 是否含有微仓
	   */
	  private Boolean hasStore;
	  
	  private Boolean showMap;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public List<Long> getArea() {
		return area;
	}

	public void setArea(List<Long> area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public CommonStatus getStatus() {
		return status;
	}

	public void setStatus(CommonStatus status) {
		this.status = status;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getRebootDay() {
		return rebootDay;
	}

	public void setRebootDay(Integer rebootDay) {
		this.rebootDay = rebootDay;
	}

	public String getRebootTime() {
		return rebootTime;
	}

	public void setRebootTime(String rebootTime) {
		this.rebootTime = rebootTime;
	}

	public Boolean getHasStore() {
		return hasStore;
	}

	public void setHasStore(Boolean hasStore) {
		this.hasStore = hasStore;
	}

	public Boolean getShowMap() {
		return showMap;
	}

	public void setShowMap(Boolean showMap) {
		this.showMap = showMap;
	}


	
}
