package com.yxkj.controller;

import javax.annotation.Resource;

import com.yxkj.common.log.LogUtil;
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

/**
 * Controller - 订单
 */
@Controller("OrderItemController")
@RequestMapping("/orderItem")
@Api(value = "orderItem", description = "订单项", tags = "OrderItemController")
public class OrderItemController extends MobileBaseController {

  @Resource(name = "orderItemServiceImpl")
  private OrderItemService orderItemService;

  /**
   * 更新出货状态
   */
  @RequestMapping(value = "updateOrderItemShipmentStatus", method = RequestMethod.POST)
  @ApiOperation(value = "更新出货状态", httpMethod = "POST", response = BaseResponse.class, notes = "更新出货状态",
      nickname = "updateOrderItemShipmentStatus")
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
}
