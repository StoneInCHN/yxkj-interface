package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.json.admin.request.GoodsReportRequest;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.GoodsSaleInfoService;
import com.yxkj.utils.TimeUtils;

/**
 * 运营管理-商品收入 -- Controller
 * 
 * @author Andrea
 * @version 2017年10月27日 下午3:25:46
 */
@Controller("goodsReportController")
@RequestMapping("/admin/goodsReport")
@Api(value = "(货柜后台)商品收入", description = "商品收入")
public class GoodsReportController extends BaseController {

  @Resource(name = "goodsSaleInfoServiceImpl")
  private GoodsSaleInfoService goodsSaleInfoService;


  /**
   * 运营管理-商品收入
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/salesGoods", method = RequestMethod.POST)
  @ApiOperation(value = "运营管理-商品收入", httpMethod = "POST", response = ResponseOne.class,
      notes = "运营管理-商品收入")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> salesGoods(
      @ApiParam(
          name = "请求参数(json)",
          value = "参数[userName:登录用户名; pageNumber:页码; pageSize:每页数量; gCateId:商品类别ID; sceneId:优享空间ID; gName:商品名称; gCode:商品条码; startTime:开始日期; endTime:结束日期]",
          required = false) @RequestBody GoodsReportRequest request) {
    Date startTime = request.getStartTime();
    Date endTime = request.getEndTime();
    if (endTime != null) {
      endTime = TimeUtils.formatDate2Day59(request.getEndTime());
    }
    if (startTime != null) {
      startTime = TimeUtils.formatDate2Day0(request.getStartTime());
    }
    ResponseMultiple<Map<String, Object>> response =
        goodsSaleInfoService.salesGoodsInfo(startTime, endTime, request.getgCateId(),
            request.getSceneId(), request.getgName(), request.getgCode(), request.getPageSize(),
            request.getPageNumber());
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

}
