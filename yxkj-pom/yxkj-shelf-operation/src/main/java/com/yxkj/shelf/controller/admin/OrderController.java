package com.yxkj.shelf.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.entity.ShelfOrder;
import com.yxkj.shelf.beans.CommonAttributes;
import com.yxkj.shelf.controller.base.BaseController;
import com.yxkj.shelf.framework.filter.Filter;
import com.yxkj.shelf.framework.ordering.Ordering;
import com.yxkj.shelf.framework.paging.Page;
import com.yxkj.shelf.framework.paging.Pageable;
import com.yxkj.shelf.json.admin.request.ShelfOrderRequest;
import com.yxkj.shelf.json.base.PageResponse;
import com.yxkj.shelf.json.base.ResponseMultiple;
import com.yxkj.shelf.json.base.ResponseOne;
import com.yxkj.shelf.service.ShelfOrderService;
import com.yxkj.shelf.utils.FieldFilterUtils;


/**
 * Controller - 订单管理
 * 
 */
@Controller("orderController")
@RequestMapping("/admin/order")
@Api(value = "(货架后台)订单管理页面", description = "订单管理页面")
public class OrderController extends BaseController {   
	
	@Resource(name = "shelfOrderServiceImpl")
	private ShelfOrderService shelfOrderService;
	
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    @ApiOperation(value = "订单列表", httpMethod = "POST", response = ResponseMultiple.class, notes = "用于获取订单列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseMultiple<Map<String, Object>> getOrderList(
    		@ApiParam @RequestBody ShelfOrderRequest request) {
    	
      ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>(); 
      Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());      
      List<Filter> filters = pageable.getFilters();
      if (request.getSn() != null) {
        filters.add(Filter.eq("sn", request.getSn()));
      }
      List<Ordering> orderings = pageable.getOrderings();
      orderings.add(Ordering.desc("createDate"));

      Page<ShelfOrder> orderPage = shelfOrderService.findPage(pageable);      
      String[] propertys =
          {"id", "sn", "tourist.userName", "tourist.nickName", "paymentType", 
    		  "paymentTime", "amount", "profit", "status", "goodsCount", "comp.sn", "comp.displayName"};
      List<Map<String, Object>> result =
          FieldFilterUtils.filterCollection(propertys, orderPage.getContent());
      PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), 
    		  pageable.getPageSize(), (int)orderPage.getTotal());
      response.setPage(pageInfo);
      response.setMsg(result);

      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
    
    @RequestMapping(value = "/getOrderDetail", method = RequestMethod.POST)
    @ApiOperation(value = "订单详情", httpMethod = "POST", response = ResponseOne.class, notes = "用于获取订单详情")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<ShelfOrder> getOrderDetail(
    		@ApiParam @RequestBody ShelfOrderRequest request) {
    	
      ResponseOne<ShelfOrder> response = new ResponseOne<ShelfOrder>(); 
      Long orderId = request.getId();  	
      if (orderId == null || orderId <= 0) {
          response.setCode(CommonAttributes.FAIL_LOGIN);
          response.setDesc(message("yxkj.request.param.missing"));
          return response;
      }      
      ShelfOrder shelfOrder = shelfOrderService.find(orderId);
      
      response.setMsg(shelfOrder);
      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
}
