package com.yxkj.controller.keeper;

import javax.annotation.Resource;

import com.yxkj.entity.VendingContainer;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.VendingContainerService;
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

import java.util.HashMap;
import java.util.Map;

@Controller("CmdController")
@RequestMapping("/cmd")
public class CmdController extends MobileBaseController {

  @Resource(name = "cmdServiceImpl")
  private CmdService cmdService;

  @Resource(name = "vendingContainerServiceImpl")
  private VendingContainerService vendingContainerService;

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
      required = true) @RequestBody CmdRequest request) {
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
  public BaseResponse updateAudioVolume(@ApiParam(name = "请求参数(json)",
      value = "deviceNo:设备号；volume:音量（0-100）", required = true) @RequestBody CmdRequest request) {
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
      required = true) @RequestBody CmdRequest request) {
    BaseResponse response = new BaseResponse();
    cmdService.appReboot(request.getDeviceNo());
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

  /**
   * 重启设备
   */
  @RequestMapping("getCurrentVolume")
  @ApiOperation(value = "getCurrentVolume", httpMethod = "POST", response = BaseResponse.class,
      notes = "getCurrentVolume", tags = "CmdController")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  @ResponseBody
  public ResponseOne<Map<String, String>> getCurrentVolume(@ApiParam(name = "请求参数(json)",
      value = "deviceNo:设备号", required = true) @RequestBody CmdRequest request) {
    ResponseOne<Map<String, String>> response = new ResponseOne<Map<String, String>>();
    VendingContainer vendingContainer = vendingContainerService.getByImei(request.getDeviceNo());
    Map<String, String> map = new HashMap<>();
    map.put("volume", String.valueOf(vendingContainer.getVolume()));
    response.setMsg(map);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
}
