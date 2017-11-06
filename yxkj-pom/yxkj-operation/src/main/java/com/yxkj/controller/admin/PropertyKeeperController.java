package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.ContainerKeeper;
import com.yxkj.entity.Order;
import com.yxkj.entity.PropertyKeeper;
import com.yxkj.entity.PropertyKeeperSalesReport;
import com.yxkj.entity.Scene;
import com.yxkj.entity.commonenum.CommonEnum.OrderStatus;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.request.OrderRequest;
import com.yxkj.json.admin.request.PropertyKeeperRequest;
import com.yxkj.json.admin.response.SceneProfile;
import com.yxkj.json.base.BaseListRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.FileService;
import com.yxkj.service.OrderService;
import com.yxkj.service.PropertyKeeperSalesReportService;
import com.yxkj.service.PropertyKeeperService;
import com.yxkj.service.SceneService;
import com.yxkj.utils.ExportHelper;
import com.yxkj.utils.FieldFilterUtils;
import com.yxkj.utils.GenerateRandom;
import com.yxkj.utils.TimeUtils;
import com.yxkj.utils.ToolsUtils;

/**
 * Controller - 物业管理
 * 
 * @author luzhang
 *
 */
@Controller("propertyKeeperController")
@RequestMapping("/admin/propertyKeeper")
@Api(value = "(货柜后台)物业管理页面", description = "物业管理页面")
public class PropertyKeeperController extends BaseController {

  @Resource(name = "propertyKeeperServiceImpl")
  private PropertyKeeperService propertyKeeperService;

  @Resource(name = "fileServiceImpl")
  private FileService fileService;

  @Resource(name = "sceneServiceImpl")
  private SceneService sceneService;

  @Resource(name = "orderServiceImpl")
  private OrderService orderService;

  @Resource(name = "propertyKeeperSalesReportServiceImpl")
  private PropertyKeeperSalesReportService propertyKeeperSalesReportService;

  @Autowired
  private ExportHelper exportHelper;

  @RequestMapping(value = "/keeperPage", method = RequestMethod.POST)
  @ApiOperation(value = "物业列表", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "用于获取物业列表")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> getKeeperList(
      @ApiParam(name = "请求参数(json)", value = "userName:用户名; pageNumber:页码; pageSize:每页数量",
          required = false) @RequestBody BaseListRequest request) {

    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());

    Page<PropertyKeeper> keeperPage = propertyKeeperService.findPage(pageable);
    String[] propertys = {"id", "userName", "realName", "cellPhoneNum", "scenes"};
    List<Map<String, Object>> result =
        FieldFilterUtils.filterCollection(propertys, keeperPage.getContent());
    PageResponse pageInfo =
        new PageResponse(pageable.getPageNumber(), pageable.getPageSize(),
            (int) keeperPage.getTotal());
    response.setPage(pageInfo);
    response.setMsg(result);

    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

  @RequestMapping(value = "/addKeeper", method = RequestMethod.POST)
  @ApiOperation(value = "添加物业", httpMethod = "POST", response = ResponseOne.class, notes = "用于添加物业")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse addKeeper(
      @ApiParam(
          name = "请求参数(json)",
          value = "userName:用户名; realName:物业姓名; cellPhoneNum:手机号; fenRunPoint:物业分润点; sceneIds:负责优享空间IDs",
          required = true) @RequestBody PropertyKeeperRequest request) {
    BaseResponse response = new BaseResponse();
    if (request.getSceneIds() != null && request.getSceneIds().length > 0) {
      PropertyKeeper keeper = propertyKeeperService.getPropertyKeeperEntity(request, null);
      String loginPwd = keeper.getLoginPwd();
      keeper.setLoginPwd(DigestUtils.md5Hex(loginPwd));
      propertyKeeperService.saveKeeper(keeper);
      ToolsUtils.sendSmsMsg(
          request.getCellPhoneNum(),
          message("yxkj.propertyKeeper.create.sendSMS", keeper.getRealName(), keeper.getUserName(),
              loginPwd, fileService.getProjectDeployUrl()));
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
    } else {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.failed"));
    }
    return response;
  }
  
