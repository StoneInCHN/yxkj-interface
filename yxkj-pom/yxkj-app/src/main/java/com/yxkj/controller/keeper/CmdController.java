package com.yxkj.controller.keeper;

import javax.annotation.Resource;

import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.request.CmdRequest;
import com.yxkj.service.CmdService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller("CmdController")
@RequestMapping("/cmd")
public class CmdController extends MobileBaseController {

  @Resource(name = "cmdServiceImpl")
  private CmdService cmdService;

  /**
   * 测试出货
   */
  @RequestMapping("salesOutTest")
  @ApiOperation(value = "salesOutTest", httpMethod = "POST", response = BaseResponse.class,
      notes = "salesOutTest", tags = "CmdController")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  @ResponseBody
  public BaseResponse salesOutTest(@ApiParam(name = "请求参数(json)", value = "channelId:货道ID",
      required = true)@RequestBody CmdRequest request) {
    BaseResponse response = new BaseResponse();
    cmdService.salesOutTest(request.getChannelId());
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

  /**
   * 调整音量
   */
  @RequestMapping("updateAudioVolume")
  @ApiOperation(value = "updateAudioVolume", httpMethod = "POST", response = BaseResponse.class,
      notes = "updateAudioVolume", tags = "CmdController")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  @ResponseBody
  public BaseResponse updateAudioVolume(@ApiParam(name = "请求参数(json)", value = "deviceNo:设备号；volume:音量（0-100）",
      required = true) @RequestBody CmdRequest request) {
    BaseResponse response = new BaseResponse();
    cmdService.updateAudioVolume(request.getDeviceNo(), request.getVolume());
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
  /**
   * 重启设备
   */
  @RequestMapping("rebootSystem")
  @ApiOperation(value = "rebootSystem", httpMethod = "POST", response = BaseResponse.class,
      notes = "rebootSystem", tags = "CmdController")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  @ResponseBody
  public BaseResponse rebootSystem(@ApiParam(name = "请求参数(json)", value = "deviceNo:设备号",
      required = true)@RequestBody CmdRequest request) {
    BaseResponse response = new BaseResponse();
    cmdService.appReboot(request.getDeviceNo());
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
}
