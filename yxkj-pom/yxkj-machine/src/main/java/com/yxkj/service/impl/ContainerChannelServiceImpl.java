package com.yxkj.service.impl;

import java.util.*;

import javax.annotation.Resource;

import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.json.beans.GoodsBean;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Service;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.beans.Message;
import com.yxkj.dao.ContainerChannelDao;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.service.ContainerChannelService;

@Service("containerChannelServiceImpl")
public class ContainerChannelServiceImpl extends BaseServiceImpl<ContainerChannel, Long>
    implements ContainerChannelService {

  @Resource(name = "containerChannelDaoImpl")
  private ContainerChannelDao containerChannelDao;

  @Resource(name = "containerChannelDaoImpl")
  public void setBaseDao(ContainerChannelDao containerChannelDao) {
    super.setBaseDao(containerChannelDao);
  }

  @Override
  public ContainerChannel getByCImeiAndChannel(String cImei, String channel) {
    return containerChannelDao.getByCImeiAndChannel(cImei, channel);
  }

  @Override
  public ResponseMultiple<Map<String, Object>> verifyStock(List<String> gList) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
    Boolean flag = false;
    for (String gStr : gList) {
      Map<String, Object> map = new HashMap<String, Object>();
      String[] g = gStr.split("-");
      ContainerChannel cc = containerChannelDao.find(Long.valueOf(g[0]));
      map.put("cId", cc.getId());
      Integer count = Integer.valueOf(g[1]);
      map.put("count", count);
      if (count > cc.getSurplus()) {
        map.put("count", cc.getSurplus());
        flag = true;
      }
      maps.add(map);
    }

    if (BooleanUtils.isTrue(flag)) {// 库存不足
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(Message.warn("yxkj.goods.stock.insufficient").getContent());
    } else {
      response.setCode(CommonAttributes.SUCCESS);
    }
    response.setMsg(maps);
    return response;
  }

  @Override
  public Boolean isVerifyStockSuccess(List<GoodsBean> gList, CommonEnum.PurMethod purMethod) {
    Boolean flag = true;
    for (GoodsBean goodsBean : gList) {
      int count = goodsBean.getCount();
      // 中控购买，优先级最高，
      // 线下扫码，输码<中控
      if (purMethod.equals(CommonEnum.PurMethod.CONTROLL_MACHINE)) {
        // 缺货
        if (count > goodsBean.getChannel().getSurplus()
            - goodsBean.getChannel().getOfflineLocalLock()) {
          goodsBean.setCount(
              goodsBean.getChannel().getSurplus() - goodsBean.getChannel().getOfflineLocalLock());
          flag = false;
        }
      } else if (purMethod.equals(CommonEnum.PurMethod.INPUT_CODE)
          || purMethod.equals(CommonEnum.PurMethod.SCAN_CODE)) {
        // 缺货
        if (count > goodsBean.getChannel().getSurplus()
            - goodsBean.getChannel().getOfflineLocalLock()) {
          goodsBean.setCount(
              goodsBean.getChannel().getSurplus() - goodsBean.getChannel().getOfflineLocalLock()
                  - goodsBean.getChannel().getOfflineRemoteLock());
          flag = false;
        }
      }
    }
    return flag;
  }
}
