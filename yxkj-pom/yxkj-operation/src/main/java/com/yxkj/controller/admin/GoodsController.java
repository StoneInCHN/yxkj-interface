package com.yxkj.controller.admin;

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

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.Goods;
import com.yxkj.entity.GoodsCategory;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.bean.GoodsData;
import com.yxkj.json.admin.request.GoodsCateRequest;
import com.yxkj.json.admin.request.GoodsListRequest;
import com.yxkj.json.base.BaseListRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.GoodsCategoryService;
import com.yxkj.service.GoodsService;
import com.yxkj.utils.ExportHelper;
import com.yxkj.json.admin.request.GoodsRequest;
import com.yxkj.utils.FieldFilterUtils;

/**
 * Controller - 商品管理
 * @author luzhang
 *
 */
@Controller("goodsController")
@RequestMapping("/admin/goods")
@Api(value = "(货柜后台)商品管理页面", description = "商品管理页面")
public class GoodsController extends BaseController {   
	
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	
	@Resource(name = "goodsCategoryServiceImpl")
	private GoodsCategoryService goodsCategoryService;
	
	@Autowired
	private ExportHelper exportHelper;	
	
    @RequestMapping(value = "/getGoodsList", method = RequestMethod.POST)
    @ApiOperation(value = "商品列表", httpMethod = "POST", response = ResponseMultiple.class, notes = "用于获取商品列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseMultiple<Map<String, Object>> getGoodsList(
    		@ApiParam(name = "请求参数(json)", value = "userName:用户名; pageNumber:页码; pageSize:每页数量; sn:商品编号; name:商品名称", required = false) 
    		@RequestBody GoodsListRequest request) {
    	
      ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>(); 
      Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());      
      List<Filter> filters = pageable.getFilters();
      if (request.getGoodsData() != null) {
          if (StringUtils.isNotBlank(request.getGoodsData().getSn())) {
              filters.add(Filter.like("sn", "%"+request.getGoodsData().getSn().trim()+"%"));
          }
          if (StringUtils.isNotBlank(request.getGoodsData().getName())) {
              filters.add(Filter.like("name", "%"+request.getGoodsData().getName().trim()+"%"));
          }	
	  }
      List<Ordering> orderings = pageable.getOrderings();
      orderings.add(Ordering.desc("createDate"));

