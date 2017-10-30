package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.ContainerCategory;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.Goods;
import com.yxkj.entity.Scene;
import com.yxkj.entity.VendingContainer;
import com.yxkj.entity.commonenum.CommonEnum.ChannelGoodsStatus;
import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.bean.ContainerChannelData;
import com.yxkj.json.admin.request.ContainerCategoryRequest;
import com.yxkj.json.admin.request.ContainerChannelRequest;
import com.yxkj.json.admin.request.SearchListRequest;
import com.yxkj.json.admin.request.VendingContainerRequest;
import com.yxkj.json.base.BaseListRequest;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.PageResponse;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.ContainerCategoryService;
import com.yxkj.service.ContainerChannelService;
import com.yxkj.service.GoodsService;
import com.yxkj.service.SceneService;
import com.yxkj.service.VendingContainerService;
import com.yxkj.utils.FieldFilterUtils;

/**
 * 货道管理 - Controller
 * @author luzhang
 */
@Controller("containerChannelController")
@RequestMapping("/admin/containerChannel")
@Api(value = "(货柜后台)货道管理", description = "货道管理")
public class ContainerChannelController extends BaseController {

  @Resource(name = "containerChannelServiceImpl")
  private ContainerChannelService containerChannelService;
  
  @Resource(name = "vendingContainerServiceImpl")
  private VendingContainerService vendingContainerService;
  
  @Resource(name = "goodsServiceImpl")
  private GoodsService goodsService;
  
  
  /**
   * 单个货柜的货道列表
   * @param request
   * @return
   */
  @RequestMapping(value = "/containerChannelList", method = RequestMethod.POST)
  @ApiOperation(value = "单个货柜的货道列表", httpMethod = "POST", response = ResponseOne.class, notes = "单个货柜的货道列表")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> vendingContainerList(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; id:货柜Id", required = true)
  		@RequestBody BaseListRequest request) {
	  
        ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>(); 
        Pageable pageable = new Pageable(request.getPageNumber(), request.getPageSize());     
        
        List<Filter> filters = pageable.getFilters();
        filters.add(Filter.eq("cntr", vendingContainerService.find(request.getId())));	
        
        List<Ordering> orderings = pageable.getOrderings();
        orderings.add(Ordering.asc("sn"));
        
        Page<ContainerChannel> channelPage = containerChannelService.findPage(pageable);      
        String[] propertys = {"id", "cntr.sn", "goods.name", "goods.spec", "goods.salePrice","capacity", "warning", "chgsStatus"};
        List<Map<String, Object>> result =
            FieldFilterUtils.filterCollection(propertys, channelPage.getContent());
        
        PageResponse pageInfo = new PageResponse(pageable.getPageNumber(), 
      		  pageable.getPageSize(), (int)channelPage.getTotal());
        response.setPage(pageInfo);
        response.setMsg(result);

        response.setCode(CommonAttributes.SUCCESS);
      return response;
  }
  /**
   * 复制货道
   * @param request
   * @return
   */
  @RequestMapping(value = "/copyChannel", method = RequestMethod.POST)
  @ApiOperation(value = "复制货道", httpMethod = "POST", response = ResponseOne.class, notes = "复制货道")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse copyChannel(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; id:复制货道Id; ChannelData:货道数据", required = true)
  		@RequestBody ContainerChannelRequest request) {
	  BaseResponse response = new BaseResponse();
      	if (request.getId() != null && request.getChannelData() != null 
      			&& request.getChannelData().getSn() != null) {
      		ContainerChannel copyChannel = containerChannelService.find(request.getId());
      		if (copyChannel == null) {
    	        response.setCode(CommonAttributes.FAIL_COMMON);
    	        response.setDesc(message("yxkj.request.failed"));
			}
      		ContainerChannel newChannel = new ContainerChannel();
      		newChannel.setSn(request.getChannelData().getSn());
      		newChannel.setGoods(copyChannel.getGoods());
      		newChannel.setCapacity(copyChannel.getCapacity());
      		newChannel.setChgsStatus(copyChannel.getChgsStatus());
      		newChannel.setCntr(copyChannel.getCntr());
      		newChannel.setOfflineLocalLock(0);
      		newChannel.setOfflineRemoteLock(0);
      		newChannel.setOnlineLock(0);
      		newChannel.setPrice(copyChannel.getPrice());
      		newChannel.setWarning(copyChannel.getWarning());
      		newChannel.setSurplus(copyChannel.getSurplus());
      		containerChannelService.save(newChannel);
	        response.setCode(CommonAttributes.SUCCESS);
	        response.setDesc(message("yxkj.request.success"));
		}else {
	        response.setCode(CommonAttributes.FAIL_COMMON);
	        response.setDesc(message("yxkj.request.failed"));
		}
      return response;
  }
  
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  @ApiOperation(value = "编辑货道", httpMethod = "POST", response = ResponseOne.class, notes = "用于编辑货道")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody BaseResponse updateContainer(
  		@ApiParam (name = "请求参数(json)", value = "userName:用户名; id:货道Id; channelData:货道数据", required = true)
  		@RequestBody ContainerChannelRequest request) {
      BaseResponse response = new BaseResponse(); 
    	if (request.getId() != null && request.getChannelData() != null 
      			&& request.getChannelData().getSn() != null) {
    		ContainerChannelData data = request.getChannelData();
      		ContainerChannel channel = containerChannelService.find(request.getId());
      		if (channel == null) {
    	        response.setCode(CommonAttributes.FAIL_COMMON);
    	        response.setDesc(message("yxkj.request.failed"));
			}
      		channel.setSn(data.getSn());
      		if (data.getGoodsId() != null) {
				Goods goods = goodsService.find(data.getGoodsId());
				channel.setGoods(goods);
			}
      		channel.setCapacity(data.getCapacity());
      		channel.setPrice(data.getPrice());
      		channel.setWarning(data.getWarning());
      		channel.setSurplus(data.getSurplus());
      		containerChannelService.update(channel);
	        response.setCode(CommonAttributes.SUCCESS);
	        response.setDesc(message("yxkj.request.success"));
		}else {
	        response.setCode(CommonAttributes.FAIL_COMMON);
	        response.setDesc(message("yxkj.request.failed"));
		}
      return response;
  }

}
