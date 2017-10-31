package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.BaseController;
import com.yxkj.json.admin.request.GoodsReportRequest;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.GoodsSaleInfoService;
import com.yxkj.utils.ExportHelper;
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

  @Autowired
  private ExportHelper exportHelper;

  /**
   * 运营管理-商品收入
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/salesList", method = RequestMethod.POST)
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
    LogUtil.debug(this.getClass(), "salesList",
        "request param: startTime: %s, endTime: %s, sceneId: %s,gCateId: %s,gCode: %s,gName: %s",
        startTime, endTime, request.getSceneId(), request.getgCateId(), request.getgCode(),
        request.getgName());
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


  /**
   * 导出数据概览Excel
   * 
   * @throws IOException
   */
  @RequestMapping(value = "/dataExport", method = {RequestMethod.GET, RequestMethod.POST})
  public void dataExport(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String startTimeStr = request.getParameter("startTime");
    String endTimeStr = request.getParameter("endTime");
    String sceneIdStr = request.getParameter("sceneId");
    String gCateIdStr = request.getParameter("gCateId");
    String gCode = request.getParameter("gCode");
    String gName = request.getParameter("gName");

    Date startTime = null;
    Date endTime = null;
    Long sceneId = null;
    Long gCateId = null;

    LogUtil.debug(this.getClass(), "dataExport",
        "request param: startTime: %s, endTime: %s, sceneId: %s,gCateId: %s,gCode: %s,gName: %s",
        startTime, endTime, sceneId, gCateId, gCode, gName);
    if (sceneIdStr != null) {
      sceneId = Long.valueOf(sceneIdStr);
    }
    if (gCateIdStr != null) {
      gCateId = Long.valueOf(gCateIdStr);
    }
    if (startTimeStr != null) {
      startTime = TimeUtils.formatDate2Day0(new Date(Long.valueOf(startTimeStr)));
    }
    if (endTimeStr != null) {
      endTime = TimeUtils.formatDate2Day59(new Date(Long.valueOf(endTimeStr)));
    }
    ResponseMultiple<Map<String, Object>> maps =
        goodsSaleInfoService.salesGoodsInfo(startTime, endTime, gCateId, sceneId, gName, gName,
            null, null);
    String title = "Goods Sales List"; // 工作簿标题，同时也是excel文件名前缀
    String[] headers = {"gSn", "gName", "gSpec", "gPrice", "gCate", "salesCount", "salesAmount"}; // 需要导出的字段
    String[] headersName = {"商品条码", "商品名称", "净含量", "商品种类", "默认售价", "商品销量", "商品销售额"}; // 字段对应列的列名
    List<Map<String, String>> mapList = null;
    if (maps.getMsg() != null && maps.getMsg().size() > 0) {
      mapList = exportHelper.convertMaps(maps.getMsg());
      exportListToExcel(response, mapList, title, headers, headersName);
    } else {
      mapList = new ArrayList<Map<String, String>>();
      exportListToExcel(response, mapList, title, headers, headersName);
    }
  }
}
