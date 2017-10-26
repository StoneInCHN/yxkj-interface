package com.yxkj.controller.file;

import com.yxkj.entity.commonenum.CommonEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.yxkj.utils.KeyGenerator;
import com.yxkj.utils.RSAHelper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.beans.Setting.CaptchaType;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.Admin;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;
import com.yxkj.entity.commonenum.CommonEnum.ImageType;
import com.yxkj.json.admin.request.LoginRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.AdminService;
import com.yxkj.service.CaptchaService;
import com.yxkj.service.CompanyService;
import com.yxkj.service.FileService;
import com.yxkj.utils.TimeUtils;
import com.yxkj.utils.TokenUtil;


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
}
