package com.yxkj.controller;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.entity.CommandRecord;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.CommandRecordService;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 命令接口
 */
@Controller("commandController")
@RequestMapping("/cmd")
@Api(value = "命令", description = "命令接口")
public class CommandRecordController extends MobileBaseController {

    @Resource(name = "commandRecordServiceImpl")
    private CommandRecordService commandRecordService;

    /**
     * 更新命令状态
     */
    @RequestMapping("finishCmdStatus")
    @ApiOperation(value = "更新命令状态", httpMethod = "POST", response = BaseResponse.class,
            notes = "更新命令状态")
    @ApiResponses({@ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
    @ResponseBody
    @ApiImplicitParam(paramType = "query", name = "commandId", value = "命令记录ID号", required = true, dataType = "Long")
    public BaseResponse finishCmdStatus(Long commandId) {

        BaseResponse response = new BaseResponse();
        CommandRecord record = commandRecordService.find(commandId);
        record.setCmdStatus(CommonEnum.CmdStatus.Finished);

        record = commandRecordService.update(record);
        if (record.getCmdStatus().equals(CommonEnum.CmdStatus.Finished))
            response.setCode(CommonAttributes.SUCCESS);
        else
            response.setCode(CommonAttributes.FAIL_COMMON);
        return response;
    }
}
