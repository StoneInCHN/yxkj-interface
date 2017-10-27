package com.yxkj.controller.keeper;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.security.PrivateKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.entity.ContainerKeeper;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.request.KeeperAccountRequest;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.ContainerKeeperService;
import com.yxkj.utils.KeyGenerator;
import com.yxkj.utils.RSAHelper;
import com.yxkj.utils.SmsUtil;
import com.yxkj.utils.TimeUtils;
import com.yxkj.utils.TokenUtil;

@Controller("keeperController")
@RequestMapping("/keeper")
public class AcconutController extends MobileBaseController {

  @Resource(name = "containerKeeperServiceImpl")
  private ContainerKeeperService containerKeeperService;

  @Resource(name = "redisTemplate")
  private RedisTemplate<String, String> redisTemplate;

  /**
   * 获取公钥
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/getPublicKey", method = RequestMethod.GET)
  @ApiOperation(value = "获取公钥", httpMethod = "GET", response = ResponseOne.class, notes = "获取公钥")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, String>> getPublicKey(HttpServletRequest request) {
    ResponseOne<Map<String, String>> response = new ResponseOne<Map<String, String>>();
    Map<String, String> map = new HashMap<>();
    try {
      map.put("publicKey", setting.getServerPublicKey());
      response.setMsg(map);
    } catch (Exception e) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.request.failed"));
      return response;
    }
    LogUtil.debug(this.getClass(), "getPublicKey", "获取公钥");
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.keeper.publickey.success"));
    return response;
  }

  /**
   * 管家密码登录
   * 
   * @param keeperRequest
   * @param request
   * @return
   */
  @RequestMapping(value = "/loginByPwd", method = RequestMethod.POST)
  @ApiOperation(value = "用户密码登录", httpMethod = "POST", response = ResponseOne.class, notes = "密码登录")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:登录成功; 0001,0006:登录失败]")})
  public @ResponseBody ResponseOne<ContainerKeeper> loginByPwd(
      @ApiParam(name = "请求参数(json)", value = "{cellPhoneNum:手机号,password:密码", required = true)
      @RequestBody KeeperAccountRequest keeperAccountRequest, HttpServletRequest request) {
    ResponseOne<ContainerKeeper> response = new ResponseOne<ContainerKeeper>();
    String cellPhoneNum = keeperAccountRequest.getCellPhoneNum();

    PrivateKey privateKey;
    try {
      privateKey = RSAHelper.getPrivateKey(setting.getServerPrivateKey());
    } catch (Exception e) {
      response.setCode(CommonAttributes.FAIL_LOGIN);
      response.setDesc(message("yxkj.keeper.password.analysis.error"));
      LogUtil.debug(this.getClass(), "login", "密码解析错误");
      return response;
    }
    if (StringUtils.isEmpty(cellPhoneNum) || StringUtils.isEmpty(keeperAccountRequest.getPassword())) {
      response.setCode(CommonAttributes.MISSING_REQUIRE_PARAM);
      response.setDesc(message("yxkj.request.param.missing"));
      return response;
    }

    LogUtil.debug(this.getClass(), "login", "登录管家手机号:%s", cellPhoneNum);
    ContainerKeeper keeper = null;

    try{
      keeper = containerKeeperService.findByCellPhoneNum(cellPhoneNum);
    }catch (Exception e) {
      response.setCode(CommonAttributes.ERROR_ACCOUNT);
      response.setDesc(message("yxkj.keeper.user.undefined"));
      LogUtil.debug(this.getClass(), "login", "用户不存在");
      return response;
    }
    if (!keeper.getAccountStatus().equals(AccountStatus.ACTIVED)) {
      response.setCode(CommonAttributes.ERROR_ACCOUNT);
      response.setDesc(message("yxkj.keeper.accountStatus.invalid"));
      LogUtil.debug(this.getClass(), "login", "账号无效");
      return response;
    }
    String password = KeyGenerator.decrypt(keeperAccountRequest.getPassword(), privateKey);
    if (StringUtils.isEmpty(password)||!DigestUtils.md5Hex(password).equals(keeper.getLoginPwd())) {
      response.setCode(CommonAttributes.ERROR_PASS);
      response.setDesc(message("yxkj.keeper.password.error"));
      LogUtil.debug(this.getClass(), "login", "密码错误");
      return response;
    }
    if (request.getRemoteAddr() != null) {
      LogUtil
          .debug(this.getClass(), "login", "登录管家：%s 登录IP:%s  登录时间:%s", keeper.getUserName(),
              request.getRemoteAddr(),
              TimeUtils.getDateFormatString("yyyy-MM-dd hh:mm:ss", new Date()));
    }
    response.setMsg(keeper);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.keeper.login.success"));
    response.setToken(TokenUtil.getJWTString(keeper.getId().toString(), ""));
    return response;
  }

  /**
   * 获取验证码
   * 
   * @param keeperAccountRequest
   * @return
   */
  @RequestMapping(value = "/getVerificationCode", method = RequestMethod.POST)
  @ApiOperation(value = "获取验证码", httpMethod = "POST", response = BaseResponse.class,
      notes = "验证手机号并发送验证码")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:获取成功; 0003,0006,1000:获取失败]")})
  public @ResponseBody ResponseOne<Map<String, String>> getVerificationCode(
      @ApiParam(name = "请求参数(json)", value = "{cellPhoneNum:手机号,type:验证码类型}", required = true)
      @RequestBody KeeperAccountRequest keeperAccountRequest) {
    ResponseOne<Map<String, String>> response = new ResponseOne<>();
    String cellPhoneNum = keeperAccountRequest.getCellPhoneNum();
    String type = keeperAccountRequest.getType();
    Map<String, String> map = new HashMap<>();
    map.put("type", type);
    if (StringUtils.isEmpty(cellPhoneNum)) {
      response.setCode(CommonAttributes.MISSING_REQUIRE_PARAM);
      response.setDesc(message("yxkj.request.param.missing"));
      return response;
    }
    try{
      containerKeeperService.findByCellPhoneNum(cellPhoneNum);
    }catch (Exception e) {
      response.setCode(CommonAttributes.ERROR_ACCOUNT);
      response.setDesc(message("yxkj.keeper.user.undefined"));
      LogUtil.debug(this.getClass(), "getVerificationCode", "用户不存在");
      return response;
    }
    String code;
    try {
      code = SmsUtil.getRandNum(6);
      SmsUtil.sendMessage(cellPhoneNum, message("yxkj.keeper.sms", code));
      redisTemplate.opsForValue().set(type+"_"+cellPhoneNum, code, setting.getSmsCodeTimeOut(), TimeUnit.SECONDS);
    } catch (Exception e) {
      e.printStackTrace();
      response.setCode(CommonAttributes.FAIL_SMSTOKEN);
      response.setDesc(message("yxkj.keeper.getVerificationCode.fail"));
      LogUtil.debug(this.getClass(), "getVerificationCode", "获取验证码失败");
      return response;
    }
    map.put("code", code);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.keeper.getVerificationCode.success"));
    response.setMsg(map);
    LogUtil.debug(this.getClass(), "getVerificationCode", "获取验证码成功");
    return response;
  }

  /**
   * 管家验证码登录
   * 
   * @param keeperRequest
   * @param request
   * @return
   */
  @RequestMapping(value = "/loginByVft", method = RequestMethod.POST)
  @ApiOperation(value = "用户验证码登录", httpMethod = "POST", response = BaseResponse.class,
      notes = "验证码登录")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:登录成功; 0001,0006:登录失败]")})
  public @ResponseBody ResponseOne<ContainerKeeper> login(
      @ApiParam(name = "请求参数(json)", value = "{cellPhoneNum:手机号,verificationCode:验证码}",required = true)
      @RequestBody KeeperAccountRequest keeperAccountRequest, HttpServletRequest request) {
    ResponseOne<ContainerKeeper> response = new ResponseOne<ContainerKeeper>();

    String cellPhoneNum = keeperAccountRequest.getCellPhoneNum();
    String inputCode = keeperAccountRequest.getVerificationCode();
    String code = redisTemplate.opsForValue().get("login_"+cellPhoneNum);

    if (StringUtils.isEmpty(cellPhoneNum) || StringUtils.isEmpty(inputCode)) {
      response.setCode(CommonAttributes.MISSING_REQUIRE_PARAM);
      response.setDesc(message("yxkj.request.param.missing"));
      return response;
    }

    LogUtil.debug(this.getClass(), "login", "登录手机号:%s", cellPhoneNum);
    ContainerKeeper keeper = null;
    try{
      keeper = containerKeeperService.findByCellPhoneNum(cellPhoneNum);
    }catch (Exception e) {
      response.setCode(CommonAttributes.ERROR_ACCOUNT);
      response.setDesc(message("yxkj.keeper.user.undefined"));
      LogUtil.debug(this.getClass(), "login", "用户不存在");
      return response;
    }
    if (!keeper.getAccountStatus().equals(AccountStatus.ACTIVED)) {
      response.setCode(CommonAttributes.ERROR_ACCOUNT);
      response.setDesc(message("yxkj.keeper.accountStatus.invalid"));
      LogUtil.debug(this.getClass(), "login", "管家账号无效");
      return response;
    }

    if (code != null && code.equals(inputCode)) {
      if (request.getRemoteAddr() != null)
        LogUtil.debug(this.getClass(), "login", "登录IP:%s  登录时间:%s", request.getRemoteAddr(),
            TimeUtils.getDateFormatString("yyyy-MM-dd hh:mm:ss", new Date()));
      response.setMsg(keeper);
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.keeper.login.success"));
      response.setToken(TokenUtil.getJWTString(keeper.getId().toString(), ""));
      return response;
    } else {
      response.setCode(CommonAttributes.ERROR_VFT);
      response.setDesc(message("yxkj.keeper.verificationCode.error"));
      LogUtil.debug(this.getClass(), "login", "验证码错误");
      return response;
    }
  }

  /**
   * 修改密码
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
  @ApiOperation(value = "修改密码", httpMethod = "POST", response = BaseResponse.class,
      notes = "通过旧密码修改密码")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 0005,0006:操作失败]")})
  public @ResponseBody ResponseOne<ContainerKeeper> updatePwd(
      @ApiParam(name = "请求参数(json)", value = "{cellPhoneNum:手机号,oldPwd:旧密码,newPwd:新密码}",
          required = true) @RequestBody KeeperAccountRequest keeperAccountRequest) {
    ResponseOne<ContainerKeeper> response = new ResponseOne<ContainerKeeper>();
    PrivateKey privateKey;
    try {
      privateKey = RSAHelper.getPrivateKey(setting.getServerPrivateKey());
    } catch (Exception e) {
      response.setCode(CommonAttributes.FAIL_RESET_PWD);
      response.setDesc(message("yxkj.keeper.password.analysis.error"));
      LogUtil.debug(this.getClass(), "resetPassword", "密码解析错误");
      return response;
    }

    String cellPhoneNum = keeperAccountRequest.getCellPhoneNum();
    String newPwd = KeyGenerator.decrypt(keeperAccountRequest.getNewPwd(), privateKey);
    String oldPwd = KeyGenerator.decrypt(keeperAccountRequest.getOldPwd(), privateKey);

    if (StringUtils.isEmpty(cellPhoneNum) && StringUtils.isEmpty(newPwd)
        && StringUtils.isEmpty(oldPwd)) {
      response.setCode(CommonAttributes.MISSING_REQUIRE_PARAM);
      response.setDesc(message("yxkj.request.param.missing"));
      return response;
    }
    String pattern = "^[a-zA-Z0-9]{8,}&";
    Pattern p = Pattern.compile(pattern);
    Matcher matcher = p.matcher(newPwd);
    if(!matcher.matches()){
      response.setCode(CommonAttributes.ERROR_FORMAL);
      response.setDesc(message("yxkj.keeper.password.formal.error"));
      LogUtil.debug(this.getClass(), "resetPassword", "新密码格式错误");
      return response;
    }
    if (containerKeeperService.findByCellPhoneNum(cellPhoneNum).getLoginPwd()
        .equals(DigestUtils.md5Hex(oldPwd))) {
      try {
        containerKeeperService.resetPassword(cellPhoneNum, DigestUtils.md5Hex(newPwd));
        response.setCode(CommonAttributes.SUCCESS);
        response.setDesc(message("yxkj.keeper.password.reset.success"));
        LogUtil.debug(this.getClass(), "resetPassword", "重置密码成功");
        return response;
      } catch (Exception e) {
        response.setCode(CommonAttributes.FAIL_RESET_PWD);
        response.setDesc(message("yxkj.keeper.password.reset.fail"));
        LogUtil.debug(this.getClass(), "resetPassword", "重置密码失败");
        return response;
      }
    } else {
      response.setCode(CommonAttributes.ERROR_PASS);
      response.setDesc(message("yxkj.keeper.password.reset.error"));
      LogUtil.debug(this.getClass(), "resetPassword", "原密码错误");
      return response;
    }
  }

  /**
   * 忘记密码时验证用户身份
   * 
   * @param keeperRequest
   * @param request
   * @return
   */
  @RequestMapping(value = "/forgetPwd", method = RequestMethod.POST)
  @ApiOperation(value = "忘记密码身份验证", httpMethod = "POST", response = BaseResponse.class,
      notes = "短信验证码身份验证")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:验证成功; 0006,1000:操作失败]")})
  public @ResponseBody ResponseOne<ContainerKeeper> forgetPwd(
      @ApiParam(name = "请求参数(json)", value = "{cellPhoneNum:手机号,verificationCode:验证码", required = true)
      @RequestBody KeeperAccountRequest keeperAccountRequest) {
    ResponseOne<ContainerKeeper> response = new ResponseOne<ContainerKeeper>();

    String cellPhoneNum = keeperAccountRequest.getCellPhoneNum();
    String inputCode = keeperAccountRequest.getVerificationCode();
    String code = redisTemplate.opsForValue().get("resetPwd_"+cellPhoneNum);
    System.out.println(code);
    if (StringUtils.isEmpty(inputCode)) {
      response.setCode(CommonAttributes.MISSING_REQUIRE_PARAM);
      response.setDesc(message("yxkj.request.param.missing"));
      return response;
    }

    if (code != null && code.equals(inputCode)) {
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.keeper.verification.success"));
      LogUtil.debug(this.getClass(), "login", "验证成功");
      return response;
    } else {
      response.setCode(CommonAttributes.ERROR_VFT);
      response.setDesc(message("yxkj.keeper.verificationCode.error"));
      LogUtil.debug(this.getClass(), "login", "验证码错误");
      return response;
    }
  }

  /**
   * 重置密码
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
  @ApiOperation(value = "重置密码", httpMethod = "POST", response = BaseResponse.class, notes = "重置密码")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 0005:操作失败]")})
  public @ResponseBody ResponseOne<ContainerKeeper> resetPwd(
      @ApiParam(name = "请求参数(json)", value = "{cellPhoneNum:手机号,newPwd:新密码,verificationCode:验证码}", required = true)
      @RequestBody KeeperAccountRequest keeperAccountRequest) {
    ResponseOne<ContainerKeeper> response = new ResponseOne<ContainerKeeper>();
    PrivateKey privateKey;
    try {
      privateKey = RSAHelper.getPrivateKey(setting.getServerPrivateKey());
    } catch (Exception e) {
      response.setCode(CommonAttributes.FAIL_RESET_PWD);
      response.setDesc(message("yxkj.keeper.password.analysis.error"));
      LogUtil.debug(this.getClass(), "resetPassword", "密码解析错误");
      return response;
    }
    String cellPhoneNum = keeperAccountRequest.getCellPhoneNum();
    String newPwd = KeyGenerator.decrypt(keeperAccountRequest.getNewPwd(), privateKey);
    String pattern = "^[a-zA-Z0-9]{8,}&";
    Pattern p = Pattern.compile(pattern);
    Matcher matcher = p.matcher(newPwd);
    if(!matcher.matches()){
      response.setCode(CommonAttributes.ERROR_FORMAL);
      response.setDesc(message("yxkj.keeper.password.formal.error"));
      LogUtil.debug(this.getClass(), "resetPassword", "新密码格式错误");
      return response;
    }
    try {
      containerKeeperService.resetPassword(cellPhoneNum, DigestUtils.md5Hex(newPwd));
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.keeper.password.reset.success"));
      LogUtil.debug(this.getClass(), "resetPassword", "重置密码成功");
      return response;
    } catch (Exception e) {
      response.setCode(CommonAttributes.FAIL_RESET_PWD);
      response.setDesc(message("yxkj.keeper.password.reset.fail"));
      LogUtil.debug(this.getClass(), "resetPassword", "重置密码失败");
      return response;
    }
  }
}
