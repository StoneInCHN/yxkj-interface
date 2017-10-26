package com.yxkj.controller.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.ShipmentException;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.base.BaseListRequest;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.service.ShipmentExceptionService;
import com.yxkj.utils.FieldFilterUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * Controller - 出货异常管理
 * 
 * @author huyong
 *
 */
@Controller("ShipmentExceptionController")
@RequestMapping(value = "/admin/shipmentException")
@Api(value = "(货柜后台)异常管理", description = "异常页面", tags = "ShipmentExceptionController")
public class ShipmentExceptionController extends BaseController {

  @Resource(name = "shipmentExceptionServiceImpl")
  private ShipmentExceptionService shipmentExceptionService;

  @RequestMapping(value = "/list", method = RequestMethod.POST,
      produces = "application/json;charset=UTF-8")
  @ApiOperation(value = "出货异常记录", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "出货异常记录")
  @ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")
  public @ResponseBody ResponseMultiple<Map<String, Object>> list(@ApiParam(name = "请求参数Json",
      value = "参数[pageNumber:页码；pageSize:总页数]") @RequestBody BaseListRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();

    Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());

    List<Ordering> orderings = pageable.getOrderings();
    orderings.add(Ordering.desc("createDate"));

    Page<ShipmentException> shipmentExceptionPage = shipmentExceptionService.findPage(pageable);
    String[] propertys =
        {"id", "createDate", "sceneName", "cntrSn", "channelSn", "excpType", "excpReason"};
    List<Map<String, Object>> result =
        FieldFilterUtils.filterCollection(propertys, shipmentExceptionPage.getContent());
    PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), pageable.getPageSize(),
        (int) shipmentExceptionPage.getTotal());
    response.setPage(pageInfo);
    response.setMsg(result);

    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    return response;
  }
}
