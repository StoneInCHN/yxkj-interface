package com.yxkj.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yxkj.common.log.LogUtil;
import com.yxkj.entity.AdMachine;
import com.yxkj.framework.filter.Filter;
import com.yxkj.json.admin.request.AdMachineListRequest;
import com.yxkj.json.admin.request.AdMachineRequest;
import com.yxkj.service.AdMachineService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.AdResource;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.request.AdResourceRequest;
import com.yxkj.json.base.*;
import com.yxkj.service.AdResourceService;
import com.yxkj.utils.FieldFilterUtils;

import io.swagger.annotations.*;

/**
 * Controller - 广告库管理
 * 
 * @author huyong
 *
 */
@Controller("AdMachineController")
@RequestMapping(value = "/admin/advertisement")
@Api(value = "(货柜后台)广告管理", description = "广告管理页面", tags = "AdMachineController")
public class AdMachineController extends BaseController {

  @Resource(name = "adResourceServiceImpl")
  private AdResourceService adResourceService;

  @Resource(name = "adMachineServiceImpl")
  private AdMachineService adMachineService;

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  @ApiOperation(value = "添加广告", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "添加广告")
  @ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")
  public @ResponseBody BaseResponse add(@ApiParam(name = "请求参数Json",
      value = "参数[fileName:文件名；fileUrl:文件访问地址；fileType:广告类型]") @RequestBody AdResourceRequest request) {
    BaseResponse response = new BaseResponse();
    AdResource adResource = new AdResource();
    adResource.setRemark(request.getRemark());
    adResource.setFileUrl(request.getFileUrl());
    adResource.setFileName(request.getFileName());
    adResource.setFileType(request.getFileType());
    adResourceService.save(adResource);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.add.success"));
    return response;
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  @ApiOperation(value = "编辑广告", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "编辑广告")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse update(@ApiParam(name = "请求参数Json",
      value = "参数[adva:视频A:advb:视频B；advc:图片1；advd:图片2；adve:图片3]") @RequestBody AdMachineRequest request) {
    BaseResponse response = new BaseResponse();
    if (request == null) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.param.notmatch"));
      return response;
    }

    adMachineService.updateAdMachine(request);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.update.success"));
    return response;
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @ApiOperation(value = "删除广告", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "删除广告")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse delete(@ApiParam(name = "请求参数Json",
      value = "参数[ids:需要删除的广告库id数组]") @RequestBody BaseRequest request) {

    BaseResponse response = new BaseResponse();

    if (request == null || request.getIds().length == 0) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.param.notmatch"));
      return response;
    }
    try {
      adResourceService.delete(request.getIds());
    } catch (Exception e) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.adResource.hasUsed.cannot.remove"));
    }
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.remove.success"));
    return response;
  }

  @RequestMapping(value = "/list", method = RequestMethod.POST,
      produces = "application/json;charset=UTF-8")
  @ApiOperation(value = "广告列表", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "广告列表")
  @ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")
  public @ResponseBody ResponseMultiple<Map<String, Object>> list(@ApiParam(name = "请求参数Json",
      value = "参数[pageNumber:页码；pageSize:总页数；sceneName：优享空间名称；sceneSn:优享空间编号]") @RequestBody AdMachineListRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    List<Filter> filters = new ArrayList<>();
    if (request.getSceneName() != null) {
      LogUtil.debug(AdMachine.class, "list", "sceneName:%s", request.getSceneName());
      Filter sceneNameFilter =
          new Filter("scene.name", Filter.Operator.like, "%" + request.getSceneName() + "%");
      filters.add(sceneNameFilter);
    }
    if (request.getSceneSn() != null) {
      LogUtil.debug(AdMachine.class, "list", "sceneSn:%s", request.getSceneSn());
      Filter sceneNameFilter =
          new Filter("scene.sn", Filter.Operator.like, "%" + request.getSceneSn() + "%");
      filters.add(sceneNameFilter);
    }

    Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());

    pageable.setFilters(filters);
    List<Ordering> orderings = pageable.getOrderings();
    orderings.add(Ordering.desc("createDate"));

    Page<AdMachine> adMachinePage = adMachineService.findPage(pageable);
    String[] propertys = {"id", "scene.name", "scene.sn", "advA", "advB", "advC", "advD", "advE"};
    List<Map<String, Object>> result =
        FieldFilterUtils.filterCollection(propertys, adMachinePage.getContent());
    PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), pageable.getPageSize(),
        (int) adMachinePage.getTotal());
    response.setPage(pageInfo);
    response.setMsg(result);

    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    return response;
  }
}
