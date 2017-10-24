package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.GoodsCategory;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.request.GoodsCateRequest;
import com.yxkj.json.base.BaseListRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.GoodsCategoryService;
import com.yxkj.service.GoodsService;
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
	
    @RequestMapping(value = "/goodsCateList", method = RequestMethod.POST)
    @ApiOperation(value = "商品分类列表", httpMethod = "POST", response = ResponseMultiple.class, notes = "用于商品分类列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})	
	public @ResponseBody ResponseMultiple<Map<String, Object>> goodsCateList(@ApiParam @RequestBody BaseListRequest request) {
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
    public @ResponseBody BaseResponse goodsCate(@ApiParam @RequestBody GoodsCateRequest request) {
      BaseResponse response = new BaseResponse(); 
      GoodsCategory goodsCategory = null;
      if (request.getId() != null) {//编辑
    	  goodsCategory = goodsCategoryService.find(request.getId());
    	  if (goodsCategory != null) {
        	  goodsCategory.setIsActive(true);
        	  goodsCategory.setCateName(request.getCateName());
        	  goodsCategory.setCategPicUrl(request.getCatePicUrl());
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
		  goodsCategory.setCategPicUrl(request.getCatePicUrl());
		  goodsCategoryService.save(goodsCategory);
	  }
      response.setDesc(message("yxkj.request.success"));
      response.setCode(CommonAttributes.SUCCESS);
      return response;
    } 
    
    @RequestMapping(value = "/deleteGoodsCate", method = RequestMethod.POST)
    @ApiOperation(value = "删除商品分类", httpMethod = "POST", response = ResponseOne.class, notes = "用于删除商品分类")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse deleteGoodsCate(@ApiParam @RequestBody BaseRequest request) {
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
