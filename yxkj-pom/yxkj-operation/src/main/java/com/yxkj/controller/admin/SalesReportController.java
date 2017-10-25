package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.Order;
import com.yxkj.entity.commonenum.CommonEnum.OrderStatus;
import com.yxkj.framework.filter.Filter;
import com.yxkj.json.admin.request.OrderRequest;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.OrderItemService;
import com.yxkj.service.OrderService;
import com.yxkj.service.TouristService;
import com.yxkj.utils.TimeUtils;

/**
 * 销售数据概览 - Controller
 * 
 * @author Andrea
 * @version 2017年10月25日 下午3:00:46
 */
@Controller("salesReportController")
@RequestMapping("/admin/salesReport")
@Api(value = "(货柜后台)数据概览", description = "数据概览")
public class SalesReportController extends BaseController {



  @Resource(name = "orderServiceImpl")
  private OrderService orderService;

  @Resource(name = "orderItemServiceImpl")
  private OrderItemService orderItemService;

  @Resource(name = "touristServiceImpl")
  private TouristService touristService;


  /**
   * 数据概览
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/overview", method = RequestMethod.POST)
  @ApiOperation(value = "数据概览", httpMethod = "POST", response = ResponseOne.class, notes = "数据概览")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> overview(@ApiParam(name = "请求参数(json)",
      value = "参数[userName:登录用户名]", required = false) @RequestBody OrderRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    Map<String, Object> resMap = new HashMap<String, Object>();

    resMap.put("userCount", touristService.count());// 总用户数
    resMap.put("orderCount", 0);// 总订单数
    resMap.put("saleIncome", 0);// 总销售收入
    resMap.put("saleCost", 0);// 总销售成本
    resMap.put("profitRate", 0);// 毛利率
    resMap.put("avgUnitPrice", 0);// 平均客单价
    resMap.put("repeatPurRate", 0);// 重复购买率(默认复购1次以上)
    List<Filter> filters = new ArrayList<Filter>();
    filters.add(Filter.ne("status", OrderStatus.UNPAID));
    List<Order> orderList = orderService.findList(null, filters, null);
    if (!CollectionUtils.isEmpty(orderList)) {
      resMap.put("orderCount", orderList.size());
      BigDecimal saleIncome = new BigDecimal(0);
      BigDecimal profit = new BigDecimal(0);
      for (Order order : orderList) {
        saleIncome = saleIncome.add(order.getAmount());
        profit = profit.add(order.getProfit());
      }
      resMap.put("saleIncome", saleIncome);
      resMap.put("saleCost", saleIncome.subtract(profit));
      resMap.put("profitRate", profit.divide(saleIncome, 4, BigDecimal.ROUND_HALF_UP));
      resMap.put("avgUnitPrice",
          saleIncome.divide(new BigDecimal(orderList.size()), 2, BigDecimal.ROUND_HALF_UP));
      resMap.put("repeatPurRate", orderService.calRePurRate(2, null));
    }
    response.setMsg(resMap);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }


  /**
   * 数据概览-改变复购次数
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/chgRepeatCount", method = RequestMethod.POST)
  @ApiOperation(value = "数据概览-改变复购次数", httpMethod = "POST", response = ResponseOne.class,
      notes = "数据概览-改变复购次数")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> chgRepeatCount(
      @ApiParam(name = "请求参数(json)", value = "参数[userName:登录用户名; repeatCount:复购次数]",
          required = true) @RequestBody OrderRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    Integer repeatCount = request.getRepeatCount();
    Map<String, Object> resMap = new HashMap<String, Object>();
    resMap.put("repeatPurRate", orderService.calRePurRate(repeatCount + 1, null));
    response.setMsg(resMap);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }


  /**
   * 数据概览-数据折线图
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/salesGraph", method = RequestMethod.POST)
  @ApiOperation(value = "数据概览-数据折线图", httpMethod = "POST", response = ResponseOne.class,
      notes = "数据概览-数据折线图")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> salesGraph(
      @ApiParam(name = "请求参数(json)", value = "参数[userName:登录用户名; startTime:开始日期; endTime:结束日期]",
          required = false) @RequestBody OrderRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    Date startTime = request.getStartTime();
    Date endTime = request.getEndTime();
    Map<String, Object> resMap = new HashMap<String, Object>();
    List<Filter> filters = new ArrayList<Filter>();
    if (startTime != null) {
      filters.add(Filter.ge("createDate", TimeUtils.formatDate2Day0(startTime)));
    }
    if (endTime != null) {
      filters.add(Filter.lt("createDate", TimeUtils.formatDate2Day59(endTime)));
    }
    filters.add(Filter.ne("", OrderStatus.UNPAID));
    resMap.put("userCount", touristService.count());// 总用户数
    // List<DictConfig> dictConfigs = touristService.findList(null, filters, null);
    String[] propertys = {"id", "configValue"};
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }


}
