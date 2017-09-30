package com.yxkj.shelf.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.entity.Admin;
import com.yxkj.entity.MenuAuthority;
import com.yxkj.entity.Role;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;
import com.yxkj.shelf.beans.CommonAttributes;
import com.yxkj.shelf.common.log.LogUtil;
import com.yxkj.shelf.controller.base.BaseController;
import com.yxkj.shelf.json.admin.request.AdminRequest;
import com.yxkj.shelf.json.base.BaseRequest;
import com.yxkj.shelf.json.base.BaseResponse;
import com.yxkj.shelf.json.base.ResponseOne;
import com.yxkj.shelf.service.AdminService;
import com.yxkj.shelf.utils.TimeUtils;
import com.yxkj.shelf.utils.TokenUtil;


/**
 * Controller - 公共
 * 
 */
@Controller("commonController")
@RequestMapping("/common")
@Api(value = "(货架后台)公共", description = "公共")
public class CommonController extends BaseController {
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	  
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", httpMethod = "POST", response = BaseResponse.class, notes = "用户登录")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<Admin> login(@ApiParam @RequestBody AdminRequest adminRequest, 
    		HttpServletRequest request) {
    	ResponseOne<Admin> response = new ResponseOne<Admin>();
    	
        String userName = adminRequest.getUserName();
        String password = adminRequest.getPassword();  	
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.request.param.missing"));
            return response;
        }
        LogUtil.debug(this.getClass(), "login", "登录名:%s  登录密码:%s", userName, password);        
        Admin admin = adminService.findByUserName(userName);
          
        if (admin == null) {
            response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.admin.userName.password.error"));
            LogUtil.debug(this.getClass(), "login", "用户名或密码错误");
            return response;
        }
        if (!admin.getAdminStatus().equals(AccountStatus.ACTIVED)) {
            response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.admin.accountStatus.invalid"));
            LogUtil.debug(this.getClass(), "login", "账号无效");
            return response;
        }
        if (!DigestUtils.md5Hex(password).equals(admin.getPassword())) {
            response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.admin.userName.password.error"));
            LogUtil.debug(this.getClass(), "login", "密码错误");
            return response;
        }
        if (request.getRemoteAddr() != null) {
            admin.setLoginIp(request.getRemoteAddr());
            admin.setLoginDate(new Date());
            LogUtil.debug(this.getClass(), "login", "登录IP:%s  登录时间:%s", admin.getLoginIp(), 
            		TimeUtils.getDateFormatString("yyyy-MM-dd hh:mm:ss", admin.getLoginDate()));
        }
        adminService.update(admin);
        
        //因为货架后台没有管理角色权限的功能，暂时写死 start
        /** 菜单权限列表 */
        Set<MenuAuthority> authorities = new HashSet<MenuAuthority>();
        MenuAuthority home = new MenuAuthority("首页", "/dashboard", "speedometer", "Dashboard", null, null, null);
        MenuAuthority order = new MenuAuthority("订单管理", "/orderList", "clipboard", "order/OrderList", null, null, null);
        MenuAuthority orderDetail = new MenuAuthority("订单详情", "/orderDetail/:id", null, "order/OrderDetail", null, true, null);
        MenuAuthority company = new MenuAuthority("公司管理", "/companyList", "ios-photos-outline", "company/CompanyList", null, null, null);
        MenuAuthority goods = new MenuAuthority("商品管理", "/goodsList", "android-playstore", "order/OrderList", null, null, null);
        MenuAuthority user = new MenuAuthority("用户管理", "/userList", "android-contacts", "order/OrderList", null, null, null);
        authorities.add(home);
        authorities.add(order);
        authorities.add(orderDetail);
        authorities.add(company);
        authorities.add(goods);
        authorities.add(user);
        /** 角色 */
        Role role = new Role();
        role.setName("admin");
        role.setDescription("超级管理员");
        role.setIsSystem(true);
        role.setAuthorities(authorities);
        admin.getRoles().add(role);
        //因为货架后台没有管理角色权限的功能，暂时写死 end
        
        response.setMsg(admin);        
        response.setCode(CommonAttributes.SUCCESS);            
        response.setDesc(message("yxkj.admin.login.success"));     
        //JWT根据用户名生成token
        response.setToken(TokenUtil.getJWTString(userName, ""));            
        
        return response;
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "用户注销", httpMethod = "POST", response = BaseResponse.class, notes = "用户注销")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse logout(@ApiParam @RequestBody BaseRequest req) {
      BaseResponse response = new BaseResponse();
//      Long userId = req.getUserId();
//      String token = req.getToken();
      //删除token
      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
    
}
