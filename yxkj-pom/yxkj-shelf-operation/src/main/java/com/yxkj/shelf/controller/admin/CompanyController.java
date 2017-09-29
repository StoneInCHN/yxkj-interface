package com.yxkj.shelf.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.entity.Company;
import com.yxkj.entity.CompanyShelf;
import com.yxkj.entity.ShelfCategory;
import com.yxkj.shelf.beans.CommonAttributes;
import com.yxkj.shelf.controller.base.BaseController;
import com.yxkj.shelf.json.base.BaseRequest;
import com.yxkj.shelf.json.base.BaseResponse;
import com.yxkj.shelf.json.base.PageResponse;
import com.yxkj.shelf.json.base.ResponseMultiple;


/**
 * Controller - 公司管理
 * 
 */
@Controller("companyController")
@RequestMapping("/company")
@Api(value = "(货架后台)公司管理页面", description = "公司管理页面")
public class CompanyController extends BaseController {
	
    @RequestMapping(value = "/getCompanyList", method = RequestMethod.POST)
    @ApiOperation(value = "公司列表", httpMethod = "POST", response = BaseResponse.class, notes = "用于获取公司列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseMultiple<Company> getCompanyList(
            @ApiParam @RequestBody BaseRequest request) {

      ResponseMultiple<Company> response = new ResponseMultiple<Company>();
      
      //测试数据 start
      List<Company> companyList = new ArrayList<Company>();
      Company company = new Company();      
      company.setId(1L);
      company.setDisplayName("XXX公司");
      company.setContactPerson("sss");
      company.setContactPhone("185234534123");
      company.setSn("000000001");
      company.setAddress("香年广场11");
      Set<CompanyShelf> shelfs = new HashSet<CompanyShelf>();
      CompanyShelf shelf = new CompanyShelf();
      shelf.setCount(2);      
      ShelfCategory shelfCate = new ShelfCategory();
      shelfCate.setHeight("1.5m");
      shelf.setShelfCate(shelfCate);
      shelfs.add(shelf);
      company.setGoodsShelves(shelfs);
      companyList.add(company);
      //测试数据 end
      
      response.setMsg(companyList);
      
      PageResponse pageInfo = new PageResponse();
      pageInfo.setPageNumber(1);
      pageInfo.setPageSize(10);
      pageInfo.setTotal(10);
      response.setPage(pageInfo);

      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
}
