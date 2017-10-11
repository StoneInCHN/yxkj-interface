package com.yxkj.controller.keeper;

import java.security.PrivateKey;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.ContainerKeeper;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.KeeperRequest;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.ContainerKeeperService;
import com.yxkj.utils.RSAHelper;
import com.yxkj.utils.RSAUtils;
import com.yxkj.utils.SettingUtils;
import com.yxkj.utils.TimeUtils;
import com.yxkj.utils.TokenUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller("keeperController")
@RequestMapping("/keeper")
public class KeeperController extends BaseController{

	@Resource(name="containerKeeperServiceImpl")
	private ContainerKeeperService containerKeeperService;
	
	@RequestMapping(value="/loginByPwd", method=RequestMethod.POST)
	@ApiOperation(value = "用户登录", httpMethod = "POST", response = BaseResponse.class, notes = "密码登录")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
	public @ResponseBody ResponseOne<ContainerKeeper> loginByPwd(@ApiParam @RequestBody KeeperRequest keeperRequest, 
    		HttpServletRequest request){
		ResponseOne<ContainerKeeper> response = new ResponseOne<ContainerKeeper>();
		String cellPhoneNum = keeperRequest.getCellPhoneNum();
		
		PrivateKey privateKey;
		try {
			privateKey = RSAHelper.getPrivateKey(SettingUtils.get().getServerPrivateKey());
		} catch (Exception e) {
			response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.kepper.userName.password.error"));
            LogUtil.debug(this.getClass(), "login", "密码解析错误");
            return response;
		}
		
		String password = RSAUtils.decrypt(privateKey, keeperRequest.getPassword());  	
		
		if (StringUtils.isEmpty(cellPhoneNum) || StringUtils.isEmpty(password)) {
			response.setCode(CommonAttributes.FAIL_LOGIN);
			response.setDesc(message("yxkj.request.param.missing"));
			return response;
		}
    	
        LogUtil.debug(this.getClass(), "login", "登录管家手机号:%s", cellPhoneNum);
        ContainerKeeper keeper = containerKeeperService.findByCellPhoneNum(cellPhoneNum);
          
        if (keeper == null) {
            response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.kepper.userName.password.error"));
            LogUtil.debug(this.getClass(), "login", "用户不存在");
            return response;
        }
        if (!keeper.getAccountStatus().equals(AccountStatus.ACTIVED)) {
            response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.kepper.accountStatus.invalid"));
            LogUtil.debug(this.getClass(), "login", "账号无效");
            return response;
        }
        if (!DigestUtils.md5Hex(password).equals(keeper.getLoginPwd())) {
            response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.keeper.userName.password.error"));
            LogUtil.debug(this.getClass(), "login", "密码错误");
            return response;
        }
        if (request.getRemoteAddr() != null) {
            LogUtil.debug(this.getClass(), "login", "登录IP:%s  登录时间:%s", request.getRemoteAddr(), 
            		TimeUtils.getDateFormatString("yyyy-MM-dd hh:mm:ss", new Date()));
        }
        response.setMsg(keeper);
        response.setCode(CommonAttributes.SUCCESS);            
        response.setDesc(message("yxkj.admin.login.success"));  
        response.setToken(TokenUtil.getJWTString(keeper.getUserName(), "", 1800000));
		return response;
	}
	
	@RequestMapping(value="/loginByVft", method=RequestMethod.POST)
	@ApiOperation(value = "用户登录", httpMethod = "POST", response = BaseResponse.class, notes = "验证码登录")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
	public @ResponseBody ResponseOne<ContainerKeeper> login(@ApiParam @RequestBody KeeperRequest keeperRequest, 
    		HttpServletRequest request){
		ResponseOne<ContainerKeeper> response = new ResponseOne<ContainerKeeper>();
    	
        String cellPhoneNum = keeperRequest.getCellPhoneNum();
        String verificationCode = keeperRequest.getVerificationCode();  	
        if (StringUtils.isEmpty(cellPhoneNum) || StringUtils.isEmpty(verificationCode)) {
            response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.request.param.missing"));
            return response;
        }
        LogUtil.debug(this.getClass(), "login", "登录手机号:%s", cellPhoneNum);        
        ContainerKeeper keeper = containerKeeperService.findByCellPhoneNum(cellPhoneNum);
          
        if (keeper == null) {
            response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.kepper.userName.password.error"));
            LogUtil.debug(this.getClass(), "login", "用户不存在");
            return response;
        }
        if (!keeper.getAccountStatus().equals(AccountStatus.ACTIVED)) {
            response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.kepper.accountStatus.invalid"));
            LogUtil.debug(this.getClass(), "login", "账号无效");
            return response;
        }
        if (request.getRemoteAddr() != null) {
            LogUtil.debug(this.getClass(), "login", "登录IP:%s  登录时间:%s", request.getRemoteAddr(), 
            		TimeUtils.getDateFormatString("yyyy-MM-dd hh:mm:ss", new Date()));
        }
        if(verificationCode.equals("3306")){
	        response.setMsg(keeper);
	        response.setCode(CommonAttributes.SUCCESS);            
	        response.setDesc(message("yxkj.admin.login.success"));  
	        response.setToken(TokenUtil.getJWTString(keeper.getUserName(), "", 1800000));
			return response;
        }else{
        	response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.kepper.verificationCode.error"));
            LogUtil.debug(this.getClass(), "login", "验证码错误");
            return response;
        }
	}
	
}
