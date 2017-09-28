package com.yxkj.shelf.json.beans;

import com.yxkj.entity.Goods;

/**
 *
 * 类说明
 * 
 * @author Andrea
 * @version 创建时间：2017年9月28日 下午2:43:36
 * 
 */
public class GoodsBean {

  /**
   * 商品
   */
  private Goods goods;

  /**
   * 商品数量
   */
  private Integer count;

  public Goods getGoods() {
    return goods;
  }

  public void setGoods(Goods goods) {
    this.goods = goods;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }



}
