package com.yxkj.shelf.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yxkj.entity.Admin;
import com.yxkj.entity.Company;
import com.yxkj.entity.Goods;
import com.yxkj.entity.MenuAuthority;
import com.yxkj.entity.Role;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;
import com.yxkj.entity.commonenum.CommonEnum.ImageType;
import com.yxkj.shelf.beans.CommonAttributes;
import com.yxkj.shelf.common.log.LogUtil;
import com.yxkj.shelf.controller.base.BaseController;
import com.yxkj.shelf.json.admin.request.AdminRequest;
import com.yxkj.shelf.json.admin.request.CompanyGoods;
import com.yxkj.shelf.json.base.BaseRequest;
import com.yxkj.shelf.json.base.BaseResponse;
import com.yxkj.shelf.json.base.ResponseOne;
import com.yxkj.shelf.service.AdminService;
import com.yxkj.shelf.service.CompanyService;
import com.yxkj.shelf.service.FileService;
import com.yxkj.shelf.service.GoodsService;
import com.yxkj.shelf.utils.FieldFilterUtils;
import com.yxkj.shelf.utils.GeneratePdf;
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
	
	@Resource(name = "companyServiceImpl")
	private CompanyService companyService;
	
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	
	@Resource(name = "fileServiceImpl")
	private FileService fileService;
	
	@Resource(name = "taskExecutor")
	private Executor threadPoolExecutor;
	
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
        LogUtil.debug(this.getClass(), "login", "登录名:%s", userName);        
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
        MenuAuthority companyAdd = new MenuAuthority("公司新增", "/companyAdd", null, "company/CompanyAdd", null, true, null);
        MenuAuthority companyEdit = new MenuAuthority("编辑公司", "/companyEdit/:id", null, "company/CompanyEdit", null, true, null);     
        MenuAuthority companyGoodsQr = new MenuAuthority("商品二维码", "/companyGoodsQr/:id", null, "company/CompanyGoodsQr", null, true, null); 
        MenuAuthority goods = new MenuAuthority("商品管理", "/goodsList", "android-playstore", "goods/GoodsList", null, null, null);
        MenuAuthority goodsAdd = new MenuAuthority("商品新增", "/goodsAdd", null, "goods/GoodsAdd", null, true, null);
        MenuAuthority goodsEdit = new MenuAuthority("编辑商品", "/goodsEdit/:id", null, "goods/GoodsEdit", null, true, null);
        MenuAuthority endUser = new MenuAuthority("用户管理", "/touristList", "android-contacts", "user/TouristList", null, null, null);
        authorities.add(home);
        authorities.add(order);
        authorities.add(orderDetail);
        authorities.add(company);
        authorities.add(companyAdd);
        authorities.add(companyEdit);
        authorities.add(companyGoodsQr);
        authorities.add(goods);
        authorities.add(goodsAdd);
        authorities.add(goodsEdit);
        authorities.add(endUser);
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
    @RequestMapping(value = "/uploadImg", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody BaseResponse uploadImg(HttpServletRequest request) {
      BaseResponse response = new BaseResponse();
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile mf = entity.getValue();
          String displayPath = fileService.saveImage(mf, ImageType.GOODS_IMG);
          response.setDesc(displayPath); 
          break;
      }
      response.setCode(CommonAttributes.SUCCESS);            
      return response;
    }
    /**
     * 下载商品二维码pdf
     * 
     */
    @RequestMapping(value = "/downloadQrPdf", method = {RequestMethod.GET, RequestMethod.POST})
    public void downloadQrPdf(CompanyGoods companyGoods, HttpServletResponse response, HttpSession session) {
      Company company = companyService.find(companyGoods.getCompanyId());
      if (company == null) {
        LogUtil.debug(this.getClass(), "downloadQrPdf", "company is null");
        return;
      }
      if (companyGoods.getSelectKeys() == null && companyGoods.getSelectKeys().size() > 0) {
          LogUtil.debug(this.getClass(), "downloadQrPdf", "SelectKeys is null");
          return;
      }
      List<Goods> goods = new ArrayList<Goods>();
      List<Long> selectKeys = companyGoods.getSelectKeys();
      for (int i = 0; i < selectKeys.size(); i++) {
    	  goods.add(goodsService.find(selectKeys.get(i)));
	  }      
      String[] propertys = {"id", "sn", "name", "spec"};
      List<Map<String, Object>> goodsList = FieldFilterUtils.filterCollection(propertys, goods);

      try {
        response.setContentType("octets/stream");
        String filename = TimeUtils.getDateFormatString("yyyyMMddHHmmss", new Date());
        response.addHeader("Content-Disposition", "attachment;filename=" + filename + ".pdf");

        OutputStream out = response.getOutputStream();// 获得输出流        
        GeneratePdf generatePdf = new GeneratePdf(
        		company.getDisplayName(),company.getId().toString(),goodsList,out,fileService.getProjectDeployUrl());        
        Object locker = new Object();// 当前主线程的一把锁
        synchronized (locker) {
          threadPoolExecutor.execute(// 加入到线程池中执行
              new Runnable() {
                public void run() {
                	generatePdf.generatePdf();
                  synchronized (locker) {
                    locker.notify();
                  }
                }
              });
          locker.wait();// 主线程等待
        }

        out.flush();
        out.close();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
}
