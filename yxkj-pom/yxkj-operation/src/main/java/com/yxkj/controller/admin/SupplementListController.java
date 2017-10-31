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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.SupplementList;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.bean.SupplementData;
import com.yxkj.json.admin.request.SupplementRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.service.SupplementListService;
import com.yxkj.utils.ExportHelper;
import com.yxkj.utils.FieldFilterUtils;
import com.yxkj.utils.TimeUtils;

/**
 * Controller - 待补货清单
 * @author luzhang
 *
 */
@Controller("supplementListController")
@RequestMapping("/admin/supplementList")
@Api(value = "(货柜后台)待补货清单", description = "待补货清单页面")
public class SupplementListController extends BaseController {   
	
	@Resource(name = "supplementListServiceImpl")
	private SupplementListService supplementListService;
	
	@Autowired
	private ExportHelper exportHelper;	
	
    @RequestMapping(value = "/listPage", method = RequestMethod.POST)
    @ApiOperation(value = ")待补货清单列表", httpMethod = "POST", response = ResponseMultiple.class, notes = ")待补货清单列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})	
	public @ResponseBody BaseResponse update(
			@ApiParam(name = "请求参数(json)", value = "userName:用户名; supplementData:查询字段", required = true) 
			@RequestBody SupplementRequest request) {
        ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>(); 
        Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());      
        List<Filter> filters = pageable.getFilters();
        if (request.getSupplementData() != null) {
        	SupplementData supplementData = request.getSupplementData();
            if (StringUtils.isNotBlank(supplementData.getGoodsSn())) {
                filters.add(Filter.like("goodsSn", "%"+supplementData.getGoodsSn().trim()+"%"));
            }
            if (StringUtils.isNotBlank(supplementData.getGoodName())) {
                filters.add(Filter.like("goodsName", "%"+supplementData.getGoodName().trim()+"%"));
            }	
            if (supplementData.getSceneId() != null) {
            	filters.add(Filter.eq("sceneId", supplementData.getSceneId()));
			}
            if (supplementData.getStartDate() != null) {
                filters.add(Filter.ge("createDate", TimeUtils.formatDate2Day0(supplementData.getStartDate())));
            }
            if (supplementData.getEndDate() != null) {
                filters.add(Filter.lt("createDate", TimeUtils.formatDate2Day59(supplementData.getEndDate())));
            }   
  	  	}
        Page<SupplementList> page = supplementListService.findPage(pageable);      
        String[] propertys =
            {"id", "sceneSn", "sceneName", "cntrSn", "channel.sn", "goodsSn", "goodsName", "remainCount", "waitSupplyCount", "suppName", "suppMobile"};
        List<Map<String, Object>> result =
            FieldFilterUtils.filterCollection(propertys, page.getContent());
        PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), 
      		  pageable.getPageSize(), (int)page.getTotal());
        response.setPage(pageInfo);
        response.setMsg(result);

        response.setCode(CommonAttributes.SUCCESS);
        return response;
	}
    /**
     * 导出Excel
     * @throws IOException 
     */
    @RequestMapping(value = "/dataExport", method = {RequestMethod.GET, RequestMethod.POST})
    public void dataExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
      List<Filter> filters = new ArrayList<Filter>();
	  String goodsSn = request.getParameter("goodsSn");
	  String goodsName = request.getParameter("goodsName");
	  String sceneId = request.getParameter("sceneId");
	  String startTime = request.getParameter("startTime");
	  String endTime = request.getParameter("endTime");
	  
      if (StringUtils.isNotBlank(goodsSn)) {
          filters.add(Filter.like("goodsSn", "%"+goodsSn.trim()+"%"));
      }
      if (StringUtils.isNotBlank(goodsName)) {
          filters.add(Filter.like("goodsName", "%"+goodsName.trim()+"%"));
      }	
      if (StringUtils.isNotBlank(sceneId)) {
      	filters.add(Filter.eq("sceneId", Long.parseLong(sceneId)));
	  }
      if (StringUtils.isNotBlank(startTime)) {    	  
          filters.add(Filter.ge("createDate", TimeUtils.formatDate2Day0(startTime)));
      }
      if (StringUtils.isNotBlank(endTime)) {
          filters.add(Filter.lt("createDate", TimeUtils.formatDate2Day59(endTime)));
      }
	
      List<SupplementList> lists = supplementListService.findList(null, filters, null); 
      
      String title = "Supplement List"; // 工作簿标题，同时也是excel文件名前缀
      String[] headers = {"sceneSn", "sceneName", "cntrSn", "channelSn", "goodsSn", "goodsName", "remainCount", "waitSupplyCount", "suppPerson"}; // 需要导出的字段
      String[] headersName = {"优享空间编号","优享空间地址","补货货柜","补货货道","商品条码（包含规格）","剩余数量","待补货数量","补货人"}; // 字段对应列的列名
      List<Map<String, String>> mapList = null;
      if (lists != null && lists.size() > 0) {
          mapList = exportHelper.prepareExportSupplementList(lists);
          exportListToExcel(response, mapList, title, headers, headersName);
      }else {
    	  mapList = new ArrayList<Map<String, String>>();
    	  exportListToExcel(response, mapList, title, headers, headersName);
	  }
    }
    
}
