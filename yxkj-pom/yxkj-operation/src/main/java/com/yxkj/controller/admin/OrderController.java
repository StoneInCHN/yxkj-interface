package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.DictConfig;
import com.yxkj.entity.Order;
import com.yxkj.entity.OrderItem;
import com.yxkj.entity.commonenum.CommonEnum.DictKey;
import com.yxkj.entity.commonenum.CommonEnum.OrderStatus;
import com.yxkj.entity.commonenum.CommonEnum.PurMethod;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.filter.Filter.Operator;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.request.OrderRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.DictConfigService;
import com.yxkj.service.OrderItemService;
import com.yxkj.service.OrderService;
import com.yxkj.utils.FieldFilterUtils;
import com.yxkj.utils.TimeUtils;

/**
 * 订单数据 - Controller
 * 
 * @author Andrea
 * @version 2017年10月24日 上午10:42:55
 */
@Controller("orderController")
@RequestMapping("/admin/order")
@Api(value = "(货柜后台)订单数据", description = "订单数据")
public class OrderController extends BaseController {



  @Resource(name = "orderServiceImpl")
  private OrderService orderService;

  @Resource(name = "orderItemServiceImpl")
  private OrderItemService orderItemService;

  @Resource(name = "dictConfigServiceImpl")
  private DictConfigService dictConfigService;

  /**
   * 订单数据页初始化参数
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/initData", method = RequestMethod.POST)
  @ApiOperation(value = "订单数据页初始化参数", httpMethod = "POST", response = ResponseOne.class,
      notes = "订单数据页初始化参数")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> initData(
      @ApiParam @RequestBody BaseRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("purMethod", PurMethod.values());
    List<Filter> filters = new ArrayList<Filter>();
    filters.add(new Filter("configKey", Operator.eq, DictKey.PAYMENTTYPE));
    List<DictConfig> dictConfigs = dictConfigService.findList(null, filters, null);
    String[] propertys = {"id", "configValue"};
    List<Map<String, Object>> result = FieldFilterUtils.filterCollection(propertys, dictConfigs);
    map.put("payType", result);
    response.setMsg(map);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

  /**
   * 订单数据
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/list", method = RequestMethod.POST)
  @ApiOperation(value = "订单数据", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "订单数据")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> list(
      @ApiParam(
          name = "请求参数(json)",
          value = "参数[paymentTypeId:支付方式ID; status:订单状态; purMethod:购买方式; orderSn:订单号; userName:用户名; sceneName:优享空间名称; startTime:开始日期; endTime:结束日期]",
          required = true) @RequestBody OrderRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    String sceneName = request.getSceneName();
    Long paymentTypeId = request.getPaymentTypeId();
    OrderStatus status = request.getStatus();
    PurMethod purMethod = request.getPurMethod();
    String orderSn = request.getOrderSn();
    String userName = request.getUserName();
    Date startTime = request.getStartTime();
    Date endTime = request.getEndTime();

    List<Filter> filters = new ArrayList<Filter>();
    if (StringUtils.isNotBlank(sceneName)) {
      filters.add(Filter.like("sceneName", "%" + sceneName.trim() + "%"));
    }
    if (StringUtils.isNotBlank(orderSn)) {
      filters.add(Filter.like("sn", "%" + orderSn.trim() + "%"));
    }
    if (StringUtils.isNotBlank(userName)) {
      filters.add(Filter.like("endUser.userName", "%" + userName.trim() + "%"));
    }
    if (startTime != null) {
      filters.add(Filter.ge("createDate", TimeUtils.formatDate2Day0(startTime)));
    }
    if (endTime != null) {
      filters.add(Filter.lt("createDate", TimeUtils.formatDate2Day59(endTime)));
    }
    if (paymentTypeId != null) {
      filters.add(Filter.eq("paymentTypeId", paymentTypeId));
    }
    if (purMethod != null) {
      filters.add(Filter.eq("purMethod", purMethod));
    }
    if (status != null) {
      filters.add(Filter.eq("status", status));
    }
    Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());
    pageable.setFilters(filters);
    List<Ordering> orderings = pageable.getOrderings();
    orderings.add(Ordering.desc("createDate"));

    Page<Order> orderPage = orderService.findPage(pageable);
    String[] propertys =
        {"id", "sn", "endUser.userName", "tourist.userName", "amount", "itemCount", "sceneName",
            "status", "purMethod", "paymentTypeId", "paymentType", "createDate"};
    List<Map<String, Object>> result =
        FieldFilterUtils.filterCollection(propertys, orderPage.getContent());
    PageResponse pageInfo =
        new PageResponse(pageable.getPageNumber(), pageable.getPageSize(),
            (int) orderPage.getTotal());
    response.setPage(pageInfo);
    response.setMsg(result);

    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }


  /**
   * 订单详情
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/detail", method = RequestMethod.POST)
  @ApiOperation(value = "订单详情", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "订单详情")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> detail(@ApiParam(name = "请求参数(json)",
      value = "参数[id:订单ID]", required = true) @RequestBody BaseRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    Long orderId = request.getId();
    List<Filter> filters = new ArrayList<Filter>();
    filters.add(Filter.eq("userOrder", orderId));
    List<OrderItem> items = orderItemService.findList(null, filters, null);
    String[] propertys =
        {"id", "price", "gName", "gSn", "spec", "cntrSn", "pickupStatus", "shipmentStatus",
            "refundStatus", "paymentTypeId"};
    List<Map<String, Object>> result = FieldFilterUtils.filterCollection(propertys, items);
    response.setMsg(result);

    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
}
