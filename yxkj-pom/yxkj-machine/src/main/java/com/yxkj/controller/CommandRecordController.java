package com.yxkj.controller;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.commonenum.CommonEnum;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.entity.CommandRecord;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.service.CmdService;
import com.yxkj.service.CommandRecordService;
import com.yxkj.service.OrderService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
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

  @Resource(name = "orderServiceImpl")
  private OrderService orderService;

  /**
   * 更新命令状态
   */
  @RequestMapping("sendCMD")
  @ApiOperation(value = "sendCMD", httpMethod = "POST", response = BaseResponse.class,
      notes = "sendCMD")
  @ApiResponses({
      @ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
  @ResponseBody
  public BaseResponse sendCMD() {
    BaseResponse response = new BaseResponse();
    Map<String, String> contentMap = new HashMap<>();
    contentMap.put("video_bottom",
        "https://gss3.baidu.com/6LZ0ej3k1Qd3ote6lo7D0j9wehsv/tieba-smallvideo-transcode/115_1a22d4759be8239577563fafe92120bc_2.mp4");
    // cmdService.updateAdv("863010031227460", contentMap);
     cmdService.updateAudioVolume("863010031227460", 20);
    cmdService.salesOut(1l);
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
  @ApiImplicitParams({
      @ApiImplicitParam(paramType = "query", name = "commandId", value = "命令记录ID号", required = true,
          dataType = "Long"),
      @ApiImplicitParam(paramType = "query", name = "isSuccess", value = "操作是否成功", required = true,
          dataType = "Boolean")})
  public BaseResponse finishCmdStatus(Long commandId, Boolean isSuccess) {

    BaseResponse response = new BaseResponse();

    CommandRecord record = commandRecordService.updateCmdStatus(commandId, isSuccess);
    if (record.getCmdStatus().equals(CommonEnum.CmdStatus.Finished))
      response.setCode(CommonAttributes.SUCCESS);
    else
      response.setCode(CommonAttributes.FAIL_COMMON);
    return response;
  }


}
