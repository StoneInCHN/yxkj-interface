package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
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
import com.yxkj.entity.KeeperRemindMsg;
import com.yxkj.entity.OperationMsgInfo;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.request.MsgRequest;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.service.KeeperRemindMsgService;
import com.yxkj.service.OperationMsgInfoService;
import com.yxkj.utils.FieldFilterUtils;

/**
 * 运营平台 消息 - Controller
 * 
 * @author Andrea
 * @version 2017年10月31日 下午5:33:49
 */
@Controller("msgInfoController")
@RequestMapping("/admin/msgInfo")
@Api(value = "消息", description = "消息")
public class MsgInfoController extends BaseController {


  @Resource(name = "keeperRemindMsgServiceImpl")
  private KeeperRemindMsgService keeperRemindMsgService;

  @Resource(name = "operationMsgInfoServiceImpl")
  private OperationMsgInfoService operationMsgInfoService;

  /**
   * 消息-补货取货通知
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/keeperMsg", method = RequestMethod.POST)
  @ApiOperation(value = "消息-补货取货通知", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "消息-补货取货通知")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> keeperMsg(@ApiParam(
      name = "请求参数(json)", value = "参数[userName:登录用户名; type:通知类型; pageNumber:页码; pageSize:每页数量]",
      required = true) @RequestBody MsgRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();

    List<Filter> filters = new ArrayList<Filter>();
    filters.add(Filter.eq("type", request.getType()));
    Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());
    pageable.setFilters(filters);
    List<Ordering> orderings = pageable.getOrderings();
    orderings.add(Ordering.desc("createDate"));

    Page<KeeperRemindMsg> msgs = keeperRemindMsgService.findPage(pageable);
    String[] propertys = {"id", "title", "createDate", "content"};
    List<Map<String, Object>> result =
        FieldFilterUtils.filterCollection(propertys, msgs.getContent());
    PageResponse pageInfo =
        new PageResponse(pageable.getPageNumber(), pageable.getPageSize(), (int) msgs.getTotal());
    response.setPage(pageInfo);
    response.setMsg(result);

    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }


  /**
   * 消息-其他消息
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/otherMsg", method = RequestMethod.POST)
  @ApiOperation(value = "消息-其他消息", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "消息-其他消息")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> otherMsg(@ApiParam(
      name = "请求参数(json)", value = "参数[userName:登录用户名; type:消息类型; pageNumber:页码; pageSize:每页数量]",
      required = true) @RequestBody MsgRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();

    List<Filter> filters = new ArrayList<Filter>();
    filters.add(Filter.eq("type", request.getType()));
    Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());
    pageable.setFilters(filters);
    List<Ordering> orderings = pageable.getOrderings();
    orderings.add(Ordering.desc("createDate"));

    Page<OperationMsgInfo> msgs = operationMsgInfoService.findPage(pageable);
    String[] propertys = {"id", "title", "createDate", "content"};
    List<Map<String, Object>> result =
        FieldFilterUtils.filterCollection(propertys, msgs.getContent());
    PageResponse pageInfo =
        new PageResponse(pageable.getPageNumber(), pageable.getPageSize(), (int) msgs.getTotal());
    response.setPage(pageInfo);
    response.setMsg(result);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

}
