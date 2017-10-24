package com.yxkj.controller;

import javax.annotation.Resource;

import com.yxkj.common.log.LogUtil;
import com.yxkj.entity.Order;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.entity.OrderItem;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.service.OrderItemService;

import io.swagger.annotations.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

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

  /**
   * 更新出货状态
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
   *
   * @param
   * @return
   * @throws UnsupportedEncodingException
   */
  @RequestMapping(value = "/getOrderItemOutStatus", method = RequestMethod.POST)
  @ApiOperation(value = "商品出货状态", httpMethod = "POST", response = ResponseOne.class,
      notes = "商品出货状态")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|0004-token timeout")})
  // @UserValidCheck
  public @ResponseBody ResponseMultiple<Map<String, Object>> getOrderItemOutStatus(Long orderId) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<>();

    Order order = orderService.find(orderId);

    Set<OrderItem> orderItemSet = order.getOrderItems();
    List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
    orderItemSet.forEach(orderItem -> {
      boolean isContain = false;
      for (Map<String, Object> map : maps) {
        Long cid = (Long) map.get("cId");
        if (cid.equals(orderItem.getCntrId())) {
          isContain = true;
          map.put("count", (Integer) map.get("count") + 1);
        }
      }
      if (!isContain) {
        Map<String, Object> map = new HashMap<>();
        map.put("cId", orderItem.getCntrId());
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
}
