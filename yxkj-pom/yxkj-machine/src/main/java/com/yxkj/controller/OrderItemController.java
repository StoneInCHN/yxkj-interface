package com.yxkj.controller;

import java.util.*;

import javax.annotation.Resource;

import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.GoodsPic;
import com.yxkj.json.request.OrderInfoReq;
import com.yxkj.service.ContainerChannelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.entity.Order;
import com.yxkj.entity.OrderItem;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.OrderItemService;
import com.yxkj.service.OrderService;

import io.swagger.annotations.*;

import static com.yxkj.entity.commonenum.CommonEnum.RefundStatus.REFUNDED;

/**
 * Controller - 订单
 */
@Controller("OrderItemController")
@RequestMapping("/orderItem")
@Api(value = "orderItem", description = "订单项", tags = "OrderItemController")
public class OrderItemController extends MobileBaseController {

  @Resource(name = "orderItemServiceImpl")
  private OrderItemService orderItemService;

  @Resource(name = "orderServiceImpl")
  private OrderService orderService;

  @Resource(name = "containerChannelServiceImpl")
  private ContainerChannelService containerChannelService;

  /**
   * 更新出货状态
   * 
   * @param orderItemId 订单项ID
   * @param shipmentStatus 出货状态
   * @return
   */
  @RequestMapping(value = "updateOrderItemShipmentStatus", method = RequestMethod.POST)
  @ApiOperation(value = "更新出货状态", httpMethod = "POST", response = BaseResponse.class,
      notes = "更新出货状态", nickname = "updateOrderItemShipmentStatus")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  @ResponseBody
  @ApiImplicitParams({
      @ApiImplicitParam(paramType = "query", name = "orderItemId", value = "命令记录ID号",
          required = true, dataType = "Long"),
      @ApiImplicitParam(paramType = "query", name = "shipmentStatus", value = "出货状态",
          required = true, dataType = "string")})
  public BaseResponse updateOrderItemShipmentStatus(Long orderItemId,
      CommonEnum.ShipmentStatus shipmentStatus) {

    LogUtil.debug(this.getClass(), "updateOrderItemShipmentStatus", "orderItemId= %s;status=%s",
        orderItemId, shipmentStatus);
    BaseResponse response = new BaseResponse();

    OrderItem orderItem =
        orderItemService.updateOrderItemShipmentStatus(orderItemId, shipmentStatus);
    if (orderItem != null
        && orderItem.getShipmentStatus().equals(CommonEnum.ShipmentStatus.SHIPMENT_SUCCESS))
      response.setCode(CommonAttributes.SUCCESS);
    else
      response.setCode(CommonAttributes.FAIL_COMMON);
    return response;
  }


  /**
   * - 商品出货状态查询
   * 
   * @return
   */
  @RequestMapping(value = "/getOrderItemOutStatus", method = RequestMethod.POST)
  @ApiOperation(value = "商品出货状态查询", httpMethod = "POST", response = ResponseOne.class,
      notes = "商品出货状态查询")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|0004-token timeout")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> getOrderItemOutStatus(
      @RequestBody OrderInfoReq req) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<>();

    Order order = orderService.getOrderBySn(req.getOrderSn());

    Set<OrderItem> orderItemSet = order.getOrderItems();
    List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
    orderItemSet.forEach(orderItem -> {
      boolean isContain = false;
      for (Map<String, Object> map : maps) {
        Long cid = (Long) map.get("cId");
        if (cid.equals(orderItem.getCntrId())
            && map.get("status").equals(orderItem.getShipmentStatus())) {
          isContain = true;
          map.put("count", (Integer) map.get("count") + 1);
        }
      }
      if (!isContain) {

        ContainerChannel containerChannel = containerChannelService.find(orderItem.getCntrId());

        List<GoodsPic> goodsPics = containerChannel.getGoods().getGoodsPics();

        Map<String, Object> map = new HashMap<>();
        map.put("cId", orderItem.getCntrId());
        for (GoodsPic goodsPic : goodsPics) {
          if (goodsPic.getOrder() == 0) {
            map.put("pic", goodsPic.getSource());
          }
        }
        map.put("price", orderItem.getPrice());
        map.put("pickUpStatus", orderItem.getPickupStatus());
        map.put("refundStatus", orderItem.getRefundStatus());
        map.put("cntrSn", containerChannel.getCntr().getSn());
        map.put("status", orderItem.getShipmentStatus());
        map.put("count", 1);
        map.put("goodName", orderItem.getgName());
        maps.add(map);
      }
    });
    response.setMsg(maps);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }


  /**
   * - 商品退款状态查询
   *
   * @return
   */
  @RequestMapping(value = "/getOrderItemRefundStatus", method = RequestMethod.POST)
  @ApiOperation(value = "商品退款状态查询", httpMethod = "POST", response = ResponseOne.class,
      notes = "商品退款状态查询")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|0004-token timeout")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> getOrderItemRefundStatus(
      @RequestBody OrderInfoReq req) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<>();

    Order order = orderService.getOrderBySn(req.getOrderSn());

    Set<OrderItem> orderItemSet = order.getOrderItems();
    List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
    orderItemSet.forEach(orderItem -> {
      if (REFUNDED.equals(orderItem.getRefundStatus())) {
        boolean isContain = false;
        for (Map<String, Object> map : maps) {
          Long cid = (Long) map.get("cId");
          if (cid.equals(orderItem.getCntrId())) {
            isContain = true;
            map.put("count", (Integer) map.get("count") + 1);
          }
        }
        if (!isContain) {

          ContainerChannel containerChannel = containerChannelService.find(orderItem.getCntrId());

          List<GoodsPic> goodsPics = containerChannel.getGoods().getGoodsPics();

          Map<String, Object> map = new HashMap<>();
          map.put("cId", orderItem.getCntrId());
          for (GoodsPic goodsPic : goodsPics) {
            if (goodsPic.getOrder() == 0) {
              map.put("pic", goodsPic.getSource());
            }
          }
          map.put("price", orderItem.getPrice());
          map.put("cntrSn", containerChannel.getCntr().getSn());
          map.put("count", 1);
          map.put("goodName", orderItem.getgName());
          maps.add(map);
        }
      }
    });
    response.setMsg(maps);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
}
