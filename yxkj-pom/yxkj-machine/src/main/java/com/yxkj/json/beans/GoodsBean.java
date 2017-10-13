package com.yxkj.json.beans;

import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.Goods;

/**
 *
 * 商品
 * 
 * @author Andrea
 * @version 创建时间：2017年10月9日 下午5:01:01
 * 
 */
public class GoodsBean {

  /**
   * 商品
   */
  private Goods goods;

  /**
   * 商品货道
   */
  private ContainerChannel channel;

  /**
   * 商品数量
   */
  private Integer count;



  public ContainerChannel getChannel() {
    return channel;
  }

  public void setChannel(ContainerChannel channel) {
    this.channel = channel;
  }

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
