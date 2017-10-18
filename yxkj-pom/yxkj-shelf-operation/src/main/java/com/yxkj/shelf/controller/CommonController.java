package com.yxkj.shelf.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.yxkj.shelf.service.ShelfOrderService;
import com.yxkj.shelf.service.TouristService;
import com.yxkj.shelf.utils.ExportHelper;
import com.yxkj.shelf.utils.FieldFilterUtils;
import com.yxkj.shelf.utils.GeneratePdf;
import com.yxkj.shelf.utils.ImportExcel;
import com.yxkj.shelf.utils.TimeUtils;
import com.yxkj.shelf.utils.TokenUtil;


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

  @Autowired
  private ExportHelper exportHelper;

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

    // 因为货架后台没有管理角色权限的功能，暂时写死 start
    /** 菜单权限列表 */
    Set<MenuAuthority> authorities = new HashSet<MenuAuthority>();
    MenuAuthority home =
        new MenuAuthority("首页", "/dashboard", "speedometer", "Dashboard", null, null, null);
    MenuAuthority order =
        new MenuAuthority("订单管理", "/orderList", "clipboard", "order/OrderList", null, null, null);
    MenuAuthority orderDetail =
        new MenuAuthority("订单详情", "/orderDetail/:id", null, "order/OrderDetail", null, true, null);
    MenuAuthority company =
        new MenuAuthority("公司管理", "/companyList", "ios-photos-outline", "company/CompanyList",
            null, null, null);
    MenuAuthority companyAdd =
        new MenuAuthority("公司新增", "/companyAdd", null, "company/CompanyAdd", null, true, null);
    MenuAuthority companyEdit =
        new MenuAuthority("编辑公司", "/companyEdit/:id", null, "company/CompanyEdit", null, true, null);
    MenuAuthority companyGoodsQr =
        new MenuAuthority("商品二维码", "/companyGoodsQr/:id", null, "company/CompanyGoodsQr", null,
            true, null);
    MenuAuthority goods =
        new MenuAuthority("商品管理", "/goodsList", "android-playstore", "goods/GoodsList", null, null,
            null);
    MenuAuthority goodsAdd =
        new MenuAuthority("商品新增", "/goodsAdd", null, "goods/GoodsAdd", null, true, null);
    MenuAuthority goodsEdit =
        new MenuAuthority("编辑商品", "/goodsEdit/:id", null, "goods/GoodsEdit", null, true, null);
    MenuAuthority endUser =
        new MenuAuthority("用户管理", "/touristList", "android-contacts", "user/TouristList", null,
            null, null);
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
    // 因为货架后台没有管理角色权限的功能，暂时写死 end

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
   * 下载商品二维码pdf
   * 
   */
  @RequestMapping(value = "/downloadQrPdf", method = {RequestMethod.GET, RequestMethod.POST})
  public void downloadQrPdf(CompanyGoods companyGoods, HttpServletResponse response,
      HttpSession session) {
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
      GeneratePdf generatePdf =
          new GeneratePdf(company.getDisplayName(), company.getId().toString(), goodsList, out,
              fileService.getQrCodePrefixUrl());
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

  // /**
  // * 导出Excel
  // * @throws IOException
  // */
  // @RequestMapping(value = "/dataExport", method = {RequestMethod.GET, RequestMethod.POST})
  // public void dataExport(HttpServletRequest request, HttpServletResponse response) throws
  // IOException {
  // List<Ordering> orders = new ArrayList<Ordering>();
  // orders.add(Ordering.desc("createDate"));
  // List<Filter> filters = new ArrayList<Filter>();
  // // String requestParam = HttpServletRequestUtils.getRequestParam(request, "UTF-8");
  // // String nickName = getReqPram(requestParam, "nickName");
  // // String companyName = getReqPram(requestParam, "companyName");
  // String nickName = request.getParameter("nickName");
  // String companyName = request.getParameter("companyName");
  // if (nickName != null && StringUtils.isNotBlank(nickName)) {
  // filters.add(Filter.like("nickName", "%"+nickName.trim()+"%"));
  // }
  // if (companyName != null && StringUtils.isNotBlank(companyName)) {
  // filters.add(Filter.like("companyName", "%"+companyName.trim()+"%"));
  // }
  // List<Tourist> lists = touristService.findList(null, filters, orders);
  // if (lists != null && lists.size() > 0) {
  // String title = "User List"; // 工作簿标题，同时也是excel文件名前缀
  // String[] headers = {"id", "userName", "cellPhoneNum", "gender", "nickName", "userChannel",
  // "regTime", "companyName"}; // 需要导出的字段
  // String[] headersName = {"用户ID", "用户识别码", "手机号", "性别", "账号昵称", "用户获取渠道", "注册时间", "所属公司"}; //
  // 字段对应列的列名
  // List<Map<String, String>> mapList = exportHelper.prepareExportTourist(lists);
  // if (mapList.size() > 0) {
  // exportListToExcel(response, mapList, title, headers, headersName);
  // }
  // }
  // }
  private boolean isValidGoodsRow(Map<String, Object> rowMap) {
    if (rowMap.get("sn") != null && rowMap.get("name") != null && rowMap.get("spec") != null
        && rowMap.get("costPrice") != null && rowMap.get("salePrice") != null) {
      return true;
    }
    return false;
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
    List<ShelfOrder> shelfOrders = shelfOrderService.getShelfOrderByStatus(ShelfOrderStatus.PAID);
    if (!CollectionUtils.isEmpty(shelfOrders)) {
      resMap.put("orderCount", shelfOrders.size());
      BigDecimal saleIncome = new BigDecimal(0);
      BigDecimal profit = new BigDecimal(0);
      for (ShelfOrder shelfOrder : shelfOrders) {
        saleIncome = saleIncome.add(shelfOrder.getAmount());
        profit = profit.add(shelfOrder.getProfit());
      }
      resMap.put("saleIncome", saleIncome);
      resMap.put("saleCost", saleIncome.subtract(profit));
      resMap.put("profitRate", profit.divide(saleIncome, 4, BigDecimal.ROUND_HALF_UP));
      resMap.put("avgUnitPrice",
          saleIncome.divide(new BigDecimal(shelfOrders.size()), 2, BigDecimal.ROUND_HALF_UP));
    }
    response.setMsg(resMap);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

}
