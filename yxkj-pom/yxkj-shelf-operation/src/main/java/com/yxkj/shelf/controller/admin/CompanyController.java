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
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.entity.Area;
import com.yxkj.entity.Company;
import com.yxkj.shelf.beans.CommonAttributes;
import com.yxkj.shelf.controller.base.BaseController;
import com.yxkj.shelf.framework.filter.Filter;
import com.yxkj.shelf.framework.ordering.Ordering;
import com.yxkj.shelf.framework.paging.Page;
import com.yxkj.shelf.framework.paging.Pageable;
import com.yxkj.shelf.json.admin.request.AdminRequest;
import com.yxkj.shelf.json.admin.request.CompanyData;
import com.yxkj.shelf.json.admin.request.CompanyRequest;
import com.yxkj.shelf.json.admin.request.GoodsShelveRow;
import com.yxkj.shelf.json.admin.response.GoodsProfile;
import com.yxkj.shelf.json.base.BaseResponse;
import com.yxkj.shelf.json.base.PageResponse;
import com.yxkj.shelf.json.base.ResponseMultiple;
import com.yxkj.shelf.json.base.ResponseOne;
import com.yxkj.shelf.service.AreaService;
import com.yxkj.shelf.service.CompanyService;
import com.yxkj.shelf.service.GoodsService;
import com.yxkj.shelf.service.ShelfCategoryService;
import com.yxkj.shelf.utils.ExportHelper;


/**
 * Controller - 公司管理
 * @author luzhang
 * 
 */
@Controller("companyController")
@RequestMapping("/admin/company")
@Api(value = "(货架后台)公司管理页面", description = "公司管理页面")
public class CompanyController extends BaseController {
	
	@Resource(name = "companyServiceImpl")
	private CompanyService companyService;
	
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	@Resource(name = "shelfCategoryServiceImpl")
	private ShelfCategoryService shelfCategoryService;	
	
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	
	@Autowired
	private ExportHelper exportHelper;
	
    @RequestMapping(value = "/getCompanyList", method = RequestMethod.POST)
    @ApiOperation(value = "公司列表", httpMethod = "POST", response = BaseResponse.class, notes = "用于获取公司列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseMultiple<Company> getCompanyList(
                @ApiParam @RequestBody CompanyRequest request) {    	
    	
      ResponseMultiple<Company> response = new ResponseMultiple<Company>(); 
      Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());      
      List<Filter> filters = pageable.getFilters();
      CompanyData companyData = request.getCompanyData();
      if (companyData != null) {
          if (StringUtils.isNotBlank(companyData.getSn())) {
              filters.add(Filter.like("sn", "%"+companyData.getSn().trim()+"%"));
          }
          if (StringUtils.isNotBlank(companyData.getDisplayName())) {
              filters.add(Filter.like("fullName", "%"+companyData.getDisplayName().trim()+"%"));
          }
	  }
      List<Ordering> orderings = pageable.getOrderings();
      orderings.add(Ordering.desc("createDate"));

      Page<Company> orderPage = companyService.findPage(pageable);      

      PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), 
    		  pageable.getPageSize(), (int)orderPage.getTotal());
      response.setPage(pageInfo);
      response.setMsg(orderPage.getContent());

      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }

    @RequestMapping(value = "/getCompany", method = RequestMethod.POST)
    @ApiOperation(value = "公司详情", httpMethod = "POST", response = ResponseOne.class, notes = "用于获取公司详情")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<Company> getCompany(
    		@ApiParam @RequestBody AdminRequest request) {
    	
    	ResponseOne<Company> response = new ResponseOne<Company>(); 
      Long orderId = request.getId();  	
      if (orderId == null || orderId <= 0) {
          response.setCode(CommonAttributes.FAIL_LOGIN);
          response.setDesc(message("yxkj.request.param.missing"));
          return response;
      }      
      Company Company = companyService.find(orderId);
      
      response.setMsg(Company);
      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
    
    @RequestMapping(value = "/getCompanyData", method = RequestMethod.POST)
    @ApiOperation(value = "公司详情数据", httpMethod = "POST", response = ResponseOne.class, notes = "用于获取公司详情数据")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<CompanyData> editCompany(
    		@ApiParam @RequestBody AdminRequest request) {
    	
      ResponseOne<CompanyData> response = new ResponseOne<CompanyData>(); 
      Long companyId = request.getId();  	
      if (companyId == null || companyId <= 0) {
          response.setCode(CommonAttributes.FAIL_LOGIN);
          response.setDesc(message("yxkj.request.param.missing"));
          return response;
      }      
      Company company = companyService.find(companyId);
      CompanyData companyData = companyService.getCompanyData(company);
      response.setMsg(companyData);
      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
    
    @RequestMapping(value = "/deleteCompany", method = RequestMethod.POST)
    @ApiOperation(value = "删除公司", httpMethod = "POST", response = ResponseOne.class, notes = "用于删除公司")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse deleteCompany(@ApiParam @RequestBody AdminRequest request) {
      BaseResponse response = new BaseResponse(); 
      if (request.getIds() != null && request.getIds().length > 0) {
    	  try {
    		  companyService.deleteCompany(request.getIds());
    	  } catch (Exception e) {
    		  if (e.getCause() instanceof ConstraintViolationException) {
    			  response.setCode(CommonAttributes.FAIL_COMMON);
    			  response.setDesc("不能删除有订单记录的公司！");
			  }else {
	    		  response.setCode(CommonAttributes.FAIL_COMMON);              
	              response.setDesc(message("yxkj.request.failed"));
			  }
    		  return response;
    	  }    	  
    	  //companyService.delete(request.getIds());
          response.setCode(CommonAttributes.SUCCESS);
          response.setDesc("删除成功！");
	  }else {
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.failed"));
	  }
      return response;
    }  
    @RequestMapping(value = "/addCompany", method = RequestMethod.POST)
    @ApiOperation(value = "新增公司", httpMethod = "POST", response = ResponseOne.class, notes = "用于新增公司")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse addCompany(@ApiParam @RequestBody CompanyRequest request) {
      BaseResponse response = new BaseResponse(); 
      CompanyData companyData = request.getCompanyData();
      if (companyData != null ) {
    	  Company company = companyService.getCompnayEntity(companyData, null);
    	  company.setSn(companyService.genComSn());
    	  companyService.save(company);
          response.setCode(CommonAttributes.SUCCESS);
          response.setDesc(message("yxkj.request.success"));
	  }
      return response;
    } 
    @RequestMapping(value = "/updateCompany", method = RequestMethod.POST)
    @ApiOperation(value = "更新公司", httpMethod = "POST", response = ResponseOne.class, notes = "用于更新公司")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse updateCompany(@ApiParam @RequestBody CompanyRequest request) {
      BaseResponse response = new BaseResponse(); 
      if (request.getCompanyData() != null && request.getId() != null) {
    	  CompanyData companyData = request.getCompanyData();
//    	  Company company = companyService.getCompnayEntity(companyData, request.getId());    	  
//    	  companyService.update(company);
    	  companyService.updateCompany(companyData, request.getId());  
          response.setCode(CommonAttributes.SUCCESS);
          response.setDesc(message("yxkj.request.success"));
	  }
      return response;
    }
    @RequestMapping(value = "/getAreaList", method = RequestMethod.POST)
    @ApiOperation(value = "获取地址列表", httpMethod = "POST", response = ResponseOne.class, notes = "用于获取地址列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseMultiple<Area> getAreaList(@ApiParam @RequestBody AdminRequest request) {
      ResponseMultiple<Area> response = new ResponseMultiple<Area>(); 
      List<Filter> filters = new ArrayList<Filter>();
      if (request.getId() == null) {// 查询顶级地区
        filters.add(Filter.isNull("parent"));
      } else {// 查询areaId下的子级地区
        filters.add(Filter.eq("parent", request.getId()));
      }
      List<Ordering> orderings = new ArrayList<Ordering>();
      orderings.add(Ordering.asc("pyName"));
      List<Area> list = areaService.findList(null, filters, orderings);
      response.setMsg(list);
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
      return response;
    }      
    @RequestMapping(value = "/getShelfList", method = RequestMethod.POST)
    @ApiOperation(value = "获取货架类型列表", httpMethod = "POST", response = ResponseOne.class, notes = "用于获取货架类型列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseMultiple<GoodsShelveRow> getShelfList(@ApiParam @RequestBody AdminRequest request) {
      ResponseMultiple<GoodsShelveRow> response = new ResponseMultiple<GoodsShelveRow>(); 
      List<GoodsShelveRow> rowList = companyService.getShelfList(request.getId());
      response.setMsg(rowList);
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
      return response;
    }
    @RequestMapping(value = "/getAllGoodsProfile", method = RequestMethod.POST)
    @ApiOperation(value = "获取商品简介列表", httpMethod = "POST", response = ResponseOne.class, notes = "用于获取商品简介列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseMultiple<GoodsProfile> getAllGoodsProfile(@ApiParam @RequestBody AdminRequest request) {
      ResponseMultiple<GoodsProfile> response = new ResponseMultiple<GoodsProfile>(); 
      List<GoodsProfile> list = goodsService.getAllGoodsProfile();
      response.setMsg(list);
      response.setCode(CommonAttributes.SUCCESS);
      response.setDesc(message("yxkj.request.success"));
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
//	  String requestParam = HttpServletRequestUtils.getRequestParam(request, "UTF-8");
//	  String sn = getReqPram(requestParam, "sn");
//	  String displayName = getReqPram(requestParam, "displayName");
	  String sn = request.getParameter("sn");
	  String displayName = request.getParameter("displayName");
      if (sn != null) {
          filters.add(Filter.like("sn", "%"+sn.trim()+"%"));
      }
      if (displayName != null) {
          filters.add(Filter.like("fullName", "%"+displayName.trim()+"%"));
      }		
      List<Company> lists = companyService.findList(null, filters, orders); 
      
      String title = "Company List"; // 工作簿标题，同时也是excel文件名前缀
      String[] headers = {"sn", "fullName", "displayName", "contactPerson", "contactPhone", "area", "address", "remark"}; // 需要导出的字段
      String[] headersName = {"公司编号", "公司全名", "公司展示名称", "联系人", "联系电话", "地区", "详细地址", "备注"}; // 字段对应列的列名
      List<Map<String, String>> mapList = null;
      if (lists != null && lists.size() > 0) {
          mapList = exportHelper.prepareExportCompany(lists);
          exportListToExcel(response, mapList, title, headers, headersName);
      }else {
    	  mapList = new ArrayList<Map<String, String>>();
    	  exportListToExcel(response, mapList, title, headers, headersName);
	  }
    }
    @RequestMapping(value = "/isExistCompany", method = RequestMethod.POST)
    @ApiOperation(value = "查询公司名称是否存在", httpMethod = "POST", response = ResponseOne.class, notes = "查询公司名称是否存在")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse isExistCompany(@ApiParam @RequestBody CompanyRequest request) {
        BaseResponse response = new BaseResponse(); 
        CompanyData companyData = request.getCompanyData();
        boolean existDispalyName = false;
        boolean existFullName = false;
        if (companyData != null) {
        	if (StringUtils.isNotBlank(companyData.getDisplayName())) {
        		existDispalyName = companyService.exists(Filter.eq("displayName", companyData.getDisplayName()));
			}
        	if (StringUtils.isNotBlank(companyData.getFullName())) {
        		existFullName = companyService.exists(Filter.eq("fullName", companyData.getFullName()));
			}       	
  	    }
        if (existDispalyName || existFullName) {
            response.setDesc("true");
            response.setCode(CommonAttributes.SUCCESS);
		  }else {
            response.setDesc("false");
            response.setCode(CommonAttributes.SUCCESS);
		  }
        return response;
    }
    
}