  @RequestMapping(value = "/getKeeperData", method = RequestMethod.POST)
  @ApiOperation(value = "物业数据", httpMethod = "POST", response = ResponseMultiple.class, notes = "用于获取物业数据")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> getKeeperData(
  		@ApiParam(name = "请求参数(json)", value = "userName:用户名; id:管家ID;", required = false) 
  		@RequestBody BaseListRequest request) {
  	
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>(); 
    PropertyKeeper keeper = propertyKeeperService.find(request.getId());      
    String[] propertys = {"id", "userName", "realName", "cellPhoneNum", "scenes"};
    Map<String, Object> result = FieldFilterUtils.filterEntity(propertys, keeper);
    response.setMsg(result);

    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
  
  @RequestMapping(value = "/updateKeeper", method = RequestMethod.POST)
  @ApiOperation(value = "更新物业", httpMethod = "POST", response = ResponseOne.class, notes = "用于更新物业")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse updateGoods(
      @ApiParam(
          name = "请求参数(json)",
          value = "serName:用户名; realName:物业姓名; cellPhoneNum:手机号; fenRunPoint:物业分润点; sceneIds:负责优享空间IDs",
          required = true) @RequestBody PropertyKeeperRequest request) {
    BaseResponse response = new BaseResponse();
    if (request.getId() != null && request.getSceneIds() != null
        && request.getSceneIds().length > 0) {
      propertyKeeperService.updateKeeper(request);
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
    } else {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.failed"));
    }
    return response;
  }

  @RequestMapping(value = "/deleteKeeper", method = RequestMethod.POST)
  @ApiOperation(value = "删除物业", httpMethod = "POST", response = ResponseOne.class, notes = "用于删除物业")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse deleteGoods(@ApiParam(name = "请求参数(json)",
      value = "userName:用户名; ids:物业ID数组", required = true) @RequestBody BaseRequest request) {
    BaseResponse response = new BaseResponse();
    if (request.getIds() != null && request.getIds().length > 0) {
      propertyKeeperService.deleteKeeper(request.getIds());
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
    } else {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.failed"));
    }
    return response;
  }

  @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
  @ApiOperation(value = "物业重置（随机）密码", httpMethod = "POST", response = ResponseOne.class,
      notes = "物业重置（随机）密码")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse resetPwd(@ApiParam(name = "请求参数(json)",
      value = "userName:用户名; id:物业ID", required = true) @RequestBody BaseRequest request) {
    BaseResponse response = new BaseResponse();
    if (request.getId() == null) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.failed"));
    }
    PropertyKeeper keeper = propertyKeeperService.find(request.getId());
    String newPwd = new GenerateRandom().createPassWord(10);// 生成随机密码
    keeper.setLoginPwd(DigestUtils.md5Hex(newPwd));
    propertyKeeperService.update(keeper);
    ToolsUtils.sendSmsMsg(
        keeper.getCellPhoneNum(),
        message("yxkj.propertyKeeper.resetPwd.sendSMS", keeper.getRealName(), keeper.getUserName(),
            newPwd, fileService.getProjectDeployUrl()));
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));

    return response;
  }
  @RequestMapping(value = "/getSceneList", method = RequestMethod.POST)
  @ApiOperation(value = "获取优享空间列表", httpMethod = "POST", response = ResponseOne.class, notes = "用于获取优享空间列表")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<SceneProfile> getSceneList(@ApiParam @RequestBody BaseRequest request) {
    ResponseMultiple<SceneProfile> response = new ResponseMultiple<SceneProfile>(); 
    List<SceneProfile> list = sceneService.getSceneListByProperty(request.getId());
    response.setMsg(list);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    return response;
  }

  /**
   * 物业平台-总销售统计数据
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/salesSummary", method = RequestMethod.POST)
  @ApiOperation(value = "物业平台-总销售统计数据", httpMethod = "POST", response = ResponseOne.class,
      notes = "物业平台-总销售统计数据")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> salesSummary(@ApiParam(name = "请求参数(json)",
      value = "userName:用户名; id:物业ID", required = true) @RequestBody BaseRequest request) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    List<Filter> filters = new ArrayList<Filter>();
    filters.add(Filter.eq("proKeeperId", request.getId()));
    List<PropertyKeeperSalesReport> reports =
        propertyKeeperSalesReportService.findList(null, filters, null);
    BigDecimal salesAmount = new BigDecimal(0);
    BigDecimal profitAmount = new BigDecimal(0);
    for (PropertyKeeperSalesReport propertyKeeperSalesReport : reports) {
      profitAmount = profitAmount.add(propertyKeeperSalesReport.getProfitAmount());
      salesAmount = salesAmount.add(propertyKeeperSalesReport.getSaleAmount());
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("profitAmount", profitAmount);
    map.put("salesAmount", salesAmount);
    response.setMsg(map);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    return response;
  }

  /**
   * 物业平台-销售订单列表
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/salesOrderList", method = RequestMethod.POST)
  @ApiOperation(value = "物业平台-销售订单列表", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "物业平台-销售订单列表")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> salesOrderList(
      @ApiParam(
          name = "请求参数(json)",
          value = "userName:用户名; id:物业ID; sceneId:优享空间ID; startTime:开始时间; endTime:结束时间; pageNumber:页码; pageSize:页面大小",
          required = true) @RequestBody OrderRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    Date startTime = request.getStartTime();
    Date endTime = request.getEndTime();
    Long sceneId = request.getSceneId();

    List<Filter> filters = new ArrayList<Filter>();
    List<Filter> orderFilters = new ArrayList<Filter>();
    if (startTime != null) {
      orderFilters.add(Filter.ge("createDate", startTime));
      filters.add(Filter.ge("reportTime", startTime));
    }
    if (endTime != null) {
      orderFilters.add(Filter.le("createDate", endTime));
      filters.add(Filter.le("reportTime", endTime));
    }
    if (sceneId != null) {
      orderFilters.add(Filter.eq("sceneId", sceneId));
      filters.add(Filter.eq("sceneId", sceneId));
      filters.add(Filter.eq("proKeeperId", request.getId()));
      List<PropertyKeeperSalesReport> reports =
          propertyKeeperSalesReportService.findList(null, filters, null);
      BigDecimal salesAmount = new BigDecimal(0);
      BigDecimal profitAmount = new BigDecimal(0);
      for (PropertyKeeperSalesReport propertyKeeperSalesReport : reports) {
        profitAmount = profitAmount.add(propertyKeeperSalesReport.getProfitAmount());
        salesAmount = salesAmount.add(propertyKeeperSalesReport.getSaleAmount());
      }
      Scene scene = sceneService.find(sceneId);
      response.setDesc(salesAmount + "," + profitAmount + "," + scene.getFenRunPoint());
    } else {
      PropertyKeeper propertyKeeper = propertyKeeperService.find(request.getId());
      List<Long> sceneIds = new ArrayList<Long>();
      for (Scene scene : propertyKeeper.getScenes()) {
        sceneIds.add(scene.getId());
      }
      orderFilters.add(Filter.in("sceneId", sceneIds.toArray()));
    }

    orderFilters.add(Filter.ne("status", OrderStatus.UNPAID));

    Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());
    pageable.setFilters(orderFilters);
    List<Ordering> orderings = pageable.getOrderings();
    orderings.add(Ordering.desc("createDate"));
    Page<Order> orderPage = orderService.findPage(pageable);
    String[] propertys =
        {"id", "sn", "amount", "itemCount", "sceneName", "status", "fenRunPoint", "fenRunAmount",
            "createDate"};
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
   * 导出数据概览Excel
   * 
   * @throws IOException
   */
  @RequestMapping(value = "/dataExport", method = {RequestMethod.GET, RequestMethod.POST})
  public void dataExport(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String startTimeStr = request.getParameter("startTime");
    String endTimeStr = request.getParameter("endTime");
    String sceneIdStr = request.getParameter("sceneId");
    Long proKeeperId = Long.valueOf(request.getParameter("id"));

    Date startTime = null;
    Date endTime = null;
    Long sceneId = null;

    LogUtil.debug(this.getClass(), "dataExport",
        "request param: startTime: %s, endTime: %s, sceneId: %s,proKeeperId: %s", startTime,
        endTime, sceneId, proKeeperId);
    List<Filter> filters = new ArrayList<Filter>();
    if (sceneIdStr != null) {
      sceneId = Long.valueOf(sceneIdStr);
      filters.add(Filter.eq("sceneId", sceneId));
    } else {
      PropertyKeeper propertyKeeper = propertyKeeperService.find(proKeeperId);
      List<Long> sceneIds = new ArrayList<Long>();
      for (Scene scene : propertyKeeper.getScenes()) {
        sceneIds.add(scene.getId());
      }
      filters.add(Filter.in("sceneId", sceneIds.toArray()));
    }
    if (startTimeStr != null) {
      startTime = TimeUtils.formatDate2Day0(new Date(Long.valueOf(startTimeStr)));
      filters.add(Filter.ge("createDate", startTime));
    }
    if (endTimeStr != null) {
      endTime = TimeUtils.formatDate2Day59(new Date(Long.valueOf(endTimeStr)));
      filters.add(Filter.le("createDate", startTime));
    }
    filters.add(Filter.ne("status", OrderStatus.UNPAID));
    List<Ordering> orderings = new ArrayList<Ordering>();
    orderings.add(Ordering.desc("createDate"));
    List<Order> orders = orderService.findList(null, filters, orderings);
    String title = "PropertyKeeper Sales List"; // 工作簿标题，同时也是excel文件名前缀
    String[] headers =
        {"sceneName", "sn", "amount", "itemCount", "status", "createDate", "fenRunPoint",
            "fenRunAmount"}; // 需要导出的字段
    String[] headersName = {"优享空间", "订单号", "订单金额", "商品数量", "订单状态", "订单时间", "分润点", "分润金额"}; // 字段对应列的列名
    List<Map<String, String>> mapList = null;
    if (orders != null && orders.size() > 0) {
      mapList = exportHelper.prepareExportProKeeperOrderList(orders);
      exportListToExcel(response, mapList, title, headers, headersName);
    } else {
      mapList = new ArrayList<Map<String, String>>();
      exportListToExcel(response, mapList, title, headers, headersName);
    }
  }
}
