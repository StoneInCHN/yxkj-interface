package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.Date;
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
import com.yxkj.entity.EndUser;
import com.yxkj.entity.Tourist;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.request.UserRequest;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.service.EndUserService;
import com.yxkj.service.TouristService;
import com.yxkj.utils.FieldFilterUtils;
import com.yxkj.utils.TimeUtils;

/**
 * 用户数据 - Controller
 * 
 * @author Andrea
 * @version 2017年10月24日 上午10:42:55
 */
@Controller("userController")
@RequestMapping("/admin/user")
@Api(value = "(货柜后台)用户数据", description = "用户数据")
public class UserController extends BaseController {


  @Resource(name = "touristServiceImpl")
  private TouristService touristService;

  @Resource(name = "endUserServiceImpl")
  private EndUserService endUserService;

  /**
   * 用户数据-所有用户
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/allList", method = RequestMethod.POST)
  @ApiOperation(value = "用户数据-所有用户", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "用户数据-所有用户")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> list(
      @ApiParam(name = "请求参数(json)", value = "参数[sceneId:优享空间ID; startTime:开始日期; endTime:结束日期]",
          required = true) @RequestBody UserRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    Long sceneId = request.getSceneId();
    Date startTime = request.getStartTime();
    Date endTime = request.getEndTime();

    List<Filter> filters = new ArrayList<Filter>();
    if (sceneId != null) {
      filters.add(Filter.eq("sceneId", sceneId));
    }
    if (startTime != null) {
      filters.add(Filter.ge("createDate", TimeUtils.formatDate2Day0(startTime)));
    }
    if (endTime != null) {
      filters.add(Filter.lt("createDate", TimeUtils.formatDate2Day59(endTime)));
    }
    Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());
    pageable.setFilters(filters);
    List<Ordering> orderings = pageable.getOrderings();
    orderings.add(Ordering.desc("createDate"));

    Page<Tourist> touristPage = touristService.findPage(pageable);
    String[] propertys =
        {"id", "userName", "nickName", "userChannel", "cellPhoneNum", "gender", "regTime",
            "sceneName"};
    List<Map<String, Object>> result =
        FieldFilterUtils.filterCollection(propertys, touristPage.getContent());
    PageResponse pageInfo =
        new PageResponse(pageable.getPageNumber(), pageable.getPageSize(),
            (int) touristPage.getTotal());
    response.setPage(pageInfo);
    response.setMsg(result);

    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }


  /**
   * 用户数据-注册用户
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/regList", method = RequestMethod.POST)
  @ApiOperation(value = "用户数据-注册用户", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "用户数据-注册用户")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> regList(
      @ApiParam(name = "请求参数(json)", value = "参数[sceneId:优享空间ID; startTime:开始日期; endTime:结束日期]",
          required = true) @RequestBody UserRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    Long sceneId = request.getSceneId();
    Date startTime = request.getStartTime();
    Date endTime = request.getEndTime();

    List<Filter> filters = new ArrayList<Filter>();
    if (sceneId != null) {
      filters.add(Filter.eq("sceneId", sceneId));
    }
    if (startTime != null) {
      filters.add(Filter.ge("createDate", TimeUtils.formatDate2Day0(startTime)));
    }
    if (endTime != null) {
      filters.add(Filter.lt("createDate", TimeUtils.formatDate2Day59(endTime)));
    }
    Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());
    pageable.setFilters(filters);
    List<Ordering> orderings = pageable.getOrderings();
    orderings.add(Ordering.desc("createDate"));

    Page<EndUser> touristPage = endUserService.findPage(pageable);
    String[] propertys =
        {"id", "wechatNickName", "alipayName", "userChannel", "cellPhoneNum", "gender", "sceneName"};
    List<Map<String, Object>> result =
        FieldFilterUtils.filterCollection(propertys, touristPage.getContent());
    PageResponse pageInfo =
        new PageResponse(pageable.getPageNumber(), pageable.getPageSize(),
            (int) touristPage.getTotal());
    response.setPage(pageInfo);
    response.setMsg(result);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

}
