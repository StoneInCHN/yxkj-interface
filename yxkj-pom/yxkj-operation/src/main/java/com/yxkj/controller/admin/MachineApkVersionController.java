package com.yxkj.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.yxkj.entity.MachineApkVersion;
import com.yxkj.json.admin.request.MachineApkVersionListRequest;
import com.yxkj.json.admin.request.MachineApkVersionRequest;
import com.yxkj.service.MachineApkVersionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.AdMachine;
import com.yxkj.entity.AdResource;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.request.AdResourceListRequest;
import com.yxkj.json.admin.request.AdResourceRequest;
import com.yxkj.json.base.*;
import com.yxkj.service.AdResourceService;
import com.yxkj.service.FileService;
import com.yxkj.utils.FieldFilterUtils;

import io.swagger.annotations.*;

/**
 * Controller - 机器中控apk版本
 * 
 * @author huyong
 *
 */
@Controller("MachineApkVersionController")
@RequestMapping("/admin/machineApkVersion")
@Api(value = "(货柜后台)机器中控apk版本管理", description = "机器中控apk版本管理页面",
    tags = "MachineApkVersionController")
public class MachineApkVersionController extends BaseController {

  @Resource(name = "machineApkVersionServiceImpl")
  private MachineApkVersionService machineApkVersionService;


  @RequestMapping(value = "/add", method = RequestMethod.POST)
  @ApiOperation(value = "添加中控版本", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "添加中控版本")
  @ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")
  public @ResponseBody BaseResponse add(@ApiParam(name = "请求参数Json",
      value = "参数[versionName：版本号；versionCode:版本序列号；apkPath:文件访问地址；updateContent：版本更新内容；sceneIds：更新的优享空间id数组]") @RequestBody MachineApkVersionRequest request) {
    BaseResponse response = new BaseResponse();
    machineApkVersionService.saveMachineApkVersion(request);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.add.success"));
    return response;
  }

  @RequestMapping(value = "/addUpdateScene", method = RequestMethod.POST)
  @ApiOperation(value = "新增优享空间", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "新增优享空间")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse addUpdateScene(@ApiParam(name = "请求参数Json",
      value = "sceneIds：新增优享空间id数组]") @RequestBody MachineApkVersionRequest request) {
    BaseResponse response = new BaseResponse();
    if (request == null) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.param.notmatch"));
      return response;
    }

    machineApkVersionService.addUpdateScene(request);
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
      // adResourceService.delete(request.getIds());
    } catch (Exception e) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.adResource.hasUsed.cannot.remove"));
    }
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.remove.success"));
    return response;
  }

  @RequestMapping(value = "/list", method = RequestMethod.POST)
  @ApiOperation(value = "中控版本列表", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "中控版本列表")
  @ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")
  public @ResponseBody ResponseMultiple<MachineApkVersion> list(@ApiParam(name = "请求参数Json",
      value = "参数[pageNumber:页码；pageSize:总页数;fileType:广告类型]") @RequestBody MachineApkVersionListRequest request) {
    ResponseMultiple<MachineApkVersion> response = new ResponseMultiple<>();
    Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());

    List<Filter> filters = new ArrayList<>();
    if (request.getSceneName() != null) {
      LogUtil.debug(AdMachine.class, "list", "fileType:%s", request.getSceneName());
      Filter fileTypeFilter =
          new Filter("scene.name", Filter.Operator.like, "%" + request.getSceneName() + "%");
      filters.add(fileTypeFilter);
    }

    pageable.setFilters(filters);
    List<Ordering> orderings = pageable.getOrderings();
    orderings.add(Ordering.desc("createDate"));

    Page<MachineApkVersion> machineApkVersionPage = machineApkVersionService.findPage(pageable);
    PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), pageable.getPageSize(),
        (int) machineApkVersionPage.getTotal());
    response.setPage(pageInfo);
    response.setMsg(machineApkVersionPage.getContent());

    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.request.success"));
    return response;
  }

  /**
   * 批量上传图片
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/batchUploadFile", method = {RequestMethod.GET, RequestMethod.POST})
  public @ResponseBody ResponseOne<List<String>> batchUploadFile(HttpServletRequest request,
      CommonEnum.FileType fileType) {
    ResponseOne<List<String>> response = new ResponseOne<>();
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    // adResourceService.batchUpload(fileMap, fileType);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
}
