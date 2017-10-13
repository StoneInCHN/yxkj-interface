package com.yxkj.controller;

import com.yxkj.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.aspect.UserValidCheck;
import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.entity.CmdMsg;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.json.request.UserInfoReq;
import com.yxkj.service.CmdService;
import com.yxkj.service.GoodsService;
import com.yxkj.service.TouristService;
import com.yxkj.utils.TokenUtil;

/**
 * Controller - 共用
 */
@Controller("commonController")
@RequestMapping("/common")
@Api(value = "common", description = "公共方法")
// 添加注释
public class CommonController extends MobileBaseController {

    @Resource(name = "cmdServiceImpl")
    private CmdService cmdService;

    @Resource(name = "touristServiceImpl")
    private TouristService touristService;

    @Resource(name = "goodsServiceImpl")
    private GoodsService goodsService;

    @Resource(name = "orderServiceImpl")
    private OrderService orderService;

//    @Resource(name = "taskExecutor")
//    private TaskExecutor taskExecutor;


    /**
     * 用户登录
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录login", httpMethod = "POST", response = BaseResponse.class,
            notes = "用户登录")
    public @ResponseBody
    BaseResponse login(@ApiParam(name = "基础对象", value = "userName:用户名",
            required = true) @RequestBody BaseRequest req) {
        BaseResponse response = new BaseResponse();
        String key = setting.getServerPublicKey();
        response.setCode(CommonAttributes.SUCCESS);
        response.setToken(req.getUserName().toString());
        response.setDesc(TokenUtil.getJWTString(req.getUserName().toString(), ""));
        return response;
    }


    /**
     * swagger测试
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ApiOperation(value = "swagger测试", httpMethod = "POST", response = BaseResponse.class,
            notes = "swagger测试")
    @UserValidCheck
    public @ResponseBody
    BaseResponse test(@ApiParam(name = "请求参数(json)",
            value = "userName:用户名|header:用户令牌", required = true) @RequestBody BaseRequest req) {
        BaseResponse response = new BaseResponse();
        String key = setting.getServerPublicKey();
        response.setCode(CommonAttributes.SUCCESS);
        response.setToken(req.getUserName().toString());
        response.setDesc("中文测试");
        return response;
    }

    /**
     * swagger测试
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/testSendCmd", method = RequestMethod.POST)
    @ApiOperation(value = "Redis测试", httpMethod = "POST", response = BaseResponse.class,
            notes = "Redis测试")
    public @ResponseBody
    BaseResponse testSendCmd(@RequestBody CmdMsg cmdMsg) {
        BaseResponse response = new BaseResponse();

        // CmdMsg cmdMsg = new CmdMsg();
        // cmdMsg.setContent("test");
        // cmdMsg.setType(0);
        // cmdMsg.setDeviceNo("001");
        cmdService.sendCmd(cmdMsg);
//        orderService.salesOut(1L);
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc("测试Redis队列");
        return response;
    }


    /**
     * 获取授权用户信息(包括支付宝,微信用户)
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/authUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "获取授权用户信息", httpMethod = "POST", response = ResponseOne.class,
            notes = "获取授权用户信息(微信及支付宝)")
    @ApiResponses({@ApiResponse(code = 200, message = "code:0000-request success|code:1000-auth fail")})
    public @ResponseBody
    ResponseOne<Map<String, Object>> authUserInfo(
            @ApiParam(name = "请求参数(json)", value = "authCode:授权码|gList:商品集合字符串|type:支付类型wx,alipay",
                    required = true) @RequestBody UserInfoReq req) {
        ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
        Map<String, Object> resMap = new HashMap<String, Object>();
        String authCode = req.getAuthCode();
        Map<String, Object> result = null;
        if ("wx".equals(req.getType())) {
            result =
                    touristService.getWxUserInfo(setting.getWxPublicAppId(), setting.getWxPublicAppSecret(),
                            authCode);
        }
        if ("alipay".equals(req.getType())) {
            result =
                    touristService.getAlipayUserInfo(setting.getAlipayPublicKey(),
                            setting.getAlipayPrivateKey(), setting.getAlipayAppId(), authCode);
        }
        if (result == null || result.isEmpty()) {
            response.setCode(CommonAttributes.FAIL_COMMON);
            response.setDesc(message("yxkj.auth.failed"));
            return response;
        }
        resMap.put("userInfo", result);

        List<Map<String, Object>> gMaps = goodsService.getGforScanH5(req.getgList());
        resMap.put("gInfo", gMaps);

        response.setMsg(resMap);
        response.setCode(CommonAttributes.SUCCESS);

        String userId = (String) result.get("userId");
        String nickName = (String) result.get("nickname");
        response.setToken(TokenUtil.getJWTString(userId, ""));
        // taskExecutor.execute(new Runnable() {
        // public void run() {
        // touristService.saveTourist(userId, nickName, req.getType(), company);
        // }
        // });
        return response;
    }
}
