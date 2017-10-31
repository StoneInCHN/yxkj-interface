package com.yxkj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yxkj.beans.CommonAttributes;
import com.yxkj.beans.Setting.CaptchaType;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.Admin;
import com.yxkj.entity.MenuAuthority;
import com.yxkj.entity.PropertyKeeper;
import com.yxkj.entity.Role;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;
import com.yxkj.framework.filter.Filter;
import com.yxkj.json.admin.request.LoginRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.AdminService;
import com.yxkj.service.CaptchaService;
import com.yxkj.service.CompanyService;
import com.yxkj.service.FileService;
import com.yxkj.service.PropertyKeeperService;
import com.yxkj.utils.KeyGenerator;
import com.yxkj.utils.RSAHelper;
import com.yxkj.utils.TimeUtils;
import com.yxkj.utils.TokenUtil;


/**
 * Controller - 公共
 * 
 * @author luzhang
 * 
 */
@Controller("commonController")
@RequestMapping("/common")
@Api(value = "(货架后台)公共", description = "公共")
public class CommonController extends BaseController {

  @Resource(name = "adminServiceImpl")
  private AdminService adminService;

  @Resource(name = "companyServiceImpl")
  private CompanyService companyService;

  @Resource(name = "fileServiceImpl")
  private FileService fileService;
  
  @Resource(name = "captchaServiceImpl")
  private CaptchaService captchaService;
  
  @Resource(name = "propertyKeeperServiceImpl")
  private PropertyKeeperService propertyKeeperService;

  @RequestMapping(value = "/getRsa", method = RequestMethod.POST)
  @ApiOperation(value = "获取rsa密文，只供测试用", httpMethod = "POST", response = BaseResponse.class, notes = "获取rsa密文，只供测试用")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse getRsa(String str) throws Exception {
    BaseResponse response = new BaseResponse();
    String key = setting.getServerPublicKey();
    String rsaStr = KeyGenerator.encrypt(str, RSAHelper.getPublicKey(key));
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(rsaStr);
    return response;
  }
  
  @RequestMapping(value = "/rsa", method = RequestMethod.POST)
  @ApiOperation(value = "获取公匙", httpMethod = "POST", response = BaseResponse.class, notes = "获取公匙")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse rsa() {
    BaseResponse response = new BaseResponse();
    String key = setting.getServerPublicKey();
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(key);
    return response;
  }
  
  /**
   * 验证码
   */
  @RequestMapping(value = "/captcha", method = RequestMethod.GET)
  public void image(String captchaId, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    if (StringUtils.isEmpty(captchaId)) {
      captchaId = request.getSession().getId();
    }
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0);
    response.setContentType("image/jpeg");

    ServletOutputStream servletOutputStream = null;
    try {
      servletOutputStream = response.getOutputStream();
      BufferedImage bufferedImage = captchaService.buildImage(captchaId);
      ImageIO.write(bufferedImage, "jpg", servletOutputStream);
      servletOutputStream.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(servletOutputStream);
    }
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ApiOperation(value = "用户登录", httpMethod = "POST", response = BaseResponse.class, notes = "用户登录")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Admin> login(
      @ApiParam(name = "请求参数(json)", value = "userName:用户名; password:密码(rsa密文)",
          required = true) @RequestBody LoginRequest loginRequest,
      HttpServletRequest request) {

    ResponseOne<Admin> response = new ResponseOne<Admin>();

    String serverPrivateKey = setting.getServerPrivateKey();
    String userName = loginRequest.getUserName();
    String password = loginRequest.getPassword();
    String captcha = loginRequest.getCaptcha();
    String captchaId = loginRequest.getCaptchaId();
    boolean autoLogin = loginRequest.isAutoLogin();
    if (StringUtils.isEmpty(userName)) {
      response.setCode(CommonAttributes.FAIL_LOGIN);
      response.setDesc(message("yxkj.admin.userName.empty"));
      return response;
    }
    try {
      password = KeyGenerator.decrypt(password, RSAHelper.getPrivateKey(serverPrivateKey));
    } catch (Exception e) {
      LogUtil.debug(this.getClass(), "login", "登录密码，RSA解密错误：%s", e.getMessage());
      response.setCode(CommonAttributes.FAIL_LOGIN);
      response.setDesc(message("yxkj.request.failed"));
      return response;
    }
    if (StringUtils.isEmpty(password)) {
      response.setCode(CommonAttributes.FAIL_LOGIN);
      response.setDesc(message("yxkj.admin.password.empty"));
      return response;
    }
    if (StringUtils.isEmpty(captcha) || StringUtils.isEmpty(captchaId)) {
      response.setCode(CommonAttributes.FAIL_LOGIN);
      response.setDesc(message("yxkj.admin.captcha.empty"));
      return response;
    }
    if (!autoLogin && !captchaService.isValid(CaptchaType.adminLogin, captchaId, captcha)) {
      response.setCode(CommonAttributes.FAIL_LOGIN);
      response.setDesc(message("yxkj.admin.userName.captcha.error"));
      LogUtil.debug(this.getClass(), "login", "验证码错误");
      return response;
    }
    LogUtil.debug(this.getClass(), "login", "登录名:%s", userName);
    Admin admin = adminService.findByUserName(userName);

    if (admin == null) {
        //下个版本，物业有自己的登录平台后，这块代码需要移除 start
  		List<Filter> filters = new ArrayList<Filter>();
  		filters.add(Filter.eq("cellPhoneNum", userName));
      	PropertyKeeper propertyKeeper = propertyKeeperService.findFirst(filters, null);
      	if (propertyKeeper != null && DigestUtils.md5Hex(password).equals(propertyKeeper.getLoginPwd())) {
  			Admin keeper = new Admin();
  			keeper.setUserName(userName);
  			keeper.setAdminStatus(AccountStatus.ACTIVED);
  			keeper.setIsSystem(false);
  		    if (request.getRemoteAddr() != null) {
  		    	keeper.setLoginIp(request.getRemoteAddr());
  		    	keeper.setLoginDate(new Date());
  		    }    
  		    /** 菜单权限列表 */
  		    List<MenuAuthority> authorities = new ArrayList<MenuAuthority>();
  		    MenuAuthority propertyKeeperMenu =
  		        new MenuAuthority("物业平台", "/propertyKeeper", "speedometer", "PropertyKeeper", null, null, null);
  		    authorities.add(propertyKeeperMenu);
  		    /** 角色 */
  		    Role role = new Role();
  		    role.setName("物业");
  		    role.setDescription("物业管理员");
  		    role.setIsSystem(true);
  		    role.setAuthorities(authorities);
  		    keeper.getRoles().add(role);
  		    response.setMsg(keeper);
  		    response.setCode(CommonAttributes.SUCCESS);
  		    response.setDesc(message("yxkj.admin.login.success"));
  		    // JWT根据用户名生成token
  		    response.setToken(TokenUtil.getJWTString(userName, ""));
  		    return response;
  		}   	
        //下个版本，物业有自己的登录平台后，这块代码需要移除 end
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
    response.setMsg(admin);
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(message("yxkj.admin.login.success"));
    // JWT根据用户名生成token
    response.setToken(TokenUtil.getJWTString(userName, ""));

    return response;
  }

  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  @ApiOperation(value = "用户注销", httpMethod = "POST", response = BaseResponse.class, notes = "用户注销")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse logout(@ApiParam @RequestBody BaseRequest req) {
    BaseResponse response = new BaseResponse();
    // Long userId = req.getUserId();
    // String token = req.getToken();
    // 删除token
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

}
