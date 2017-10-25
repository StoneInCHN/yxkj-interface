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

import com.yxkj.entity.Goods;
import com.yxkj.shelf.beans.CommonAttributes;
import com.yxkj.shelf.controller.base.BaseController;
import com.yxkj.shelf.framework.filter.Filter;
import com.yxkj.shelf.framework.ordering.Ordering;
import com.yxkj.shelf.framework.paging.Page;
import com.yxkj.shelf.framework.paging.Pageable;
import com.yxkj.shelf.json.admin.request.AdminRequest;
import com.yxkj.shelf.json.admin.request.GoodsData;
import com.yxkj.shelf.json.admin.request.GoodsRequest;
import com.yxkj.shelf.json.base.BaseResponse;
import com.yxkj.shelf.json.base.PageResponse;
import com.yxkj.shelf.json.base.ResponseMultiple;
import com.yxkj.shelf.json.base.ResponseOne;
import com.yxkj.shelf.service.GoodsService;
import com.yxkj.shelf.utils.ExportHelper;
import com.yxkj.shelf.utils.FieldFilterUtils;


/**
 * Controller - 商品管理
 * @author luzhang
 * 
 */
@Controller("goodsController")
@RequestMapping("/admin/goods")
@Api(value = "(货架后台)商品管理页面", description = "商品管理页面")
public class GoodsController extends BaseController {   
	
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;	
	
	@Autowired
	private ExportHelper exportHelper;		
	
	
    @RequestMapping(value = "/getGoodsList", method = RequestMethod.POST)
    @ApiOperation(value = "商品列表", httpMethod = "POST", response = ResponseMultiple.class, notes = "用于获取商品列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseMultiple<Map<String, Object>> getGoodsList(
    		@ApiParam @RequestBody GoodsRequest request) {
    	
      ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>(); 
      Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());      
      List<Filter> filters = pageable.getFilters();
      GoodsData goodsData = request.getGoodsData();
      if (goodsData != null) {
          if (StringUtils.isNotBlank(goodsData.getSn())) {
              filters.add(Filter.like("sn", "%"+goodsData.getSn().trim()+"%"));
          }
          if (StringUtils.isNotBlank(goodsData.getName())) {
              filters.add(Filter.like("name", "%"+goodsData.getName().trim()+"%"));
          }		
	  }
      List<Ordering> orderings = pageable.getOrderings();
      orderings.add(Ordering.desc("createDate"));
      orderings.add(Ordering.desc("sn"));

      Page<Goods> goodsPage = goodsService.findPage(pageable);      
      String[] propertys =
          {"id", "name", "spec", "sn", "costPrice", "salePrice", "goodsPics"};
      List<Map<String, Object>> result =
          FieldFilterUtils.filterCollection(propertys, goodsPage.getContent());
      PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), 
    		  pageable.getPageSize(), (int)goodsPage.getTotal());
      response.setPage(pageInfo);
      response.setMsg(result);

      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
    @RequestMapping(value = "/getGoodsData", method = RequestMethod.POST)
    @ApiOperation(value = "商品详情", httpMethod = "POST", response = ResponseOne.class, notes = "用于获取商品详情")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseOne<GoodsData> getGoodsData(
    		@ApiParam @RequestBody AdminRequest request) {
    	
      ResponseOne<GoodsData> response = new ResponseOne<GoodsData>(); 
      Long goodsId = request.getId();  	
      if (goodsId == null || goodsId <= 0) {
          response.setCode(CommonAttributes.FAIL_LOGIN);
          response.setDesc(message("yxkj.request.param.missing"));
          return response;
      }      
      Goods goods = goodsService.find(goodsId);
      GoodsData goodsData = goodsService.getGoodsData(goods);
      response.setMsg(goodsData);
      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
    @RequestMapping(value = "/deleteGoods", method = RequestMethod.POST)
    @ApiOperation(value = "删除商品", httpMethod = "POST", response = ResponseOne.class, notes = "用于删除商品")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse deleteGoods(@ApiParam @RequestBody AdminRequest request) {
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
    @RequestMapping(value = "/addGoods", method = RequestMethod.POST)
    @ApiOperation(value = "添加商品", httpMethod = "POST", response = ResponseOne.class, notes = "用于添加商品")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody BaseResponse addGoods(@ApiParam @RequestBody GoodsRequest request) {
        BaseResponse response = new BaseResponse(); 
        GoodsData goodsData = request.getGoodsData();
        if (goodsData != null ) {      
        	if (goodsService.exists(Filter.eq("sn", goodsData.getSn()))) {
                response.setCode(CommonAttributes.FAIL_COMMON);
                response.setDesc("商品条码已存在");
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
    public @ResponseBody BaseResponse updateGoods(@ApiParam @RequestBody GoodsRequest request) {
        BaseResponse response = new BaseResponse(); 
        GoodsData goodsData = request.getGoodsData();
        if (goodsData != null && request.getId() != null) {
        	if (goodsService.exists(Filter.eq("sn", goodsData.getSn()),Filter.ne("id", request.getId()))) {
                response.setCode(CommonAttributes.FAIL_COMMON);
                response.setDesc("商品条码已存在");
                return response;
			}
      	  Goods goods = goodsService.getGoodsEntity(goodsData, request.getId());
      	  goodsService.update(goods);
          response.setCode(CommonAttributes.SUCCESS);
          response.setDesc(message("yxkj.request.success"));
  	    }
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
//	  String name = getReqPram(requestParam, "name");
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
      String[] headers = {"sn", "name", "fullName", "spec", "costPrice", "salePrice", "status", "goodsUrl"}; // 需要导出的字段
      String[] headersName = {"商品条码", "商品名称", "商品全名", "商品规格", "成本价", "销售价", "商品状态", "商品图片连接"}; // 字段对应列的列名
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
    public @ResponseBody BaseResponse isExistSn(@ApiParam @RequestBody GoodsRequest request) {
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
}
