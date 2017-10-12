package com.yxkj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
public class ContainerChannelServiceImpl extends BaseServiceImpl<ContainerChannel, Long> implements
    ContainerChannelService {

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
}
