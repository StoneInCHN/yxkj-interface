package com.yxkj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
import com.yxkj.entity.ShelfOrder;
import com.yxkj.entity.commonenum.CommonEnum.AccountStatus;
import com.yxkj.entity.commonenum.CommonEnum.ImageType;
import com.yxkj.entity.commonenum.CommonEnum.ShelfOrderStatus;
import com.yxkj.beans.CommonAttributes;
import com.yxkj.beans.Setting.CaptchaType;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.BaseController;
import com.yxkj.framework.filter.Filter;
import com.yxkj.json.admin.request.LoginRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.AdminService;
import com.yxkj.service.CaptchaService;
import com.yxkj.service.CompanyService;
import com.yxkj.service.FileService;
import com.yxkj.service.GoodsService;
import com.yxkj.service.ShelfOrderService;
import com.yxkj.service.TouristService;
import com.yxkj.utils.ExportHelper;
import com.yxkj.utils.FieldFilterUtils;
import com.yxkj.utils.ImportExcel;
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

  @Resource(name = "goodsServiceImpl")
  private GoodsService goodsService;

  @Resource(name = "fileServiceImpl")
  private FileService fileService;

  @Resource(name = "taskExecutor")
  private Executor threadPoolExecutor;

  @Resource(name = "touristServiceImpl")
  private TouristService touristService;

  @Resource(name = "shelfOrderServiceImpl")
  private ShelfOrderService shelfOrderService;
  
  @Resource(name = "captchaServiceImpl")
  private CaptchaService captchaService;

  @Autowired
  private ExportHelper exportHelper;
  
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
  public @ResponseBody ResponseOne<Admin> login(@ApiParam @RequestBody LoginRequest loginRequest,
      HttpServletRequest request) {
    ResponseOne<Admin> response = new ResponseOne<Admin>();

    String userName = loginRequest.getUserName();
    String password = loginRequest.getPassword();
    String captcha = loginRequest.getCaptcha();   
    String captchaId = loginRequest.getCaptchaId();
    boolean autoLogin = loginRequest.isAutoLogin();
    if (StringUtils.isEmpty(userName)) {
      response.setCode(CommonAttributes.FAIL_LOGIN);
      response.setDesc("请输入用户名");
      return response;
    }
    if (StringUtils.isEmpty(password)) {
      response.setCode(CommonAttributes.FAIL_LOGIN);
      response.setDesc("请输入密码");
      return response;
    }
    if (StringUtils.isEmpty(captcha) || StringUtils.isEmpty(captchaId)) {
      response.setCode(CommonAttributes.FAIL_LOGIN);
      response.setDesc("请输入验证码");
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
   * Excel数据导入
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/dataImport", method = {RequestMethod.GET, RequestMethod.POST})
  public @ResponseBody BaseResponse dataImport(HttpServletRequest request) {
    BaseResponse response = new BaseResponse();
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    String desc = "商品导入结束。导入情况：";
    String errorSn = "失败商品条码：";
    int errCount = 0;
    for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
      MultipartFile excelFile = entity.getValue();
      ImportExcel importData = new ImportExcel();
      try {
        List<Map<String, Object>> rowMaps = importData.readExcelToMapList(excelFile);
        desc += "共计" + rowMaps.size() + "个";
        for (Map<String, Object> rowMap : rowMaps) {
          if (isValidGoodsRow(rowMap)) {
            Goods goods = importData.constructGoods(rowMap);
            goodsService.save(goods);
          } else {
            errCount++;
            errorSn += rowMap.get("sn") + " ";
          }
        }
        if (errCount > 0) {
          desc += "，成功" + (rowMaps.size() - errCount) + "个，失败" + errCount + "个。" + errorSn;
        } else {
          desc += "，成功" + rowMaps.size() + "个，失败0个。";
        }
      } catch (IOException e) {
        response.setCode(CommonAttributes.FAIL_COMMON);
        return response;
      }
      break;
    }
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(desc);
    return response;
  }


  /**
   * 首页数据统计
   * 
   * @param req
   * @return
   */
  @RequestMapping(value = "/hp/statistics", method = RequestMethod.POST)
  @ApiOperation(value = "首页数据统计", httpMethod = "POST", response = ResponseOne.class,
      notes = "首页数据统计")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseOne<Map<String, Object>> hpStatistics(
      @ApiParam @RequestBody BaseRequest req) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    Map<String, Object> resMap = new HashMap<String, Object>();
    resMap.put("userCount", touristService.count());// 总用户数
    resMap.put("orderCount", 0);// 总订单数
    resMap.put("saleIncome", 0);// 总销售收入
    resMap.put("saleCost", 0);// 总销售成本
    resMap.put("profitRate", 0);// 毛利率
    resMap.put("avgUnitPrice", 0);// 平均客单价
  
    response.setMsg(resMap);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
  
  private boolean isValidGoodsRow(Map<String, Object> rowMap) {
	if (rowMap.get("sn") != null && StringUtils.isNotBlank(rowMap.get("sn").toString())) {
		if (goodsService.exists(Filter.eq("sn", rowMap.get("sn").toString()))) {
			rowMap.put("sn", rowMap.get("sn")+"(商品条码已存在)");
			return false;
		}
		if (rowMap.get("name") == null || StringUtils.isBlank(rowMap.get("name").toString())) {
			rowMap.put("sn", rowMap.get("sn")+"(商品名称缺失)");
			return false;
		}
		if (rowMap.get("spec") == null || StringUtils.isBlank(rowMap.get("spec").toString())) {
			rowMap.put("sn", rowMap.get("sn")+"(净含量缺失)");
			return false;
		}
		if (rowMap.get("costPrice") == null || StringUtils.isBlank(rowMap.get("costPrice").toString())) {
			rowMap.put("sn", rowMap.get("sn")+"(成本价缺失)");
			return false;
		}
		if (rowMap.get("salePrice") == null || StringUtils.isBlank(rowMap.get("salePrice").toString())) {
			rowMap.put("sn", rowMap.get("sn")+"(售价价缺失)");
			return false;
		}
		if (rowMap.get("name") != null && rowMap.get("spec") != null
		        && rowMap.get("costPrice") != null && rowMap.get("salePrice") != null 
		        && StringUtils.isNotBlank(rowMap.get("name").toString()) && StringUtils.isNotBlank(rowMap.get("spec").toString())
		        && StringUtils.isNotBlank(rowMap.get("costPrice").toString()) && StringUtils.isNotBlank(rowMap.get("salePrice").toString())) {
			return true;
		}
	}
    return false;
  }
}
