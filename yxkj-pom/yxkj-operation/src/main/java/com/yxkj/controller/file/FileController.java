package com.yxkj.controller.file;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.entity.commonenum.CommonEnum.ImageType;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.AdResourceService;
import com.yxkj.service.FileService;

import io.swagger.annotations.Api;


/**
 * Controller - 文件上传管理
 * 
 * 
 */
@Controller("fileController")
@RequestMapping("/file")
@Api(value = "(货架后台)公共", description = "公共")
public class FileController extends BaseController {

  @Resource(name = "fileServiceImpl")
  private FileService fileService;
  @Resource(name = "adResourceServiceImpl")
  private AdResourceService adResourceService;

  /**
   * 上传文件(单个)
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/uploadFile", method = {RequestMethod.GET, RequestMethod.POST})
  public @ResponseBody BaseResponse uploadFile(HttpServletRequest request, ImageType imageType) {
    BaseResponse response = new BaseResponse();
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
      MultipartFile mf = entity.getValue();
      String displayPath = fileService.saveImage(mf, imageType);
      response.setDesc(displayPath);
      break;
    }
    response.setCode(CommonAttributes.SUCCESS);
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
      ImageType imageType, CommonEnum.FileType fileType) {
    ResponseOne<List<String>> response = new ResponseOne<>();
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    List<String> displayPathList = new ArrayList<>();
    if (imageType == ImageType.AD_RESOURCE) {
      adResourceService.batchUpload(fileMap, fileType);
    } else {
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
        MultipartFile mf = entity.getValue();
        String displayPath = fileService.saveImage(mf, imageType);
        displayPathList.add(displayPath);
      }
      response.setMsg(displayPathList);
    }
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
}
