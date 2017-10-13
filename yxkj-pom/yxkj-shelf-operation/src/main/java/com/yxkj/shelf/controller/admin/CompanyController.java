package com.yxkj.shelf.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
import com.yxkj.shelf.json.admin.request.ShelfOrderData;
import com.yxkj.shelf.json.admin.request.ShelfOrderRequest;
import com.yxkj.shelf.json.admin.response.GoodsProfile;
import com.yxkj.shelf.json.base.BaseResponse;
import com.yxkj.shelf.json.base.PageResponse;
import com.yxkj.shelf.json.base.ResponseMultiple;
import com.yxkj.shelf.json.base.ResponseOne;
import com.yxkj.shelf.service.AreaService;
import com.yxkj.shelf.service.CompanyService;
import com.yxkj.shelf.service.GoodsService;
import com.yxkj.shelf.service.ShelfCategoryService;


/**
 * Controller - 公司管理
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
              filters.add(Filter.like("sn", "%"+companyData.getSn()+"%"));
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
    	  companyService.delete(request.getIds());
          response.setCode(CommonAttributes.SUCCESS);
          response.setDesc(message("yxkj.request.success"));
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
    	  Company company = companyService.getCompnayEntity(companyData, request.getId());
    	  companyService.update(company);
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

      List<Area> list = areaService.findList(null, filters, null);
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
}
