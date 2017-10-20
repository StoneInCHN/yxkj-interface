package com.yxkj.shelf.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.yxkj.shelf.json.admin.request.ShelfOrderData;
import com.yxkj.shelf.json.admin.request.ShelfOrderRequest;
import com.yxkj.shelf.json.base.PageResponse;
import com.yxkj.shelf.json.base.ResponseMultiple;
import com.yxkj.shelf.json.base.ResponseOne;
import com.yxkj.shelf.service.ShelfOrderService;
import com.yxkj.shelf.utils.ExportHelper;
import com.yxkj.shelf.utils.FieldFilterUtils;
import com.yxkj.shelf.utils.TimeUtils;


/**
 * Controller - 订单管理
 * @author luzhang
 * 
 */
@Controller("orderController")
@RequestMapping("/admin/order")
@Api(value = "(货架后台)订单管理页面", description = "订单管理页面")
public class OrderController extends BaseController {   
	
	@Resource(name = "shelfOrderServiceImpl")
	private ShelfOrderService shelfOrderService;
	
	@Autowired
	private ExportHelper exportHelper;
	
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    @ApiOperation(value = "订单列表", httpMethod = "POST", response = ResponseMultiple.class, notes = "用于获取订单列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseMultiple<Map<String, Object>> getOrderList(
    		@ApiParam @RequestBody ShelfOrderRequest request) {
    	
      ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>(); 
      Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());      
      List<Filter> filters = pageable.getFilters();
      ShelfOrderData shelfOrderData = request.getShelfOrderData();
      if (shelfOrderData != null) {
          if (StringUtils.isNotBlank(shelfOrderData.getCompanyName())) {
              filters.add(Filter.like("comp.displayName", "%"+shelfOrderData.getCompanyName().trim()+"%"));
          }
          if (StringUtils.isNotBlank(shelfOrderData.getCompanySn())) {
              filters.add(Filter.like("comp.sn", "%"+shelfOrderData.getCompanySn().trim()+"%"));
          }
          if (StringUtils.isNotBlank(shelfOrderData.getPaymentType())) {
              filters.add(Filter.eq("paymentType", shelfOrderData.getPaymentType()));
          }
          if (shelfOrderData.getStatus() != null && shelfOrderData.getStatus().length > 0) {
        	  filters.add(Filter.in("status", shelfOrderData.getStatus()));
		  }
//          if (shelfOrderData.getBeginDate() != null) {
//              filters.add(Filter.ge("paymentTime", TimeUtils.formatDate2Day0(shelfOrderData.getBeginDate())));
//          }
//          if (shelfOrderData.getEndDate() != null) {
//              filters.add(Filter.lt("paymentTime", TimeUtils.formatDate2Day59(shelfOrderData.getEndDate())));
//          }
          if (shelfOrderData.getBeginDate() != null) {
              filters.add(Filter.ge("createDate", TimeUtils.formatDate2Day0(shelfOrderData.getBeginDate())));
          }
          if (shelfOrderData.getEndDate() != null) {
              filters.add(Filter.lt("createDate", TimeUtils.formatDate2Day59(shelfOrderData.getEndDate())));
          }          
          
	  }
      List<Ordering> orderings = pageable.getOrderings();
      orderings.add(Ordering.desc("createDate"));

      Page<ShelfOrder> orderPage = shelfOrderService.findPage(pageable);      
      String[] propertys =
          {"id", "sn", "tourist.userName", "tourist.nickName", "paymentType", 
    		  "paymentTime", "createDate", "amount", "profit", "status", "goodsCount", "comp.sn", "comp.displayName"};
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
    /**
     * 导出Excel
     * @throws IOException 
     */
    @RequestMapping(value = "/dataExport", method = {RequestMethod.GET, RequestMethod.POST})
    public void dataExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
      List<Ordering> orders = new ArrayList<Ordering>();
      orders.add(Ordering.desc("createDate"));
      List<Filter> filters = new ArrayList<Filter>();
      String companyName = request.getParameter("companyName");
	  String companySn = request.getParameter("companySn");	
      String beginDate = request.getParameter("beginDate");
	  String endDate = request.getParameter("endDate");	  
      if (StringUtils.isNotBlank(companyName)) {
          filters.add(Filter.like("comp.displayName", "%"+companyName.trim()+"%"));
      }
      if (StringUtils.isNotBlank(companySn)) {
          filters.add(Filter.like("comp.sn", "%"+companySn.trim()+"%"));
      }
      if (StringUtils.isNotBlank(beginDate)) {    	  
          filters.add(Filter.ge("paymentTime", TimeUtils.formatDate2Day0(beginDate)));
      }
      if (StringUtils.isNotBlank(endDate)) {
          filters.add(Filter.lt("paymentTime", TimeUtils.formatDate2Day59(endDate)));
      }
      List<ShelfOrder> lists = shelfOrderService.findList(null, filters, orders); 
      
      String title = "Order List"; // 工作簿标题，同时也是excel文件名前缀
      String[] headers = {"sn", "company", "touristNickName", "touristUserName", "paymentType", "paymentTime", "amount",  "status", "goodsCount"}; // 需要导出的字段
      String[] headersName = {"订单编号", "公司名", "用户昵称", "用户识别码", "支付方式", "支付时间", "订单金额", "交易状态", "商品数量"}; // 字段对应列的列名
      List<Map<String, String>> mapList = null;
      if (lists != null && lists.size() > 0) {
    	  mapList = exportHelper.prepareExportShelfOrder(lists);
          exportListToExcel(response, mapList, title, headers, headersName);
      }else {
    	  mapList = new ArrayList<Map<String, String>>();
    	  exportListToExcel(response, mapList, title, headers, headersName);
	  }
    }
}
