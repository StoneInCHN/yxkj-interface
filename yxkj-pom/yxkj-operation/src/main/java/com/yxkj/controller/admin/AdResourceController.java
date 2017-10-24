package com.yxkj.controller.admin;

import javax.annotation.Resource;

import com.yxkj.entity.AdResource;
import com.yxkj.json.admin.request.AdResourceRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.Admin;
import com.yxkj.json.admin.request.ChangePwdRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.service.AdResourceService;

import io.swagger.annotations.*;

/**
 * Controller - 广告库管理
 * 
 * @author huyong
 *
 */
@Controller("AdResourceController")
@RequestMapping("/admin/advertisement")
@Api(value = "(货柜后台)广告库管理", description = "广告库管理页面")
public class AdResourceController extends BaseController {

  @Resource(name = "adResourceServiceImpl")
  private AdResourceService adResourceService;

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  @ApiOperation(value = "添加广告", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "添加广告")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse add(@ApiParam @RequestBody AdResource request) {
    BaseResponse response = new BaseResponse();
    adResourceService.save(request);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.add.success"));
    return response;
  }

  @RequestMapping(value = "/update", method = RequestMethod.POST)
  @ApiOperation(value = "编辑广告", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "编辑广告")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse update(@ApiParam @RequestBody AdResource request) {
    BaseResponse response = new BaseResponse();
    if (request == null) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.param.notmatch"));
      return response;
    }

    adResourceService.updateAdResource(request);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.update.success"));
    return response;
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @ApiOperation(value = "删除广告", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "删除广告")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse update(@ApiParam Long[] adResourceIds) {

    BaseResponse response = new BaseResponse();

    if (adResourceIds == null || adResourceIds.length == 0) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.param.notmatch"));
    }
    adResourceService.delete(adResourceIds);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.remove.success"));
    return response;
  }
}
