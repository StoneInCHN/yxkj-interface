package com.yxkj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.Goods;
import com.yxkj.entity.GoodsPic;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.json.request.GoodsInfoReq;
import com.yxkj.service.ContainerChannelService;
import com.yxkj.service.GoodsService;

/**
 * Controller - 商品
 */
@Controller("GoodsController")
@RequestMapping("/goods")
@Api(value = "商品", description = "商品")
public class GoodsController extends MobileBaseController {

  @Resource(name = "goodsServiceImpl")
  private GoodsService goodsService;

  @Resource(name = "containerChannelServiceImpl")
  private ContainerChannelService containerChannelService;


  /**
   * 根据货道编号查询单个商品
   * 
   * @param req
   * @return
   */
  @RequestMapping(value = "/getSgByChannel", method = RequestMethod.POST)
  @ApiOperation(value = "根据货道编号查询商品", httpMethod = "POST", response = ResponseOne.class,
      notes = "根据货道编号查询商品")
  @ApiResponses({@ApiResponse(code = 200,
      message = "code:0000-request success|1000-goods not exist")})
  public @ResponseBody ResponseOne<Map<String, Object>> getSgByChannel(
      @ApiParam(name = "请求参数(json)", value = "cSn:商品货道编号 | cImei:中控imei号", required = true) @RequestBody GoodsInfoReq req) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    String cSn = req.getcSn();
    String cImei = req.getcImei();
    ContainerChannel cc = containerChannelService.getByCImeiAndChannel(cImei, cSn.substring(0, 1));
    if (cc == null || cc.getGoods() == null) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.goods.noexist", cSn));
      return response;
    }
    Goods g = cc.getGoods();
    Map<String, Object> gMap = new HashMap<String, Object>();
    gMap.put("gId", g.getId());
    gMap.put("gName", g.getName());
    gMap.put("gSpec", g.getSpec());
    gMap.put("price", cc.getPrice());
    gMap.put("status", cc.getChgsStatus());
    String gImg = "";
    for (GoodsPic goodsPic : g.getGoodsPics()) {
      gImg = goodsPic.getSource();
      if (goodsPic.getOrder() != null && goodsPic.getOrder() == 1) {// 获取中控显示的大图
        gImg = goodsPic.getSource();
      }
    }
    gMap.put("gImg", gImg);

    response.setMsg(gMap);
    response.setCode(CommonAttributes.SUCCESS);

    return response;
  }
}
