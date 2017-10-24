package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.Admin;
import com.yxkj.json.admin.request.ChangePwdRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.service.AdminService;

/**
 * Controller - 用户管理
 * @author luzhang
 *
 */
@Controller("amdinController")
@RequestMapping("/admin/admin")
@Api(value = "(货柜后台)管理员页面", description = "管理员页面")
public class AdminController extends BaseController {   
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
    @RequestMapping(value = "/changePwd", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码", httpMethod = "POST", response = ResponseMultiple.class, notes = "用于修改密码")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})	
	public @ResponseBody BaseResponse update(@ApiParam @RequestBody ChangePwdRequest request) {
    	BaseResponse response = new BaseResponse();
        String userName = request.getUserName();
        String oldEnPwd = DigestUtils.md5Hex(request.getOldPwd());
        String newEnPwd = DigestUtils.md5Hex(request.getNewPwd());
        Admin admin = adminService.findByUserName(userName);
        if (admin == null) {
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.admin.null"));
          return response;
        }
        if (!oldEnPwd.equals(admin.getPassword())) {
            response.setCode(CommonAttributes.FAIL_COMMON);
            response.setDesc(message("yxkj.oldPwd.error"));
            return response;
        }    	
    	admin.setPassword(newEnPwd);
    	adminService.update(admin);
    	
    	response.setCode(CommonAttributes.SUCCESS);
    	response.setDesc(message("yxkj.request.success"));
    	return response;
	}
}