      Page<Goods> goodsPage = goodsService.findPage(pageable);      
      String[] propertys =
          {"id", "name", "spec", "sn", "category.cateName", "costPrice", "salePrice", "goodsPics"};
      List<Map<String, Object>> result =
          FieldFilterUtils.filterCollection(propertys, goodsPage.getContent());
      PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), 
    		  pageable.getPageSize(), (int)goodsPage.getTotal());
      response.setPage(pageInfo);
      response.setMsg(result);

      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
    
    @RequestMapping(value = "/addGoods", method = RequestMethod.POST)
    @ApiOperation(value = "添加商品", httpMethod = "POST", response = ResponseOne.class, notes = "用于添加商品")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse addGoods(
    		@ApiParam (name = "请求参数(json)", value = "userName:用户名; goodsData:商品数据", required = true)
    		@RequestBody GoodsRequest request) {
        BaseResponse response = new BaseResponse(); 
        GoodsData goodsData = request.getGoodsData();
        if (goodsData != null ) {      
        	if (goodsService.exists(Filter.eq("sn", goodsData.getSn()))) {
                response.setCode(CommonAttributes.FAIL_COMMON);
                response.setDesc(message("yxkj.goods.sn.exist"));
                return response;
			}
      	    Goods goods = goodsService.getGoodsEntity(goodsData, null);
      	    goodsService.save(goods);
            response.setCode(CommonAttributes.SUCCESS);
            response.setDesc(message("yxkj.request.success"));
  	  	}
        return response;
    }
    @RequestMapping(value = "/updateGoods", method = RequestMethod.POST)
    @ApiOperation(value = "更新商品", httpMethod = "POST", response = ResponseOne.class, notes = "用于更新商品")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse updateGoods(
    		@ApiParam(name = "请求参数(json)", value = "userName:用户名; goodsData:商品数据", required = true)
    		@RequestBody GoodsRequest request) {
        BaseResponse response = new BaseResponse(); 
        GoodsData goodsData = request.getGoodsData();
        if (goodsData != null && request.getId() != null) {
        	if (goodsService.exists(Filter.eq("sn", goodsData.getSn()),Filter.ne("id", request.getId()))) {
                response.setCode(CommonAttributes.FAIL_COMMON);
                response.setDesc(message("yxkj.goods.sn.exist"));
                return response;
			}
      	  Goods goods = goodsService.getGoodsEntity(goodsData, request.getId());
      	  goodsService.update(goods);
          response.setCode(CommonAttributes.SUCCESS);
          response.setDesc(message("yxkj.request.success"));
  	    }
        return response;
    }
    
    @RequestMapping(value = "/deleteGoods", method = RequestMethod.POST)
    @ApiOperation(value = "删除商品", httpMethod = "POST", response = ResponseOne.class, notes = "用于删除商品")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse deleteGoods(
    		@ApiParam(name = "请求参数(json)", value = "userName:用户名; ids:商品ID数组", required = true) 
    		@RequestBody BaseRequest request) {
      BaseResponse response = new BaseResponse(); 
      if (request.getIds() != null && request.getIds().length > 0) {
    	  goodsService.delete(request.getIds());
          response.setCode(CommonAttributes.SUCCESS);
          response.setDesc(message("yxkj.request.success"));
	  }else {
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.failed"));
	  }
      return response;
    } 
    /**
     * 导出商品Excel
     * @throws IOException 
     */
    @RequestMapping(value = "/dataExport", method = {RequestMethod.GET, RequestMethod.POST})
    public void dataExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
      List<Ordering> orders = new ArrayList<Ordering>();
      orders.add(Ordering.desc("createDate"));
      List<Filter> filters = new ArrayList<Filter>();
	  String sn = request.getParameter("sn");
	  String name = request.getParameter("name");
      if (StringUtils.isNotBlank(sn)) {
          filters.add(Filter.like("sn", "%"+sn.trim()+"%"));
      }
      if (StringUtils.isNotBlank(name)) {
          filters.add(Filter.like("name", "%"+name.trim()+"%"));
      }		
      List<Goods> lists = goodsService.findList(null, filters, orders); 
      
      String title = "Goods List"; // 工作簿标题，同时也是excel文件名前缀
      String[] headers = {"sn", "name", "fullName", "spec", "costPrice", "salePrice", "status", "smallUrl", "largeUrl"}; // 需要导出的字段
      String[] headersName = {"商品条码", "商品名称", "商品全名", "商品规格", "成本价", "销售价", "商品状态", "商品图片(小图)连接", "商品图片(大图)连接"}; // 字段对应列的列名
      List<Map<String, String>> mapList = null;
      if (lists != null && lists.size() > 0) {
    	  mapList = exportHelper.prepareExportGoods(lists);
          exportListToExcel(response, mapList, title, headers, headersName);
      }else {
    	  mapList = new ArrayList<Map<String, String>>();
          exportListToExcel(response, mapList, title, headers, headersName);
	  }
    }
    @RequestMapping(value = "/isExistSn", method = RequestMethod.POST)
    @ApiOperation(value = "查询商品编码是否存在", httpMethod = "POST", response = ResponseOne.class, notes = "查询商品编码是否存在")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse isExistSn(
    		@ApiParam(name = "请求参数(json)", value = "userName:用户名; sn:商品编码; id:编辑时必填", required = true)
    		@RequestBody GoodsRequest request) {
        BaseResponse response = new BaseResponse(); 
        GoodsData goodsData = request.getGoodsData();
        if (goodsData == null || StringUtils.isBlank(goodsData.getSn())) {
            response.setCode(CommonAttributes.FAIL_LOGIN);
            response.setDesc(message("yxkj.request.param.missing"));
            return response;
		}
        boolean exist = false;
        if (goodsData != null && StringUtils.isNotBlank(goodsData.getSn())) {
        	if (request.getId() != null) {
				exist = goodsService.exists(Filter.eq("sn", goodsData.getSn()),Filter.ne("id", request.getId()));
			}else {
				exist = goodsService.exists(Filter.eq("sn", goodsData.getSn()));
			}
  	    }
        response.setDesc(exist +"");
        response.setCode(CommonAttributes.SUCCESS);
        return response;
    }
    
    @RequestMapping(value = "/goodsCateList", method = RequestMethod.POST)
    @ApiOperation(value = "商品分类列表", httpMethod = "POST", response = ResponseMultiple.class, notes = "用于商品分类列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})	
	public @ResponseBody ResponseMultiple<Map<String, Object>> goodsCateList(
			@ApiParam(name = "请求参数(json)", value = "userName:用户名 ; pageNumber:页码; pageSize:每页数量;", required = true) 
			@RequestBody BaseListRequest request) {
    	ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    	
        Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());   
        
        List<Filter> filters = pageable.getFilters();
        filters.add(Filter.eq("isActive", true));
        
        List<Ordering> orderings = pageable.getOrderings();
        orderings.add(Ordering.desc("createDate"));
        
        Page<GoodsCategory> goodsCatePage = goodsCategoryService.findPage(pageable);
        
        String[] propertys = {"id", "cateName", "catePicUrl"};
        List<Map<String, Object>> result =
            FieldFilterUtils.filterCollection(propertys, goodsCatePage.getContent());
        
        PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), 
      		  pageable.getPageSize(), (int)goodsCatePage.getTotal());
        response.setPage(pageInfo);
        response.setMsg(result);
        response.setCode(CommonAttributes.SUCCESS);
    	return response;
	}
    @RequestMapping(value = "/goodsCate", method = RequestMethod.POST)
    @ApiOperation(value = "新增或编辑商品分类", httpMethod = "POST", response = ResponseOne.class, notes = "用于新增或编辑商品分类")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse goodsCate(
    		@ApiParam(name = "请求参数(json)", value = "userName:用户名; id:商品分类ID; cateName:商品分类名称", required = true)  
    		@RequestBody GoodsCateRequest request) {
      BaseResponse response = new BaseResponse(); 
      GoodsCategory goodsCategory = null;
      if (request.getId() != null && request.getId() > 0) {//编辑
    	  goodsCategory = goodsCategoryService.find(request.getId());
    	  if (goodsCategory != null) {
        	  goodsCategory.setIsActive(true);
        	  goodsCategory.setCateName(request.getCateName());
        	  //goodsCategory.setCategPicUrl(request.getCatePicUrl());
        	  goodsCategoryService.update(goodsCategory);
		  }else {
		      response.setDesc(message("yxkj.request.failed"));
		      response.setCode(CommonAttributes.FAIL_COMMON);
		      return response;
		  }
	  }else {//新增
		  goodsCategory = new GoodsCategory();
		  goodsCategory.setIsActive(true);
		  goodsCategory.setCateName(request.getCateName());
		  //goodsCategory.setCategPicUrl(request.getCatePicUrl());
		  goodsCategoryService.save(goodsCategory);
	  }
      response.setDesc(message("yxkj.request.success"));
      response.setCode(CommonAttributes.SUCCESS);
      return response;
    } 
    
    @RequestMapping(value = "/deleteGoodsCate", method = RequestMethod.POST)
    @ApiOperation(value = "删除商品分类", httpMethod = "POST", response = ResponseOne.class, notes = "用于删除商品分类")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse deleteGoodsCate(
    		@ApiParam(name = "请求参数(json)", value = "userName:用户名; ids:商品分类ID数组", required = true) 
    		@RequestBody BaseRequest request) {
      BaseResponse response = new BaseResponse(); 
      if (request.getIds() != null && request.getIds().length > 0) {
    	  try {
    		  goodsCategoryService.delete(request.getIds());
    	  } catch (Exception e) {
    		  if (e.getCause() instanceof ConstraintViolationException) {
    			  response.setCode(CommonAttributes.FAIL_COMMON);
    			  response.setDesc(message("yxkj.goodsCate.hasGoods.cannot.remove"));
			  }else {
	    		  response.setCode(CommonAttributes.FAIL_COMMON);              
	              response.setDesc(message("yxkj.request.failed"));
			  }
    		  return response;
    	  }    	  
          response.setCode(CommonAttributes.SUCCESS);
          response.setDesc(message("yxkj.request.success"));
	  }else {
          response.setCode(CommonAttributes.FAIL_COMMON);
          response.setDesc(message("yxkj.request.failed"));
	  }
      return response;
    } 

}
