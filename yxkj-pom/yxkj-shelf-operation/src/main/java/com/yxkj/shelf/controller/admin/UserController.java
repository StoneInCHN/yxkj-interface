package com.yxkj.shelf.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.entity.Tourist;
import com.yxkj.entity.commonenum.CommonEnum.DeviceType;
import com.yxkj.shelf.beans.CommonAttributes;
import com.yxkj.shelf.controller.base.BaseController;
import com.yxkj.shelf.framework.filter.Filter;
import com.yxkj.shelf.framework.ordering.Ordering;
import com.yxkj.shelf.framework.paging.Page;
import com.yxkj.shelf.framework.paging.Pageable;
import com.yxkj.shelf.json.admin.request.TouristData;
import com.yxkj.shelf.json.admin.request.TouristRequest;
import com.yxkj.shelf.json.base.PageResponse;
import com.yxkj.shelf.json.base.ResponseMultiple;
import com.yxkj.shelf.json.base.ResponseOne;
import com.yxkj.shelf.service.TouristService;
import com.yxkj.shelf.utils.ExportHelper;
import com.yxkj.shelf.utils.FieldFilterUtils;
import com.yxkj.shelf.utils.HttpServletRequestUtils;

/**
 * Controller - 用户管理
 * @author luzhang
 *
 */
@Controller("userController")
@RequestMapping("/admin/user")
@Api(value = "(货架后台)用户管理页面", description = "用户管理页面")
public class UserController extends BaseController {   
	
	@Resource(name = "touristServiceImpl")
	private TouristService touristService;
	
	@Autowired
	private ExportHelper exportHelper;
	
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    @ApiOperation(value = "用户列表", httpMethod = "POST", response = ResponseMultiple.class, notes = "用于获取用户列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseMultiple<Map<String, Object>> getUserList(
    		@ApiParam @RequestBody TouristRequest request) {
    	
      ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>(); 
      Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());      
      List<Filter> filters = pageable.getFilters();
      List<Ordering> orderings = pageable.getOrderings();
      orderings.add(Ordering.desc("id"));
      TouristData touristData = request.getTouristData();
      if (touristData != null) {
          if (StringUtils.isNotBlank(touristData.getNickName())) {
              filters.add(Filter.like("nickName", "%"+touristData.getNickName()+"%"));
          }
          if (StringUtils.isNotBlank(touristData.getCompanyName())) {
              filters.add(Filter.like("companyName", "%"+touristData.getCompanyName()+"%"));
          }		
	  }    
      filters.add(Filter.eq("deviceType", DeviceType.SHELF));
      
      Page<Tourist> goodsPage = touristService.findPage(pageable);      
      String[] propertys =
          {"id", "userName", "cellPhoneNum", "gender", "nickName", 
    		  "userChannel", "companyName","regTime"};
      List<Map<String, Object>> result =
          FieldFilterUtils.filterCollection(propertys, goodsPage.getContent());
      PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), 
    		  pageable.getPageSize(), (int)goodsPage.getTotal());
      response.setPage(pageInfo);
      response.setMsg(result);

      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
    @RequestMapping(value = "/getUserDetail", method = RequestMethod.POST)
    @ApiOperation(value = "用户详情", httpMethod = "POST", response = ResponseOne.class, notes = "用于获取用户详情")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<Tourist> getUserDetail(
    		@ApiParam @RequestBody TouristRequest request) {
    	
      ResponseOne<Tourist> response = new ResponseOne<Tourist>(); 
      Long userId = request.getId();  	
      if (userId == null || userId <= 0) {
          response.setCode(CommonAttributes.FAIL_LOGIN);
          response.setDesc(message("yxkj.request.param.missing"));
          return response;
      }      
      Tourist user = touristService.find(userId);
      
      response.setMsg(user);
      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
    /**
     * 导出Excel
     * @throws IOException 
     */
    @RequestMapping(value = "/dataExport", method = {RequestMethod.GET, RequestMethod.POST})
    public void dataExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
      List<Ordering> orders = new ArrayList<Ordering>();
      orders.add(Ordering.desc("createDate"));
      List<Filter> filters = new ArrayList<Filter>();
      //request.setCharacterEncoding("UTF-8");
	  String requestParam = HttpServletRequestUtils.getRequestParam(request, "UTF-8");
	  String nickName = getReqPram(requestParam, "nickName");
	  String companyName = getReqPram(requestParam, "companyName");
      if (nickName != null) {
          filters.add(Filter.like("nickName", "%"+nickName+"%"));
      }
      if (companyName != null) {
          filters.add(Filter.like("companyName", "%"+companyName+"%"));
      }		
      List<Tourist> lists = touristService.findList(null, filters, orders); 
      if (lists != null && lists.size() > 0) {
        String title = "User List"; // 工作簿标题，同时也是excel文件名前缀
        String[] headers = {"id", "userName", "cellPhoneNum", "gender", "nickName", "userChannel", "regTime", "companyName"}; // 需要导出的字段
        String[] headersName = {"用户ID", "用户识别码", "手机号", "性别", "账号昵称", "用户获取渠道", "注册时间", "所属公司"}; // 字段对应列的列名
        List<Map<String, String>> mapList = exportHelper.prepareExportTourist(lists);
        if (mapList.size() > 0) {
          exportListToExcel(response, mapList, title, headers, headersName);
        }
      }
    }

}
