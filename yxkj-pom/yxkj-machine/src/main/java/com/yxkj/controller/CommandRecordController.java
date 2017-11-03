package com.yxkj.controller;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.common.commonenum.CommonEnum;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.entity.CommandRecord;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.request.CommandRequest;
import com.yxkj.json.request.MachineInfoRequest;
import com.yxkj.service.CmdService;
import com.yxkj.service.CommandRecordService;
import com.yxkj.service.VendingContainerService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 命令接口
 */
@Controller("commandController")
@RequestMapping("/cmd")
@Api(value = "命令", description = "命令接口")
public class CommandRecordController extends MobileBaseController {

  @Resource(name = "commandRecordServiceImpl")
  private CommandRecordService commandRecordService;

  @Resource(name = "cmdServiceImpl")
  private CmdService cmdService;

  @Resource(name = "vendingContainerServiceImpl")
  private VendingContainerService vendingContainerService;

  /**
   * 更新命令状态
   */
  @RequestMapping("sendCMD")
  @ApiOperation(value = "sendCMD", httpMethod = "POST", response = BaseResponse.class,
      notes = "sendCMD")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  @ResponseBody
  public BaseResponse sendCMD(Long orderId) {
    BaseResponse response = new BaseResponse();
    Map<String, String> contentMap = new HashMap<>();
    contentMap.put("video_bottom",
        "https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-smallvideo-transcode/115_1a22d4759be8239577563fafe92120bc_2.mp4");
    // cmdService.updateAdv("863010031227460", contentMap);
    // cmdService.updateAudioVolume("863010031227460", 20);
    cmdService.salesOut(orderId);
    // cmdService.appUpdate("863010031227460","http://192.167.1.242:8082/yxkj/app-debug.apk");
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

  /**
   * 更新命令状态
   */
  @RequestMapping("finishCmdStatus")
  @ApiOperation(value = "更新命令状态", httpMethod = "POST", response = BaseResponse.class,
      notes = "更新命令状态")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  @ResponseBody
  public BaseResponse finishCmdStatus(
      @ApiParam(value = "commandId:指令Id号，isSuccess:指令是否执行成功，extMsg:附加信息",
          required = true) @RequestBody CommandRequest request) {

    BaseResponse response = new BaseResponse();

    CommandRecord record = commandRecordService.updateCmdStatus(request.getCommandId(),
        request.getSuccess(), request.getExtMsg());
    if (record.getCmdStatus().equals(CommonEnum.CmdStatus.Finished))
      response.setCode(CommonAttributes.SUCCESS);
    else
      response.setCode(CommonAttributes.FAIL_COMMON);
    return response;
  }

  /**
   * 设备开机上传初始数据
   */
  @RequestMapping("initStatus")
  @ApiOperation(value = "设备开机上传初始数据", httpMethod = "POST", response = BaseResponse.class,
      notes = "设备开机上传初始数据")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  @ResponseBody
  public BaseResponse initStatus(
      @ApiParam(name = "请求参数(json)", value = "deviceNo:设备编号 | volume:音量（0-100）",
          required = true) @RequestBody MachineInfoRequest request) {

    BaseResponse response = new BaseResponse();
    vendingContainerService.initMachineStatus(request);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
  /**
   * 设备开机上传初始数据
   */
  @RequestMapping("connectionStatusUpdate")
  @ApiOperation(value = "更新连接状态", httpMethod = "POST", response = BaseResponse.class,
      notes = "更新连接状态")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  @ResponseBody
  public BaseResponse connectionStatusUpdate(
      @ApiParam(name = "请求参数(json)", value = "deviceNo:设备编号 | volume:音量（0-100）",
          required = true) @RequestBody MachineInfoRequest request) {

    BaseResponse response = new BaseResponse();
    vendingContainerService.updateConnectStatus(request);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
}
