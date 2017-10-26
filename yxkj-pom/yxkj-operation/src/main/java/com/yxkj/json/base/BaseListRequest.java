package com.yxkj.json.base;


public class BaseListRequest extends BaseRequest {

  /** 分页-页面大小 */
  private Integer pageSize = 10;

  /** 分页-当前页码 */
  private Integer pageNumber = 1;

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }


}
