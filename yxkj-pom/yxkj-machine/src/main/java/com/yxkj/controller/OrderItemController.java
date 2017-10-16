package com.yxkj.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yxkj.entity.CommandRecord;
import com.yxkj.entity.OrderItem;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.service.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.internal.util.AlipaySignature;
import com.yxkj.beans.CommonAttributes;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.Order;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.json.beans.GoodsBean;
import com.yxkj.json.request.OrderInfoReq;
import com.yxkj.utils.PayUtil;
import com.yxkj.utils.alipay.AuthUtil;
import com.yxkj.utils.wxpay.WeixinUtil;

import io.swagger.annotations.*;

/**
 * Controller - 订单
 */
@Controller("OrderItemController")
@RequestMapping("/orderItem")
@Api(value = "订单项", description = "订单项")
public class OrderItemController extends MobileBaseController {

  @Resource(name = "orderItemServiceImpl")
  private OrderItemService orderItemService;

  /**
   * 更新命令状态
   */
  @RequestMapping("finishCmdStatus")
  @ApiOperation(value = "更新命令状态", httpMethod = "POST", response = BaseResponse.class,
      notes = "更新命令状态")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  @ResponseBody
  @ApiImplicitParams({
      @ApiImplicitParam(paramType = "query", name = "orderItemId", value = "命令记录ID号",
          required = true, dataType = "Long"),
      @ApiImplicitParam(paramType = "query", name = "shipmentStatus", value = "出货状态",
          required = true, dataType = "CommonEnum.ShipmentStatus")})
  public BaseResponse finishCmdStatus(Long orderItemId, CommonEnum.ShipmentStatus shipmentStatus) {

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
