package com.yxkj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.MobileBaseController;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.Goods;
import com.yxkj.entity.GoodsCategory;
import com.yxkj.entity.GoodsPic;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.filter.Filter.Operator;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.ordering.Ordering.Direction;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.json.request.GoodsInfoReq;
import com.yxkj.service.ContainerChannelService;
import com.yxkj.service.GoodsCategoryService;
import com.yxkj.service.GoodsService;
import com.yxkj.utils.FieldFilterUtils;

/**
 * Controller - 商品
 */
@Controller("GoodsController")
@RequestMapping("/goods")
@Api(value = "商品", description = "商品")
public class GoodsController extends MobileBaseController {

  @Resource(name = "goodsServiceImpl")
  private GoodsService goodsService;

  @Resource(name = "goodsCategoryServiceImpl")
  private GoodsCategoryService goodsCategoryService;


  @Resource(name = "containerChannelServiceImpl")
  private ContainerChannelService containerChannelService;


  /**
   * 根据货道编号查询单个商品
   * 
   * @param req
   * @return
   */
  @RequestMapping(value = "/getSgByChannel", method = RequestMethod.POST)
  @ApiOperation(value = "根据货道编号查询商品", httpMethod = "POST", response = ResponseOne.class,
      notes = "根据货道编号查询商品")
  @ApiResponses({@ApiResponse(code = 200,
      message = "code:0000-request success|1000-goods not exist")})
  public @ResponseBody ResponseOne<Map<String, Object>> getSgByChannel(
      @ApiParam(name = "请求参数(json)", value = "cSn:商品货道编号 | cImei:中控imei号", required = true) @RequestBody GoodsInfoReq req) {
    ResponseOne<Map<String, Object>> response = new ResponseOne<Map<String, Object>>();
    String cSn = req.getcSn();
    String cImei = req.getcImei();
    ContainerChannel cc = containerChannelService.getByCImeiAndChannel(cImei, cSn);
    if (cc == null || cc.getGoods() == null) {
      response.setCode(CommonAttributes.FAIL_COMMON);
      response.setDesc(message("yxkj.goods.noexist", cSn));
      return response;
    }
    Goods g = cc.getGoods();
    Map<String, Object> gMap = new HashMap<String, Object>();
    gMap.put("cSn", cSn);
    gMap.put("cId", cc.getId());
    gMap.put("gName", g.getName());
    gMap.put("gSpec", g.getSpec());
    gMap.put("price", cc.getPrice());
    gMap.put("count", cc.getSurplus());
    String gImg = "";
    for (GoodsPic goodsPic : g.getGoodsPics()) {
      if (goodsPic.getOrder() != null && goodsPic.getOrder() == 1) {// 获取中控显示的大图
        gImg = goodsPic.getSource();
      }
    }
    gMap.put("gImg", gImg);

    response.setMsg(gMap);
    response.setCode(CommonAttributes.SUCCESS);

    return response;
  }

  /**
   * 查询商品类别
   * 
   * @param req
   * @return
   */
  @RequestMapping(value = "/getCategory", method = RequestMethod.POST)
  @ApiOperation(value = "查询商品类别", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "查询商品类别")
  @ApiResponses({@ApiResponse(code = 200, message = "code:0000-request success")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> getCategory() {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();

    List<Filter> filters = new ArrayList<Filter>();
    filters.add(new Filter("isActive", Operator.eq, true));
    List<Ordering> orderings = new ArrayList<Ordering>();
    orderings.add(new Ordering("cateOrder", Direction.asc));

    List<GoodsCategory> categories = goodsCategoryService.findList(null, filters, orderings);
    String[] properties = {"id", "cateName", "cateOrder"};
    List<Map<String, Object>> result = FieldFilterUtils.filterCollectionMap(properties, categories);
    response.setMsg(result);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

  /**
   * 根据类别查询所有商品
   * 
   * @param req
   * @return
   */
  @RequestMapping(value = "/getByCate", method = RequestMethod.POST)
  @ApiOperation(value = "根据类别查询所有商品", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "根据类别查询所有商品")
  @ApiResponses({@ApiResponse(code = 200, message = "code:0000-request success")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> getByCate(
      @ApiParam(name = "请求参数(json)", value = "cateId:商品类别ID | cImei:中控imei号", required = true) @RequestBody GoodsInfoReq req) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    Long cateId = req.getCateId();
    String cImei = req.getcImei();
    Integer pageSize = req.getPageSize();
    Integer pageNum = req.getPageNumber();
    response = goodsService.getGoodsByCate(cateId, cImei, pageSize, pageNum);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }


  /**
   * 验证商品库存数量
   * 
   * @param req
   * @return
   */
  @RequestMapping(value = "/verifyStock", method = RequestMethod.POST)
  @ApiOperation(value = "验证商品库存数量", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "验证商品库存数量")
  @ApiResponses({@ApiResponse(code = 200,
      message = "code:0000-request success|1000-goods stock insufficient")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> verifyStock(
      @ApiParam(name = "请求参数(json)", value = "[cId(货道ID)-count(商品数量),cId(货道ID)-count(商品数量)]",
          required = true) @RequestBody GoodsInfoReq req) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    List<String> gList = req.getgList();
    response = containerChannelService.verifyStock(gList);
    return response;
  }


  /**
   * H5获取扫码后商品列表
   * 
   * @param req
   * @return
   */
  @RequestMapping(value = "/getForH5Pay", method = RequestMethod.POST)
  @ApiOperation(value = "H5获取扫码后商品列表", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "H5获取扫码后商品列表")
  @ApiResponses({@ApiResponse(code = 200, message = "code:0000-request success")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> getForH5Pay(
      @ApiParam(name = "请求参数(json)", value = "[cId(货道ID)-count(商品数量),cId(货道ID)-count(商品数量)]",
          required = true) @RequestBody GoodsInfoReq req) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    List<String> gList = req.getgList();
    response = containerChannelService.verifyStock(gList);
    return response;
  }
}
